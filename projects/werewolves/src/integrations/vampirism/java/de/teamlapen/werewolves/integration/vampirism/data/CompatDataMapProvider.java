package de.teamlapen.werewolves.integration.vampirism.data;

import de.teamlapen.werewolves.common.core.ModItems;
import de.teamlapen.werewolves.integration.vampirism.common.core.CompatDataMaps;
import de.teamlapen.werewolves.integration.vampirism.common.datamaps.WolfsbaneDiffuserFuel;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.concurrent.CompletableFuture;

public class CompatDataMapProvider extends DataMapProvider {

    protected CompatDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        gatherWolfsbaneDiffuserFuel(builder(CompatDataMaps.WOLFSBANE_DIFFUSER_FUEL_MAP));
    }

    private void gatherWolfsbaneDiffuserFuel(Builder<WolfsbaneDiffuserFuel, Item> builder) {
        builder.add(ModItems.WOLFSBANE, new WolfsbaneDiffuserFuel(108000), false);
    }
}
