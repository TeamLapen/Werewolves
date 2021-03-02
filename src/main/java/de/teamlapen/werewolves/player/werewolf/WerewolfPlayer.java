package de.teamlapen.werewolves.player.werewolf;

import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.api.entity.player.actions.IAction;
import de.teamlapen.vampirism.api.entity.player.actions.IActionHandler;
import de.teamlapen.vampirism.api.entity.player.skills.ISkillHandler;
import de.teamlapen.vampirism.core.ModRegistries;
import de.teamlapen.vampirism.player.LevelAttributeModifier;
import de.teamlapen.vampirism.player.VampirismPlayer;
import de.teamlapen.vampirism.player.actions.ActionHandler;
import de.teamlapen.vampirism.player.skills.SkillHandler;
import de.teamlapen.vampirism.util.ScoreboardUtil;
import de.teamlapen.vampirism.util.SharedMonsterAttributes;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModAttributes;
import de.teamlapen.werewolves.core.ModEffects;
import de.teamlapen.werewolves.core.WerewolfSkills;
import de.teamlapen.werewolves.effects.WerewolfNightVisionEffect;
import de.teamlapen.werewolves.player.IWerewolfPlayer;
import de.teamlapen.werewolves.player.WerewolfForm;
import de.teamlapen.werewolves.player.werewolf.actions.WerewolfFormAction;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WReference;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

public class WerewolfPlayer extends VampirismPlayer<IWerewolfPlayer> implements IWerewolfPlayer {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final UUID ARMOR_TOUGHNESS = UUID.fromString("f3979aec-b8ef-4e95-84a7-2c6dab8ea46e");

    @CapabilityInject(IWerewolfPlayer.class)
    public static Capability<IWerewolfPlayer> CAP = getNull();

    private void applyEntityAttributes() {
        try {
            this.player.getAttribute(ModAttributes.bite_damage).setBaseValue(2.0);
        } catch (Exception e) {
            LOGGER.error(e);
        }
        this.player.getAttribute(ModAttributes.time_regain).setBaseValue(1.0);
    }

    public static WerewolfPlayer get(@Nonnull PlayerEntity playerEntity) {
        return (WerewolfPlayer) playerEntity.getCapability(CAP).orElseThrow(() -> new IllegalStateException("Cannot get werewolf player capability from player" + playerEntity));
    }

    public static LazyOptional<WerewolfPlayer> getOpt(@Nonnull PlayerEntity playerEntity) {
        LazyOptional<WerewolfPlayer> opt = playerEntity.getCapability(CAP).cast();
        if (!opt.isPresent()) {
            LOGGER.warn("Cannot get Werewolf player capability. This might break mod functionality.", new Throwable().fillInStackTrace());
        }
        return opt;
    }

    //-- player --------------------------------------------------------------------------------------------------------

    @Nonnull
    private final ActionHandler<IWerewolfPlayer> actionHandler;
    @Nonnull
    private final SkillHandler<IWerewolfPlayer> skillHandler;
    @Nonnull
    private final WerewolfPlayerSpecialAttributes specialAttributes = new WerewolfPlayerSpecialAttributes();
    @Nonnull
    private final NonNullList<ItemStack> armorItems = NonNullList.withSize(5, ItemStack.EMPTY);
    @Nonnull
    private WerewolfForm form = WerewolfForm.NONE;
    @Nullable
    private WerewolfFormAction lastFormAction;
    @Nonnull
    private final LevelHandler levelHandler = new LevelHandler(this);

    public WerewolfPlayer(@Nonnull PlayerEntity player) {
        super(player);
        this.actionHandler = new ActionHandler<>(this);
        this.skillHandler = new SkillHandler<>(this, WReference.WEREWOLF_FACTION);
    }

    @Nonnull
    public WerewolfForm getForm() {
        return this.form;
    }

    public void setForm(WerewolfFormAction action, WerewolfForm form) {
        switchForm(form);
        this.lastFormAction = action;
        if (!this.player.world.isRemote) {
            this.sync(Helper.nbtWith(nbt -> nbt.putString("form", this.form.getName())), true);
        }
    }

    public void switchForm(WerewolfForm form) {
        this.form = form;
        this.player.recalculateSize();
    }

    @Override
    protected VampirismPlayer<IWerewolfPlayer> copyFromPlayer(PlayerEntity playerEntity) {
        WerewolfPlayer oldWerewolf = get(playerEntity);
        CompoundNBT nbt = new CompoundNBT();
        oldWerewolf.saveData(nbt);
        this.loadData(nbt);
        return oldWerewolf;
    }

