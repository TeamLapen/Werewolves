package de.teamlapen.werewolves.mixin.entity;

import de.teamlapen.vampirism.api.entity.EntityClassType;
import de.teamlapen.vampirism.api.entity.actions.EntityActionTier;
import de.teamlapen.werewolves.core.ModEntities;
import de.teamlapen.werewolves.entities.WerewolfFormUtil;
import de.teamlapen.werewolves.entities.werewolf.BasicWerewolfEntity;
import de.teamlapen.werewolves.entities.werewolf.IVillagerTransformable;
import de.teamlapen.werewolves.entities.werewolf.WerewolfTransformable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;

import javax.annotation.Nonnull;

@Mixin(VillagerEntity.class)
public abstract class MixinVillagerEntity extends AbstractVillagerEntity implements IVillagerTransformable {
    private static final Logger LOGGER = LogManager.getLogger();
    private boolean werewolf;
    private WerewolfFormUtil.Form form;
    private int texture;

    public MixinVillagerEntity(EntityType<? extends AbstractVillagerEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public WerewolfTransformable transformToWerewolf2() {
        EntityType<? extends BasicWerewolfEntity> type;
        if (this.form == WerewolfFormUtil.Form.BEAST) {
            type = ModEntities.werewolf_beast;
        } else {
            type = ModEntities.werewolf_survivalist;
        }
        BasicWerewolfEntity entity = type.create(this.world);
        entity.copyLocationAndAnglesFrom(this);
        entity.copyDataFromOld(this);
        entity.setSourceEntity(this);
        this.world.addEntity(entity);
        this.remove(true);
        return entity;
    }

    @Override
    public WerewolfTransformable transformBack2() {
        return this;
    }

    @Override
    public boolean canTransform() {
        return this.werewolf;
    }

    @Nonnull
    @Override
    public WerewolfFormUtil.Form getWerewolfForm() {
        return this.form;
    }

    @Override
    public EntityClassType getEntityClass() {
        return null;
    }

    @Override
    public EntityActionTier getEntityTier() {
        return null;
    }

    @Override
    public int getEntityTextureType() {
        return texture;
    }

    @Override
    public void setWerewolfFaction(boolean werewolf) {
        this.werewolf = werewolf;
        this.form = this.getRNG().nextBoolean() ? WerewolfFormUtil.Form.SURVIVALIST : WerewolfFormUtil.Form.BEAST;
    }
}
