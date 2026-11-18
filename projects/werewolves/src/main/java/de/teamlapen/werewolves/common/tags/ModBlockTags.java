package de.teamlapen.werewolves.common.tags;

import de.teamlapen.werewolves.api.WIdentifier;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {
    public static final TagKey<Block> SILVER_ORE = common("ores/silver");
    public static final TagKey<Block> STORAGE_BLOCKS_SILVER = common("storage_blocks/silver");
    public static final TagKey<Block> STORAGE_BLOCKS_RAW_SILVER = common("storage_blocks/raw_silver");
    public static final TagKey<Block> MAGIC_LOG = common("magic_log");
    public static final TagKey<Block> JACARANDA_LOG = common("jacaranda_log");
    public static final TagKey<Block> NEEDS_SILVER_TOOL = common("needs_silver_tool");
    public static final TagKey<Block> INCORRECT_FOR_SILVER_TOOL = common("incorrect_for_silver_tool");


    private static TagKey<Block> mc(Identifier id) {
        return BlockTags.create(id);
    }

    private static TagKey<Block> werewolves(String id) {
        return BlockTags.create(WIdentifier.mod(id));
    }

    private static TagKey<Block> common(String id) {
        return BlockTags.create(WIdentifier.common(id));
    }
}
