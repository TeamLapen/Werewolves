package de.teamlapen.werewolves.blocks;

import de.teamlapen.werewolves.effects.SilverEffect;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nonnull;

public class WolfsbaneBlock extends FlowerBlock {
    public WolfsbaneBlock() {
        super(MobEffects.BLINDNESS, 5, Properties.of(Material.PLANT).strength(0f).noCollission().sound(SoundType.GRASS));
    }

    @Override
    public void entityInside(@Nonnull BlockState state, Level worldIn, @Nonnull BlockPos pos, @Nonnull Entity entityIn) {
        if (!worldIn.isClientSide && worldIn.getDifficulty() != Difficulty.PEACEFUL) {
            if (entityIn instanceof LivingEntity livingentity && Helper.isWerewolf(entityIn)) {
                livingentity.addEffect(SilverEffect.createEffect(livingentity, 40));
            }
        }
    }
}
