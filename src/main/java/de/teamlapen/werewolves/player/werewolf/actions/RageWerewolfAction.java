package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.player.IWerewolfPlayer;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class RageWerewolfAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer> {
    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.rage_enabled.get();
    }

    @Override
    protected boolean activate(IWerewolfPlayer iWerewolfPlayer) {
        iWerewolfPlayer.getRepresentingPlayer().addPotionEffect(new EffectInstance(Effects.STRENGTH, this.getDuration(iWerewolfPlayer.getLevel()), 0, false, false));
        return true;
    }

    @Override
    public int getDuration(int i) {
        return WerewolvesConfig.BALANCE.SKILLS.rage_duration.get() * 20;
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer iWerewolfPlayer) {
    }

    @Override
    public void onDeactivated(IWerewolfPlayer iWerewolfPlayer) {
        iWerewolfPlayer.getRepresentingPlayer().removePotionEffect(Effects.STRENGTH);
    }

    @Override
    public void onReActivated(IWerewolfPlayer iWerewolfPlayer) {

    }

    @Override
    public boolean onUpdate(IWerewolfPlayer iWerewolfPlayer) {
        return false;
    }

    @Override
    public int getCooldown() {
        return WerewolvesConfig.BALANCE.SKILLS.rage_cooldown.get() * 20;
    }
}
