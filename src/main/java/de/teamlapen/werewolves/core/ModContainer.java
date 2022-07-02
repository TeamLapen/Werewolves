package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.inventory.container.StoneAltarContainer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("deprecation")
public class ModContainer {

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, REFERENCE.MODID);

    public static final RegistryObject<ContainerType<StoneAltarContainer>> stone_altar_container = CONTAINERS.register("stone_altar_container", () -> new ContainerType<>(StoneAltarContainer::new));

    static void registerContainers(IEventBus bus) {
        CONTAINERS.register(bus);
    }
}
