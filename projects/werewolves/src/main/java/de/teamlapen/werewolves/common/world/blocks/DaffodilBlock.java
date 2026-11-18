package de.teamlapen.werewolves.common.world.blocks;

import net.minecraft.world.entity.InsideBlockEffectApplier;

import de.teamlapen.werewolves.util.Helper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class DaffodilBlock extends FlowerBlock {

    public DaffodilBlock(BlockBehaviour.Properties properties) {
        super(MobEffects.POISON, 9, properties);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {
        if (entity instanceof  LivingEntity living && !Helper.isWerewolf(living)) {
            if (!level.isClientSide() && (living.xOld != living.getX() || living.zOld != living.getZ())) {
                living.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 0));
            }
        }
    }
}
