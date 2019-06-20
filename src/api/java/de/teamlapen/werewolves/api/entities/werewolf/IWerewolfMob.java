package de.teamlapen.werewolves.api.entities.werewolf;

import de.teamlapen.vampirism.api.entity.IVampirismEntity;

public interface IWerewolfMob extends IWerewolf, IVampirismEntity {

    boolean transform(Form form);

    Form getForm();

    enum Form {
        DEFAULT,
        WEREWOLF;
    }
}
