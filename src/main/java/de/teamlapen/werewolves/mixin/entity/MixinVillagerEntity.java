package de.teamlapen.werewolves.mixin.entity;

import de.teamlapen.vampirism.api.entity.EntityClassType;
import de.teamlapen.vampirism.api.entity.IExtendedCreatureVampirism;
import de.teamlapen.vampirism.api.entity.actions.EntityActionTier;
import de.teamlapen.vampirism.api.entity.vampire.IVampire;
import de.teamlapen.vampirism.entity.ExtendedCreature;
import de.teamlapen.werewolves.core.ModEntities;
import de.teamlapen.werewolves.entities.werewolf.BasicWerewolfEntity;
import de.teamlapen.werewolves.entities.werewolf.IVillagerTransformable;
import de.teamlapen.werewolves.entities.werewolf.WerewolfTransformable;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.WerewolfForm;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nonnull;

@Mixin(VillagerEntity.class)
public abstract class MixinVillagerEntity extends AbstractVillagerEntity implements IVillagerTransformable {
    private static final DataParameter<Integer> TYPE = EntityDataManager.defineId(VillagerEntity.class, DataSerializers.INT);

    private boolean werewolf;
    private WerewolfForm form = WerewolfForm.BEAST;
    private final EntityActionTier entityTier = EntityActionTier.Medium;
    private EntityClassType entityClass;
    protected int rage;

    @Deprecated
    public MixinVillagerEntity(EntityType<? extends AbstractVillagerEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public BasicWerewolfEntity _transformToWerewolf() {
        EntityType<? extends BasicWerewolfEntity> type;
        if (this.form == WerewolfForm.BEAST) {
            type = ModEntities.werewolf_beast;
        } else {
            type = ModEntities.werewolf_survivalist;
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
    public boolean hurt(@Nonnull DamageSource source, float amount) {
        if (super.hurt(source, amount) && this.werewolf) {
            this.rage += amount * 10;
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
                ((MobEntity) werewolf).setLastHurtByMob(this.getTarget());
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

    @Nonnull
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
    public int getSkinType() {
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
    public void werewolves_addAdditionalSaveData(CompoundNBT compound, CallbackInfo ci) {
        CompoundNBT nbt = new CompoundNBT();
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
    public void werewolves_readAdditionalSaveData(CompoundNBT compound, CallbackInfo ci) {
        CompoundNBT nbt = compound.getCompound("werewolves");
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
