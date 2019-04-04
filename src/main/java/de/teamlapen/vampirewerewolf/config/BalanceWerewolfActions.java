package de.teamlapen.vampirewerewolf.config;

import de.teamlapen.lib.lib.config.BalanceValues;
import de.teamlapen.lib.lib.config.DefaultBoolean;
import de.teamlapen.lib.lib.config.DefaultInt;
import de.teamlapen.vampirism.VampirismMod;
import java.io.File;

/**
 * Balance values for Hunter Player Actions
 */
public class BalanceWerewolfActions extends BalanceValues {

    @DefaultInt(value = 10)
    public int WEREWOLF_COOLDOWN;

    @DefaultBoolean(value = true)
    public boolean WEREWOLF_ENABLED;

    /**
     * Creates a configuration for balance values
     *
     * @param directory
     */
    public BalanceWerewolfActions(File directory) {
        super("werewolf_player_actions", directory);
    }

    @Override
    protected boolean shouldUseAlternate() {
        return VampirismMod.isRealism();
    }
}
