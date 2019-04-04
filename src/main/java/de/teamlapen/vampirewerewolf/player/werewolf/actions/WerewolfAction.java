package de.teamlapen.vampirewerewolf.player.werewolf.actions;

import de.teamlapen.vampirewerewolf.api.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.vampirewerewolf.api.entities.player.werewolf.actions.DefaultWerewolfAction;
import de.teamlapen.vampirewerewolf.config.Balance;

public class WerewolfAction extends DefaultWerewolfAction {

    public WerewolfAction() {
        super(null);
    }

    @Override
    public int getCooldown() {
        return Balance.wpa.WEREWOLF_COOLDOWN;
    }

    @Override
    public int getMinU() {
        return 0;
    }

    @Override
    public int getMinV() {
        return 0;
    }

    @Override
    public String getUnlocalizedName() {
        return "werewolve";
    }

    @Override
    public boolean isEnabled() {
        return Balance.wpa.WEREWOLF_ENABLED;
    }

    @Override
    protected boolean activate(IWerewolfPlayer player) {
        return false;
    }

}
