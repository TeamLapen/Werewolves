package de.teamlapen.werewolves.client.core;

import de.teamlapen.vampirism.client.gui.GuiSleepCoffin;
import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.werewolves.client.gui.GuiSleepForced;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSleepMP;
import net.minecraft.init.Blocks;
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
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (this.zoomTime > 0) {
            this.mc.gameSettings.fovSetting += this.zoomAmount;
            this.zoomTime--;
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (this.mc.world != null) {
                if ((this.mc.currentScreen == null || this.mc.currentScreen instanceof GuiSleepMP || this.mc.currentScreen instanceof GuiSleepCoffin) && this.mc.player.isPlayerSleeping()) {
                    IBlockState state = this.mc.player.getEntityWorld().getBlockState(this.mc.player.bedLocation);
                    if (!state.getBlock().equals(ModBlocks.block_coffin) && !state.getBlock().equals(Blocks.BED)) {
                        this.mc.displayGuiScreen(new GuiSleepForced());
                    }
                } else if (this.mc.currentScreen instanceof GuiSleepForced && !this.mc.player.isPlayerSleeping()) {
                    this.mc.displayGuiScreen(null);
                }
            }
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
