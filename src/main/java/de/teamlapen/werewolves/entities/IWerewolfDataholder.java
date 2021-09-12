package de.teamlapen.werewolves.entities;

import de.teamlapen.werewolves.player.WerewolfForm;

import javax.annotation.Nonnull;

public interface IWerewolfDataholder {

    @Nonnull
    WerewolfForm getForm();

    int getSkinType();

    int getEyeType();

    boolean hasGlowingEyes();
}
