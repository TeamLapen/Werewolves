package de.teamlapen.vampirewerewolf.config;

import de.teamlapen.lib.lib.config.BalanceValues;
import de.teamlapen.lib.lib.config.DefaultInt;
import de.teamlapen.vampirism.VampirismMod;
import java.io.File;

public class BalanceWerewolfPlayer extends BalanceValues {

    @DefaultInt(value = 2)
    public int WEREWOLF_SPEED_ADDITION;
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
