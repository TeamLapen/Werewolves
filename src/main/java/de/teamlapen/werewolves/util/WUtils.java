package de.teamlapen.werewolves.util;

import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModTags;
import de.teamlapen.werewolves.mixin.ObjectiveCriteriaAccessor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

import javax.annotation.Nonnull;

public class WUtils {
    public static final BooleanProperty SOUL_FIRE = BooleanProperty.create("soulfire");
    public static final ObjectiveCriteria WEREWOLF_LEVEL_CRITERIA = ObjectiveCriteriaAccessor.registerCustom("werewolves:werewolf");
    public static final CreativeModeTab creativeTab = new CreativeModeTab(REFERENCE.MODID) {
        @Nonnull
        @Override
        public ItemStack makeIcon() {
            return ModItems.liver.get().getDefaultInstance();
        }
    };
    public static final Tier SILVER_ITEM_TIER = new Tier() {
        @Override
        public int getUses() {
            return 250;
        }

        @Override
        public float getSpeed() {
            return 6.0f;
        }

        @Override
        public float getAttackDamageBonus() {
            return 2.0f;
        }

        @Override
        public int getLevel() {
            return 2;
        }

        @Override
        public int getEnchantmentValue() {
            return 14;
        }

        @Nonnull
        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(ModTags.Items.SILVER_INGOT);
        }
    };
    public static LootTables LOOT_TABLE_MANAGER;
    public static final DamageSource OPEN_WOUND_DAMAGE_SOURCE = new DamageSource("blood_loss").bypassArmor().bypassMagic();

    @SuppressWarnings("EmptyMethod")
    public static void init() {
    }
}
