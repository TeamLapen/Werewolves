package de.teamlapen.werewolves.data;

import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.world.ModBiomeFeatures;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ExistingFileHelper;

import java.util.HashSet;
import java.util.Set;

public class BlockStateGenerator extends BlockStateProvider {
    public BlockStateGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, REFERENCE.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        Set<Block> blocks = new HashSet<Block>() {{
            add(ModBlocks.silver_ore);
            add(ModBlocks.silver_block);
            add(ModBlocks.magic_planks);
        }};
        blocks.forEach(this::simpleBlock);

        simpleBlock(ModBlocks.wolfsbane, cross("wolfsbane", modLoc("block/wolfsbane")));
        simpleBlock(ModBlocks.potted_wolfsbane, withExistingParent(modId("block/potted_wolfsbane"), "minecraft:block/flower_pot_cross").texture("plant", modId("block/wolfsbane")));
        simpleBlock(ModBlocks.jacaranda_sapling, cross("jacaranda_sapling", modLoc("block/jacaranda_sapling")));
        simpleBlock(ModBlocks.jacaranda_leaves);
        logBlock(ModBlocks.jacaranda_log);
        simpleBlock(ModBlocks.magic_sapling, cross("magic_sapling", modLoc("block/magic_sapling")));
        simpleBlock(ModBlocks.magic_leaves);
        logBlock(ModBlocks.magic_log);
    }

    private String modId(String path) {
        return REFERENCE.MODID + ":" + path;
    }
}
