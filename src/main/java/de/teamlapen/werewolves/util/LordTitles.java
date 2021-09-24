package de.teamlapen.werewolves.util;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class LordTitles extends de.teamlapen.vampirism.util.LordTitles {

    private static final ITextComponent WEREWOLF_1 = new TranslationTextComponent("text.werewolves.lord_title.werewolf.1");
    private static final ITextComponent WEREWOLF_2 = new TranslationTextComponent("text.werewolves.lord_title.werewolf.2");
    private static final ITextComponent WEREWOLF_3 = new TranslationTextComponent("text.werewolves.lord_title.werewolf.3");
    private static final ITextComponent WEREWOLF_4 = new TranslationTextComponent("text.werewolves.lord_title.werewolf.4");
    private static final ITextComponent WEREWOLF_5 = new TranslationTextComponent("text.werewolves.lord_title.werewolf.5");
    private static final ITextComponent EMPTY = new StringTextComponent("");

    public static ITextComponent getWerewolfTitle(int level, boolean female) {
        switch (level) {
            case 1:
                return WEREWOLF_1;
            case 2:
                return WEREWOLF_2;
            case 3:
                return WEREWOLF_3;
            case 4:
                return WEREWOLF_4;
            case 5:
                return WEREWOLF_5;

        }
        return EMPTY;
    }
}
