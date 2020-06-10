package de.teamlapen.werewolves.data;

import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.werewolves.core.WBlocks;
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
            add(WBlocks.silver_ore);
            add(WBlocks.silver_block);
        }};
        blocks.forEach(this::simpleBlock);

        simpleBlock(WBlocks.wolfsbane, cross("wolfsbane", modLoc("block/wolfsbane")));
        simpleBlock(WBlocks.potted_wolfsbane, withExistingParent(modId("block/potted_wolfsbane"), "minecraft:block/flower_pot_cross").texture("plant", modId("block/wolfsbane")));
    }

    private String modId(String path) {
        return REFERENCE.MODID + ":" + path;
    }
}
