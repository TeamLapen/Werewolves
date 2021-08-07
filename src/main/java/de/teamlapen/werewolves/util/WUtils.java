package de.teamlapen.werewolves.util;

import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModTags;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.scoreboard.ScoreCriteria;

import javax.annotation.Nonnull;

public class WUtils {
    public static final ScoreCriteria WEREWOLF_LEVEL_CRITERIA = new ScoreCriteria("werewolves:werewolf");
    public static final ItemGroup creativeTab = new ItemGroup(REFERENCE.MODID) {
        @Nonnull
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.liver);
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

        @Nonnull
        @Override
        public Ingredient getRepairMaterial() {
            return Ingredient.fromTag(ModTags.Items.SILVER_INGOT);
        }
    };

    public static void init() {
    }
}
