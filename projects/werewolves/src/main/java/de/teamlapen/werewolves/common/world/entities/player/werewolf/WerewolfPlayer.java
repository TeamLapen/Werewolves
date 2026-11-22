package de.teamlapen.werewolves.common.world.entities.player.werewolf;

import de.teamlapen.faction.common.util.ScoreboardUtil;
import de.teamlapen.faction.common.world.entities.player.LevelAttributeModifier;
import de.teamlapen.library.api.world.EnumStrength;
import net.minecraft.world.level.ChunkPos;

import de.teamlapen.faction.api.factions.IDisguise;
import de.teamlapen.faction.api.factions.actions.IAction;
import de.teamlapen.faction.api.factions.actions.IActionHandler;
import de.teamlapen.faction.api.factions.level.FactionUpdate;
import de.teamlapen.faction.api.factions.lord.ILordPlayer;
import de.teamlapen.faction.api.factions.refinements.IRefinementHandler;
import de.teamlapen.faction.api.factions.skills.ISkillHandler;
import de.teamlapen.faction.common.config.FactionConfig;
import de.teamlapen.faction.common.factions.FactionBasePlayer;
import de.teamlapen.faction.common.factions.actions.ActionHandler;
import de.teamlapen.faction.common.factions.skills.SkillHandler;
import de.teamlapen.faction.common.util.AttachmentSynchronization;
import de.teamlapen.faction.common.util.ModCodecs;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.api.items.IWerewolfArmor;
import de.teamlapen.werewolves.common.core.*;
import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.world.effects.LupusSanguinemEffect;
import de.teamlapen.werewolves.common.world.effects.WolfsbaneEffect;
import de.teamlapen.werewolves.common.world.effects.inst.WerewolfNightVisionEffectInstance;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.actions.WerewolfFormAction;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.properties.*;
import de.teamlapen.werewolves.misc.mixin.FoodStatsAccessor;
import de.teamlapen.werewolves.misc.mixin.entity.PlayerAccessor;
import de.teamlapen.werewolves.util.*;
import de.teamlapen.werewolves.common.world.attachments.LevelWolfsbane;
import de.teamlapen.werewolves.common.world.attachments.ModDamageSources;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.server.permission.PermissionAPI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.*;

public class WerewolfPlayer extends FactionBasePlayer<IWerewolfPlayer> implements IWerewolfPlayer {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Identifier CLAWS = WIdentifier.mod("claws");

    public static WerewolfPlayer get(Player playerEntity) {
        return playerEntity.getData(ModAttachments.WEREWOLF_PLAYER);
    }

    private final WerewolfSkillProperties specialAttributes = new WerewolfSkillProperties();
    private WerewolfForm form = WerewolfForm.NONE;
    @Nullable
    private Holder<WerewolfFormAction> lastFormAction;

    private final ActionHandler<IWerewolfPlayer> actionHandler = new ActionHandler<>(this);
    private final SkillHandler<IWerewolfPlayer> skillHandler = new SkillHandler<>(this);
    private final LevelProgressHandler levelProgressHandler = new LevelProgressHandler(this);
    private final Customization customization = new Customization(this);
    private final WerewolfInventory inventory = new WerewolfInventory(this);
    private final WerewolfDisguise disguise = new WerewolfDisguise(this);

    private int sleepTimer;

    public WerewolfPlayer(Player player) {
        super(player);
    }

    @Override
    public WerewolfForm getForm() {
        return this.form;
    }

    public void setForm(Holder<WerewolfFormAction> action, WerewolfForm form) {
        switchForm(form);
        this.lastFormAction = action;
        if (!this.player.level().isClientSide()) {
            this.sync();
        }
    }

    public WerewolfInventory getInventory() {
        return inventory;
    }

    public void switchForm(WerewolfForm form) {
        if (this.form == form) return;
        var previousForm = this.form;
        this.form = form;
        this.player.refreshDimensions();
        if (!this.form.isHumanLike()) {
            ((PlayerAccessor) this.player).invoke_removeEntitiesOnShoulder();
        }
        if (!this.player.level().isClientSide()) {
            this.inventory.swapArmorItems(previousForm, form);
        }
        checkToolDamage(this.player.getMainHandItem(), this.player.getMainHandItem(), true);
    }

