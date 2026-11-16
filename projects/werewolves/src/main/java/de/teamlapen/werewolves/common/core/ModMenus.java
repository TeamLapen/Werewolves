package de.teamlapen.werewolves.common.core;

import de.teamlapen.vampirism.common.world.inventory.diffuser.GarlicDiffuserMenu;
import de.teamlapen.werewolves.common.world.inventory.diffuser.WolfsbaneDiffuserMenu;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, REFERENCE.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<WolfsbaneDiffuserMenu>> WOLFSBANE_DIFFUSER = MENUS.register("garlic_diffuser", () -> create(new WolfsbaneDiffuserMenu.Factory()));

    private static <T extends AbstractContainerMenu> MenuType<T> create(MenuType.MenuSupplier<T> supplier) {
        return new MenuType<>(supplier, FeatureFlags.DEFAULT_FLAGS);
    }

    static void register(IEventBus bus) {
        MENUS.register(bus);
    }

}
