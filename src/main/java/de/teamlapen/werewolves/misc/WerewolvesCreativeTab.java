package de.teamlapen.werewolves.misc;

import de.teamlapen.lib.lib.util.ModDisplayItemGenerator;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;
import java.util.function.Consumer;

import static de.teamlapen.werewolves.core.ModBlocks.*;
import static de.teamlapen.werewolves.core.ModItems.*;


@Mod.EventBusSubscriber(modid = REFERENCE.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WerewolvesCreativeTab {

    public static CreativeModeTab.Builder builder(Set<ItemLike> allItems) {
        return CreativeModeTab.builder()
                .withTabsBefore(de.teamlapen.vampirism.core.ModItems.VAMPIRISM_TAB_KEY)
                .title(Component.translatable("itemGroup.werewolves"))
                .icon(() -> ModItems.LIVER.get().getDefaultInstance())
                .displayItems(new WerewolvesDisplayItemGenerator(allItems));
    };

    public static class WerewolvesDisplayItemGenerator extends ModDisplayItemGenerator {

        public WerewolvesDisplayItemGenerator(Set<ItemLike> allItems) {
            super(allItems);
        }

        @Override
        protected void addItemsToOutput() {
            addItems();
            addBlocks();
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
    }
}
