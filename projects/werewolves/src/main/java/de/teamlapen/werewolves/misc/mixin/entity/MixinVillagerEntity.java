package de.teamlapen.werewolves.misc.mixin.entity;

import de.teamlapen.werewolves.api.entities.werewolf.IVillagerTransformable;
import de.teamlapen.werewolves.api.entities.werewolf.TransformType;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfTransformable;
import de.teamlapen.werewolves.common.core.ModEntities;
import de.teamlapen.werewolves.common.world.attachments.WerewolfVillagerAttachment;
import de.teamlapen.werewolves.common.world.entities.werewolf.BasicWerewolfEntity;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mixin(Villager.class)
public abstract class MixinVillagerEntity extends AbstractVillager implements IVillagerTransformable {

    @Deprecated
    private MixinVillagerEntity(EntityType<? extends AbstractVillager> type, Level worldIn) {
        super(type, worldIn);
    }

    @Unique
    private WerewolfVillagerAttachment werewolves$data() {
        return WerewolfVillagerAttachment.get((Villager) (Object) this);
    }

    @Override
    public BasicWerewolfEntity _transformToWerewolf() {
        WerewolfForm form = getForm();
        EntityType<? extends BasicWerewolfEntity> type = form == WerewolfForm.BEAST ? ModEntities.WEREWOLF_BEAST.get() : ModEntities.WEREWOLF_SURVIVALIST.get();
        BasicWerewolfEntity entity = WerewolfTransformable.copyData(type, this);
        entity.setSkinType(form, getSkinType(form));
        entity.setSourceEntity(this);
        return entity;
    }

    @Override
    public WerewolfTransformable _transformBack() {
        return this;
    }

    @Override
    public boolean canTransform() {
        return werewolves$isWerewolf() && werewolves$data().getRage() > this.getMaxHealth() * 4;
    }

    @Unique
    private boolean werewolves$isWerewolf() {
        //noinspection ConstantValue
        return WerewolfVillagerAttachment.getOpt((Villager) (Object) this).map(WerewolfVillagerAttachment::isWerewolf).orElse(false); // TODO && !((Object) this instanceof IVampire || ExtendedCreature.getSafe(this).map(IExtendedCreatureVampirism::hasPoisonousBlood).orElse(false));
    }

    @Override
    public boolean hurtServer(@Nonnull ServerLevel level, @Nonnull DamageSource source, float amount) {
        if (super.hurtServer(level, source, amount)) {
            if (werewolves$isWerewolf()) {
                werewolves$data().addRage((int) (amount * 10));
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide() && this.werewolves$isWerewolf()) {
            WerewolfVillagerAttachment data = werewolves$data();
            if (data.getRage() > 150) {
                WerewolfTransformable werewolf = this.transformToWerewolf(TransformType.TIME_LIMITED);
                ((Mob) werewolf).setLastHurtByMob(this.getTarget());
            }
            if (this.level().getGameTime() % 400 == 10) {
                if (Helper.isFullMoon(this.level(), blockPosition())) {
                    this.transformToWerewolf(TransformType.FULL_MOON);
                }
                if (data.getRage() > 10) {
                    data.addRage(-10);
                }
            }
        }
    }

    @Override
    public void reset() {
        WerewolfVillagerAttachment.getOpt((Villager) (Object) this).ifPresent(WerewolfVillagerAttachment::resetRage);
    }

    @Nonnull
    @Override
    public WerewolfForm getForm() {
        return WerewolfVillagerAttachment.getOpt((Villager) (Object) this).map(WerewolfVillagerAttachment::getForm).orElse(WerewolfForm.BEAST);
    }

    @Override
    public int getSkinType(@Nullable WerewolfForm form) {
        return werewolves$data().getOrCreateSkinType(this.getRandom());
    }

    @Override
    public int getEyeType(@Nullable WerewolfForm form) {
        return 0;
    }

    @Override
    public void setSkinType(@Nullable WerewolfForm form, int skinType) {
        werewolves$data().setSkinType(skinType);
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
        if (werewolf) {
            werewolves$data().setWerewolf(true, this.getRandom());
        } else {
            WerewolfVillagerAttachment.getOpt((Villager) (Object) this).ifPresent(data -> data.setWerewolf(false, this.getRandom()));
        }
    }
}
