package de.teamlapen.werewolves.player;

import com.google.common.base.Throwables;

import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModPlayerEventHandler {

    @SubscribeEvent
    public void onAttachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            try {
                event.addCapability(REFERENCE.WEREWOLF_PLAYER_KEY, WerewolfPlayer.createNewCapability((EntityPlayer) event.getObject()));
            } catch (Exception e) {
                WerewolvesMod.log.e("ModPlayerEventHandler", "Failed to attach capabilities to player. Player: %s", event.getObject());
                Throwables.propagate(e);
            }
        }
    }

    @SubscribeEvent
    public void onDamageEvent(LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            if (Helper.isWerewolf(event.getEntity())) {
                WerewolfPlayer werewolf = WerewolfPlayer.get((EntityPlayer) event.getEntityLiving());
                if (werewolf.getSpecialAttributes().healing) {
                    if (event.getSource() instanceof EntityDamageSource) {
                        WerewolfPlayer.get((EntityPlayer) event.getEntityLiving()).getSpecialAttributes().addHeal(event.getAmount());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onAttackEvent(LivingAttackEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            // TODO werewolf level
            if (WerewolfPlayer.get((EntityPlayer) event.getEntityLiving()).getSpecialAttributes().werewolf > 2) {
            }
        }
    }

    @SubscribeEvent
    public void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        if (Helper.isWerewolf(event.getEntityPlayer())) {
            WerewolfPlayer werewolf = WerewolfPlayer.get(event.getEntityPlayer());
            // TODO werewolf level
            if (werewolf.getSpecialAttributes().werewolf > 1 && !(event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemTool)) {
                if (event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemTool) {
                    if (event.getState().isFullBlock() || event.getState().getBlock().equals(Blocks.FARMLAND)) {
                        event.setCanceled(true);
                    }
                } else {
                    event.setNewSpeed((float) event.getEntityPlayer().getAttributeMap().getAttributeInstance(WReference.harvestSpeed).getAttributeValue());
                }
            }
        }
    }

    @SubscribeEvent
    public void onHarvestCheck(PlayerEvent.HarvestCheck event) {
        if (Helper.isWerewolf(event.getEntity())) {
            WerewolfPlayer werewolf = WerewolfPlayer.get(event.getEntityPlayer());
            // TODO werewolf level
            if (werewolf.getSpecialAttributes().werewolf > 1) {
                if (!(event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemTool) && event.getTargetBlock().getBlock().getHarvestLevel(event.getTargetBlock()) <= event.getEntityPlayer().getAttributeMap().getAttributeInstance(WReference.harvestLevel).getAttributeValue()) {
                    event.setCanHarvest(true);
                }
            }
        }
    }
}
