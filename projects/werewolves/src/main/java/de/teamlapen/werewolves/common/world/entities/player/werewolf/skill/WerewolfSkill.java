package de.teamlapen.werewolves.common.world.entities.player.werewolf.skill;

import de.teamlapen.faction.api.factions.skills.SkillProperties;
import de.teamlapen.faction.common.factions.skills.Skill;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.common.tags.ModFactionsTags;
import de.teamlapen.werewolves.common.tags.ModSkillTreeTags;

public class WerewolfSkill extends Skill<IWerewolfPlayer> {

    public WerewolfSkill(SkillProperties<IWerewolfPlayer> properties) {
        super(properties.factions(ModFactionsTags.IS_WEREWOLF).tree(ModSkillTreeTags.WEREWOLF));
    }
}
