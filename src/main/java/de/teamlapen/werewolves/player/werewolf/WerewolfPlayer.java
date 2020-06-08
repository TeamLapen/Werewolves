package de.teamlapen.werewolves.player.werewolf;

import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.api.entity.player.actions.IActionHandler;
import de.teamlapen.vampirism.api.entity.player.skills.ISkillHandler;
import de.teamlapen.vampirism.player.VampirismPlayer;
import de.teamlapen.werewolves.api.entity.player.IWerewolfPlayer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Predicate;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

public class WerewolfPlayer extends VampirismPlayer<IWerewolfPlayer> implements IWerewolfPlayer {
    private static final Logger LOGGER = LogManager.getLogger();

    @CapabilityInject(IWerewolfPlayer.class)
    public static Capability<IWerewolfPlayer> CAP = getNull();

    public static WerewolfPlayer get(@Nonnull PlayerEntity playerEntity) {
        return (WerewolfPlayer)playerEntity.getCapability(CAP).orElseThrow(() -> new IllegalStateException("Cannot get werewolf player capability from player" + playerEntity));
    }

    public static LazyOptional<WerewolfPlayer> getOpt(@Nonnull PlayerEntity playerEntity) {
        LazyOptional<WerewolfPlayer> opt = playerEntity.getCapability(CAP).cast();
        if(!opt.isPresent()) {
            LOGGER.warn("Cannot get Werewolf player capability. This might break mod functionality.", new Throwable().fillInStackTrace());
        }
        return opt;
    }

    public static void registerCapability() {
        CapabilityManager.INSTANCE.register(IWerewolfPlayer.class, new Storage(), WerewolfPlayerDefaultImpl::new);
    }

    public WerewolfPlayer(PlayerEntity player) {
        super(player);
    }

    @Override
    protected VampirismPlayer<?> copyFromPlayer(PlayerEntity playerEntity) {
        return null;
    }

    @Override
    public void onChangedDimension(DimensionType dimensionType, DimensionType dimensionType1) {

    }

    @Override
    public void onDeath(DamageSource damageSource) {

    }

    @Override
    public boolean onEntityAttacked(DamageSource damageSource, float v) {
        return false;
    }

    @Override
    public void onJoinWorld() {

    }

    @Override
    public void onPlayerLoggedIn() {

    }

    @Override
    public void onPlayerLoggedOut() {

    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onUpdatePlayer(TickEvent.Phase phase) {

    }

    @Override
    public ResourceLocation getCapKey() {
        return null;
    }

    @Override
    public boolean canLeaveFaction() {
        return false;
    }

    @Nullable
    @Override
    public IFaction<?> getDisguisedAs() {
        return null;
    }

    @Override
    public IPlayableFaction<IWerewolfPlayer> getFaction() {
        return null;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public Predicate<LivingEntity> getNonFriendlySelector(boolean b, boolean b1) {
        return null;
    }

    @Override
    public boolean isDisguised() {
        return false;
    }

    @Override
    public void onLevelChanged(int i, int i1) {

    }

    @Override
    public ISkillHandler<IWerewolfPlayer> getSkillHandler() {
        return null;
    }

    @Override
    public IActionHandler<IWerewolfPlayer> getActionHandler() {
        return null;
    }

    public void saveData(CompoundNBT compound){
    }

    public void loadData(CompoundNBT compound) {
    }



    private static class Storage implements Capability.IStorage<IWerewolfPlayer> {
        @Nullable
        @Override
        public INBT writeNBT(Capability<IWerewolfPlayer> capability, IWerewolfPlayer instance, Direction side) {
            CompoundNBT compound = new CompoundNBT();
            ((WerewolfPlayer)instance).saveData(compound);
            return compound;
        }

        @Override
        public void readNBT(Capability<IWerewolfPlayer> capability, IWerewolfPlayer instance, Direction side, INBT compound) {
            ((WerewolfPlayer)instance).loadData((CompoundNBT)compound);
        }
    }
}
