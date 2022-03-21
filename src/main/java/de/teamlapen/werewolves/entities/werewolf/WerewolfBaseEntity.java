package de.teamlapen.werewolves.entities.werewolf;

import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.entity.VampirismEntity;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.effects.LupusSanguinemEffect;
import de.teamlapen.werewolves.util.FormHelper;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.Random;

public abstract class WerewolfBaseEntity extends VampirismEntity implements IWerewolfMob {

    public WerewolfBaseEntity(EntityType<? extends VampirismEntity> type, Level world) {
        super(type, world);
    }

    public static boolean spawnPredicateWerewolf(EntityType<? extends WerewolfBaseEntity> entityType, ServerLevelAccessor world, MobSpawnType spawnReason, BlockPos blockPos, Random random) {
        if (world.getDifficulty() == net.minecraft.world.Difficulty.PEACEFUL) return false;
        if (!spawnPredicateCanSpawn(entityType, world, spawnReason, blockPos, random)) return false;
        if (spawnReason == MobSpawnType.EVENT) return true;
        if (world.canSeeSky(blockPos) && Monster.isDarkEnoughToSpawn(world, blockPos, random)) {
            return true;
        }
        return FormHelper.isInWerewolfBiome(world, blockPos) && blockPos.getY() >= world.getSeaLevel();
    }

    public void bite(LivingEntity entity) {
        //TODO take a look at ExtendedCreature#onBite
        LupusSanguinemEffect.addSanguinemEffectRandom(entity, 0.2f);
    }

    @Override
    public MobCategory getClassification(boolean forSpawnCount) {
        return super.getClassification(forSpawnCount);
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
