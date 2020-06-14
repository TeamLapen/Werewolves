package de.teamlapen.werewolves.player.werewolf;

import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.api.entity.player.actions.IActionHandler;
import de.teamlapen.vampirism.api.entity.player.skills.ISkillHandler;
import de.teamlapen.vampirism.config.VampirismConfig;
import de.teamlapen.vampirism.player.LevelAttributeModifier;
import de.teamlapen.vampirism.player.VampirismPlayer;
import de.teamlapen.vampirism.player.actions.ActionHandler;
import de.teamlapen.vampirism.player.skills.SkillHandler;
import de.teamlapen.vampirism.util.ScoreboardUtil;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entity.player.IWerewolfPlayer;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.WerewolfActions;
import de.teamlapen.werewolves.player.werewolf.actions.WerewolfAction;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Predicate;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

public class WerewolfPlayer extends VampirismPlayer<IWerewolfPlayer> implements IWerewolfPlayer {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final UUID ARMOR_TOUGHNESS = UUID.fromString("f3979aec-b8ef-4e95-84a7-2c6dab8ea46e");

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

    @SuppressWarnings("deprecation")
    public static void registerCapability() {
        CapabilityManager.INSTANCE.register(IWerewolfPlayer.class, new Storage(), WerewolfPlayerDefaultImpl::new);
    }

