package de.teamlapen.werewolves.entities.player.werewolf.actions;

import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.player.action.IActionCooldownMenu;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class FearAction extends DefaultWerewolfAction implements IActionCooldownMenu {
    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.fear_action_enabled.get();
    }

    @Override
    protected boolean activate(IWerewolfPlayer iWerewolfPlayer, ActivationContext context) {
        WerewolfPlayer player = ((WerewolfPlayer) iWerewolfPlayer);
        List<Mob> entities = player.getRepresentingPlayer().level.getEntitiesOfClass(Mob.class, new AABB(player.getRepresentingEntity().blockPosition()).inflate(10, 3, 10), (entity -> !(Helper.isWerewolf(entity))));
        for (Mob entity : entities) {
            entity.setTarget(null);
            entity.getNavigation().stop();
            Path path = entity.getNavigation().createPath(entity.blockPosition().offset(Helper.multiplyBlockPos(entity.blockPosition().subtract(player.getRepresentingEntity().blockPosition()), 3)), 0);
            entity.getNavigation().moveTo(path, 1.7);
        }
        return true;
    }

    @Override
    public int getCooldown(IWerewolfPlayer iWerewolfPlayer) {
        return WerewolvesConfig.BALANCE.SKILLS.fear_action_cooldown.get() * 20;
    }
}
