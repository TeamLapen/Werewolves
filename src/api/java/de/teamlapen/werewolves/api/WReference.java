package de.teamlapen.werewolves.api;

import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.werewolves.api.entities.player.werewolf.IWerewolfPlayer;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

public class WReference {
    /**
     * Werewolf Player Faction
     * Filled during pre-init.
     */
    public static IPlayableFaction<IWerewolfPlayer> WEREWOLF_FACTION;

    /**
     * Werewolf creatures are of this creature type. But when they are counted for
     * spawning they belong to {@link EnumCreatureType#MONSTER}
     */
    public static EnumCreatureType WEREWOLF_CREATURE_TYPE;

    /**
     * Vampire creatures have this creature attribute.
     * Don't know why this exists alongside EnumCreatureType, but this is used by enchanments
     */
    public static EnumCreatureAttribute WEREWOLF_CREATURE_ATTRIBUTE;

    public final static IAttribute biteDamage = new RangedAttribute((IAttribute) null, "werewolves.bite_damage", 2D, 0D, 100D);

    public final static IAttribute harvestSpeed = new RangedAttribute((IAttribute) null, "werewolves.harvest_speed", 1D, 0.0D, 100D);

    public final static IAttribute harvestLevel = new RangedAttribute((IAttribute) null, "werewolves.harvest_level", 0D, 0D, 3D);
}
