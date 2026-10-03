package de.teamlapen.werewolves.api.entities.werewolf;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;

import java.util.Map;

public class WerewolfSize {
    public static final Map<Pose, EntityDimensions> BEAST = ImmutableMap.<Pose, EntityDimensions>builder()
            .put(Pose.STANDING, EntityDimensions.scalable(0.7F, 2F))
            .put(Pose.SLEEPING, EntityDimensions.fixed(0.2F, 0.3F))
            .put(Pose.FALL_FLYING, EntityDimensions.scalable(0.7F, 0.7F))
            .put(Pose.SWIMMING, EntityDimensions.scalable(0.7F, 0.7F))
            .put(Pose.SPIN_ATTACK, EntityDimensions.scalable(0.7F, 0.7F))
            .put(Pose.CROUCHING, EntityDimensions.scalable(0.7F, 1.7F))
            .put(Pose.DYING, EntityDimensions.fixed(0.2F, 0.2F)).build();

    public static final Map<Pose, EntityDimensions> SURVIVAL = ImmutableMap.<Pose, EntityDimensions>builder()
            .put(Pose.STANDING, EntityDimensions.scalable(0.7F, 1.1F))
            .put(Pose.SLEEPING, EntityDimensions.fixed(0.2F, 0.3F))
            .put(Pose.FALL_FLYING, EntityDimensions.scalable(0.7F, 0.7F))
            .put(Pose.SWIMMING, EntityDimensions.scalable(0.7F, 0.8F))
            .put(Pose.SPIN_ATTACK, EntityDimensions.scalable(0.7F, 0.7F))
            .put(Pose.CROUCHING, EntityDimensions.scalable(0.7F, 0.9F))
            .put(Pose.DYING, EntityDimensions.fixed(0.2F, 0.2F)).build();
}
