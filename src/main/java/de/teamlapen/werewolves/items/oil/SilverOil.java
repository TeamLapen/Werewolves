package de.teamlapen.werewolves.items.oil;

import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModOils;
import de.teamlapen.werewolves.core.ModTags;
import de.teamlapen.werewolves.items.ISilverItem;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public class SilverOil extends WeaponOil {

    public SilverOil(int color) {
        super(color);
    }

    public boolean canEffect(ItemStack stack, LivingEntity entity) {
        return Helper.isWerewolf(entity);
    }

    public float getAdditionalDamage(ItemStack stack, LivingEntity entity, float damage) {
        return damage * getDamageModifier();
    }

    @Override
    public boolean canBeAppliedTo(ItemStack stack) {
        return super.canBeAppliedTo(stack) && !(stack.getItem() instanceof ISilverItem || ModTags.Items.SILVER_TOOL.contains(stack.getItem()));
    }

    public float getDamageModifier() {
        return this == ModOils.silver_oil_2.get() ? 0.2f : 0.125f;
    }

    @Override
    public int getMaxDuration(ItemStack stack) {
        return this == ModOils.silver_oil_2.get() ? WerewolvesConfig.BALANCE.OILS.silverOil2Duration.get() : WerewolvesConfig.BALANCE.OILS.silverOil1Duration.get();
    }

    @Override
    public void getDescription(ItemStack stack, List<ITextComponent> tooltips) {
        super.getDescription(stack, tooltips);
        tooltips.add(new TranslationTextComponent("text.werewolves.oil.when_applied").withStyle(TextFormatting.GRAY));
        tooltips.add(new StringTextComponent(" ").append(new TranslationTextComponent("text.werewolves.oil.silver.more_damage", getDamageModifier() * 100).withStyle(TextFormatting.DARK_GREEN)));
    }
}
