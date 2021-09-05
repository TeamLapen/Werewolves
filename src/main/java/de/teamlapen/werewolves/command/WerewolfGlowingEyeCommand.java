package de.teamlapen.werewolves.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import de.teamlapen.lib.lib.util.BasicCommand;
import de.teamlapen.werewolves.command.arguments.WerewolfFormArgument;
import de.teamlapen.werewolves.player.WerewolfForm;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;

public class WerewolfGlowingEyeCommand extends BasicCommand {

    public static ArgumentBuilder<CommandSource,?> register() {
        return Commands.literal("glowingEye")
                .then(Commands.argument("form", WerewolfFormArgument.nonHumanForms())
                        .then(Commands.argument("on", BoolArgumentType.bool())
                                .executes(context -> {
                                    return setGlowingEyes(context.getSource().getPlayerOrException(), BoolArgumentType.getBool(context, "on"), WerewolfFormArgument.getForm(context, "form"));
                                })));
    }

    private static int setGlowingEyes(PlayerEntity playerEntity, boolean on, WerewolfForm form) {
        WerewolfPlayer.getOpt(playerEntity).ifPresent(w -> w.setGlowingEyes(form, on));
        return 0;
    }
}