    public static ICapabilityProvider createNewCapability(final PlayerEntity playerEntity) {
        return new ICapabilitySerializable<CompoundNBT>() {

            final IWerewolfPlayer inst = new WerewolfPlayer(playerEntity);
            final LazyOptional<IWerewolfPlayer> opt = LazyOptional.of(()->inst);
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                return CAP.orEmpty(cap,opt);
            }

            @Override
            public CompoundNBT serializeNBT() {
                return (CompoundNBT)CAP.getStorage().writeNBT(CAP,inst,null);
            }

            @Override
            public void deserializeNBT(CompoundNBT nbt) {
                CAP.getStorage().readNBT(CAP,inst,null,nbt);
            }
        };
    }

    private final ActionHandler<IWerewolfPlayer> actionHandler;
    private final SkillHandler<IWerewolfPlayer> skillHandler;
    private final WerewolfPlayerSpecialAttributes specialAttributes = new WerewolfPlayerSpecialAttributes();
    private int killedAnimal = 0;

    public WerewolfPlayer(PlayerEntity player) {
        super(player);
        this.applyEntityAttributes();
        this.actionHandler = new ActionHandler<>(this);
        this.skillHandler = new SkillHandler<>(this, WReference.WEREWOLF_FACTION);
    }

    @Override
    protected VampirismPlayer<?> copyFromPlayer(PlayerEntity playerEntity) {
        WerewolfPlayer oldWerewolf = get(playerEntity);
        CompoundNBT nbt = new CompoundNBT();
        oldWerewolf.saveData(nbt);
        this.loadData(nbt);
        return oldWerewolf;
    }

    private void applyEntityAttributes() {
        if (this.player.getAttributes().getAttributeInstance(WReference.biteDamage) == null) {
            this.player.getAttributes().registerAttribute(WReference.biteDamage);
        }
    }

    @Override
    public void onChangedDimension(DimensionType dimensionType, DimensionType dimensionType1) {

    }

    @Nonnull
    public WerewolfPlayerSpecialAttributes getSpecialAttributes() {
        return specialAttributes;
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        this.actionHandler.deactivateAllActions();
    }

    @Override
    public boolean onEntityAttacked(DamageSource damageSource, float v) {
        return false;
    }

    @Override
    public void onJoinWorld() {
        if(this.getLevel() > 0) {
            this.actionHandler.onActionsReactivated();
        }
    }

    @Override
    public void onPlayerLoggedIn() {

    }

    @Override
    public void onPlayerLoggedOut() {

    }

    @Override
    public void onUpdate() {
        this.player.getEntityWorld().getProfiler().startSection("werewolves_werewolfplayer");
        if(!isRemote()) {
            if(getLevel() > 0) {
                boolean sync = false;
                boolean syncToAll = false;
                CompoundNBT syncPacket = new CompoundNBT();

                if(this.actionHandler.updateActions()) {
                    sync = true;
                    syncToAll = true;
                    this.actionHandler.writeUpdateForClient(syncPacket);
                }
                if(this.skillHandler.isDirty()) {
                    sync = true;
                    skillHandler.writeUpdateForClient(syncPacket);
                }
                if(sync) {
                    sync(syncPacket, syncToAll);
                }
            }
        }else {
            if(getLevel() > 0) {
                this.actionHandler.updateActions();
            }
        }
        this.player.getEntityWorld().getProfiler().endSection();
    }

    @Override
    public void onUpdatePlayer(TickEvent.Phase phase) {

    }

    public boolean canBite(Entity entity) {
        return !this.player.isSpectator() && this.getLevel() > 0 && this.actionHandler.isActionActive(WerewolfActions.werewolf_form);
    }

    /**
     * Bite the entity with the given id.
     * Checks reach distance
     *
     * @param entityId
     *            The id of the entity to start biting
     */
    public void biteEntity(int entityId) {
        Entity e = this.player.getEntityWorld().getEntityByID(entityId);
        if (this.player.isSpectator()) {
            LOGGER.warn("Player can't bite in spectator mode");
            return;
        }
        if (e instanceof LivingEntity) {
            if (e.getDistance(this.player) <= this.player.getAttribute(PlayerEntity.REACH_DISTANCE).getValue() + 1) {
                this.biteAttack((LivingEntity) e);
            }
        }
    }

    /**
     * only execute in werewolf form
     *
     * @param entity The entity to attack
     */
    private void biteAttack(LivingEntity entity) {
        float damage = (float) this.player.getAttribute(WReference.biteDamage).getValue();
        entity.attackEntityFrom(DamageSource.causePlayerDamage(this.player), damage);
        if (!entity.isEntityUndead()) {
            this.eatFleshFrom(entity);
        }
        if (!entity.isAlive()) {
            this.killedAnimal++;
        }
    }

    /**
     * feeds it self from bitten entities
     *
     * @param entity
     *            The bitten entity
     */
    private void eatFleshFrom(LivingEntity entity) {
        if (this.getSpecialAttributes().eatFlesh) {
            int i = WerewolvesConfig.BALANCE.werewolf_form_duration.get() * 20;
            this.getActionHandler().extendAction(WerewolfActions.werewolf_form, i / 4);
        }
    }

    @Override
    public ResourceLocation getCapKey() {
        return REFERENCE.WEREWOLF_PLAYER_KEY;
    }

    @Override
    public boolean canLeaveFaction() {
        return true;
    }

    @Nullable
    @Override
    public IFaction<?> getDisguisedAs() {
        if(this.getSpecialAttributes().trueForm){
            return WReference.WEREWOLF_FACTION;
        }
        return null;
    }

    @Override
    public IPlayableFaction<IWerewolfPlayer> getFaction() {
        return WReference.WEREWOLF_FACTION;
    }

    @Override
    public int getMaxLevel() {
        return REFERENCE.HIGHEST_WEREWOLF_LEVEL;
    }

    @Override
    public Predicate<LivingEntity> getNonFriendlySelector(boolean otherFactionPlayers, boolean ignoreDisguise) {
        if (otherFactionPlayers) {
            return entity -> true;
        } else {
            return VampirismAPI.factionRegistry().getPredicate(getFaction(), ignoreDisguise);
        }
    }

    public void activateWerewolfForm() {
        this.specialAttributes.trueForm = true;
    }

    public void deactivateWerewolfForm() {
        this.specialAttributes.trueForm = false;
    }

    @Override
    public boolean isDisguised() {
        return false;
    }

    @Override
    public void onLevelChanged(int newLevel, int oldLevel) {
        if(!isRemote()) {
            ScoreboardUtil.updateScoreboard(this.player, WUtils.WEREWOLF_LEVEL_CRITERIA, newLevel);
            LevelAttributeModifier.applyModifier(player, SharedMonsterAttributes.MOVEMENT_SPEED, "Werewolf", getLevel(), getMaxLevel(), WerewolvesConfig.BALANCE.werewolf_speed_amount.get(), 0.3, AttributeModifier.Operation.MULTIPLY_TOTAL, false);
            LevelAttributeModifier.applyModifier(player, SharedMonsterAttributes.ARMOR_TOUGHNESS, "Werewolf", getLevel(), getMaxLevel(), WerewolvesConfig.BALANCE.werewolf_speed_amount.get(), 0.5, AttributeModifier.Operation.MULTIPLY_TOTAL, false);
            LevelAttributeModifier.applyModifier(player, SharedMonsterAttributes.ATTACK_DAMAGE, "Werewolf", getLevel(), getMaxLevel(), WerewolvesConfig.BALANCE.werewolf_damage.get(), 0.5, AttributeModifier.Operation.ADDITION, false);
            if(newLevel > 0){
                if(oldLevel == 0) {
                    this.skillHandler.enableRootSkill();
                }
            }else {
                this.actionHandler.resetTimers();
                this.skillHandler.disableAllSkills();
            }
        }else {
            if(newLevel == 0){
                this.actionHandler.resetTimers();
            }
        }
    }

    @Override
    public ISkillHandler<IWerewolfPlayer> getSkillHandler() {
        return this.skillHandler;
    }

    @Override
    public IActionHandler<IWerewolfPlayer> getActionHandler() {
        return this.actionHandler;
    }

    public void saveData(CompoundNBT compound) {
        this.actionHandler.saveToNbt(compound);
        this.skillHandler.saveToNbt(compound);
    }

    public void loadData(CompoundNBT compound) {
        this.actionHandler.loadFromNbt(compound);
        this.skillHandler.loadFromNbt(compound);
    }

    @Override
    protected void writeFullUpdate(CompoundNBT nbt) {
        this.actionHandler.writeUpdateForClient(nbt);
        this.skillHandler.writeUpdateForClient(nbt);
    }

    @Override
    protected void loadUpdate(CompoundNBT nbt) {
        this.actionHandler.readUpdateFromServer(nbt);
        this.skillHandler.readUpdateFromServer(nbt);
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

    static {
        LevelAttributeModifier.registerModdedAttributeModifier(SharedMonsterAttributes.ARMOR_TOUGHNESS,ARMOR_TOUGHNESS);
    }
}