    @Override
    public boolean canWearArmor(ItemStack stack) {
        return canWearArmor(this.form, stack);
    }

    public boolean canWearArmor(List<ItemStack> stacks) {
        return stacks.stream().allMatch(this::canWearArmor);
    }

    public boolean canWearArmor(WerewolfForm form, List<ItemStack> stack) {
        return stack.stream().allMatch(s -> canWearArmor(form, s));
    }

    public boolean canWearArmor(WerewolfForm form, ItemStack stack) {
        if (stack.isEmpty()) {
            return true;
        } else if (stack.getItem() instanceof IWerewolfArmor armor) {
            return armor.canWear(this, form);
        } else {
            return form.isHumanLike() && (!form.isTransformed() || this.getSkillHandler().isSkillEnabled(ModSkills.WEAR_ARMOR));
        }
    }

    public WerewolfSkillProperties getSpecialAttributes() {
        return specialAttributes;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (getLevel() > 0) {
            this.actionHandler.updateActions();
            if (!isRemote()) {
                if (this.player.level().getGameTime() % 10 == 0) {
                    if (this.specialAttributes.transformationTime > 0 && !FormHelper.isFormActionActive(this)) {
                        this.specialAttributes.transformationTime = Mth.clamp(this.specialAttributes.transformationTime - player.getAttribute(ModAttributes.TIME_REGAIN).getValue(), 0, 1);
                    }
                    if (this.player.level().getGameTime() % 20 == 0) {
                        if (Helper.isFullMoon(this.asEntity().level(), this.asEntity().blockPosition())) {
                            if (!FormHelper.isFormActionActive(this) && !this.skillHandler.isSkillEnabled(ModSkills.FREE_WILL)) {
                                Optional<? extends Holder<? extends IAction<IWerewolfPlayer>>> action = lastFormAction != null ? Optional.of(lastFormAction) : WerewolfFormAction.getAllAction().stream().filter(this.actionHandler::isActionUnlocked).findAny();
                                action.ifPresent(a -> this.actionHandler.toggleAction(a, new ActionHandler.ActivationContext()));
                            }
                        }

                        if (this.player.isInWater() && this.player.isEyeInFluidType(NeoForgeMod.WATER_TYPE.value()) && !this.skillHandler.isSkillEnabled(ModSkills.WATER_LOVER)) {
                            this.player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 50, 0, true, true));
                        }
                    }
                }

                if (this.skillHandler.isSkillEnabled(ModSkills.HEALTH_REG)) {
                    this.tickFoodStats();
                }

                MobEffectInstance effect = this.player.getEffect(MobEffects.NIGHT_VISION);
                if (this.getForm().isTransformed() && this.specialAttributes.night_vision) {
                    if (effect == null || !effect.factions$hasProperty(WReference.PERMANENT_INVISIBLE_MOB_EFFECT)) {
                        if (effect != null) {
                            player.removeEffectNoUpdate(effect.getEffect());
                        }
                        player.addEffect(new WerewolfNightVisionEffectInstance(effect));
                    }
                } else {
                    if (effect != null && effect.factions$hasProperty(WReference.PERMANENT_INVISIBLE_MOB_EFFECT)) {
                        effect.factions$removeEffect();
                    }
                }

                if (this.player.tickCount % REFERENCE.REFRESH_WOLFSBANE_TICKS == 0 && this.isAffectedByWolfsbane(this.player.level(), true)) {
                    this.player.addEffect(WolfsbaneEffect.createWolfsbaneEffect(this.player, REFERENCE.REFRESH_WOLFSBANE_TICKS + 10, this.wolfsbaneCache));
                }
            } else {

                this.actionHandler.updateActions();

                if (this.player.level().getGameTime() % 10 == 0) {
                    if (this.specialAttributes.transformationTime > 0 && !FormHelper.isFormActionActive(this)) {
                        this.specialAttributes.transformationTime = Mth.clamp(this.specialAttributes.transformationTime - ((float) player.getAttribute(ModAttributes.TIME_REGAIN).getValue()), 0, 1);
                    }
                }

                MobEffectInstance effect = this.player.getEffect(MobEffects.NIGHT_VISION);
                if (this.getForm().isTransformed() && this.specialAttributes.night_vision && !(effect instanceof WerewolfNightVisionEffectInstance)) {
                    player.removeEffectNoUpdate(MobEffects.NIGHT_VISION);
                    player.addEffect(new WerewolfNightVisionEffectInstance());
                }
            }

