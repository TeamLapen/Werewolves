package de.teamlapen.werewolves.common.core;

import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.common.tags.ModVillagerTradeTags;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.TradeSet;
import net.minecraft.world.item.trading.TradeSets;
import net.minecraft.world.item.trading.VillagerTrade;

import java.util.List;
import java.util.Optional;

public class ModTrades {

    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_1_LIVER_EMERALD = trade("werewolf_expert/1/liver_emerald");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_1_CRACKED_BONE_EMERALD = trade("werewolf_expert/1/cracked_bone_emerald");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_1_EMERALD_LIVER = trade("werewolf_expert/1/emerald_liver");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_1_EMERALD_CRACKED_BONE = trade("werewolf_expert/1/emerald_cracked_bone");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_2_LIVER_EMERALD = trade("werewolf_expert/2/liver_emerald");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_2_CRACKED_BONE_EMERALD = trade("werewolf_expert/2/cracked_bone_emerald");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_2_EMERALD_WEREWOLF_TOOTH = trade("werewolf_expert/2/emerald_werewolf_tooth");
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
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_5_EMERALD_WEREWOLF_TOOTH = trade("werewolf_expert/5/emerald_werewolf_tooth");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_5_EMERALD_LIVER = trade("werewolf_expert/5/emerald_liver");
    public static final ResourceKey<VillagerTrade> WEREWOLF_EXPERT_5_EMERALD_CRACKED_BONE = trade("werewolf_expert/5/emerald_cracked_bone");


    public static final ResourceKey<TradeSet> WEREWOLF_EXPERT_LEVEL_1 = tradeSet("werewolf_expert/level_1");
    public static final ResourceKey<TradeSet> WEREWOLF_EXPERT_LEVEL_2 = tradeSet("werewolf_expert/level_2");
    public static final ResourceKey<TradeSet> WEREWOLF_EXPERT_LEVEL_3 = tradeSet("werewolf_expert/level_3");
    public static final ResourceKey<TradeSet> WEREWOLF_EXPERT_LEVEL_4 = tradeSet("werewolf_expert/level_4");
    public static final ResourceKey<TradeSet> WEREWOLF_EXPERT_LEVEL_5 = tradeSet("werewolf_expert/level_5");

    public static ResourceKey<VillagerTrade> trade(String path) {
        return ResourceKey.create(Registries.VILLAGER_TRADE, WIdentifier.mod(path));
    }

    public static ResourceKey<TradeSet> tradeSet(String path) {
        return ResourceKey.create(Registries.TRADE_SET, WIdentifier.mod(path));
    }

    static void bootstrapTradeSets(BootstrapContext<TradeSet> context) {
        TradeSets.register(context, WEREWOLF_EXPERT_LEVEL_1, ModVillagerTradeTags.WEREWOLF_EXPERT_LEVEL_1);
        TradeSets.register(context, WEREWOLF_EXPERT_LEVEL_2, ModVillagerTradeTags.WEREWOLF_EXPERT_LEVEL_2);
        TradeSets.register(context, WEREWOLF_EXPERT_LEVEL_3, ModVillagerTradeTags.WEREWOLF_EXPERT_LEVEL_3);
        TradeSets.register(context, WEREWOLF_EXPERT_LEVEL_4, ModVillagerTradeTags.WEREWOLF_EXPERT_LEVEL_4);
        TradeSets.register(context, WEREWOLF_EXPERT_LEVEL_5, ModVillagerTradeTags.WEREWOLF_EXPERT_LEVEL_5);
    }

