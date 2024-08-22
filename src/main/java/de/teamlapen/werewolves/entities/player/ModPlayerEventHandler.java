package de.teamlapen.werewolves.entities.player;

import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.api.entity.player.skills.ISkillHandler;
import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.vampirism.entity.player.actions.ActionHandler;
import de.teamlapen.vampirism.items.VampirismItemBloodFoodItem;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.api.items.ISilverItem;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.*;
import de.teamlapen.werewolves.effects.SilverEffect;
import de.teamlapen.werewolves.effects.inst.UnWerewolfEffectInstance;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.entities.werewolf.WerewolfBaseEntity;
import de.teamlapen.werewolves.mixin.LivingEntityAccessor;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.neoforge.event.entity.EntityMountEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.*;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.stream.StreamSupport;

public class ModPlayerEventHandler {

    @SubscribeEvent
    public void onFootEatenFinish(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof Player && Helper.isWerewolf((Player) event.getEntity())) {
            if (event.getItem().getItem() instanceof VampirismItemBloodFoodItem) {
                event.getEntity().removeEffect(MobEffects.CONFUSION);
            }
        }
    }

    @SubscribeEvent
    public void onKilled(LivingDeathEvent event) {
        if (!Helper.isNoLiving(event.getEntity()) && event.getSource().getEntity() instanceof Player && Helper.isWerewolf(((Player) event.getSource().getEntity()))) {
            WerewolfPlayer player = WerewolfPlayer.get(((Player) event.getSource().getEntity()));
            if (player.getSkillHandler().isSkillEnabled(ModSkills.HEALTH_AFTER_KILL.get())) {
                ((Player) event.getSource().getEntity()).addEffect(new MobEffectInstance(MobEffects.REGENERATION, player.getSkillHandler().isRefinementEquipped(ModRefinements.HEALTH_AFTER_KILL.get()) ? 5 : 4, 10));
            }
            ((Player) event.getSource().getEntity()).getFoodData().setSaturation(((Player) event.getSource().getEntity()).getFoodData().getSaturationLevel() + 0.5F);
        }
    }

    @SubscribeEvent
    public void onFall(PlayerFlyableFallEvent event) {
        if (Helper.isWerewolf(event.getEntity())) {
            WerewolfPlayer werewolf = WerewolfPlayer.get( event.getEntity());
            if (werewolf.getSpecialAttributes().leap) {
                ((ActionHandler<IWerewolfPlayer>) werewolf.getActionHandler()).deactivateAction(ModActions.LEAP.get(), false, true);
            }
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
                ((ActionHandler<IWerewolfPlayer>) werewolf.getActionHandler()).deactivateAction(ModActions.LEAP.get(), false, true);
            }

            if (werewolf.getForm().isTransformed() && werewolf.getSkillHandler().isSkillEnabled(ModSkills.JUMP.get())) {
                event.setDistance(event.getDistance() - 0.5f);
            }

        }
    }

    @SubscribeEvent
    public void onEquipmentChange(LivingEquipmentChangeEvent event) {
        if (event.getSlot() == EquipmentSlot.MAINHAND && event.getEntity() instanceof Player player) {
            WerewolfPlayer.get(player).checkToolDamage(event.getFrom(), event.getTo(), false);
        }
    }

    @SubscribeEvent
    public void onJump(LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() instanceof Player) {
            if (Helper.isWerewolf(((Player) event.getEntity()))) {
                LivingEntity entity = event.getEntity();
                WerewolfPlayer werewolf = WerewolfPlayer.get(((Player) entity));
                if (werewolf.getSkillHandler().isSkillEnabled(ModSkills.WOLF_PAWN.get())) {
                    Vec3 motion = entity.getDeltaMovement().multiply(1.1, 1.2, 1.1);
                    entity.setDeltaMovement(motion);
                }

                //unnecessary leap attribute because LivingFallEvent is not called for creative player
                if (werewolf.getActionHandler().isActionActive(ModActions.LEAP.get())) {
                    if (!werewolf.getSpecialAttributes().leap) {
                        werewolf.getSpecialAttributes().leap = true;
                        Vec3 viewVector = entity.getViewVector(0);
                        Vec3 leap = new Vec3(viewVector.x, ((LivingEntityAccessor) entity).getJumpPower$werewolves() * 0.5, viewVector.z);
                        float leapModifier = werewolf.getForm().getLeapModifier();
                        entity.addDeltaMovement(leap.multiply(leapModifier, leapModifier, leapModifier));
                    }
                }

                if (werewolf.getForm().isTransformed() && werewolf.getSkillHandler().isSkillEnabled(ModSkills.JUMP.get())) {
                    entity.addDeltaMovement(new Vec3(0, 0.5 * 0.1,0));
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onWakeUp(PlayerWakeUpEvent event) {
        if (!event.getEntity().level().isClientSide && event.getEntity().getEffect(ModEffects.LUPUS_SANGUINEM) != null) {
            event.getEntity().getEffect(ModEffects.LUPUS_SANGUINEM).getEffect().value().applyEffectTick(event.getEntity(), 0);
            event.getEntity().removeEffect(ModEffects.LUPUS_SANGUINEM);
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
                    IPlayableFaction<?> faction = FactionPlayerHandler.get(event.getEntity()).getCurrentFaction();
                    if (WReference.WEREWOLF_FACTION.equals(faction)) {
                        if (player.getEffect(ModEffects.UN_WEREWOLF) == null) {
                            player.addEffect(new UnWerewolfEffectInstance(2000));
                            stack.shrink(1);
                            if (stack.isEmpty()) {
                                player.getInventory().removeItem(stack);
                            }
                        } else {
                            player.displayClientMessage(Component.translatable("text.werewolves.injection.in_use"), true);
                        }
                    } else {
                        player.displayClientMessage(Component.translatable("text.werewolves.injection.not_use"), true);
                    }
                }
            }
            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void useItem(PlayerInteractEvent.RightClickItem event) {
        if (Helper.isWerewolf(event.getEntity()) && event.getItemStack().getItem() instanceof Equipable) {
            WerewolfPlayer werewolf = WerewolfPlayer.get(event.getEntity());
            if (!werewolf.canWearArmor(event.getItemStack())) {
                event.setCancellationResult(InteractionResult.FAIL);
                event.setCanceled(true);
                event.getEntity().displayClientMessage(Component.translatable("text.werewolves.equipment.equip_failed"), true);
            }
        }
    }

    @SubscribeEvent
    public void playerSize(EntityEvent.Size event) {
        if (event.getEntity() instanceof Player player && event.getEntity().isAlive()) {
            WerewolfPlayer.get(((Player) event.getEntity())).getForm().getSize(event.getPose()).ifPresent(size -> {
                event.setNewSize(size);
            });
        }
    }

    @SubscribeEvent
    public void onEntityIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player player && Helper.isWerewolf(player)) {
            WerewolfPlayer werewolf = WerewolfPlayer.get(player);
            ISkillHandler<IWerewolfPlayer> skillHandler = werewolf.getSkillHandler();
            if (event.getSource().is(ModTags.DamageTypes.WEREWOLF_FUR_IMMUNE) && werewolf.getForm().isTransformed() && skillHandler.isSkillEnabled(ModSkills.WOLF_PAWN.get())) {
                event.setCanceled(true);
            }

            if (werewolf.getForm() == WerewolfForm.SURVIVALIST) {
                if (player.getDeltaMovement().lengthSqr() > 0.01 && skillHandler.isSkillEnabled(ModSkills.ARROW_AWARENESS.get()) && event.getSource().is(DamageTypes.ARROW) && event.getEntity().getRandom().nextFloat() < 0.4) {
                    event.setCanceled(true);
                } else if (player.isSprinting() && event.getSource().getEntity() != null && skillHandler.isSkillEnabled(ModSkills.MOVEMENT_TACTICS.get())) {
                    float limit = WerewolvesConfig.BALANCE.SKILLS.movement_tactics_doge_chance.get().floatValue();
                    if (skillHandler.isRefinementEquipped(ModRefinements.GREATER_DOGE_CHANCE.get())) {
                        limit += WerewolvesConfig.BALANCE.REFINEMENTS.greater_doge_chance.get().floatValue();
                    }
                    if (player.getRandom().nextFloat() < limit) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingDamageHurtPost(LivingDamageEvent.Post event) {
        if (event.getSource().is(DamageTypes.MOB_ATTACK) && event.getSource().getDirectEntity() instanceof LivingEntity source && Helper.isWerewolf(source)) {
            int sum = StreamSupport.stream(event.getEntity().getArmorSlots().spliterator(), false).mapToInt(stack -> stack.getItem() instanceof ISilverItem ? 1 : 0).sum();
            if (sum > 0) {
                source.addEffect(SilverEffect.createSilverEffect(source, WerewolvesConfig.BALANCE.UTIL.silverArmorAttackEffectDuration.get() * sum, 0));
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
    public void isCorrectToolForDrop(PlayerEvent.HarvestCheck event) {
        if(!event.canHarvest() && Helper.isWerewolf(event.getEntity())) {
            WerewolfPlayer werewolf = WerewolfPlayer.get(event.getEntity());
            if (werewolf.getForm().isTransformed()) {
                event.setCanHarvest(event.getEntity().getMainHandItem().isEmpty() && werewolf.getDigDropTier().map(tier -> !event.getTargetBlock().is(tier.getIncorrectBlocksForDrops())).orElse(false)
                        && (event.getTargetBlock().is(BlockTags.MINEABLE_WITH_PICKAXE)
                        || event.getTargetBlock().is(BlockTags.MINEABLE_WITH_SHOVEL)
                        || event.getTargetBlock().is(BlockTags.MINEABLE_WITH_HOE)));
            }
        }
    }

    @SubscribeEvent
    public void tickTool(PlayerTickEvent.Post event) {
        if (Helper.isWerewolf(event.getEntity())) {
            if ((Helper.isSilverItem(event.getEntity().getMainHandItem())) || Helper.isSilverItem(event.getEntity().getOffhandItem()) && event.getEntity().level().getGameTime() % 10 == 0) {
                event.getEntity().addEffect(SilverEffect.createSilverEffect(event.getEntity(), 20, 1, true));
            }
            if (StreamSupport.stream(event.getEntity().getArmorSlots().spliterator(), false).anyMatch(Helper::isSilverItem)) {
                event.getEntity().addEffect(SilverEffect.createSilverEffect(event.getEntity(), 20, 1, true));
            }
        }
    }
}
