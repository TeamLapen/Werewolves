package de.teamlapen.vampirewerewolf.player;

import com.google.common.base.Throwables;
import de.teamlapen.vampirewerewolf.VampireWerewolfMod;
import de.teamlapen.vampirewerewolf.player.werewolf.WerewolfPlayer;
import de.teamlapen.vampirewerewolf.util.Helper;
import de.teamlapen.vampirewerewolf.util.REFERENCE;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModPlayerEventHandler {

    @SubscribeEvent
    public void onAttachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            try {
                event.addCapability(REFERENCE.WEREWOLF_PLAYER_KEY, WerewolfPlayer.createNewCapability((EntityPlayer) event.getObject()));
            } catch (Exception e) {
                VampireWerewolfMod.log.e("ModPlayerEventHandler", "Failed to attach capabilities to player. Player: %s", event.getObject());
                Throwables.propagate(e);
            }
        }
    }

    @SubscribeEvent
    public void onDamageEvent(LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            if (Helper.isWerewolf(event.getEntity()) && WerewolfPlayer.get((EntityPlayer) event.getEntity()).getSpecialAttributes().healing) {
                WerewolfPlayer.get((EntityPlayer) event.getEntityLiving()).getSpecialAttributes().addHeal(event.getAmount() / 2);
                VampireWerewolfMod.log.t("Health: %s, Damage: %s", WerewolfPlayer.get((EntityPlayer) event.getEntityLiving()).getSpecialAttributes().heals, event.getAmount());
            }
        }
    }

    @SubscribeEvent
    public void onItemUsed(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            if (Helper.isWerewolf(event.getEntity()) && WerewolfPlayer.get((EntityPlayer) event.getEntity()).getSpecialAttributes().moreFoodFromRawMeat) {
                if(event.getItem().getItem() instanceof ItemFood) {
                    if(Helper.RAWMEAT.contains(event.getItem().getItem())) {
                        ItemFood food = (ItemFood) event.getItem().getItem();
                        ((EntityPlayer) event.getEntity()).getFoodStats().addStats(food.getHealAmount(event.getItem()) / 2, food.getSaturationModifier((event.getItem())) / 2);
                    }
                }
            }
        }
    }
}
