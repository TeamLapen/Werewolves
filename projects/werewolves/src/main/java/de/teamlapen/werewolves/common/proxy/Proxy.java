package de.teamlapen.werewolves.common.proxy;

import de.teamlapen.lib.lib.util.IInitListener;
import de.teamlapen.werewolves.common.world.blocks.entity.WolfsbaneDiffuserBlockEntity;
import de.teamlapen.werewolves.common.world.entities.minion.WerewolfMinionEntity;
import net.minecraft.network.chat.Component;

public interface Proxy extends IInitListener {

    default void displayWolfsbaneScreen(WolfsbaneDiffuserBlockEntity tile, Component title) {

    }

    default void displayWerewolfMinionAppearanceScreen(WerewolfMinionEntity entity) {

    }

    default void displayWerewolfStatsScreen(WerewolfMinionEntity entity) {

    }
}
