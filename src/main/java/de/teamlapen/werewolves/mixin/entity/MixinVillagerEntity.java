package de.teamlapen.werewolves.mixin.entity;

import de.teamlapen.vampirism.api.entity.EntityClassType;
import de.teamlapen.vampirism.api.entity.IExtendedCreatureVampirism;
import de.teamlapen.vampirism.api.entity.actions.EntityActionTier;
import de.teamlapen.vampirism.api.entity.vampire.IVampire;
import de.teamlapen.vampirism.entity.ExtendedCreature;
import de.teamlapen.werewolves.api.entities.werewolf.IVillagerTransformable;
import de.teamlapen.werewolves.api.entities.werewolf.TransformType;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfTransformable;
import de.teamlapen.werewolves.core.ModEntities;
import de.teamlapen.werewolves.entities.werewolf.BasicWerewolfEntity;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public abstract class MixinVillagerEntity extends AbstractVillager implements IVillagerTransformable {
    @SuppressWarnings("WrongEntityDataParameterClass")
    private static final EntityDataAccessor<Integer> TYPE = SynchedEntityData.defineId(Villager.class, EntityDataSerializers.INT);

    private boolean werewolf;
    private WerewolfForm form = WerewolfForm.BEAST;
    private final EntityActionTier entityTier = EntityActionTier.Medium;
    private EntityClassType entityClass;
    protected int rage;

    @Deprecated
    public MixinVillagerEntity(EntityType<? extends AbstractVillager> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public BasicWerewolfEntity _transformToWerewolf() {
        EntityType<? extends BasicWerewolfEntity> type;
        if (this.form == WerewolfForm.BEAST) {
            type = ModEntities.WEREWOLF_BEAST.get();
        } else {
            type = ModEntities.WEREWOLF_SURVIVALIST.get();
        }
        BasicWerewolfEntity entity = WerewolfTransformable.copyData(type, this);
        entity.setSourceEntity(this);
        return entity;
    }

    @Override
    public WerewolfTransformable _transformBack() {
        return this;
    }

    @Override
    public boolean canTransform() {
        return isWerewolf() && this.rage > this.getMaxHealth() * 4;
    }

    private boolean isWerewolf() {
        return this.werewolf && !(this instanceof IVampire || ExtendedCreature.getSafe(this).map(IExtendedCreatureVampirism::hasPoisonousBlood).orElse(false));
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (super.hurt(source, amount)) {
            if (isWerewolf()) {
                this.rage += amount * 10;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.isWerewolf()) {
            if (this.rage > 150) {
                WerewolfTransformable werewolf = this.transformToWerewolf(TransformType.TIME_LIMITED);
                ((Mob) werewolf).setLastHurtByMob(this.getTarget());
            }
            if (this.level.getGameTime() % 400 == 10) {
                if (Helper.isFullMoon(this.level)) {
                    this.transformToWerewolf(TransformType.FULL_MOON);
                }
                if (rage > 10) {
                    this.rage -= 10;
                }
            }
        }
    }

    @Override
    public void reset() {
        this.rage = 0;
    }

    @NotNull
    @Override
    public WerewolfForm getForm() {
        return this.form;
    }

    @Override
    public EntityClassType getEntityClass() {
        return this.entityClass == null ? this.entityClass = EntityClassType.getRandomClass(this.getRandom()) : this.entityClass;
    }

    @Override
    public EntityActionTier getEntityTier() {
        return this.entityTier;
    }

    @Override
    public int getSkinType(@Nullable WerewolfForm form) {
        int i = getEntityData().get(TYPE);
        return Math.max(i, 0);
    }

    @Override
    public void setWerewolfFaction(boolean werewolf) {
        this.werewolf = werewolf;
        if (werewolf) {
            this.form = this.getRandom().nextBoolean() ? WerewolfForm.SURVIVALIST : WerewolfForm.BEAST;
        }
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        if (this.getEntityData().get(TYPE) == -1) {
            this.getEntityData().set(TYPE, this.getRandom().nextInt(TYPES));
        }
    }

    @Inject(method = "defineSynchedData", at = @At("RETURN"))
    protected void werewolves_defineSynchedData(CallbackInfo ci) {
        this.getEntityData().define(TYPE, -1);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("RETURN"))
    public void werewolves_addAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean("werewolf", this.werewolf);
        if (this.form != null) {
            nbt.putString("form", this.form.getName());
        }
        nbt.putInt("type", this.getSkinType());
        if (this.entityClass != null) {
            nbt.putInt("entityclasstype", EntityClassType.getID(this.entityClass));
        }
        compound.put("werewolves", nbt);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("RETURN"))
    public void werewolves_readAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        CompoundTag nbt = compound.getCompound("werewolves");
        this.werewolf = nbt.getBoolean("werewolf");
        if (nbt.contains("form")) {
            this.form = WerewolfForm.getForm(nbt.getString("form"));
        }
        if (nbt.contains("type")) {
            int t = nbt.getInt("type");
            this.getEntityData().set(TYPE, t < TYPES && t >= 0 ? t : -1);
        }
        if (nbt.contains("entityclasstype")) {
            this.entityClass = EntityClassType.getEntityClassType(nbt.getInt("entityclasstype"));
        }
    }
}
