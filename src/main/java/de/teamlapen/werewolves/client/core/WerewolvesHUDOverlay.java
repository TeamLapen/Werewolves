package de.teamlapen.werewolves.client.core;

import com.mojang.blaze3d.platform.GlStateManager;
import de.teamlapen.lib.lib.client.gui.ExtendedGui;
import de.teamlapen.werewolves.core.WerewolfActions;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.player.werewolf.actions.WerewolfAction;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class WerewolvesHUDOverlay extends ExtendedGui {

    private final Minecraft mc = Minecraft.getInstance();
    private final ResourceLocation icons = new ResourceLocation("vampirism" + ":textures/gui/icons.png");//TODO other icon
    private final ResourceLocation FUR = new ResourceLocation(REFERENCE.MODID,"textures/gui/overlay/werewolf_fur_border.png");

    @SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Pre event) {
        if(mc.player == null || !mc.player.isAlive()) {
            return;
        }
        switch (event.getType()){
            case CROSSHAIRS:
                this.renderCrosshair(event);
                break;
            case ALL:
                this.renderFur(event);
                break;
        }
    }

    @SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Post event) {
        if(mc.player == null || !mc.player.isAlive()) {
            return;
        }
        if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            this.renderExperienceBar(event);
        }
    }

    private void renderFangs(int width, int height) {//TODO replace texture
        float r = ((0xFFFFFF & 0xFF0000) >> 16) / 256f;
        float g = ((0xFFFFFF & 0xFF00) >> 8) / 256f;
        float b = (0xFFFFFF & 0xFF) / 256f;
        this.mc.getTextureManager().bindTexture(icons);
        int left = width / 2 - 8;
        int top = height / 2 - 4;
        GL11.glEnable(GL11.GL_BLEND);
        GlStateManager.color4f(1f, 1f, 1f, 0.7F);
        blit(left, top, 27, 0, 16, 10);
        GlStateManager.color4f(r, g, b, 0.8F);
        int percHeight = 10 * 100;
        blit(left, top + (10 - percHeight), 27, 10 - percHeight, 16, percHeight);
        GlStateManager.color4f(1F, 1F, 1F, 1F);
        GL11.glDisable(GL11.GL_BLEND);
    }

    private void renderFur(RenderGameOverlayEvent.Pre event) {
        if(Helper.isWerewolf(this.mc.player) && WerewolfPlayer.getOpt(this.mc.player).map(player -> player.getActionHandler().isActionActive(WerewolfActions.werewolf_form)).orElse(false)){
            this.mc.getTextureManager().bindTexture(FUR);
            GlStateManager.enableBlend();
            blit(0, 0, this.blitOffset, 0, 0, this.mc.mainWindow.getWidth(), this.mc.mainWindow.getHeight(), this.mc.mainWindow.getScaledHeight(), this.mc.mainWindow.getScaledWidth());
            GlStateManager.disableBlend();
        }
    }

    private void renderCrosshair(RenderGameOverlayEvent.Pre event) {
        RayTraceResult p = Minecraft.getInstance().objectMouseOver;

        if (p != null && p.getType() == RayTraceResult.Type.ENTITY) {
            WerewolfPlayer player = WerewolfPlayer.get(mc.player);
            if (player.canBite(((EntityRayTraceResult) p).getEntity())) {
                Entity entity = ((EntityRayTraceResult) p).getEntity();
                renderFangs(this.mc.mainWindow.getScaledWidth(), this.mc.mainWindow.getScaledHeight());
                event.setCanceled(true);
            }
        }
    }

    private void renderExperienceBar(RenderGameOverlayEvent.Post event) {
        PlayerEntity player = mc.player;
        if(Helper.isWerewolf(player)) {
            WerewolfPlayer werewolf = WerewolfPlayer.get(player);
            if(werewolf.getActionHandler().isActionActive(WerewolfActions.werewolf_form)) {
                float perc = WerewolfAction.getDurationPercentage(werewolf);
                renderExpBar(perc);
            }
        }
    }

    private void renderExpBar(float perc) { //TODO maybe not in creative mode
        int scaledWidth = Minecraft.getInstance().ingameGUI.scaledWidth;
        int scaledHeight = Minecraft.getInstance().ingameGUI.scaledHeight;
        int x = scaledWidth / 2 - 91;
        this.mc.getProfiler().startSection("werewolfActionDurationBar");
        this.mc.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
        GlStateManager.color4f(1f, 0.1f, 0, 1);

        int k = (int) ((1 -perc) * 183.0F);
        int l = scaledHeight - 32 + 3;
        this.blit(x, l, 0, 64, 182, 5);
        if (k > 0) {
            this.blit(x+k, l, k, 69, 182-k, 5);
        }
        this.mc.getProfiler().endSection();
    }

}
