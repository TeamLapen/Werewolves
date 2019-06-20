package de.teamlapen.werewolves.client.core;

import de.teamlapen.vampirism.client.core.ModKeys;
import de.teamlapen.vampirism.client.core.ModKeys.KEY;
import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.network.InputEventPacket;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModKeysWerewolves {

    private static KeyBinding SUCK;
    private static boolean suck_down;

    public ModKeysWerewolves() {
        SUCK = ModKeys.getKeyBinding(KEY.SUCK);
    }

    @SubscribeEvent
    public void handleInputEvent(InputEvent event) {
        if (!SUCK.isKeyDown()) {
            suck_down = true;
        }
        if (SUCK.isKeyDown() && suck_down && !((ClientProxy) WerewolvesMod.proxy).getClientEventHandler().isZoomActive()) {
            suck_down = false;
            RayTraceResult mouseOver = Minecraft.getMinecraft().objectMouseOver;
            EntityPlayer player = Minecraft.getMinecraft().player;
            if (mouseOver != null && !player.isSpectator() && FactionPlayerHandler.get(player).isInFaction(WReference.WEREWOLF_FACTION) && WerewolfPlayer.get(player).getSpecialAttributes().werewolf > 1) {
                if (mouseOver.entityHit != null) {
                    WerewolvesMod.dispatcher.sendToServer(new InputEventPacket(InputEventPacket.BITE, "" + mouseOver.entityHit.getEntityId()));
                    ((ClientProxy) WerewolvesMod.proxy).getClientEventHandler().onZoomPressed();
                }
            }
        }
    }
}
