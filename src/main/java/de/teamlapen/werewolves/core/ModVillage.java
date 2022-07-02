package de.teamlapen.werewolves.core;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.entity.FactionVillagerProfession;
import de.teamlapen.vampirism.world.FactionPointOfInterestType;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.util.REFERENCE;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class ModVillage {

    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(ForgeRegistries.Keys.VILLAGER_PROFESSIONS, REFERENCE.MODID);
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.Keys.POI_TYPES, REFERENCE.MODID);

    public static final RegistryObject<PoiType> werewolf_faction = POI_TYPES.register("werewolf_faction", () -> new FactionPointOfInterestType("werewolf_faction", getAllStates(ModBlocks.totem_top_werewolves_werewolf.get(), ModBlocks.totem_top_werewolves_werewolf_crafted.get()), 1, 1));
    public static final RegistryObject<VillagerProfession> werewolf_expert = VILLAGER_PROFESSIONS.register("werewolf_expert", () -> new FactionVillagerProfession("werewolf_expert", werewolf_faction.get(), ImmutableSet.of(), ImmutableSet.of(), null) {
        @Override
        public IFaction<?> getFaction() {
            return WReference.WEREWOLF_FACTION;
        }
    });


    static void register(IEventBus bus) {
        VILLAGER_PROFESSIONS.register(bus);
        POI_TYPES.register(bus);
    }

    public static void villageTradeSetup() {
        VillagerTrades.TRADES.computeIfAbsent(werewolf_expert.get(), trades -> new Int2ObjectOpenHashMap<>()).putAll(getWerewolfTrades());
    }

    private static Set<BlockState> getAllStates(Block... blocks) {
        return Arrays.stream(blocks).map(block -> block.getStateDefinition().any()).collect(ImmutableSet.toImmutableSet());
    }

    private static Map<Integer, VillagerTrades.ItemListing[]> getWerewolfTrades() {
        return ImmutableMap.of(
                1, new VillagerTrades.ItemListing[]{
                        new VillagerTrades.ItemsForEmeralds(ModItems.liver.get(), 15, 2, 5, 4),
                        new VillagerTrades.ItemsForEmeralds(ModItems.cracked_bone.get(), 25, 1, 3, 6),

                        new VillagerTrades.EmeraldForItems(ModItems.liver.get(), 1, 5, 4),
                        new VillagerTrades.EmeraldForItems(ModItems.cracked_bone.get(), 2, 3, 6)
                }, 2, new VillagerTrades.ItemListing[]{
                        new VillagerTrades.ItemsForEmeralds(ModItems.liver.get(), 14, 2, 5, 4),
                        new VillagerTrades.ItemsForEmeralds(ModItems.cracked_bone.get(), 24, 1, 3, 6),
                        new VillagerTrades.ItemsForEmeralds(ModItems.werewolf_tooth.get(), 64, 1, 15),

                        new VillagerTrades.EmeraldForItems(ModItems.liver.get(), 2, 5, 4),
                        new VillagerTrades.EmeraldForItems(ModItems.cracked_bone.get(), 4, 3, 6)
                }, 3, new VillagerTrades.ItemListing[]{
                        new VillagerTrades.ItemsForEmeralds(ModItems.liver.get(), 13, 2, 5, 4),
                        new VillagerTrades.ItemsForEmeralds(ModItems.cracked_bone.get(), 23, 1, 3, 6),

                        new VillagerTrades.EmeraldForItems(ModItems.liver.get(), 2, 5, 4),
                        new VillagerTrades.EmeraldForItems(ModItems.cracked_bone.get(), 5, 3, 6)
                }, 4, new VillagerTrades.ItemListing[]{
                        new VillagerTrades.ItemsForEmeralds(ModItems.liver.get(), 12, 2, 5, 4),
                        new VillagerTrades.ItemsForEmeralds(ModItems.cracked_bone.get(), 22, 1, 3, 6),

                        new VillagerTrades.EmeraldForItems(ModItems.liver.get(), 3, 5, 4),
                        new VillagerTrades.EmeraldForItems(ModItems.cracked_bone.get(), 6, 3, 6)
                }, 5, new VillagerTrades.ItemListing[]{
                        new VillagerTrades.ItemsForEmeralds(ModItems.liver.get(), 10, 3, 5, 4),
                        new VillagerTrades.ItemsForEmeralds(ModItems.cracked_bone.get(), 20, 2, 3, 6),
                        new VillagerTrades.ItemsForEmeralds(ModItems.werewolf_tooth.get(), 35, 1, 15),

                        new VillagerTrades.EmeraldForItems(ModItems.liver.get(), 3, 5, 4),
                        new VillagerTrades.EmeraldForItems(ModItems.cracked_bone.get(), 7, 3, 6)
                }
        );
    }
}
