package de.teamlapen.werewolves.client.core;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.library.client.renderer.NoOpOutlineBufferSource;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.core.ModActions;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelTargetBundle;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.feature.ModelPartFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.client.renderer.SubmitNodeCollection;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;
import java.util.SequencedMap;

/**
 * Renders the werewolf sense vision. Entities near the player are highlighted from red to white depending on their remaining health.
 * <p>
 * Based on the Vampirism blood vision renderer
 */
public class RenderHandler implements ResourceManagerReloadListener {
    private static final int ENTITY_NEAR_SQ_DISTANCE = 100;
    private static final int VISION_FADE_TICKS = 30;
    private static final ContextKey<Integer> SENSE_COLOR = new ContextKey<>(WIdentifier.mod("sense_color"));

    private final SubmitNodeStorage nodeCollector = new SubmitNodeStorage();
    private final OutlineBufferSource noOp = new NoOpOutlineBufferSource();
    @UnknownNullability
    private ColoredBufferSource visionBuffer;
    private List<LivingEntityRenderState> cachedRenderStates = List.of();

    private int ticks;
    private int lastTicks;

    private Minecraft mc() {
        return Minecraft.getInstance();
    }

    public void registerReloadListeners(AddClientReloadListenersEvent event) {
        event.addListener(WIdentifier.mod("render_handler"), this);
    }

    public void createBuffers(RegisterRenderBuffersEvent event) {
        SequencedMap<RenderType, ByteBufferBuilder> map = Util.make(new Object2ObjectLinkedOpenHashMap<>(), m -> m.put(ModRenderPipelines.solidTransparencyEntity(), new ByteBufferBuilder(786432)));
        this.visionBuffer = new ColoredBufferSource(map, new ByteBufferBuilder(786432));
    }

    @SuppressWarnings("unchecked")
    public static void addRenderStateModifier(RegisterRenderStateModifiersEvent event) {
        event.registerEntityModifier((Class<? extends net.minecraft.client.renderer.entity.EntityRenderer<? extends LivingEntity, ? extends LivingEntityRenderState>>) (Object) LivingEntityRenderer.class, RenderHandler::extractSenseColor);
    }

    private static void extractSenseColor(LivingEntity entity, LivingEntityRenderState state) {
        float maxHealth = entity.getMaxHealth();
        float healthPercent = maxHealth > 0 ? Mth.clamp(entity.getHealth() / maxHealth, 0, 1) : 1;
        int greenBlue = Mth.floor((1 - healthPercent) * 255);
        int color = ARGB.color(255, greenBlue, greenBlue) & 0xFFFFFF;
        state.setRenderData(SENSE_COLOR, entity.isInWater() ? null : color);
    }

    @SubscribeEvent
    public void onClientTick(ClientTickEvent.Pre event) {
        if (this.mc() == null || this.mc().level == null || this.mc().player == null || !this.mc().player.isAlive()) return;
        this.lastTicks = this.ticks;
        if (Helper.isWerewolf(this.mc().player) && WerewolfPlayer.get(this.mc().player).getActionHandler().isActionActive(ModActions.SENSE)) {
            if (this.ticks < VISION_FADE_TICKS) {
                this.ticks++;
            }
        } else {
            if (this.ticks > 0) {
                this.ticks -= 2;
            }
        }
    }

    @SubscribeEvent
    public void onExtract(ExtractLevelRenderStateEvent event) {
        if (!shouldRenderVision()) {
            this.cachedRenderStates = List.of();
            return;
        }
        double maxDistance = ModConfig.balance().SKILLS.sense_distance_sq.get();
        this.cachedRenderStates = event.getRenderState().entityRenderStates.stream()
                .filter(LivingEntityRenderState.class::isInstance)
                .map(LivingEntityRenderState.class::cast)
                .filter(x -> x.distanceToCameraSq <= maxDistance)
                .filter(x -> x.getRenderData(SENSE_COLOR) != null)
                .toList();
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderLevelStageEvent.AfterWeather event) {
        if (!shouldRenderVision() || this.visionBuffer == null) return;
        PostChain blur = this.mc().getShaderManager().getPostChain(Identifier.withDefaultNamespace("blur"), LevelTargetBundle.MAIN_TARGETS);
        if (blur == null) return;
        blur.process(this.mc().getMainRenderTarget(), this.mc().gameRenderer.resourcePool);

        PoseStack poseStack = new PoseStack();
        ModelPartFeatureRenderer parts = this.mc().gameRenderer.getFeatureRenderDispatcher().werewolves$modelPartFeatureRenderer();
        ModelFeatureRenderer models = this.mc().gameRenderer.getFeatureRenderDispatcher().werewolves$modelFeatureRenderer();
        EntityRenderDispatcher dispatcher = event.getLevelRenderer().werewolves$entityRenderDispatcher();
        float progress = getVisionProgress();

        for (LivingEntityRenderState state : this.cachedRenderStates) {
            double dist = state.distanceToCameraSq;
            float alpha = (float) ((dist > ENTITY_NEAR_SQ_DISTANCE ? 50 : (dist / (double) ENTITY_NEAR_SQ_DISTANCE * 50d)) / 255d) * progress;
            Integer color = state.getRenderData(SENSE_COLOR);
            this.visionBuffer.setColor(ARGB.color(Mth.clamp(alpha, 0, 1), color == null ? 0xA0A0A0 : color));

            submitEntity(poseStack, event.getLevelRenderState(), state, dispatcher);
            for (SubmitNodeCollection value : this.nodeCollector.getSubmitsPerOrder().values()) {
                parts.renderSolid(value, this.visionBuffer, this.noOp, this.visionBuffer);
                parts.renderTranslucent(value, this.visionBuffer, this.noOp, this.visionBuffer);
                models.renderSolid(value, this.visionBuffer, this.noOp, this.visionBuffer);
                models.renderTranslucent(value, this.visionBuffer, this.noOp, this.visionBuffer);
            }
            this.nodeCollector.clear();
            this.visionBuffer.endBatch();
        }
        this.cachedRenderStates = List.of();
    }

    private void submitEntity(PoseStack poseStack, LevelRenderState levelRenderState, EntityRenderState state, EntityRenderDispatcher dispatcher) {
        Vec3 camera = levelRenderState.cameraRenderState.pos;
        dispatcher.submit(state, levelRenderState.cameraRenderState, state.x - camera.x(), state.y - camera.y(), state.z - camera.z(), poseStack, this.nodeCollector);
    }

    @SubscribeEvent
    public void onWorldLoad(LevelEvent.Load event) {
        this.ticks = 0;
        this.lastTicks = 0;
    }

    private float getVisionProgress() {
        float partialTicks = this.mc().getDeltaTracker().getGameTimeDeltaPartialTick(false);
        return Mth.clamp((ticks + (ticks - lastTicks) * partialTicks) / (float) VISION_FADE_TICKS, 0, 1);
    }

    private boolean shouldRenderVision() {
        return this.ticks > 0 && this.mc().player != null;
    }

    @Override
    public void onResourceManagerReload(@NotNull ResourceManager resourceManager) {
    }
}
