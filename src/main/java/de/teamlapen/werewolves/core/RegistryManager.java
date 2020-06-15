package de.teamlapen.werewolves.core;

import de.teamlapen.lib.lib.util.IInitListener;
import de.teamlapen.vampirism.api.entity.player.actions.IAction;
import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.vampirism.core.ModBiomes;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.ModLifecycleEvent;

@SuppressWarnings("unused")
public class RegistryManager implements IInitListener {
    @Override
    public void onInitStep(Step step, ModLifecycleEvent event) {
        switch (step) {
            case COMMON_SETUP:
                WBiomes.addBiomes();
                WEntities.registerSpawnEntries();
                break;
        }
    }

    @SubscribeEvent
    public void onRegisterSkills(RegistryEvent.Register<ISkill> event) {
        WerewolfSkills.registerWerewolfSkills(event.getRegistry());
    }

    @SubscribeEvent
    public void onRegisterBlocks(RegistryEvent.Register<Block> event) {
        WBlocks.registerBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public void onRegisterItems(RegistryEvent.Register<Item> event) {
        WItems.registerItems(event.getRegistry());
        WBlocks.registerItemBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public void onRegisterBiomes(RegistryEvent.Register<Biome> event) {
        WBiomes.registerBiomes(event.getRegistry());
    }

    @SubscribeEvent
    public void onRegisterEffects(RegistryEvent.Register<Effect> event) {
        WEffects.registerEffects(event.getRegistry());
    }

    @SubscribeEvent
    public void onRegisterActions(RegistryEvent.Register<IAction> event) {
        WerewolfActions.registerActions(event.getRegistry());
    }

    @SubscribeEvent
    public void onRegisterEntityTypes(RegistryEvent.Register<EntityType<?>> event) {
        WEntities.registerEntities(event.getRegistry());
    }
}
