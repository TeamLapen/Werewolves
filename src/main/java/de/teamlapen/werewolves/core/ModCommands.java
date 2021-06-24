package de.teamlapen.werewolves.core;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.werewolves.command.WerewolfEyeCommand;
import de.teamlapen.werewolves.command.WerewolfGlowingEyeCommand;
import de.teamlapen.werewolves.command.WerewolfSkinCommand;
import de.teamlapen.werewolves.command.WerewolfTransformCommand;
import de.teamlapen.werewolves.command.arguments.WerewolfFormArgument;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.ArgumentSerializer;
import net.minecraft.command.arguments.ArgumentTypes;

import java.util.List;

public class ModCommands {

    public static void registerCommands(CommandDispatcher<CommandSource> dispatcher) {
        List<String> main = Lists.newArrayList("werewolves");
        List<String> test = Lists.newArrayList("vampirism-test");
        if (VampirismMod.inDev) {
            main.add("w");
            test.add("vtest");
        }

        for (String s : main) {
            dispatcher.register(
                    LiteralArgumentBuilder.<CommandSource>literal(s)
                            .then(WerewolfEyeCommand.register())
                            .then(WerewolfSkinCommand.register())
                            .then(WerewolfGlowingEyeCommand.register())
            );
        }

        for (String s : test) {
            dispatcher.register(LiteralArgumentBuilder.<CommandSource>literal(s)
                    .then(WerewolfTransformCommand.register()));
        }
    }

    static void registerArgumentTypesUsages() {
        ArgumentTypes.register("werewolves_form", WerewolfFormArgument.class, new ArgumentSerializer<>(WerewolfFormArgument::new));
    }
}
