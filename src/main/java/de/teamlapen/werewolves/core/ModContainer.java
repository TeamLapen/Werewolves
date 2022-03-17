package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.inventory.container.StoneAltarContainer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@SuppressWarnings("deprecation")
@ObjectHolder(REFERENCE.MODID)
public class ModContainer {

    public static final MenuType<StoneAltarContainer> stone_altar_container = getNull();

    @SubscribeEvent
    public static void registerContainerTypes(RegistryEvent.Register<MenuType<?>> event) {
        IForgeRegistry<MenuType<?>> registry = event.getRegistry();
        registry.register(new MenuType<>(StoneAltarContainer::new).setRegistryName(REFERENCE.MODID, "stone_altar_container"));
    }
}
