package de.teamlapen.werewolves.entities;

import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.vampirism.tileentity.TotemHelper;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModTags;
import de.teamlapen.werewolves.core.WerewolfSkills;
import de.teamlapen.werewolves.effects.SilverEffect;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.entities.werewolf.IVillagerTransformable;
import de.teamlapen.werewolves.entities.werewolf.WerewolfTransformable;
import de.teamlapen.werewolves.network.AttackTargetEventPacket;
import de.teamlapen.werewolves.util.*;
import it.unimi.dsi.fastutil.objects.Object2BooleanArrayMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.StrayEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
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
            if (ModTags.Items.SILVER_TOOL.contains(event.getPlayer().getMainHandItem().getItem())) {
                ((LivingEntity) event.getTarget()).addEffect(SilverEffect.createEffect(((LivingEntity) event.getTarget()), WerewolvesConfig.BALANCE.UTIL.silverItemEffectDuration.get()));
            }
        }
        if (event.getTarget() instanceof WerewolfTransformable) {
            if (((WerewolfTransformable) event.getTarget()).canTransform()) {
                 ((WerewolfTransformable) event.getTarget()).transformToWerewolf(WerewolfTransformable.TransformType.TIME_LIMITED);
            }
        }
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        ModifiableAttributeInstance s = event.getEntityLiving().getAttribute(Attributes.ARMOR);
        if (s != null) {
            s.removeModifier(ARMOR_REDUCTION);
            if (event.getSource() instanceof EntityDamageSource && event.getSource().getEntity() != null && Helper.isWerewolf(event.getSource().getEntity())) {
                Set<AttributeModifier> modifiers = new HashSet<>();
                int i = 0;
                for (ItemStack stack : event.getEntityLiving().getArmorSlots()) {
                    modifiers.addAll(stack.getAttributeModifiers(EquipmentSlotType.byTypeAndIndex(EquipmentSlotType.Group.ARMOR, i++)).get(Attributes.ARMOR));
                }
                ModifiableAttributeInstance tmp = new ModifiableAttributeInstance(Attributes.ARMOR, (a -> {
                }));
                s.getModifiers().stream().filter(modifiers::contains).forEach(tmp::addTransientModifier);
                double value = s.getValue() - (s.getValue() - tmp.getValue());
                float levelModifier = WerewolfPlayer.getOptEx(event.getSource().getEntity()).map(player -> player.getLevel() / (float) player.getMaxLevel()).orElse(1f);
                s.addTransientModifier(new AttributeModifier(ARMOR_REDUCTION, "werewolf_attack", -value * ((event.getSource() instanceof BiteDamageSource ? 0.8 : 0.3) * levelModifier), AttributeModifier.Operation.ADDITION));
            }
        }
        if (Helper.isWerewolf(event.getEntity())) {
            if (!event.getSource().isMagic()) {
                float damage = event.getAmount();
                float damageReduction = FormHelper.getForm(event.getEntityLiving()).getDamageReduction().getLeft();
                damageReduction *= event.getEntityLiving() instanceof PlayerEntity ? WerewolfPlayer.getOpt(((PlayerEntity) event.getEntityLiving())).filter(a -> !a.getForm().isHumanLike()).filter(a -> a.getSkillHandler().isSkillEnabled(WerewolfSkills.thick_fur.get())).map(a -> WerewolvesConfig.BALANCE.SKILLS.thick_fur_multiplier.get()).orElse(1D).floatValue() : 1F;
                if (event.getSource().getEntity() != null && Helper.isVampire(event.getSource().getEntity())) {
                    damageReduction *= 0.3;
                }
                damage -= event.getAmount() * damageReduction;
                event.setAmount(MathHelper.clamp(damage, 0, Float.MAX_VALUE));
            }
        }

        if (event.getSource() instanceof EntityDamageSource && event.getSource().getEntity() instanceof LivingEntity) {
            LivingEntity source = ((LivingEntity) event.getSource().getEntity());
            ItemStack handStack = source.getItemInHand(Hand.MAIN_HAND);
            WeaponOilHelper.executeAndReduce(handStack, (stack, oil, duration) -> {
                if (oil.canEffect(stack, event.getEntityLiving())) {
                    event.setAmount(event.getAmount() + oil.getAdditionalDamage(stack, event.getEntityLiving(), event.getAmount()));
                }
            });
        }
    }

    @SubscribeEvent
    public void onAttack(LivingSetAttackTargetEvent event) {
        if (event.getTarget() instanceof ServerPlayerEntity) {
            if (Helper.isWerewolf(((PlayerEntity) event.getTarget()))) {
                WerewolfPlayer.getOpt(((PlayerEntity) event.getTarget())).ifPresent(werewolf -> {
                    if (werewolf.getSkillHandler().isSkillEnabled(WerewolfSkills.sixth_sense.get())) {
                        WerewolvesMod.dispatcher.sendTo(new AttackTargetEventPacket(event.getEntity().getId()), ((ServerPlayerEntity) event.getTarget()));
                    }
                });

            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW) //TODO lower priority so that vampirism does not override our ai changes
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity().level.isClientSide()) return;
        if (event.getEntity() instanceof VillagerEntity) {
            TotemHelper.getTotemNearPos(((ServerWorld) event.getWorld()), event.getEntity().blockPosition(), true).ifPresent(totem -> {
                if (WReference.WEREWOLF_FACTION.equals(totem.getControllingFaction())) {
                    if (((VillagerEntity) event.getEntity()).getRandom().nextInt(6) == 0) {
                        ((IVillagerTransformable) event.getEntity()).setWerewolfFaction(true);
                    }
                }
            });
        }
        if (WerewolvesConfig.BALANCE.UTIL.skeletonIgnoreWerewolves.get()) {
            if (event.getEntity() instanceof SkeletonEntity || event.getEntity() instanceof StrayEntity) {
                makeWerewolfFriendly("skeleton", (AbstractSkeletonEntity) event.getEntity(), NearestAttackableTargetGoal.class, PlayerEntity.class, 2, (entity, predicate) -> new NearestAttackableTargetGoal<>(entity, PlayerEntity.class, 10, true, false, predicate), type -> type == EntityType.SKELETON || type == EntityType.STRAY);
            }
        }
    }

    /**
     * copy from {@link de.teamlapen.vampirism.entity.ModEntityEventHandler#makeVampireFriendly(String, MobEntity, Class, Class, int, BiFunction, Predicate)}
     */
    public static <T extends MobEntity, S extends LivingEntity, Q extends NearestAttackableTargetGoal<S>> void makeWerewolfFriendly(String name, T entity, Class<Q> targetClass, Class<S> targetEntityClass, int attackPriority, BiFunction<T, Predicate<LivingEntity>, Q> replacement, Predicate<EntityType<? extends T>> typeCheck) {
        try {
            Goal target = null;
            for (PrioritizedGoal t : entity.targetSelector.availableGoals) {
                Goal g = t.getGoal();
                if (targetClass.equals(g.getClass()) && t.getPriority() == attackPriority && targetEntityClass.equals(((NearestAttackableTargetGoal<?>) g).targetType)) {
                    target = g;
                    break;
                }
            }
            if (target != null) {

                entity.targetSelector.removeGoal(target);
                EntityType<? extends T> type = (EntityType<? extends T>) entity.getType();
                if (typeCheck.test(type)) {
                    Predicate<LivingEntity> newPredicate = nonWerewolfCheck;
                    if (((Q) target).targetConditions.selector != null) {
                        newPredicate = newPredicate.and(((NearestAttackableTargetGoal<?>) target).targetConditions.selector);
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
        ModifiableAttributeInstance s = event.getEntityLiving().getAttribute(Attributes.ARMOR);
        if (s != null) {
            s.removeModifier(ARMOR_REDUCTION);
        }
        if (event.getSource() instanceof EntityDamageSource) {
            WerewolfPlayer.getOptEx(event.getSource().getEntity()).filter(w -> w.getForm() == WerewolfForm.BEAST).filter(w -> w.getSkillHandler().isSkillEnabled(WerewolfSkills.throat_seeker.get()) && !UtilLib.canReallySee(event.getEntityLiving(), w.getRepresentingPlayer(), true)).ifPresent(werewolf -> {
                if (event.getEntityLiving().getHealth() / event.getEntityLiving().getMaxHealth() < 0.25) {
                    if (werewolf.getRepresentingPlayer().getRandom().nextInt(4) < 1) {
                        event.setAmount(10000f);
                    }
                }
            });
        }
    }
}
