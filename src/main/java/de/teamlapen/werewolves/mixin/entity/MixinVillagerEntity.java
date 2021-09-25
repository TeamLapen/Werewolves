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
import de.teamlapen.werewolves.util.WerewolfForm;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nonnull;

@Mixin(VillagerEntity.class)
public abstract class MixinVillagerEntity extends AbstractVillagerEntity implements IVillagerTransformable {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final DataParameter<Integer> TYPE = EntityDataManager.defineId(VillagerEntity.class, DataSerializers.INT);

    private boolean werewolf;
    private WerewolfForm form;
    private final EntityActionTier entitytier = EntityActionTier.Medium;
    private EntityClassType entityclass;

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
        boolean can = this instanceof IVampire || ExtendedCreature.getSafe(this).map(IExtendedCreatureVampirism::hasPoisonousBlood).orElse(false);
        return !can && this.werewolf;
    }

    @Nonnull
    @Override
    public WerewolfForm getForm() {
        return this.form;
    }

    @Override
    public EntityClassType getEntityClass() {
        return this.entityclass == null ? this.entityclass = EntityClassType.getRandomClass(this.getRandom()) : this.entityclass;
    }

    @Override
    public EntityActionTier getEntityTier() {
        return this.entitytier;
    }

    @Override
    public int getSkinType() {
        int i = getEntityData().get(TYPE);
        return Math.max(i, 0);
    }

    @Override
    public void setWerewolfFaction(boolean werewolf) {
        this.werewolf = werewolf;
        this.form = this.getRandom().nextBoolean() ? WerewolfForm.SURVIVALIST : WerewolfForm.BEAST;
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        if (this.getEntityData().get(TYPE) == -1) {
            this.getEntityData().set(TYPE, this.getRandom().nextInt(TYPES));
        }
    }

    @Inject(method = "defineSynchedData", at = @At("RETURN"))
    protected void defineSynchedData(CallbackInfo ci) {
        this.getEntityData().define(TYPE, -1);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("RETURN"))
    public void addAdditionalSaveData(CompoundNBT compound, CallbackInfo ci) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putBoolean("werewolf", this.werewolf);
        if (form != null) {
            nbt.putString("form", this.form.getName());
        }
        nbt.putInt("type", this.getSkinType());
        if (this.entityclass != null) {
            nbt.putInt("entityclasstype", EntityClassType.getID(this.entityclass));
        }
        compound.put("werewolves", nbt);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("RETURN"))
    public void readAdditionalSaveData(CompoundNBT compound, CallbackInfo ci) {
        CompoundNBT nbt = compound.getCompound("werewolves");
        this.werewolf = nbt.getBoolean("werewolf");
        if (compound.contains("form")) {
            this.form = WerewolfForm.getForm(nbt.getString("form"));
        }
        if (nbt.contains("type")) {
            int t = nbt.getInt("type");
            this.getEntityData().set(TYPE, t < TYPES && t >= 0 ? t : -1);
        }
        if (nbt.contains("entityclasstype")) {
            this.entityclass = EntityClassType.getEntityClassType(nbt.getInt("entityclasstype"));
        }
    }
}
