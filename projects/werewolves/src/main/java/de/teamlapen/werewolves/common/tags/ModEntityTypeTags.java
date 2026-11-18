package de.teamlapen.werewolves.common.tags;

import de.teamlapen.werewolves.api.WIdentifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class ModEntityTypeTags {
    public static final TagKey<EntityType<?>> WEREWOLF = tag("werewolf");
    public static final TagKey<EntityType<?>> VAMPIRE = vampirism("vampire");
    public static final TagKey<EntityType<?>> HUNTER = vampirism("hunter");
    public static final TagKey<EntityType<?>> ADVANCED_HUNTER = vampirism("advanced_hunter");
    public static final TagKey<EntityType<?>> ADVANCED_VAMPIRE = vampirism("advanced_vampire");

    private static TagKey<EntityType<?>> tag(Identifier resourceLocation) {
        return TagKey.create(Registries.ENTITY_TYPE, resourceLocation);
    }

    private static TagKey<EntityType<?>> tag(String name) {
        return tag(WIdentifier.mod(name));
    }

    private static TagKey<EntityType<?>> vampirism(String name) {
        return tag(WIdentifier.loc("vampirism", name));
    }
}
