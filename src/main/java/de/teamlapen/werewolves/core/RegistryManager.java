package de.teamlapen.werewolves.core;

import de.teamlapen.lib.lib.util.IInitListener;
import de.teamlapen.werewolves.world.gen.WerewolvesBiomeFeatures;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.MissingMappingsEvent;

@SuppressWarnings("unused")
public class RegistryManager implements IInitListener {

    public static void setupRegistries(IEventBus bus) {
        ModBlocks.register(bus);
        ModItems.register(bus);
        ModAttributes.register(bus);
        ModContainer.register(bus);
        ModEffects.register(bus);
        ModEntities.register(bus);
        ModRecipes.register(bus);
        ModRefinements.register(bus);
        ModRefinementSets.register(bus);
        ModSounds.register(bus);
        ModTiles.register(bus);
        ModVillage.register(bus);
        ModLootTables.register(bus);
        ModCommands.register(bus);
        ModActions.register(bus);
        ModSkills.register(bus);
        ModOils.register(bus);
        ModMinionTasks.register(bus);
        ModEntityActions.register(bus);
        ModWorld.register(bus);
    }

    public RegistryManager() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.register(ModContainer.class);
        bus.addListener(ModEntities::onRegisterEntityTypeAttributes);
        bus.addListener(ModEntities::onModifyEntityTypeAttributes);
        MinecraftForge.EVENT_BUS.addListener(this::onMissingMappings);
    }

    @Override
    public void onInitStep(Step step, ParallelDispatchEvent event) {
        switch (step) {
            case COMMON_SETUP:
                ModEntities.registerSpawns();
                WerewolvesBiomeFeatures.init();
                event.enqueueWork(ModVillage::villageTradeSetup);
                ModTiles.registerTileExtensionsUnsafe();
                break;
            case LOAD_COMPLETE:
                break;
        }
    }

    public void onMissingMappings(MissingMappingsEvent event){
        ModItems.remapItems(event);
    }
}
