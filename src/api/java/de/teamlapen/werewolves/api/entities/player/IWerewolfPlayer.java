package de.teamlapen.werewolves.api.entities.player;

import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.api.entity.player.IFactionPlayer;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entities.werewolf.IWerewolf;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface IWerewolfPlayer extends IWerewolf, IFactionPlayer<IWerewolfPlayer> {
    @Override
    default @NotNull IPlayableFaction<IWerewolfPlayer> getFaction() {
        return WReference.WEREWOLF_FACTION;
    }

    boolean canWearArmor(ItemStack stack);
}
