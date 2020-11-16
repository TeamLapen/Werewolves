package de.teamlapen.werewolves.entities;

import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModEffects;
import de.teamlapen.werewolves.core.WerewolfActions;
import de.teamlapen.werewolves.items.ISilverItem;
import de.teamlapen.werewolves.network.AttackTargetEventPacket;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModEntityEventHandler {

    @SubscribeEvent
    public void onEntityAttacked(AttackEntityEvent event) {
        if (event.getTarget() instanceof LivingEntity && Helper.isWerewolf(event.getTarget())) {
            if (event.getPlayer().getHeldItemMainhand().getItem() instanceof ISilverItem) { //TODO maybe check for silver tag
                ((LivingEntity) event.getTarget()).addPotionEffect(new EffectInstance(ModEffects.silver, WerewolvesConfig.BALANCE.UTIL.silverItemEffectDuration.get()));
            }
        }
        if (event.getTarget() instanceof IExtendedWerewolf) {
            ((IExtendedWerewolf) event.getTarget()).makeWerewolf();
        }
    }

    @SubscribeEvent
    public void onAttack(LivingSetAttackTargetEvent event) {
        if (event.getTarget() instanceof ServerPlayerEntity) {
            if (Helper.isWerewolf(((PlayerEntity) event.getTarget()))) {
                if (WerewolfPlayer.getOpt(((ServerPlayerEntity) event.getTarget())).map(werewolf -> werewolf.getActionHandler().isActionActive(WerewolfActions.sixth_sense)).orElse(false)) {
                    WerewolvesMod.dispatcher.sendTo(new AttackTargetEventPacket(event.getEntity().getEntityId()), ((ServerPlayerEntity) event.getTarget()));
                }
            }
        }
    }
}
