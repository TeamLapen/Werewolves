package de.teamlapen.werewolves.client.render;

import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderHandler {
    private final Minecraft mc;

    public RenderHandler(Minecraft mc) {
        this.mc = mc;
    }

    @SubscribeEvent
    public void onHandRendered(RenderSpecificHandEvent event) {
        EntityPlayer player = Minecraft.getMinecraft().player;
        if (Helper.isWerewolf(player)) {
            WerewolfPlayer werewolf = WerewolfPlayer.get(player);
            if (werewolf.getSpecialAttributes().werewolf > 0 && player.getHeldItemMainhand().isEmpty()) {
                event.setCanceled(true);
            }
        }
    }
}
