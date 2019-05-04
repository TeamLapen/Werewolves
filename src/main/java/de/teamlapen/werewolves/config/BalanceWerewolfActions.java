package de.teamlapen.werewolves.config;

import de.teamlapen.lib.lib.config.BalanceValues;
import de.teamlapen.lib.lib.config.DefaultBoolean;
import de.teamlapen.lib.lib.config.DefaultDouble;
import de.teamlapen.lib.lib.config.DefaultInt;
import de.teamlapen.vampirism.VampirismMod;

import java.io.File;

/**
 * Balance values for Hunter Player Actions
 */
public class BalanceWerewolfActions extends BalanceValues {

    @DefaultBoolean(value = true)
    public boolean WEREWOLF_ANIMALHUNTER = false;
    
    @DefaultInt(value = 100)
    public int WEREWOLF_ANIMALHUNTER_COOLDOWN;
    
    @DefaultInt(value = 100)
    public int WEREWOLF_ANIMALHUNTER_DURATION;

    @DefaultInt(value = 10)
    public int WEREWOLF_COOLDOWN;

    @DefaultBoolean(value = true)
    public boolean WEREWOLF_ENABLED;

    @DefaultInt(value = Integer.MAX_VALUE, minValue = 10, name = "werewolf_duration")
    public int WEREWOLF_DURATION;

    @DefaultDouble(value = 1.5D, minValue = 1, comment = "multiplier")
    public double WEREWOLF_SPEED_MAX;

    @DefaultInt(value = 10, minValue = 0, comment = "additive")
    public int WEREWOLF_ARMOR_MAX;

    @DefaultInt(value = 5, minValue = 0, comment = "additive")
    public int WEREWOLF_ARMOR_THOUGNESS_MAX;

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
