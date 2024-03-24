package de.teamlapen.werewolves.entities.player.werewolf.skill;

import de.teamlapen.vampirism.api.entity.factions.ISkillTree;
import de.teamlapen.vampirism.api.entity.player.actions.IAction;
import de.teamlapen.vampirism.entity.player.skills.ActionSkill;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.core.ModActions;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.entities.player.werewolf.actions.WerewolfFormAction;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public class FormActionSkill extends ActionSkill<IWerewolfPlayer> {
    private final Supplier<WerewolfFormAction> action;

    public FormActionSkill(Supplier<WerewolfFormAction> action, ResourceKey<ISkillTree> skillTree, int skillPointCost) {
        super(action, skillTree, skillPointCost, true);
        this.action = action;
    }

    @Override
    protected void onDisabled(IWerewolfPlayer player) {
        super.onDisabled(player);
        if (!player.asEntity().level().isClientSide) {
            ((WerewolfPlayer) player).getInventory().dropFormEquipment(this.action.get().getForm());
        }
    }
}
