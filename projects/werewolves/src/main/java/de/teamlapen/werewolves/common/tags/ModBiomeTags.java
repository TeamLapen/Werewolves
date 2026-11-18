package de.teamlapen.werewolves.common.tags;

import de.teamlapen.faction.api.util.FIdentifier;
import de.teamlapen.werewolves.api.WIdentifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class ModBiomeTags {
    public static final TagKey<Biome> IS_WEREWOLF_BIOME = tag("has_faction/werewolf");
    public static final TagKey<Biome> IS_FACTION_BIOME = faction("has_faction");

    public static class HasGen {
        public static final TagKey<Biome> SILVER_ORE = tag("has_gen/silver_ore");
        public static final TagKey<Biome> WOLFSBANE = tag("has_gen/wolfsbane");

    }

    public static class HasSpawn {
        public static final TagKey<Biome> WEREWOLF = tag("has_spawn/werewolf");
        public static final TagKey<Biome> HUMAN_WEREWOLF = tag("has_spawn/human_werewolf");
    }

    public static class NoSpawn {
        public static final TagKey<Biome> WEREWOLF = tag("no_spawn/werewolf");
        public static final TagKey<Biome> HUMAN_WEREWOLF = tag("no_spawn/human_werewolf");
    }

    private static TagKey<Biome> tag(String name) {
        return TagKey.create(Registries.BIOME, WIdentifier.mod(name));
    }

    private static TagKey<Biome> faction(String name) {
        return TagKey.create(Registries.BIOME, FIdentifier.mod(name));
    }
}
