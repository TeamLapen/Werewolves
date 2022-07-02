package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.inventory.container.StoneAltarContainer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModContainer {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, REFERENCE.MODID);

    public static final RegistryObject<MenuType<StoneAltarContainer>> stone_altar_container = CONTAINERS.register("stone_altar_container", () -> new MenuType<>(StoneAltarContainer::new));

    public static void register(IEventBus bus) {
        CONTAINERS.register(bus);
    }
}
