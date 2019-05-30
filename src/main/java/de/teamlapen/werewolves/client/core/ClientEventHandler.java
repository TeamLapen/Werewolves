package de.teamlapen.werewolves.client.core;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientEventHandler {
    private final Minecraft mc = Minecraft.getMinecraft();
    private int zoomTime = 0;
    private float zoomAmount = 0;

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent evt) {
        if (this.zoomTime > 0) {
            this.mc.gameSettings.fovSetting += this.zoomAmount;
            this.zoomTime--;
        }
    }

    public void onZoomPressed() {
        this.zoomAmount = Minecraft.getMinecraft().gameSettings.fovSetting / 4 / 20;
        this.zoomTime = 20;
        this.mc.gameSettings.fovSetting -= this.mc.gameSettings.fovSetting / 4;
    }

    public boolean isZoomActive() {
        return this.zoomTime > 0;
    }
}
