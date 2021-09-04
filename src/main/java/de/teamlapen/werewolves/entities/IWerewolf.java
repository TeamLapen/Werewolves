package de.teamlapen.werewolves.entities;

import de.teamlapen.vampirism.api.entity.factions.IFactionEntity;
import de.teamlapen.werewolves.player.WerewolfForm;

import javax.annotation.Nonnull;

public interface IWerewolf extends IFactionEntity {

    @Nonnull
    WerewolfForm getForm();

    int getSkinType();

    int getEyeType();

    boolean hasGlowingEyes();
}
