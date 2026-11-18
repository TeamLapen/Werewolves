package de.teamlapen.werewolves.common.core;

import com.google.common.collect.ImmutableSet;
import de.teamlapen.werewolves.common.tags.ModPoiTypeTags;
import de.teamlapen.werewolves.util.REFERENCE;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Arrays;
import java.util.Set;

public class ModVillage {

    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(Registries.VILLAGER_PROFESSION, REFERENCE.MODID);
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, REFERENCE.MODID);

    public static final DeferredHolder<PoiType, PoiType> WEREWOLF_FACTION = POI_TYPES.register("werewolf_faction", () -> new PoiType(getAllStates(ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF.get(), ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF_CRAFTED.get()), 1, 1));
    public static final DeferredHolder<VillagerProfession, VillagerProfession> WEREWOLF_EXPERT = VILLAGER_PROFESSIONS.register("werewolf_expert", () -> new VillagerProfession(Component.translatable("entity.minecraft.villager.werewoolves.werewolf_expert"), (holder) -> holder.is(ModPoiTypeTags.IS_WEREWOLF),(holder) -> holder.is(ModPoiTypeTags.IS_WEREWOLF), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_CARTOGRAPHER, Int2ObjectMap.ofEntries(
            Int2ObjectMap.entry(1, ModTrades.WEREWOLF_EXPERT_LEVEL_1),
            Int2ObjectMap.entry(2, ModTrades.WEREWOLF_EXPERT_LEVEL_2),
            Int2ObjectMap.entry(3, ModTrades.WEREWOLF_EXPERT_LEVEL_3),
            Int2ObjectMap.entry(4, ModTrades.WEREWOLF_EXPERT_LEVEL_4),
            Int2ObjectMap.entry(5, ModTrades.WEREWOLF_EXPERT_LEVEL_5)
    )));


    static void register(IEventBus bus) {
        VILLAGER_PROFESSIONS.register(bus);
        POI_TYPES.register(bus);
    }

    private static Set<BlockState> getAllStates(Block... blocks) {
        return Arrays.stream(blocks).map(block -> block.getStateDefinition().any()).collect(ImmutableSet.toImmutableSet());
    }

}
