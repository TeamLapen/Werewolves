package de.teamlapen.werewolves.client;

import de.teamlapen.werewolves.mixin.client.ClientModLoaderAccessor;
import net.neoforged.fml.ModLoadingException;

public class ClientUtils {

    @SuppressWarnings("UnreachableCode")
    public static boolean noLoadingExceptions() {
        ModLoadingException error = ClientModLoaderAccessor.getError();
        return error == null || error.getIssues().isEmpty();
    }
}
