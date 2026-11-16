package de.teamlapen.werewolves.common.server.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import de.teamlapen.faction.common.server.commands.BasicCommand;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.common.server.command.arguments.WerewolfFormArgument;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.player.Player;

public class WerewolfEyeCommand extends BasicCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("eye")
                .then(Commands.argument("form", WerewolfFormArgument.nonHumanForms())
                        .then(Commands.argument("type", IntegerArgumentType.integer(0, REFERENCE.EYE_TYPE_COUNT - 1))
                                .executes(context -> {
                                    return setEye(context, context.getSource().getPlayerOrException(), IntegerArgumentType.getInteger(context, "type"), WerewolfFormArgument.getForm(context, "form"));
                                })));
    }

    @SuppressWarnings("SameReturnValue")
    private static int setEye(CommandContext<CommandSourceStack> context, Player playerEntity, int type, WerewolfForm form) {
        WerewolfPlayer.get(playerEntity).setEyeType(form, type);
        return 0;
    }
}
