package de.teamlapen.werewolves.entities.werewolf;

import de.teamlapen.werewolves.util.WerewolfForm;

import javax.annotation.Nonnull;

public interface IWerewolfDataholder {

    @Nonnull
    WerewolfForm getForm();

    int getSkinType();

    int getEyeType();

    boolean hasGlowingEyes();
}
