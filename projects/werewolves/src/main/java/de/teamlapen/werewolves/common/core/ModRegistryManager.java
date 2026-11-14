package de.teamlapen.werewolves.common.core;

import de.teamlapen.faction.Services;
import net.neoforged.bus.api.IEventBus;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class ModRegistryManager extends Services {

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
        ModBlockEntities.register(bus);
        ModVillage.register(bus);
        ModCommands.register(bus);
        ModActions.register(bus);
        ModSkills.register(bus);
        ModOils.register(bus);
        ModMinionTasks.register(bus);
        ModWorld.register(bus);
        ModAttachments.register(bus);
        ModAdvancements.register(bus);
        ModParticles.register(bus);
    }

    @Override
    public void registerModBus(IEventBus bus) {
        setupRegistries(bus);
        bus.addListener(ModBlockEntities::registerTileExtensions);
    }

    @Override
    public void registerGameBus(IEventBus bus) {
        bus.addListener(ModCommands::registerCommands);
    }
}
