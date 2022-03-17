package de.teamlapen.werewolves.core;

import de.teamlapen.lib.lib.util.IInitListener;
import de.teamlapen.vampirism.api.entity.actions.IEntityAction;
import de.teamlapen.vampirism.api.entity.minion.IMinionTask;
import de.teamlapen.vampirism.api.entity.player.actions.IAction;
import de.teamlapen.vampirism.api.entity.player.refinement.IRefinement;
import de.teamlapen.vampirism.api.entity.player.refinement.IRefinementSet;
import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.vampirism.api.entity.player.task.Task;
import de.teamlapen.werewolves.items.oil.IOil;
import de.teamlapen.werewolves.world.WerewolvesBiomeFeatures;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
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
                ModItems.registerOilRecipes();
                break;
            case LOAD_COMPLETE:
                break;
        }
    }

    @SubscribeEvent
    public void onRegisterSkills(RegistryEvent.Register<ISkill<?>> event) {
        WerewolfSkills.registerWerewolfSkills(event.getRegistry());
    }

    @SubscribeEvent
    public void onRegisterBlocks(RegistryEvent.Register<Block> event) {
        ModBlocks.registerBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public void onRegisterItems(RegistryEvent.Register<Item> event) {
        ModItems.registerItems(event.getRegistry());
        ModBlocks.registerItemBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public void onMissingItem(RegistryEvent.MissingMappings<Item> event) {
        ModItems.remapItems(event);
    }

    @SubscribeEvent
    public void onRegisterBiomes(RegistryEvent.Register<Biome> event) {
        ModBiomes.registerBiomes(event.getRegistry());
    }

    @SubscribeEvent
    public void onRegisterEffects(RegistryEvent.Register<MobEffect> event) {
        ModEffects.registerEffects(event.getRegistry());
    }

    @SubscribeEvent
    public void onRegisterActions(RegistryEvent.Register<IAction<?>> event) {
        ModActions.registerActions(event.getRegistry());
    }

    @SubscribeEvent
    public void onRegisterEntityTypes(RegistryEvent.Register<EntityType<?>> event) {
        ModEntities.registerEntities(event.getRegistry());
    }

    @SubscribeEvent
    public void onRegisterFeatures(RegistryEvent.Register<Feature<?>> event) {
    }

    @SubscribeEvent
    public void onRegisterProfessions(RegistryEvent.Register<VillagerProfession> event) {
        ModVillage.registerProfessions(event.getRegistry());
    }

    @SubscribeEvent
    public void onRegisterPointOfInterests(RegistryEvent.Register<PoiType> event) {
        ModVillage.registerPointOfInterestTypes(event.getRegistry());
    }

    @SubscribeEvent
    public void onRegisterTiles(RegistryEvent.Register<BlockEntityType<?>> event) {
        ModTiles.registerTiles(event.getRegistry());
    }

    @SubscribeEvent
    public void onRegisterEntityActions(RegistryEvent.Register<IEntityAction> event) {
        ModEntityActions.registerEntityActions(event.getRegistry());
    }

    @SubscribeEvent
    public void onRegisterAttributes(RegistryEvent.Register<Attribute> event) {
        ModAttributes.registerAttributes(event.getRegistry());
    }

    @SubscribeEvent
    public void onRegisterGlobalLootModifierSerializer(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event){
        ModLootTables.registerLootModifier(event.getRegistry());
    }

    @SubscribeEvent
    public void onRegisterTasks(RegistryEvent.Register<Task> event){
        ModTasks.registerTasks(event.getRegistry());
    }

    @SubscribeEvent
    public void onRegisterMinionTasks(RegistryEvent.Register<IMinionTask<?, ?>> event) {
        ModMinionTasks.register(event.getRegistry());
    }

    @SubscribeEvent
    public void onRegisterWeaponOils(RegistryEvent.Register<IOil> event) {
        ModOils.register(event.getRegistry());
    }

    @SubscribeEvent
    public void onRegisterRecipeSerializer(RegistryEvent.Register<RecipeSerializer<?>> event){
        ModRecipes.register(event.getRegistry());
    }

    @SubscribeEvent
    public void onRegisterRefinementSets(RegistryEvent.Register<IRefinementSet> event) {
        ModRefinementSets.register(event.getRegistry());
    }

    @SubscribeEvent
    public void onRegisterRefinements(RegistryEvent.Register<IRefinement> event) {
        ModRefinements.register(event.getRegistry());
    }

    @SubscribeEvent
    public void onRegisterSoundEvents(RegistryEvent.Register<SoundEvent> event) {
        ModSounds.registerSounds(event.getRegistry());
    }
}
