package de.teamlapen.werewolves.common.world.entities.werewolf;

import de.teamlapen.werewolves.api.entities.werewolf.IWerewolfMob;
import de.teamlapen.werewolves.util.FormHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;

public abstract class WerewolfBaseEntity extends PathfinderMob implements IWerewolfMob {

    private final boolean countAsMonsterForSpawn;

    public WerewolfBaseEntity(EntityType<? extends PathfinderMob> type, Level world, boolean countAsMonsterForSpawn) {
        super(type, world);
        this.countAsMonsterForSpawn = countAsMonsterForSpawn;
    }

    public static boolean spawnPredicateWerewolf(EntityType<? extends WerewolfBaseEntity> entityType, ServerLevelAccessor world, EntitySpawnReason spawnReason, BlockPos blockPos, RandomSource random) {
        if (world.getDifficulty() == net.minecraft.world.Difficulty.PEACEFUL) return false;
        if (spawnReason == EntitySpawnReason.EVENT) return true;
        if (!Monster.isDarkEnoughToSpawn(world, blockPos, random) && !FormHelper.isInWerewolfBiome(world, blockPos)) return false;
        return Mob.checkMobSpawnRules(entityType, world, spawnReason, blockPos, random);
    }

    public void bite(LivingEntity entity) {
        //TODO take a look at ExtendedCreature#onBite
    }

    @Override
    public MobCategory getClassification(boolean forSpawnCount) {
        return forSpawnCount && this.countAsMonsterForSpawn ? MobCategory.MONSTER : super.getClassification(forSpawnCount);
    }

    @Override
    public LivingEntity asEntity() {
        return this;
    }

    public static AttributeSupplier.Builder getAttributeBuilder() {
        return PathfinderMob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.ATTACK_DAMAGE, 5)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.FOLLOW_RANGE);
    }
}
