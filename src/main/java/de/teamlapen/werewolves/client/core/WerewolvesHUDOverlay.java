package de.teamlapen.werewolves.client.core;

import com.mojang.blaze3d.platform.GlStateManager;
import de.teamlapen.lib.lib.client.gui.ExtendedGui;
import de.teamlapen.vampirism.util.REFERENCE;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
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
    private final ResourceLocation icons = new ResourceLocation(REFERENCE.MODID + ":textures/gui/icons.png");//TODO other icon

    @SubscribeEvent
    public void onRenderCrosshair(RenderGameOverlayEvent.Pre event) {

        if (event.getType() != RenderGameOverlayEvent.ElementType.CROSSHAIRS || mc.player == null || !mc.player.isAlive()) {
            return;
        }

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

    private void renderFangs(int width, int height) {

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

}
