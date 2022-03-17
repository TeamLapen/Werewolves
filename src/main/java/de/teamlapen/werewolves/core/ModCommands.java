package de.teamlapen.werewolves.core;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.werewolves.command.*;
import de.teamlapen.werewolves.command.arguments.WerewolfFormArgument;
import de.teamlapen.werewolves.command.arguments.serializer.WerewolfFormArgumentSerializer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.synchronization.ArgumentTypes;

import java.util.List;

public class ModCommands {

    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        List<String> main = Lists.newArrayList("werewolves");
        List<String> test = Lists.newArrayList("vampirism-test");
        if (VampirismMod.inDev) {
            main.add("w");
            test.add("vtest");
        }

        for (String s : main) {
            dispatcher.register(
                    LiteralArgumentBuilder.<CommandSourceStack>literal(s)
                            .then(WerewolfEyeCommand.register())
                            .then(WerewolfSkinCommand.register())
                            .then(WerewolfGlowingEyeCommand.register())
            );
        }

        for (String s : test) {
            dispatcher.register(LiteralArgumentBuilder.<CommandSourceStack>literal(s)
                    .then(MinionCommand.register())
                    .then(WerewolfTransformCommand.register()));
        }
    }

    static void registerArgumentTypesUsages() {
        ArgumentTypes.register("werewolves_form", WerewolfFormArgument.class, new WerewolfFormArgumentSerializer());
    }
}
