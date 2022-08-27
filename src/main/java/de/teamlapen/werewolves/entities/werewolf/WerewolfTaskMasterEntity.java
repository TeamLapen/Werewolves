package de.teamlapen.werewolves.entities.werewolf;

import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.entity.IDefaultTaskMasterEntity;
import de.teamlapen.vampirism.entity.ai.goals.ForceLookEntityGoal;
import de.teamlapen.vampirism.entity.ai.goals.LookAtClosestVisibleGoal;
import de.teamlapen.vampirism.entity.hunter.HunterBaseEntity;
import de.teamlapen.vampirism.inventory.TaskBoardMenu;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class WerewolfTaskMasterEntity extends WerewolfBaseEntity implements IDefaultTaskMasterEntity {

    private static final EntityDataAccessor<String> BIOME_TYPE = SynchedEntityData.defineId(WerewolfTaskMasterEntity.class, EntityDataSerializers.STRING);
    @Nullable
    private Player interactor;

    public WerewolfTaskMasterEntity(EntityType<? extends WerewolfTaskMasterEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder getAttributeBuilder() {
        return WerewolfBaseEntity.getAttributeBuilder();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.interactor != null && (!this.interactor.isAlive() || !(this.interactor.containerMenu instanceof TaskBoardMenu))) {
            this.interactor = null;
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean shouldShowName() {
        return Helper.isWerewolf(Minecraft.getInstance().player);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, PathfinderMob.class, 10.0F, 1.0D, 1.1D, VampirismAPI.factionRegistry().getPredicate(this.getFaction(), false, true, false, false, null)));
        this.goalSelector.addGoal(2, new ForceLookEntityGoal<>(this));
        this.goalSelector.addGoal(8, new MoveThroughVillageGoal(this, 0.6D, true, 600, () -> false));
        this.goalSelector.addGoal(9, new RandomStrollGoal(this, 0.7D));
        this.goalSelector.addGoal(10, new LookAtClosestVisibleGoal(this, Player.class, 20.0F, 0.6F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, HunterBaseEntity.class, 17.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BIOME_TYPE, Registry.VILLAGER_TYPE.getDefaultKey().toString());
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        SpawnGroupData data = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        this.setBiomeType(VillagerType.byBiome(worldIn.getBiome(this.blockPosition())));
        return data;
    }

    @NotNull
    @Override
    protected InteractionResult mobInteract(@NotNull Player playerEntity, @NotNull InteractionHand hand) {
        if (!this.level.isClientSide) {
            if (Helper.isWerewolf(playerEntity) && this.interactor == null && this.processInteraction(playerEntity, this)) {
                this.getNavigation().stop();
                this.interactor = playerEntity;
            }

        }
        return InteractionResult.SUCCESS;
    }

    @NotNull
    @Override
    public Optional<Player> getForceLookTarget() {
        return Optional.ofNullable(this.interactor);
    }

    public VillagerType getBiomeType() {
        String key = this.entityData.get(BIOME_TYPE);
        ResourceLocation id = new ResourceLocation(key);
        return Registry.VILLAGER_TYPE.get(id);
    }

    protected void setBiomeType(VillagerType type) {
        this.entityData.set(BIOME_TYPE, Registry.VILLAGER_TYPE.getKey(type).toString());
    }

    @NotNull
    @Override
    public WerewolfForm getForm() {
        return WerewolfForm.NONE;
    }

    @Override
    public int getSkinType(WerewolfForm form) {
        return 0;
    }

    @Override
    public int getEyeType(WerewolfForm form) {
        return 0;
    }

    @Override
    public boolean hasGlowingEyes(WerewolfForm form) {
        return false;
    }
}
