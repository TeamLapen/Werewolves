package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.inventory.container.StoneAltarContainer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModContainer {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, REFERENCE.MODID);

    @SuppressWarnings("deprecation")
    public static final RegistryObject<MenuType<StoneAltarContainer>> STONE_ALTAR = CONTAINERS.register("stone_altar", () -> new MenuType<>(StoneAltarContainer::new, FeatureFlags.DEFAULT_FLAGS));

    static void register(IEventBus bus) {
        CONTAINERS.register(bus);
    }
}
