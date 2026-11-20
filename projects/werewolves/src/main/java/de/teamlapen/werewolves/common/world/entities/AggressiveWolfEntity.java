package de.teamlapen.werewolves.common.world.entities;

import de.teamlapen.faction.common.util.DamageHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class AggressiveWolfEntity extends Wolf {

    private boolean restrictLiveSpan;
    private boolean dead;
    private int maxTicks;

    public AggressiveWolfEntity(EntityType<? extends AggressiveWolfEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public void restrictLiveSpan(int ticks) {
        this.restrictLiveSpan = true;
        this.maxTicks = ticks;
    }

    @Override
    public boolean canMate(Animal entity) {
        return false;
    }

    @Override
    public void setOrderedToSit(boolean p_233687_1_) {
    }

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
    public boolean canBeLeashed() {
        return false;
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("restrictLiveSpan", this.restrictLiveSpan);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput compound) {
        super.readAdditionalSaveData(compound);
        this.restrictLiveSpan = compound.getBooleanOr("restrictLiveSpan", false);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.restrictLiveSpan) {
            if (this.tickCount > maxTicks && this.level() instanceof ServerLevel serverLevel) {
                DamageHandler.hurtVanilla(serverLevel, this, DamageSources::magic, 10);
            }
        }
    }

    @Override
    public void die(DamageSource cause) {
        this.dead = true;
        super.die(cause);
    }
}
