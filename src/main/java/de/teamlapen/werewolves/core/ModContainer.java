package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.inventory.container.StoneAltarContainer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModContainer {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(Registries.MENU, REFERENCE.MODID);

    @SuppressWarnings("deprecation")
    public static final DeferredHolder<MenuType<?>, MenuType<StoneAltarContainer>> STONE_ALTAR = CONTAINERS.register("stone_altar", () -> new MenuType<>(StoneAltarContainer::new, FeatureFlags.DEFAULT_FLAGS));

    static void register(IEventBus bus) {
        CONTAINERS.register(bus);
    }
}
