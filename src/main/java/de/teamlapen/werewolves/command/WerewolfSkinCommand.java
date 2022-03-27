package de.teamlapen.werewolves.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import de.teamlapen.lib.lib.util.BasicCommand;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.player.Player;

public class WerewolfSkinCommand extends BasicCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("skin")
                .then(Commands.literal("beast")
                        .then(Commands.argument("type", IntegerArgumentType.integer(0, WerewolfForm.BEAST.getSkinTypes() - 1))
                                .executes(context -> {
                                    return setSkin(context, context.getSource().getPlayerOrException(), IntegerArgumentType.getInteger(context, "type"), WerewolfForm.BEAST);
                                })))
                .then(Commands.literal("survival")
                        .then(Commands.argument("type", IntegerArgumentType.integer(0, WerewolfForm.SURVIVALIST.getSkinTypes() - 1))
                                .executes(context -> {
                                    return setSkin(context, context.getSource().getPlayerOrException(), IntegerArgumentType.getInteger(context, "type"), WerewolfForm.SURVIVALIST);
                                })))
                .then(Commands.literal("human")
                        .then(Commands.argument("type", IntegerArgumentType.integer(0, WerewolfForm.HUMAN.getSkinTypes() - 1))
                                .executes(context -> {
                                    return setSkin(context, context.getSource().getPlayerOrException(), IntegerArgumentType.getInteger(context, "type"), WerewolfForm.HUMAN);
                                })));
    }

    @SuppressWarnings("SameReturnValue")
    private static int setSkin(CommandContext<CommandSourceStack> context, Player playerEntity, int type, WerewolfForm form) {
        WerewolfPlayer.getOpt(playerEntity).ifPresent(w -> w.setSkinType(form, type));
        return 0;
    }
}
