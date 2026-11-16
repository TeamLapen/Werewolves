package de.teamlapen.werewolves.mixin.entity;

import de.teamlapen.vampirism.api.world.entity.IExtendedCreatureVampirism;
import de.teamlapen.vampirism.api.world.entity.vampire.IVampire;
import de.teamlapen.vampirism.common.world.entity.ExtendedCreature;
import de.teamlapen.werewolves.api.entities.werewolf.IVillagerTransformable;
import de.teamlapen.werewolves.api.entities.werewolf.TransformType;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfTransformable;
import de.teamlapen.werewolves.common.core.ModEntities;
import de.teamlapen.werewolves.common.world.entities.werewolf.BasicWerewolfEntity;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mixin(Villager.class)
public abstract class MixinVillagerEntity extends AbstractVillager implements IVillagerTransformable {
    @Unique
    @SuppressWarnings("WrongEntityDataParameterClass")
    private static final EntityDataAccessor<Integer> TYPE = SynchedEntityData.defineId(Villager.class, EntityDataSerializers.INT);

    @Unique
    private boolean werewolf;
    @Unique
    private WerewolfForm form = WerewolfForm.BEAST;
    @Unique
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
        entity.setSkinType(this.form, getSkinType(form));
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
    public boolean hurtServer(@Nonnull ServerLevel level, @Nonnull DamageSource source, float amount) {
        if (super.hurtServer(level, source, amount)) {
            if (isWerewolf()) {
                this.rage += (int) (amount * 10);
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
            if (this.level().getGameTime() % 400 == 10) {
                if (Helper.isFullMoon(this.level())) {
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
    public int getSkinType(@Nullable WerewolfForm form) {
        int i = getEntityData().get(TYPE);
        return Math.max(i, 0);
    }

    @Override
    public int getEyeType(@Nullable WerewolfForm form) {
        return 0;
    }

    @Override
    public void setSkinType(@Nullable WerewolfForm form, int skinType) {
        this.getEntityData().set(TYPE, skinType);
    }

    @Override
    public void setEyeType(@Nullable WerewolfForm form, int eyeType) {
    }

    @Override
    public boolean hasGlowingEyes(WerewolfForm form) {
        return false;
    }

    @Override
    public void setWerewolfFaction(boolean werewolf) {
        this.werewolf = werewolf;
        if (werewolf) {
            this.form = this.getRandom().nextBoolean() ? WerewolfForm.SURVIVALIST : WerewolfForm.BEAST;
        }
    }

    @Override
    public void onAddedToLevel() {
        super.onAddedToLevel();
        if (this.getEntityData().get(TYPE) == -1) {
            this.getEntityData().set(TYPE, this.getRandom().nextInt(TYPES));
        }
    }

    @Inject(method = "defineSynchedData", at = @At("RETURN"))
    protected void werewolves_defineSynchedData(SynchedEntityData.Builder pBuilder, CallbackInfo ci) {
        pBuilder.define(TYPE, -1);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("RETURN"))
    public void werewolves_addAdditionalSaveData(ValueOutput output, CallbackInfo ci) {
        ValueOutput nbt = output.child("werewolves");
        nbt.putBoolean("werewolf", this.werewolf);
        if (this.form != null) {
            nbt.putString("form", this.form.getName());
        }
        nbt.putInt("type", this.getSkinType());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("RETURN"))
    public void werewolves_readAdditionalSaveData(ValueInput input, CallbackInfo ci) {
        input.child("werewolves").ifPresent(nbt -> {
            this.werewolf = nbt.getBooleanOr("werewolf", false);
            nbt.getString("form").ifPresent(form -> this.form = WerewolfForm.getForm(form));
            nbt.getInt("type").ifPresent(t -> this.getEntityData().set(TYPE, t < TYPES && t >= 0 ? t : -1));
        });
    }
}
