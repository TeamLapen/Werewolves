package de.teamlapen.werewolves.client.render;

import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import de.teamlapen.vampirism.api.entity.hunter.IHunter;
import de.teamlapen.vampirism.api.entity.vampire.IVampire;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.WerewolfSkills;
import de.teamlapen.werewolves.entities.IWerewolf;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class RenderHandler {
    private static final Logger LOGGER = LogManager.getLogger();

    private final Minecraft mc;
    private final List<LivingEntity> meatEntities = new LinkedList<>();
    private final List<LivingEntity> vampireEntities = new LinkedList<>();
    private final List<LivingEntity> hunterEntities = new LinkedList<>();
    private final List<LivingEntity> otherEntities = new LinkedList<>();
    private int displayHeight, displayWidth;

    private boolean bloodVisionShaderInit;
    private boolean shaderWarning;
    private boolean showedShaderWarning;

    private Framebuffer meatFrameBuffer;
    private ShaderGroup meatVisionShader;
    private boolean meatVisionRendered;

    private Framebuffer vampireFrameBuffer;
    private ShaderGroup vampireVisionShader;
    private boolean vampireVisionRendered;

    private Framebuffer hunterFrameBuffer;
    private ShaderGroup hunterVisionShader;
    private boolean hunterVisionRendered;

    private Framebuffer otherFrameBuffer;
    private ShaderGroup otherVisionShader;
    private boolean otherVisionRendered;
    private Shader blit1, blit2, blit3, blit4;

    private int ticks;

    public RenderHandler(Minecraft mc) {
        this.mc = mc;
        this.displayHeight = mc.mainWindow.getHeight();
        this.displayWidth = mc.mainWindow.getWidth();
    }

    public void addTrackedEntities(List<LivingEntity> entities) {
        if (WerewolfPlayer.get(this.mc.player).getSkillHandler().isSkillEnabled(WerewolfSkills.advanced_sense)) {
            this.hunterEntities.addAll(entities.stream().filter(entity -> entity instanceof IHunter).collect(Collectors.toList()));
            entities.removeAll(this.hunterEntities);
            this.vampireEntities.addAll(entities.stream().filter(entity -> entity instanceof IVampire).collect(Collectors.toList()));
            entities.removeAll(this.vampireEntities);
            this.meatEntities.addAll(entities.stream().filter(entity -> !(entity.isEntityUndead() || entity instanceof IWerewolf)).collect(Collectors.toList()));
            entities.removeAll(this.meatEntities);
            otherEntities.addAll(entities);
        } else {
            this.meatEntities.addAll(entities);
        }

        this.ticks = WerewolvesConfig.BALANCE.SKILLS.sense_duration.get() * 20;
    }

    public void clearTrackedEntities() {
        this.meatEntities.clear();
        this.ticks = -1;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (mc.world == null || mc.player == null || !mc.player.isAlive()) return;
        if (event.phase == TickEvent.Phase.END) return;

        if (ticks > 0) {
            meatEntities.removeIf(entity -> !entity.isAlive());
            otherEntities.removeIf(entity -> !entity.isAlive());
            vampireEntities.removeIf(entity -> !entity.isAlive());
            hunterEntities.removeIf(entity -> !entity.isAlive());
            --ticks;
        } else if (ticks == 0) {
            this.meatEntities.clear();
            this.otherEntities.clear();
            this.vampireEntities.clear();
            this.hunterEntities.clear();
            ticks = -1;
        }

        if (shaderWarning && mc.player != null) {
            shaderWarning = false;
            showedShaderWarning = true;
            mc.player.sendMessage(new StringTextComponent("Blood vision does not work on your hardware, because shaders are not supported"));
            mc.player.sendMessage(new StringTextComponent("If you are running on recent hardware and use updated drivers, but this still shows up, please contact the author of Werewolves"));
        }
    }

    @SubscribeEvent
    public void onRenderLast(RenderWorldLastEvent event) {
        if (mc.world == null) return;
        if (ticks > 0) {
            renderBloodVisionOutlines(event.getPartialTicks());
        }

        if (displayHeight != mc.mainWindow.getHeight() || displayWidth != mc.mainWindow.getWidth()) {
            this.displayHeight = mc.mainWindow.getHeight();
            this.displayWidth = mc.mainWindow.getWidth();
            if (GLX.isNextGen() && isRenderEntityOutlines()) {
                meatVisionShader.createBindFramebuffers(displayWidth, displayHeight);
                vampireVisionShader.createBindFramebuffers(displayWidth, displayHeight);
                hunterVisionShader.createBindFramebuffers(displayWidth, displayHeight);
                otherVisionShader.createBindFramebuffers(displayWidth, displayHeight);
            }
        }
    }

    private boolean isRenderEntityOutlines() {
        return this.meatFrameBuffer != null && this.meatVisionShader != null && this.mc.player != null;
    }

    private void renderBloodVisionOutlines(float partialTicks) {
        if (!bloodVisionShaderInit) {
            makeBloodVisionShader();
            bloodVisionShaderInit = true;
        }
        if (!isRenderEntityOutlines()) {
            if (!showedShaderWarning) {
                shaderWarning = true;
            }
            return;
        }

        adjustBloodVisionShaders();

        if (!meatEntities.isEmpty() || this.meatVisionRendered) {
            meatVisionRendered = renderEntityOutlines(meatEntities, meatVisionShader, meatFrameBuffer, partialTicks);
        }

        if (!vampireEntities.isEmpty() || this.vampireVisionRendered) {
            vampireVisionRendered = renderEntityOutlines(vampireEntities, vampireVisionShader, vampireFrameBuffer, partialTicks);
        }

        if (!hunterEntities.isEmpty() || this.hunterVisionRendered) {
            hunterVisionRendered = renderEntityOutlines(hunterEntities, hunterVisionShader, hunterFrameBuffer, partialTicks);
        }

        if (!otherEntities.isEmpty() || this.otherVisionRendered) {
            otherVisionRendered = renderEntityOutlines(otherEntities, otherVisionShader, otherFrameBuffer, partialTicks);
        }

        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);


        this.meatFrameBuffer.framebufferRenderExt(this.mc.mainWindow.getFramebufferWidth(), this.mc.mainWindow.getFramebufferHeight(), false);
        this.vampireFrameBuffer.framebufferRenderExt(this.mc.mainWindow.getFramebufferWidth(), this.mc.mainWindow.getFramebufferHeight(), false);
        this.hunterFrameBuffer.framebufferRenderExt(this.mc.mainWindow.getFramebufferWidth(), this.mc.mainWindow.getFramebufferHeight(), false);
        this.otherFrameBuffer.framebufferRenderExt(this.mc.mainWindow.getFramebufferWidth(), this.mc.mainWindow.getFramebufferHeight(), false);

        this.mc.getFramebuffer().bindFramebuffer(false);

        GlStateManager.disableBlend();
    }

    private void adjustBloodVisionShaders() {

        blit1.getShaderManager().getShaderUniform("ColorModulate").set(1F, 0.1F, 0.1F, 1F);
        blit2.getShaderManager().getShaderUniform("ColorModulate").set(1F, 0.1F, 1.0F, 1F);
        blit3.getShaderManager().getShaderUniform("ColorModulate").set(0.1F, 0.1F, 1.0F, 1F);
        blit4.getShaderManager().getShaderUniform("ColorModulate").set(0.25F, 0.25F, 0.25F, 1F);
    }

    private void makeBloodVisionShader() {
        if (GLX.isNextGen()) {
            if (ShaderLinkHelper.getStaticShaderLinkHelper() == null) {
                ShaderLinkHelper.setNewStaticShaderLinkHelper();
            }

            ResourceLocation resourcelocationOutline = new ResourceLocation("vampirism", "shaders/blood_vision_outline.json");

            try {


                this.meatVisionShader = new ShaderGroup(this.mc.getTextureManager(), this.mc.getResourceManager(), this.mc.getFramebuffer(), resourcelocationOutline);
                Framebuffer swap = this.meatVisionShader.getFramebufferRaw("swap");
                this.meatFrameBuffer = this.meatVisionShader.getFramebufferRaw("final");

                blit1 = meatVisionShader.addShader("blit", swap, meatFrameBuffer);

                this.meatVisionShader.createBindFramebuffers(this.mc.mainWindow.getFramebufferWidth(), this.mc.mainWindow.getFramebufferHeight());

                this.vampireVisionShader = new ShaderGroup(this.mc.getTextureManager(), this.mc.getResourceManager(), this.mc.getFramebuffer(), resourcelocationOutline);
                swap = this.vampireVisionShader.getFramebufferRaw("swap");
                this.vampireFrameBuffer = this.vampireVisionShader.getFramebufferRaw("final");

                blit2 = vampireVisionShader.addShader("blit", swap, vampireFrameBuffer);

                this.vampireVisionShader.createBindFramebuffers(this.mc.mainWindow.getFramebufferWidth(), this.mc.mainWindow.getFramebufferHeight());

                this.hunterVisionShader = new ShaderGroup(this.mc.getTextureManager(), this.mc.getResourceManager(), this.mc.getFramebuffer(), resourcelocationOutline);
                swap = this.hunterVisionShader.getFramebufferRaw("swap");
                this.hunterFrameBuffer = this.hunterVisionShader.getFramebufferRaw("final");

                blit3 = hunterVisionShader.addShader("blit", swap, hunterFrameBuffer);

                this.hunterVisionShader.createBindFramebuffers(this.mc.mainWindow.getFramebufferWidth(), this.mc.mainWindow.getFramebufferHeight());

                this.otherVisionShader = new ShaderGroup(this.mc.getTextureManager(), this.mc.getResourceManager(), this.mc.getFramebuffer(), resourcelocationOutline);
                swap = this.otherVisionShader.getFramebufferRaw("swap");
                this.otherFrameBuffer = this.otherVisionShader.getFramebufferRaw("final");

                blit4 = otherVisionShader.addShader("blit", swap, otherFrameBuffer);

                this.otherVisionShader.createBindFramebuffers(this.mc.mainWindow.getFramebufferWidth(), this.mc.mainWindow.getFramebufferHeight());

            } catch (IOException | JsonSyntaxException ioexception) {

                LOGGER.error("Failed to load shader", ioexception);
                this.meatVisionShader = null;
                this.meatFrameBuffer = null;
                this.vampireVisionShader = null;
                this.vampireFrameBuffer = null;
                this.hunterVisionShader = null;
                this.hunterFrameBuffer = null;
                this.otherVisionShader = null;
                this.otherFrameBuffer = null;
            }
        } else {
            this.meatVisionShader = null;
            this.meatFrameBuffer = null;
            this.vampireVisionShader = null;
            this.vampireFrameBuffer = null;
            this.hunterVisionShader = null;
            this.hunterFrameBuffer = null;
            this.otherVisionShader = null;
            this.otherFrameBuffer = null;
        }
    }

    private boolean renderEntityOutlines(List<LivingEntity> entities, ShaderGroup shader, Framebuffer framebuffer, float partialTicks) {
        EntityRendererManager renderManager = mc.getRenderManager();
        this.mc.world.getProfiler().startSection("senseVision");
        framebuffer.framebufferClear(Minecraft.IS_RUNNING_ON_MAC);
        if (!entities.isEmpty()) {
            this.mc.gameRenderer.enableLightmap();

            GlStateManager.depthFunc(519);
            GlStateManager.disableFog();

            framebuffer.bindFramebuffer(false);

            RenderHelper.disableStandardItemLighting();

            renderManager.setRenderOutlines(true);
            for (Entity entity1 : entities) {
                renderManager.renderEntityStatic(entity1, partialTicks, false);
            }

            renderManager.setRenderOutlines(false);

            RenderHelper.enableStandardItemLighting();

            GlStateManager.depthMask(false);
            shader.render(partialTicks);
            GlStateManager.enableLighting();
            GlStateManager.depthMask(true);
            GlStateManager.enableBlend();

            GlStateManager.enableColorMaterial();
            GlStateManager.depthFunc(515);
            GlStateManager.enableDepthTest();
            this.mc.gameRenderer.disableLightmap();
        }

        this.mc.getFramebuffer().bindFramebuffer(false);

        mc.world.getProfiler().endSection();

        return !entities.isEmpty();
    }
}
