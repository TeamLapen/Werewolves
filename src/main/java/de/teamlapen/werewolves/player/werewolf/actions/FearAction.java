package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.player.IWerewolfPlayer;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class FearAction extends DefaultWerewolfAction {
    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.fear_action_enabled.get();
    }

    @Override
    protected boolean activate(IWerewolfPlayer iWerewolfPlayer) {
        WerewolfPlayer player = ((WerewolfPlayer) iWerewolfPlayer);
        List<MobEntity> entities = player.getRepresentingPlayer().world.getEntitiesWithinAABB(MobEntity.class, new AxisAlignedBB(player.getRepresentingEntity().getPosition()).grow(10, 3, 10), (entity -> !(Helper.isWerewolf(entity))));
        for (MobEntity entity : entities) {
            entity.setAttackTarget(null);
            entity.getNavigator().clearPath();
            Path path = entity.getNavigator().func_179680_a(entity.getPosition().add(Helper.multiplyBlockPos(entity.getPosition().subtract(player.getRepresentingEntity().getPosition()), 3)), 0);
            entity.getNavigator().setPath(path, 2);
        }
        return true;
    }

    @Override
    public int getCooldown() {
        return WerewolvesConfig.BALANCE.SKILLS.fear_action_cooldown.get();
    }
}
