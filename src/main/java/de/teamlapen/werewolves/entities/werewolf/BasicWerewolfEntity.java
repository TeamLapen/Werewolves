package de.teamlapen.werewolves.entities.werewolf;

import de.teamlapen.vampirism.api.entity.EntityClassType;
import de.teamlapen.vampirism.api.entity.actions.EntityActionTier;
import de.teamlapen.werewolves.entities.WerewolfFormUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public abstract class BasicWerewolfEntity extends WerewolfBaseEntity {

    private final WerewolfFormUtil.Form werewolfForm;
    private WerewolfTransformable transformed;

    public BasicWerewolfEntity(EntityType<? extends WerewolfBaseEntity> type, World world, WerewolfFormUtil.Form werewolfForm) {
        super(type, world, EntityClassType.getRandomClass(world.rand), EntityActionTier.Low);
        this.werewolfForm = werewolfForm;
    }

    @Override
    public WerewolfFormUtil.Form getForm() {
        return werewolfForm;
    }

    @Override
    public WerewolfTransformable transformBack() {
        ((Entity) this.transformed).copyLocationAndAnglesFrom(this);
        ((Entity) this.transformed).revive();
        this.world.addEntity((Entity) this.transformed);
        this.remove();
        return this.transformed;
    }

    @Override
    public WerewolfTransformable transformToWerewolf() {
        return this;
    }

    @Override
    public boolean canTransform() {
        return transformed != null;
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

        @Override
        public boolean canTransform() {
            return false;
        }
    }

    public static class Survivalist extends BasicWerewolfEntity {
        public Survivalist(EntityType<? extends WerewolfBaseEntity> type, World world) {
            super(type, world, WerewolfFormUtil.Form.SURVIVALIST);
        }

        @Override
        public boolean canTransform() {
            return false;
        }
    }
}
