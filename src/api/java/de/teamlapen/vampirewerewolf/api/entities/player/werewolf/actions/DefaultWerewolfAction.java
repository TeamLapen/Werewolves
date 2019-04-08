package de.teamlapen.vampirewerewolf.api.entities.player.werewolf.actions;

import de.teamlapen.vampirewerewolf.api.VReference;
import de.teamlapen.vampirewerewolf.api.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.vampirism.api.entity.player.actions.DefaultAction;
import net.minecraft.util.ResourceLocation;

/**
 * Basic implementation of IAction<IWerewolvePlayer>. It is recommend to extend this
 */
public abstract class DefaultWerewolfAction extends DefaultAction<IWerewolfPlayer> {

    /**
     * @param icons
     *            If null Vampirism's default one will be used
     */
    public DefaultWerewolfAction(ResourceLocation icons) {
        super(VReference.WEREWOLF_FACTION, icons);
    }
}
