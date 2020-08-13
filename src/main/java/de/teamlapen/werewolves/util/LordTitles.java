package de.teamlapen.werewolves.util;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class LordTitles extends de.teamlapen.vampirism.util.LordTitles {
    public static ITextComponent getWerewolfTitle(int level, boolean female) {
        return new StringTextComponent("Lord " + level);
    }
}
