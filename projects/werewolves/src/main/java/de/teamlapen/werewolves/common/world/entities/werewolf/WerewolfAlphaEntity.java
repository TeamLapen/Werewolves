package de.teamlapen.werewolves.common.world.entities.werewolf;

import de.teamlapen.library.api.difficulty.Difficulty;
import de.teamlapen.library.api.difficulty.IAdjustableLevel;
import de.teamlapen.werewolves.api.entities.werewolf.IWerewolfAlpha;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.common.config.EntityProperties;
import de.teamlapen.werewolves.common.core.ModBiomes;
import de.teamlapen.werewolves.common.core.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.damagesource.DamageSource;
import de.teamlapen.faction.api.factions.IFactionPredicate;
import de.teamlapen.faction.common.factions.FactionPlayerHandler;
import de.teamlapen.faction.common.world.entities.goals.LookAtClosestVisibleGoal;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueOutput;

import javax.annotation.Nullable;

public class WerewolfAlphaEntity extends WerewolfBaseEntity implements IWerewolfAlpha, IAdjustableLevel {
    public static final int MAX_LEVEL = 4;
    private static final EntityDataAccessor<Integer> LEVEL = SynchedEntityData.defineId(WerewolfAlphaEntity.class, EntityDataSerializers.INT);


    public static boolean spawnPredicateAlpha(EntityType<? extends WerewolfAlphaEntity> entityType, ServerLevelAccessor world, EntitySpawnReason spawnReason, BlockPos blockPos, RandomSource random) {
        return world.getBiome(blockPos).is(ModBiomes.WEREWOLF_FOREST) && world.getDifficulty() != net.minecraft.world.Difficulty.PEACEFUL && spawnPredicateWerewolf(entityType, world, spawnReason, blockPos, random);
    }

    public static AttributeSupplier.Builder getAttributeBuilder() {
        return WerewolfBaseEntity.getAttributeBuilder() //TODO values
                .add(Attributes.MAX_HEALTH, EntityProperties.VAMPIRE_BARON_MAX_HEALTH)
                .add(Attributes.ATTACK_DAMAGE, EntityProperties.VAMPIRE_BARON_ATTACK_DAMAGE)
                .add(Attributes.MOVEMENT_SPEED, EntityProperties.VAMPIRE_BARON_MOVEMENT_SPEED)
                .add(Attributes.FOLLOW_RANGE, 5);
    }

    private int followingEntities = 0;

    public WerewolfAlphaEntity(EntityType<? extends WerewolfBaseEntity> type, Level world) {
        super(type, world, true);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("level", getEntityLevel());
    }

    @Override
    public void decreaseFollowerCount() {
        this.followingEntities = Math.max(0, this.followingEntities - 1);
    }

    @Override
    public int getFollowingCount() {
        return this.followingEntities;
    }

    public void setEntityLevel(int level) {
        level = Mth.clamp(level, 0, MAX_LEVEL);
        if (level >= 0) {
            getEntityData().set(LEVEL, level);
            this.updateEntityAttributes();
            this.setCustomName(getTypeName().plainCopy().append(Component.translatable("entity.werewolves.alpha_werewolf.level", level + 1)));
        } else {
            this.setCustomName(null);
        }
    }

    public int getEntityLevel() {
        return getEntityData().get(LEVEL);
    }

    public int getMaxEntityLevel() {
        return MAX_LEVEL;
    }

    @Override
    public int getMaxHeadXRot() {
        return 5;
    }

    @Override
    public int getMaxHeadYRot() {
        return 5;
    }

    @Override
    public int getMaxFollowerCount() {
        return (int) ((EntityProperties.ADVANCED_VAMPIRE_MAX_FOLLOWER) * ((this.getEntityLevel() + 1) / (float) (this.getMaxEntityLevel() + 1)));
    }

    @Override
    public int getPortalCooldown() {
        return 500;
    }

    @Override
    public boolean increaseFollowerCount() {
        if (this.followingEntities < getMaxFollowerCount()) {
            this.followingEntities++;
            return true;
        }
        return false;
    }

