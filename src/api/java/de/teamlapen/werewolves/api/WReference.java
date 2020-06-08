package de.teamlapen.werewolves.api;

import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.werewolves.api.entity.player.IWerewolfPlayer;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityClassification;

public class WReference {
    public static EntityClassification WEREWOLF_CREATUE_TYPE;
    public static CreatureAttribute WEREWOLF_CREATURE_ATTRIBUTES;
    public static IPlayableFaction<IWerewolfPlayer> WEREWOLF_FACTION;
}
