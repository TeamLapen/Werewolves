package de.teamlapen.werewolves.api;

import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.CreativeModeTab;

public class WReference {
    public static final String MODID = "werewolves";
    public static MobType WEREWOLF_CREATURE_ATTRIBUTES;
    public static IPlayableFaction<IWerewolfPlayer> WEREWOLF_FACTION;
}
