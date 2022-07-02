package de.teamlapen.werewolves.entities.player.skill;

import de.teamlapen.vampirism.api.entity.player.IFactionPlayer;
import de.teamlapen.vampirism.api.entity.player.actions.IAction;

public class ActionSkill<T extends IFactionPlayer<?>> extends de.teamlapen.vampirism.player.skills.ActionSkill<T> {

    public ActionSkill(IAction action) {
        super(action.getRegistryName(), action);
    }

    public ActionSkill(IAction action, boolean customDescription) {
        super(action.getRegistryName(), action, customDescription);
    }

}
