package de.teamlapen.werewolves.common.server;

import de.teamlapen.faction.common.factions.actions.ActionHandler;
import de.teamlapen.werewolves.common.core.ModActions;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.common.network.ServerboundBiteEventPackage;
import de.teamlapen.werewolves.common.network.ServerboundSimpleInputEventPacket;
import de.teamlapen.werewolves.common.network.ServerboundWerewolfAppearancePacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerPayloadHandler {

    public static void handleSimpleInputEventPacket(ServerboundSimpleInputEventPacket msg, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (msg.action() == ServerboundSimpleInputEventPacket.Action.LEAP) {
                WerewolfPlayer werewolf = WerewolfPlayer.get(context.player());
                werewolf.getActionHandler().toggleAction(ModActions.LEAP, new ActionHandler.ActivationContext());
            }
        });
    }

    public static void handleWerewolfAppearancePacket(ServerboundWerewolfAppearancePacket msg, IPayloadContext context) {
        context.enqueueWork(() -> {
            Entity entity = context.player().level().getEntity(msg.entityId());
            if (entity instanceof Player target) {
                WerewolfPlayer.get(target).setSkinData(msg.form(), msg.data());
            }
        });
    }

    public static void handleBiteEventPacket(ServerboundBiteEventPackage msg, IPayloadContext context) {
        context.enqueueWork(() -> {
            WerewolfPlayer.get(context.player()).bite(msg.entityId());
        });
    }
}
