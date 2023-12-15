package de.teamlapen.werewolves.entities.player.werewolf;

import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.entity.effect.EffectInstanceWithSource;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.entity.player.actions.IAction;
import de.teamlapen.vampirism.api.entity.player.actions.IActionHandler;
import de.teamlapen.vampirism.api.entity.player.skills.ISkillHandler;
import de.teamlapen.vampirism.api.entity.player.skills.SkillType;
import de.teamlapen.vampirism.entity.player.FactionBasePlayer;
import de.teamlapen.vampirism.entity.player.LevelAttributeModifier;
import de.teamlapen.vampirism.entity.player.actions.ActionHandler;
import de.teamlapen.vampirism.entity.player.skills.SkillHandler;
import de.teamlapen.vampirism.util.RegUtil;
import de.teamlapen.vampirism.util.ScoreboardUtil;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.*;
import de.teamlapen.werewolves.effects.LupusSanguinemEffect;
import de.teamlapen.werewolves.effects.WolfsbaneEffect;
import de.teamlapen.werewolves.effects.inst.WerewolfNightVisionEffectInstance;
import de.teamlapen.werewolves.entities.player.werewolf.actions.WerewolfFormAction;
import de.teamlapen.werewolves.mixin.ArmorItemAccessor;
import de.teamlapen.werewolves.mixin.FoodStatsAccessor;
import de.teamlapen.werewolves.mixin.entity.PlayerAccessor;
import de.teamlapen.werewolves.util.*;
import de.teamlapen.werewolves.world.ModDamageSources;
import de.teamlapen.werewolves.world.WerewolvesWorld;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.server.permission.PermissionAPI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

public class WerewolfPlayer extends FactionBasePlayer<IWerewolfPlayer> implements IWerewolfPlayer {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final UUID ARMOR_TOUGHNESS = UUID.fromString("f3979aec-b8ef-4e95-84a7-2c6dab8ea46e");

    public static final Capability<IWerewolfPlayer> CAP = CapabilityManager.get(new CapabilityToken<>(){});

