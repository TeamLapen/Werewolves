package de.teamlapen.werewolves.core;

import de.teamlapen.lib.lib.util.IInitListener;
import de.teamlapen.werewolves.world.WerewolvesBiomeFeatures;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@SuppressWarnings("unused")
public class RegistryManager implements IInitListener {

    public RegistryManager() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.register(ModContainer.class);
        bus.addListener(ModEntities::onRegisterEntityTypeAttributes);
        bus.addListener(ModEntities::onModifyEntityTypeAttributes);
    }

    @SubscribeEvent
    public void onBuildRegistries(RegistryEvent.NewRegistry event) {
        ModRegistries.init();
    }

    @Override
    public void onInitStep(Step step, ParallelDispatchEvent event) {
        switch (step) {
            case COMMON_SETUP:
                event.enqueueWork(ModBiomes::addBiomesToGeneratorUnsafe);
                ModEntities.registerSpawns();
                event.enqueueWork(ModCommands::registerArgumentTypesUsages);
                WerewolvesBiomeFeatures.init();
                ModBiomes.removeStructuresFromBiomes();
                event.enqueueWork(ModVillage::villageTradeSetup);
                event.enqueueWork(ModEntities::initializeEntities);
                event.enqueueWork(WerewolvesBiomeFeatures::registerBiomeFeatures);
                break;
            case LOAD_COMPLETE:
                break;
        }
    }

    @SubscribeEvent
    public void onMissingItem(RegistryEvent.MissingMappings<Item> event) {
        ModItems.remapItems(event);
    }

    public static void setupRegistries(IEventBus bus) {
        ModActions.registerActions(bus);
        ModAttributes.registerAttributes(bus);
        ModBiomes.registerBiomes(bus);
        ModBlocks.registerBlocks(bus);
        ModContainer.registerContainers(bus);
        ModEffects.registerEffects(bus);
        ModEntities.registerEntities(bus);
        ModEntityActions.registerEntityActions(bus);
        ModItems.registerItems(bus);
        ModMinionTasks.registerMinionTasks(bus);
        ModOils.registerOils(bus);
        ModRefinements.registerRefinements(bus);
        ModRefinementSets.registerRefinementSets(bus);
        ModTasks.registerTasks(bus);
        ModVillage.registerVillageObjects(bus);
        WerewolfSkills.registerSkills(bus);
        ModTiles.registerTiles(bus);
        ModLootTables.registerLootModifier(bus);
        ModSounds.registerSounds(bus);
    }
}
