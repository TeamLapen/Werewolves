package de.teamlapen.werewolves.entities.werewolf;

import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.difficulty.Difficulty;
import de.teamlapen.vampirism.api.entity.EntityClassType;
import de.teamlapen.vampirism.api.entity.IVillageCaptureEntity;
import de.teamlapen.vampirism.api.entity.actions.EntityActionTier;
import de.teamlapen.vampirism.api.entity.actions.IActionHandlerEntity;
import de.teamlapen.vampirism.api.entity.actions.IEntityActionUser;
import de.teamlapen.vampirism.api.world.ICaptureAttributes;
import de.teamlapen.vampirism.entity.action.ActionHandlerEntity;
import de.teamlapen.vampirism.entity.goals.AttackVillageGoal;
import de.teamlapen.vampirism.entity.goals.DefendVillageGoal;
import de.teamlapen.vampirism.entity.goals.LookAtClosestVisibleGoal;
import de.teamlapen.vampirism.entity.hunter.HunterBaseEntity;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.entities.WerewolfFormUtil;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.Structure;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class BasicWerewolfEntity extends WerewolfBaseEntity implements WerewolfTransformable, IEntityActionUser, IVillageCaptureEntity {
    protected static final DataParameter<Integer> TYPE = EntityDataManager.createKey(WerewolfBaseEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> LEVEL = EntityDataManager.createKey(WerewolfBaseEntity.class, DataSerializers.VARINT);
    private static final int MAX_LEVEL = 2;

    private final WerewolfFormUtil.Form werewolfForm;
    private WerewolfTransformable transformed;
    private int transformedDuration;

    private final ActionHandlerEntity<?> entityActionHandler;
    private EntityClassType entityClass;
    private EntityActionTier entityTier;

    @Nullable
    private ICaptureAttributes villageAttributes;
    private boolean attack;

    public BasicWerewolfEntity(EntityType<? extends WerewolfBaseEntity> type, World world, WerewolfFormUtil.Form werewolfForm) {
        super(type, world);
        this.werewolfForm = werewolfForm;
        this.entityClass = EntityClassType.getRandomClass(world.rand);
        this.entityTier = EntityActionTier.Low;
        this.entityActionHandler = new ActionHandlerEntity<>(this);
    }


    public WerewolfFormUtil.Form getForm() {
        return werewolfForm;
    }

    @Override
    public BasicWerewolfEntity _transformToWerewolf() {
        return this;
    }

    @Override
    public WerewolfTransformable _transformBack() {
        if (this.transformed == null) return this;
        WerewolfTransformable.copyData(((MobEntity) this.transformed), this);
        ((MobEntity) this.transformed).revive();
        return this.transformed;
    }

    @Override
    public EntityClassType getEntityClass() {
        return entityClass;
    }

    @Override
    public EntityActionTier getEntityTier() {
        return entityTier;
    }

    public int getEntityTextureType() {
        int i = this.getDataManager().get(TYPE);
        return Math.max(i, 0);
    }

    @Override
    public void start() {
        this.transformedDuration = WerewolvesConfig.BALANCE.MOBPROPS.werewolf_transform_duration.get() * 20;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (this.transformed != null && this.world.getGameTime() % 20 == 0) {
            if (--this.transformedDuration <= 0) {
                this.transformBack();
            }
        }
        if (this.entityActionHandler != null) {
            this.entityActionHandler.handle();
        }
    }

    @Override
    public void readAdditional(CompoundNBT nbt) {
        super.readAdditional(nbt);
        if (nbt.contains("level")) {
            this.setLevel(nbt.getInt("level"));
        }
        if (nbt.contains("type")) {
            int t = nbt.getInt("type");
            this.getDataManager().set(TYPE, t < 126 && t >= 0 ? t : -1);
        }
        if (nbt.contains("transformedDuration")) {
            this.transformedDuration = nbt.getInt("transformedDuration");
        }
        if (this.entityActionHandler != null) {
            this.entityActionHandler.read(nbt);
        }
        if (nbt.contains("attack")) {
            this.attack = nbt.getBoolean("attack");
        }
    }

    @Override
    public void writeAdditional(CompoundNBT nbt) {
        super.writeAdditional(nbt);
        nbt.putInt("transformedDuration", this.transformedDuration);
        if (this.entityActionHandler != null) {
            this.entityActionHandler.write(nbt);
        }
        nbt.putInt("level", this.getLevel());
        nbt.putInt("type", this.getEntityTextureType());
        nbt.putBoolean("attack", this.attack);

    }

    @Override
    public IActionHandlerEntity getActionHandler() {
        return this.entityActionHandler;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        this.transformedDuration = WerewolvesConfig.BALANCE.MOBPROPS.werewolf_transform_duration.get() * 20;
        return super.attackEntityFrom(source, amount);
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
    public boolean canTransform() {
        return transformed != null;
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
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(WerewolvesConfig.BALANCE.MOBPROPS.werewolf_max_health.get() + WerewolvesConfig.BALANCE.MOBPROPS.werewolf_max_health_pl.get() * l);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(WerewolvesConfig.BALANCE.MOBPROPS.werewolf_attack_damage.get() + WerewolvesConfig.BALANCE.MOBPROPS.werewolf_attack_damage_pl.get() * l);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(WerewolvesConfig.BALANCE.MOBPROPS.werewolf_speed.get());
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        if (this.getDataManager().get(TYPE) == -1) {
            this.getDataManager().set(TYPE, this.getRNG().nextInt(126));
        }
    }

    public void setSourceEntity(WerewolfTransformable entity) {
        this.entityClass = entity.getEntityClass();
        this.entityTier = entity.getEntityTier();
        this.transformed = entity;
        this.getDataManager().set(TYPE, entity.getEntityTextureType());
    }

    @Override
    public void attackVillage(ICaptureAttributes iCaptureAttributes) {
        this.villageAttributes = iCaptureAttributes;
        this.attack = true;
    }

    @Nonnull
    public WerewolfFormUtil.Form getWerewolfForm() {
        return werewolfForm;
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

    @Nullable
    @Override
    public ICaptureAttributes getCaptureInfo() {
        return this.villageAttributes;
    }

    @Override
    public boolean isDefendingVillage() {
        return this.villageAttributes != null && !attack;
    }

    @Override
    public boolean isAttackingVillage() {
        return this.villageAttributes != null && attack;
    }

    @Override
    public void stopVillageAttackDefense() {
        this.villageAttributes = null;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
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
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 5, true, false, VampirismAPI.factionRegistry().getPredicate(getFaction(), true, false, true, true, null)));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, CreatureEntity.class, 5, true, false, VampirismAPI.factionRegistry().getPredicate(getFaction(), false, true, false, false, null)));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, PatrollerEntity.class, 5, true, true, (living) -> UtilLib.isInsideStructure(living, Structure.VILLAGE)));
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.getDataManager().register(LEVEL, -1);
        this.getDataManager().register(TYPE, -1);
    }

    public static class Beast extends BasicWerewolfEntity {
        public Beast(EntityType<? extends WerewolfBaseEntity> type, World world) {
            super(type, world, WerewolfFormUtil.Form.BEAST);
        }
    }

    public static class Survivalist extends BasicWerewolfEntity {
        public Survivalist(EntityType<? extends WerewolfBaseEntity> type, World world) {
            super(type, world, WerewolfFormUtil.Form.SURVIVALIST);
        }
    }
}
