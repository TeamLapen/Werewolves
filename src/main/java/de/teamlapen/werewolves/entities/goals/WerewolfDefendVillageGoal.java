package de.teamlapen.werewolves.entities.goals;

import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.entity.IVillageCaptureEntity;
import de.teamlapen.vampirism.entity.goals.DefendVillageGoal;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * same as {@link DefendVillageGoal} but with ignore disguise
 */
public class WerewolfDefendVillageGoal<T extends CreatureEntity & IVillageCaptureEntity> extends DefendVillageGoal<T> {

    public WerewolfDefendVillageGoal(T creature) {
        super(creature);
        this.entityPredicate.setCustomPredicate(VampirismAPI.factionRegistry().getPredicate(creature.getFaction(), true));
    }
}
