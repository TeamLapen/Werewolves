package de.teamlapen.werewolves.common.tags;

import de.teamlapen.werewolves.api.WIdentifier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;

public class ModDataComponentTags {
    public static final TagKey<DataComponentType<?>> FACTION_FOOD = tagV("food/faction");
    public static final TagKey<DataComponentType<?>> WEREWOLF_FOOD = tag("food/faction/werewolf");

    private static TagKey<DataComponentType<?>> tag(String name) {
        return TagKey.create(Registries.DATA_COMPONENT_TYPE, WIdentifier.mod(name));
    }

    private static TagKey<DataComponentType<?>> tagV(String name) {
        return TagKey.create(Registries.DATA_COMPONENT_TYPE, WIdentifier.vampirism(name));
    }

}
