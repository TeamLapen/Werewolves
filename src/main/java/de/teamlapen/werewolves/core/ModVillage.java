package de.teamlapen.werewolves.core;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.entity.FactionVillagerProfession;
import de.teamlapen.vampirism.world.FactionPointOfInterestType;
import de.teamlapen.werewolves.mixin.PointOfInterestTypeInvoker;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WReference;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class ModVillage {

    public static final VillagerProfession werewolf_expert = getNull();

    public static final PointOfInterestType werewolf_faction = getNull();

    static void registerProfessions(IForgeRegistry<VillagerProfession> registry) {
        VillagerProfession werewolf_expert = new FactionVillagerProfession("werewolf_expert", werewolf_faction, ImmutableSet.of(), ImmutableSet.of(), null){
            @Override
            public IFaction<?> getFaction() {
                return WReference.WEREWOLF_FACTION;
            }
        }.setRegistryName(REFERENCE.MODID, "werewolf_expert");
        registry.register(werewolf_expert);
        VillagerTrades.TRADES.computeIfAbsent(werewolf_expert, trades -> new Int2ObjectOpenHashMap<>()).putAll(getWerewolfTrades());
    }

    static void registerPointOfInterestTypes(IForgeRegistry<PointOfInterestType> registry) {
        PointOfInterestType werewolf_faction = new FactionPointOfInterestType("werewolf_faction", getAllStates(ModBlocks.totem_top_werewolves_werewolf, ModBlocks.totem_top_werewolves_werewolf_crafted), 1, 1).setRegistryName(REFERENCE.MODID, "werewolf_faction");
        registry.register(werewolf_faction);
        PointOfInterestTypeInvoker.registerBlockStates_werewolves(werewolf_faction);
    }

    private static Set<BlockState> getAllStates(Block... blocks) {
        return Arrays.stream(blocks).map(block -> block.getStateDefinition().any()).collect(ImmutableSet.toImmutableSet());
    }

    private static Map<Integer, VillagerTrades.ITrade[]> getWerewolfTrades() {
        return ImmutableMap.of(
                1, new VillagerTrades.ITrade[]{
                        new VillagerTrades.ItemsForEmeraldsTrade(ModItems.liver, 15, 2, 5, 4),
                        new VillagerTrades.ItemsForEmeraldsTrade(ModItems.cracked_bone, 25, 1, 3, 6),

                        new VillagerTrades.EmeraldForItemsTrade(ModItems.liver, 1, 5, 4),
                        new VillagerTrades.EmeraldForItemsTrade(ModItems.cracked_bone, 2, 3, 6)
                }, 2, new VillagerTrades.ITrade[]{
                        new VillagerTrades.ItemsForEmeraldsTrade(ModItems.liver, 14, 2, 5, 4),
                        new VillagerTrades.ItemsForEmeraldsTrade(ModItems.cracked_bone, 24, 1, 3, 6),
                        new VillagerTrades.ItemsForEmeraldsTrade(ModItems.werewolf_tooth, 64, 1, 15),

                        new VillagerTrades.EmeraldForItemsTrade(ModItems.liver, 2, 5, 4),
                        new VillagerTrades.EmeraldForItemsTrade(ModItems.cracked_bone, 4, 3, 6)
                }, 3, new VillagerTrades.ITrade[]{
                        new VillagerTrades.ItemsForEmeraldsTrade(ModItems.liver, 13, 2, 5, 4),
                        new VillagerTrades.ItemsForEmeraldsTrade(ModItems.cracked_bone, 23, 1, 3, 6),

                        new VillagerTrades.EmeraldForItemsTrade(ModItems.liver, 2, 5, 4),
                        new VillagerTrades.EmeraldForItemsTrade(ModItems.cracked_bone, 5, 3, 6)
                }, 4, new VillagerTrades.ITrade[]{
                        new VillagerTrades.ItemsForEmeraldsTrade(ModItems.liver, 12, 2, 5, 4),
                        new VillagerTrades.ItemsForEmeraldsTrade(ModItems.cracked_bone, 22, 1, 3, 6),

                        new VillagerTrades.EmeraldForItemsTrade(ModItems.liver, 3, 5, 4),
                        new VillagerTrades.EmeraldForItemsTrade(ModItems.cracked_bone, 6, 3, 6)
                }, 5, new VillagerTrades.ITrade[]{
                        new VillagerTrades.ItemsForEmeraldsTrade(ModItems.liver, 10, 3, 5, 4),
                        new VillagerTrades.ItemsForEmeraldsTrade(ModItems.cracked_bone, 20, 2, 3, 6),
                        new VillagerTrades.ItemsForEmeraldsTrade(ModItems.werewolf_tooth, 35, 1, 15),

                        new VillagerTrades.EmeraldForItemsTrade(ModItems.liver, 3, 5, 4),
                        new VillagerTrades.EmeraldForItemsTrade(ModItems.cracked_bone, 7, 3, 6)
                }
        );
    }
}
