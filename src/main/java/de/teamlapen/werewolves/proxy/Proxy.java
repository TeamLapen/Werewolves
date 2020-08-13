package de.teamlapen.werewolves.proxy;

import de.teamlapen.lib.lib.util.IInitListener;
import net.minecraft.entity.LivingEntity;

import java.util.List;

public interface Proxy extends IInitListener {

    default void setTrackedEntities(List<LivingEntity> entities) {
    }

    default void clearTrackedEntities() {
    }
}
