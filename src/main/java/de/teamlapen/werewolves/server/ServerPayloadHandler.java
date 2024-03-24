package de.teamlapen.werewolves.server;

import de.teamlapen.vampirism.entity.player.actions.ActionHandler;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.core.ModActions;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.network.ServerboundBiteEventPackage;
import de.teamlapen.werewolves.network.ServerboundSimpleInputEventPacket;
import de.teamlapen.werewolves.network.ServerboundWerewolfAppearancePacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Objects;

public class ServerPayloadHandler {

    public static void handleSimpleInputEventPacket(ServerboundSimpleInputEventPacket msg, IPayloadContext context) {
        context.workHandler().execute(() -> {
            context.player().ifPresent(player -> {
                if (msg.type() == ServerboundSimpleInputEventPacket.Type.LEAP) {
                    WerewolfPlayer werewolf = WerewolfPlayer.get(player);
                    werewolf.getActionHandler().toggleAction(ModActions.LEAP.get(), new ActionHandler.ActivationContext());
                }
            });
        });
    }

    public static void handleWerewolfAppearancePacket(ServerboundWerewolfAppearancePacket msg, IPayloadContext context) {
        context.workHandler().execute(() -> {
                context.level().ifPresent(level -> {
                    Entity entity = level.getEntity(msg.entityId());
                    if (entity instanceof Player target) {
                        WerewolfPlayer.get(target).setSkinData(msg.form(), msg.data());
                    }
                });
        });
    }

    public static void handleBiteEventPacket(ServerboundBiteEventPackage msg, IPayloadContext context) {
        context.workHandler().execute(() -> {
            context.player().ifPresent(player -> {
                WerewolfPlayer.get(player).bite(msg.entityId());
            });
        });

    }
}
