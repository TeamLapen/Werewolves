package de.teamlapen.werewolves.core;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.werewolves.command.*;
import de.teamlapen.werewolves.command.arguments.WerewolfFormArgument;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModCommands {

    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> COMMAND_ARGUMENT_TYPES = DeferredRegister.create(ForgeRegistries.COMMAND_ARGUMENT_TYPES, REFERENCE.MODID);

    public static final RegistryObject<ArgumentTypeInfo<?, ?>> WEREWOLF_FORM = COMMAND_ARGUMENT_TYPES.register("werewolf_form", () -> ArgumentTypeInfos.registerByClass(WerewolfFormArgument.class, new WerewolfFormArgument.Info()));


    static void register(IEventBus bus) {
        COMMAND_ARGUMENT_TYPES.register(bus);
    }

    public static void registerCommands(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
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

}
