package de.teamlapen.werewolves.client;

import de.teamlapen.werewolves.mixin.client.ClientModLoaderAccessor;
import net.minecraftforge.fml.LoadingFailedException;

public class ClientUtils {

    public static void checkForModLoadingErrorsAndThrow() {
        LoadingFailedException error = ClientModLoaderAccessor.getError();
        if (error != null) {
            throw new RuntimeException("""
                    Failed to load mods. Check the log file for a 'ModLoadingException' entry to fix this.
                    
                    This exception is thrown by Werewolves to indicate that another mod had issues initializing. This does not mean that Werewolves itself had issues, but Werewolves shows this error rather than a misleading one which blames Werewolves.
                    See details here: https://github.com/MinecraftForge/MinecraftForge/issues/8342
                    
                    """);
        }
    }
}
