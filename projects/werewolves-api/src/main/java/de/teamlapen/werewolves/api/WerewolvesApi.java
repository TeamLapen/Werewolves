package de.teamlapen.werewolves.api;

import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.world.IWolfsbaneChunkHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.ServiceLoader;

public class WerewolvesApi {

    public static IWerewolvesServices services() {
        return WerewolvesApi.Holder.SERVICES;
    }

    public static IWerewolfPlayer werwolfPlayer(Player player) {
        return player.getData(WerewolvesAttachments.WEREWOLF_PLAYER);
    }

    public static IWolfsbaneChunkHandler wolfsbaneHandler(Level w) {
        return w.getData(WerewolvesAttachments.WOLFSBANE_HANDLER);
    }

    private static final class Holder {
        private static final IWerewolvesServices SERVICES = load();

        private static IWerewolvesServices load() {
            return ServiceLoader.load(IWerewolvesServices.class, WerewolvesApi.class.getClassLoader())
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No implementation of " + IWerewolvesServices.class.getName() + " found. Is Werewolves installed correctly?"));
        }
    }
}