    @Override
    public void onChangedDimension(RegistryKey<World> registryKey, RegistryKey<World> registryKey1) {

    }

    @Nonnull
    public WerewolfPlayerSpecialAttributes getSpecialAttributes() {
        return specialAttributes;
    }

    @Override
    public void onUpdate() {
        this.player.getEntityWorld().getProfiler().startSection("werewolves_werewolfplayer");
        if (!isRemote()) {
            if (getLevel() > 0) {
                boolean sync = false;
                boolean syncToAll = false;
                CompoundNBT syncPacket = new CompoundNBT();
                if(this.player.world.getGameTime() % 10 == 0) {
                    if(this.specialAttributes.werewolfTime > 0 && !Helper.isFormActionActive(this)) {
                        this.specialAttributes.werewolfTime -= 10 * player.getAttribute(ModAttributes.time_regain).getValue();
                        --this.specialAttributes.werewolfTime;
                        sync = true;
                        syncPacket.putLong("werewolfTime", this.specialAttributes.werewolfTime);
                    }
                }
                if (this.actionHandler.updateActions()) {
                    sync = true;
                    syncToAll = true;
                    this.actionHandler.writeUpdateForClient(syncPacket);
                }
                if (this.skillHandler.isDirty()) {
                    sync = true;
                    skillHandler.writeUpdateForClient(syncPacket);
                }
                if (sync) {
                    sync(syncPacket, syncToAll);
                }
            }

            if (this.player.world.getGameTime() % 20 == 0 && Helper.isFullMoon(this.getRepresentingPlayer().getEntityWorld())) {
                if (!Helper.isFormActionActive(this) && !this.skillHandler.isSkillEnabled(WerewolfSkills.free_will)) {
                    Optional<? extends IAction> action = lastFormAction != null ? Optional.of(lastFormAction) : WerewolfFormAction.getAllAction().stream().filter(this.actionHandler::isActionUnlocked).findAny();
                    action.ifPresent(this.actionHandler::toggleAction);
                }
            }
        } else {
            if (getLevel() > 0) {
                if (this.player.world.getGameTime() % 10 == 0) {
                    if (this.specialAttributes.werewolfTime > 0 && !Helper.isFormActionActive(this)) {
                        this.specialAttributes.werewolfTime -= 10 * player.getAttribute(ModAttributes.time_regain).getValue();
                    }
                }
                this.actionHandler.updateActions();
            }
        }
        EffectInstance effect = this.player.getActivePotionEffect(Effects.NIGHT_VISION);
        if (this.getForm() != WerewolfForm.NONE && this.specialAttributes.night_vision) {
            if (!(effect instanceof WerewolfNightVisionEffect)) {
                player.removeActivePotionEffect(Effects.NIGHT_VISION);
                effect = null;
            }
            if (effect == null) {
                player.addPotionEffect(new WerewolfNightVisionEffect());
            }
        } else {
            if (effect instanceof WerewolfNightVisionEffect) {
                player.removeActivePotionEffect(Effects.NIGHT_VISION);
            }
        }


        this.player.getEntityWorld().getProfiler().endSection();
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        this.actionHandler.deactivateAllActions();
    }

    @Nonnull
    public LevelHandler getLevelHandler() {
        return levelHandler;
    }

    @Override
    public boolean onEntityAttacked(DamageSource damageSource, float v) {
        return false;
    }

    @Override
    public void onJoinWorld() {
        if (this.getLevel() > 0) {
            this.actionHandler.onActionsReactivated();
        }
    }

    @Override
    public void onPlayerLoggedIn() {

    }

    @Override
    public void onPlayerLoggedOut() {

    }

    /**
     * Bite the entity with the given id.
     * Checks reach distance
     *
     * @param entityId The id of the entity to start biting
     */
    public void biteEntity(int entityId) {
        Entity e = this.player.getEntityWorld().getEntityByID(entityId);
        if (this.player.isSpectator()) {
            LOGGER.warn("Player can't bite in spectator mode");
            return;
        }
        if (e instanceof LivingEntity) {
            if (e.getDistance(this.player) <= this.player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue() + 1) {
                this.biteAttack((LivingEntity) e);
            }
        }
    }

    @Override
    public void onUpdatePlayer(TickEvent.Phase phase) {

    }

    public boolean canBite(Entity entity) {
        return !this.player.isSpectator() && this.getLevel() > 0 && Helper.isFormActionActive(this);
    }

