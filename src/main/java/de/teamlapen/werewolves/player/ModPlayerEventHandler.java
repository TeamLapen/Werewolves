package de.teamlapen.werewolves.player;

import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModTags;
import de.teamlapen.werewolves.core.WerewolfSkills;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModPlayerEventHandler {
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public void onAttachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            try {
                event.addCapability(REFERENCE.WEREWOLF_PLAYER_KEY, WerewolfPlayer.createNewCapability((PlayerEntity) event.getObject()));
            } catch (Exception e) {
                LOGGER.error("Failed to attach capabilities to player. Player: {}", event.getObject());
                throw e;
            }
        }
    }

    @SubscribeEvent
    public void onFootEaten(LivingEntityUseItemEvent.Start event) {
        if (event.getEntity() instanceof PlayerEntity && Helper.isWerewolf((PlayerEntity) event.getEntity())) {
            //noinspection ConstantConditions
            if (event.getItem().isFood() && !event.getItem().getItem().getFood().isMeat()) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onFootEaten(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof PlayerEntity && Helper.isWerewolf((PlayerEntity) event.getEntity())) {
            //noinspection ConstantConditions
            if (event.getItem().isFood() && event.getItem().getItem().getFood().isMeat() && ModTags.Items.RAWMEATS.contains(event.getItem().getItem())) {
                ((PlayerEntity) event.getEntityLiving()).getFoodStats().consume(event.getItem().getItem(), event.getItem());
            }
        }
    }

    @SubscribeEvent
    public void onHealing(LivingHealEvent event) {
        if (event.getEntity() instanceof PlayerEntity && Helper.isWerewolf(((PlayerEntity) event.getEntity()))) {
            if (WerewolfPlayer.getOpt(((PlayerEntity) event.getEntity())).map(player -> player.getSkillHandler().isSkillEnabled(WerewolfSkills.health_reg)).orElse(false)) {
                event.setAmount(event.getAmount() * (1 + WerewolvesConfig.BALANCE.SKILLS.health_reg_modifier.get().floatValue()));
            }
        }
    }

    @SubscribeEvent
    public void onKilled(LivingDeathEvent event) {
        if (event.getSource() instanceof EntityDamageSource && event.getSource().getTrueSource() instanceof PlayerEntity && Helper.isWerewolf(((PlayerEntity) event.getSource().getTrueSource()))) {
            WerewolfPlayer player = WerewolfPlayer.get(((PlayerEntity) event.getSource().getTrueSource()));
            if (player.getSkillHandler().isSkillEnabled(WerewolfSkills.health_after_kill)) {
                ((PlayerEntity) event.getSource().getTrueSource()).addPotionEffect(new EffectInstance(Effects.REGENERATION, 4, 10));
            } else if (player.getSkillHandler().isSkillEnabled(WerewolfSkills.speed_after_kill)) {
                player.getRepresentingPlayer().addPotionEffect(new EffectInstance(Effects.SPEED, 40));
            }
        }
    }
}
