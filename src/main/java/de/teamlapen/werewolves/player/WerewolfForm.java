package de.teamlapen.werewolves.player;

import de.teamlapen.werewolves.util.WerewolfSize;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.Pose;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WerewolfForm {
    private static final Map<String, WerewolfForm> REGISTRY = new HashMap<>();
    public static final WerewolfForm NONE = new WerewolfForm("none", null, true, false);
    public static final WerewolfForm HUMAN = new WerewolfForm("human", null, true, true);
    public static final WerewolfForm BEAST = new WerewolfForm("beast", WerewolfSize.BEAST, false, true);
    public static final WerewolfForm SURVIVALIST = new WerewolfForm("survivalist", WerewolfSize.SURVIVAL, false, true);

    @Nonnull
    private final String name;
    @Nullable
    private final Map<Pose, EntitySize> sizeMap;
    private final boolean humanLike;
    private final boolean transformed;

    WerewolfForm(@Nonnull String name, @Nullable Map<Pose, EntitySize> sizeMap, boolean humanLike, boolean transformed) {
        if (REGISTRY.containsKey(name)) throw new IllegalStateException("this name already exists");
        REGISTRY.put(name, this);
        if (sizeMap == null) {
            sizeMap = new HashMap<>();
        }
        this.name = name;
        this.sizeMap = sizeMap;
        this.humanLike = humanLike;
        this.transformed = transformed;
    }

    public boolean isHumanLike() {
        return humanLike;
    }

    public boolean isTransformed() {
        return transformed;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public Optional<EntitySize> getSize(Pose pose) {
        if (sizeMap != null) {
            return Optional.ofNullable(this.sizeMap.getOrDefault(pose, this.sizeMap.get(Pose.STANDING)));
        } else {
            return Optional.empty();
        }
    }

    public static WerewolfForm getForm(String name){
        return REGISTRY.get(name);
    }
}
