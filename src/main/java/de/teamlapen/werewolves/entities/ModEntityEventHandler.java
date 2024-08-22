package de.teamlapen.werewolves.entities;

import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.vampirism.util.TotemHelper;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entities.werewolf.IVillagerTransformable;
import de.teamlapen.werewolves.api.entities.werewolf.TransformType;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfTransformable;
import de.teamlapen.werewolves.api.items.ISilverItem;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModDamageTypes;
import de.teamlapen.werewolves.core.ModSkills;
import de.teamlapen.werewolves.core.ModTags;
import de.teamlapen.werewolves.effects.SilverEffect;
import de.teamlapen.werewolves.effects.WerewolfWeakeningEffect;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.mixin.GoalSelectorAccessor;
import de.teamlapen.werewolves.mixin.NearestAttackabletargetGoalAccessor;
import de.teamlapen.werewolves.mixin.TargetingConditionsAccessor;
import de.teamlapen.werewolves.network.ClientboundAttackTargetEventPacket;
import de.teamlapen.werewolves.util.FormHelper;
import de.teamlapen.werewolves.util.Helper;
import it.unimi.dsi.fastutil.objects.Object2BooleanArrayMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

public class ModEntityEventHandler {

    private final static Logger LOGGER = LogManager.getLogger();
    private static final Predicate<LivingEntity> nonWerewolfCheck = entity -> !Helper.isWerewolf(entity);
    private static final Object2BooleanMap<String> entityAIReplacementWarnMap = new Object2BooleanArrayMap<>();
    private static final UUID ARMOR_REDUCTION = UUID.fromString("5b7612e9-1847-435c-b4eb-a455af4ce8c7");

    @SubscribeEvent
    public void onEntityAttacked(AttackEntityEvent event) {
        if (event.getTarget() instanceof LivingEntity && Helper.isWerewolf(event.getTarget())) {
            if (event.getEntity().getMainHandItem().is(ModTags.Items.SILVER_TOOL)) {
                ((LivingEntity) event.getTarget()).addEffect(SilverEffect.createSilverEffect(((LivingEntity) event.getTarget()), WerewolvesConfig.BALANCE.UTIL.silverItemEffectDuration.get(), 1));
            }
        }
        if (event.getTarget() instanceof WerewolfTransformable) {
            if (((WerewolfTransformable) event.getTarget()).canTransform()) {
                ((WerewolfTransformable) event.getTarget()).transformToWerewolf(TransformType.TIME_LIMITED);
            }
        }
        if (Helper.isWerewolf(event.getEntity()) && event.getTarget() instanceof LivingEntity target) {
            int sum = StreamSupport.stream(target.getArmorSlots().spliterator(), false).mapToInt(stack -> stack.getItem() instanceof ISilverItem ? 1 : 0).sum();
            if (sum > 0) {
                event.getEntity().addEffect(SilverEffect.createSilverEffect(event.getEntity(), WerewolvesConfig.BALANCE.UTIL.silverArmorAttackEffectDuration.get() * sum, 0));
            }
        }
    }

