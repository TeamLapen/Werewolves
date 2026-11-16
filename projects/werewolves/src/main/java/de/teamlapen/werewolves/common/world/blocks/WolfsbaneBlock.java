package de.teamlapen.werewolves.common.world.blocks;

import de.teamlapen.werewolves.common.world.effects.WolfsbaneEffect;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class WolfsbaneBlock extends FlowerBlock {
    public WolfsbaneBlock(BlockBehaviour.Properties properties) {
        super(MobEffects.BLINDNESS, 5, properties);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {
        if (!level.isClientSide() && level.getDifficulty() != Difficulty.PEACEFUL) {
            if (entity instanceof LivingEntity livingentity && Helper.isWerewolf(entity)) {
                livingentity.addEffect(WolfsbaneEffect.createWolfsbaneEffect(livingentity, 45, 1));
            }
        }
    }
}
