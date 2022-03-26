package de.teamlapen.werewolves.api.entities.werewolf;

import de.teamlapen.vampirism.api.difficulty.IAdjustableLevel;
import de.teamlapen.vampirism.api.entity.IEntityLeader;
import net.minecraft.world.entity.monster.Enemy;

public interface IWerewolfAlpha extends IWerewolfMob, IAdjustableLevel, Enemy, IEntityLeader {
}
