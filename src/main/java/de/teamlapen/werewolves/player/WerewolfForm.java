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
    public static final WerewolfForm NONE = new WerewolfForm("none", null);
    public static final WerewolfForm HUMAN = new WerewolfForm("human", null);
    public static final WerewolfForm BEAST = new WerewolfForm("beast", WerewolfSize.BEAST);
    public static final WerewolfForm SURVIVALIST = new WerewolfForm("survivalist", WerewolfSize.SURVIVAL);

    @Nonnull
    private final String name;
    @Nullable
    private final Map<Pose, EntitySize> sizeMap;

    WerewolfForm(@Nonnull String name, @Nullable Map<Pose, EntitySize> sizeMap) {
        if (REGISTRY.containsKey(name)) throw new IllegalStateException("this name already exists");
        REGISTRY.put(name, this);
        if (sizeMap == null){
            sizeMap = new HashMap<>();
        }
        this.name = name;
        this.sizeMap = sizeMap;
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
