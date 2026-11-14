package de.teamlapen.werewolves.util;

import de.teamlapen.faction.api.factions.IPlayableFaction;
import de.teamlapen.faction.api.factions.lord.ILordTitleProvider;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class WerewolfLordTitles implements ILordTitleProvider {

    private static Component title(String faction, int level, String suffix) {
        return Component.translatable("lord_title.werewolves." + faction + "." + level + suffix);
    }

    @Override
    public @Nullable Component getLordTitle(int level, IPlayableFaction.TitleGender titleGender) {
        return title("werewolf", level, "");
    }

    @Override
    public @Nullable Component getShort(int level, IPlayableFaction.TitleGender titleGender) {
        return title("werewolf", level, ".short");
    }
}