    /**
     * only execute in werewolf form
     *
     * @param entity The entity to attack
     */
    private void biteAttack(LivingEntity entity) {
        float damage = (float) this.player.getAttribute(ModAttributes.bite_damage).getValue();
        entity.attackEntityFrom(DamageSource.causePlayerDamage(this.player), damage);
        if (this.skillHandler.isSkillEnabled(WerewolfSkills.stun_bite)) {
            entity.addPotionEffect(new EffectInstance(ModEffects.freeze, WerewolvesConfig.BALANCE.SKILLS.stun_bite_duration.get()));
        } else if (this.skillHandler.isSkillEnabled(WerewolfSkills.bleeding_bite)) {
            entity.addPotionEffect(new EffectInstance(ModEffects.bleeding, WerewolvesConfig.BALANCE.SKILLS.bleeding_bite_duration.get()));
        }
        if (!entity.isEntityUndead()) {
            this.eatFleshFrom(entity);
        }
        if (!entity.isAlive()) {
        }
    }

    @Override
    public void onLevelChanged(int newLevel, int oldLevel) {
        this.applyEntityAttributes();
        if (!isRemote()) {
            ScoreboardUtil.updateScoreboard(this.player, WUtils.WEREWOLF_LEVEL_CRITERIA, newLevel);
            LevelAttributeModifier.applyModifier(player, SharedMonsterAttributes.MOVEMENT_SPEED, "Werewolf", getLevel(), getMaxLevel(), WerewolvesConfig.BALANCE.PLAYER.werewolf_speed_amount.get(), 0.3, AttributeModifier.Operation.MULTIPLY_TOTAL, false);
            LevelAttributeModifier.applyModifier(player, SharedMonsterAttributes.ARMOR_TOUGHNESS, "Werewolf", getLevel(), getMaxLevel(), WerewolvesConfig.BALANCE.PLAYER.werewolf_speed_amount.get(), 0.5, AttributeModifier.Operation.MULTIPLY_TOTAL, false);
            LevelAttributeModifier.applyModifier(player, SharedMonsterAttributes.ATTACK_DAMAGE, "Werewolf", getLevel(), getMaxLevel(), WerewolvesConfig.BALANCE.PLAYER.werewolf_damage.get(), 0.5, AttributeModifier.Operation.ADDITION, false);
            if (newLevel > 0) {
                if (oldLevel == 0) {
                    this.skillHandler.enableRootSkill();
                }
            } else {
                this.actionHandler.resetTimers();
                this.skillHandler.disableAllSkills();
            }
        } else {
            if (newLevel == 0) {
                this.actionHandler.resetTimers();
            }
        }
    }

    @Nonnull
    public NonNullList<ItemStack> getArmorItems() {
        return this.armorItems;
    }

