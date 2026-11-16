package de.teamlapen.werewolves.common.world.entities.player.werewolf;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;

public enum DiggerTier {
    WOOD(ToolMaterial.WOOD),
    STONE(ToolMaterial.STONE),
    IRON(ToolMaterial.IRON),
    DIAMOND(ToolMaterial.DIAMOND);

    private final ToolMaterial material;

    DiggerTier(ToolMaterial material) {
        this.material = material;
    }

    public float attackDamage() {
        return material.attackDamageBonus();
    }

    public TagKey<Block> invalidBlocks() {
        return material.incorrectBlocksForDrops();
    }
}
