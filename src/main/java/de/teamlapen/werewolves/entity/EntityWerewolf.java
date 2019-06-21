package de.teamlapen.werewolves.entity;

import de.teamlapen.vampirism.api.entity.IVillageCaptureEntity;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.world.IVampirismVillage;
import de.teamlapen.werewolves.api.WReference;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityWerewolf extends EntityWerewolfBase implements IVillageCaptureEntity {

    private AxisAlignedBB area;
    private boolean attacking;
    protected @Nonnull
    EntityLivingBase sourceEntity;
    protected @Nullable
    IVampirismVillage cachedVillage;

    public static EntityWerewolf makeWerewolf(EntityLivingBase entity) {
        EntityWerewolf werewolf = new EntityWerewolf(entity.world);
        NBTTagCompound nbt = new NBTTagCompound();
        entity.writeToNBT(nbt);
        werewolf.readFromNBT(nbt);
        werewolf.setUniqueId(MathHelper.getRandomUUID(entity.getRNG()));
        werewolf.setSourceEntity(entity);
        entity.setDead();
        return werewolf;
    }

    protected EntityWerewolf(World world) {
        super(world);
    }

    protected void setSourceEntity(EntityLivingBase entity) {
        this.sourceEntity = entity;
    }

    @Override
    public void attackVillage(AxisAlignedBB area) {
        this.area = area;
        attacking = true;
    }

    @Override
    public void defendVillage(AxisAlignedBB area) {
        this.area = area;
        attacking = false;
    }

    @Nullable
    @Override
    public AxisAlignedBB getTargetVillageArea() {
        return area;
    }

    @Override
    public boolean isAttackingVillage() {
        return attacking;
    }

    @Override
    public void stopVillageAttackDefense() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        sourceEntity.readFromNBT(nbt);
        this.setDead();
        sourceEntity.isDead = true;
    }

    @Nullable
    @Override
    public IVampirismVillage getCurrentFriendlyVillage() {
        return this.cachedVillage != null ? this.cachedVillage.getControllingFaction() == WReference.WEREWOLF_FACTION ? this.cachedVillage : null : null;
    }

    @Override
    public IFaction getFaction() {
        return WReference.WEREWOLF_FACTION;
    }

    @Override
    public EntityLivingBase getRepresentingEntity() {
        return this;
    }
}
