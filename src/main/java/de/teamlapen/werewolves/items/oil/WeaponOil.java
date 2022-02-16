package de.teamlapen.werewolves.items.oil;

import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public class WeaponOil extends Oil {

    public WeaponOil(int color) {
        super(color);
    }

    @Override
    public boolean canBeAppliedTo(ItemStack stack) {
        return stack.getItem() instanceof SwordItem;
    }

    @Override
    public void getDescription(ItemStack stack, List<ITextComponent> tooltips) {
        tooltips.add(new TranslationTextComponent("oil." + this.getRegistryName().getNamespace() + "." + this.getRegistryName().getPath() + ".desc").append(" (" + getMaxDuration(stack) + ")").withStyle(TextFormatting.GOLD));
        tooltips.add(StringTextComponent.EMPTY);
        tooltips.add(new TranslationTextComponent("text.werewolves.oil.applied_to").withStyle(TextFormatting.GRAY));
        tooltips.add(new StringTextComponent(" ").append(new TranslationTextComponent("text.werewolves.swords").withStyle(TextFormatting.DARK_GREEN)));
    }
}
