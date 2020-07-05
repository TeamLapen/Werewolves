package de.teamlapen.werewolves.client.core;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import de.teamlapen.lib.lib.client.gui.ExtendedGui;
import de.teamlapen.werewolves.api.items.ISilverItem;
import de.teamlapen.werewolves.core.WerewolfActions;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.player.werewolf.actions.WerewolfAction;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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
    private final ResourceLocation ICONS = new ResourceLocation(REFERENCE.MODID, "textures/gui/hud.png");
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
                this.renderFur();
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

    private void renderFangs(int width, int height, Entity entity) {//TODO replace texture
        this.mc.getTextureManager().bindTexture(ICONS);
        int left = width / 2 - 9;
        int top = height / 2 - 6;
        boolean silver = false;
        for(ItemStack stack : entity.getArmorInventoryList()) {
            if(stack.getItem() instanceof ISilverItem) {
                silver = true;
                break;
            }
        }
        GL11.glEnable(GL11.GL_BLEND);
        GlStateManager.color4f(1f,1f,1f, 1f);
        blit(left, top, this.blitOffset,silver ?30:15, 0, 15, 15,256,256);//other option is 18x18 - 307x307
        GL11.glDisable(GL11.GL_BLEND);
    }

    private void renderFur() {
        if(this.mc.gameSettings.thirdPersonView == 0 && Helper.isWerewolf(this.mc.player) && WerewolfPlayer.getOpt(this.mc.player).map(player -> player.getActionHandler().isActionActive(WerewolfActions.werewolf_form)).orElse(false)){
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
                renderFangs(this.mc.mainWindow.getScaledWidth(), this.mc.mainWindow.getScaledHeight(), entity);
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
