package de.teamlapen.werewolves.blocks;

import net.minecraft.block.FlowerBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.potion.Effect;

public class WFlowerBlock extends FlowerBlock {

    public WFlowerBlock(Effect effect, int effectDuration) {
        super(effect, effectDuration, Properties.create(Material.PLANTS).hardnessAndResistance(0f).doesNotBlockMovement().sound(SoundType.PLANT));
    }
}
