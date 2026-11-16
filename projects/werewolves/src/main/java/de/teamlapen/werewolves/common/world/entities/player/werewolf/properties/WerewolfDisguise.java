package de.teamlapen.werewolves.common.world.entities.player.werewolf.properties;

import de.teamlapen.faction.api.Factions;
import de.teamlapen.faction.api.factions.IDisguise;
import de.teamlapen.faction.api.factions.IFaction;
import de.teamlapen.faction.api.factions.IPlayableFaction;
import de.teamlapen.sync.PropertyParentSync;
import de.teamlapen.sync.PropertySync;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.Nullable;

public class WerewolfDisguise extends PropertyParentSync implements IDisguise {

    private final WerewolfPlayer werewolf;

    public WerewolfDisguise(WerewolfPlayer parent) {
        super(parent);
        this.werewolf = parent;
    }

    @Override
    public Holder<? extends IPlayableFaction<?>> actualFaction() {
        return this.werewolf.getFaction();
    }

    @Override
    public Holder<? extends IFaction<?>> getViewedFaction(@Nullable Holder<? extends IFaction<?>> viewerFaction) {
        return this.werewolf.getForm().isTransformed() ? actualFaction() : Factions.NEUTRAL;
    }

    @Override
    public void disguiseAs(@Nullable Holder<? extends IFaction<?>> faction) {

    }

    @Override
    public void unDisguise() {

    }

    @Override
    public boolean isDisguised() {
        return !this.werewolf.getForm().isTransformed();
    }
}
