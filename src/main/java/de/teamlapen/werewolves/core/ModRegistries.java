package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.api.items.oil.IOil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class ModRegistries {
    public static final ResourceKey<Registry<IOil>> OIL_ID = ResourceKey.createRegistryKey(new ResourceLocation("werewolves:weapon_oil"));

    static final DeferredRegister<IOil> DEFERRED_OILS = DeferredRegister.create(OIL_ID, OIL_ID.location().getNamespace());

    public static final Supplier<IForgeRegistry<IOil>> OILS = DEFERRED_OILS.makeRegistry(RegistryBuilder::new);

    static void init(IEventBus bus){
        DEFERRED_OILS.register(bus);
    }
}
