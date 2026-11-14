package de.teamlapen.werewolves.common.world.entities.player.werewolf.skill;

import de.teamlapen.faction.api.factions.skills.SkillProperties;
import de.teamlapen.faction.api.tags.FactionSkillTreeTags;
import de.teamlapen.faction.common.factions.skills.Skill;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;

public class WerewolfLordSkill extends WerewolfSkill {
    public WerewolfLordSkill(SkillProperties<IWerewolfPlayer> properties) {
        super(properties.tree(FactionSkillTreeTags.LORD));
    }
}
