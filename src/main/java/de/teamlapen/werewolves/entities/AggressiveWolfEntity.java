package de.teamlapen.werewolves.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.WolfEntity;
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

    @Nonnull
    @Override
    protected ITextComponent getProfessionName() {
        return EntityType.WOLF.getName();
    }

    @Nullable
    @Override
    public LivingEntity getOwner() {
        return dead ? null : super.getOwner();
    }

    @Override
    public void writeAdditional(@Nonnull CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("restrictLiveSpan", this.restrictLiveSpan);
    }

    @Override
    public void readAdditional(@Nonnull CompoundNBT compound) {
        super.readAdditional(compound);
        this.restrictLiveSpan = compound.getBoolean("restrictLiveSpan");
    }

    @Override
    public void tick() {
        super.tick();
        if (this.restrictLiveSpan) {
            if (this.ticksExisted > maxTicks) {
                this.attackEntityFrom(DamageSource.MAGIC, 10.0F);
            }
        }
    }

    @Override
    public void onDeath(@Nonnull DamageSource cause) {
        dead = true;
        super.onDeath(cause);
    }
}
