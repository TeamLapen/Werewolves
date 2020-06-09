package de.teamlapen.werewolves.util;

import de.teamlapen.lib.lib.util.IInitListener;
import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.werewolves.core.WBlocks;
import de.teamlapen.werewolves.core.WItems;
import de.teamlapen.werewolves.core.WerewolfSkills;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.ModLifecycleEvent;

public class RegistryManager implements IInitListener {
    @Override
    public void onInitStep(Step step, ModLifecycleEvent event) {

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
}
