package de.teamlapen.werewolves.entities.goals;

import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.entity.IVillageCaptureEntity;
import de.teamlapen.vampirism.entity.ai.goals.DefendVillageGoal;
import net.minecraft.world.entity.PathfinderMob;

/**
 * same as {@link DefendVillageGoal} but with ignore disguise
 */
public class WerewolfDefendVillageGoal<T extends PathfinderMob & IVillageCaptureEntity> extends DefendVillageGoal<T> {

    public WerewolfDefendVillageGoal(T creature) {
        super(creature);
        this.entityPredicate.selector(VampirismAPI.factionRegistry().getPredicate(creature.getFaction(), true));
    }
}
