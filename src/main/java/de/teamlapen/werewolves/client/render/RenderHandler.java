package de.teamlapen.werewolves.client.render;

import de.teamlapen.vampirism.api.entity.factions.IFactionEntity;
import de.teamlapen.vampirism.config.VampirismConfig;
import de.teamlapen.werewolves.core.WerewolfActions;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OutlineLayerBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.resource.VanillaResourceType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Predicate;

public class RenderHandler implements ISelectiveResourceReloadListener {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int ENTITY_NEAR_SQ_DISTANCE = 100;

    @Nonnull
    private final Minecraft mc;
    private final int VISION_FADE_TICKS = 30;

    private OutlineLayerBuffer visionBuffer;

    private int ticks;
    private int lastTicks;
    @Nullable
    private ShaderGroup blurShader;

    private int displayHeight, displayWidth;
    private boolean isInsideVisionRendering = false;


    private Shader blit0;


    public RenderHandler(@Nonnull Minecraft mc) {
        this.mc = mc;
    }

    @Nullable
    @Override
    public IResourceType getResourceType() {
        return VanillaResourceType.SHADERS;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (this.mc.level == null || this.mc.player == null || !this.mc.player.isAlive()) return;
        if (event.phase == TickEvent.Phase.END) return;
        this.lastTicks = this.ticks;
        WerewolfPlayer werewolf = WerewolfPlayer.get(this.mc.player);

        if (werewolf.getActionHandler().isActionActive(WerewolfActions.sense)) {
            if (this.ticks < VISION_FADE_TICKS) {
                this.ticks++;
            }
        }else {
            if (this.ticks > 0) {
                this.ticks-=2;
            }
        }
    }

    @SubscribeEvent
    public void onRenderLivingPost(RenderLivingEvent.Post<?, ?> event) {
        if (!this.isInsideVisionRendering && this.shouldRenderVision()) {
            LivingEntity entity = event.getEntity();

            boolean flag = true;
            double dist = this.mc.player.distanceToSqr(entity);
            if (dist > VampirismConfig.BALANCE.vsBloodVisionDistanceSq.get()) {
                flag = false;
            }
            if (flag) {
                int color = 0xA0A0A0;
                if (entity instanceof IFactionEntity) {
                    color = ((IFactionEntity) entity).getFaction().getColor().getRGB();
                } else {
                    if (!entity.isInvertedHealAndHarm()) {
                        color = 0xFF0000;
                    }
                }
                EntityRendererManager renderManager = this.mc.getEntityRenderDispatcher();
                if (this.visionBuffer == null) {
                    this.visionBuffer = new OutlineLayerBuffer(this.mc.renderBuffers().bufferSource());
                }
                int r = color >> 16 & 255;
                int g = color >> 8 & 255;
                int b = color & 255;
                int alpha = (int) ((dist > ENTITY_NEAR_SQ_DISTANCE ? 50 : (dist / (double) ENTITY_NEAR_SQ_DISTANCE * 50d)) * getVisionProgress(event.getPartialRenderTick()));
                this.visionBuffer.setColor(r, g, b, alpha);
                float f = MathHelper.lerp(event.getPartialRenderTick(), entity.yRotO, entity.yRot);
                this.isInsideVisionRendering = true;
                EntityRenderer<? super Entity> entityRenderer = renderManager.getRenderer(entity);
                entityRenderer.render(entity, f, event.getPartialRenderTick(), event.getMatrixStack(), this.visionBuffer, renderManager.getPackedLightCoords(entity, event.getPartialRenderTick()));
                this.mc.getMainRenderTarget().bindWrite(false);
                this.isInsideVisionRendering = false;

            }

        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        this.ticks = 0;//Reset blood vision on world load
    }

    private float getVisionProgress(float partialTicks) {
        return (ticks + (ticks - lastTicks) * partialTicks) / (float) VISION_FADE_TICKS;
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
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

    private void adjustVisionShaders(float progress) {
        if (blit0 == null) return;
        progress = MathHelper.clamp(progress, 0, 1);
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
    public void onResourceManagerReload(@Nonnull IResourceManager resourceManager, @Nonnull Predicate<IResourceType> resourcePredicate) {
        this.reMakeShader();
    }

    private void reMakeShader() {
        if (this.blurShader != null) {
            this.blurShader.close();
        }
        ResourceLocation resourcelocationBlur = new ResourceLocation(REFERENCE.VMODID, "shaders/blank.json");
        try {
            this.blurShader = new ShaderGroup(this.mc.getTextureManager(), this.mc.getResourceManager(), this.mc.getMainRenderTarget(), resourcelocationBlur);
            Framebuffer swap = this.blurShader.getTempTarget("swap");

            blit0 = blurShader.addPass("blit", swap, this.mc.getMainRenderTarget());

            this.blurShader.resize(this.mc.getWindow().getWidth(), this.mc.getWindow().getHeight());

        } catch (Exception e) {
            LOGGER.warn("Failed to load blood vision blur shader", e);
            this.blurShader = null;
        }
    }
}
