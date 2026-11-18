package de.teamlapen.werewolves.integration.vampirism.common.core;

import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.integration.vampirism.common.datamaps.WolfsbaneDiffuserFuel;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

public class CompatDataMaps {

    public static final DataMapType<Item, WolfsbaneDiffuserFuel> WOLFSBANE_DIFFUSER_FUEL_MAP = DataMapType.builder(WIdentifier.mod("wolfsbane_diffuser_fuel"), Registries.ITEM, WolfsbaneDiffuserFuel.CODEC).synced(WolfsbaneDiffuserFuel.NETWORK_CODEC, true).build();

    public static void registerDataMaps(RegisterDataMapTypesEvent event) {
        event.register(WOLFSBANE_DIFFUSER_FUEL_MAP);
    }
}
