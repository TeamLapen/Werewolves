package de.teamlapen.vampirewerewolf.config;

import de.teamlapen.lib.lib.config.BalanceValues;
import de.teamlapen.vampirism.VampirismMod;
import java.io.File;

public class BalanceWerewolfPlayer extends BalanceValues {

    /**
     * Creates a configuration for balance values
     *
     * @param directory
     */
    public BalanceWerewolfPlayer(File directory) {
        super("werewolf_player", directory);
    }

    @Override
    protected boolean shouldUseAlternate() {
        return VampirismMod.isRealism();
    }
}
