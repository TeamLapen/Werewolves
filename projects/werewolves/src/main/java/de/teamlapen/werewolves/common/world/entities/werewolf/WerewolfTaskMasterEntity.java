package de.teamlapen.werewolves.common.world.entities.werewolf;

import de.teamlapen.faction.api.factions.IFactionPredicate;
import de.teamlapen.faction.common.world.entities.ForceLookEntityGoal;
import de.teamlapen.faction.common.world.entities.goals.LookAtClosestVisibleGoal;
import de.teamlapen.faction.common.world.inventory.TaskBoardMenu;
import de.teamlapen.vampirism.common.world.entity.IDefaultTaskMasterEntity;
import de.teamlapen.vampirism.common.world.entity.hunter.HunterBaseEntity;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.npc.villager.VillagerData;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.entity.npc.villager.VillagerType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class WerewolfTaskMasterEntity extends WerewolfBaseEntity implements IDefaultTaskMasterEntity {

    private static final EntityDataAccessor<VillagerData> VILLAGER_DATA = SynchedEntityData.defineId(WerewolfTaskMasterEntity.class, EntityDataSerializers.VILLAGER_DATA);
    @Nullable
    private Player interactor;
    @Nullable
    private AABB home;

    public WerewolfTaskMasterEntity(EntityType<? extends WerewolfTaskMasterEntity> type, Level world) {
        super(type, world, false);
    }

    public static AttributeSupplier.Builder getAttributeBuilder() {
        return WerewolfBaseEntity.getAttributeBuilder();
    }

    private static VillagerData createVillagerData(Holder<VillagerType> type) {
        return new VillagerData(type, BuiltInRegistries.VILLAGER_PROFESSION.getOrThrow(VillagerProfession.NONE), 0);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.interactor != null && (!this.interactor.isAlive() || !(this.interactor.containerMenu instanceof TaskBoardMenu))) {
            this.interactor = null;
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, PathfinderMob.class, 10.0F, 1.0D, 1.1D, IFactionPredicate.defaultTargets(this.getFaction()).onlyNonPlayers().build()));
        this.goalSelector.addGoal(2, new ForceLookEntityGoal<>(this));
        this.goalSelector.addGoal(8, new MoveThroughVillageGoal(this, 0.6D, true, 600, () -> false));
        this.goalSelector.addGoal(9, new RandomStrollGoal(this, 0.7D));
        this.goalSelector.addGoal(10, new LookAtClosestVisibleGoal(this, Player.class, 20.0F, 0.6F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, HunterBaseEntity.class, 17.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(VILLAGER_DATA, createVillagerData(BuiltInRegistries.VILLAGER_TYPE.getOrThrow(VillagerType.PLAINS)));
    }

    @Override
    protected void addAdditionalSaveData(@Nonnull ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.store("VillagerData", VillagerData.CODEC, this.getVillageData());
    }

    @Override
    protected void readAdditionalSaveData(@Nonnull ValueInput input) {
        super.readAdditionalSaveData(input);
        input.read("VillagerData", VillagerData.CODEC).ifPresent(data -> this.entityData.set(VILLAGER_DATA, data));
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(@Nonnull ServerLevelAccessor worldIn, @Nonnull DifficultyInstance difficultyIn, @Nonnull EntitySpawnReason reason, @Nullable SpawnGroupData spawnDataIn) {
        SpawnGroupData data = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn);
        this.setBiomeType(VillagerType.byBiome(worldIn.getBiome(this.blockPosition())));
        return data;
    }

    @Nonnull
    @Override
    protected InteractionResult mobInteract(@Nonnull Player playerEntity, @Nonnull InteractionHand hand) {
        if (!this.level().isClientSide()) {
            if (Helper.isWerewolf(playerEntity) && this.interactor == null && this.processInteraction(playerEntity, this)) {
                this.getNavigation().stop();
                this.interactor = playerEntity;
            }

        }
        return InteractionResult.SUCCESS;
    }

    @Nonnull
    @Override
    public Optional<Player> getForceLookTarget() {
        return Optional.ofNullable(this.interactor);
    }

    @Override
    public VillagerData getVillageData() {
        return this.entityData.get(VILLAGER_DATA);
    }

    protected void setBiomeType(ResourceKey<VillagerType> type) {
        this.entityData.set(VILLAGER_DATA, createVillagerData(BuiltInRegistries.VILLAGER_TYPE.getOrThrow(type)));
    }

    @Nullable
    @Override
    public AABB getHome() {
        return this.home;
    }

    @Override
    public void setHome(@Nullable AABB home) {
        this.home = home;
        if (home != null) {
            Vec3 center = home.getCenter();
            super.setHomeTo(BlockPos.containing(center), (int) home.getSize());
        } else {
            super.setHomeTo(BlockPos.ZERO, -1);
        }
    }

    @Override
    public boolean isWithinHomeDistance(double x, double y, double z) {
        return this.home == null || this.home.contains(new Vec3(x, y, z));
    }

    @Override
    public void setHomeArea(BlockPos pos, int r) {
        this.setHome(new AABB(Vec3.atLowerCornerOf(pos.offset(-r, -r, -r)), Vec3.atLowerCornerWithOffset(pos.offset(r, r, r), 1, 1, 1)));
    }

    @Nonnull
    @Override
    public WerewolfForm getForm() {
        return WerewolfForm.NONE;
    }

    @Override
    public int getSkinType(@Nullable WerewolfForm form) {
        return 0;
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
        return false;
    }
}
