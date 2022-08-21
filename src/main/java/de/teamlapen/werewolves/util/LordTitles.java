package de.teamlapen.werewolves.util;

import net.minecraft.network.chat.Component;

public class LordTitles extends de.teamlapen.vampirism.util.LordTitles {

    private static final Component WEREWOLF_1 = Component.translatable("text.werewolves.lord_title.werewolf.1");
    private static final Component WEREWOLF_2 = Component.translatable("text.werewolves.lord_title.werewolf.2");
    private static final Component WEREWOLF_3 = Component.translatable("text.werewolves.lord_title.werewolf.3");
    private static final Component WEREWOLF_4 = Component.translatable("text.werewolves.lord_title.werewolf.4");
    private static final Component WEREWOLF_5 = Component.translatable("text.werewolves.lord_title.werewolf.5");
    private static final Component EMPTY = Component.empty();

    public static Component getWerewolfTitle(int level, boolean female) {
        return switch (level) {
            case 1 -> WEREWOLF_1;
            case 2 -> WEREWOLF_2;
            case 3 -> WEREWOLF_3;
            case 4 -> WEREWOLF_4;
            case 5 -> WEREWOLF_5;
            default -> EMPTY;
        };
    }
}
