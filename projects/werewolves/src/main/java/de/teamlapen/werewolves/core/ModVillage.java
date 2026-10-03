package de.teamlapen.werewolves.core;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.teamlapen.werewolves.util.REFERENCE;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class ModVillage {

    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(Registries.VILLAGER_PROFESSION, REFERENCE.MODID);
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, REFERENCE.MODID);

    public static final DeferredHolder<PoiType, PoiType> WEREWOLF_FACTION = POI_TYPES.register("werewolf_faction", () -> new PoiType(getAllStates(ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF.get(), ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF_CRAFTED.get()), 1, 1));
    public static final DeferredHolder<VillagerProfession, VillagerProfession> WEREWOLF_EXPERT = VILLAGER_PROFESSIONS.register("werewolf_expert", () -> new VillagerProfession("werewolf_expert", (holder) -> holder.is(ModTags.PoiTypes.IS_WEREWOLF),(holder) -> holder.is(ModTags.PoiTypes.IS_WEREWOLF), ImmutableSet.of(), ImmutableSet.of(), null));


    static void register(IEventBus bus) {
        VILLAGER_PROFESSIONS.register(bus);
        POI_TYPES.register(bus);
    }

    public static void villageTradeSetup() {
        VillagerTrades.TRADES.computeIfAbsent(WEREWOLF_EXPERT.get(), trades -> new Int2ObjectOpenHashMap<>()).putAll(getWerewolfTrades());
    }

    private static Set<BlockState> getAllStates(Block... blocks) {
        return Arrays.stream(blocks).map(block -> block.getStateDefinition().any()).collect(ImmutableSet.toImmutableSet());
    }

    private static Map<Integer, VillagerTrades.ItemListing[]> getWerewolfTrades() {
        return ImmutableMap.of(
                1, new VillagerTrades.ItemListing[]{
                        new VillagerTrades.ItemsForEmeralds(ModItems.LIVER.get(), 15, 2, 5, 4),
                        new VillagerTrades.ItemsForEmeralds(ModItems.CRACKED_BONE.get(), 25, 1, 3, 6),

                        new VillagerTrades.EmeraldForItems(ModItems.LIVER.get(), 1, 5, 4),
                        new VillagerTrades.EmeraldForItems(ModItems.CRACKED_BONE.get(), 2, 3, 6)
                }, 2, new VillagerTrades.ItemListing[]{
                        new VillagerTrades.ItemsForEmeralds(ModItems.LIVER.get(), 14, 2, 5, 4),
                        new VillagerTrades.ItemsForEmeralds(ModItems.CRACKED_BONE.get(), 24, 1, 3, 6),
                        new VillagerTrades.ItemsForEmeralds(ModItems.WEREWOLF_TOOTH.get(), 64, 1, 15),

                        new VillagerTrades.EmeraldForItems(ModItems.LIVER.get(), 2, 5, 4),
                        new VillagerTrades.EmeraldForItems(ModItems.CRACKED_BONE.get(), 4, 3, 6)
                }, 3, new VillagerTrades.ItemListing[]{
                        new VillagerTrades.ItemsForEmeralds(ModItems.LIVER.get(), 13, 2, 5, 4),
                        new VillagerTrades.ItemsForEmeralds(ModItems.CRACKED_BONE.get(), 23, 1, 3, 6),

                        new VillagerTrades.EmeraldForItems(ModItems.LIVER.get(), 2, 5, 4),
                        new VillagerTrades.EmeraldForItems(ModItems.CRACKED_BONE.get(), 5, 3, 6)
                }, 4, new VillagerTrades.ItemListing[]{
                        new VillagerTrades.ItemsForEmeralds(ModItems.LIVER.get(), 12, 2, 5, 4),
                        new VillagerTrades.ItemsForEmeralds(ModItems.CRACKED_BONE.get(), 22, 1, 3, 6),

                        new VillagerTrades.EmeraldForItems(ModItems.LIVER.get(), 3, 5, 4),
                        new VillagerTrades.EmeraldForItems(ModItems.CRACKED_BONE.get(), 6, 3, 6)
                }, 5, new VillagerTrades.ItemListing[]{
                        new VillagerTrades.ItemsForEmeralds(ModItems.LIVER.get(), 10, 3, 5, 4),
                        new VillagerTrades.ItemsForEmeralds(ModItems.CRACKED_BONE.get(), 20, 2, 3, 6),
                        new VillagerTrades.ItemsForEmeralds(ModItems.WEREWOLF_TOOTH.get(), 35, 1, 15),

                        new VillagerTrades.EmeraldForItems(ModItems.LIVER.get(), 3, 5, 4),
                        new VillagerTrades.EmeraldForItems(ModItems.CRACKED_BONE.get(), 7, 3, 6)
                }
        );
    }
}
