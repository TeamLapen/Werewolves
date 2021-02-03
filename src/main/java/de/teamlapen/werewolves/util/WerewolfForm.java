package de.teamlapen.werewolves.util;

import net.minecraft.entity.EntitySize;
import net.minecraft.entity.Pose;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum WerewolfForm implements IStringSerializable {
    NONE("none", null), HUMAN("human", null), BEAST("beast", WerewolfSize.BEAST), SURVIVALIST("survivalist", WerewolfSize.SURVIVAL);

    private final String name;
    private final Map<Pose, EntitySize> sizemap;

    WerewolfForm(String name, Map<Pose, EntitySize> sizemap) {
        if (sizemap == null){
            sizemap = new HashMap<>();
        }
        this.name = name;
        this.sizemap = sizemap;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    @Override
    public String getString() {
        return name;
    }

    public Optional<EntitySize> getSize(Pose pose) {
        return Optional.ofNullable(this.sizemap.getOrDefault(pose, this.sizemap.get(Pose.STANDING)));
    }
}
