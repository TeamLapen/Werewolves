package de.teamlapen.werewolves.client.core;

import de.teamlapen.lib.lib.client.gui.ExtendedGui;
import de.teamlapen.werewolves.config.Balance;
import de.teamlapen.werewolves.core.ModPotions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class WerewolvesHUDOverlay extends ExtendedGui {
    private final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        ScaledResolution scaledresolution = new ScaledResolution(mc);
        int i = scaledresolution.getScaledWidth();
        int j = scaledresolution.getScaledHeight();
        GlStateManager.clear(GL11.GL_DEPTH_BUFFER_BIT);
        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth_double(), scaledresolution.getScaledHeight_double(), 0.0D, 1D, -1D);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.loadIdentity();
        GlStateManager.pushMatrix();
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        if (mc.player.isPotionActive(ModPotions.drowsy)) {
            if (mc.player.getActivePotionEffect(ModPotions.drowsy).getDuration() > 0) {
                float s = mc.player.getActivePotionEffect(ModPotions.drowsy).getDuration() / (Balance.ge.DROWSYTIME * 1200);
                float a = (float) (1 - s * 0.7D > 0.7D ? 0.7D : 1 - s * 0.7D);
                //WerewolvesMod.log.t("duration: %s, balance: %s, s: %s, a: %s", mc.player.getActivePotionEffect(ModPotions.drowsy).getDuration(),(float) Balance.ge.DROWSYTIME * 1200, s, a);

                GlStateManager.disableTexture2D();
                GlStateManager.enableBlend();
                GlStateManager.disableAlpha();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager.shadeModel(7425);
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder worldrenderer = tessellator.getBuffer();
                worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                worldrenderer.pos(0, j, zLevel).color(0, 0, 0, a).endVertex();
                worldrenderer.pos(i, j, zLevel).color(0, 0, 0, a).endVertex();
                worldrenderer.pos(i, 0, zLevel).color(0, 0, 0, a).endVertex();
                worldrenderer.pos(0, 0, zLevel).color(0, 0, 0, a).endVertex();

                tessellator.draw();
                GlStateManager.shadeModel(7424);
                GlStateManager.disableBlend();
                GlStateManager.enableAlpha();
                GlStateManager.enableTexture2D();
            }
        }
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GlStateManager.popMatrix();
    }
}
