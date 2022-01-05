package de.teamlapen.werewolves.entities.player.werewolf;

import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.entity.effect.EffectInstanceWithSource;
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
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.*;
import de.teamlapen.werewolves.effects.LupusSanguinemEffect;
import de.teamlapen.werewolves.effects.WerewolfNightVisionEffect;
import de.teamlapen.werewolves.entities.player.werewolf.actions.WerewolfFormAction;
import de.teamlapen.werewolves.mixin.ArmorItemAccessor;
import de.teamlapen.werewolves.mixin.FoodStatsAccessor;
import de.teamlapen.werewolves.util.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

public class WerewolfPlayer extends VampirismPlayer<IWerewolfPlayer> implements IWerewolfPlayer {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final UUID ARMOR_TOUGHNESS = UUID.fromString("f3979aec-b8ef-4e95-84a7-2c6dab8ea46e");
    private static final UUID RAGE_FURY = UUID.fromString("aeb05d51-2388-412c-a164-fb75cab0ab95");

    @CapabilityInject(IWerewolfPlayer.class)
    public static Capability<IWerewolfPlayer> CAP = getNull();

    private void applyEntityAttributes() {
        try {
            this.player.getAttribute(ModAttributes.bite_damage);
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
    private WerewolfForm form = WerewolfForm.NONE;
    @Nullable
    private WerewolfFormAction lastFormAction;
    @Nonnull
    private final LevelHandler levelHandler = new LevelHandler(this);
    private boolean checkArmorModifer;
    private final Map<WerewolfForm, Integer> eyeType = new HashMap<>();
    private final Map<WerewolfForm, Integer> skinType = new HashMap<>();
    private final Map<WerewolfForm, Boolean> glowingEyes = new HashMap<>();

    public WerewolfPlayer(@Nonnull PlayerEntity player) {
        super(player);
        this.actionHandler = new ActionHandler<>(this);
        this.skillHandler = new SkillHandler<>(this, WReference.WEREWOLF_FACTION);
    }

    @Override
    @Nonnull
    public WerewolfForm getForm() {
        return this.form;
    }

    public void setForm(WerewolfFormAction action, WerewolfForm form) {
        switchForm(form);
        this.lastFormAction = action;
        if (!this.player.level.isClientSide) {
            this.sync(NBTHelper.nbtWith(nbt -> nbt.putString("form", this.form.getName())), true);
        }
    }

    public void switchForm(WerewolfForm form) {
        this.form = form;
        this.player.refreshDimensions();
    }

    @Override
    protected VampirismPlayer<IWerewolfPlayer> copyFromPlayer(PlayerEntity playerEntity) {
        WerewolfPlayer oldWerewolf = get(playerEntity);
        CompoundNBT nbt = new CompoundNBT();
        oldWerewolf.saveData(nbt);
        this.loadData(nbt);
        return oldWerewolf;
    }

    public void requestArmorEvaluation() {
        this.checkArmorModifer = true;
    }

    public void removeArmorModifier(){
        for (UUID uuid : ArmorItemAccessor.getARMOR_MODIFIERS()) {
            this.player.getAttribute(Attributes.ARMOR_TOUGHNESS).removeModifier(uuid);
            this.player.getAttribute(Attributes.ARMOR).removeModifier(uuid);
        }
    }

    public void addArmorModifier() {
        Set<UUID> uuids = Sets.newHashSet(ArmorItemAccessor.getARMOR_MODIFIERS());
        int i = 0;
        for (ItemStack stack : this.player.getArmorSlots()) {
            EquipmentSlotType slotType = EquipmentSlotType.byTypeAndIndex(EquipmentSlotType.Group.ARMOR, i);
            ++i;
            Multimap<Attribute, AttributeModifier> map = stack.getAttributeModifiers(slotType);
            for (Map.Entry<Attribute, Collection<AttributeModifier>> entry : map.asMap().entrySet()) {
                for (AttributeModifier modifier : entry.getValue()) {
                    if (uuids.contains(modifier.getId())) {
                        ModifiableAttributeInstance attribute = this.player.getAttribute(entry.getKey());
                        if (!attribute.hasModifier(modifier)) {
                            attribute.addPermanentModifier(modifier);
                        }
                    }
                }
            }
        }
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
        this.player.getCommandSenderWorld().getProfiler().push("werewolves_werewolfplayer");
        if (!isRemote()) {
            if (getLevel() > 0) {
                boolean sync = false;
                boolean syncToAll = false;
                CompoundNBT syncPacket = new CompoundNBT();
                if(this.player.level.getGameTime() % 10 == 0) {
                    if(this.specialAttributes.werewolfTime > 0 && !FormHelper.isWerewolfFormTicking(this.player.level, this.player.blockPosition())) {
                        this.specialAttributes.werewolfTime -= 7 * player.getAttribute(ModAttributes.time_regain).getValue();
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

                if (this.player.level.getGameTime() % 20 == 0) {
                    if (Helper.isFullMoon(this.getRepresentingPlayer().getCommandSenderWorld())) {
                        if (!FormHelper.isFormActionActive(this) && !this.skillHandler.isSkillEnabled(WerewolfSkills.free_will)) {
                            Optional<? extends IAction> action = lastFormAction != null ? Optional.of(lastFormAction) : WerewolfFormAction.getAllAction().stream().filter(this.actionHandler::isActionUnlocked).findAny();
                            action.ifPresent(this.actionHandler::toggleAction);
                        }
                    }

                    if (this.form.isTransformed()) {
                        if (this.player.isInWater() && this.player.isEyeInFluid(FluidTags.WATER) && !this.skillHandler.isSkillEnabled(WerewolfSkills.water_lover)) {
                            this.player.addEffect(new EffectInstance(Effects.WEAKNESS, 21, 0, true, true));
                        }
                    }
                }

                if (this.skillHandler.isSkillEnabled(WerewolfSkills.health_reg)) {
                    this.tickFoodStats();
                }

                if (sync) {
                    sync(syncPacket, syncToAll);
                }

                if (this.checkArmorModifer && this.form.isTransformed() /*&& this.player.world.getGameTime() % 2 == 0*/) {
                    if (!(this.form.isHumanLike() && this.skillHandler.isSkillEnabled(WerewolfSkills.wear_armor))) {
                        this.removeArmorModifier();
                    }
                    this.checkArmorModifer = false;
                }
            }

            EffectInstance effect = this.player.getEffect(Effects.NIGHT_VISION);
            if (this.getForm().isTransformed() && this.specialAttributes.night_vision) {
                if (!(effect instanceof WerewolfNightVisionEffect)) {
                    if (effect != null) {
                        player.removeEffect(effect.getEffect());
                    }
                    player.addEffect(new WerewolfNightVisionEffect());
                }
            } else {
                if (effect instanceof WerewolfNightVisionEffect) {
                    this.player.removeEffect(effect.getEffect());
                    effect = ((EffectInstanceWithSource) effect).getHiddenEffect();
                    if (effect != null) {
                        this.player.addEffect(effect);
                    }
                }
            }
        } else {
            if (getLevel() > 0) {
                if (this.player.level.getGameTime() % 10 == 0) {
                    if (this.specialAttributes.werewolfTime > 0 && !FormHelper.isFormActionActive(this)) {
                        this.specialAttributes.werewolfTime -= 10 * player.getAttribute(ModAttributes.time_regain).getValue();
                    }
                }
                this.actionHandler.updateActions();
            }
        }

        this.specialAttributes.biteTicks = Math.max(0, this.specialAttributes.biteTicks-1);


        this.player.getCommandSenderWorld().getProfiler().pop();
    }

    private void tickFoodStats() {
        Difficulty difficulty = this.player.level.getDifficulty();
        boolean flag = this.player.level.getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION);
        FoodStats stats = this.player.getFoodData();
        if (flag && stats.getSaturationLevel() > 0.0F && player.isHurt() && stats.getFoodLevel() >= 20) {
            if (((FoodStatsAccessor) stats).getFoodTimer() >= 9) {
                float f = Math.min(stats.getSaturationLevel(), 6.0F);
                player.heal(f / 6.0F);
                stats.addExhaustion(f);
            }
        } else if (flag && stats.getFoodLevel() >= 18 && player.isHurt()) {
            if (((FoodStatsAccessor) stats).getFoodTimer() >= 79) {
                player.heal(1.0F);
                stats.addExhaustion(6.0F);
            }
        } else if (stats.getFoodLevel() <= 0) {
            if (((FoodStatsAccessor) stats).getFoodTimer() >= 79) {
                if (player.getHealth() > 10.0F || difficulty == Difficulty.HARD || player.getHealth() > 1.0F && difficulty == Difficulty.NORMAL) {
                    player.hurt(DamageSource.STARVE, 1.0F);
                }

            }
        }
    }

    public boolean setGlowingEyes(WerewolfForm form, boolean on) {
        if (on != this.glowingEyes.getOrDefault(form, false)) {
            this.glowingEyes.put(form, on);
            if (!isRemote()) {
                CompoundNBT nbt = new CompoundNBT();
                CompoundNBT glowingEyes = new CompoundNBT();
                this.glowingEyes.forEach((key, value) -> glowingEyes.putBoolean(key.getName(), value));
                nbt.put("glowingEyes", glowingEyes);
                this.sync(nbt, true);
            }
            return true;
        }
        return false;
    }

    public boolean setEyeType(WerewolfForm form, int type) {
        if (type != this.eyeType.getOrDefault(form, -1)) {
            this.eyeType.put(form, type);
            if (!isRemote()) {
                CompoundNBT nbt = new CompoundNBT();
                CompoundNBT eye = new CompoundNBT();
                this.eyeType.forEach((key, value) -> eye.putInt(key.getName(), value));
                nbt.put("eyeTypes", eye);
                this.sync(nbt, true);
            }
            return true;
        }
        return false;
    }

    public boolean setSkinType(WerewolfForm form, int type) {
        if (type != this.skinType.getOrDefault(form,-1)) {
            this.skinType.put(form, type);
            if (!isRemote()) {
                CompoundNBT nbt = new CompoundNBT();
                CompoundNBT skin = new CompoundNBT();
                this.skinType.forEach((key, value) -> skin.putInt(key.getName(), value));
                nbt.put("skinTypes", skin);
                this.sync(nbt, true);
            }
        }
        return true;
    }
    
    public void setSkinData(WerewolfForm form, int[] data) {
        this.setEyeType(form, data[0]);
        this.setSkinType(form, data[1]);
        this.setGlowingEyes(form, data[2] == 1);
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
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
    public void onEntityKilled(LivingEntity victim, DamageSource src) {
        if (this.getSkillHandler().isRefinementEquipped(ModRefinements.rage_fury)) {
            this.player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST,40, 1));
            this.actionHandler.extendActionTimer(ModActions.rage, WerewolvesConfig.BALANCE.REFINEMENTS.rage_fury_timer_extend.get());
        }
        this.levelHandler.increaseProgress((int) (victim.getMaxHealth() * 0.1));
        this.syncLevelHandler();
    }

    @Override
    public void onJoinWorld() {
        if (this.getLevel() > 0) {
            this.actionHandler.onActionsReactivated();
        }
    }

    public void syncLevelHandler() {
        CompoundNBT sync = new CompoundNBT();
        this.levelHandler.saveToNbt(sync);
        this.sync(sync, false);
    }

    @Override
    public void onPlayerLoggedIn() {

    }

    @Override
    public void onPlayerLoggedOut() {

    }

    @Override
    public void onUpdatePlayer(TickEvent.Phase phase) {

    }

    public boolean canBiteEntity(LivingEntity entity) {
        return entity.distanceTo(this.player) <= this.player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue()+1;
    }

    public boolean canBite(){
        return this.form.isTransformed() && !this.player.isSpectator() && this.getLevel() > 0 && this.specialAttributes.biteTicks <= 0;
    }

    public boolean bite(int entityId) {
        Entity entity = this.player.level.getEntity(entityId);
        if (entity instanceof LivingEntity) {
            return bite(((LivingEntity) entity));
        }
        return false;
    }

    private boolean bite(LivingEntity entity) {
        if (this.specialAttributes.biteTicks > 0)return false;
        if (!this.form.isTransformed()) return false;
        if (entity.distanceTo(this.player) > this.player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue()+1) return false;
        double damage = this.player.getAttribute(ModAttributes.bite_damage).getValue() + this.player.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
        boolean flag = entity.hurt(Helper.causeWerewolfDamage(this.player), (float) damage);
        if (flag) {
            this.eatEntity(entity);
            this.specialAttributes.biteTicks = WerewolvesConfig.BALANCE.PLAYER.bite_cooldown.get();
            if (this.skillHandler.isSkillEnabled(WerewolfSkills.stun_bite)) {
                int duration = WerewolvesConfig.BALANCE.SKILLS.stun_bite_duration.get();
                if (this.skillHandler.isRefinementEquipped(ModRefinements.stun_bite)) {
                    duration += WerewolvesConfig.BALANCE.REFINEMENTS.stun_bite_duration_extend.get();
                }
                entity.addEffect(new EffectInstance(ModEffects.V.freeze, duration));
            } else if (this.skillHandler.isSkillEnabled(WerewolfSkills.bleeding_bite)) {
                entity.addEffect(new EffectInstance(ModEffects.bleeding, WerewolvesConfig.BALANCE.SKILLS.bleeding_bite_duration.get(), this.skillHandler.isRefinementEquipped(ModRefinements.bleeding_bite)?3:0));
            }
            this.sync(NBTHelper.nbtWith(nbt -> nbt.putInt("biteTicks", this.specialAttributes.biteTicks)),false);
            LupusSanguinemEffect.addSanguinemEffectRandom(entity);
        }
        return flag;
    }

    private void eatEntity(LivingEntity entity) {
        if (entity.isInvertedHealAndHarm()) return;
        if (!entity.isAlive() && entity.getType().getCategory().isPersistent()){
            this.player.getFoodData().eat(1,1);
        }
    }

    @Override
    public void onLevelChanged(int newLevel, int oldLevel) {
        this.applyEntityAttributes();
        if (!isRemote()) {
            ScoreboardUtil.updateScoreboard(this.player, WUtils.WEREWOLF_LEVEL_CRITERIA, newLevel);
            LevelAttributeModifier.applyModifier(player, Attributes.MOVEMENT_SPEED, "Werewolf", getLevel(), getMaxLevel(), WerewolvesConfig.BALANCE.PLAYER.werewolf_speed_amount.get(), 0.3, AttributeModifier.Operation.MULTIPLY_TOTAL, false);
            LevelAttributeModifier.applyModifier(player, Attributes.ARMOR_TOUGHNESS, "Werewolf", getLevel(), getMaxLevel(), WerewolvesConfig.BALANCE.PLAYER.werewolf_speed_amount.get(), 0.5, AttributeModifier.Operation.MULTIPLY_TOTAL, false);
            LevelAttributeModifier.applyModifier(player, Attributes.ATTACK_DAMAGE, "Werewolf", getLevel(), getMaxLevel(), WerewolvesConfig.BALANCE.PLAYER.werewolf_damage.get(), 0.5, AttributeModifier.Operation.ADDITION, false);
            if (newLevel > 0) {
                if (oldLevel == 0) {
                    this.skillHandler.enableRootSkill();
                    this.specialAttributes.werewolfTime = 0;
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

    /**
     * feeds it self from bitten entities
     *
     * @param entity The bitten entity
     */
    private void eatFleshFrom(LivingEntity entity) {
        this.player.getFoodData().eat(1, 1);
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
        if (this.getForm().isTransformed()) {
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

    @Override
    public boolean isDisguised() {
        return !this.getForm().isTransformed();
    }

    static {
        LevelAttributeModifier.registerModdedAttributeModifier(Attributes.ARMOR_TOUGHNESS, ARMOR_TOUGHNESS);
    }

    @Nullable
    public WerewolfFormAction getLastFormAction() {
        return lastFormAction;
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

    @Override
    public void saveData(CompoundNBT compound) {
        this.actionHandler.saveToNbt(compound);
        this.skillHandler.saveToNbt(compound);
        this.levelHandler.saveToNbt(compound);
        compound.putLong("werewolfTime", this.specialAttributes.werewolfTime);
        compound.putString("form", this.form.getName());
        if (this.lastFormAction != null) {
            compound.putString("lastFormAction", this.lastFormAction.getRegistryName().toString());
        }
        compound.putInt("biteTicks", this.specialAttributes.biteTicks);
        CompoundNBT eye = new CompoundNBT();
        this.eyeType.forEach((key, value) -> eye.putInt(key.getName(), value));
        compound.put("eyeTypes", eye);
        CompoundNBT skin = new CompoundNBT();
        this.skinType.forEach((key, value) -> skin.putInt(key.getName(), value));
        compound.put("skinTypes", skin);
        CompoundNBT glowingEye = new CompoundNBT();
        this.glowingEyes.forEach((key, value) -> glowingEye.putBoolean(key.getName(), value));
        compound.put("glowingEyes", glowingEye);
    }

    @Override
    public void loadData(CompoundNBT compound) {
        this.actionHandler.loadFromNbt(compound);
        this.skillHandler.loadFromNbt(compound);
        this.levelHandler.loadFromNbt(compound);
        CompoundNBT armor = compound.getCompound("armor");
        for (int i = 0; i < armor.size(); i++) {
            try { //TODO remove
                ItemStack stack = ItemStack.of(armor.getCompound("" + i));
                this.player.setItemSlot(EquipmentSlotType.values()[i],stack);
            } catch (Exception ignored) {

            }
        }
        if (NBTHelper.containsLong(compound,"werewolfTime")) {
            this.specialAttributes.werewolfTime = compound.getLong("werewolfTime");
        }
        if (NBTHelper.containsString(compound, "form")) {
            this.switchForm(WerewolfForm.getForm(compound.getString("form")));
        }
        if (NBTHelper.containsString(compound, "lastFormAction")) {
            this.lastFormAction = ((WerewolfFormAction) ModRegistries.ACTIONS.getValue(new ResourceLocation(compound.getString("lastFormAction"))));
        }
        this.specialAttributes.biteTicks = compound.getInt("biteTicks");
        CompoundNBT eye = compound.getCompound("eyeTypes");
        eye.getAllKeys().forEach(string -> this.eyeType.put(WerewolfForm.getForm(string), eye.getInt(string)));
        CompoundNBT skin = compound.getCompound("skinTypes");
        skin.getAllKeys().forEach(string -> this.skinType.put(WerewolfForm.getForm(string), skin.getInt(string)));
        CompoundNBT glowingEyes = compound.getCompound("glowingEyes");
        glowingEyes.getAllKeys().forEach(string -> this.glowingEyes.put(WerewolfForm.getForm(string), glowingEyes.getBoolean(string)));
    }

    @Override
    protected void writeFullUpdate(CompoundNBT nbt) {
        this.actionHandler.writeUpdateForClient(nbt);
        this.skillHandler.writeUpdateForClient(nbt);
        this.levelHandler.saveToNbt(nbt);
        nbt.putLong("werewolfTime", this.specialAttributes.werewolfTime);
        nbt.putString("form", this.form.getName());
        nbt.putInt("biteTicks", this.specialAttributes.biteTicks);
        CompoundNBT eye = new CompoundNBT();
        this.eyeType.forEach((key, value) -> eye.putInt(key.getName(), value));
        nbt.put("eyeTypes", eye);
        CompoundNBT skin = new CompoundNBT();
        this.skinType.forEach((key, value) -> skin.putInt(key.getName(), value));
        nbt.put("skinTypes", skin);
        CompoundNBT glowingEye = new CompoundNBT();
        this.glowingEyes.forEach((key, value) -> glowingEye.putBoolean(key.getName(), value));
        nbt.put("glowingEyes", glowingEye);
    }

    @Override
    protected void loadUpdate(CompoundNBT nbt) {
        this.actionHandler.readUpdateFromServer(nbt);
        this.skillHandler.readUpdateFromServer(nbt);
        this.levelHandler.loadFromNbt(nbt);
        if (NBTHelper.containsLong(nbt,"werewolfTime")) {
            this.specialAttributes.werewolfTime = nbt.getLong("werewolfTime");
        }
        if (NBTHelper.containsString(nbt, "form")) {
            this.switchForm(WerewolfForm.getForm(nbt.getString("form")));
        }
        if (nbt.contains("biteTicks")) {
            this.specialAttributes.biteTicks = nbt.getInt("biteTicks");
        }
        if (nbt.contains("eyeTypes")){
            CompoundNBT eye = nbt.getCompound("eyeTypes");
            eye.getAllKeys().forEach(string -> this.eyeType.put(WerewolfForm.getForm(string), eye.getInt(string)));
        }
        if (nbt.contains("skinTypes")){
            CompoundNBT skin = nbt.getCompound("skinTypes");
            skin.getAllKeys().forEach(string -> this.skinType.put(WerewolfForm.getForm(string), skin.getInt(string)));
        }
        if (nbt.contains("glowingEyes")) {
            CompoundNBT glowingEyes = nbt.getCompound("glowingEyes");
            glowingEyes.getAllKeys().forEach(string -> this.glowingEyes.put(WerewolfForm.getForm(string), glowingEyes.getBoolean(string)));
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

    @Override
    public int getEyeType() {
        return this.eyeType.getOrDefault(this.form, 0);
    }

    @Override
    public int getSkinType() {
        return this.skinType.getOrDefault(this.form, 0);
    }

    @Override
    public boolean hasGlowingEyes() {
        return this.glowingEyes.getOrDefault(this.form, false);
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
