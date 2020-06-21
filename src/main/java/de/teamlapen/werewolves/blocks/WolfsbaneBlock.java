package de.teamlapen.werewolves.blocks;

import de.teamlapen.werewolves.core.ModEffects;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class WolfsbaneBlock extends FlowerBlock {
    public WolfsbaneBlock() {
        super(Effects.BLINDNESS, 5, Properties.create(Material.PLANTS).hardnessAndResistance(0f).doesNotBlockMovement().sound(SoundType.PLANT));
        this.setRegistryName(REFERENCE.MODID,"wolfsbane");
    }

    @Override
    public void onEntityCollision(@Nonnull BlockState state, World worldIn, @Nonnull BlockPos pos, @Nonnull Entity entityIn) {
        if (!worldIn.isRemote && worldIn.getDifficulty() != Difficulty.PEACEFUL) {
            if (entityIn instanceof LivingEntity && Helper.isWerewolf(entityIn)) {
                LivingEntity livingentity = (LivingEntity)entityIn;
                livingentity.addPotionEffect(new EffectInstance(ModEffects.silver, 40));
            }
        }
    }
}
