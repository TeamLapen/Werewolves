package de.teamlapen.werewolves.entities.werewolf;

import de.teamlapen.vampirism.api.entity.EntityClassType;
import de.teamlapen.vampirism.api.entity.actions.EntityActionTier;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.entities.WerewolfFormUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.UUID;

public abstract class BasicWerewolfEntity extends WerewolfBaseEntity {

    private final WerewolfFormUtil.Form werewolfForm;
    private WerewolfTransformable transformed;
    private int transformedDuration;

    public BasicWerewolfEntity(EntityType<? extends WerewolfBaseEntity> type, World world, WerewolfFormUtil.Form werewolfForm) {
        super(type, world, EntityClassType.getRandomClass(world.rand), EntityActionTier.Low);
        this.werewolfForm = werewolfForm;
    }

    @Override
    public WerewolfFormUtil.Form getForm() {
        return werewolfForm;
    }

    @Override
    public WerewolfTransformable transformBack2() {
        if (this.transformed == null) return this;
        ((Entity) this.transformed).copyLocationAndAnglesFrom(this);
        UUID uuid = ((Entity) this.transformed).getUniqueID();
        ((Entity) this.transformed).copyDataFromOld(this);
        ((Entity) this.transformed).setUniqueId(uuid);
        ((Entity) this.transformed).revive();
        this.world.addEntity((Entity) this.transformed);
        this.remove();
        ((LivingEntity) transformed).setHealth(this.getHealth() / this.getMaxHealth() * ((LivingEntity) transformed).getMaxHealth());
        return this.transformed;
    }

    @Override
    public void start() {
        this.transformedDuration = WerewolvesConfig.BALANCE.MOBPROPS.werewolf_transform_duration.get();
    }


    @Override
    public WerewolfTransformable transformToWerewolf2() {
        return this;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (this.transformed != null && this.world.getGameTime() % 20 == 0) {
            if (--this.transformedDuration <= 0) {
                this.transformBack();
            }
        }
    }

    @Override
    public boolean canTransform() {
        return transformed != null;
    }

    @Override
    public void writeAdditional(CompoundNBT nbt) {
        super.writeAdditional(nbt);
        nbt.putInt("transformedDuration", this.transformedDuration);
    }

    @Override
    public void readAdditional(CompoundNBT nbt) {
        super.readAdditional(nbt);
        if (nbt.contains("transformedDuration")) {
            this.transformedDuration = nbt.getInt("transformedDuration");
        }
    }

    public void setSourceEntity(WerewolfTransformable entity) {
        this.setup(entity.getEntityClass(), entity.getEntityTier());
        this.transformed = entity;
        this.getDataManager().set(TYPE, entity.getEntityTextureType());
    }

    @Nonnull
    public WerewolfFormUtil.Form getWerewolfForm() {
        return werewolfForm;
    }

    public static class Beast extends BasicWerewolfEntity {
        public Beast(EntityType<? extends WerewolfBaseEntity> type, World world) {
            super(type, world, WerewolfFormUtil.Form.BEAST);
        }
    }

    public static class Survivalist extends BasicWerewolfEntity {
        public Survivalist(EntityType<? extends WerewolfBaseEntity> type, World world) {
            super(type, world, WerewolfFormUtil.Form.SURVIVALIST);
        }
    }
}
