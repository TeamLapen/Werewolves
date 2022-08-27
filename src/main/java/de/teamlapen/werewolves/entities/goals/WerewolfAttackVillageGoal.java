package de.teamlapen.werewolves.entities.goals;

import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.entity.IVillageCaptureEntity;
import de.teamlapen.vampirism.entity.VampirismEntity;
import de.teamlapen.vampirism.entity.ai.goals.AttackVillageGoal;
import org.jetbrains.annotations.NotNull;

/**
 * same as {@link AttackVillageGoal} but with ignore disguise
 */
public class WerewolfAttackVillageGoal<T extends VampirismEntity & IVillageCaptureEntity> extends AttackVillageGoal<T> {

    public WerewolfAttackVillageGoal(@NotNull T creature) {
        super(creature);
        this.entityPredicate.selector(VampirismAPI.factionRegistry().getPredicate(creature.getFaction(), true));
    }
}
