package de.teamlapen.werewolves.common.world.entities.player.werewolf.actions;

import de.teamlapen.faction.api.factions.actions.IActionResult;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.core.ModEffects;
import de.teamlapen.werewolves.common.core.ModSounds;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public class FearAction extends DefaultWerewolfAction {
    @Override
    public boolean isEnabled() {
        return ModConfig.balance().SKILLS.fear_action_enabled.get();
    }

    @Override
    protected IActionResult activateServer(IWerewolfPlayer werewolf, ActivationContext context) {
        Player player = werewolf.asEntity();

        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.ENTITY_WEREWOLF_GROWL.get(), SoundSource.PLAYERS, 1, 1);

        int duration = ModConfig.balance().SKILLS.fear_action_duration.get() * 20;
        int radius = ModConfig.balance().SKILLS.fear_action_radius.get();
        player.level().getEntitiesOfClass(Mob.class, player.getBoundingBox().inflate(radius), mob -> mob.getTarget() == player || !Helper.isWerewolf(mob)).forEach(mob -> mob.addEffect(new MobEffectInstance(ModEffects.DISORIENTATION, duration), player));

        return IActionResult.SUCCESS;
    }

    @Override
    public int getCooldown(IWerewolfPlayer iWerewolfPlayer) {
        return ModConfig.balance().SKILLS.fear_action_cooldown.get() * 20;
    }
}
