package de.teamlapen.werewolves.blocks;

import de.teamlapen.werewolves.util.Helper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;

public class DaffodilBlock extends FlowerBlock {

    public DaffodilBlock() {
        super(MobEffects.POISON, 9, Block.Properties.of(Material.PLANT).noCollission().instabreak().offsetType(BlockBehaviour.OffsetType.XZ).sound(SoundType.GRASS));
    }

    @Override
    public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
        if (entity instanceof  LivingEntity living && !Helper.isWerewolf(living)) {
            if (!level.isClientSide && (living.xOld != living.getX() || living.zOld != living.getZ())) {
                living.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 0));
            }
        }
    }
}
