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
        EntityPredicate predicate = (new EntityPredicate() {
            public boolean canTarget(@Nullable LivingEntity attackEntity, @Nonnull LivingEntity targetEntity) {
                if (creature.getCaptureInfo() != null && creature.getCaptureInfo().shouldForceTargets() && getTargetDistance() > 0.0D) {
                    this.setDistance(-1.0D);
                } else if (getTargetDistance() < 0.0D) {
                    this.setDistance(getTargetDistance() * 4);
                }

                return super.canTarget(attackEntity, targetEntity);
            }
        }).setCustomPredicate(VampirismAPI.factionRegistry().getPredicate(creature.getFaction(), true)).setLineOfSiteRequired();//TODO new vampirism version
        ObfuscationReflectionHelper.setPrivateValue(DefendVillageGoal.class,this, predicate, "entityPredicate");
    }
}
