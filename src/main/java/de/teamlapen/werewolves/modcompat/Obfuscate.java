package de.teamlapen.werewolves.modcompat;

import net.minecraftforge.fml.ModList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

public class Obfuscate {

    public static final Logger LOGGER = LogManager.getLogger();


    public static void check() {
        ModList.get().getModContainerById("obfuscate").ifPresent(modContainer -> {
            if (modContainer.getModInfo().getVersion().compareTo(new DefaultArtifactVersion("0.6.3")) < 0) {
                LOGGER.error(modContainer.getModInfo().getVersion());
                LOGGER.error(new DefaultArtifactVersion("0.6.3"));
                LOGGER.error(modContainer.getModInfo().getVersion().compareTo(new DefaultArtifactVersion("0.6.3")) < 0);
                LOGGER.error("------------------------------------------------");
                LOGGER.error("");
                LOGGER.error("Obfuscate is not compatible with Werewolves because Obfuscate behaves wrong");
                LOGGER.error("Take a look https://github.com/MrCrayfish/Obfuscate/issues/23 if you want to complain");
                LOGGER.error("Either remove one of the two mods or wait for Obfuscate version 0.6.3");
                LOGGER.error("");
                LOGGER.error("------------------------------------------------");
                throw new IllegalStateException("The Obfuscate version is not compatible with Werewolves (details in the log files)");
            }
        });
    }
}