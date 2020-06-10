package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.werewolves.api.entity.player.IWerewolfPlayer;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import net.minecraft.util.math.MathHelper;

public class WerewolfAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer> {
    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.werewolf_form_enabled.get();
    }

    @Override
    protected boolean activate(IWerewolfPlayer player) {
        ((WerewolfPlayer)player).getSpecialAttributes().trueForm = true;
        return true;
    }

    @Override
    public int getDuration(int level) {
        return MathHelper.clamp(WerewolvesConfig.BALANCE.werewolf_form_duration.get(), 10, Integer.MAX_VALUE / 20 - 1) * 20;
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer player) {
        ((WerewolfPlayer)player).getSpecialAttributes().trueForm = true;
    }

    @Override
    public void onDeactivated(IWerewolfPlayer player) {
        ((WerewolfPlayer)player).getSpecialAttributes().trueForm = false;
    }

    @Override
    public void onReActivated(IWerewolfPlayer player) {
        ((WerewolfPlayer)player).getSpecialAttributes().trueForm = true;
    }

    @Override
    public boolean onUpdate(IWerewolfPlayer player) {
        return false;
    }

    @Override
    public int getCooldown() {
        return WerewolvesConfig.BALANCE.werewolf_form_cooldown.get() * 20 + 1;
    }
}
