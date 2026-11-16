package de.teamlapen.werewolves.common.world.entities.player.werewolf.skill;

import de.teamlapen.faction.api.factions.actions.IAction;
import de.teamlapen.faction.api.factions.skills.SkillProperties;
import de.teamlapen.vampirism.api.entity.factions.ISkillTree;
import de.teamlapen.vampirism.api.entity.player.actions.IAction;
import de.teamlapen.vampirism.entity.player.skills.ActionSkill;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.werewolf.IWerewolf;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.actions.WerewolfFormAction;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public class FormActionSkill extends WerewolfSkill {
    private final Holder<? extends WerewolfFormAction> action;

    public FormActionSkill(Holder<? extends WerewolfFormAction> action, SkillProperties<IWerewolfPlayer> properties) {
        super(properties.actionSkill(action));
        this.action = action;
    }

    @Override
    protected void onDisabled(IWerewolfPlayer player) {
        super.onDisabled(player);
        if (!player.asEntity().level().isClientSide()) {
            ((WerewolfPlayer) player).getInventory().dropFormEquipment(this.action.value().getForm());
        }
    }
}