    @Override
    protected boolean canRide(Entity pVehicle) {
        return false;
    }

    @Override
    public boolean killedEntity(ServerLevel level, LivingEntity entity, DamageSource source) {
        super.killedEntity(level, entity, source);
        if (entity instanceof WerewolfAlphaEntity) {
            this.setHealth(this.getMaxHealth());
        }
        return true;
    }

    @Override
    protected void readAdditionalSaveData(ValueInput nbt) {
        super.readAdditionalSaveData(nbt);
        nbt.getInt("level").ifPresent(this::setEntityLevel);
    }

    @Override
    public boolean shouldShowName() {
        return true;
    }

    public int getAmbientSoundInterval() {
        return 240;
    }

    protected SoundEvent getAmbientSound() {
        return ModSounds.ENTITY_WEREWOLF_GROWL.get();
    }

    @Override
    public int suggestEntityLevel(Difficulty d) {
        int avg = Math.round(((d.avgPercLevel()) / 100F - 5 / 14F) / (1F - 5 / 14F) * MAX_LEVEL);
        int max = Math.round(((d.maxPercLevel()) / 100F - 5 / 14F) / (1F - 5 / 14F) * MAX_LEVEL);
        int min = Math.round(((d.minPercLevel()) / 100F - 5 / 14F) / (1F - 5 / 14F) * (MAX_LEVEL));

        return switch (random.nextInt(7)) {
            case 0 -> min;
            case 1 -> max + 1;
            case 2 -> avg;
            case 3 -> avg + 1;
            case 4, 5 -> random.nextInt(MAX_LEVEL + 1);
            default -> random.nextInt(max + 2 - min) + min;
        };
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(LEVEL, -1);
    }

    @Override
    protected int getBaseExperienceReward(ServerLevel level) {
        return 20 + 5 * getEntityLevel();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0F, false));
        this.goalSelector.addGoal(6, new AvoidEntityGoal<>(this, Player.class, 6.0F, 0.6, 0.7F, input -> input != null && !isLowerLevel(input)));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 0.2));
        this.goalSelector.addGoal(9, new LookAtClosestVisibleGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (entity, level) -> isLowerLevel(entity)));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, WerewolfAlphaEntity.class, true, false));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, PathfinderMob.class, 5, true, false, IFactionPredicate.defaultTargets(getFaction()).onlyNonPlayers().build()));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, AbstractSkeleton.class, false));
    }

    private boolean isLowerLevel(LivingEntity entity) {
        if (entity instanceof Player player) {
            int playerLevel = FactionPlayerHandler.get(player).getCurrentLevel();
            return (playerLevel - 8) / 2F - this.getEntityLevel() <= 0;
        }
        return false;
    }

    @Override
    public WerewolfForm getForm() {
        return WerewolfForm.BEAST4L;
    }

    @Override
    public int getSkinType(@Nullable WerewolfForm form) {
        return this.getId();
    }

    @Override
    public int getEyeType(@Nullable WerewolfForm form) {
        return 0;
    }

    @Override
    public void setSkinType(@Nullable WerewolfForm form, int skinType) {
    }

    @Override
    public void setEyeType(@Nullable WerewolfForm form, int eyeType) {
    }

    @Override
    public boolean hasGlowingEyes(WerewolfForm form) {
        return true;
    }

    protected void updateEntityAttributes() { //TODO different values
        this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(20D);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(EntityProperties.VAMPIRE_BARON_MOVEMENT_SPEED * Math.pow((EntityProperties.VAMPIRE_BARON_IMPROVEMENT_PER_LEVEL - 1) / 5 + 1, (getEntityLevel())));
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(EntityProperties.VAMPIRE_BARON_MAX_HEALTH * Math.pow(EntityProperties.VAMPIRE_BARON_IMPROVEMENT_PER_LEVEL, getEntityLevel()));
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(EntityProperties.VAMPIRE_BARON_ATTACK_DAMAGE * Math.pow(EntityProperties.VAMPIRE_BARON_IMPROVEMENT_PER_LEVEL, getEntityLevel()));
    }
}