    /**
     * feeds it self from bitten entities
     *
     * @param entity The bitten entity
     */
    private void eatFleshFrom(LivingEntity entity) {
        this.player.getFoodStats().addStats(1, 1);
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
        if (this.getForm() != WerewolfForm.NONE) {
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

    public void storeArmor() {
        if (!this.skillHandler.isSkillEnabled(WerewolfSkills.wear_armor)) {
            for (int i = 0; i < player.inventory.armorInventory.size(); i++) {
                this.armorItems.set(i, this.player.inventory.armorInventory.get(i));
                this.player.inventory.armorInventory.set(i, ItemStack.EMPTY);
            }
            this.sync(this.saveArmorItems(new CompoundNBT()), false);
        }
    }

    public void loadArmor() {
        for (int i = 0; i < this.armorItems.size() - 1; i++) {
            ItemStack stack = this.armorItems.get(i);
            if (!stack.isEmpty()) {
                this.player.inventory.armorInventory.set(i, stack);
                this.armorItems.set(i, ItemStack.EMPTY);
            }
        }
        this.sync(this.saveArmorItems(new CompoundNBT()), false);
    }

    @Override
    public boolean isDisguised() {
        return this.getForm() == WerewolfForm.NONE;
    }

    static {
        LevelAttributeModifier.registerModdedAttributeModifier(Attributes.ARMOR_TOUGHNESS, ARMOR_TOUGHNESS);
    }

    @Override
    public ISkillHandler<IWerewolfPlayer> getSkillHandler() {
        return this.skillHandler;
    }

    @Override
    public IActionHandler<IWerewolfPlayer> getActionHandler() {
        return this.actionHandler;
    }

    //-- load/save -----------------------------------------------------------------------------------------------------

    public CompoundNBT saveArmorItems(CompoundNBT nbt) {
        CompoundNBT armor = new CompoundNBT();
        for (int i = 0; i < this.armorItems.size(); i++) {
            armor.put("" + i, this.armorItems.get(i).serializeNBT());
        }
        nbt.put("armor", armor);
        return nbt;
    }

    @Override
    public void saveData(CompoundNBT compound) {
        this.actionHandler.saveToNbt(compound);
        this.skillHandler.saveToNbt(compound);
        this.levelHandler.saveToNbt(compound);
        this.saveArmorItems(compound);
        compound.putLong("werewolfTime", this.specialAttributes.werewolfTime);
        compound.putString("form", this.form.getName());
        if (this.lastFormAction != null) {
            compound.putString("lastFormAction", this.lastFormAction.getRegistryName().toString());
        }
    }

    @Override
    public void loadData(CompoundNBT compound) {
        this.actionHandler.loadFromNbt(compound);
        this.skillHandler.loadFromNbt(compound);
        this.levelHandler.loadFromNbt(compound);
        CompoundNBT armor = compound.getCompound("armor");
        for (int i = 0; i < armor.size(); i++) {
            try { //TODO remove
                this.armorItems.set(i, ItemStack.read(armor.getCompound("" + i)));
            } catch (IndexOutOfBoundsException ignored) {

            }
        }
        if (compound.contains("werewolfTime")) {
            this.specialAttributes.werewolfTime = compound.getLong("werewolfTime");
        }
        if (compound.contains("form")) {
            this.switchForm(WerewolfForm.getForm(compound.getString("form")));
        }
        if (compound.contains("lastFormAction")) {
            this.lastFormAction = ((WerewolfFormAction) ModRegistries.ACTIONS.getValue(new ResourceLocation(compound.getString("lastFormAction"))));
        }

    }

    @Override
    protected void writeFullUpdate(CompoundNBT nbt) {
        this.actionHandler.writeUpdateForClient(nbt);
        this.skillHandler.writeUpdateForClient(nbt);
        this.levelHandler.saveToNbt(nbt);
        this.saveArmorItems(nbt);
        nbt.putLong("werewolfTime", this.specialAttributes.werewolfTime);
        nbt.putString("form", this.form.getName());
    }

    @Override
    protected void loadUpdate(CompoundNBT nbt) {
        this.actionHandler.readUpdateFromServer(nbt);
        this.skillHandler.readUpdateFromServer(nbt);
        this.levelHandler.loadFromNbt(nbt);
        CompoundNBT armor = nbt.getCompound("armor");
        for (int i = 0; i < armor.size(); i++) {
            this.armorItems.set(i, ItemStack.read(armor.getCompound("" + i)));
        }
        if (nbt.contains("werewolfTime")) {
            this.specialAttributes.werewolfTime = nbt.getLong("werewolfTime");
        }
        if (nbt.contains("form")) {
            this.switchForm(WerewolfForm.getForm(nbt.getString("form")));
        }
    }

    //-- capability ----------------------------------------------------------------------------------------------------

    @SuppressWarnings("deprecation")
    public static void registerCapability() {
        CapabilityManager.INSTANCE.register(IWerewolfPlayer.class, new Storage(), WerewolfPlayerDefaultImpl::new);
    }

    public static ICapabilityProvider createNewCapability(final PlayerEntity playerEntity) {
        return new ICapabilitySerializable<CompoundNBT>() {

            final IWerewolfPlayer inst = new WerewolfPlayer(playerEntity);
            final LazyOptional<IWerewolfPlayer> opt = LazyOptional.of(() -> inst);

            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                return CAP.orEmpty(cap, opt);
            }

            @Override
            public CompoundNBT serializeNBT() {
                return (CompoundNBT) CAP.getStorage().writeNBT(CAP, inst, null);
            }

            @Override
            public void deserializeNBT(CompoundNBT nbt) {
                CAP.getStorage().readNBT(CAP, inst, null, nbt);
            }
        };
    }

    private static class Storage implements Capability.IStorage<IWerewolfPlayer> {
        @Nullable
        @Override
        public INBT writeNBT(Capability<IWerewolfPlayer> capability, IWerewolfPlayer instance, Direction side) {
            CompoundNBT compound = new CompoundNBT();
            ((WerewolfPlayer) instance).saveData(compound);
            return compound;
        }

        @Override
        public void readNBT(Capability<IWerewolfPlayer> capability, IWerewolfPlayer instance, Direction side, INBT compound) {
            ((WerewolfPlayer) instance).loadData((CompoundNBT) compound);
        }
    }
}
