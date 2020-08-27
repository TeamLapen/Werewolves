package de.teamlapen.werewolves.entities;

import de.teamlapen.lib.lib.network.ISyncable;
import de.teamlapen.werewolves.entities.werewolf.TransformedWerewolfEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

public class ExtendedWerewolf<T extends AbstractVillagerEntity> implements ISyncable.ISyncableEntityCapabilityInst, IExtendedWerewolf {

    @CapabilityInject(IExtendedWerewolf.class)
    public static Capability<IExtendedWerewolf> CAP = getNull();
    private final T villager;
    private boolean hasWerewolfFaction;

    public ExtendedWerewolf(T villager) {
        this.villager = villager;
    }

    public static LazyOptional<IExtendedWerewolf> getSafe(AbstractVillagerEntity mob) {
        return mob.getCapability(CAP);
    }

    public static void registerCapability() {
        CapabilityManager.INSTANCE.register(IExtendedWerewolf.class, new Storage(), ExtendedWerewolfDefaultImpl::new);
    }

    static <Q extends AbstractVillagerEntity> ICapabilityProvider createNewCapability(final Q creature) {
        return new ICapabilitySerializable<CompoundNBT>() {

            final IExtendedWerewolf inst = new ExtendedWerewolf<>(creature);
            final LazyOptional<IExtendedWerewolf> opt = LazyOptional.of(() -> inst);

            @Override
            public void deserializeNBT(CompoundNBT nbt) {
                CAP.getStorage().readNBT(CAP, inst, null, nbt);
            }

            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, Direction facing) {
                return CAP.orEmpty(capability, opt);
            }

            @Override
            public CompoundNBT serializeNBT() {
                return (CompoundNBT) CAP.getStorage().writeNBT(CAP, inst, null);
            }
        };
    }

    @Override
    public ResourceLocation getCapKey() {
        return REFERENCE.EXTENDED_WEREWOLF_KEY;
    }

    @Override
    public int getTheEntityID() {
        return this.villager.getEntityId();
    }

    public T getVillager() {
        return villager;
    }

    public void setWerewolfFaction(boolean werewolf) {
        this.hasWerewolfFaction = werewolf;
    }

    public boolean isWerewolf() {
        return hasWerewolfFaction;
    }

    public boolean canBecomeWerewolf() {
        return hasWerewolfFaction;
    }

    public void makeWerewolf() {
        if (canBecomeWerewolf()) {
            TransformedWerewolfEntity werewolf = TransformedWerewolfEntity.createFromVillager(this.villager);
            this.villager.remove(true);
            this.villager.world.addEntity(werewolf);
        }
    }

    @Override
    public void loadUpdateFromNBT(CompoundNBT compoundNBT) {
        hasWerewolfFaction = compoundNBT.getBoolean("werewolf");
    }

    @Override
    public void writeFullUpdateToNBT(CompoundNBT compoundNBT) {
        compoundNBT.putBoolean("werewolf", hasWerewolfFaction);
    }

    public void saveData(CompoundNBT compound) {
        compound.putBoolean("werewolf", hasWerewolfFaction);

    }

    public void loadData(CompoundNBT compound) {
        hasWerewolfFaction = compound.getBoolean("werewolf");
    }

    private static class Storage implements Capability.IStorage<IExtendedWerewolf> {
        @Nullable
        @Override
        public INBT writeNBT(Capability<IExtendedWerewolf> capability, IExtendedWerewolf instance, Direction side) {
            CompoundNBT compound = new CompoundNBT();
            ((ExtendedWerewolf<?>) instance).saveData(compound);
            return compound;
        }

        @Override
        public void readNBT(Capability<IExtendedWerewolf> capability, IExtendedWerewolf instance, Direction side, INBT compound) {
            ((ExtendedWerewolf<?>) instance).loadData((CompoundNBT) compound);
        }
    }
}
