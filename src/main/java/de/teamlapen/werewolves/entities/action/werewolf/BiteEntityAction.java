package de.teamlapen.werewolves.entities.action.werewolf;

import de.teamlapen.vampirism.api.entity.EntityClassType;
import de.teamlapen.vampirism.api.entity.actions.EntityActionTier;
import de.teamlapen.vampirism.api.entity.actions.IEntityActionUser;
import de.teamlapen.vampirism.api.entity.actions.IInstantAction;
import de.teamlapen.werewolves.entities.werewolf.WerewolfBaseEntity;

import javax.annotation.Nonnull;

public class BiteEntityAction<T extends WerewolfBaseEntity & IEntityActionUser> extends WerewolfEntityAction implements IInstantAction<T> {

    public BiteEntityAction(@Nonnull EntityActionTier tier, EntityClassType... param) {
        super(tier, param);
    }

    @Override
    public boolean activate(T t) {
        if (!t.getEntityWorld().isRemote()) {
            if (t.getAttackTarget() != null && t.isEntityInRange(t.getAttackTarget(), t.getWidth() * 2.0F * t.getWidth() * 2.0F + t.getWidth())) {
                t.bite(t.getAttackTarget());
            }
        }
        return true;
    }

    @Override
    public int getCooldown(int i) {
        return 5;
    }
}
