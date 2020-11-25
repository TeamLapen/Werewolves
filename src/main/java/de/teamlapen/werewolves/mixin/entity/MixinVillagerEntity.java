package de.teamlapen.werewolves.mixin.entity;

import de.teamlapen.vampirism.api.entity.EntityClassType;
import de.teamlapen.vampirism.api.entity.IExtendedCreatureVampirism;
import de.teamlapen.vampirism.api.entity.actions.EntityActionTier;
import de.teamlapen.vampirism.api.entity.vampire.IVampire;
import de.teamlapen.vampirism.entity.ExtendedCreature;
import de.teamlapen.werewolves.core.ModEntities;
import de.teamlapen.werewolves.entities.WerewolfFormUtil;
import de.teamlapen.werewolves.entities.werewolf.BasicWerewolfEntity;
import de.teamlapen.werewolves.entities.werewolf.IVillagerTransformable;
import de.teamlapen.werewolves.entities.werewolf.WerewolfTransformable;
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
    private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(VillagerEntity.class, DataSerializers.VARINT);

    private boolean werewolf;
    private WerewolfFormUtil.Form form;
    private final EntityActionTier entitytier = EntityActionTier.Medium;
    private EntityClassType entityclass;

    @Deprecated
    public MixinVillagerEntity(EntityType<? extends AbstractVillagerEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public BasicWerewolfEntity _transformToWerewolf() {
        EntityType<? extends BasicWerewolfEntity> type;
        if (this.form == WerewolfFormUtil.Form.BEAST) {
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
    public WerewolfFormUtil.Form getWerewolfForm() {
        return this.form;
    }

    @Override
    public EntityClassType getEntityClass() {
        return this.entityclass == null ? this.entityclass = EntityClassType.getRandomClass(this.getRNG()) : this.entityclass;
    }

    @Override
    public EntityActionTier getEntityTier() {
        return this.entitytier;
    }

    @Override
    public int getEntityTextureType() {
        int i = getDataManager().get(TYPE);
        return Math.max(i, 0);
    }

    @Override
    public void setWerewolfFaction(boolean werewolf) {
        this.werewolf = werewolf;
        this.form = this.getRNG().nextBoolean() ? WerewolfFormUtil.Form.SURVIVALIST : WerewolfFormUtil.Form.BEAST;
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        if (this.getDataManager().get(TYPE) == -1) {
            this.getDataManager().set(TYPE, this.getRNG().nextInt(TYPES));
        }
    }

    @Inject(method = "registerData", at = @At("RETURN"))
    protected void registerData(CallbackInfo ci) {
        this.getDataManager().register(TYPE, -1);
    }

    @Inject(method = "readAdditional", at = @At("RETURN"))
    public void writeAdditional(CompoundNBT compound, CallbackInfo ci) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putBoolean("werewolf", this.werewolf);
        if (form != null) {
            nbt.putString("form", this.form.toString());
        }
        nbt.putInt("type", this.getEntityTextureType());
        if (this.entityclass != null) {
            nbt.putInt("entityclasstype", EntityClassType.getID(this.entityclass));
        }
        compound.put("werewolves", nbt);
    }

    @Inject(method = "readAdditional", at = @At("RETURN"))
    public void readAdditional(CompoundNBT compound, CallbackInfo ci) {
        CompoundNBT nbt = compound.getCompound("werewolves");
        this.werewolf = nbt.getBoolean("werewolf");
        if (compound.contains("form")) {
            this.form = WerewolfFormUtil.Form.valueOf(nbt.getString("form"));
        }
        if (nbt.contains("type")) {
            int t = nbt.getInt("type");
            this.getDataManager().set(TYPE, t < TYPES && t >= 0 ? t : -1);
        }
        if (nbt.contains("entityclasstype")) {
            this.entityclass = EntityClassType.getEntityClassType(nbt.getInt("entityclasstype"));
        }
    }
}