    @SubscribeEvent
    public void onTargetChange(LivingChangeTargetEvent event) {
        if (event.getNewAboutToBeSetTarget() instanceof ServerPlayer player && Helper.isWerewolf(player)) {
            if(WerewolfPlayer.get(player).getSkillHandler().isSkillEnabled(ModSkills.SIXTH_SENSE.get())) {
                player.connection.send(new ClientboundAttackTargetEventPacket(event.getEntity().getId()));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW) // lower priority so that vampirism does not override our ai changes
    public void onEntityJoinWorld(EntityJoinLevelEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        if (event.getEntity() instanceof Villager) {
            TotemHelper.getTotemNearPos(((ServerLevel) event.getLevel()), event.getEntity().blockPosition(), true).ifPresent(totem -> {
                if (WReference.WEREWOLF_FACTION.equals(totem.getControllingFaction())) {
                    if (((Villager) event.getEntity()).getRandom().nextInt(6) == 0) {
                        ((IVillagerTransformable) event.getEntity()).setWerewolfFaction(true);
                    }
                }
            });
        }
        if (WerewolvesConfig.BALANCE.UTIL.skeletonIgnoreWerewolves.get()) {
            if (event.getEntity() instanceof Skeleton || event.getEntity() instanceof Stray) {
                //noinspection unchecked
                makeWerewolfFriendly("skeleton", (AbstractSkeleton) event.getEntity(), NearestAttackableTargetGoal.class, Player.class, 2, (entity, predicate) -> new NearestAttackableTargetGoal<>(entity, Player.class, 10, true, false, predicate), type -> type == EntityType.SKELETON || type == EntityType.STRAY);
            }
        }
    }

    /**
     * copy from {@link de.teamlapen.vampirism.entity.ModEntityEventHandler#makeVampireFriendly(String, Mob, Class, Class, int, BiFunction, Predicate)}
     */
    @SuppressWarnings("unchecked")
    public static <T extends Mob, S extends LivingEntity, Q extends NearestAttackableTargetGoal<S>> void makeWerewolfFriendly(String name, T entity, Class<Q> targetClass, Class<S> targetEntityClass, int attackPriority, BiFunction<T, Predicate<LivingEntity>, Q> replacement, Predicate<EntityType<? extends T>> typeCheck) {
        try {
            Goal target = null;
            for (WrappedGoal t : ((GoalSelectorAccessor) entity.targetSelector).getAvailableGoals()) {
                Goal g = t.getGoal();
                if (targetClass.equals(g.getClass()) && t.getPriority() == attackPriority && targetEntityClass.equals(((NearestAttackabletargetGoalAccessor<?>) g).getTargetType())) {
                    target = g;
                    break;
                }
            }
            if (target != null) {

                entity.targetSelector.removeGoal(target);
                EntityType<? extends T> type = (EntityType<? extends T>) entity.getType();
                if (typeCheck.test(type)) {
                    Predicate<LivingEntity> newPredicate = nonWerewolfCheck;
                    if (((TargetingConditionsAccessor) ((NearestAttackabletargetGoalAccessor<?>)target).getTargetConditions()).getSelector() != null) {
                        newPredicate = newPredicate.and(((TargetingConditionsAccessor) ((NearestAttackabletargetGoalAccessor<?>) target).getTargetConditions()).getSelector());
                    }
                    entity.targetSelector.addGoal(attackPriority, replacement.apply(entity, newPredicate));
                }
            } else {
                if (entityAIReplacementWarnMap.getOrDefault(name, true)) {
                    LOGGER.warn("Could not replace {} attack target task for {}", name, entity.getType().getDescription());
                    entityAIReplacementWarnMap.put(name, false);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Could not replace {} attack target task for {}", name, entity.getType().getDescription(), e);
        }
    }

    @SubscribeEvent
    public void onWerewolfAttackPre(LivingDamageEvent.Pre event) {
        if (event.getSource().is(DamageTypeTags.IS_PLAYER_ATTACK) && event.getSource().getEntity() instanceof Player player) {
            WerewolfPlayer werewolf = WerewolfPlayer.get(player);
            if (checkThroatSeeker(event, player, werewolf)) {
                return;
            }
        }
    }

    @SubscribeEvent
    public void onWerewolfAttackedPre(LivingDamageEvent.Pre event) {
        if (checkWerewolfResistance(event)) {
            return;
        }
    }

    @SubscribeEvent
    public void onWerewolfAttackIncoming(LivingIncomingDamageEvent event) {
        if (event.getSource().is(ModTags.DamageTypes.WEREWOLF_ARMOR_REDUCTION) && event.getSource().getEntity() instanceof Player player) {
            WerewolfPlayer werewolf = WerewolfPlayer.get(player);
            checkBite(event, player, werewolf);
        }
    }

    @SubscribeEvent
    public void onWerewolfWeakeningEffectApplied(MobEffectEvent.Applicable event) {
        if (event.getEffectInstance().getEffect() instanceof WerewolfWeakeningEffect && !Helper.isWerewolf(event.getEntity())) {
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
        }
    }

    private boolean checkWerewolfResistance(LivingDamageEvent.Pre event) {
        if (!event.getSource().is(DamageTypeTags.WITCH_RESISTANT_TO) && Helper.isWerewolf(event.getEntity())) {
            float damage = event.getNewDamage();
            float damageReduction = FormHelper.getForm(event.getEntity()).getDamageReduction();
            if (event.getEntity() instanceof Player player) {
                WerewolfPlayer werewolfPlayer = WerewolfPlayer.get(player);
                if (!werewolfPlayer.getForm().isTransformed() && werewolfPlayer.getSkillHandler().isSkillEnabled(ModSkills.THICK_FUR.get())) {
                    damageReduction *= WerewolvesConfig.BALANCE.SKILLS.thick_fur_multiplier.get().floatValue();
                }
            }
            if (event.getSource().getEntity() != null && Helper.isVampire(event.getSource().getEntity())) {
                damageReduction *= 0.3f;
            }
            event.setNewDamage(damage * (1 - damageReduction));
        }
        return false;
    }

    private boolean checkThroatSeeker(LivingDamageEvent.Pre event, Player player, WerewolfPlayer werewolf) {
        if (werewolf.getForm() == WerewolfForm.BEAST && werewolf.getSkillHandler().isSkillEnabled(ModSkills.THROAT_SEEKER) && !UtilLib.canReallySee(event.getEntity(), player, true) && player.getRandom().nextInt(4) < 1) {
            event.setNewDamage(10000f);
            return true;
        }
        return false;
    }

    private void checkBite(LivingIncomingDamageEvent event, Player player, WerewolfPlayer werewolf) {
        float reductionModifier = werewolf.getLevel() / (float) werewolf.getMaxLevel();
        if (event.getSource().is(ModDamageTypes.BITE)) {
            reductionModifier *= 0.8f;
        } else {
            reductionModifier *= 0.3f;
        }

        float finalReductionModifier = reductionModifier;
        event.addReductionModifier(DamageContainer.Reduction.ARMOR, (type, reductionIn) -> reductionIn * (1- finalReductionModifier));
    }


}
