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
        if (!t.getCommandSenderWorld().isClientSide()) {
            if (t.getTarget() != null && t.closerThan(t.getTarget(), t.getBbWidth() * 2.0F * t.getBbWidth() * 2.0F + t.getBbWidth())) {
                t.bite(t.getTarget());
            }
        }
        return true;
    }

    @Override
    public int getCooldown(int i) {
        return 5;
    }
}
