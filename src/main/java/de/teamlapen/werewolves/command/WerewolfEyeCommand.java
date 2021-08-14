package de.teamlapen.werewolves.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import de.teamlapen.lib.lib.util.BasicCommand;
import de.teamlapen.werewolves.command.arguments.WerewolfFormArgument;
import de.teamlapen.werewolves.player.WerewolfForm;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;

public class WerewolfEyeCommand extends BasicCommand {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("eye")
                .then(Commands.argument("form", WerewolfFormArgument.formArgument())
                        .then(Commands.argument("type", IntegerArgumentType.integer(0, REFERENCE.EYE_TYPE_COUNT-1))
                                .executes(context -> {
                                    return setEye(context, context.getSource().getPlayerOrException(), IntegerArgumentType.getInteger(context, "type"), WerewolfFormArgument.getForm(context, "form"));
                        })));
    }

    private static int setEye(CommandContext<CommandSource> context, PlayerEntity playerEntity, int type, WerewolfForm form) {
        WerewolfPlayer.getOpt(playerEntity).ifPresent(w -> w.setEyeType(form, type));
        return 0;
    }
}
