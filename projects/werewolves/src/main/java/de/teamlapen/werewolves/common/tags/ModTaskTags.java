package de.teamlapen.werewolves.common.tags;

import de.teamlapen.faction.api.FactionRegistries;
import de.teamlapen.faction.api.factions.tasks.Task;
import de.teamlapen.werewolves.api.WIdentifier;
import net.minecraft.tags.TagKey;

public class ModTaskTags {
    public static final TagKey<Task> IS_WEREWOLF = tag("is_werewolf");
    public static final TagKey<Task> AWARDS_LORD_LEVEL = vampirism("awards_lord_level");
    public static final TagKey<Task> HAS_FACTION = vampirism("has_faction");

    private static TagKey<Task> tag(String name) {
        return TagKey.create(FactionRegistries.Keys.TASK, WIdentifier.mod(name));
    }

    private static TagKey<Task> vampirism(String name) {
        return TagKey.create(FactionRegistries.Keys.TASK, WIdentifier.loc("vampirism", name));
    }
}
