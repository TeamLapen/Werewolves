package de.teamlapen.werewolves.client;

import de.teamlapen.werewolves.mixin.client.ClientModLoaderAccessor;
import net.neoforged.fml.LoadingFailedException;

public class ClientUtils {

    @SuppressWarnings("UnreachableCode")
    public static boolean noLoadingExceptions() {
        LoadingFailedException error = ClientModLoaderAccessor.getError();
        return error == null || error.getErrors().isEmpty();
    }
}
