package de.teamlapen.werewolves.player;

import de.teamlapen.werewolves.util.WerewolfSize;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.Pose;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class WerewolfForm {
    private static final Map<String, WerewolfForm> REGISTRY = new HashMap<>();
    public static final WerewolfForm NONE = new WerewolfForm("none", null, true, false,0);
    public static final WerewolfForm HUMAN = new WerewolfForm("human", null, true, true,3);
    public static final WerewolfForm BEAST = new WerewolfForm("beast", WerewolfSize.BEAST, false, true,8);
    public static final WerewolfForm SURVIVALIST = new WerewolfForm("survivalist", WerewolfSize.SURVIVAL, false, true,8);

    @Nonnull
    private final String name;
    @Nullable
    private final Map<Pose, EntitySize> sizeMap;
    private final boolean humanLike;
    private final boolean transformed;
    private final int skinTypes;
    private final ITextComponent textComponent;

    WerewolfForm(@Nonnull String name, @Nullable Map<Pose, EntitySize> sizeMap, boolean humanLike, boolean transformed, int skinTypes) {
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
        this.textComponent = new TranslationTextComponent("form.werewolves." + name);
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

    public ITextComponent getTextComponent() {
        return textComponent.plainCopy();
    }

    public int getSkinTypes() {
        return skinTypes;
    }

    public static WerewolfForm getForm(String name){
        return REGISTRY.get(name);
    }

    public static Collection<WerewolfForm> getAllForms() {
        return new ArrayList<>(REGISTRY.values());
    }
}
