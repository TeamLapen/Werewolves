package de.teamlapen.werewolves.common.tags;

import de.teamlapen.faction.api.FactionRegistries;
import de.teamlapen.faction.api.factions.skills.ISkillTree;
import de.teamlapen.werewolves.api.WIdentifier;
import net.minecraft.tags.TagKey;

public class ModSkillTreeTags {
    public static final TagKey<ISkillTree> WEREWOLF = tag("faction/werewolf");

    private static TagKey<ISkillTree> tag(String name) {
        return TagKey.create(FactionRegistries.Keys.SKILL_TREE, WIdentifier.mod(name));
    }

}
