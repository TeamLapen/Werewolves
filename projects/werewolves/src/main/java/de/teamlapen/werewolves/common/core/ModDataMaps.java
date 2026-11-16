package de.teamlapen.werewolves.common.core;

import de.teamlapen.werewolves.api.datamaps.IWolfsbaneDiffuserFuel;
import de.teamlapen.werewolves.common.datamaps.WolfsbaneDiffuserFuel;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

import static de.teamlapen.vampirism.api.VampirismDataMaps.Keys.GARLIC_DIFFUSER_FUEL;

public class ModDataMaps {
    public static final DataMapType<Item, IWolfsbaneDiffuserFuel> WOLFSBANE_DIFFUSER_FUEL_MAP = DataMapType.builder(GARLIC_DIFFUSER_FUEL, Registries.ITEM, WolfsbaneDiffuserFuel.CODEC).synced(WolfsbaneDiffuserFuel.NETWORK_CODEC, true).build();

    static void registerDataMaps(RegisterDataMapTypesEvent event) {
        event.register(WOLFSBANE_DIFFUSER_FUEL_MAP);
    }
}