            this.specialAttributes.biteTicks = Math.max(0, this.specialAttributes.biteTicks - 1);

        } else if (!this.isRemote() && this.player.isSleeping() && this.player.hasEffect(ModEffects.LUPUS_SANGUINEM)) {
            if (this.sleepTimer++ >= 200) {
                this.player.getEffect(ModEffects.LUPUS_SANGUINEM).tickServer(((ServerLevel) this.player.level()),this.player, () -> {});
                this.player.removeEffect(ModEffects.LUPUS_SANGUINEM);
                this.player.stopSleeping();
            }
        } else {
            this.sleepTimer = 0;
        }
    }

    @Override
    public void onUpdatePlayer(PlayerTickEvent playerTickEvent) {

    }

    private void tickFoodStats() {
        FoodData stats = this.player.getFoodData();
        ((FoodStatsAccessor) stats).setTickTimer(((FoodStatsAccessor) stats).getTickTimer() + 1);
    }

    public void setGlowingEyes(WerewolfForm form, boolean on) {
        this.customization.setGlowingEyes(form, on);
    }

    @Override
    public void setEyeType(WerewolfForm form, int type) {
        this.customization.setEyeType(form, type);
    }

    @Override
    public void setSkinType(WerewolfForm form, int type) {
        this.customization.setSkinType(form, type);
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

    public LevelProgressHandler getLevelHandler() {
        return levelProgressHandler;
    }

    @Override
    public void onEntityKilled(LivingEntity victim, DamageSource src) {
        if (getRefinementHandler().isRefinementEquipped(ModRefinements.RAGE_FURY)) {
            this.player.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 40, 1));
            this.actionHandler.extendActionTimer(ModActions.RAGE, ModConfig.balance().REFINEMENTS.rage_fury_timer_extend.get());
        }
        this.levelProgressHandler.increaseProgress((int) (victim.getMaxHealth() * 0.2));
    }

    @Override
    public void onJoinWorld() {
        if (this.getLevel() > 0) {
            this.actionHandler.onActionsReactivated();
        }
    }

    public boolean canBiteEntity(LivingEntity entity) {
        return entity.distanceTo(this.player) <= this.player.getAttribute(Attributes.BLOCK_INTERACTION_RANGE).getValue() + 1 && (!(entity instanceof ServerPlayer) || PermissionAPI.getPermission((ServerPlayer) this.asEntity(), Permissions.BITE_PLAYER));
    }

    public boolean canBite() {
        return this.form.isTransformed() && !this.player.isSpectator() && this.getLevel() > 0 && this.specialAttributes.biteTicks <= 0 && (!(player instanceof ServerPlayer) || PermissionAPI.getPermission((ServerPlayer) this.asEntity(), Permissions.BITE));
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
        if (!(asEntity().level() instanceof ServerLevel serverLevel)) return false;
        double damage = this.player.getAttribute(ModAttributes.BITE_DAMAGE).getValue();
        boolean flag = DamageHandler.hurtModded(serverLevel, entity, (ModDamageSources sources) -> sources.bite(this.player), (float) damage);
        if (flag) {
            var refinements = getRefinementHandler();
            this.asEntity().playSound(ModSounds.ENTITY_WEREWOLF_BITE.get(), 1.0F, 1.0F);
            this.eatEntity(entity);
            this.specialAttributes.biteTicks = ModConfig.balance().PLAYER.bite_cooldown.get();
            if (!getForm().isHumanLike()) {
                if (this.skillHandler.isSkillEnabled(ModSkills.STUN_BITE)) {
                    int duration = ModConfig.balance().SKILLS.stun_bite_duration.get();
                    if (refinements.isRefinementEquipped(ModRefinements.STUN_BITE)) {
                        duration += ModConfig.balance().REFINEMENTS.stun_bite_duration_extend.get();
                    }
                    entity.addEffect(new MobEffectInstance(ModEffects.STUN, duration));
                }
                if (this.skillHandler.isSkillEnabled(ModSkills.BLEEDING_BITE)) {
                    entity.addEffect(new MobEffectInstance(ModEffects.bleeding(), ModConfig.balance().SKILLS.bleeding_bite_duration.get(), refinements.isRefinementEquipped(ModRefinements.BLEEDING_BITE) ? 3 : 0));
                }
            }
            if (!(entity instanceof ServerPlayer) || PermissionAPI.getPermission((ServerPlayer) this.asEntity(), Permissions.INFECT_PLAYER)) {
                LupusSanguinemEffect.infectRandomByPlayer(entity);
            }
        }
        return flag;
    }

    private void eatEntity(LivingEntity entity) {
        if (!Helper.isNoLiving(entity)) return;
        if (!entity.isAlive()) {
            this.player.getFoodData().eat(1, 1);
        }
    }

    @Override
    public void levelChanged(FactionUpdate changes) {
        onLevelChanged(changes.getLevel());
    }

    @Override
    public void leaveFaction() {
        onLevelChanged(0);
        this.actionHandler.resetTimers();
        this.skillHandler.disableAllSkills();
        sync();
    }

    protected void onLevelChanged(int newLevel) {
        ScoreboardUtil.updateScoreboard(this.player, WUtils.WEREWOLF_LEVEL_CRITERIA, newLevel);
        this.applyEntityAttributes();
        LevelAttributeModifier.applyModifier(player, Attributes.MOVEMENT_SPEED, "Werewolf", newLevel, getMaxLevel(), ModConfig.balance().PLAYER.werewolf_speed_amount.get(), 0.3, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL, false);
        LevelAttributeModifier.applyModifier(player, Attributes.ARMOR_TOUGHNESS, "Werewolf", newLevel, getMaxLevel(), ModConfig.balance().PLAYER.werewolf_speed_amount.get(), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL, false);
        LevelAttributeModifier.applyModifier(player, Attributes.ATTACK_DAMAGE, "Werewolf", newLevel, getMaxLevel(), ModConfig.balance().PLAYER.werewolf_damage.get(), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL, false);

    }

    private int wolfsbaneCache = -1;

    public boolean isAffectedByWolfsbane(LevelAccessor accessor, boolean forceRefresh) {
        if (forceRefresh) {
            this.wolfsbaneCache = accessor instanceof Level level ? (LevelWolfsbane.get(level).getStrengthAtChunk(ChunkPos.containing(asEntity().blockPosition())) != EnumStrength.NONE ? 1 : -1) : -1;
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
    public boolean canLeaveFaction() {
        return true;
    }

    public Optional<DiggerTier> getDigDropTier() {
        return Optional.ofNullable(this.specialAttributes.diggerTier);
    }

    public float getDigSpeed() {
        return this.specialAttributes.diggingSpeed;
    }

    @Override
    public int getMaxLevel() {
        return REFERENCE.HIGHEST_WEREWOLF_LEVEL;
    }

    @Override
    public boolean isDisguised() {
        return !this.getForm().isTransformed();
    }

    @Nullable
    public Holder<? extends WerewolfFormAction> getLastFormAction() {
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
    protected void registerProperties() {
        super.registerProperties();
        this.registerProperty(WIdentifier.mod("action_handler")).subProperty(() -> this.actionHandler).register();
        this.registerProperty(WIdentifier.mod("skill_handler")).subProperty(() -> this.skillHandler).register();
        this.registerProperty(WIdentifier.mod("customization")).subProperty(() -> this.customization).register();
        this.registerProperty(WIdentifier.mod("level_handler")).subProperty(() -> this.levelProgressHandler).register();
        this.registerProperty(WIdentifier.mod("inventory")).subProperty(() -> this.inventory).register();
        this.registerProperty(WIdentifier.mod("form")).simple(WerewolfForm.CODEC).provider(() -> this.form).commonLoader(newForm -> {
            var prev = this.form;
            this.form = newForm;
            return !Objects.equals(prev, this.form);
        });
        this.registerProperty(WIdentifier.mod("bite_ticks")).simple(0, () -> this.specialAttributes.biteTicks, x -> this.specialAttributes.biteTicks = x);
        this.registerProperty(WIdentifier.mod("transformation_time")).simple(0, () -> this.specialAttributes.transformationTime, x -> this.specialAttributes.transformationTime = x);
        this.registerProperty(WIdentifier.mod("last_form_action")).simple(ModCodecs.action().optionalFieldOf("action").codec()).defaultValue(Optional.empty()).provider(() -> Optional.ofNullable(lastFormAction))
                .serverLoader(x -> this.lastFormAction = (Holder<WerewolfFormAction>) x.filter(y -> y.value() instanceof WerewolfFormAction).orElse(null))
                .clientLoader(x -> {
            var old = this.lastFormAction;
            lastFormAction = (Holder<WerewolfFormAction>) x.orElse(null);
            return !Objects.equals(old, lastFormAction);
        }).register();
    }

    private void applyEntityAttributes() {
        try {
            this.player.getAttribute(ModAttributes.BITE_DAMAGE);
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    public void checkToolDamage(ItemStack from, ItemStack itemInHand, boolean forceCalculation) {
        AttributeInstance attribute = player.getAttribute(Attributes.ATTACK_DAMAGE);
        if (this.getLevel() > 0 && this.form.isTransformed() && itemInHand.isEmpty()) {
            if (!from.isEmpty() || forceCalculation) {
                float damage = ModConfig.balance().PLAYER.werewolf_claw_damage.get().floatValue();
                if (specialAttributes.diggerTier != null) {
                    damage += (1 + specialAttributes.diggerTier.attackDamage()) / 2;
                }
                attribute.removeModifier(CLAWS);
                attribute.addTransientModifier(new AttributeModifier(CLAWS, damage, AttributeModifier.Operation.ADD_VALUE));
            }
        } else {
            attribute.removeModifier(CLAWS);
        }
    }

    public void checkWerewolfFormModifier() {
        WerewolfFormAction.getAllAction().stream().filter(action -> getActionHandler().isActionActive(action)).findAny().ifPresent(action -> {
            action.value().checkDayNightModifier(this);
        });
    }

    @Override
    public IDisguise getDisguise() {
        return this.disguise;
    }

    @Override
    public @org.jetbrains.annotations.Nullable Component getShortLevelDisplay() {
        return getPlayerLord().filter(l -> l.getLordLevel() > 0).map(ILordPlayer::getLordTitleShort)
                .orElseGet(() -> Component.literal(String.valueOf(getLevel())));
    }

    @Override
    public @org.jetbrains.annotations.Nullable Component getLevelDisplay() {
        return getPlayerLord().filter(l -> l.getLordLevel() > 0).map(ILordPlayer::getLordTitle)
                .orElseGet(() -> Component.translatable("gui.factionapi.level").append(" " + getLevel()));
    }

    @Override
    public @org.jetbrains.annotations.Nullable Component getChatDisplay() {
        if (FactionConfig.server().factionLordPrefixInChat.get()) {
            return getPlayerLord().filter(x -> x.getLordLevel() > 0).map(ILordPlayer::getLordTitle).orElse(null);
        }
        return null;
    }

    @Override
    public AttachmentType<?> getType() {
        return ModAttachments.WEREWOLF_PLAYER.get();
    }

    @Override
    public int getEyeType(WerewolfForm form) {
        return this.customization.getEyeType(form);
    }

    @Override
    public int getSkinType(WerewolfForm form) {
        return this.customization.getSkinType(form);
    }

    @Override
    public boolean hasGlowingEyes(WerewolfForm form) {
        return this.customization.hasGlowingEyes(form);
    }

    public void dropEquipment() {
        this.inventory.dropEquipment();
    }

    public static class AttachmentOptions extends AttachmentSynchronization.PlayerOptions<WerewolfPlayer> {
        @Override
        protected WerewolfPlayer create(Player player) {
            return new WerewolfPlayer(player);
        }
    }
}
