package de.teamlapen.werewolves.entity;

import de.teamlapen.vampirism.api.entity.IVillageCaptureEntity;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.world.IVampirismVillage;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityWerewolfVillager extends EntityVillager implements IVillageCaptureEntity {
    public EntityWerewolfVillager(World worldIn) {
        super(worldIn);
    }

    @Override
    public void attackVillage(AxisAlignedBB area) {

    }

    @Override
    public void defendVillage(AxisAlignedBB area) {

    }

    @Nullable
    @Override
    public AxisAlignedBB getTargetVillageArea() {
        return null;
    }

    @Override
    public boolean isAttackingVillage() {
        return false;
    }

    @Override
    public void stopVillageAttackDefense() {

    }

    @Nullable
    @Override
    public IVampirismVillage getCurrentFriendlyVillage() {
        return null;
    }

    @Override
    public IFaction getFaction() {
        return null;
    }

    @Override
    public EntityLivingBase getRepresentingEntity() {
        return null;
    }
}
