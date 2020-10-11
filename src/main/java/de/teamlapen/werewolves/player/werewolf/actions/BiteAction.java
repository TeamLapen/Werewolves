package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.player.IWerewolfPlayer;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import net.minecraft.entity.player.PlayerEntity;

public class BiteAction extends DefaultWerewolfAction {

    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.bite_enabled.get();
    }

    @Override
    protected boolean activate(IWerewolfPlayer player) {
        ((WerewolfPlayer)player).biteEntity(((WerewolfPlayer)player).getSpecialAttributes().target);
        ((WerewolfPlayer)player).getSpecialAttributes().target = 0;
        return true;
    }

    @Override
    public int getCooldown() {
        return WerewolvesConfig.BALANCE.SKILLS.bite_cooldown.get() * 20;
    }

    @Override
    public boolean showInSelectAction(PlayerEntity player) {
        return false;
    }

    @Override
    public boolean showInCooldownMenu() {
        return true;
    }
}
