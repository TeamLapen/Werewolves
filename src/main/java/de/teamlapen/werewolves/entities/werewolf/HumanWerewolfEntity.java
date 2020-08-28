package de.teamlapen.werewolves.entities.werewolf;

import de.teamlapen.vampirism.entity.goals.LookAtClosestVisibleGoal;
import de.teamlapen.vampirism.entity.hunter.HunterBaseEntity;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class HumanWerewolfEntity extends CreatureEntity {
    private static final DataParameter<Integer> FORM = EntityDataManager.createKey(HumanWerewolfEntity.class, DataSerializers.VARINT);

    public HumanWerewolfEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public static boolean spawnPredicateHumanWerewolf(EntityType<? extends CreatureEntity> entityType, IWorld world, SpawnReason spawnReason, BlockPos blockPos, Random random) {
        return world.getDifficulty() != net.minecraft.world.Difficulty.PEACEFUL && MobEntity.canSpawnOn(entityType, world, spawnReason, blockPos, random);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.getDataManager().register(FORM, -1);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new OpenDoorGoal(this, true));

        this.goalSelector.addGoal(9, new RandomWalkingGoal(this, 0.7));
        this.goalSelector.addGoal(10, new LookAtClosestVisibleGoal(this, PlayerEntity.class, 20F, 0.6F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, HunterBaseEntity.class, 17F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.updateEntityAttributes();
    }

    protected void updateEntityAttributes() {
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(WerewolvesConfig.BALANCE.MOBPROPS.human_werewolf_max_health.get());
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(WerewolvesConfig.BALANCE.MOBPROPS.human_werewolf_attack_damage.get());
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(WerewolvesConfig.BALANCE.MOBPROPS.human_werewolf_speed.get());
    }

    @Override
    public void readAdditional(@Nonnull CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("form")) {
            int t = compound.getInt("form");
            this.getDataManager().set(FORM, t < 126 && t >= 0 ? t : -1);
        }
    }

    @Override
    public void writeAdditional(@Nonnull CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("form", this.getDataManager().get(FORM));
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        if (this.getDataManager().get(FORM) == -1) {
            this.getDataManager().set(FORM, this.getRNG().nextInt(2));
        }
    }
}
