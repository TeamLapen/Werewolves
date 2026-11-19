package de.teamlapen.werewolves.integration.vampirism.common.world.blockentities;

import de.teamlapen.library.api.world.EnumStrength;
import de.teamlapen.werewolves.common.world.effects.WolfsbaneEffect;
import de.teamlapen.werewolves.integration.vampirism.common.core.CompatBlockEntities;
import de.teamlapen.werewolves.integration.vampirism.common.world.blocks.WolfsbaneDiffuserCoreBlock;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class WolfsbaneDiffuserCoreBlockEntity extends BlockEntity {

    private static final int RADIUS = 3;
    private static final int TICK_INTERVAL = 6;
    private static final int EFFECT_DURATION = 10 * 20;

    public final EnumStrength strength;

    public WolfsbaneDiffuserCoreBlockEntity(BlockPos pos, BlockState blockState) {
        this(pos, blockState, ((WolfsbaneDiffuserCoreBlock) blockState.getBlock()).strength);
    }

    public WolfsbaneDiffuserCoreBlockEntity(BlockPos pos, BlockState blockState, EnumStrength strength) {
        super(CompatBlockEntities.WOLFSBANE_DIFFUSER_CORE.get(), pos, blockState);
        this.strength = strength;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, WolfsbaneDiffuserCoreBlockEntity blockEntity) {
        if (level.isClientSide()) return;
        if (level.getGameTime() % TICK_INTERVAL != 0) return;
        if (blockEntity.strength == EnumStrength.NONE) return;

        for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(RADIUS))) {
            if (entity.isAlive() && !entity.isSpectator() && Helper.isWerewolf(entity)) {
                entity.addEffect(WolfsbaneEffect.createWolfsbaneEffect(entity, EFFECT_DURATION, blockEntity.strength.getStrength() - 1));
            }
        }
    }
}
