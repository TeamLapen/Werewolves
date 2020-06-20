package de.teamlapen.werewolves.util;

import de.teamlapen.vampirism.core.ModItems;
import de.teamlapen.werewolves.core.ModTags;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.scoreboard.ScoreCriteria;

import java.util.UUID;

public class WUtils {
    public static final UUID WEREWOLF_FORM_TIME = UUID.fromString("4af447d7-5aca-4ab1-b58f-a4a5f6e15746");

    public static final ScoreCriteria WEREWOLF_LEVEL_CRITERIA = new ScoreCriteria("werewolves:werewolf");
    public static final ItemGroup creativeTab = new ItemGroup(REFERENCE.MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.item_garlic);//TODO change
        }
    };
    public static final IItemTier SILVER_ITEM_TIER = new IItemTier() {
        @Override
        public int getMaxUses() {
            return 250;
        }

        @Override
        public float getEfficiency() {
            return 6.0f;
        }

        @Override
        public float getAttackDamage() {
            return 2.0f;
        }

        @Override
        public int getHarvestLevel() {
            return 2;
        }

        @Override
        public int getEnchantability() {
            return 14;
        }

        @Override
        public Ingredient getRepairMaterial() {
            return Ingredient.fromTag(ModTags.Items.SILVER_INGOT);
        }
    };

    public static void init() {
    }
}
