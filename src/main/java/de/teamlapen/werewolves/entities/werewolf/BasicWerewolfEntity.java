package de.teamlapen.werewolves.entities.werewolf;

import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.difficulty.Difficulty;
import de.teamlapen.vampirism.api.entity.EntityClassType;
import de.teamlapen.vampirism.api.entity.IEntityLeader;
import de.teamlapen.vampirism.api.entity.IVillageCaptureEntity;
import de.teamlapen.vampirism.api.entity.actions.EntityActionTier;
import de.teamlapen.vampirism.api.entity.actions.IActionHandlerEntity;
import de.teamlapen.vampirism.api.entity.actions.IEntityActionUser;
import de.teamlapen.vampirism.api.world.ICaptureAttributes;
import de.teamlapen.vampirism.effects.BadOmenEffect;
import de.teamlapen.vampirism.entity.action.ActionHandlerEntity;
import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.vampirism.entity.goals.LookAtClosestVisibleGoal;
import de.teamlapen.vampirism.entity.hunter.HunterBaseEntity;
import de.teamlapen.vampirism.entity.minion.management.MinionTasks;
import de.teamlapen.vampirism.entity.minion.management.PlayerMinionController;
import de.teamlapen.vampirism.player.FactionBasePlayer;
import de.teamlapen.vampirism.world.MinionWorldData;
import de.teamlapen.werewolves.api.entities.IEntityFollower;
import de.teamlapen.werewolves.api.entities.werewolf.TransformType;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfTransformable;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModEntities;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModSounds;
import de.teamlapen.werewolves.entities.goals.DefendLeaderGoal;
import de.teamlapen.werewolves.entities.goals.FollowAlphaWerewolfGoal;
import de.teamlapen.werewolves.entities.goals.WerewolfAttackVillageGoal;
import de.teamlapen.werewolves.entities.goals.WerewolfDefendVillageGoal;
import de.teamlapen.werewolves.entities.minion.WerewolfMinionEntity;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.WerewolfVillageData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public abstract class BasicWerewolfEntity extends WerewolfBaseEntity implements WerewolfTransformable, IEntityActionUser, IVillageCaptureEntity, IEntityFollower {
    protected static final EntityDataAccessor<Integer> SKINTYPE = SynchedEntityData.defineId(BasicWerewolfEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> EYETYPE = SynchedEntityData.defineId(BasicWerewolfEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> LEVEL = SynchedEntityData.defineId(BasicWerewolfEntity.class, EntityDataSerializers.INT);
    private static final int MAX_LEVEL = 2;

    private final WerewolfForm werewolfForm;
    private final ActionHandlerEntity<?> entityActionHandler;
    private WerewolfTransformable transformed;
    /**
     * only used if {@link #transformType} = {@link TransformType#TIME_LIMITED}
     */
    private int transformedDuration;
    private TransformType transformType;
    private EntityClassType entityClass;
    private EntityActionTier entityTier;
    private IEntityLeader entityLeader;

    @Nullable
    protected ICaptureAttributes villageAttributes;
    protected boolean attack;

    public BasicWerewolfEntity(EntityType<? extends BasicWerewolfEntity> type, Level world, WerewolfForm werewolfForm) {
        super(type, world);
        this.werewolfForm = werewolfForm;
        this.entityClass = EntityClassType.getRandomClass(world.random);
        this.entityTier = EntityActionTier.Low;
        this.entityActionHandler = new ActionHandlerEntity<>(this);
        this.xpReward = 3;
    }

    @Nonnull
    @Override
    public EntityDimensions getDimensions(@Nonnull Pose poseIn) {
        return this.werewolfForm.getSize(poseIn).map(p -> p.scale(this.getScale())).orElse(super.getDimensions(poseIn));
    }

    @Override
    public boolean isPersistenceRequired() {
        return super.isPersistenceRequired() || this.transformed != null;
    }

    @Nonnull
    @Override
    public WerewolfForm getForm() {
        return werewolfForm;
    }

    @Override
    public BasicWerewolfEntity _transformToWerewolf() {
        return this;
    }

    @Override
    public WerewolfTransformable _transformBack() {
        if (this.transformed == null) return this;
        ((Mob) this.transformed).copyPosition(this);
        ((Mob) this.transformed).revive();
        this.level.addFreshEntity(((Mob) this.transformed));
        this.remove(RemovalReason.DISCARDED);
        ((Mob) this.transformed).setHealth(this.getHealth() / this.getMaxHealth() * ((Mob) this.transformed).getMaxHealth());
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

    @Override
    public int getSkinType(@Nullable WerewolfForm form) {
        return Math.max(0, this.getEntityData().get(SKINTYPE));
    }

    @Override
    public int getEyeType(@Nullable WerewolfForm form) {
        return Math.max(0, this.getEntityData().get(EYETYPE));
    }

    @Override
    public void start(TransformType type) {
        this.transformType = type;
        if (type == TransformType.TIME_LIMITED) {
            this.transformedDuration = WerewolvesConfig.BALANCE.MOBPROPS.werewolf_transform_duration.get();
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.transformed != null && this.level.getGameTime() % 20 == 0) {
            switch (this.transformType) {
                case TIME_LIMITED:
                    if (--this.transformedDuration <= 0) {
                        this.transformBack();
                    }
                    break;
                case FULL_MOON:
                    if (!Helper.isFullMoon(this.level)) {
                        this.transformBack();
                    }
                    break;
            }
        }
        if (this.entityActionHandler != null) {
            this.entityActionHandler.handle();
        }
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        if (nbt.contains("level")) {
            this.setEntityLevel(nbt.getInt("level"));
        }
        if (nbt.contains("type")) {
            int t = nbt.getInt("type");
            this.getEntityData().set(SKINTYPE, t < 126 && t >= 0 ? t : -1);
        }
        if (nbt.contains("eyeType")) {
            int t = nbt.getInt("eyeType");
            this.getEntityData().set(EYETYPE, t < 126 && t >= 0 ? t : -1);
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
        if (nbt.contains("transformType")) {
            this.transformType = TransformType.valueOf(nbt.getString("transformType"));
        }
        if (nbt.contains("transformed")) {
            ResourceLocation id = new ResourceLocation(nbt.getString("transformed_id"));
            EntityType<?> type = ForgeRegistries.ENTITIES.getValue(id);
            if (type != null) {
                Entity entity = type.create(this.level);
                if (entity instanceof LivingEntity && entity instanceof WerewolfTransformable) {
                    ((LivingEntity) entity).readAdditionalSaveData(nbt.getCompound("transformed"));
                    this.transformed = ((WerewolfTransformable) entity);
                }
            }
        }
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("transformedDuration", this.transformedDuration);
        if (this.entityActionHandler != null) {
            this.entityActionHandler.write(nbt);
        }
        if (this.transformType != null) {
            nbt.putString("transformType", this.transformType.name());
        }
        nbt.putInt("level", this.getEntityLevel());
        nbt.putInt("type", this.getSkinType());
        nbt.putInt("eyeType", this.getEyeType());
        nbt.putBoolean("attack", this.attack);
        if (this.transformed != null) {
            CompoundTag transformed = new CompoundTag();
            ((LivingEntity) this.transformed).saveWithoutId(transformed);
            nbt.put("transformed", transformed);
            nbt.putString("transformed_id", ((LivingEntity) this.transformed).getType().getRegistryName().toString());
        }
    }

    @Override
    public IActionHandlerEntity getActionHandler() {
        return this.entityActionHandler;
    }

    @Override
    public boolean hurt(@Nonnull DamageSource source, float amount) {
        if (this.transformType == TransformType.TIME_LIMITED) {
            this.transformedDuration = WerewolvesConfig.BALANCE.MOBPROPS.werewolf_transform_duration.get() * 20;
        }
        return super.hurt(source, amount);
    }

    @Override
    public int getEntityLevel() {
        return getEntityData().get(LEVEL);
    }

    @Override
    public boolean hasGlowingEyes(WerewolfForm form) {
        return true; //TODO
    }

    @Override
    public void setEntityLevel(int level) {
        if (level >= 0) {
            getEntityData().set(LEVEL, level);
            this.updateEntityAttributes();
            if (level == 2) {
                this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1000000, 1));
            }
            this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }
    }

    @Nonnull
    @Override
    protected InteractionResult mobInteract(@Nonnull Player player, @Nonnull InteractionHand hand) {
        int werewolfLevel = WerewolfPlayer.getOpt(player).map(FactionBasePlayer::getLevel).orElse(0);
        if (werewolfLevel > 0) {
            FactionPlayerHandler.getOpt(player).ifPresent(fph -> {
                if (fph.getMaxMinions() > 0) {
                    ItemStack heldItem = player.getItemInHand(hand);

                    if (this.getEntityLevel() > 0) {
                        if (heldItem.getItem() == ModItems.werewolf_minion_charm) {
                            player.displayClientMessage(new TranslatableComponent("text.werewolves.basic_werewolf.minion.unavailable"), true);
                        }
                    } else {
                        boolean freeSlot = MinionWorldData.getData(player.level).map(data -> data.getOrCreateController(fph)).map(PlayerMinionController::hasFreeMinionSlot).orElse(false);
                        player.displayClientMessage(new TranslatableComponent("text.werewolves.basic_werewolf.minion.available"), false);
                        if (heldItem.getItem() == ModItems.werewolf_minion_charm) {
                            if (!freeSlot) {
                                player.displayClientMessage(new TranslatableComponent("text.werewolves.basic_werewolf.minion.no_free_slot"), false);
                            } else {
                                player.displayClientMessage(new TranslatableComponent("text.werewolves.basic_werewolf.minion.start_serving"), false);
                                convertToMinion(player);
                                if (!player.getAbilities().instabuild) heldItem.shrink(1);
                            }
                        } else if (freeSlot) {
                            player.displayClientMessage(new TranslatableComponent("text.werewolves.basic_werewolf.minion.require_equipment", UtilLib.translate(ModItems.werewolf_minion_charm.getDescriptionId())), false);
                        }
                    }
                }
            });
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    /**
     * Assumes preconditions as been met. Checks conditions but does not give feedback to user
     */
    public void convertToMinion(Player lord) {
        FactionPlayerHandler.getOpt(lord).ifPresent(fph -> {
            if (fph.getMaxMinions() > 0) {
                MinionWorldData.getData(lord.level).map(w -> w.getOrCreateController(fph)).ifPresent(controller -> {
                    if (controller.hasFreeMinionSlot()) {
                        if (fph.getCurrentFaction() == this.getFaction()) {
                            WerewolfMinionEntity.WerewolfMinionData data = new WerewolfMinionEntity.WerewolfMinionData("Minion", this.getSkinType(), this.getEyeType(), this.hasGlowingEyes(), this.getForm());
                            int id = controller.createNewMinionSlot(data, ModEntities.werewolf_minion);
                            if (id < 0) {
                                LOGGER.error("Failed to get minion slot");
                                return;
                            }
                            WerewolfMinionEntity minion = ModEntities.werewolf_minion.create(this.level);
                            minion.claimMinionSlot(id, controller);
                            minion.copyPosition(this);
                            minion.markAsConverted();
                            controller.activateTask(0, MinionTasks.stay);
                            UtilLib.replaceEntity(this, minion);

                        } else {
                            LOGGER.warn("Wrong faction for minion");
                        }
                    } else {
                        LOGGER.warn("No free slot");
                    }
                });
            } else {
                LOGGER.error("Can't have minions");
            }
        });
    }

    @Override
    public int getMaxEntityLevel() {
        return MAX_LEVEL;
    }

    @Override
    public boolean canTransform() {
        return transformed != null;
    }

    @Override
    public int suggestEntityLevel(Difficulty difficulty) {
        return switch (this.random.nextInt(5)) {
            case 0 -> (int) (difficulty.minPercLevel / 100F * MAX_LEVEL);
            case 1 -> (int) (difficulty.avgPercLevel / 100F * MAX_LEVEL);
            case 2 -> (int) (difficulty.maxPercLevel / 100F * MAX_LEVEL);
            default -> this.random.nextInt(MAX_LEVEL + 1);
        };
    }

    protected void updateEntityAttributes() {
        int l = Math.max(getEntityLevel(), 0);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(WerewolvesConfig.BALANCE.MOBPROPS.werewolf_max_health.get() + WerewolvesConfig.BALANCE.MOBPROPS.werewolf_max_health_pl.get() * l);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(WerewolvesConfig.BALANCE.MOBPROPS.werewolf_attack_damage.get() + WerewolvesConfig.BALANCE.MOBPROPS.werewolf_attack_damage_pl.get() * l);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(WerewolvesConfig.BALANCE.MOBPROPS.werewolf_speed.get());
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        if (this.getEntityData().get(SKINTYPE) == -1) {
            this.getEntityData().set(SKINTYPE, this.getRandom().nextInt(126));
        }
        if (this.getEntityData().get(EYETYPE) == -1) {
            this.getEntityData().set(EYETYPE, this.getRandom().nextInt(126));
        }
    }

    public void setSourceEntity(WerewolfTransformable entity) {
        this.entityClass = entity.getEntityClass();
        this.entityTier = entity.getEntityTier();
        this.transformed = entity;
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
    public AABB getTargetVillageArea() {
        return this.villageAttributes == null ? null : this.villageAttributes.getVillageArea();
    }

    public int getAmbientSoundInterval() {
        return 240;
    }

    protected SoundEvent getAmbientSound() {
        return ModSounds.entity_werewolf_growl;
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
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BreakDoorGoal(this, (difficulty) -> difficulty == net.minecraft.world.Difficulty.HARD));//Only break doors on hard difficulty
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(6, new FollowAlphaWerewolfGoal<>(this, 0.8));
        this.goalSelector.addGoal(9, new RandomStrollGoal(this, 0.7));
        this.goalSelector.addGoal(10, new LookAtClosestVisibleGoal(this, Player.class, 20F, 0.6F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, HunterBaseEntity.class, 17F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));


        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new WerewolfAttackVillageGoal<>(this));
        this.targetSelector.addGoal(4, new WerewolfDefendVillageGoal<>(this));//Should automatically be mutually exclusive with  attack village
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Player.class, 5, true, false, VampirismAPI.factionRegistry().getPredicate(getFaction(), true, false, true, true, null)));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, PathfinderMob.class, 5, true, false, VampirismAPI.factionRegistry().getPredicate(getFaction(), false, true, false, false, null)));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, PatrollingMonster.class, 5, true, true, (living) -> UtilLib.isInsideStructure(living, StructureFeature.VILLAGE)));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, AbstractSkeleton.class, false));
        this.targetSelector.addGoal(8, new DefendLeaderGoal<>(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(LEVEL, -1);
        this.getEntityData().define(SKINTYPE, -1);
        this.getEntityData().define(EYETYPE, -1);
    }

    @Nonnull
    @Override
    public Optional<IEntityLeader> getLeader() {
        return Optional.ofNullable(this.entityLeader);
    }

    @Override
    public void setLeader(@Nullable IEntityLeader leader) {
        this.entityLeader = leader;
    }

    public static class Beast extends BasicWerewolfEntity {
        public Beast(EntityType<? extends Beast> type, Level world) {
            super(type, world, WerewolfForm.BEAST);
        }

        @Override
        public void die(@Nonnull DamageSource cause) {
            if (this.villageAttributes == null) {
                BadOmenEffect.handlePotentialBannerKill(cause.getEntity(), this);
            }
            super.die(cause);
        }

        @Nullable
        @Override
        public SpawnGroupData finalizeSpawn(@Nonnull ServerLevelAccessor world, @Nonnull DifficultyInstance difficulty, @Nonnull MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag nbt) {
            if (!(reason == MobSpawnType.SPAWN_EGG || reason == MobSpawnType.BUCKET || reason == MobSpawnType.CONVERSION || reason == MobSpawnType.COMMAND) && this.getRandom().nextInt(50) == 0) {
                this.setItemSlot(EquipmentSlot.HEAD, WerewolfVillageData.createBanner());
            }
            return super.finalizeSpawn(world, difficulty, reason, spawnData, nbt);
        }
    }

    public static class Survivalist extends BasicWerewolfEntity {
        public Survivalist(EntityType<? extends Survivalist> type, Level world) {
            super(type, world, WerewolfForm.SURVIVALIST);
        }
    }
}
