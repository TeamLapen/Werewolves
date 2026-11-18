package de.teamlapen.werewolves.api.entities.player;

import de.teamlapen.faction.api.factions.IPlayableFaction;
import de.teamlapen.faction.api.factions.skills.ISkillPlayer;
import de.teamlapen.faction.api.world.entities.player.IFactionPlayer;
import de.teamlapen.werewolves.api.WerewolvesFactions;
import de.teamlapen.werewolves.api.entities.werewolf.IWerewolf;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;

public interface IWerewolfPlayer extends IWerewolf, IFactionPlayer<IWerewolfPlayer>, ISkillPlayer<IWerewolfPlayer> {
    @Override
    default Holder<? extends IPlayableFaction<IWerewolfPlayer>> getFaction() {
        return WerewolvesFactions.WEREWOLF;
    }

    boolean canWearArmor(ItemStack stack);
}
