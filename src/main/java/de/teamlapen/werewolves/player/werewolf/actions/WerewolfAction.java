package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.api.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.player.werewolf.actions.DefaultWerewolfAction;
import de.teamlapen.werewolves.config.Balance;
import de.teamlapen.werewolves.core.ModPotions;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;

public class WerewolfAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer> {

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
        return "werewolf";
    }

    @Override
    public boolean isEnabled() {
        return Balance.wpa.WEREWOLF_ENABLED;
    }

    @Override
    protected boolean activate(IWerewolfPlayer werewolf) {
        if (((WerewolfPlayer) werewolf).getSpecialAttributes().werewolf > 0) {
            onDeactivated(werewolf);
            return false;
        }
        WerewolvesMod.log.t("num: %s", ((WerewolfPlayer) werewolf).getSpecialAttributes().werewolfLevel);
        return werewolf.transformWerewolf();
    }

    @Override
    public boolean canBeUsedBy(IWerewolfPlayer werewolf) {
        if (werewolf.getRepresentingPlayer().isPotionActive(ModPotions.true_form)) {
            return false;
        }
        return true;
    }

    @Override
    public int getDuration(int level) {
        return Balance.wpa.WEREWOLF_DURATION;
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer werewolf) {
        werewolf.transformWerewolf();
    }

    @Override
    public void onDeactivated(IWerewolfPlayer werewolf) {
        werewolf.transformHuman();

    }

    @Override
    public void onReActivated(IWerewolfPlayer werewolf) {
        werewolf.transformWerewolf();
    }

    @Override
    public boolean onUpdate(IWerewolfPlayer werewolf) {
        return false;
    }
}
