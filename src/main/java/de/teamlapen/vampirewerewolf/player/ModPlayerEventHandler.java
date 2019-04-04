package de.teamlapen.vampirewerewolf.player;

import com.google.common.base.Throwables;
import de.teamlapen.vampirewerewolf.VampireWerewolfMod;
import de.teamlapen.vampirewerewolf.player.werewolf.WerewolfPlayer;
import de.teamlapen.vampirewerewolf.util.REFERENCE;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModPlayerEventHandler {

    @SubscribeEvent
    public void onAttachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            try {
                event.addCapability(REFERENCE.WEREWOLF_PLAYER_KEY, WerewolfPlayer.createNewCapability((EntityPlayer) event.getObject()));
            } catch (Exception e) {
                VampireWerewolfMod.log.e("ModPlayerEventHandler", "Failed to attach capabilities to player. Player: %s", event.getObject());
                Throwables.propagate(e);
            }
        }
    }
}
