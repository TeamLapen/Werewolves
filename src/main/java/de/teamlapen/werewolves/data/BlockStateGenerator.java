package de.teamlapen.werewolves.data;

import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.util.REFERENCE;
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
        }};
        blocks.forEach(this::simpleBlock);

        simpleBlock(ModBlocks.wolfsbane, cross("wolfsbane", modLoc("block/wolfsbane")));
        simpleBlock(ModBlocks.potted_wolfsbane, withExistingParent(modId("block/potted_wolfsbane"), "minecraft:block/flower_pot_cross").texture("plant", modId("block/wolfsbane")));
    }

    private String modId(String path) {
        return REFERENCE.MODID + ":" + path;
    }
}
