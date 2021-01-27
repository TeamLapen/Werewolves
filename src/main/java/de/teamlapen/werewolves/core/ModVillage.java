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
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Map;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class ModVillage extends de.teamlapen.vampirism.core.ModVillage {

    public static final VillagerProfession werewolf_expert = getNull();

    public static final PointOfInterestType werewolf_faction = getNull();

    static void registerProfessions(IForgeRegistry<VillagerProfession> registry) {
        VillagerProfession werewolf_expert = new FactionVillagerProfession("werewolf_expert", werewolf_faction, ImmutableSet.of(), ImmutableSet.of(), null){
            @Override
            public IFaction<?> getFaction() {
                return WReference.WEREWOLF_FACTION;
            }
        }.setRegistryName(REFERENCE.MODID,"werewolf_expert");
        registry.register(werewolf_expert);
        VillagerTrades.VILLAGER_DEFAULT_TRADES.computeIfAbsent(werewolf_expert, trades -> new Int2ObjectOpenHashMap<>()).putAll(getWerewolfTrades());
    }

    static void registerPointOfInterestTypes(IForgeRegistry<PointOfInterestType> registry) {
        PointOfInterestType werewolf_faction = new FactionPointOfInterestType("werewolf_faction", ImmutableSet.of(ModBlocks.totem_top_werewolves_werewolf.getStateContainer().getBaseState()),1,1).setRegistryName(REFERENCE.MODID, "werewolf_faction");
        registry.register(werewolf_faction);
        PointOfInterestTypeInvoker.registerBlockStates_werewolves(werewolf_faction);
    }

    private static Map<Integer, VillagerTrades.ITrade[]> getWerewolfTrades() {
        return ImmutableMap.of(
                1,new VillagerTrades.ITrade[]{
                        new VillagerTrades.EmeraldForItemsTrade(ModBlocks.silver_block,4,1,4)
                },
                2,new VillagerTrades.ITrade[]{
                        new VillagerTrades.EmeraldForItemsTrade(ModBlocks.silver_block,4,1,4)
                },
                3,new VillagerTrades.ITrade[]{
                        new VillagerTrades.EmeraldForItemsTrade(ModBlocks.silver_block,4,1,4)
                },
                4,new VillagerTrades.ITrade[]{
                        new VillagerTrades.EmeraldForItemsTrade(ModBlocks.silver_block,4,1,4)
                },
                5,new VillagerTrades.ITrade[]{
                        new VillagerTrades.EmeraldForItemsTrade(ModBlocks.silver_block,4,1,4)
                }
        );
    }
}