    static void bootstrapTrades(BootstrapContext<VillagerTrade> context) {
        // level 1
        register(context, WEREWOLF_EXPERT_1_EMERALD_LIVER, trade(new TradeCost(Items.EMERALD, 15), new ItemStackTemplate(ModItems.LIVER.get(), 2), 5, 4));
        register(context, WEREWOLF_EXPERT_1_EMERALD_CRACKED_BONE, trade(new TradeCost(Items.EMERALD, 25), new ItemStackTemplate(ModItems.CRACKED_BONE.get(), 1), 3, 6));
        register(context, WEREWOLF_EXPERT_1_LIVER_EMERALD, trade(new TradeCost(ModItems.LIVER.get(), 1), new ItemStackTemplate(Items.EMERALD), 5, 4));
        register(context, WEREWOLF_EXPERT_1_CRACKED_BONE_EMERALD, trade(new TradeCost(ModItems.CRACKED_BONE.get(), 2), new ItemStackTemplate(Items.EMERALD), 3, 6));
        // level 2
        register(context, WEREWOLF_EXPERT_2_EMERALD_LIVER, trade(new TradeCost(Items.EMERALD, 14), new ItemStackTemplate(ModItems.LIVER.get(), 2), 5, 4));
        register(context, WEREWOLF_EXPERT_2_EMERALD_CRACKED_BONE, trade(new TradeCost(Items.EMERALD, 24), new ItemStackTemplate(ModItems.CRACKED_BONE.get(), 1), 3, 6));
        register(context, WEREWOLF_EXPERT_2_EMERALD_WEREWOLF_TOOTH, trade(new TradeCost(Items.EMERALD, 64), new ItemStackTemplate(ModItems.WEREWOLF_TOOTH.get(), 1), 12, 15));
        register(context, WEREWOLF_EXPERT_2_LIVER_EMERALD, trade(new TradeCost(ModItems.LIVER.get(), 2), new ItemStackTemplate(Items.EMERALD), 5, 4));
        register(context, WEREWOLF_EXPERT_2_CRACKED_BONE_EMERALD, trade(new TradeCost(ModItems.CRACKED_BONE.get(), 4), new ItemStackTemplate(Items.EMERALD), 3, 6));
        // level 3
        register(context, WEREWOLF_EXPERT_3_EMERALD_LIVER, trade(new TradeCost(Items.EMERALD, 13), new ItemStackTemplate(ModItems.LIVER.get(), 2), 5, 4));
        register(context, WEREWOLF_EXPERT_3_EMERALD_CRACKED_BONE, trade(new TradeCost(Items.EMERALD, 23), new ItemStackTemplate(ModItems.CRACKED_BONE.get(), 1), 3, 6));
        register(context, WEREWOLF_EXPERT_3_LIVER_EMERALD, trade(new TradeCost(ModItems.LIVER.get(), 2), new ItemStackTemplate(Items.EMERALD), 5, 4));
        register(context, WEREWOLF_EXPERT_3_CRACKED_BONE_EMERALD, trade(new TradeCost(ModItems.CRACKED_BONE.get(), 5), new ItemStackTemplate(Items.EMERALD), 3, 6));
        // level 4
        register(context, WEREWOLF_EXPERT_4_EMERALD_LIVER, trade(new TradeCost(Items.EMERALD, 12), new ItemStackTemplate(ModItems.LIVER.get(), 2), 5, 4));
        register(context, WEREWOLF_EXPERT_4_EMERALD_CRACKED_BONE, trade(new TradeCost(Items.EMERALD, 22), new ItemStackTemplate(ModItems.CRACKED_BONE.get(), 1), 3, 6));
        register(context, WEREWOLF_EXPERT_4_LIVER_EMERALD, trade(new TradeCost(ModItems.LIVER.get(), 3), new ItemStackTemplate(Items.EMERALD), 5, 4));
        register(context, WEREWOLF_EXPERT_4_CRACKED_BONE_EMERALD, trade(new TradeCost(ModItems.CRACKED_BONE.get(), 6), new ItemStackTemplate(Items.EMERALD), 3, 6));
        // level 5
        register(context, WEREWOLF_EXPERT_5_EMERALD_LIVER, trade(new TradeCost(Items.EMERALD, 10), new ItemStackTemplate(ModItems.LIVER.get(), 3), 5, 4));
        register(context, WEREWOLF_EXPERT_5_EMERALD_CRACKED_BONE, trade(new TradeCost(Items.EMERALD, 20), new ItemStackTemplate(ModItems.CRACKED_BONE.get(), 2), 3, 6));
        register(context, WEREWOLF_EXPERT_5_EMERALD_WEREWOLF_TOOTH, trade(new TradeCost(Items.EMERALD, 35), new ItemStackTemplate(ModItems.WEREWOLF_TOOTH.get(), 1), 12, 15));
        register(context, WEREWOLF_EXPERT_5_LIVER_EMERALD, trade(new TradeCost(ModItems.LIVER.get(), 3), new ItemStackTemplate(Items.EMERALD), 5, 4));
        register(context, WEREWOLF_EXPERT_5_CRACKED_BONE_EMERALD, trade(new TradeCost(ModItems.CRACKED_BONE.get(), 7), new ItemStackTemplate(Items.EMERALD), 3, 6));
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
