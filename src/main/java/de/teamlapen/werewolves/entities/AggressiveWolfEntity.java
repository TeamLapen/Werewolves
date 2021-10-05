package de.teamlapen.werewolves.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AggressiveWolfEntity extends WolfEntity {

    private boolean restrictLiveSpan;
    private boolean dead;
    private int maxTicks;

    public AggressiveWolfEntity(EntityType<? extends AggressiveWolfEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public void restrictLiveSpan(int ticks) {
        this.restrictLiveSpan = true;
        this.maxTicks = ticks;
    }

    @Override
    public boolean canMate(@Nonnull AnimalEntity entity) {
        return false;
    }

    @Override
    public void setOrderedToSit(boolean p_233687_1_) {
    }

    @Nonnull
    @Override
    protected ITextComponent getTypeName() {
        return EntityType.WOLF.getDescription();
    }

    @Nullable
    @Override
    public LivingEntity getOwner() {
        return dead ? null : super.getOwner();
    }

    @Override
    public boolean canBeLeashed(PlayerEntity player) {
        return false;
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("restrictLiveSpan", this.restrictLiveSpan);
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.restrictLiveSpan = compound.getBoolean("restrictLiveSpan");
    }

    @Override
    public void tick() {
        super.tick();
        if (this.restrictLiveSpan) {
            if (this.tickCount > maxTicks) {
                this.hurt(DamageSource.MAGIC, 10.0F);
            }
        }
    }

    @Override
    public void die(@Nonnull DamageSource cause) {
        this.dead = true;
        super.die(cause);
    }
}
