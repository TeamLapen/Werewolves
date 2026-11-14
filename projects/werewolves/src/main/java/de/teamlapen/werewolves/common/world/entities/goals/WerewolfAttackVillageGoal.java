package de.teamlapen.werewolves.common.world.entities.goals;


import de.teamlapen.faction.api.world.entities.IVillageCaptureEntity;

/**
 * same as {@link AttackVillageGoal} but with ignore disguise
 */
public class WerewolfAttackVillageGoal<T extends VampirismEntity & IVillageCaptureEntity> extends AttackVillageGoal<T> {

    public WerewolfAttackVillageGoal(T creature) {
        super(creature);
        this.entityPredicate.selector(VampirismAPI.factionRegistry().getPredicate(creature.getFaction(), true));
    }
}
