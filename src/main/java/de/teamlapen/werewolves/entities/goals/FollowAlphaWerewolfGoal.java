package de.teamlapen.werewolves.entities.goals;

import de.teamlapen.vampirism.api.entity.IEntityLeader;
import de.teamlapen.werewolves.api.entities.IEntityFollower;
import de.teamlapen.werewolves.entities.werewolf.WerewolfBaseEntity;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class FollowAlphaWerewolfGoal<T extends WerewolfBaseEntity & IEntityFollower> extends Goal {

    protected final T entity;
    protected final double speed;
    private final int DIST = 122;
    private int delayCounter;

    public FollowAlphaWerewolfGoal(T entity, double speed) {
        this.entity = entity;
        this.speed = speed;
    }

    @Override
    public boolean canContinueToUse() {
        return this.entity.getLeader().map(leader -> {
            double d0 = this.entity.distanceToSqr(leader.getRepresentingEntity());
            return d0 >= DIST && d0 <= 256.0D;
        }).orElse(false);
    }

    @Override
    public boolean canUse() {
        if (this.entity.getLeader().isEmpty()) {
            List<WerewolfBaseEntity> list = this.entity.getCommandSenderWorld().getEntitiesOfClass(WerewolfBaseEntity.class, this.entity.getBoundingBox().inflate(16, 8, 16), entity -> entity instanceof IEntityLeader);
            double d0 = Double.MAX_VALUE;
            IEntityLeader newLeader = null;
            for (WerewolfBaseEntity entity : list) {
                IEntityLeader leader = (IEntityLeader) entity;
                if (entity.isAlive() && leader.getFollowingCount() < leader.getMaxFollowerCount()){
                    double d1 = this.entity.distanceToSqr(entity);

                    if (d1 <= d0) {
                        d0 = d1;
                        newLeader = leader;
                    }
                }
            }

            if(newLeader != null) {
                this.entity.setLeader(newLeader);
                newLeader.increaseFollowerCount();
            }
        }
        return this.entity.getLeader().map(leader -> leader.getRepresentingEntity().isAlive() && this.entity.distanceToSqr(leader.getRepresentingEntity()) > DIST).orElse(false);
    }

    @Override
    public void start() {
        this.delayCounter = 0;
    }

    @Override
    public void tick() {
        if (--this.delayCounter <= 0 && entity.getLeader().isPresent()) {
            this.delayCounter = 10;
            this.entity.getLeader().ifPresent(leader -> {
                this.entity.getNavigation().moveTo(leader.getRepresentingEntity(), this.speed);
                this.entity.lookAt(EntityAnchorArgument.Anchor.EYES, leader.getRepresentingEntity().position().add(0, leader.getRepresentingEntity().getEyeHeight(), 0));
            });
        }
    }
}
