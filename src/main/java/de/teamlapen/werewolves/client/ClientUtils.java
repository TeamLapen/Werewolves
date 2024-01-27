package de.teamlapen.werewolves.client;

import de.teamlapen.werewolves.mixin.client.ClientModLoaderAccessor;
import net.minecraftforge.fml.LoadingFailedException;

public class ClientUtils {

    public static boolean noLoadingExceptions() {
        System.out.println("");
        LoadingFailedException error = ClientModLoaderAccessor.getError();
        return error == null || error.getErrors().isEmpty();
    }
}
