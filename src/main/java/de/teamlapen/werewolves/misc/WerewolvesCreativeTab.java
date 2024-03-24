package de.teamlapen.werewolves.misc;

import de.teamlapen.lib.lib.util.ModDisplayItemGenerator;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.ItemLike;
import net.neoforged.fml.common.Mod;

import java.util.Set;
import java.util.function.Consumer;

import static de.teamlapen.werewolves.core.ModBlocks.*;
import static de.teamlapen.werewolves.core.ModItems.*;


public class WerewolvesCreativeTab {

    public static CreativeModeTab.Builder builder(Set<ItemLike> allItems) {
        return CreativeModeTab.builder()
                .withTabsBefore(de.teamlapen.vampirism.core.ModItems.VAMPIRISM_TAB_KEY)
                .title(Component.translatable("itemGroup.werewolves"))
                .icon(() -> ModItems.LIVER.get().getDefaultInstance())
                .displayItems(new WerewolvesDisplayItemGenerator(allItems));
    }

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
            addBuildingBlocks();
            addBlock(WOLFSBANE);
            addBlock(DAFFODIL);
            addBlock(STONE_ALTAR);
            addBlock(STONE_ALTAR_FIRE_BOWL);
            addBlock(WOLFSBANE_DIFFUSER);
            addBlock(WOLFSBANE_DIFFUSER_LONG);
            addBlock(WOLFSBANE_DIFFUSER_IMPROVED);
        }

        private void addBuildingBlocks() {
            addBlock(SILVER_BLOCK);
            addBlock(RAW_SILVER_BLOCK);
            addBlock(SILVER_ORE);
            addBlock(DEEPSLATE_SILVER_ORE);

            addBlock(JACARANDA_LEAVES);
            addBlock(JACARANDA_SAPLING);
            addBlock(JACARANDA_LOG);
            addBlock(JACARANDA_WOOD);
            addBlock(STRIPPED_JACARANDA_LOG);
            addBlock(STRIPPED_JACARANDA_WOOD);
            addBlock(JACARANDA_PLANKS);
            addBlock(JACARANDA_STAIRS);
            addBlock(JACARANDA_SLAB);
            addBlock(JACARANDA_FENCE);
            addBlock(JACARANDA_FENCE_GATE);
            addBlock(JACARANDA_DOOR);
            addBlock(JACARANDA_TRAPDOOR);
            addBlock(JACARANDA_PRESSURE_PLATE);
            addBlock(JACARANDA_BUTTON);
            addItem(ModItems.JACARANDA_SIGN);

            addBlock(MAGIC_LEAVES);
            addBlock(MAGIC_SAPLING);
            addBlock(MAGIC_LOG);
            addBlock(MAGIC_WOOD);
            addBlock(STRIPPED_MAGIC_LOG);
            addBlock(STRIPPED_MAGIC_WOOD);
            addBlock(MAGIC_PLANKS);
            addBlock(MAGIC_STAIRS);
            addBlock(MAGIC_SLAB);
            addBlock(MAGIC_FENCE);
            addBlock(MAGIC_FENCE_GATE);
            addBlock(MAGIC_DOOR);
            addBlock(MAGIC_TRAPDOOR);
            addBlock(MAGIC_PRESSURE_PLATE);
            addBlock(MAGIC_BUTTON);
            addItem(ModItems.MAGIC_SIGN);
        }

        private void addItems() {
            addItem(SILVER_HELMET);
            addItem(SILVER_CHESTPLATE);
            addItem(SILVER_LEGGINGS);
            addItem(SILVER_BOOTS);
            addItem(SILVER_SWORD);
            addItem(SILVER_PICKAXE);
            addItem(SILVER_AXE);
            addItem(SILVER_SHOVEL);
            addItem(SILVER_HOE);
            addItem(CROSSBOW_ARROW_SILVER_BOLT);
            addItem(SILVER_INGOT);
            addItem(RAW_SILVER);
            addItem(SILVER_NUGGET);
            addItem(PELT_HELMET);
            addItem(PELT_CHESTPLATE);
            addItem(PELT_LEGGINGS);
            addItem(PELT_BOOTS);
            addItem(DARK_PELT_HELMET);
            addItem(DARK_PELT_CHESTPLATE);
            addItem(DARK_PELT_LEGGINGS);
            addItem(DARK_PELT_BOOTS);
            addItem(WHITE_PELT_HELMET);
            addItem(WHITE_PELT_CHESTPLATE);
            addItem(WHITE_PELT_LEGGINGS);
            addItem(WHITE_PELT_BOOTS);
            addItem(PELT);
            addItem(DARK_PELT);
            addItem(WHITE_PELT);
            addItem(WHITE_PELT_UPGRADE_SMITHING_TEMPLATE);
            addItem(CRACKED_BONE);
            addItem(WEREWOLF_TOOTH);
            addItem(LIVER);
            addItem(WOLF_BERRIES);
            addItem(BONE_NECKLACE);
            addItem(DREAM_CATCHER);
            addItem(CHARM_BRACELET);
            addItem(WEREWOLF_MINION_CHARM);
            addItem(WEREWOLF_MINION_UPGRADE_SIMPLE);
            addItem(WEREWOLF_MINION_UPGRADE_ENHANCED);
            addItem(WEREWOLF_MINION_UPGRADE_SPECIAL);
            addItem(INJECTION_UN_WEREWOLF);
            addItem(WOLFSBANE_DIFFUSER_CORE);
            addItem(WOLFSBANE_DIFFUSER_CORE_IMPROVED);
            addItem(WOLFSBANE_FINDER);
        }
    }
}
