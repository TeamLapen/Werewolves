package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.world.BigTreeFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraftforge.registries.IForgeRegistry;

public class ModFeatures extends de.teamlapen.vampirism.core.ModFeatures {

    public static final TreeFeature jacaranda_tree;
    public static final TreeFeature magic_tree;
    public static final BigTreeFeature magic_tree_big;

    //needed for world gen
    static void registerFeatures(IForgeRegistry<Feature<?>> registry) {
        registry.register(jacaranda_tree.setRegistryName(REFERENCE.MODID, "jacaranda_tree"));
        registry.register(magic_tree.setRegistryName(REFERENCE.MODID, "magic_tree"));
        registry.register(magic_tree_big.setRegistryName(REFERENCE.MODID, "magic_tree_big"));
    }

    static {
        jacaranda_tree = new TreeFeature(NoFeatureConfig::deserialize, true, 4, ModBlocks.jacaranda_log.getDefaultState(), ModBlocks.jacaranda_leaves.getDefaultState(), false);
        magic_tree = new TreeFeature(NoFeatureConfig::deserialize, true, 4, ModBlocks.magic_log.getDefaultState(), ModBlocks.magic_leaves.getDefaultState(), false);
        magic_tree_big = new BigTreeFeature(NoFeatureConfig::deserialize, true, ModBlocks.magic_log.getDefaultState(), ModBlocks.magic_leaves.getDefaultState());
    }
}
