package de.teamlapen.werewolves.core;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.werewolves.command.WerewolfTransformCommand;
import net.minecraft.command.CommandSource;

import java.util.List;

public class ModCommands {

    public static void registerCommands(CommandDispatcher<CommandSource> dispatcher) {
        List<String> test = Lists.newArrayList("vampirism-test");
        if (VampirismMod.inDev) {
            test.add("vtest");
        }

        for (String s : test) {
            dispatcher.register(LiteralArgumentBuilder.<CommandSource>literal(s)
                    .then(WerewolfTransformCommand.register()));
        }
    }
}