    private void applyEntityAttributes() {
        try {
            this.player.getAttribute(ModAttributes.BITE_DAMAGE.get());
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    public static WerewolfPlayer get(@Nonnull Player playerEntity) {
        return (WerewolfPlayer) playerEntity.getCapability(CAP).orElseThrow(() -> new IllegalStateException("Cannot get werewolf player capability from player" + playerEntity));
    }

    public static LazyOptional<WerewolfPlayer> getOpt(@Nonnull Player playerEntity) {
        LazyOptional<WerewolfPlayer> opt = playerEntity.getCapability(CAP).cast();
        if (!opt.isPresent()) {
            LOGGER.warn("Cannot get Werewolf player capability. This might break mod functionality.", new Throwable().fillInStackTrace());
        }
        return opt;
    }

    /**
     * gets werewolf player optional
     * <p>
     * already filters if the entity is a werewolf player
     */
    public static LazyOptional<WerewolfPlayer> getOptEx(@Nullable Entity entity) {
        if (!(entity instanceof Player) || !Helper.isWerewolf(((Player) entity))) return LazyOptional.empty();
        return getOpt(((Player) entity));
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

    private int sleepTimer;

    public WerewolfPlayer(@Nonnull Player player) {
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
        if (!this.player.level().isClientSide) {
            this.sync(NBTHelper.nbtWith(nbt -> nbt.putString("form", this.form.getName())), true);
        }
    }

    public void switchForm(WerewolfForm form) {
        this.form = form;
        this.player.refreshDimensions();
        if (!this.form.isHumanLike()) {
            ((PlayerAccessor) this.player).invoke_removeEntitiesOnShoulder();
        }
    }

    @Override
    protected FactionBasePlayer<IWerewolfPlayer> copyFromPlayer(Player playerEntity) {
        WerewolfPlayer oldWerewolf = get(playerEntity);
        CompoundTag nbt = new CompoundTag();
        oldWerewolf.saveData(nbt);
        this.loadData(nbt);
        return oldWerewolf;
    }

    public void requestArmorEvaluation() {
        this.checkArmorModifer = true;
    }

    public void removeArmorModifier() {
        for (UUID uuid : ArmorItemAccessor.getARMOR_MODIFIERS().values()) {
            this.player.getAttribute(Attributes.ARMOR_TOUGHNESS).removeModifier(uuid);
            this.player.getAttribute(Attributes.ARMOR).removeModifier(uuid);
        }
    }

    public void addArmorModifier() {
        Set<UUID> uuids = Sets.newHashSet(ArmorItemAccessor.getARMOR_MODIFIERS().values());
        int i = 0;
        for (ItemStack stack : this.player.getArmorSlots()) {
            EquipmentSlot slotType = EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, i);
            ++i;
            Multimap<Attribute, AttributeModifier> map = stack.getAttributeModifiers(slotType);
            for (Map.Entry<Attribute, Collection<AttributeModifier>> entry : map.asMap().entrySet()) {
                for (AttributeModifier modifier : entry.getValue()) {
                    if (uuids.contains(modifier.getId())) {
                        AttributeInstance attribute = this.player.getAttribute(entry.getKey());
                        if (!attribute.hasModifier(modifier)) {
                            attribute.addPermanentModifier(modifier);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onChangedDimension(ResourceKey<Level> registryKey, ResourceKey<Level> registryKey1) {

    }

    @Nonnull
    public WerewolfPlayerSpecialAttributes getSpecialAttributes() {
        return specialAttributes;
    }

    @Override
    public void onUpdate() {
        this.player.getCommandSenderWorld().getProfiler().push("werewolves_werewolfplayer");
        super.onUpdate();
        if (getLevel() > 0) {
            if (!isRemote()) {
                boolean sync = false;
                boolean syncToAll = false;
                CompoundTag syncPacket = new CompoundTag();
                if (this.actionHandler.updateActions()) {
                    sync = true;
                    syncToAll = true;
                    this.actionHandler.writeUpdateForClient(syncPacket);
                }
                if (this.skillHandler.isDirty()) {
                    sync = true;
                    syncToAll = true;
                    skillHandler.writeUpdateForClient(syncPacket);
                }
                if (this.player.level().getGameTime() % 10 == 0) {
                    if (this.specialAttributes.transformationTime > 0 && FormHelper.getActiveFormAction(this).map(action -> !action.consumesWerewolfTime()).orElse(true)) {
                        this.specialAttributes.transformationTime = Mth.clamp(this.specialAttributes.transformationTime - player.getAttribute(ModAttributes.TIME_REGAIN.get()).getValue(), 0, 1);
                        syncPacket.putDouble("transformationTime", this.specialAttributes.transformationTime);
                    }
                    if (this.player.level().getGameTime() % 20 == 0) {
                        if (Helper.isFullMoon(this.getRepresentingPlayer().getCommandSenderWorld())) {
                            if (!FormHelper.isFormActionActive(this) && !this.skillHandler.isSkillEnabled(ModSkills.FREE_WILL.get())) {
                                Optional<? extends IAction<IWerewolfPlayer>> action = lastFormAction != null ? Optional.of(lastFormAction) : WerewolfFormAction.getAllAction().stream().filter(this.actionHandler::isActionUnlocked).findAny();
                                action.ifPresent(a -> this.actionHandler.toggleAction(a, new ActionHandler.ActivationContext()));
                            }
                        }

                        if (this.player.isInWater() && this.player.isEyeInFluidType(ForgeMod.WATER_TYPE.get()) && !this.skillHandler.isSkillEnabled(ModSkills.WATER_LOVER.get())) {
                            this.player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 50, 0, true, true));
                        }
                    }
                }

                if (this.skillHandler.isSkillEnabled(ModSkills.HEALTH_REG.get())) {
                    this.tickFoodStats();
                }

                if (sync) {
                    sync(syncPacket, syncToAll);
                }

                MobEffectInstance effect = this.player.getEffect(MobEffects.NIGHT_VISION);
                if (this.getForm().isTransformed() && this.specialAttributes.night_vision) {
                    if (!(effect instanceof WerewolfNightVisionEffectInstance)) {
                        if (effect != null) {
                            player.removeEffectNoUpdate(effect.getEffect());
                        }
                        player.addEffect(new WerewolfNightVisionEffectInstance(effect));
                    }
                } else {
                    if (effect instanceof WerewolfNightVisionEffectInstance) {
                        this.player.removeEffect(effect.getEffect());
                        effect = ((EffectInstanceWithSource) effect).getHiddenEffect();
                        if (effect != null) {
                            this.player.addEffect(effect);
                        }
                    }
                }

                if (this.player.tickCount % de.teamlapen.vampirism.REFERENCE.REFRESH_GARLIC_TICKS == 0 && this.isAffectedByWolfsbane(this.player.level(), true)) {
                    this.player.addEffect(WolfsbaneEffect.createWolfsbaneEffect(this.player, de.teamlapen.vampirism.REFERENCE.REFRESH_GARLIC_TICKS + 10, this.wolfsbaneCache));
                }
            } else {

                this.actionHandler.updateActions();

                if (this.player.level().getGameTime() % 10 == 0) {
                    if (this.specialAttributes.transformationTime > 0 && FormHelper.getActiveFormAction(this).map(action -> !action.consumesWerewolfTime()).orElse(true)) {
                        this.specialAttributes.transformationTime = Mth.clamp(this.specialAttributes.transformationTime - ((float) player.getAttribute(ModAttributes.TIME_REGAIN.get()).getValue()), 0, 1);
                    }
                }

                MobEffectInstance effect = this.player.getEffect(MobEffects.NIGHT_VISION);
                if (this.getForm().isTransformed() && this.specialAttributes.night_vision && !(effect instanceof WerewolfNightVisionEffectInstance)) {
                    player.removeEffectNoUpdate(MobEffects.NIGHT_VISION);
                    player.addEffect(new WerewolfNightVisionEffectInstance());
                }
            }

            if (this.checkArmorModifer && this.form.isTransformed()) {
                if (!(this.form.isHumanLike() && this.skillHandler.isSkillEnabled(ModSkills.WEAR_ARMOR.get()))) {
                    this.removeArmorModifier();
                }
                this.checkArmorModifer = false;
            }

            this.specialAttributes.biteTicks = Math.max(0, this.specialAttributes.biteTicks - 1);

        } else if (!this.isRemote() && this.player.isSleeping() && this.player.hasEffect(ModEffects.LUPUS_SANGUINEM.get())) {
            if (this.sleepTimer++ >= 200) {
                this.player.getEffect(ModEffects.LUPUS_SANGUINEM.get()).applyEffect(this.player);
                this.player.removeEffect(ModEffects.LUPUS_SANGUINEM.get());
                this.player.stopSleeping();
            }
        } else {
            this.sleepTimer = 0;
        }
        this.player.getCommandSenderWorld().getProfiler().pop();
    }

    private void tickFoodStats() {
        FoodData stats = this.player.getFoodData();
        ((FoodStatsAccessor) stats).setTickTimer(((FoodStatsAccessor) stats).getTickTimer() + 1);
    }

    public boolean setGlowingEyes(WerewolfForm form, boolean on) {
        if (on != this.glowingEyes.getOrDefault(form, false)) {
            this.glowingEyes.put(form, on);
            if (!isRemote()) {
                CompoundTag nbt = new CompoundTag();
                CompoundTag glowingEyes = new CompoundTag();
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
                CompoundTag nbt = new CompoundTag();
                CompoundTag eye = new CompoundTag();
                this.eyeType.forEach((key, value) -> eye.putInt(key.getName(), value));
                nbt.put("eyeTypes", eye);
                this.sync(nbt, true);
            }
            return true;
        }
        return false;
    }

    public boolean setSkinType(WerewolfForm form, int type) {
        if (type != this.skinType.getOrDefault(form, -1)) {
            this.skinType.put(form, type);
            if (!isRemote()) {
                CompoundTag nbt = new CompoundTag();
                CompoundTag skin = new CompoundTag();
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
        if (this.getSkillHandler().isRefinementEquipped(ModRefinements.RAGE_FURY.get())) {
            this.player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 1));
            this.actionHandler.extendActionTimer(ModActions.RAGE.get(), WerewolvesConfig.BALANCE.REFINEMENTS.rage_fury_timer_extend.get());
        }
        this.levelHandler.increaseProgress((int) (victim.getMaxHealth() * 0.2));
        this.syncLevelHandler();
    }

    @Override
    public void onJoinWorld() {
        if (this.getLevel() > 0) {
            this.actionHandler.onActionsReactivated();
        }
    }

    public void syncLevelHandler() {
        CompoundTag sync = new CompoundTag();
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
        return entity.distanceTo(this.player) <= this.player.getAttribute(ForgeMod.BLOCK_REACH.get()).getValue() + 1 && (!(entity instanceof ServerPlayer) || PermissionAPI.getPermission((ServerPlayer) this.getRepresentingPlayer(), Permissions.BITE_PLAYER));
    }

    public boolean canBite() {
        return this.form.isTransformed() && !this.player.isSpectator() && this.getLevel() > 0 && this.specialAttributes.biteTicks <= 0 && (!(player instanceof ServerPlayer) || PermissionAPI.getPermission((ServerPlayer) this.getRepresentingPlayer(), Permissions.BITE));
    }

    public boolean bite(int entityId) {
        Entity entity = this.player.level().getEntity(entityId);
        if (entity instanceof LivingEntity) {
            return bite(((LivingEntity) entity));
        }
        return false;
    }

    private boolean bite(LivingEntity entity) {
        if (this.specialAttributes.biteTicks > 0) return false;
        if (!this.form.isTransformed()) return false;
        if (!canBite()) return false;
        if (!canBiteEntity(entity)) return false;
        double damage = this.player.getAttribute(ModAttributes.BITE_DAMAGE.get()).getValue();
        boolean flag = DamageHandler.hurtModded(entity, (ModDamageSources sources) -> sources.bite(this.player), (float) damage);
        if (flag) {
            this.getRepresentingPlayer().playSound(ModSounds.ENTITY_WEREWOLF_BITE.get(), 1.0F, 1.0F);
            this.getRepresentingPlayer().playNotifySound(ModSounds.ENTITY_WEREWOLF_BITE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            this.eatEntity(entity);
            this.specialAttributes.biteTicks = WerewolvesConfig.BALANCE.PLAYER.bite_cooldown.get();
            if (!getForm().isHumanLike()) {
                if (this.skillHandler.isSkillEnabled(ModSkills.STUN_BITE.get())) {
                    int duration = WerewolvesConfig.BALANCE.SKILLS.stun_bite_duration.get();
                    if (this.skillHandler.isRefinementEquipped(ModRefinements.STUN_BITE.get())) {
                        duration += WerewolvesConfig.BALANCE.REFINEMENTS.stun_bite_duration_extend.get();
                    }
                    entity.addEffect(new MobEffectInstance(ModEffects.V.FREEZE.get(), duration));
                }
                if (this.skillHandler.isSkillEnabled(ModSkills.BLEEDING_BITE.get())) {
                    entity.addEffect(new MobEffectInstance(ModEffects.BLEEDING.get(), WerewolvesConfig.BALANCE.SKILLS.bleeding_bite_duration.get(), this.skillHandler.isRefinementEquipped(ModRefinements.BLEEDING_BITE.get()) ? 3 : 0));
                }
            }
            this.sync(NBTHelper.nbtWith(nbt -> nbt.putInt("biteTicks", this.specialAttributes.biteTicks)), false);
            if (!(entity instanceof ServerPlayer) || PermissionAPI.getPermission((ServerPlayer) this.getRepresentingPlayer(), Permissions.INFECT_PLAYER)) {
                LupusSanguinemEffect.infectRandomByPlayer(entity);
            }
        }
        return flag;
    }

    private void eatEntity(LivingEntity entity) {
        if (entity.isInvertedHealAndHarm()) return;
        if (!entity.isAlive() && entity.getType().getCategory().isPersistent()) {
            this.player.getFoodData().eat(1, 1);
        }
    }

    @Override
    public void onLevelChanged(int newLevel, int oldLevel) {
        super.onLevelChanged(newLevel, oldLevel);
        this.applyEntityAttributes();
        if (!isRemote()) {
            ScoreboardUtil.updateScoreboard(this.player, WUtils.WEREWOLF_LEVEL_CRITERIA, newLevel);
            LevelAttributeModifier.applyModifier(player, Attributes.MOVEMENT_SPEED, "Werewolf", getLevel(), getMaxLevel(), WerewolvesConfig.BALANCE.PLAYER.werewolf_speed_amount.get(), 0.3, AttributeModifier.Operation.MULTIPLY_TOTAL, false);
            LevelAttributeModifier.applyModifier(player, Attributes.ARMOR_TOUGHNESS, "Werewolf", getLevel(), getMaxLevel(), WerewolvesConfig.BALANCE.PLAYER.werewolf_speed_amount.get(), 0.5, AttributeModifier.Operation.MULTIPLY_TOTAL, false);
            LevelAttributeModifier.applyModifier(player, Attributes.ATTACK_DAMAGE, "Werewolf", getLevel(), getMaxLevel(), WerewolvesConfig.BALANCE.PLAYER.werewolf_damage.get(), 0.5, AttributeModifier.Operation.ADDITION, false);
            if (newLevel > 0) {
                if (oldLevel == 0) {
                    this.skillHandler.enableRootSkill(SkillType.LEVEL);
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

    private int wolfsbaneCache = -1;

    public boolean isAffectedByWolfsbane(LevelAccessor accessor, boolean forceRefresh) {
        if (forceRefresh) {
            this.wolfsbaneCache = accessor instanceof Level level ? WerewolvesWorld.getOpt(level).map(x -> x.isEffectedByWolfsbane(getRepresentingPlayer().blockPosition())).orElse(-1) : -1;
        }
        return this.wolfsbaneCache > -1;
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

    public Tier getDigDropTier() {
        return Tiers.values()[this.specialAttributes.diggingLevel];
    }

    public float getDigSpeed() {
        return this.specialAttributes.diggingSpeed;
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
    public void saveData(CompoundTag compound) {
        super.saveData(compound);
        this.actionHandler.saveToNbt(compound);
        this.skillHandler.saveToNbt(compound);
        this.levelHandler.saveToNbt(compound);
        compound.putString("form", this.form.getName());
        if (this.lastFormAction != null) {
            compound.putString("lastFormAction", RegUtil.id(this.lastFormAction).toString());
        }
        compound.putInt("biteTicks", this.specialAttributes.biteTicks);
        CompoundTag eye = new CompoundTag();
        this.eyeType.forEach((key, value) -> eye.putInt(key.getName(), value));
        compound.put("eyeTypes", eye);
        CompoundTag skin = new CompoundTag();
        this.skinType.forEach((key, value) -> skin.putInt(key.getName(), value));
        compound.put("skinTypes", skin);
        CompoundTag glowingEye = new CompoundTag();
        this.glowingEyes.forEach((key, value) -> glowingEye.putBoolean(key.getName(), value));
        compound.put("glowingEyes", glowingEye);
        compound.putDouble("transformationTime", this.specialAttributes.transformationTime);
    }

    @Override
    public void loadData(CompoundTag compound) {
        super.loadData(compound);
        this.actionHandler.loadFromNbt(compound);
        this.skillHandler.loadFromNbt(compound);
        this.levelHandler.loadFromNbt(compound);
        CompoundTag armor = compound.getCompound("armor");
        for (int i = 0; i < armor.size(); i++) {
            try { //TODO remove
                ItemStack stack = ItemStack.of(armor.getCompound("" + i));
                this.player.setItemSlot(EquipmentSlot.values()[i], stack);
            } catch (Exception ignored) {

            }
        }
        if (NBTHelper.containsString(compound, "form")) {
            this.switchForm(WerewolfForm.getForm(compound.getString("form")));
        }
        if (NBTHelper.containsString(compound, "lastFormAction")) {
            this.lastFormAction = ((WerewolfFormAction) RegUtil.getAction(new ResourceLocation(compound.getString("lastFormAction"))));
        }
        this.specialAttributes.biteTicks = compound.getInt("biteTicks");
        CompoundTag eye = compound.getCompound("eyeTypes");
        eye.getAllKeys().forEach(string -> this.eyeType.put(WerewolfForm.getForm(string), eye.getInt(string)));
        CompoundTag skin = compound.getCompound("skinTypes");
        skin.getAllKeys().forEach(string -> this.skinType.put(WerewolfForm.getForm(string), skin.getInt(string)));
        CompoundTag glowingEyes = compound.getCompound("glowingEyes");
        glowingEyes.getAllKeys().forEach(string -> this.glowingEyes.put(WerewolfForm.getForm(string), glowingEyes.getBoolean(string)));
        if (compound.contains("transformationTime")) {
            this.specialAttributes.transformationTime = compound.getFloat("transformationTime");
        }
    }

    @Override
    protected void writeFullUpdate(CompoundTag nbt) {
        super.writeFullUpdate(nbt);
        this.actionHandler.writeUpdateForClient(nbt);
        this.skillHandler.writeUpdateForClient(nbt);
        this.levelHandler.saveToNbt(nbt);
        nbt.putString("form", this.form.getName());
        nbt.putInt("biteTicks", this.specialAttributes.biteTicks);
        CompoundTag eye = new CompoundTag();
        this.eyeType.forEach((key, value) -> eye.putInt(key.getName(), value));
        nbt.put("eyeTypes", eye);
        CompoundTag skin = new CompoundTag();
        this.skinType.forEach((key, value) -> skin.putInt(key.getName(), value));
        nbt.put("skinTypes", skin);
        CompoundTag glowingEye = new CompoundTag();
        this.glowingEyes.forEach((key, value) -> glowingEye.putBoolean(key.getName(), value));
        nbt.put("glowingEyes", glowingEye);
        nbt.putDouble("transformationTime", this.specialAttributes.transformationTime);
    }

    @Override
    protected void loadUpdate(CompoundTag nbt) {
        super.loadUpdate(nbt);
        this.actionHandler.readUpdateFromServer(nbt);
        this.skillHandler.readUpdateFromServer(nbt);
        this.levelHandler.loadFromNbt(nbt);
        if (NBTHelper.containsString(nbt, "form")) {
            this.switchForm(WerewolfForm.getForm(nbt.getString("form")));
        }
        if (nbt.contains("biteTicks")) {
            this.specialAttributes.biteTicks = nbt.getInt("biteTicks");
        }
        if (nbt.contains("eyeTypes")) {
            CompoundTag eye = nbt.getCompound("eyeTypes");
            eye.getAllKeys().forEach(string -> this.eyeType.put(WerewolfForm.getForm(string), eye.getInt(string)));
        }
        if (nbt.contains("skinTypes")) {
            CompoundTag skin = nbt.getCompound("skinTypes");
            skin.getAllKeys().forEach(string -> this.skinType.put(WerewolfForm.getForm(string), skin.getInt(string)));
        }
        if (nbt.contains("glowingEyes")) {
            CompoundTag glowingEyes = nbt.getCompound("glowingEyes");
            glowingEyes.getAllKeys().forEach(string -> this.glowingEyes.put(WerewolfForm.getForm(string), glowingEyes.getBoolean(string)));
        }
        if (nbt.contains("transformationTime")) {
            this.specialAttributes.transformationTime = nbt.getFloat("transformationTime");
        }
    }

    //-- capability ----------------------------------------------------------------------------------------------------

    public static ICapabilityProvider createNewCapability(final Player player) {
        return new ICapabilitySerializable<CompoundTag>() {

            final WerewolfPlayer inst = new WerewolfPlayer(player);
            final LazyOptional<IWerewolfPlayer> opt = LazyOptional.of(() -> inst);

            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                return CAP.orEmpty(cap, opt);
            }

            @Override
            public CompoundTag serializeNBT() {
                CompoundTag tag = new CompoundTag();
                inst.saveData(tag);
                return tag;
            }

            @Override
            public void deserializeNBT(CompoundTag nbt) {
                inst.loadData(nbt);
            }
        };
    }

    @Override
    public int getEyeType(WerewolfForm form) {
        return this.eyeType.getOrDefault(form, 0);
    }

    @Override
    public int getSkinType(WerewolfForm form) {
        return this.skinType.getOrDefault(form, 0);
    }

    @Override
    public boolean hasGlowingEyes(WerewolfForm form) {
        return this.glowingEyes.getOrDefault(form, false);
    }
}
