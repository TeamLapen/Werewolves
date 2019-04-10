package de.teamlapen.vampirewerewolf.config;

import de.teamlapen.lib.lib.config.BalanceValues;
import de.teamlapen.lib.lib.config.DefaultInt;
import de.teamlapen.vampirism.VampirismMod;

import java.io.File;

/**
 * Balance values for all hunter skills
 */
public class BalanceWerewolfSkills extends BalanceValues {

    @DefaultInt(value = 1, minValue = -1)
    public int WEREWOLF_JUMPBOOST;
    /**
     * Creates a configuration for balance values
     *
     * @param directory
     */
    public BalanceWerewolfSkills(File directory) {
        super("werewolf_skills", directory);
    }

    @Override
    protected boolean shouldUseAlternate() {
        return VampirismMod.isRealism();
    }
}
