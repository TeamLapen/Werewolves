package de.teamlapen.werewolves.api.entities.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.DefaultAction;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entities.player.werewolf.IWerewolfPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * Basic implementation of IAction<IWerewolfPlayer>. It is recommend to extend this
 */
public abstract class DefaultWerewolfAction extends DefaultAction<IWerewolfPlayer> {

    /**
     * @param icons
     *            If null Vampirism's default one will be used
     */
    public DefaultWerewolfAction(ResourceLocation icons) {
        super(WReference.WEREWOLF_FACTION, icons);
    }
}
