package de.teamlapen.werewolves.core;

import de.teamlapen.lib.lib.util.IInitListener;
import de.teamlapen.vampirism.api.entity.player.actions.IAction;
import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.vampirism.api.entity.player.skills.SkillEvent;
import de.teamlapen.werewolves.api.VReference;
import de.teamlapen.werewolves.compat.OreDictionaryCompat;
import de.teamlapen.werewolves.player.werewolf.actions.WerewolfActions;
import de.teamlapen.werewolves.player.werewolf.skills.WerewolfSkills;
import de.teamlapen.werewolves.world.gen.WerewolvesWorldGen;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLStateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.ObjectHolderRegistry;

public class RegistryManager implements IInitListener {

    /**
     * Delegate for some client side registrations
     */
    @SideOnly(Side.CLIENT)
    private static de.teamlapen.werewolves.client.core.RegistryManagerClient registryManagerClient;

    @SideOnly(Side.CLIENT)
    public static void setupClientRegistryManager() {
        registryManagerClient = new de.teamlapen.werewolves.client.core.RegistryManagerClient();
        MinecraftForge.EVENT_BUS.register(registryManagerClient);
    }

    @SideOnly(Side.CLIENT)
    public static de.teamlapen.werewolves.client.core.RegistryManagerClient getRegistryManagerClient() {
        return registryManagerClient;
    }

    @Override
    public void onInitStep(Step step, FMLStateEvent event) {
        switch (step) {
            case INIT:
                ModItems.registerCraftingRecipes();
                OreDictionaryCompat.registerOres();
                GameRegistry.registerWorldGenerator(new WerewolvesWorldGen(), 0);
                break;
            case PRE_INIT:
                break;
            case POST_INIT:
                break;
            default:
                break;
        }
    }

    @SubscribeEvent
    public void onRegisterActions(RegistryEvent.Register<IAction> event) {
        WerewolfActions.registerDefaultActions(event.getRegistry());
        ObjectHolderRegistry.INSTANCE.applyObjectHolders();
    }

    @SubscribeEvent
    public void onRegisterSkills(RegistryEvent.Register<ISkill> event) {
        WerewolfSkills.registerWerewolfSkills(event.getRegistry());
    }

    @SubscribeEvent
    public void onSkillNodeCreated(SkillEvent.CreatedNode event) {
        if (event.getNode().isRoot()) {
            if (event.getNode().getFaction().equals(VReference.WEREWOLF_FACTION)) {
                WerewolfSkills.buildSkillTree(event.getNode());
            }
        }
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
    public void onRegisterEntities(RegistryEvent.Register<EntityEntry> event) {
        ModEntities.registerEntities(event.getRegistry());
    }

    @SubscribeEvent
    public void onRegisterBiomes(RegistryEvent.Register<Biome> event) {
        ModBiomes.registerBiomes(event.getRegistry());
    }
}
