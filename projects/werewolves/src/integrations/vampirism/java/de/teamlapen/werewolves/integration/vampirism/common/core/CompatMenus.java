package de.teamlapen.werewolves.integration.vampirism.common.core;

import de.teamlapen.werewolves.integration.vampirism.common.world.inventory.WolfsbaneDiffuserMenu;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CompatMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, REFERENCE.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<WolfsbaneDiffuserMenu>> WOLFSBANE_DIFFUSER = MENUS.register("garlic_diffuser", () -> create(new WolfsbaneDiffuserMenu.Factory()));

    private static <T extends AbstractContainerMenu> MenuType<T> create(MenuType.MenuSupplier<T> supplier) {
        return new MenuType<>(supplier, FeatureFlags.DEFAULT_FLAGS);
    }

    public static void register(IEventBus bus) {
        MENUS.register(bus);
    }
}
