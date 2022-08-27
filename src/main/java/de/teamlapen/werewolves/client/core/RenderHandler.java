package de.teamlapen.werewolves.client.core;

import com.mojang.blaze3d.pipeline.RenderTarget;
import de.teamlapen.vampirism.api.entity.factions.IFactionEntity;
import de.teamlapen.vampirism.config.VampirismConfig;
import de.teamlapen.werewolves.core.ModActions;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RenderHandler implements ResourceManagerReloadListener {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int ENTITY_NEAR_SQ_DISTANCE = 100;

    @NotNull
    private final Minecraft mc;
    private final int VISION_FADE_TICKS = 30;

    private OutlineBufferSource visionBuffer;

    private int ticks;
    private int lastTicks;
    @Nullable
    private PostChain blurShader;

    private int displayHeight, displayWidth;
    private boolean isInsideVisionRendering = false;


    private PostPass blit0;


    public RenderHandler(@NotNull Minecraft mc) {
        this.mc = mc;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (this.mc.level == null || this.mc.player == null || !this.mc.player.isAlive()) return;
        if (event.phase == TickEvent.Phase.END) return;
        this.lastTicks = this.ticks;
        WerewolfPlayer werewolf = WerewolfPlayer.get(this.mc.player);

        if (werewolf.getActionHandler().isActionActive(ModActions.SENSE.get())) {
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
    public void onRenderLivingPost(RenderLivingEvent.Post<?, ?> event) {
        if (!this.isInsideVisionRendering && this.shouldRenderVision()) {
            LivingEntity entity = event.getEntity();

            double dist = this.mc.player.distanceToSqr(entity);
            if (!(dist > VampirismConfig.BALANCE.vsBloodVisionDistanceSq.get() || entity.isInWater())) {
                int color = 0xA0A0A0;
                if (entity instanceof IFactionEntity) {
                    color = ((IFactionEntity) entity).getFaction().getColor();
                } else {
                    if (!entity.isInvertedHealAndHarm()) {
                        color = 0xFF0000;
                    }
                }
                EntityRenderDispatcher renderManager = this.mc.getEntityRenderDispatcher();
                if (this.visionBuffer == null) {
                    this.visionBuffer = new OutlineBufferSource(this.mc.renderBuffers().bufferSource());
                }
                int r = color >> 16 & 255;
                int g = color >> 8 & 255;
                int b = color & 255;
                int alpha = (int) ((dist > ENTITY_NEAR_SQ_DISTANCE ? 50 : (dist / (double) ENTITY_NEAR_SQ_DISTANCE * 50d)) * getVisionProgress(event.getPartialTick()));
                this.visionBuffer.setColor(r, g, b, alpha);
                float f = Mth.lerp(event.getPartialTick(), entity.yRotO, entity.getYRot());
                this.isInsideVisionRendering = true;
                EntityRenderer<? super Entity> entityRenderer = renderManager.getRenderer(entity);
                entityRenderer.render(entity, f, event.getPartialTick(), event.getPoseStack(), this.visionBuffer, renderManager.getPackedLightCoords(entity, event.getPartialTick()));
                this.mc.getMainRenderTarget().bindWrite(false);
                this.isInsideVisionRendering = false;

            }

        }
    }

    @SubscribeEvent
    public void onWorldLoad(LevelEvent.Load event) {
        this.ticks = 0;//Reset blood vision on world load
    }

    private float getVisionProgress(float partialTicks) {
        return (ticks + (ticks - lastTicks) * partialTicks) / (float) VISION_FADE_TICKS;
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_WEATHER) {
            if (mc.level == null) return;

            /*
             * DO NOT USE partial ticks from event. They are bugged: https://github.com/MinecraftForge/MinecraftForge/issues/6380
             */
            float partialTicks = mc.getFrameTime();


            if (displayHeight != mc.getWindow().getHeight() || displayWidth != mc.getWindow().getWidth()) {
                this.displayHeight = mc.getWindow().getHeight();
                this.displayWidth = mc.getWindow().getWidth();
                this.updateFramebufferSize(this.displayWidth, this.displayHeight);
            }

            if (shouldRenderVision()) {
                adjustVisionShaders(getVisionProgress(partialTicks));
                this.blurShader.process(partialTicks);
                if (this.visionBuffer != null) this.visionBuffer.endOutlineBatch();
            }
        }
    }

    private void adjustVisionShaders(float progress) {
        if (blit0 == null) return;
        progress = Mth.clamp(progress, 0, 1);
        blit0.getEffect().safeGetUniform("ColorModulate").set((1 - 0.4F * progress), (1 - 0.5F * progress), (1 - 0.3F * progress), 1);

    }

    private void updateFramebufferSize(int width, int height) {
        if (this.blurShader != null) {
            this.blurShader.resize(width, height);
        }
    }

    private boolean shouldRenderVision() {
        return this.ticks > 0 && this.blurShader != null && this.mc.player != null;
    }

    @Override
    public void onResourceManagerReload(@NotNull ResourceManager p_10758_) {
        this.reMakeShader();
    }

    private void reMakeShader() {
        if (this.blurShader != null) {
            this.blurShader.close();
        }
        ResourceLocation resourcelocationBlur = new ResourceLocation(REFERENCE.VMODID, "shaders/blank.json");
        try {
            this.blurShader = new PostChain(this.mc.getTextureManager(), this.mc.getResourceManager(), this.mc.getMainRenderTarget(), resourcelocationBlur);
            RenderTarget swap = this.blurShader.getTempTarget("swap");

            blit0 = blurShader.addPass("blit", swap, this.mc.getMainRenderTarget());

            this.blurShader.resize(this.mc.getWindow().getWidth(), this.mc.getWindow().getHeight());

        } catch (Exception e) {
            LOGGER.warn("Failed to load blood vision blur shader", e);
            this.blurShader = null;
        }
    }
}
