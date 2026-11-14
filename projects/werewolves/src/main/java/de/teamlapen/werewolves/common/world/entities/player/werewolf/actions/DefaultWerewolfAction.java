package de.teamlapen.werewolves.common.world.entities.player.werewolf.actions;

import de.teamlapen.faction.api.factions.IFaction;
import de.teamlapen.faction.common.factions.actions.DefaultAction;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.common.core.ModTags;
import net.minecraft.tags.TagKey;

import javax.annotation.Nonnull;
import java.util.Optional;

public abstract class DefaultWerewolfAction extends DefaultAction<IWerewolfPlayer> {

    @Override
    public TagKey<? extends IFaction<?>> factions() {
        return ModTags.Factions.IS_WEREWOLF;
    }
}
