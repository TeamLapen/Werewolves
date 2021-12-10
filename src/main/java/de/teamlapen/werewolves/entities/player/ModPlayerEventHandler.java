package de.teamlapen.werewolves.entities.player;

import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.vampirism.api.entity.factions.IFactionPlayerHandler;
import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.*;
import de.teamlapen.werewolves.effects.UnWerewolfEffectInstance;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.entities.werewolf.WerewolfBaseEntity;
import de.teamlapen.werewolves.mixin.LivingEntityInvoker;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WReference;
import de.teamlapen.werewolves.util.WerewolfForm;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.UUID;

public class ModPlayerEventHandler {
    private static final Logger LOGGER = LogManager.getLogger();

    public static final UUID CLAWS = UUID.fromString("70435284-afcd-4470-85c2-d9b36b3d94e8");

    @SubscribeEvent
    public void onAttachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            try {
                event.addCapability(REFERENCE.WEREWOLF_PLAYER_KEY, WerewolfPlayer.createNewCapability((PlayerEntity) event.getObject()));
            } catch (Exception e) {
                LOGGER.error("Failed to attach capabilities to player. Player: {}", event.getObject());
                throw e;
            }
        }
    }

    @SubscribeEvent
    public void onFootEatenStart(LivingEntityUseItemEvent.Start event) {
        if (event.getEntity() instanceof PlayerEntity && Helper.isWerewolf((PlayerEntity) event.getEntity())) {
            if (!Helper.canWerewolfPlayerEatItem(((PlayerEntity) event.getEntity()), event.getItem())){
                event.setCanceled(true);
                ((PlayerEntity) event.getEntity()).displayClientMessage(new TranslationTextComponent("text.werewolves.no_meat"), true);
            }
        }
    }

    @SubscribeEvent
    public void onFootEatenFinish(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof PlayerEntity && Helper.isWerewolf((PlayerEntity) event.getEntity())) {
            if (Helper.isRawMeat(event.getItem())) {
                ((PlayerEntity) event.getEntityLiving()).getFoodData().eat(event.getItem().getItem(), event.getItem());
            }
        }
    }

    @SubscribeEvent
    public void onKilled(LivingDeathEvent event) {
        if (event.getSource() instanceof EntityDamageSource && event.getSource().getEntity() instanceof PlayerEntity && Helper.isWerewolf(((PlayerEntity) event.getSource().getEntity()))) {
            WerewolfPlayer player = WerewolfPlayer.get(((PlayerEntity) event.getSource().getEntity()));
            if (player.getSkillHandler().isSkillEnabled(WerewolfSkills.health_after_kill)) {
                ((PlayerEntity) event.getSource().getEntity()).addEffect(new EffectInstance(Effects.REGENERATION, player.getSkillHandler().isRefinementEquipped(ModRefinements.health_after_kill)?5: 4, 10));
            }/* else if (player.getSkillHandler().isSkillEnabled(WerewolfSkills.speed_after_kill)) {
                player.getRepresentingPlayer().addPotionEffect(new EffectInstance(Effects.SPEED, 40));
            }*/
        }
    }

    @SubscribeEvent
    public void onFall(LivingFallEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity && Helper.isWerewolf(((PlayerEntity) event.getEntityLiving()))) {
            WerewolfPlayer werewolf = WerewolfPlayer.get(((PlayerEntity) event.getEntity()));
            if (werewolf.getSkillHandler().isSkillEnabled(WerewolfSkills.wolf_pawn)) {
                event.setDistance(event.getDistance() * 0.8f);
                event.setDamageMultiplier(event.getDamageMultiplier() * 0.8f);
            }
            if (werewolf.getSpecialAttributes().leap) {
                werewolf.getActionHandler().toggleAction(ModActions.leap);
            }

        }
    }

    @SubscribeEvent
    public void onEquipmentChange(LivingEquipmentChangeEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            if (Helper.isWerewolf(((PlayerEntity) event.getEntity()))) {
                if (WerewolfPlayer.get(((PlayerEntity) event.getEntity())).getForm().isTransformed()) {
                    if (event.getTo().isEmpty()) { // see WerewolfFormAction#applyModifier
                        if (((PlayerEntity) event.getEntity()).getAttribute(Attributes.ATTACK_DAMAGE).getModifier(CLAWS) == null) {
//                            double damage = WerewolvesConfig.BALANCE.PLAYER.werewolf_claw_damage.get();
//                            if (WerewolfPlayer.get(((PlayerEntity) event.getEntity())).getSkillHandler().isSkillEnabled(WerewolfSkills.better_claws)) {
//                                damage += WerewolvesConfig.BALANCE.SKILLS.better_claw_damage.get();
//                            }
//                            ((PlayerEntity) event.getEntity()).getAttribute(Attributes.ATTACK_DAMAGE).applyPersistentModifier(new AttributeModifier(CLAWS, "werewolf_claws", damage, AttributeModifier.Operation.ADDITION));
                        }
                    } else {
                        ((PlayerEntity) event.getEntity()).getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(CLAWS);
                    }
                    WerewolfPlayer.getOpt(((PlayerEntity) event.getEntity())).filter(werewolf -> !(werewolf.getForm().isHumanLike() && werewolf.getSkillHandler().isSkillEnabled(WerewolfSkills.wear_armor))).ifPresent(WerewolfPlayer::requestArmorEvaluation);
                }
            }
        }
    }

    @SubscribeEvent
    public void onJump(LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            if (Helper.isWerewolf(((PlayerEntity) event.getEntity()))) {
                WerewolfPlayer werewolf = WerewolfPlayer.get(((PlayerEntity) event.getEntity()));
                if (werewolf.getSkillHandler().isSkillEnabled(WerewolfSkills.wolf_pawn)) {
                    Vector3d motion = event.getEntity().getDeltaMovement().multiply(1.1, 1.2, 1.1);
                    event.getEntity().setDeltaMovement(motion);
                }

                //unnecessary leap attribute because LivingFallEvent is not called for creative player
                if (werewolf.getActionHandler().isActionActive(ModActions.leap)) {
                    if (werewolf.getSpecialAttributes().leap) {
                        werewolf.getActionHandler().toggleAction(ModActions.leap);
                    } else {
                        werewolf.getSpecialAttributes().leap = true;
                        Vector3d vector3d = event.getEntity().getDeltaMovement();
                        event.getEntity().setDeltaMovement(vector3d.x, vector3d.y  + (((LivingEntityInvoker)event.getEntity()).invokeGetJumpPower_werewolves() * 0.3), vector3d.z);
                    }
                } else {
                    werewolf.getSpecialAttributes().leap = false;
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onWakeUp(PlayerWakeUpEvent event) {
        if (!event.getPlayer().level.isClientSide && event.getPlayer().getEffect(ModEffects.lupus_sanguinem) != null) {
            event.getPlayer().getEffect(ModEffects.lupus_sanguinem).applyEffect(event.getPlayer());
            event.getPlayer().removeEffect(ModEffects.lupus_sanguinem);
        }
    }

    @SubscribeEvent
    public void onItemUseEntity(PlayerInteractEvent.EntityInteract event) {
        if (event.getPlayer().getItemInHand(event.getHand()).getItem() == ModItems.injection_empty) {
            if (event.getTarget() instanceof WerewolfBaseEntity) {
                event.getPlayer().getItemInHand(event.getHand()).shrink(1);
                event.getPlayer().addItem(new ItemStack(ModItems.injection_un_werewolf));
            }
        }
    }

    @SubscribeEvent
    public void onItemUseBlock(PlayerInteractEvent.RightClickBlock event) {
        PlayerEntity player = event.getPlayer();
        if (player.getItemInHand(event.getHand()).getItem() == ModItems.injection_un_werewolf) {
            if (event.getWorld().getBlockState(event.getPos()).getBlock() == ModBlocks.med_chair) {
                ItemStack stack = player.getItemInHand(event.getHand());
                if (player.isAlive()) {
                    IFactionPlayerHandler handler = FactionPlayerHandler.get(event.getPlayer());
                    IPlayableFaction<?> faction = handler.getCurrentFaction();
                    boolean used = false;
                    if (WReference.WEREWOLF_FACTION.equals(faction)) {
                        if (player.getEffect(ModEffects.un_werewolf) == null) {
                            player.addEffect(new UnWerewolfEffectInstance(2000));
                            used = true;
                        } else {
                            player.displayClientMessage(new TranslationTextComponent("text.werewolves.injection.in_use"), true);
                        }
                    } else if (faction != null) {
                        player.displayClientMessage(new TranslationTextComponent("text.werewolves.injection.not_use"), true);
                    }
                    if (used) {
                        stack.shrink(1);
                        if (stack.isEmpty()) {
                            player.inventory.removeItem(stack);
                        }
                    }
                }
            }
            event.setCancellationResult(ActionResultType.SUCCESS);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void playerSize(EntityEvent.Size event) {
        if (event.getEntity() instanceof PlayerEntity && ((PlayerEntity) event.getEntity()).inventory != null) {
            LazyOptional<WerewolfPlayer> werewolf = WerewolfPlayer.getOpt(((PlayerEntity) event.getEntity()));
            Optional<EntitySize> size = werewolf.map(WerewolfPlayer::getForm).flatMap(form -> form.getSize(event.getPose()));
            if (size.isPresent()) {
                event.setNewSize(size.get());
                event.setNewEyeHeight(size.get().height * 0.85F);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerAttacked(LivingAttackEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            if (Helper.isWerewolf(((PlayerEntity) event.getEntity()))) {
                if (event.getEntity().isSprinting() && event.getSource() instanceof EntityDamageSource) {
                    WerewolfPlayer.getOpt(((PlayerEntity) event.getEntity())).filter(w -> w.getForm() == WerewolfForm.SURVIVALIST).map(WerewolfPlayer::getSkillHandler).ifPresent(skillHandler -> {
                        if (skillHandler.isSkillEnabled(WerewolfSkills.movement_tactics)) {
                            float limit = WerewolvesConfig.BALANCE.SKILLS.movement_tactics_doge_chance.get().floatValue();
                            if (skillHandler.isRefinementEquipped(ModRefinements.greater_doge_chance)) {
                                limit += WerewolvesConfig.BALANCE.REFINEMENTS.greater_doge_chance.get().floatValue();
                            }
                            if (((PlayerEntity) event.getEntity()).getRandom().nextFloat() < limit) {
                                event.setCanceled(true);
                            }
                        }
                    });
                } else if (event.getSource() == DamageSource.SWEET_BERRY_BUSH || event.getSource() == DamageSource.CACTUS || event.getSource() == DamageSource.HOT_FLOOR) {
                    if (WerewolfPlayer.getOpt(((PlayerEntity) event.getEntity())).filter(w -> w.getForm().isTransformed()).map(w -> w.getSkillHandler().isSkillEnabled(WerewolfSkills.wolf_pawn)).orElse(false)) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onCriticalHit(CriticalHitEvent event) {
        if (Helper.isWerewolf(event.getPlayer()) && event.getTarget() instanceof LivingEntity) {
            LazyOptional<WerewolfPlayer> wOpt = WerewolfPlayer.getOpt(event.getPlayer());
            if (wOpt.filter(w -> w.getForm() == WerewolfForm.BEAST).map(w -> w.getSkillHandler().isSkillEnabled(WerewolfSkills.throat_seeker) && !UtilLib.canReallySee(((LivingEntity) event.getTarget()), event.getPlayer(), true)).orElse(false)) {
                if (((LivingEntity) event.getTarget()).getHealth() / ((LivingEntity) event.getTarget()).getMaxHealth() < 0.25) {
                    if (event.getPlayer().getRandom().nextInt(4) < 1) {
                        event.setDamageModifier(10000f);
                        event.setResult(Event.Result.ALLOW);
                    }
                }
            }
        }
    }
}
