package de.teamlapen.werewolves.util;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.Pose;

import java.util.Map;

public class WerewolfSize {
    public static final Map<Pose, EntitySize> BEAST = ImmutableMap.<Pose, EntitySize>builder()
            .put(Pose.STANDING, EntitySize.flexible(0.7F, 2F))
            .put(Pose.SLEEPING, EntitySize.fixed(0.2F, 0.3F))
            .put(Pose.FALL_FLYING, EntitySize.flexible(0.7F, 0.7F))
            .put(Pose.SWIMMING, EntitySize.flexible(0.7F, 0.7F))
            .put(Pose.SPIN_ATTACK, EntitySize.flexible(0.7F, 0.7F))
            .put(Pose.CROUCHING, EntitySize.flexible(0.7F, 1.7F))
            .put(Pose.DYING, EntitySize.fixed(0.2F, 0.2F)).build();

    public static final Map<Pose, EntitySize> SURVIVAL = ImmutableMap.<Pose, EntitySize>builder()
            .put(Pose.STANDING, EntitySize.flexible(0.7F, 1.1F))
            .put(Pose.SLEEPING, EntitySize.fixed(0.2F, 0.3F))
            .put(Pose.FALL_FLYING, EntitySize.flexible(0.7F, 0.7F))
            .put(Pose.SWIMMING, EntitySize.flexible(0.7F, 0.8F))
            .put(Pose.SPIN_ATTACK, EntitySize.flexible(0.7F, 0.7F))
            .put(Pose.CROUCHING, EntitySize.flexible(0.7F, 0.9F))
            .put(Pose.DYING, EntitySize.fixed(0.2F, 0.2F)).build();
}
