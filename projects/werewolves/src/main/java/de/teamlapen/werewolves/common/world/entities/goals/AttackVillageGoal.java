package de.teamlapen.werewolves.common.world.entities.goals;


import de.teamlapen.faction.api.factions.IFactionPredicate;
import de.teamlapen.faction.api.world.entities.IVillageCaptureEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import org.jetbrains.annotations.Nullable;

/**
 * same as {@link AttackVillageGoal} but with ignore disguise
 */
public class AttackVillageGoal<T extends Mob & IVillageCaptureEntity> extends TargetGoal {

    private final T attacker;
    protected final TargetingConditions entityPredicate;
    private final double distance = getFollowDistance() * 4;

    public AttackVillageGoal(T creature) {
        super(creature, false, false);
        this.attacker = creature;
        this.entityPredicate = new TargetingConditions(true) {

            @Override
            public boolean test(ServerLevel level, @Nullable LivingEntity attackEntity, LivingEntity targetEntity) {
                if (attacker.getCaptureInfo() != null && attacker.getCaptureInfo().shouldForceTargets() && getFollowDistance() > 0) {
                    range(-1.0D);
                } else if (getFollowDistance() < 0) {
                    range(distance);
                }
                return super.test(level, attackEntity, targetEntity);
            }
        }.selector(IFactionPredicate.builder(this.attacker.getFaction()).notNeutral().build()).ignoreInvisibilityTesting();
    }

    @Override
    public boolean canUse() {
        if (!attacker.isAttackingVillage()) return false;
        if (attacker.getTargetVillageArea() == null) return false;
        this.targetMob = this.attacker.level() instanceof ServerLevel serverLevel? serverLevel.getNearestEntity(LivingEntity.class, entityPredicate, this.mob, this.mob.getX(), this.mob.getY() + (double) this.mob.getEyeHeight(), this.mob.getZ(), attacker.getTargetVillageArea()) : null;
        return targetMob != null;
    }

    @Override
    public void start() {
        this.attacker.setTarget(this.targetMob);
        super.start();
    }
}
