package de.teamlapen.werewolves.api;

import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.werewolves.api.entity.player.IWerewolfPlayer;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

public class WReference {
    public static EntityClassification WEREWOLF_CREATUE_TYPE;
    public static CreatureAttribute WEREWOLF_CREATURE_ATTRIBUTES;
    public static IPlayableFaction<IWerewolfPlayer> WEREWOLF_FACTION;

    public final static IAttribute biteDamage = new RangedAttribute(null, "werewolves.bite_damage", 2D, 0D, 100D);

}
