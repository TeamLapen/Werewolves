package de.teamlapen.werewolves.entities.goals;

import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.entity.IVillageCaptureEntity;
import de.teamlapen.vampirism.entity.VampirismEntity;
import de.teamlapen.vampirism.entity.goals.AttackVillageGoal;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * same as {@link AttackVillageGoal} but with ignore disguise
 */
public class WerewolfAttackVillageGoal<T extends VampirismEntity & IVillageCaptureEntity> extends AttackVillageGoal<T> {

    public WerewolfAttackVillageGoal(T creature) {
        super(creature);
        this.entityPredicate.setCustomPredicate(VampirismAPI.factionRegistry().getPredicate(creature.getFaction(), true));
    }
}
