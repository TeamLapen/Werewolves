package de.teamlapen.werewolves.entities;

import de.teamlapen.vampirism.api.entity.EntityClassType;
import de.teamlapen.vampirism.api.entity.actions.EntityActionTier;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public abstract class PermanentWerewolfEntity extends WerewolfEntity {

    private WerewolfFormUtil.Form werewolfForm;

    public PermanentWerewolfEntity(EntityType<? extends WerewolfEntity> type, World world, WerewolfFormUtil.Form form) {
        super(type, world, EntityClassType.getRandomClass(world.rand), EntityActionTier.Low);
        this.werewolfForm = form;
    }

    @Override
    public WerewolfFormUtil.Form getForm() {
        return werewolfForm;
    }

    public static class Beast extends PermanentWerewolfEntity {
        public Beast(EntityType<? extends WerewolfEntity> type, World world) {
            super(type, world, WerewolfFormUtil.Form.BEAST);
        }
    }

    public static class Survivalist extends PermanentWerewolfEntity {
        public Survivalist(EntityType<? extends WerewolfEntity> type, World world) {
            super(type, world, WerewolfFormUtil.Form.SURVIVALIST);
        }
    }
}
