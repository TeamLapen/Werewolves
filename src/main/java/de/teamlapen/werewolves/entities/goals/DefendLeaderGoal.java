package de.teamlapen.werewolves.entities.goals;

import de.teamlapen.vampirism.api.entity.IEntityLeader;
import de.teamlapen.werewolves.api.entities.IEntityFollower;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.EnumSet;
import java.util.Optional;

public class DefendLeaderGoal<T extends Mob & IEntityFollower> extends TargetGoal {

    private final T entity;
    private LivingEntity attacker;
    private int timestamp;

    public DefendLeaderGoal(T entity) {
        super(entity, false);
        this.entity = entity;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        Optional<IEntityLeader> leader = this.entity.getLeader();
        if (leader.isEmpty()) {
            return false;
        } else {
            this.attacker = leader.get().getRepresentingEntity().getLastHurtByMob();
            int i = leader.get().getRepresentingEntity().getLastHurtByMobTimestamp();
            return i != this.timestamp && this.canAttack(this.attacker, TargetingConditions.DEFAULT);
        }
    }

    public void start() {
        this.mob.setTarget(this.attacker);
        this.entity.getLeader().ifPresent(leader -> {
            this.timestamp = leader.getRepresentingEntity().getLastHurtByMobTimestamp();
        });
        super.start();
    }
}
