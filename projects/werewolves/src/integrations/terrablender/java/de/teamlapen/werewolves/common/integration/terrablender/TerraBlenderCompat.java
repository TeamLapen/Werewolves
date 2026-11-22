package de.teamlapen.werewolves.common.integration.terrablender;

import de.teamlapen.integration.Integration;
import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.world.features.OverworldModifications;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * When TerraBlender is installed we use it to add our biomes to the overworld, instead of hacking it into the overworld preset. This is more compatible with other mods.
 * <br>
 * #registerBiomeProviderIfPresentUnsafe() is called during common setup.
 * The hack code in {@link OverworldModifications} is called during load complete and is skipped if the biome was added via TerraBlender.
 */
@Integration(modIds = "terrablender")
public class TerraBlenderCompat {

    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void registerBiomeProviderIfPresentUnsafe(FMLCommonSetupEvent event) {
        if (ModConfig.common().disableWerewolfForest.get()) {
            return;
        }
        TerraBlenderRegistration.registerRegions();
        LOGGER.info("TerraBlender is installed. Using it to add werewolf forest to overworld.");
        OverworldModifications.useTerraBlender();
    }
}
