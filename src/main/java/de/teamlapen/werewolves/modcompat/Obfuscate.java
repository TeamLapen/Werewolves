package de.teamlapen.werewolves.modcompat;

import net.minecraftforge.fml.ModList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Obfuscate {

    public static final Logger LOGGER = LogManager.getLogger();


    public static void check() {
        if (ModList.get().isLoaded("obfuscate")) {
            LOGGER.error("------------------------------------------------");
            LOGGER.error("");
            LOGGER.error("Obfuscate is not compatible with Werewolves because Obfuscate behaves wrong");
            LOGGER.error("Take a look https://github.com/MrCrayfish/Obfuscate/issues/23 if you want to complain");
            LOGGER.error("");
            LOGGER.error("------------------------------------------------");
            throw new IllegalStateException("Obfuscate is not compatible with Werewolves (see log files)");
        }
    }
}