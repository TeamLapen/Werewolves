package de.teamlapen.werewolves.entities.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.player.action.IActionCooldownMenu;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import net.minecraft.world.entity.player.Player;

public class SenseWerewolfAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer>, IActionCooldownMenu {

    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.sense_enabled.get();
    }

    @Override
    protected boolean activate(IWerewolfPlayer player, ActivationContext context) {
        return true;
    }

    @Override
    public int getDuration(IWerewolfPlayer werewolf) {
        return WerewolvesConfig.BALANCE.SKILLS.sense_duration.get() * 20;
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer player) {
    }

    @Override
    public void onDeactivated(IWerewolfPlayer player) {
    }

    @Override
    public void onReActivated(IWerewolfPlayer player) {

    }

    @Override
    public boolean onUpdate(IWerewolfPlayer player) {
        return false;
    }

    @Override
    public int getCooldown(IWerewolfPlayer werewolf) {
        return WerewolvesConfig.BALANCE.SKILLS.sense_cooldown.get() + WerewolvesConfig.BALANCE.SKILLS.sense_duration.get() * 20;
    }

    @Override
    public boolean showHudCooldown(Player player) {
        return true;
    }

    @Override
    public boolean showHudDuration(Player player) {
        return true;
    }
}
