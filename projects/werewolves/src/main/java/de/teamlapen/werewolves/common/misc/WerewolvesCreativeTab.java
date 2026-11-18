package de.teamlapen.werewolves.common.misc;

import de.teamlapen.library.common.world.items.BaseDisplayItemGenerator;
import de.teamlapen.werewolves.common.core.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

import static de.teamlapen.werewolves.common.core.ModItems.*;


public class WerewolvesCreativeTab {

    public static CreativeModeTab.Builder builder() {
        return CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.werewolves"))
                .icon(() -> ModItems.LIVER.get().getDefaultInstance())
                .displayItems(new WerewolvesDisplayItemGenerator());
    }

    public static class WerewolvesDisplayItemGenerator extends BaseDisplayItemGenerator {

        @Override
        protected void addAll() {
            addItems();
            addBlocks();
        }

        private void addBlocks() {
            addBuildingBlocks();
            add(WOLFSBANE);
            add(DAFFODIL);
            add(STONE_ALTAR);
            add(STONE_ALTAR_FIRE_BOWL);
        }

        private void addBuildingBlocks() {
            add(SILVER_BLOCK);
            add(RAW_SILVER_BLOCK);
            add(SILVER_ORE);
            add(DEEPSLATE_SILVER_ORE);

            add(JACARANDA_LEAVES);
            add(JACARANDA_SAPLING);
            add(JACARANDA_LOG);
            add(JACARANDA_WOOD);
            add(STRIPPED_JACARANDA_LOG);
            add(STRIPPED_JACARANDA_WOOD);
            add(JACARANDA_PLANKS);
            add(JACARANDA_STAIRS);
            add(JACARANDA_SLAB);
            add(JACARANDA_FENCE);
            add(JACARANDA_FENCE_GATE);
            add(JACARANDA_DOOR);
            add(JACARANDA_TRAPDOOR);
            add(JACARANDA_PRESSURE_PLATE);
            add(JACARANDA_BUTTON);
            add(ModItems.JACARANDA_SIGN);

            add(MAGIC_LEAVES);
            add(MAGIC_SAPLING);
            add(MAGIC_LOG);
            add(MAGIC_WOOD);
            add(STRIPPED_MAGIC_LOG);
            add(STRIPPED_MAGIC_WOOD);
            add(MAGIC_PLANKS);
            add(MAGIC_STAIRS);
            add(MAGIC_SLAB);
            add(MAGIC_FENCE);
            add(MAGIC_FENCE_GATE);
            add(MAGIC_DOOR);
            add(MAGIC_TRAPDOOR);
            add(MAGIC_PRESSURE_PLATE);
            add(MAGIC_BUTTON);
            add(MAGIC_SIGN);
        }

        private void addItems() {
            add(SILVER_HELMET);
            add(SILVER_CHESTPLATE);
            add(SILVER_LEGGINGS);
            add(SILVER_BOOTS);
            add(SILVER_SWORD);
            add(SILVER_PICKAXE);
            add(SILVER_AXE);
            add(SILVER_SHOVEL);
            add(SILVER_HOE);
            add(SILVER_INGOT);
            add(RAW_SILVER);
            add(SILVER_NUGGET);
            add(PELT_HELMET);
            add(PELT_CHESTPLATE);
            add(PELT_LEGGINGS);
            add(PELT_BOOTS);
            add(DARK_PELT_HELMET);
            add(DARK_PELT_CHESTPLATE);
            add(DARK_PELT_LEGGINGS);
            add(DARK_PELT_BOOTS);
            add(WHITE_PELT_HELMET);
            add(WHITE_PELT_CHESTPLATE);
            add(WHITE_PELT_LEGGINGS);
            add(WHITE_PELT_BOOTS);
            add(PELT);
            add(DARK_PELT);
            add(WHITE_PELT);
            add(WHITE_PELT_UPGRADE_SMITHING_TEMPLATE);
            add(CRACKED_BONE);
            add(WEREWOLF_TOOTH);
            add(LIVER);
            add(WOLF_BERRIES);
            addItemGen(BONE_NECKLACE);
            addItemGen(DREAM_CATCHER);
            addItemGen(CHARM_BRACELET);
            add(WEREWOLF_MINION_CHARM);
            add(WEREWOLF_MINION_UPGRADE_SIMPLE);
            add(WEREWOLF_MINION_UPGRADE_ENHANCED);
            add(WEREWOLF_MINION_UPGRADE_SPECIAL);
            add(INJECTION_UN_WEREWOLF);
            add(WOLFSBANE_FINDER);
        }
    }
}
