package de.teamlapen.werewolves.entities;

import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.difficulty.Difficulty;
import de.teamlapen.vampirism.api.entity.EntityClassType;
import de.teamlapen.vampirism.api.entity.IVillageCaptureEntity;
import de.teamlapen.vampirism.api.entity.actions.EntityActionTier;
import de.teamlapen.vampirism.api.entity.actions.IEntityActionUser;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.world.ICaptureAttributes;
import de.teamlapen.vampirism.config.BalanceMobProps;
import de.teamlapen.vampirism.core.ModEntities;
import de.teamlapen.vampirism.core.ModLootTables;
import de.teamlapen.vampirism.entity.VampirismEntity;
import de.teamlapen.vampirism.entity.action.ActionHandlerEntity;
import de.teamlapen.vampirism.entity.goals.AttackMeleeNoSunGoal;
import de.teamlapen.vampirism.entity.goals.AttackVillageGoal;
import de.teamlapen.vampirism.entity.goals.DefendVillageGoal;
import de.teamlapen.vampirism.entity.goals.LookAtClosestVisibleGoal;
import de.teamlapen.vampirism.entity.hunter.HunterBaseEntity;
import de.teamlapen.vampirism.entity.vampire.BasicVampireEntity;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entity.IWerewolfMob;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.WEntities;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.Structures;
import net.minecraftforge.registries.ForgeRegistries;
import sun.swing.SwingLazyValue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WerewolfEntity extends VampirismEntity implements IVillageCaptureEntity, IEntityActionUser, IWerewolfMob {
    private static final DataParameter<Integer> LEVEL = EntityDataManager.createKey(VampirismEntity.class, DataSerializers.VARINT);
    private static final int MAX_LEVEL = 2;

    private AbstractVillagerEntity villager;
    private boolean isConverted;

    public WerewolfEntity(EntityType<? extends WerewolfEntity> type, World world) {
        super(type, world);
        entitytier = EntityActionTier.Medium;
        entityclass = EntityClassType.getRandomClass(this.getRNG());
        this.entityActionHandler = new ActionHandlerEntity<>(this);
        //this.enableImobConversion();
    }

    public static WerewolfEntity createFromVillager(AbstractVillagerEntity villager) {
        WerewolfEntity werewolf = WEntities.werewolf.create(villager.world);
        werewolf.villager = villager;
        werewolf.isConverted = true;
        werewolf.copyLocationAndAnglesFrom(villager);
        return werewolf;
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
    public void writeAdditional(CompoundNBT nbt) {
        super.writeAdditional(nbt);
        nbt.putInt("level",getLevel());
        nbt.putBoolean("attack",this.attack);
        nbt.putInt("entityclasstype", EntityClassType.getID(this.entityclass));
        if(isConverted) {
            nbt.putBoolean("isConverted", this.isConverted);
            nbt.putString("type", this.villager.getType().getRegistryName().toString());
            nbt.put("villager",this.villager.serializeNBT());
        }
        this.entityActionHandler.write(nbt);
    }

    @Override
    public void readAdditional(CompoundNBT nbt) {
        super.readAdditional(nbt);
        if (nbt.contains("level")) {
            setLevel(nbt.getInt("level"));
        }
        if (nbt.contains("attack")) {
            this.attack = nbt.getBoolean("attack");
        }
        if(nbt.contains("isConverted")) {
            this.isConverted = true;
            EntityType<? extends AbstractVillagerEntity> type = (EntityType<? extends AbstractVillagerEntity>)ForgeRegistries.ENTITIES.getValue(new ResourceLocation(nbt.getString("type")));
            this.villager = type.create(this.world);
            this.villager.deserializeNBT(nbt.getCompound("villager"));
        }
        this.entityActionHandler.read(nbt);
    }

    public void transformBack() {
        this.villager.copyLocationAndAnglesFrom(this);
        this.remove();
        this.villager.revive();
        this.world.addEntity(villager);
    }

    @Override
    public void livingTick() {
        super.livingTick();
        this.entityActionHandler.handle();
    }

    @Override
    protected int getExperiencePoints(@Nonnull PlayerEntity player) {
        return 6 + getLevel();
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.getDataManager().register(LEVEL,-1);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new BreakDoorGoal(this, (difficulty) -> difficulty == net.minecraft.world.Difficulty.HARD));//Only break doors on hard difficulty
        this.goalSelector.addGoal(4, new AttackMeleeNoSunGoal(this, 1.0, false));
        this.goalSelector.addGoal(8, new MoveThroughVillageGoal(this, 0.6, true, 600, () -> false));
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

    protected void updateEntityAttributes() {
        int l = Math.max(getLevel(), 0);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(WerewolvesConfig.BALANCE.MOBPROPS.WEREWOLF_MAX_HEALTH.get() + WerewolvesConfig.BALANCE.MOBPROPS.WEREWOLF_MAX_HEALTH_PL.get() * l);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(WerewolvesConfig.BALANCE.MOBPROPS.WEREWOLF_ATTACK_DAMAGE.get() + WerewolvesConfig.BALANCE.MOBPROPS.WEREWOLF_ATTACK_DAMAGE_PL.get() * l);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(WerewolvesConfig.BALANCE.MOBPROPS.WEREWOLF_SPEED.get());
    }

    //Leveling ---------------------------------------------------------------------------------------------------------

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
    public int suggestLevel(Difficulty d) {
        switch (this.rand.nextInt(5)) {
            case 0:
                return (int) (d.minPercLevel / 100F * MAX_LEVEL);
            case 1:
                return (int) (d.avgPercLevel / 100F * MAX_LEVEL);
            case 2:
                return (int) (d.maxPercLevel / 100F * MAX_LEVEL);
            default:
                return this.rand.nextInt(MAX_LEVEL + 1);
        }
    }

    //IMob -------------------------------------------------------------------------------------------------------------

    @Override
    protected EntityType<?> getIMobTypeOpt(boolean iMob) {
        return iMob ? WEntities.werewolf_imob : WEntities.werewolf;
    }

    public static class IMob extends WerewolfEntity implements net.minecraft.entity.monster.IMob {

        public IMob(EntityType<? extends WerewolfEntity> type, World world) {
            super(type, world);
        }

        @Nonnull
        @Override
        protected ResourceLocation getLootTable() {
            return WEntities.werewolf.getLootTable();
        }
    }
    //Entityactions ----------------------------------------------------------------------------------------------------
    /**
     * available actions for AI task & task
     */
    private final ActionHandlerEntity<?> entityActionHandler;
    private final EntityClassType entityclass;
    private final EntityActionTier entitytier;

    @Override
    public EntityClassType getEntityClass() {
        return entityclass;
    }

    @Override
    public EntityActionTier getEntityTier() {
        return entitytier;
    }

    @Override
    public ActionHandlerEntity getActionHandler() {
        return entityActionHandler;
    }

    //Village stuff-----------------------------------------------------------------------------------------------------
    @Nullable
    private ICaptureAttributes villageAttributes;
    private boolean attack;

    @Override
    public void attackVillage(ICaptureAttributes totem) {
        this.villageAttributes = totem;
        this.attack = true;
    }

    @Override
    public void defendVillage(ICaptureAttributes totem) {
        this.villageAttributes = totem;
        this.attack = false;
    }

    @Nullable
    @Override
    public AxisAlignedBB getTargetVillageArea() {
        return villageAttributes == null ? null : villageAttributes.getVillageArea();
    }

    @Override
    public boolean isAttackingVillage() {
        return villageAttributes != null && attack;
    }

    @Override
    public boolean isDefendingVillage() {
        return villageAttributes != null && !attack;
    }

    @Override
    public void stopVillageAttackDefense() {
        this.setCustomName(null);
        this.villageAttributes = null;
    }

    @Nullable
    @Override
    public ICaptureAttributes getCaptureInfo() {
        return villageAttributes;
    }
}
