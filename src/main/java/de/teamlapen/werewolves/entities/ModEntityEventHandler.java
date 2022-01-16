package de.teamlapen.werewolves.entities;

import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.vampirism.tileentity.TotemHelper;
import de.teamlapen.vampirism.tileentity.TotemTileEntity;
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
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class ModEntityEventHandler {

    private final static Logger LOGGER = LogManager.getLogger();
    private static final Predicate<LivingEntity> nonWerewolfCheck = entity -> !Helper.isWerewolf(entity);
    private static final Object2BooleanMap<String> entityAIReplacementWarnMap = new Object2BooleanArrayMap<>();

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
    public void onEntityAttack(LivingHurtEvent event) {
        if (event.getSource().getEntity() != null) {
            if (Helper.isWerewolf(event.getSource().getEntity())) {
                ((DamageSourceExtended) event.getSource()).setArmorIgnorePerc(0.9f);
            }
        }
        if (Helper.isWerewolf(event.getEntity())) {
            Pair<Float, Float> damageReduction = FormHelper.getForm(event.getEntityLiving()).getDamageReduction();
            float multiplier = event.getEntityLiving() instanceof PlayerEntity ? WerewolfPlayer.getOpt(((PlayerEntity) event.getEntityLiving())).filter(a -> a.getSkillHandler().isSkillEnabled(WerewolfSkills.thick_fur)).map(a -> WerewolvesConfig.BALANCE.SKILLS.thick_fur_multiplier.get()).orElse(1D).floatValue() : 1F;
            event.setAmount(MathHelper.clamp(event.getAmount() - event.getAmount() * damageReduction.getLeft() * multiplier, 0, Float.MAX_VALUE));
        }
    }

    @SubscribeEvent
    public void onAttack(LivingSetAttackTargetEvent event) {
        if (event.getTarget() instanceof ServerPlayerEntity) {
            if (Helper.isWerewolf(((PlayerEntity) event.getTarget()))) {
                WerewolfPlayer.getOpt(((PlayerEntity) event.getTarget())).ifPresent(werewolf -> {
                    if (werewolf.getSkillHandler().isSkillEnabled(WerewolfSkills.sixth_sense)) {
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
            Optional<TotemTileEntity> totemOpt = TotemHelper.getTotemNearPos(((ServerWorld) event.getWorld()), event.getEntity().blockPosition(), true);
            totemOpt.ifPresent(totem -> {
                if (WReference.WEREWOLF_FACTION.equals(totem.getControllingFaction())) {
                    if (((VillagerEntity) event.getEntity()).getRandom().nextBoolean()) {
                        ((IVillagerTransformable) event.getEntity()).setWerewolfFaction(true);
                    }
                }
            });
        }
        if (WerewolvesConfig.BALANCE.UTIL.skeletonIgnoreWerewolves.get()) {
            if (event.getEntity() instanceof SkeletonEntity) {
                makeWerewolfFriendly("skeleton", (SkeletonEntity) event.getEntity(), NearestAttackableTargetGoal.class, PlayerEntity.class, 2, (entity, predicate) -> new NearestAttackableTargetGoal<>(entity, PlayerEntity.class, 10, true, false, predicate), type -> type == EntityType.SKELETON);
            }
        }
    }

    /**
     * copy from {@link de.teamlapen.vampirism.entity.ModEntityEventHandler#makeVampireFriendly(String, MobEntity, Class, Class, int, BiFunction, Predicate)}
     */
    public static <T extends MobEntity, S extends LivingEntity, Q extends NearestAttackableTargetGoal<S>> void makeWerewolfFriendly(String name, T e, Class<Q> targetClass, Class<S> targetEntityClass, int attackPriority, BiFunction<T, Predicate<LivingEntity>, Q> replacement, Predicate<EntityType<? extends T>> typeCheck) {
        Goal target = null;
        for (PrioritizedGoal t : e.targetSelector.availableGoals) {
            Goal g = t.getGoal();
            if (targetClass.equals(g.getClass()) && t.getPriority() == attackPriority && targetEntityClass.equals(((NearestAttackableTargetGoal<?>) g).targetType)) {
                target = g;
                break;
            }
        }
        if (target != null) {
            e.targetSelector.removeGoal(target);
            EntityType<? extends T> type = (EntityType<? extends T>) e.getType();
            if (typeCheck.test(type)) {
                e.targetSelector.addGoal(attackPriority, replacement.apply(e, nonWerewolfCheck.and(((Q) target).targetConditions.selector)));
            }
        } else {
            if (entityAIReplacementWarnMap.getOrDefault(name, true)) {
                LOGGER.warn("Could not replace {} attack target task for {}", name, e.getType().getDescription());
                entityAIReplacementWarnMap.put(name, false);
            }

        }
    }

    @SubscribeEvent
    public void onLivingDamage(LivingDamageEvent event) {
        if (event.getSource() instanceof EntityDamageSource) {
            WerewolfPlayer.getOptEx(event.getSource().getEntity()).filter(w -> w.getForm() == WerewolfForm.BEAST).filter(w -> w.getSkillHandler().isSkillEnabled(WerewolfSkills.throat_seeker) && !UtilLib.canReallySee(event.getEntityLiving(), w.getRepresentingPlayer(), true)).ifPresent(werewolf -> {
                if (event.getEntityLiving().getHealth() / event.getEntityLiving().getMaxHealth() < 0.25) {
                    if (werewolf.getRepresentingPlayer().getRandom().nextInt(4) < 1) {
                        event.setAmount(10000f);
                    }
                }
            });
        }
    }
}
