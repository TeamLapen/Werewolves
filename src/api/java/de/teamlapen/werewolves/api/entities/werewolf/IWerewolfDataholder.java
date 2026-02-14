package de.teamlapen.werewolves.api.entities.werewolf;

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

    boolean setSkinType(@Nullable WerewolfForm form, int skinType);

    boolean setEyeType(@Nullable WerewolfForm form, int eyeType);

    default boolean hasGlowingEyes() {
        return hasGlowingEyes(this.getForm());
    }

    boolean hasGlowingEyes(WerewolfForm form);
}
