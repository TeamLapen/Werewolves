package de.teamlapen.werewolves.proxy;

import de.teamlapen.lib.lib.util.IInitListener;
import de.teamlapen.werewolves.blocks.entity.WolfsbaneDiffuserBlockEntity;
import de.teamlapen.werewolves.entities.minion.WerewolfMinionEntity;
import de.teamlapen.werewolves.network.ClientboundAttackTargetEventPacket;
import net.minecraft.network.chat.Component;

public interface Proxy extends IInitListener {

    default void displayWolfsbaneScreen(WolfsbaneDiffuserBlockEntity tile, Component title) {

    }

    default void displayWerewolfMinionAppearanceScreen(WerewolfMinionEntity entity) {

    }

    default void displayWerewolfStatsScreen(WerewolfMinionEntity entity) {

    }
}
