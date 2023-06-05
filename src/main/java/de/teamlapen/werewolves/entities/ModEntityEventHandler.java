package de.teamlapen.werewolves.entities;

import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.vampirism.util.TotemHelper;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entities.werewolf.IVillagerTransformable;
import de.teamlapen.werewolves.api.entities.werewolf.TransformType;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfTransformable;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModDamageTypes;
import de.teamlapen.werewolves.core.ModSkills;
import de.teamlapen.werewolves.core.ModTags;
import de.teamlapen.werewolves.effects.SilverEffect;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class ModEntityEventHandler {

    private final static Logger LOGGER = LogManager.getLogger();
    private static final Predicate<LivingEntity> nonWerewolfCheck = entity -> !Helper.isWerewolf(entity);
    private static final Object2BooleanMap<String> entityAIReplacementWarnMap = new Object2BooleanArrayMap<>();
    private static final UUID ARMOR_REDUCTION = UUID.fromString("5b7612e9-1847-435c-b4eb-a455af4ce8c7");

    @SubscribeEvent
    public void onEntityAttacked(AttackEntityEvent event) {
        if (event.getTarget() instanceof LivingEntity && Helper.isWerewolf(event.getTarget())) {
            if (event.getEntity().getMainHandItem().is(ModTags.Items.SILVER_TOOL)) {
                ((LivingEntity) event.getTarget()).addEffect(SilverEffect.createEffect(((LivingEntity) event.getTarget()), WerewolvesConfig.BALANCE.UTIL.silverItemEffectDuration.get()));
            }
        }
        if (event.getTarget() instanceof WerewolfTransformable) {
            if (((WerewolfTransformable) event.getTarget()).canTransform()) {
                ((WerewolfTransformable) event.getTarget()).transformToWerewolf(TransformType.TIME_LIMITED);
            }
        }
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        AttributeInstance s = event.getEntity().getAttribute(Attributes.ARMOR);
        if (s != null) {
            s.removeModifier(ARMOR_REDUCTION);
            if (event.getSource().getEntity() != null && Helper.isWerewolf(event.getSource().getEntity())) {
                Set<AttributeModifier> modifiers = new HashSet<>();
                int i = 0;
                for (ItemStack stack : event.getEntity().getArmorSlots()) {
                    modifiers.addAll(stack.getAttributeModifiers(EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, i++)).get(Attributes.ARMOR));
                }
                AttributeInstance tmp = new AttributeInstance(Attributes.ARMOR, (a -> {
                }));
                s.getModifiers().stream().filter(modifiers::contains).forEach(tmp::addTransientModifier);
                double value = s.getValue() - (s.getValue() - tmp.getValue());
                float levelModifier = WerewolfPlayer.getOptEx(event.getSource().getEntity()).map(player -> player.getLevel() / (float) player.getMaxLevel()).orElse(1f);
                s.addTransientModifier(new AttributeModifier(ARMOR_REDUCTION, "werewolf_attack", -value * ((event.getSource().is(ModDamageTypes.BITE) ? 0.8 : 0.3) * levelModifier), AttributeModifier.Operation.ADDITION));
            }
        }
        if (Helper.isWerewolf(event.getEntity())) {
            if (!event.getSource().is(DamageTypeTags.WITCH_RESISTANT_TO)) {
                float damage = event.getAmount();
                float damageReduction = FormHelper.getForm(event.getEntity()).getDamageReduction();
                damageReduction *= event.getEntity() instanceof Player ? WerewolfPlayer.getOpt(((Player) event.getEntity())).filter(a -> !a.getForm().isHumanLike()).filter(a -> a.getSkillHandler().isSkillEnabled(ModSkills.THICK_FUR.get())).map(a -> WerewolvesConfig.BALANCE.SKILLS.thick_fur_multiplier.get()).orElse(1D).floatValue() : 1F;
                if (event.getSource().getEntity() != null && Helper.isVampire(event.getSource().getEntity())) {
                    damageReduction *= 0.3;
                }
                damage -= event.getAmount() * damageReduction;
                event.setAmount(Mth.clamp(damage, 0, Float.MAX_VALUE));
            }
        }

    }

    @SubscribeEvent
    public void onTargetChange(LivingChangeTargetEvent event) {
        if (event.getNewTarget() instanceof ServerPlayer player && Helper.isWerewolf(player)) {
            WerewolfPlayer.getOpt(player).filter(werewolf -> werewolf.getSkillHandler().isSkillEnabled(ModSkills.SIXTH_SENSE.get())).ifPresent(werewolf -> {
                WerewolvesMod.dispatcher.sendTo(new ClientboundAttackTargetEventPacket(event.getEntity().getId()), player);
            });
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW) // lower priority so that vampirism does not override our ai changes
    public void onEntityJoinWorld(EntityJoinLevelEvent event) {
        if (event.getEntity().level.isClientSide()) return;
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
            LOGGER.error("Could not replace " + name + " attack target task for " + entity.getType().getDescription(), e);
        }
    }

    @SubscribeEvent
    public void onLivingDamage(LivingDamageEvent event) {
        AttributeInstance s = event.getEntity().getAttribute(Attributes.ARMOR);
        if (s != null) {
            s.removeModifier(ARMOR_REDUCTION);
        }

        WerewolfPlayer.getOptEx(event.getSource().getEntity()).filter(w -> w.getForm() == WerewolfForm.BEAST).filter(w -> w.getSkillHandler().isSkillEnabled(ModSkills.THROAT_SEEKER.get()) && !UtilLib.canReallySee(event.getEntity(), w.getRepresentingPlayer(), true)).ifPresent(werewolf -> {
            if (event.getEntity().getHealth() / event.getEntity().getMaxHealth() < 0.25) {
                if (werewolf.getRepresentingPlayer().getRandom().nextInt(4) < 1) {
                    event.setAmount(10000f);
                }
            }
        });
    }
}
