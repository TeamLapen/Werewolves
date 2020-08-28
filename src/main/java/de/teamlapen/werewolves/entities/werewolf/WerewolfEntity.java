package de.teamlapen.werewolves.entities.werewolf;

import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.difficulty.Difficulty;
import de.teamlapen.vampirism.api.entity.EntityClassType;
import de.teamlapen.vampirism.api.entity.IVillageCaptureEntity;
import de.teamlapen.vampirism.api.entity.actions.EntityActionTier;
import de.teamlapen.vampirism.api.entity.actions.IActionHandlerEntity;
import de.teamlapen.vampirism.api.entity.actions.IEntityActionUser;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.world.ICaptureAttributes;
import de.teamlapen.vampirism.entity.VampirismEntity;
import de.teamlapen.vampirism.entity.action.ActionHandlerEntity;
import de.teamlapen.vampirism.entity.goals.AttackVillageGoal;
import de.teamlapen.vampirism.entity.goals.DefendVillageGoal;
import de.teamlapen.vampirism.entity.goals.LookAtClosestVisibleGoal;
import de.teamlapen.vampirism.entity.hunter.HunterBaseEntity;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.entities.IWerewolfMob;
import de.teamlapen.werewolves.entities.WerewolfFormUtil;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.PatrollerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.Structures;

import javax.annotation.Nullable;
import java.util.Random;

public abstract class WerewolfEntity extends VampirismEntity implements IWerewolfMob, IVillageCaptureEntity, IEntityActionUser {
    private static final DataParameter<Integer> LEVEL = EntityDataManager.createKey(VampirismEntity.class, DataSerializers.VARINT);
    private static final int MAX_LEVEL = 2;

    private final ActionHandlerEntity<?> entityActionHandler;
    private final EntityClassType entityClass;
    private final EntityActionTier entityTier;

    @Nullable
    private ICaptureAttributes villageAttributes;
    private boolean attack;

    public WerewolfEntity(EntityType<? extends VampirismEntity> type, World world, EntityClassType entityClass, EntityActionTier entityTier) {
        super(type, world);
        this.entityClass = entityClass;
        this.entityTier = entityTier;
        this.entityActionHandler = new ActionHandlerEntity<>(this);
    }

    public static boolean spawnPredicateWerewolf(EntityType<? extends WerewolfEntity> entityType, IWorld world, SpawnReason spawnReason, BlockPos blockPos, Random random) {
        return world.getDifficulty() != net.minecraft.world.Difficulty.PEACEFUL && spawnPredicateCanSpawn(entityType, world, spawnReason, blockPos, random);
    }

    @Override
    public EntityClassification getClassification(boolean forSpawnCount) {
        if (forSpawnCount) {
            return EntityClassification.MONSTER;
        }
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

    @Override
    protected void registerData() {
        super.registerData();
        this.getDataManager().register(LEVEL, -1);
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (this.entityActionHandler != null) {
            this.entityActionHandler.handle();
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new BreakDoorGoal(this, (difficulty) -> difficulty == net.minecraft.world.Difficulty.HARD));//Only break doors on hard difficulty
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(9, new RandomWalkingGoal(this, 0.7));
        this.goalSelector.addGoal(10, new LookAtClosestVisibleGoal(this, PlayerEntity.class, 20F, 0.6F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, HunterBaseEntity.class, 17F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));


        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new AttackVillageGoal<>(this));
        this.targetSelector.addGoal(4, new DefendVillageGoal<>(this));//Should automatically be mutually exclusive with  attack village
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 5, true, false, VampirismAPI.factionRegistry().getPredicate(getFaction(), true, false, true, false, null)));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, CreatureEntity.class, 5, true, false, VampirismAPI.factionRegistry().getPredicate(getFaction(), false, true, false, false, null)));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, PatrollerEntity.class, 5, true, true, (living) -> Structures.VILLAGE.isPositionInStructure(living.world, living.getPosition())));
    }

    @Override
    public void attackVillage(ICaptureAttributes iCaptureAttributes) {
        this.villageAttributes = iCaptureAttributes;
        this.attack = true;
    }

    @Override
    public void defendVillage(ICaptureAttributes iCaptureAttributes) {
        this.villageAttributes = iCaptureAttributes;
        this.attack = false;
    }

    @Nullable
    @Override
    public AxisAlignedBB getTargetVillageArea() {
        return this.villageAttributes == null ? null : this.villageAttributes.getVillageArea();
    }

    @Override
    public boolean isAttackingVillage() {
        return this.villageAttributes != null && attack;
    }

    @Override
    public boolean isDefendingVillage() {
        return this.villageAttributes != null && !attack;
    }

    @Override
    public void stopVillageAttackDefense() {
        this.villageAttributes = null;
    }

    @Nullable
    @Override
    public ICaptureAttributes getCaptureInfo() {
        return this.villageAttributes;
    }

    @Override
    public IActionHandlerEntity getActionHandler() {
        return this.entityActionHandler;
    }

    @Override
    public int getLevel() {
        return getDataManager().get(LEVEL);
    }

    @Override
    public void setLevel(int level) {
        if (level >= 0) {
            getDataManager().set(LEVEL, level);
            this.updateEntityAttributes();
            if (level == 2) {
                this.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 1000000, 1));
            }
            if (level == 1) {
                this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_SWORD));
            } else {
                this.setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
            }

        }
    }

    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    @Override
    public int suggestLevel(Difficulty difficulty) {
        switch (this.rand.nextInt(5)) {
            case 0:
                return (int) (difficulty.minPercLevel / 100F * MAX_LEVEL);
            case 1:
                return (int) (difficulty.avgPercLevel / 100F * MAX_LEVEL);
            case 2:
                return (int) (difficulty.maxPercLevel / 100F * MAX_LEVEL);
            default:
                return this.rand.nextInt(MAX_LEVEL + 1);
        }
    }

    protected void updateEntityAttributes() {
        int l = Math.max(getLevel(), 0);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(WerewolvesConfig.BALANCE.MOBPROPS.werewolf_max_health.get() + WerewolvesConfig.BALANCE.MOBPROPS.werewolf_max_health_pl.get() * l);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(WerewolvesConfig.BALANCE.MOBPROPS.werewolf_attack_damage.get() + WerewolvesConfig.BALANCE.MOBPROPS.werewolf_attack_damage_pl.get() * l);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(WerewolvesConfig.BALANCE.MOBPROPS.werewolf_speed.get());
    }

    @Override
    public EntityActionTier getEntityTier() {
        return entityTier;
    }

    @Override
    public EntityClassType getEntityClass() {
        return entityClass;
    }

    public abstract WerewolfFormUtil.Form getForm();

    @Override
    public void readAdditional(CompoundNBT nbt) {
        super.readAdditional(nbt);
        if (nbt.contains("level")) {
            this.setLevel(nbt.getInt("level"));
        }
        if (nbt.contains("attack")) {
            this.attack = nbt.getBoolean("attack");
        }
        if (this.entityActionHandler != null) {
            this.entityActionHandler.read(nbt);
        }
    }

    @Override
    public void writeAdditional(CompoundNBT nbt) {
        super.writeAdditional(nbt);
        nbt.putBoolean("attack", this.attack);
        nbt.putInt("level", this.getLevel());
        if (this.entityActionHandler != null) {
            this.entityActionHandler.write(nbt);
        }
    }
}
