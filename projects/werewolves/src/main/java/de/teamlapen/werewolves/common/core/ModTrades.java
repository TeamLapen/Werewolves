package de.teamlapen.werewolves.common.core;

import de.teamlapen.vampirism.api.util.VIdentifier;
import de.teamlapen.vampirism.common.tags.ModVillagerTradeTags;
import de.teamlapen.werewolves.api.WIdentifier;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.TradeSet;
import net.minecraft.world.item.trading.TradeSets;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;
import java.util.Optional;

public class ModTrades {

    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_1_LIVER_EMERALD = trade("werewolf_expert/1/liver_emerald");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_1_CRACKED_BONE_EMERALD = trade("werewolf_expert/1/cracked_bone_emerald");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_1_EMERALD_LIVER = trade("werewolf_expert/1/emerald_liver");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_1_EMERALD_CRACKED_BONE = trade("werewolf_expert/1/emerald_cracked_bone");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_2_LIVER_EMERALD = trade("werewolf_expert/2/liver_emerald");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_2_CRACKED_BONE_EMERALD = trade("werewolf_expert/2/cracked_bone_emerald");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_2_WEREWOLF_TOOTH_EMERALD = trade("werewolf_expert/2/werewolf_tooth_emerald");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_2_EMERALD_LIVER = trade("werewolf_expert/2/emerald_liver");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_2_EMERALD_CRACKED_BONE = trade("werewolf_expert/2/emerald_cracked_bone");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_3_LIVER_EMERALD = trade("werewolf_expert/3/liver_emerald");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_3_CRACKED_BONE_EMERALD = trade("werewolf_expert/3/cracked_bone_emerald");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_3_EMERALD_LIVER = trade("werewolf_expert/3/emerald_liver");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_3_EMERALD_CRACKED_BONE = trade("werewolf_expert/3/emerald_cracked_bone");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_4_LIVER_EMERALD = trade("werewolf_expert/4/liver_emerald");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_4_CRACKED_BONE_EMERALD = trade("werewolf_expert/4/cracked_bone_emerald");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_4_EMERALD_LIVER = trade("werewolf_expert/4/emerald_liver");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_4_EMERALD_CRACKED_BONE = trade("werewolf_expert/4/emerald_cracked_bone");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_5_LIVER_EMERALD = trade("werewolf_expert/5/liver_emerald");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_5_CRACKED_BONE_EMERALD = trade("werewolf_expert/5/cracked_bone_emerald");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_5_WEREWOLF_TOOTH_EMERALD = trade("werewolf_expert/5/cracked_bone_emerald");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_5_EMERALD_LIVER = trade("werewolf_expert/5/emerald_liver");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_5_EMERALD_CRACKED_BONE = trade("werewolf_expert/5/emerald_cracked_bone");


    public static final ResourceKey<TradeSet> WEREWOLF_EXPERT_LEVEL_1 = tradeSet("werewolf_expert/level_1");
    public static final ResourceKey<TradeSet> WEREWOLF_EXPERT_LEVEL_2 = tradeSet("werewolf_expert/level_2");
    public static final ResourceKey<TradeSet> WEREWOLF_EXPERT_LEVEL_3 = tradeSet("werewolf_expert/level_3");
    public static final ResourceKey<TradeSet> WEREWOLF_EXPERT_LEVEL_4 = tradeSet("werewolf_expert/level_4");
    public static final ResourceKey<TradeSet> WEREWOLF_EXPERT_LEVEL_5 = tradeSet("werewolf_expert/level_5");

    public static ResourceKey<TradeSet> tradeSet(String path) {
        return ResourceKey.create(Registries.TRADE_SET, WIdentifier.mod(path));
    }

    static void bootstrapTradeSets(BootstrapContext<TradeSet> context) {
        TradeSets.register(context, WEREWOLF_EXPERT_LEVEL_1, ModTags.VillagerTradeTags.WEREWOLF_EXPERT_LEVEL_1);
        TradeSets.register(context, WEREWOLF_EXPERT_LEVEL_2, ModTags.VillagerTradeTags.WEREWOLF_EXPERT_LEVEL_2);
        TradeSets.register(context, WEREWOLF_EXPERT_LEVEL_3, ModTags.VillagerTradeTags.WEREWOLF_EXPERT_LEVEL_3);
        TradeSets.register(context, WEREWOLF_EXPERT_LEVEL_4, ModTags.VillagerTradeTags.WEREWOLF_EXPERT_LEVEL_4);
        TradeSets.register(context, WEREWOLF_EXPERT_LEVEL_5, ModTags.VillagerTradeTags.WEREWOLF_EXPERT_LEVEL_5);
    }

    static void bootstrap(BootstrapContext<VillagerTrade> context) {
        HolderGetter<Item> items = context.lookup(Registries.ITEM);

        register(context, WEREWOLF_EXPERT_1_CRACKED_BONE_EMERALD, trade(new TradeCost(ModItems.CRACKED_BONE.get(), 15), new ItemStackTemplate(Items.EMERALD), 15, 2));
    }

    private static VillagerTrade trade(TradeCost wants,
                                       ItemStackTemplate gives,
                                       int maxUses,
                                       int xp) {
        return new VillagerTrade(wants, gives, maxUses, xp, 0.05f, Optional.empty(), List.of());
    }

    public static Holder.Reference<VillagerTrade> register(
            BootstrapContext<VillagerTrade> context, ResourceKey<VillagerTrade> resourceKey, VillagerTrade villagerTrade
    ) {
        return context.register(resourceKey, villagerTrade);
    }
}
