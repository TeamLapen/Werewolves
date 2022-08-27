package de.teamlapen.werewolves.api.entities.werewolf;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IWerewolfDataholder {

    @NotNull
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
