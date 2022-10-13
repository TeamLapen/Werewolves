package de.teamlapen.werewolves.util;

import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModTags;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.loot.LootTableManager;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.DamageSource;

import javax.annotation.Nonnull;

public class WUtils {
    public static final BooleanProperty SOUL_FIRE = BooleanProperty.create("soulfire");
    public static final ScoreCriteria WEREWOLF_LEVEL_CRITERIA = new ScoreCriteria("werewolves:werewolf");
    public static final ItemGroup creativeTab = new ItemGroup(REFERENCE.MODID) {
        @Nonnull
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.LIVER.get());
        }
    };
    public static final IItemTier SILVER_ITEM_TIER = new IItemTier() {
        @Override
        public int getUses() {
            return 250;
        }

        @Override
        public float getSpeed() {
            return 8.0f;
        }

        @Override
        public float getAttackDamageBonus() {
            return 1.5f;
        }

        @Override
        public int getLevel() {
            return 2;
        }

        @Override
        public int getEnchantmentValue() {
            return 12;
        }

        @Nonnull
        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(ModTags.Items.SILVER_INGOT);
        }
    };
    public static LootTableManager LOOT_TABLE_MANAGER;
    public static DamageSource OPEN_WOUND_DAMAGE_SOURCE = new DamageSource("blood_loss").bypassArmor().bypassMagic();

    public static void init() {
    }
}
