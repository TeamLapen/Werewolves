package de.teamlapen.werewolves.common.core;

import com.mojang.serialization.Codec;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, REFERENCE.MODID);


    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> MINION_EYES = DATA_COMPONENTS.registerComponentType("werewolf_eyes", builder -> builder.networkSynchronized(ByteBufCodecs.VAR_INT).persistent(Codec.INT));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<WerewolfForm>> WEREWOLF_FORM = DATA_COMPONENTS.registerComponentType("werewolf_type", builder -> builder.networkSynchronized(WerewolfForm.STREAM_CODEC).persistent(WerewolfForm.CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> GLOWING_EYES = DATA_COMPONENTS.registerComponentType("glowing_eyes", builder -> builder.networkSynchronized(ByteBufCodecs.BOOL).persistent(Codec.BOOL));

    static void register(IEventBus eventBus) {
        DATA_COMPONENTS.register(eventBus);
    }

}
