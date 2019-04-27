package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.werewolves.api.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.player.werewolf.actions.DefaultWerewolfAction;
import de.teamlapen.werewolves.config.Balance;
import de.teamlapen.werewolves.core.ModPotions;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;

import java.util.UUID;

public class WerewolfAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer> {
    private static final UUID SPEED = UUID.fromString("57ac98ff-35a1-4115-96ee-2479dc7e1460");
    private static final UUID ARMOR = UUID.fromString("a16dfca7-98b1-44a1-8057-a9cb38fbfb19");
    private static final UUID ARMOR_TOUGHNESS = UUID.fromString("c70fbf55-9f19-4679-8daa-919b29ed7104");

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
        if (((WerewolfPlayer) werewolf).getSpecialAttributes().werewolf) {
            onDeactivated(werewolf);
            return false;
        }
        werewolf.transformWerewolf();
        return true;
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
