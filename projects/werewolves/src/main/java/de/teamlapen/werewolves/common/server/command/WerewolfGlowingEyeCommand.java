package de.teamlapen.werewolves.common.server.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import de.teamlapen.faction.common.server.commands.BasicCommand;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.common.server.command.arguments.WerewolfFormArgument;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.player.Player;

public class WerewolfGlowingEyeCommand extends BasicCommand {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("glowingEye")
                .then(Commands.argument("form", WerewolfFormArgument.nonHumanForms())
                        .then(Commands.argument("on", BoolArgumentType.bool())
                                .executes(context -> {
                                    return setGlowingEyes(context.getSource().getPlayerOrException(), BoolArgumentType.getBool(context, "on"), WerewolfFormArgument.getForm(context, "form"));
                                })));
    }

    @SuppressWarnings("SameReturnValue")
    private static int setGlowingEyes(Player playerEntity, boolean on, WerewolfForm form) {
        WerewolfPlayer.get(playerEntity).setGlowingEyes(form, on);
        return 0;
    }
}
