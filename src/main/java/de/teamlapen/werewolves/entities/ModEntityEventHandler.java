package de.teamlapen.werewolves.entities;

import de.teamlapen.vampirism.tileentity.TotemHelper;
import de.teamlapen.vampirism.tileentity.TotemTileEntity;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModEffects;
import de.teamlapen.werewolves.core.WerewolfActions;
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
import net.minecraft.potion.EffectInstance;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.village.PointOfInterest;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Collection;
import java.util.stream.Collectors;

public class ModEntityEventHandler {

    @SubscribeEvent
    public void onEntityAttacked(AttackEntityEvent event) {
        if (event.getTarget() instanceof LivingEntity && Helper.isWerewolf(event.getTarget())) {
            if (event.getPlayer().getHeldItemMainhand().getItem() instanceof ISilverItem) { //TODO maybe check for silver tag
                ((LivingEntity) event.getTarget()).addPotionEffect(new EffectInstance(ModEffects.silver, WerewolvesConfig.BALANCE.UTIL.silverItemEffectDuration.get()));
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
                if (WerewolfPlayer.getOpt(((ServerPlayerEntity) event.getTarget())).map(werewolf -> werewolf.getActionHandler().isActionActive(WerewolfActions.sixth_sense)).orElse(false)) {
                    WerewolvesMod.dispatcher.sendTo(new AttackTargetEventPacket(event.getEntity().getEntityId()), ((ServerPlayerEntity) event.getTarget()));
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity().world.isRemote()) return;
        if (event.getEntity() instanceof VillagerEntity) {
            Collection<PointOfInterest> points = ((ServerWorld) event.getWorld()).getPointOfInterestManager().func_219146_b(p -> true, event.getEntity().getPosition(), 25, PointOfInterestManager.Status.ANY).collect(Collectors.toList());
            if (points.size() > 0) {
                BlockPos pos = TotemHelper.getTotemPosition(points);
                if (pos != null && event.getWorld().getChunkProvider().isChunkLoaded(new ChunkPos(pos))) {
                    TileEntity tileEntity = event.getWorld().getTileEntity(pos);
                    if (tileEntity instanceof TotemTileEntity) {
                        if (WReference.WEREWOLF_FACTION.equals(((TotemTileEntity) tileEntity).getControllingFaction())) {
                            if (((VillagerEntity) event.getEntity()).getRNG().nextBoolean()) {
                                ((IVillagerTransformable) event.getEntity()).setWerewolfFaction(true);
                            }
                        }
                    }
                }
            }
        }
    }
}
