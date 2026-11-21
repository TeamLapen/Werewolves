package de.teamlapen.werewolves.common.tags;

import de.teamlapen.faction.api.FactionRegistries;
import de.teamlapen.faction.api.factions.tasks.Task;
import de.teamlapen.faction.common.tags.FactionTaskTags;
import de.teamlapen.werewolves.api.WIdentifier;
import net.minecraft.tags.TagKey;

public class ModTaskTags {
    public static final TagKey<Task> IS_WEREWOLF = tag("has_faction/werewolf");
    public static final TagKey<Task> AWARDS_LORD_LEVEL = FactionTaskTags.AWARDS_LORD_LEVEL;
    public static final TagKey<Task> HAS_FACTION = FactionTaskTags.HAS_FACTION;

    private static TagKey<Task> tag(String name) {
        return TagKey.create(FactionRegistries.Keys.TASK, WIdentifier.mod(name));
    }

}
