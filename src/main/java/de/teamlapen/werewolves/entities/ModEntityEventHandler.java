package de.teamlapen.werewolves.entities;

import de.teamlapen.vampirism.tileentity.TotemHelper;
import de.teamlapen.vampirism.tileentity.TotemTileEntity;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.WerewolfSkills;
import de.teamlapen.werewolves.effects.SilverEffect;
import de.teamlapen.werewolves.entities.werewolf.IVillagerTransformable;
import de.teamlapen.werewolves.entities.werewolf.WerewolfTransformable;
import de.teamlapen.werewolves.items.ISilverItem;
import de.teamlapen.werewolves.network.AttackTargetEventPacket;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Optional;

public class ModEntityEventHandler {

    @SubscribeEvent
    public void onEntityAttacked(AttackEntityEvent event) {
        if (event.getTarget() instanceof LivingEntity && Helper.isWerewolf(event.getTarget())) {
            if (event.getPlayer().getHeldItemMainhand().getItem() instanceof ISilverItem) { //TODO maybe check for silver tag
                ((LivingEntity) event.getTarget()).addPotionEffect(SilverEffect.createEffect(((LivingEntity) event.getTarget()), WerewolvesConfig.BALANCE.UTIL.silverItemEffectDuration.get()));
            }
        }
        if (event.getTarget() instanceof WerewolfTransformable) {
            if (((WerewolfTransformable) event.getTarget()).canTransform()) {
                ((WerewolfTransformable) event.getTarget()).transformToWerewolf();
            }
        }
    }

    @SubscribeEvent
    public void onAttack(LivingSetAttackTargetEvent event) {
        if (event.getTarget() instanceof ServerPlayerEntity) {
            if (Helper.isWerewolf(((PlayerEntity) event.getTarget()))) {
                if (WerewolfPlayer.getOpt(((ServerPlayerEntity) event.getTarget())).map(werewolf -> werewolf.getSkillHandler().isSkillEnabled(WerewolfSkills.sixth_sense)).orElse(false)) {
                    WerewolvesMod.dispatcher.sendTo(new AttackTargetEventPacket(event.getEntity().getEntityId()), ((ServerPlayerEntity) event.getTarget()));
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity().world.isRemote()) return;
        if (event.getEntity() instanceof VillagerEntity) {
            Optional<TotemTileEntity> totemOpt = TotemHelper.getTotemNearPos(((ServerWorld) event.getWorld()), event.getEntity().getPosition(), false);
            totemOpt.ifPresent(totem -> {
                if (WReference.WEREWOLF_FACTION.equals(totem.getControllingFaction())) {
                    if (((VillagerEntity) event.getEntity()).getRNG().nextBoolean()) {
                        ((IVillagerTransformable) event.getEntity()).setWerewolfFaction(true);
                    }
                }
            });
        }
    }
}
