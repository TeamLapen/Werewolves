package de.teamlapen.werewolves.core;

import de.teamlapen.lib.lib.util.IInitListener;
import de.teamlapen.werewolves.world.gen.WerewolvesBiomeFeatures;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.ParallelDispatchEvent;
import net.neoforged.neoforge.common.NeoForge;

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
        ModCommands.register(bus);
        ModActions.register(bus);
        ModSkills.register(bus);
        ModOils.register(bus);
        ModMinionTasks.register(bus);
        ModEntityActions.register(bus);
        ModWorld.register(bus);
        ModAttachments.register(bus);
        ModArmorMaterials.register(bus);
    }

    public RegistryManager(IEventBus modEventBus) {
        modEventBus.addListener(ModEntities::onRegisterEntityTypeAttributes);
        modEventBus.addListener(ModEntities::onModifyEntityTypeAttributes);
        modEventBus.addListener(ModEntities::registerSpawns);
    }

    @Override
    public void onInitStep(Step step, ParallelDispatchEvent event) {
        switch (step) {
            case COMMON_SETUP:
                WerewolvesBiomeFeatures.init();
                event.enqueueWork(ModVillage::villageTradeSetup);
                ModTiles.registerTileExtensionsUnsafe();
                break;
            case LOAD_COMPLETE:
                break;
        }
    }
}
