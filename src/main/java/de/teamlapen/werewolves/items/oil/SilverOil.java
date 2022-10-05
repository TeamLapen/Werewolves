package de.teamlapen.werewolves.items.oil;

import de.teamlapen.vampirism.api.items.oil.IWeaponOil;
import de.teamlapen.vampirism.items.oil.WeaponOil;
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

    public SilverOil(int color, int maxDuration) {
        super(color, maxDuration);
    }

    @Override
    public float onDamage(ItemStack stack, float amount, IWeaponOil oil, LivingEntity target, LivingEntity source) {
        return Helper.isWerewolf(target) ? amount * getDamageModifier():0;
    }

    @Override
    public boolean canBeApplied(ItemStack stack) {
        return super.canBeApplied(stack) && !(stack.getItem() instanceof ISilverItem || ModTags.Items.SILVER_TOOL.contains(stack.getItem()));
    }

    public float getDamageModifier() {
        return this == ModOils.SILVER_OIL_2.get() ? 0.25f : 0.125f;
    }

    @Override
    public int getMaxDuration(ItemStack stack) {
        return this == ModOils.SILVER_OIL_2.get() ? WerewolvesConfig.BALANCE.OILS.silverOil2Duration.get() : WerewolvesConfig.BALANCE.OILS.silverOil1Duration.get(); //TODO check
    }

    @Override
    public void getDescription(ItemStack stack, List<ITextComponent> tooltips) {
        super.getDescription(stack, tooltips);
        tooltips.add(new TranslationTextComponent("text.werewolves.oil.when_applied").withStyle(TextFormatting.GRAY));
        tooltips.add(new StringTextComponent(" ").append(new TranslationTextComponent("text.werewolves.oil.silver.more_damage", getDamageModifier() * 100).withStyle(TextFormatting.DARK_GREEN)));
    }
}
