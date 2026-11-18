package de.teamlapen.werewolves.common.tags;

import de.teamlapen.werewolves.api.WIdentifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.trading.VillagerTrade;

public class ModVillagerTradeTags {
    public static final TagKey<VillagerTrade> WEREWOLF_EXPERT_LEVEL_1 = tag("werewolf_expert/level_1");
    public static final TagKey<VillagerTrade> WEREWOLF_EXPERT_LEVEL_2 = tag("werewolf_expert/level_2");
    public static final TagKey<VillagerTrade> WEREWOLF_EXPERT_LEVEL_3 = tag("werewolf_expert/level_3");
    public static final TagKey<VillagerTrade> WEREWOLF_EXPERT_LEVEL_4 = tag("werewolf_expert/level_4");
    public static final TagKey<VillagerTrade> WEREWOLF_EXPERT_LEVEL_5 = tag("werewolf_expert/level_5");

    private static TagKey<VillagerTrade> tag(String name) {
        return TagKey.create(Registries.VILLAGER_TRADE, WIdentifier.mod(name));
    }

}
