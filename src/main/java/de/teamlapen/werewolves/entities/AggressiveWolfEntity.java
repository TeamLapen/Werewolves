package de.teamlapen.werewolves.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AggressiveWolfEntity extends Wolf {

    private boolean restrictLiveSpan;
    private boolean dead;
    private int maxTicks;

    public AggressiveWolfEntity(@NotNull EntityType<? extends AggressiveWolfEntity> type, @NotNull Level worldIn) {
        super(type, worldIn);
    }

    public void restrictLiveSpan(int ticks) {
        this.restrictLiveSpan = true;
        this.maxTicks = ticks;
    }

    @Override
    public boolean canMate(@NotNull Animal entity) {
        return false;
    }

    @Override
    public void setOrderedToSit(boolean p_233687_1_) {
    }

    @NotNull
    @Override
    protected Component getTypeName() {
        return EntityType.WOLF.getDescription();
    }

    @Nullable
    @Override
    public LivingEntity getOwner() {
        return dead ? null : super.getOwner();
    }

    @Override
    public boolean canBeLeashed(@NotNull Player player) {
        return false;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("restrictLiveSpan", this.restrictLiveSpan);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
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
    public void die(@NotNull DamageSource cause) {
        this.dead = true;
        super.die(cause);
    }
}
