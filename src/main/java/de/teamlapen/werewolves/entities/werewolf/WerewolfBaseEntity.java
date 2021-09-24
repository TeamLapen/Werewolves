package de.teamlapen.werewolves.entities.werewolf;

import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.entity.VampirismEntity;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.effects.LupusSanguinemEffect;
import de.teamlapen.werewolves.entities.IWerewolfMob;
import de.teamlapen.werewolves.player.WerewolfForm;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public abstract class WerewolfBaseEntity extends VampirismEntity implements IWerewolfMob {

    public WerewolfBaseEntity(EntityType<? extends VampirismEntity> type, World world) {
        super(type, world);
    }

    public static boolean spawnPredicateWerewolf(EntityType<? extends WerewolfBaseEntity> entityType, IServerWorld world, SpawnReason spawnReason, BlockPos blockPos, Random random) {
        if (world.getDifficulty() == net.minecraft.world.Difficulty.PEACEFUL) return false;
        if (!spawnPredicateCanSpawn(entityType, world, spawnReason, blockPos, random)) return false;
        if (random.nextInt(3) != 0) return false;
        if (world.canSeeSkyFromBelowWater(blockPos) && MonsterEntity.isDarkEnoughToSpawn(world, blockPos, random))  {
            return true;
        }
        return Helper.isInWerewolfBiome(world, blockPos) && blockPos.getY() >= world.getSeaLevel();
    }

    public void bite(LivingEntity entity) {
        //TODO take a look at ExtendedCreature#onBite
        if (entity.getRandom().nextInt(10) == 0) {
            LupusSanguinemEffect.addSanguinemEffect(entity);
        }
    }

    @Override
    public EntityClassification getClassification(boolean forSpawnCount) {
        return super.getClassification(forSpawnCount);
    }

    @Override
    public IFaction<?> getFaction() {
        return WReference.WEREWOLF_FACTION;
    }

    @Override
    public LivingEntity getRepresentingEntity() {
        return this;
    }

    @Nonnull
    public abstract WerewolfForm getForm();

    public static AttributeModifierMap.MutableAttribute getAttributeBuilder() {
        return VampirismEntity.getAttributeBuilder()
                .add(Attributes.MAX_HEALTH, WerewolvesConfig.BALANCE.MOBPROPS.werewolf_max_health.get())
                .add(Attributes.ATTACK_DAMAGE, WerewolvesConfig.BALANCE.MOBPROPS.werewolf_attack_damage.get())
                .add(Attributes.MOVEMENT_SPEED, WerewolvesConfig.BALANCE.MOBPROPS.werewolf_speed.get());
    }
}
