package de.teamlapen.werewolves.integration.vampirism.data;

import de.teamlapen.werewolves.data.provider.generator.ModBlockModelGenerators;
import de.teamlapen.werewolves.data.provider.generator.ModItemModelGenerator;
import de.teamlapen.werewolves.integration.vampirism.common.core.CompatBlocks;
import de.teamlapen.werewolves.integration.vampirism.common.core.CompatItems;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.stream.Stream;

public class ModModelProvider extends ModelProvider {

    public ModModelProvider(PackOutput output) {
        super(output, REFERENCE.MODID);
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return CompatBlocks.BLOCKS.getEntries().stream();
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return CompatItems.ITEMS.getEntries().stream();
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        super.registerModels(new CompatBlockModelGenerator(blockModels), new CompatItemModelGenerator(itemModels));
    }
}
