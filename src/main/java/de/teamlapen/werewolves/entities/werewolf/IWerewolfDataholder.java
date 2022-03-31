package de.teamlapen.werewolves.entities.werewolf;

import de.teamlapen.werewolves.util.WerewolfForm;
import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface IWerewolfDataholder {

    WerewolfForm getForm();

    default int getSkinType() {
        return this.getSkinType(this.getForm());
    }

    int getSkinType(WerewolfForm form);

    default int getEyeType() {
        return this.getSkinType(this.getForm());
    }

    int getEyeType(WerewolfForm form);

    default boolean hasGlowingEyes() {
        return this.hasGlowingEyes(this.getForm());
    }

    boolean hasGlowingEyes(WerewolfForm form);
}
