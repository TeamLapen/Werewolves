package de.teamlapen.werewolves.core;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.entity.FactionVillagerProfession;
import de.teamlapen.vampirism.world.FactionPointOfInterestType;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WReference;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class ModVillage {

    public static DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, REFERENCE.MODID);
    public static DeferredRegister<PointOfInterestType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, REFERENCE.MODID);

    public static final RegistryObject<PointOfInterestType> WEREWOLF_FACTION = POI_TYPES.register("werewolf_faction", () -> new FactionPointOfInterestType("werewolf_faction", getAllStates(ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF.get(), ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF_CRAFTED.get()), 1, 1));
    public static final RegistryObject<VillagerProfession> WEREWOLF_EXPERT = PROFESSIONS.register("werewolf_expert", () -> new FactionVillagerProfession("werewolf_expert", WEREWOLF_FACTION.get(), ImmutableSet.of(), ImmutableSet.of(), null){
        @Override
        public IFaction<?> getFaction() {
            return WReference.WEREWOLF_FACTION;
        }
    });

    static void registerVillageObjects(IEventBus bus) {
        PROFESSIONS.register(bus);
        POI_TYPES.register(bus);
    }

    static void villageTradeSetup() {
        VillagerTrades.TRADES.computeIfAbsent(WEREWOLF_EXPERT.get(), trades -> new Int2ObjectOpenHashMap<>()).putAll(getWerewolfTrades());
    }

    private static Set<BlockState> getAllStates(Block... blocks) {
        return Arrays.stream(blocks).map(block -> block.getStateDefinition().any()).collect(ImmutableSet.toImmutableSet());
    }

    private static Map<Integer, VillagerTrades.ITrade[]> getWerewolfTrades() {
        return ImmutableMap.of(
                1, new VillagerTrades.ITrade[]{
                        new VillagerTrades.ItemsForEmeraldsTrade(ModItems.LIVER.get(), 15, 2, 5, 4),
                        new VillagerTrades.ItemsForEmeraldsTrade(ModItems.CRACKED_BONE.get(), 25, 1, 3, 6),

                        new VillagerTrades.EmeraldForItemsTrade(ModItems.LIVER.get(), 1, 5, 4),
                        new VillagerTrades.EmeraldForItemsTrade(ModItems.CRACKED_BONE.get(), 2, 3, 6)
                }, 2, new VillagerTrades.ITrade[]{
                        new VillagerTrades.ItemsForEmeraldsTrade(ModItems.LIVER.get(), 14, 2, 5, 4),
                        new VillagerTrades.ItemsForEmeraldsTrade(ModItems.CRACKED_BONE.get(), 24, 1, 3, 6),
                        new VillagerTrades.ItemsForEmeraldsTrade(ModItems.WEREWOLF_TOOTH.get(), 64, 1, 15),

                        new VillagerTrades.EmeraldForItemsTrade(ModItems.LIVER.get(), 2, 5, 4),
                        new VillagerTrades.EmeraldForItemsTrade(ModItems.CRACKED_BONE.get(), 4, 3, 6)
                }, 3, new VillagerTrades.ITrade[]{
                        new VillagerTrades.ItemsForEmeraldsTrade(ModItems.LIVER.get(), 13, 2, 5, 4),
                        new VillagerTrades.ItemsForEmeraldsTrade(ModItems.CRACKED_BONE.get(), 23, 1, 3, 6),

                        new VillagerTrades.EmeraldForItemsTrade(ModItems.LIVER.get(), 2, 5, 4),
                        new VillagerTrades.EmeraldForItemsTrade(ModItems.CRACKED_BONE.get(), 5, 3, 6)
                }, 4, new VillagerTrades.ITrade[]{
                        new VillagerTrades.ItemsForEmeraldsTrade(ModItems.LIVER.get(), 12, 2, 5, 4),
                        new VillagerTrades.ItemsForEmeraldsTrade(ModItems.CRACKED_BONE.get(), 22, 1, 3, 6),

                        new VillagerTrades.EmeraldForItemsTrade(ModItems.LIVER.get(), 3, 5, 4),
                        new VillagerTrades.EmeraldForItemsTrade(ModItems.CRACKED_BONE.get(), 6, 3, 6)
                }, 5, new VillagerTrades.ITrade[]{
                        new VillagerTrades.ItemsForEmeraldsTrade(ModItems.LIVER.get(), 10, 3, 5, 4),
                        new VillagerTrades.ItemsForEmeraldsTrade(ModItems.CRACKED_BONE.get(), 20, 2, 3, 6),
                        new VillagerTrades.ItemsForEmeraldsTrade(ModItems.WEREWOLF_TOOTH.get(), 35, 1, 15),

                        new VillagerTrades.EmeraldForItemsTrade(ModItems.LIVER.get(), 3, 5, 4),
                        new VillagerTrades.EmeraldForItemsTrade(ModItems.CRACKED_BONE.get(), 7, 3, 6)
                }
        );
    }
}
