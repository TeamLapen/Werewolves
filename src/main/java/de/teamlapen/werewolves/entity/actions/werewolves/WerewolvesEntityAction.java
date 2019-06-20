package de.teamlapen.werewolves.entity.actions.werewolves;

import de.teamlapen.vampirism.api.entity.EntityClassType;
import de.teamlapen.vampirism.api.entity.actions.DefaultEntityAction;
import de.teamlapen.vampirism.api.entity.actions.EntityActionTier;
import de.teamlapen.vampirism.api.entity.actions.IEntityAction;
import de.teamlapen.vampirism.api.entity.actions.IEntityActionUser;
import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.werewolves.api.WReference;
import net.minecraft.entity.EntityCreature;

public abstract class WerewolvesEntityAction<T extends EntityCreature & IEntityActionUser> extends DefaultEntityAction<T> implements IEntityAction {
    public WerewolvesEntityAction(EntityActionTier tier, EntityClassType... param) {
        super(tier, param);
    }

    @Override
    public IPlayableFaction getFaction() {
        return WReference.WEREWOLF_FACTION;
    }
}
