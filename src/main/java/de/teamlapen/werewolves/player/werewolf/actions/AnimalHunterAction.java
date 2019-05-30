package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.werewolves.api.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.player.werewolf.actions.DefaultWerewolfAction;
import de.teamlapen.werewolves.config.Balance;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;

public class AnimalHunterAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer> {

    public AnimalHunterAction() {
        super(null);
    }

    @Override
    public int getCooldown() {
        return Balance.wpa.WEREWOLF_ANIMALHUNTER_COOLDOWN;
    }

    @Override
    public int getMinU() {
        // TODO
        return 0;
    }

    @Override
    public int getMinV() {
        // TODO
        return 0;
    }

    @Override
    public String getUnlocalizedName() {
        // TODO
        return "animal_hunter";
    }

    @Override
    public boolean isEnabled() {
        return Balance.wpa.WEREWOLF_ANIMALHUNTER;
    }

    @Override
    protected boolean activate(IWerewolfPlayer player) {
        ((WerewolfPlayer) player).getSpecialAttributes().animalHunter = true;
        return true;
    }

    @Override
    public int getDuration(int level) {
        // TODO
        return Balance.wpa.WEREWOLF_ANIMALHUNTER_DURATION * 100;
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer player) {
        ((WerewolfPlayer) player).getSpecialAttributes().animalHunter = true;
    }

    @Override
    public void onDeactivated(IWerewolfPlayer player) {
        ((WerewolfPlayer) player).getSpecialAttributes().animalHunter = false;
    }

    @Override
    public void onReActivated(IWerewolfPlayer player) {
        ((WerewolfPlayer) player).getSpecialAttributes().animalHunter = true;
    }

    @Override
    public boolean onUpdate(IWerewolfPlayer player) {
        return false;
    }

}
