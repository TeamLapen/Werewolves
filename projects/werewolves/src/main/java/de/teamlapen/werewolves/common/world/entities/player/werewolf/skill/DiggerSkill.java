package de.teamlapen.werewolves.common.world.entities.player.werewolf.skill;

import de.teamlapen.faction.api.factions.skills.SkillProperties;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.common.core.ModSkills;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;

public class DiggerSkill extends WerewolfSkill {

    public DiggerSkill(SkillProperties<IWerewolfPlayer> properties) {
        super(properties.withDescription(ModSkills.appliesTo(ModSkills.BEAST_FORM, ModSkills.SURVIVAL_FORM, ModSkills.HUMAN_FORM)));
    }

    @Override
    protected void onEnabled(IWerewolfPlayer player) {
        super.onEnabled(player);
        ((WerewolfPlayer) player).getSpecialAttributes().increaseDiggerLevel();
        ((WerewolfPlayer) player).checkToolDamage(player.asEntity().getMainHandItem(), player.asEntity().getMainHandItem(), true);
    }

    @Override
    protected void onDisabled(IWerewolfPlayer player) {
        super.onDisabled(player);
        ((WerewolfPlayer) player).getSpecialAttributes().decreaseDiggerLevel();
        ((WerewolfPlayer) player).checkToolDamage(player.asEntity().getMainHandItem(), player.asEntity().getMainHandItem(), true);
    }
}
