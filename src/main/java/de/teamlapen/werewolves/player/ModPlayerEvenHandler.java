package de.teamlapen.werewolves.player;

import de.teamlapen.werewolves.core.WTags;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModPlayerEvenHandler {
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public void onAttachCapability(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof PlayerEntity) {
            try {
                event.addCapability(REFERENCE.WEREWOLF_PLAYER_KEY, WerewolfPlayer.createNewCapability((PlayerEntity)event.getObject()));
            } catch (Exception e) {
                LOGGER.error("Failed to attach capabilities to player. Player: {}", event.getObject());
                throw e;
            }
        }
    }

    @SubscribeEvent
    public void onFootEaten(LivingEntityUseItemEvent.Start event) {
        if(event.getEntity() instanceof PlayerEntity && Helper.isWerewolf((PlayerEntity)event.getEntity())) {
            if(event.getItem().isFood() && !WTags.Items.RAWMEATS.contains(event.getItem().getItem())) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onFootEaten(LivingEntityUseItemEvent.Finish event) {
        if(event.getEntity() instanceof PlayerEntity && Helper.isWerewolf((PlayerEntity)event.getEntity())) {
            if(event.getItem().isFood() && WTags.Items.RAWMEATS.contains(event.getItem().getItem())) {
                ((PlayerEntity)event.getEntityLiving()).getFoodStats().consume(event.getItem().getItem(),event.getItem());
            }
        }
    }
}
