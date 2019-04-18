package de.teamlapen.werewolves.config;

import de.teamlapen.lib.lib.config.BalanceValues;
import de.teamlapen.vampirism.config.Configs;
import de.teamlapen.werewolves.WerewolvesMod;

import javax.annotation.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Main balance configuration handler
 */
public class Balance {
    private final static String TAG = "Balance";
    private final static Map<String, BalanceValues> categories = new HashMap<>();
    public static BalanceWerewolfPlayer wp;
    public static BalanceWerewolfSkills wps;
    public static BalanceWerewolfActions wpa;

    public static void init(File configDir, boolean inDev) {
        File balanceDir = new File(configDir, "balance");
        wp = addBalance(new BalanceWerewolfPlayer(balanceDir));
        wps = addBalance(new BalanceWerewolfSkills(balanceDir));
        wpa = addBalance(new BalanceWerewolfActions(balanceDir));
        if (inDev && Configs.resetConfigurationInDev) {
            resetAndReload(null);
        } else {
            loadConfiguration();
        }

        WerewolvesMod.log.i(TAG, "Loaded balance configuration");
    }

    private static <T extends BalanceValues> T addBalance(T cat) {
        categories.put(cat.getName(), cat);
        return cat;
    }

    private static void loadConfiguration() {
        for (BalanceValues values : categories.values()) {
            values.loadBalance();
        }
    }

    public static void onConfigurationChanged() {
        WerewolvesMod.log.i(TAG, "Reloading changed balance configuration");
        loadConfiguration();
    }


    /**
     * Resets the matching balance category and reloads it
     *
     * @param category False if category is not found
     * @return
     */
    public static boolean resetAndReload(@Nullable String category) {
        if (category == null || category.equals("all")) {
            for (BalanceValues values : categories.values()) {
                values.resetAndReload();
            }
            return true;
        }
        BalanceValues values = categories.get(category);
        if (values != null) {
            values.resetAndReload();
            return true;
        }
        return false;
    }

    public static Map<String, BalanceValues> getCategories() {
        return categories;
    }

}
