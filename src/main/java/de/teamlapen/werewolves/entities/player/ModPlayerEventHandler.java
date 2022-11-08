package de.teamlapen.werewolves.entities.player;

import de.teamlapen.vampirism.api.entity.factions.IFactionPlayerHandler;
import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.vampirism.entity.player.actions.ActionHandler;
import de.teamlapen.vampirism.items.VampirismItemBloodFoodItem;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.*;
import de.teamlapen.werewolves.effects.inst.UnWerewolfEffectInstance;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.entities.werewolf.WerewolfBaseEntity;
import de.teamlapen.werewolves.mixin.LivingEntityAccessor;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.entity.player.SleepingTimeCheckEvent;
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
        if (event.getObject() instanceof Player) {
            try {
                event.addCapability(REFERENCE.WEREWOLF_PLAYER_KEY, WerewolfPlayer.createNewCapability((Player) event.getObject()));
            } catch (Exception e) {
                LOGGER.error("Failed to attach capabilities to player. Player: {}", event.getObject());
                throw e;
            }
        }
    }

    @SubscribeEvent
    public void onFootEatenStart(LivingEntityUseItemEvent.Start event) {
        if (event.getEntity() instanceof Player && Helper.isWerewolf((Player) event.getEntity())) {
            if (!Helper.canWerewolfPlayerEatItem(((Player) event.getEntity()), event.getItem())) {
                event.setCanceled(true);
                ((Player) event.getEntity()).displayClientMessage(Component.translatable("text.werewolves.no_meat"), true);
            }
        }
    }

    @SubscribeEvent
    public void onFootEatenFinish(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof Player && Helper.isWerewolf((Player) event.getEntity())) {
            if (Helper.isRawMeat(event.getItem())) {
                ((Player) event.getEntity()).getFoodData().eat(event.getItem().getItem(), event.getItem());
            }
            if (event.getItem().getItem() instanceof VampirismItemBloodFoodItem) {
                event.getEntity().removeEffect(MobEffects.CONFUSION);
            }
        }
    }

    @SubscribeEvent
    public void onKilled(LivingDeathEvent event) {
        if (event.getSource() instanceof EntityDamageSource && event.getSource().getEntity() instanceof Player && Helper.isWerewolf(((Player) event.getSource().getEntity()))) {
            WerewolfPlayer player = WerewolfPlayer.get(((Player) event.getSource().getEntity()));
            if (player.getSkillHandler().isSkillEnabled(ModSkills.HEALTH_AFTER_KILL.get())) {
                ((Player) event.getSource().getEntity()).addEffect(new MobEffectInstance(MobEffects.REGENERATION, player.getSkillHandler().isRefinementEquipped(ModRefinements.HEALTH_AFTER_KILL.get()) ? 5 : 4, 10));
            }/* else if (player.getSkillHandler().isSkillEnabled(WerewolfSkills.speed_after_kill)) {
                player.getRepresentingPlayer().addPotionEffect(new EffectInstance(Effects.SPEED, 40));
            }*/
            ((Player) event.getSource().getEntity()).getFoodData().setSaturation(((Player) event.getSource().getEntity()).getFoodData().getSaturationLevel() + 0.5F);
        }
    }

    @SubscribeEvent
    public void onFall(LivingFallEvent event) {
        if (event.getEntity() instanceof Player && Helper.isWerewolf(((Player) event.getEntity()))) {
            WerewolfPlayer werewolf = WerewolfPlayer.get(((Player) event.getEntity()));
            if (werewolf.getSkillHandler().isSkillEnabled(ModSkills.WOLF_PAWN.get())) {
                event.setDistance(event.getDistance() * 0.8f);
                event.setDamageMultiplier(event.getDamageMultiplier() * 0.8f);
            }
            if (werewolf.getSpecialAttributes().leap) {
                werewolf.getActionHandler().toggleAction(ModActions.LEAP.get(), new ActionHandler.ActivationContext());
            }

        }
    }

    @SubscribeEvent
    public void onEquipmentChange(LivingEquipmentChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            if (Helper.isWerewolf(((Player) event.getEntity()))) {
                if (WerewolfPlayer.get(((Player) event.getEntity())).getForm().isTransformed()) {
                    if (event.getTo().isEmpty()) { // see WerewolfFormAction#applyModifier
                        //noinspection StatementWithEmptyBody
                        if (((Player) event.getEntity()).getAttribute(Attributes.ATTACK_DAMAGE).getModifier(CLAWS) == null) {
//                            double damage = WerewolvesConfig.BALANCE.PLAYER.werewolf_claw_damage.get();
//                            if (WerewolfPlayer.get(((PlayerEntity) event.getEntity())).getSkillHandler().isSkillEnabled(WerewolfSkills.better_claws)) {
//                                damage += WerewolvesConfig.BALANCE.SKILLS.better_claw_damage.get();
//                            }
//                            ((PlayerEntity) event.getEntity()).getAttribute(Attributes.ATTACK_DAMAGE).applyPersistentModifier(new AttributeModifier(CLAWS, "werewolf_claws", damage, AttributeModifier.Operation.ADDITION));
                        }
                    } else {
                        ((Player) event.getEntity()).getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(CLAWS);
                    }
                    WerewolfPlayer.getOpt(((Player) event.getEntity())).filter(werewolf -> !(werewolf.getForm().isHumanLike() && werewolf.getSkillHandler().isSkillEnabled(ModSkills.WEAR_ARMOR.get()))).ifPresent(WerewolfPlayer::requestArmorEvaluation);
                }
            }
        }
    }

    @SubscribeEvent
    public void onJump(LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() instanceof Player) {
            if (Helper.isWerewolf(((Player) event.getEntity()))) {
                WerewolfPlayer werewolf = WerewolfPlayer.get(((Player) event.getEntity()));
                if (werewolf.getSkillHandler().isSkillEnabled(ModSkills.WOLF_PAWN.get())) {
                    Vec3 motion = event.getEntity().getDeltaMovement().multiply(1.1, 1.2, 1.1);
                    event.getEntity().setDeltaMovement(motion);
                }

                //unnecessary leap attribute because LivingFallEvent is not called for creative player
                if (werewolf.getActionHandler().isActionActive(ModActions.LEAP.get())) {
                    if (werewolf.getSpecialAttributes().leap) {
                        werewolf.getActionHandler().toggleAction(ModActions.LEAP.get(), new ActionHandler.ActivationContext());
                    } else {
                        werewolf.getSpecialAttributes().leap = true;
                        Vec3 vector3d = event.getEntity().getDeltaMovement();
                        event.getEntity().setDeltaMovement(vector3d.x, vector3d.y + (((LivingEntityAccessor) event.getEntity()).invokeGetJumpPower_werewolves() * 0.3), vector3d.z);
                    }
                } else {
                    werewolf.getSpecialAttributes().leap = false;
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onWakeUp(PlayerWakeUpEvent event) {
        if (!event.getEntity().level.isClientSide && event.getEntity().getEffect(ModEffects.LUPUS_SANGUINEM.get()) != null) {
            event.getEntity().getEffect(ModEffects.LUPUS_SANGUINEM.get()).applyEffect(event.getEntity());
            event.getEntity().removeEffect(ModEffects.LUPUS_SANGUINEM.get());
        }
    }

    @SubscribeEvent
    public void onItemUseEntity(PlayerInteractEvent.EntityInteract event) {
        if (event.getEntity().getItemInHand(event.getHand()).getItem() == ModItems.V.INJECTION_EMPTY.get()) {
            if (event.getTarget() instanceof WerewolfBaseEntity) {
                event.getEntity().getItemInHand(event.getHand()).shrink(1);
                event.getEntity().addItem(ModItems.INJECTION_UN_WEREWOLF.get().getDefaultInstance());
            }
        }
    }

    @SubscribeEvent
    public void onItemUseBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        if (player.getItemInHand(event.getHand()).getItem() == ModItems.INJECTION_UN_WEREWOLF.get()) {
            if (event.getLevel().getBlockState(event.getPos()).getBlock() == ModBlocks.V.MED_CHAIR.get()) {
                ItemStack stack = player.getItemInHand(event.getHand());
                if (player.isAlive()) {
                    IFactionPlayerHandler handler = FactionPlayerHandler.get(event.getEntity());
                    IPlayableFaction<?> faction = handler.getCurrentFaction();
                    boolean used = false;
                    if (WReference.WEREWOLF_FACTION.equals(faction)) {
                        if (player.getEffect(ModEffects.UN_WEREWOLF.get()) == null) {
                            player.addEffect(new UnWerewolfEffectInstance(2000));
                            used = true;
                        } else {
                            player.displayClientMessage(Component.translatable("text.werewolves.injection.in_use"), true);
                        }
                    } else if (faction != null) {
                        player.displayClientMessage(Component.translatable("text.werewolves.injection.not_use"), true);
                    }
                    if (used) {
                        stack.shrink(1);
                        if (stack.isEmpty()) {
                            player.getInventory().removeItem(stack);
                        }
                    }
                }
            }
            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void playerSize(EntityEvent.Size event) {
        if (event.getEntity() instanceof Player player && player.getInventory() != null && event.getEntity().isAlive()) {
            LazyOptional<WerewolfPlayer> werewolf = WerewolfPlayer.getOpt(((Player) event.getEntity()));
            Optional<EntityDimensions> size = werewolf.map(WerewolfPlayer::getForm).flatMap(form -> form.getSize(event.getPose()));
            if (size.isPresent()) {
                event.setNewSize(size.get());
                event.setNewEyeHeight(size.get().height * 0.85F);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerAttacked(LivingAttackEvent event) {
        if (event.getEntity() instanceof Player) {
            if (Helper.isWerewolf(((Player) event.getEntity()))) {
                if (event.getEntity().isSprinting() && event.getSource() instanceof EntityDamageSource) {
                    WerewolfPlayer.getOpt(((Player) event.getEntity())).filter(w -> w.getForm() == WerewolfForm.SURVIVALIST).map(WerewolfPlayer::getSkillHandler).ifPresent(skillHandler -> {
                        if (skillHandler.isSkillEnabled(ModSkills.MOVEMENT_TACTICS.get())) {
                            float limit = WerewolvesConfig.BALANCE.SKILLS.movement_tactics_doge_chance.get().floatValue();
                            if (skillHandler.isRefinementEquipped(ModRefinements.GREATER_DOGE_CHANCE.get())) {
                                limit += WerewolvesConfig.BALANCE.REFINEMENTS.greater_doge_chance.get().floatValue();
                            }
                            if (((Player) event.getEntity()).getRandom().nextFloat() < limit) {
                                event.setCanceled(true);
                            }
                        }
                    });
                } else if (event.getSource() == DamageSource.SWEET_BERRY_BUSH || event.getSource() == DamageSource.CACTUS || event.getSource() == DamageSource.HOT_FLOOR) {
                    if (WerewolfPlayer.getOpt(((Player) event.getEntity())).filter(w -> w.getForm().isTransformed()).map(w -> w.getSkillHandler().isSkillEnabled(ModSkills.WOLF_PAWN.get())).orElse(false)) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onMountEvent(EntityMountEvent event) {
        if (event.isMounting() && WerewolfPlayer.getOptEx(event.getEntityMounting()).map(p -> !p.getForm().isHumanLike()).orElse(false)) {
            event.setCanceled(true);
            ((Player) event.getEntityMounting()).displayClientMessage(Component.translatable("text.werewolves.nomount.transformed"), true);
        }
    }

    @SubscribeEvent
    public void onSleepTime(SleepingTimeCheckEvent event) {

    }

    @SubscribeEvent
    public void isCorrectToolForDrop(PlayerEvent.HarvestCheck event) {
        if(!event.canHarvest() && Helper.isWerewolf(event.getEntity())) {
            WerewolfPlayer.getOpt(event.getEntity()).filter(a -> a.getForm().isTransformed()).ifPresent(werewolf -> {
                event.setCanHarvest(event.getEntity().getMainHandItem().isEmpty() && TierSortingRegistry.isCorrectTierForDrops(werewolf.getDigDropTier(), event.getTargetBlock())
                        && (event.getTargetBlock().is(BlockTags.MINEABLE_WITH_PICKAXE)
                        || event.getTargetBlock().is(BlockTags.MINEABLE_WITH_SHOVEL)
                        || event.getTargetBlock().is(BlockTags.MINEABLE_WITH_HOE)));
            });
        }
    }
}
