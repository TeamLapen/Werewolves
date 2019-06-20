package de.teamlapen.werewolves.config;

import de.teamlapen.lib.lib.config.BalanceValues;
import de.teamlapen.lib.lib.config.DefaultInt;
import de.teamlapen.vampirism.VampirismMod;

import java.io.File;

public class BalanceGeneral extends BalanceValues {

    @DefaultInt(value = 10, comment = "in minutes")
    public int DROWSYTIME;

    public BalanceGeneral(File directory) {
        super("general", directory);
    }

    @Override
    protected boolean shouldUseAlternate() {
        return VampirismMod.isRealism();
    }

}
