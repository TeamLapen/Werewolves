package de.teamlapen.werewolves.entities.werewolf;

import de.teamlapen.vampirism.api.entity.EntityClassType;
import de.teamlapen.vampirism.api.entity.actions.EntityActionTier;
import de.teamlapen.vampirism.entity.goals.LookAtClosestVisibleGoal;
import de.teamlapen.vampirism.entity.hunter.HunterBaseEntity;
import de.teamlapen.vampirism.entity.vampire.VampireBaseEntity;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModEntities;
import de.teamlapen.werewolves.entities.WerewolfFormUtil;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.UUID;

public class HumanWerewolfEntity extends CreatureEntity implements WerewolfTransformable {
    private static final DataParameter<Integer> FORM = EntityDataManager.createKey(HumanWerewolfEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(HumanWerewolfEntity.class, DataSerializers.VARINT);

    private final EntityClassType classType;
    private final EntityActionTier actionTier;

    protected int rage;

    public HumanWerewolfEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);
        this.classType = EntityClassType.getRandomClass(this.getRNG());
        this.actionTier = EntityActionTier.Medium;
    }

    public static boolean spawnPredicateHumanWerewolf(EntityType<? extends CreatureEntity> entityType, IWorld world, SpawnReason spawnReason, BlockPos blockPos, Random random) {
        return world.getDifficulty() != net.minecraft.world.Difficulty.PEACEFUL && MobEntity.canSpawnOn(entityType, world, spawnReason, blockPos, random);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.getDataManager().register(FORM, -1);
        this.getDataManager().register(TYPE, -1);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.2));

        this.goalSelector.addGoal(9, new RandomWalkingGoal(this, 0.7));
        this.goalSelector.addGoal(10, new LookAtClosestVisibleGoal(this, PlayerEntity.class, 20F, 0.6F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, HunterBaseEntity.class, 17F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, VampireBaseEntity.class, 17F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource source, float amount) {
        if (super.attackEntityFrom(source, amount)) {
            this.rage += amount * 10;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (this.rage > 50) {
            WerewolfTransformable werewolf = this.transformToWerewolf();
            ((MobEntity) werewolf).setRevengeTarget(this.getAttackTarget());
        }
    }

    @Override
    public void reset() {
        this.rage = 0;
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
            this.getDataManager().set(FORM, t < 2 && t >= 0 ? t : -1);
        }
        if (compound.contains("type")) {
            int t = compound.getInt("type");
            this.getDataManager().set(TYPE, t < 126 && t >= 0 ? t : -1);
        }
    }

    @Override
    public void writeAdditional(@Nonnull CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("form", this.getDataManager().get(FORM));
        compound.putInt("type", this.getDataManager().get(TYPE));
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        if (this.getDataManager().get(FORM) == -1) {
            this.getDataManager().set(FORM, this.getRNG().nextInt(2));
        }
        if (this.getDataManager().get(TYPE) == -1) {
            this.getDataManager().set(TYPE, this.getRNG().nextInt(126));
        }
    }

    public int getEntityTextureType() {
        int i = this.getDataManager().get(TYPE);
        return Math.max(i, 0);
    }

    @Override
    public BasicWerewolfEntity transformToWerewolf2() {
        BasicWerewolfEntity werewolf;
        if (this.getDataManager().get(FORM) == 0) {
            werewolf = ModEntities.werewolf_beast.create(this.world);
        } else {
            werewolf = ModEntities.werewolf_survivalist.create(this.world);
        }
        werewolf.setSourceEntity(this);
        werewolf.copyLocationAndAnglesFrom(this);
        werewolf.copyDataFromOld(this);
        werewolf.setUniqueId(UUID.randomUUID());
        this.world.addEntity(werewolf);
        this.remove(true);
        werewolf.setHealth(this.getHealth() / this.getMaxHealth() * werewolf.getMaxHealth());
        return werewolf;
    }

    @Override
    public EntityActionTier getEntityTier() {
        return this.actionTier;
    }

    @Override
    public EntityClassType getEntityClass() {
        return this.classType;
    }

    @Override
    public WerewolfTransformable transformBack2() {
        return this;
    }

    @Override
    public boolean canTransform() {
        return true;
    }

    @Nonnull
    @Override
    public WerewolfFormUtil.Form getWerewolfForm() {
        switch (this.getDataManager().get(FORM)) {
            case 0:
                return WerewolfFormUtil.Form.BEAST;
            case 1:
                return WerewolfFormUtil.Form.SURVIVALIST;
            default:
                throw new IllegalStateException("Werewolf form is not set");
        }
    }
}
