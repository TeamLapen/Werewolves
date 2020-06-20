package de.teamlapen.werewolves.entities;

import de.teamlapen.werewolves.api.items.ISilverItem;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModEffects;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class ModEntityEventHandler {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Random RNG = new Random();

    @SubscribeEvent
    public void onAttachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof AbstractVillagerEntity) {
            try {
                event.addCapability(REFERENCE.EXTENDED_WEREWOLF_KEY, ExtendedWerewolf.createNewCapability((AbstractVillagerEntity) event.getObject()));
            } catch (Exception e) {
                LOGGER.error("Failed to attach capabilities to entity. Entity: {}", event.getObject());
                throw e;
            }
        }
    }

    @SubscribeEvent
    public void onEntitySpawn(LivingSpawnEvent event) {
        if (event.getEntity() instanceof AbstractVillagerEntity) {
            if (!(Helper.isHunter(event.getEntity()) || Helper.isVampire(event.getEntity()))) {
                if (RNG.nextInt(15) == 0) {
                    ExtendedWerewolf.getSafe((AbstractVillagerEntity) event.getEntityLiving()).ifPresent(villager -> villager.setWerewolfFaction(true));
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityAttacked(AttackEntityEvent event) {
        if(event.getTarget() instanceof LivingEntity && Helper.isWerewolf(event.getTarget())) {
            if(event.getPlayer().getHeldItemMainhand().getItem() instanceof ISilverItem) { //TODO maybe check for silver tag
                ((LivingEntity) event.getTarget()).addPotionEffect(new EffectInstance(ModEffects.silver, WerewolvesConfig.BALANCE.UTIL.silverItemEffectDuration.get()));
            }
        }
    }
}
