package de.teamlapen.werewolves.core;

import de.teamlapen.lib.lib.util.IInitListener;
import de.teamlapen.werewolves.world.gen.WerewolvesBiomeFeatures;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.NewRegistryEvent;

@SuppressWarnings("unused")
public class RegistryManager implements IInitListener {

    public static void setupRegistries(IEventBus bus) {
        ModAttributes.register(bus);
        ModBiomes.register(bus);
        ModBlocks.register(bus);
        ModContainer.register(bus);
        ModEffects.register(bus);
        ModEntities.register(bus);
        ModItems.register(bus);
        ModRecipes.register(bus);
        ModRefinements.register(bus);
        ModRefinementSets.register(bus);
        ModSounds.register(bus);
        ModTasks.register(bus);
        ModTiles.register(bus);
        ModVillage.register(bus);
    }

    public RegistryManager() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.register(ModContainer.class);
        bus.addListener(ModEntities::onRegisterEntityTypeAttributes);
        bus.addListener(ModEntities::onModifyEntityTypeAttributes);
    }

    @SubscribeEvent
    public void onBuildRegistries(NewRegistryEvent event) {
        ModRegistries.createRegistries(event);
    }

    @Override
    public void onInitStep(Step step, ParallelDispatchEvent event) {
        switch (step) {
            case COMMON_SETUP:
                ModEntities.registerSpawns();
                event.enqueueWork(ModCommands::registerArgumentTypesUsages);
                WerewolvesBiomeFeatures.init();
                ModItems.registerOilRecipes();
                event.enqueueWork(ModVillage::villageTradeSetup);
                break;
            case LOAD_COMPLETE:
                break;
        }
    }


    @SubscribeEvent
    public void onMissingItem(RegistryEvent.MissingMappings<Item> event) {
        ModItems.remapItems(event);
    }

    @SubscribeEvent
    public void onRegisterGlobalLootModifierSerializer(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        ModLootTables.registerLootModifier(event.getRegistry());
    }
}
