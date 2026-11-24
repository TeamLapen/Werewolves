package de.teamlapen.werewolves.client.core;

import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.resource.ResourceHandle;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.library.client.renderer.NoOpOutlineBufferSource;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.core.ModActions;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.SubmitNodeCollection;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.feature.ModelPartFeatureRenderer;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Set;

/**
 * Renders the werewolf sense vision. Entities near the player are highlighted from red to white depending on their remaining health.
 * <p>
 * The sensed entities are first drawn as flat colored silhouettes into a separate target ({@link #senseTarget}), using the nearest surface
 * of each entity so that overlapping model parts merge into one shape. The {@code werewolves:sense} post chain then composites them onto
 * the world: dimmed world with vignette, and the silhouettes heavily blurred into a colored fog.
 * <p>
 * Shader parameters that change every frame are passed through the 1x1 {@link #paramsTarget}, as post chain uniforms are static.
 */
public class RenderHandler implements ResourceManagerReloadListener {
    private static final int ENTITY_NEAR_SQ_DISTANCE = 100;
    private static final float ENTITY_NEAR_MIN_INTENSITY = 0.35f;
    private static final int VISION_FADE_TICKS = 30;
    private static final ContextKey<Integer> SENSE_COLOR = new ContextKey<>(WIdentifier.mod("sense_color"));
    private static final Identifier SENSE_POST_CHAIN = WIdentifier.mod("sense");
    private static final Identifier SENSE_TARGET_ID = WIdentifier.mod("sense");
    private static final Identifier SENSE_PARAMS_TARGET_ID = WIdentifier.mod("sense_params");
    private static final Set<Identifier> SENSE_TARGETS = Set.of(PostChain.MAIN_TARGET_ID, SENSE_TARGET_ID, SENSE_PARAMS_TARGET_ID);

    private static @Nullable RenderTarget senseTarget;
    private static @Nullable RenderTarget paramsTarget;

    private final SubmitNodeStorage nodeCollector = new SubmitNodeStorage();
    private final OutlineBufferSource noOp = new NoOpOutlineBufferSource();
    @UnknownNullability
    private SenseBufferSource visionBuffer;
    private List<LivingEntityRenderState> cachedRenderStates = List.of();

    private int ticks;
    private int lastTicks;

    /**
     * @return the target the sensed entity silhouettes are rendered to. Used by {@link ModRenderPipelines#SENSE_TARGET}
     */
    static @Nullable RenderTarget senseTarget() {
        return senseTarget;
    }

    private Minecraft mc() {
        return Minecraft.getInstance();
    }

    public void registerReloadListeners(AddClientReloadListenersEvent event) {
        event.addListener(WIdentifier.mod("render_handler"), this);
    }

    public void createBuffers(RegisterRenderBuffersEvent event) {
        this.visionBuffer = new SenseBufferSource(new ByteBufferBuilder(786432));
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
        PostChain chain = this.mc().getShaderManager().getPostChain(SENSE_POST_CHAIN, SENSE_TARGETS);
        if (chain == null) return;
        RenderTarget main = this.mc().getMainRenderTarget();
        RenderTarget sense = getOrResizeTargets(main.width, main.height);
        float progress = getVisionProgress();

        CommandEncoder encoder = RenderSystem.getDevice().createCommandEncoder();
        encoder.clearColorAndDepthTextures(sense.getColorTexture(), 0, sense.getDepthTexture(), 1.0);
        encoder.clearColorTexture(paramsTarget.getColorTexture(), ARGB.colorFromFloat(1, progress, 0, 0));

        renderSilhouettes(event);

        FrameGraphBuilder frame = new FrameGraphBuilder();
        PostChain.TargetBundle targets = new SenseTargetBundle(frame.importExternal("main", main), frame.importExternal("werewolves_sense", sense), frame.importExternal("werewolves_sense_params", paramsTarget));
        chain.addToFrame(frame, main.width, main.height, targets);
        frame.execute(this.mc().gameRenderer.resourcePool);
    }

    private void renderSilhouettes(RenderLevelStageEvent.AfterWeather event) {
        PoseStack poseStack = new PoseStack();
        ModelPartFeatureRenderer parts = this.mc().gameRenderer.getFeatureRenderDispatcher().werewolves$modelPartFeatureRenderer();
        ModelFeatureRenderer models = this.mc().gameRenderer.getFeatureRenderDispatcher().werewolves$modelFeatureRenderer();
        EntityRenderDispatcher dispatcher = event.getLevelRenderer().werewolves$entityRenderDispatcher();

        // make sure the draws end up in the sense target
        GpuTextureView colorOverride = RenderSystem.outputColorTextureOverride;
        GpuTextureView depthOverride = RenderSystem.outputDepthTextureOverride;
        RenderSystem.outputColorTextureOverride = null;
        RenderSystem.outputDepthTextureOverride = null;
        try {
            for (LivingEntityRenderState state : this.cachedRenderStates) {
                Integer color = state.getRenderData(SENSE_COLOR);
                float intensity = Mth.clampedLerp((float) (state.distanceToCameraSq / ENTITY_NEAR_SQ_DISTANCE), ENTITY_NEAR_MIN_INTENSITY, 1f);
                this.visionBuffer.setColor(ARGB.opaque(ARGB.scaleRGB(color == null ? 0xA0A0A0 : color, intensity)));

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
        } finally {
            RenderSystem.outputColorTextureOverride = colorOverride;
            RenderSystem.outputDepthTextureOverride = depthOverride;
            this.cachedRenderStates = List.of();
        }
    }

    private static RenderTarget getOrResizeTargets(int width, int height) {
        if (senseTarget == null) {
            senseTarget = new TextureTarget("Werewolf Sense", width, height, true);
        } else if (senseTarget.width != width || senseTarget.height != height) {
            senseTarget.resize(width, height);
        }
        if (paramsTarget == null) {
            paramsTarget = new TextureTarget("Werewolf Sense Params", 1, 1, false);
        }
        return senseTarget;
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

    private static class SenseTargetBundle implements PostChain.TargetBundle {
        private ResourceHandle<RenderTarget> main;
        private ResourceHandle<RenderTarget> sense;
        private ResourceHandle<RenderTarget> params;

        private SenseTargetBundle(ResourceHandle<RenderTarget> main, ResourceHandle<RenderTarget> sense, ResourceHandle<RenderTarget> params) {
            this.main = main;
            this.sense = sense;
            this.params = params;
        }

        @Override
        public void replace(Identifier id, ResourceHandle<RenderTarget> handle) {
            if (id.equals(PostChain.MAIN_TARGET_ID)) {
                this.main = handle;
            } else if (id.equals(SENSE_TARGET_ID)) {
                this.sense = handle;
            } else if (id.equals(SENSE_PARAMS_TARGET_ID)) {
                this.params = handle;
            } else {
                throw new IllegalArgumentException("No target with id " + id);
            }
        }

        @Override
        public @Nullable ResourceHandle<RenderTarget> get(Identifier id) {
            if (id.equals(PostChain.MAIN_TARGET_ID)) {
                return this.main;
            } else if (id.equals(SENSE_TARGET_ID)) {
                return this.sense;
            } else if (id.equals(SENSE_PARAMS_TARGET_ID)) {
                return this.params;
            }
            return null;
        }
    }
}
