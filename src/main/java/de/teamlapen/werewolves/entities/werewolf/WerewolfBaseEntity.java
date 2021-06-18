package de.teamlapen.werewolves.entities.werewolf;

import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.entity.VampirismEntity;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.effects.LupusSanguinemEffect;
import de.teamlapen.werewolves.entities.IWerewolfMob;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

public abstract class WerewolfBaseEntity extends VampirismEntity implements IWerewolfMob {

    public WerewolfBaseEntity(EntityType<? extends VampirismEntity> type, World world) {
        super(type, world);
    }

    public static boolean spawnPredicateWerewolf(EntityType<? extends WerewolfBaseEntity> entityType, IServerWorld world, SpawnReason spawnReason, BlockPos blockPos, Random random) {
        return world.getDifficulty() != net.minecraft.world.Difficulty.PEACEFUL && world.canBlockSeeSky(blockPos) && MonsterEntity.isValidLightLevel(world, blockPos, random) && spawnPredicateCanSpawn(entityType, world, spawnReason, blockPos, random) && random.nextInt(3) == 0;
    }

    public void bite(LivingEntity entity) {
        //TODO take a look at ExtendedCreature#onBite
        LupusSanguinemEffect.addSanguinemEffect(entity);
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

    public static AttributeModifierMap.MutableAttribute getAttributeBuilder() {
        return VampirismEntity.getAttributeBuilder()
                .createMutableAttribute(Attributes.MAX_HEALTH, WerewolvesConfig.BALANCE.MOBPROPS.werewolf_max_health.get())
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, WerewolvesConfig.BALANCE.MOBPROPS.werewolf_attack_damage.get())
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, WerewolvesConfig.BALANCE.MOBPROPS.werewolf_speed.get());
    }
}
