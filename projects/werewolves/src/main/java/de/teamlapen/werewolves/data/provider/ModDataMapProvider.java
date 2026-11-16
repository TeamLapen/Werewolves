package de.teamlapen.werewolves.data.provider;

import de.teamlapen.werewolves.api.datamaps.IWolfsbaneDiffuserFuel;
import de.teamlapen.werewolves.common.core.ModDataMaps;
import de.teamlapen.werewolves.common.core.ModItems;
import de.teamlapen.werewolves.common.datamaps.WolfsbaneDiffuserFuel;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {

    protected ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        gatherWolfsbaneDiffuserFuel(builder(ModDataMaps.WOLFSBANE_DIFFUSER_FUEL_MAP));
    }

    private void gatherWolfsbaneDiffuserFuel(Builder<IWolfsbaneDiffuserFuel, Item> builder) {
        builder.add(ModItems.WOLFSBANE, new WolfsbaneDiffuserFuel(108000), false);
    }
}
