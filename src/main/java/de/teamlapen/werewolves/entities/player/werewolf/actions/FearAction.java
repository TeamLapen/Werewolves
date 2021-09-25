package de.teamlapen.werewolves.entities.player.werewolf.actions;

import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class FearAction extends DefaultWerewolfAction implements IActionCooldownMenu {
    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.fear_action_enabled.get();
    }

    @Override
    protected boolean activate(IWerewolfPlayer iWerewolfPlayer) {
        WerewolfPlayer player = ((WerewolfPlayer) iWerewolfPlayer);
        List<MobEntity> entities = player.getRepresentingPlayer().level.getEntitiesOfClass(MobEntity.class, new AxisAlignedBB(player.getRepresentingEntity().blockPosition()).inflate(10, 3, 10), (entity -> !(Helper.isWerewolf(entity))));
        for (MobEntity entity : entities) {
            entity.setTarget(null);
            entity.getNavigation().stop();
            Path path = entity.getNavigation().createPath(entity.blockPosition().offset(Helper.multiplyBlockPos(entity.blockPosition().subtract(player.getRepresentingEntity().blockPosition()), 3)), 0);
            entity.getNavigation().moveTo(path, 1.7);
        }
        return true;
    }

    @Override
    public int getCooldown() {
        return WerewolvesConfig.BALANCE.SKILLS.fear_action_cooldown.get() * 20;
    }

}
