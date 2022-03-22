package de.teamlapen.werewolves.entities.werewolf;

import de.teamlapen.werewolves.util.WerewolfForm;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IWerewolfDataholder {

    @Nonnull
    WerewolfForm getForm();

    default int getSkinType() {
        return this.getSkinType(this.getForm());
    }

    default int getEyeType() {
        return this.getEyeType(this.getForm());
    }

    int getSkinType(@Nullable WerewolfForm form);

    int getEyeType(@Nullable WerewolfForm form);

    default boolean hasGlowingEyes() {
        return hasGlowingEyes(this.getForm());
    }

    boolean hasGlowingEyes(WerewolfForm form);
}
