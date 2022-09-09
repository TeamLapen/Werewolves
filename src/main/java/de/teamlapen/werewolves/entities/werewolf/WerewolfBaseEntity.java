package de.teamlapen.werewolves.entities.werewolf;

import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.entity.VampirismEntity;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entities.werewolf.IWerewolfMob;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.effects.LupusSanguinemEffect;
import de.teamlapen.werewolves.util.FormHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nonnull;
import java.util.Random;

public abstract class WerewolfBaseEntity extends VampirismEntity implements IWerewolfMob {

    private final boolean countAsMonsterForSpawn;

    public WerewolfBaseEntity(EntityType<? extends VampirismEntity> type, Level world, boolean countAsMonsterForSpawn) {
        super(type, world);
        this.countAsMonsterForSpawn = countAsMonsterForSpawn;
    }

    public static boolean spawnPredicateWerewolf(EntityType<? extends WerewolfBaseEntity> entityType, ServerLevelAccessor world, MobSpawnType spawnReason, BlockPos blockPos, Random random) {
        if (world.getDifficulty() == net.minecraft.world.Difficulty.PEACEFUL) return false;
        if (spawnReason == MobSpawnType.EVENT) return true;
        if (!Monster.isDarkEnoughToSpawn(world, blockPos, random) && !FormHelper.isInWerewolfBiome(world, blockPos)) return false;
        return Mob.checkMobSpawnRules(entityType, world, spawnReason, blockPos, random);
    }

    public void bite(LivingEntity entity) {
        //TODO take a look at ExtendedCreature#onBite
        LupusSanguinemEffect.addSanguinemEffectRandom(entity, 0.2f);
    }

    @Override
    public MobCategory getClassification(boolean forSpawnCount) {
        return forSpawnCount && this.countAsMonsterForSpawn ? MobCategory.MONSTER : super.getClassification(forSpawnCount);
    }

    @Nonnull
    @Override
    public IFaction<?> getFaction() {
        return WReference.WEREWOLF_FACTION;
    }

    @Override
    public LivingEntity getRepresentingEntity() {
        return this;
    }

    public static AttributeSupplier.Builder getAttributeBuilder() {
        return VampirismEntity.getAttributeBuilder()
                .add(Attributes.MAX_HEALTH, WerewolvesConfig.BALANCE.MOBPROPS.werewolf_max_health.get())
                .add(Attributes.ATTACK_DAMAGE, WerewolvesConfig.BALANCE.MOBPROPS.werewolf_attack_damage.get())
                .add(Attributes.MOVEMENT_SPEED, WerewolvesConfig.BALANCE.MOBPROPS.werewolf_speed.get());
    }
}
