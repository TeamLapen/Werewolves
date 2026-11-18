package de.teamlapen.werewolves.common.world.entities.player.werewolf.actions;

import de.teamlapen.faction.api.factions.actions.IActionResult;

import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class FearAction extends DefaultWerewolfAction {
    @Override
    public boolean isEnabled() {
        return ModConfig.balance().SKILLS.fear_action_enabled.get();
    }

    @Override
    protected IActionResult activateServer(IWerewolfPlayer iWerewolfPlayer, ActivationContext context) {
        WerewolfPlayer player = ((WerewolfPlayer) iWerewolfPlayer);
        List<Mob> entities = player.asEntity().level().getEntitiesOfClass(Mob.class, new AABB(player.asEntity().blockPosition()).inflate(10, 3, 10), (entity -> !(Helper.isWerewolf(entity))));
        for (Mob entity : entities) {
            entity.setTarget(null);
            entity.getNavigation().stop();
            Path path = entity.getNavigation().createPath(entity.blockPosition().offset(Helper.multiplyBlockPos(entity.blockPosition().subtract(player.asEntity().blockPosition()), 3)), 0);
            entity.getNavigation().moveTo(path, 1.7);
        }
        return IActionResult.SUCCESS;
    }

    @Override
    public int getCooldown(IWerewolfPlayer iWerewolfPlayer) {
        return ModConfig.balance().SKILLS.fear_action_cooldown.get() * 20;
    }


}
