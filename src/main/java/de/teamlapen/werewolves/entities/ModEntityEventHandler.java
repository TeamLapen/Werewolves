package de.teamlapen.werewolves.entities;

import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModEffects;
import de.teamlapen.werewolves.items.ISilverItem;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class ModEntityEventHandler {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Random RNG = new Random();

    @SubscribeEvent
    public void onEntityAttacked(AttackEntityEvent event) {
        if(event.getTarget() instanceof LivingEntity && Helper.isWerewolf(event.getTarget())) {
            if(event.getPlayer().getHeldItemMainhand().getItem() instanceof ISilverItem) { //TODO maybe check for silver tag
                ((LivingEntity) event.getTarget()).addPotionEffect(new EffectInstance(ModEffects.silver, WerewolvesConfig.BALANCE.UTIL.silverItemEffectDuration.get()));
            }
        }
    }
}
