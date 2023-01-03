package de.teamlapen.werewolves.misc;

import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.function.Consumer;

import static de.teamlapen.werewolves.core.ModBlocks.*;
import static de.teamlapen.werewolves.core.ModItems.*;


@Mod.EventBusSubscriber(modid = REFERENCE.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WerewolvesCreativeTab {

    private static final Consumer<CreativeModeTab.Builder> BUILDER = builder -> {
        builder.title(Component.translatable("itemGroup.werewolves"))
                .icon(() -> ModItems.LIVER.get().getDefaultInstance())
                .displayItems(new WerewolvesDisplayItemGenerator());
    };

    @SubscribeEvent
    public static void registerCreativeTab(CreativeModeTabEvent.Register event) {
        WReference.CREATIVE_TAB = event.registerCreativeModeTab(new ResourceLocation(REFERENCE.MODID, "default"), BUILDER);
    }

    public static class WerewolvesDisplayItemGenerator implements CreativeModeTab.DisplayItemsGenerator {

        private CreativeModeTab.Output output;
        @SuppressWarnings("FieldCanBeLocal")
        private FeatureFlagSet featureFlagSet;
        @SuppressWarnings("FieldCanBeLocal")
        private boolean hasPermission;
        private Set<ItemLike> items;

        @Override
        public void accept(@NotNull FeatureFlagSet featureFlagSet, CreativeModeTab.@NotNull Output output, boolean hasPermission) {
            this.output = output;
            this.featureFlagSet = featureFlagSet;
            this.hasPermission = hasPermission;
            this.items  = ModItems.getAllWerewolvesTabItems();

            this.addItems();
            this.addBlocks();
            this.items.forEach(output::accept);
        }

        private void addBlocks() {
            addBlock(SILVER_BLOCK);
            addBlock(RAW_SILVER_BLOCK);
            addBlock(SILVER_ORE);
            addBlock(DEEPSLATE_SILVER_ORE);
            addBlock(JACARANDA_LOG);
            addBlock(JACARANDA_LEAVES);
            addBlock(JACARANDA_SAPLING);
            addBlock(MAGIC_LOG);
            addBlock(MAGIC_PLANKS);
            addBlock(MAGIC_LEAVES);
            addBlock(MAGIC_SAPLING);
            addBlock(WOLFSBANE);
            addBlock(STONE_ALTAR);
            addBlock(STONE_ALTAR_FIRE_BOWL);
        }

        private void addItems() {
            addItem(SILVER_SWORD);
            addItem(SILVER_PICKAXE);
            addItem(SILVER_AXE);
            addItem(SILVER_SHOVEL);
            addItem(SILVER_HOE);
            addItem(CROSSBOW_ARROW_SILVER_BOLT);
            addItem(SILVER_INGOT);
            addItem(RAW_SILVER);
            addItem(SILVER_NUGGET);
            addItem(CRACKED_BONE);
            addItem(WEREWOLF_TOOTH);
            addItem(LIVER);
            addItem(BONE_NECKLACE);
            addItem(DREAM_CATCHER);
            addItem(CHARM_BRACELET);
            addItem(WEREWOLF_MINION_CHARM);
            addItem(WEREWOLF_MINION_UPGRADE_SIMPLE);
            addItem(WEREWOLF_MINION_UPGRADE_ENHANCED);
            addItem(WEREWOLF_MINION_UPGRADE_SPECIAL);
            addItem(INJECTION_UN_WEREWOLF);
        }

        private void add(ItemLike item) {
            this.items.remove(item);
            this.output.accept(item);
        }

        private void add(ItemStack item) {
            this.items.remove(item.getItem());
            this.output.accept(item);
        }

        private void addItem(RegistryObject<? extends Item> item) {
            add(item.get());
        }

        private void addBlock(RegistryObject<? extends Block> item) {
            add(item.get());
        }

        private <T extends ItemLike & CreativeTabItemProvider> void addGen(RegistryObject<T> item) {
            this.items.remove(item.get());
            item.get().generateCreativeTab(this.featureFlagSet, this.output, this.hasPermission);
        }

    }

    public interface CreativeTabItemProvider {
        void generateCreativeTab(FeatureFlagSet featureFlagSet, CreativeModeTab.Output output, boolean hasPermission);
    }

}
