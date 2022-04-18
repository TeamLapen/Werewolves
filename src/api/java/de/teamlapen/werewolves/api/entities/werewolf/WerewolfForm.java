package de.teamlapen.werewolves.api.entities.werewolf;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class WerewolfForm {
    private static final Map<String, WerewolfForm> REGISTRY = new HashMap<>();
    public static final WerewolfForm NONE = new WerewolfForm("none", null, true, false, 0, 0F);
    public static final WerewolfForm HUMAN = new WerewolfForm("human", null, true, true, 3, 0.3F);
    public static final WerewolfForm BEAST = new WerewolfForm("beast", WerewolfSize.BEAST, false, true, 8, 0.3F);
    public static final WerewolfForm BEAST4L = new WerewolfForm("beast4l", WerewolfSize.BEAST, false, true, 8, 0.3F);
    public static final WerewolfForm SURVIVALIST = new WerewolfForm("survivalist", WerewolfSize.SURVIVAL, false, true, 8, 0.3F);

    @Nonnull
    private final String name;
    @Nullable
    private final Map<Pose, EntityDimensions> sizeMap;
    private final boolean humanLike;
    private final boolean transformed;
    private final int skinTypes;
    private final Component textComponent;
    private final float damageReduction;

    WerewolfForm(@Nonnull String name, @Nullable Map<Pose, EntityDimensions> sizeMap, boolean humanLike, boolean transformed, int skinTypes, float damageReduction) {
        if (REGISTRY.containsKey(name)) throw new IllegalStateException("this name already exists");
        REGISTRY.put(name, this);
        if (sizeMap == null) {
            sizeMap = new HashMap<>();
        }
        this.name = name;
        this.sizeMap = sizeMap;
        this.humanLike = humanLike;
        this.transformed = transformed;
        this.skinTypes = skinTypes;
        this.textComponent = new TranslatableComponent("form.werewolves." + name);
        this.damageReduction = damageReduction;
    }

    public boolean isHumanLike() {
        return humanLike;
    }

    public boolean isTransformed() {
        return transformed;
    }

    /**
     * first value is the percentage reduction and second is max damage reduction
     */
    public float getDamageReduction() {
        return damageReduction;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public Optional<EntityDimensions> getSize(Pose pose) {
        if (sizeMap != null) {
            return Optional.ofNullable(this.sizeMap.getOrDefault(pose, this.sizeMap.get(Pose.STANDING)));
        } else {
            return Optional.empty();
        }
    }

    public Component getTextComponent() {
        return textComponent.plainCopy();
    }

    public int getSkinTypes() {
        return skinTypes;
    }

    public static WerewolfForm getForm(String name) {
        return REGISTRY.get(name);
    }

    public static Collection<WerewolfForm> getAllForms() {
        return new ArrayList<>(REGISTRY.values());
    }
}
