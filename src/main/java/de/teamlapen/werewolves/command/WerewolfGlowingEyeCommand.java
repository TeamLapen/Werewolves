package de.teamlapen.werewolves.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import de.teamlapen.lib.lib.util.BasicCommand;
import de.teamlapen.werewolves.player.WerewolfForm;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;

public class WerewolfGlowingEyeCommand extends BasicCommand {

    public static ArgumentBuilder<CommandSource,?> register() {
        return Commands.literal("glowingEye")
                .then(Commands.literal("beast")
                        .then(Commands.argument("on", BoolArgumentType.bool())
                                .executes(context -> {
                                    return setGlowingEyes(context, context.getSource().asPlayer(), BoolArgumentType.getBool(context, "on"), WerewolfForm.BEAST);
                                })))
                .then(Commands.literal("survival")
                        .then(Commands.argument("on",  BoolArgumentType.bool())
                                .executes(context -> {
                                    return setGlowingEyes(context, context.getSource().asPlayer(), BoolArgumentType.getBool(context, "on"), WerewolfForm.SURVIVALIST);
                                })));
    }

    private static int setGlowingEyes(CommandContext<CommandSource> context, PlayerEntity playerEntity, boolean on, WerewolfForm form) {
        WerewolfPlayer.getOpt(playerEntity).ifPresent(w -> w.setGlowingEyes(form, on));
        return 0;
    }
}
