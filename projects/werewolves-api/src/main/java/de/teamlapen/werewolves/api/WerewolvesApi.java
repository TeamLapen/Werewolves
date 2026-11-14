package de.teamlapen.werewolves.api;

import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ServiceLoader;

public class WerewolvesApi {

    public static IWerewolvesServices services() {
        return WerewolvesApi.Holder.SERVICES;
    }

    public static IWerewolfPlayer werwolfPlayer(Player player) {
        return player.getData(WerewolvesAttachments.WEREWOLF_PLAYER);
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
