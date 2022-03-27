package de.teamlapen.werewolves.items.oil;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

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
    public void getDescription(ItemStack stack, List<Component> tooltips) {
        tooltips.add(new TranslatableComponent("oil." + this.getRegistryName().getNamespace() + "." + this.getRegistryName().getPath() + ".desc").append(" (" + getMaxDuration(stack) + ")").withStyle(ChatFormatting.GOLD));
        tooltips.add(TextComponent.EMPTY);
        tooltips.add(new TranslatableComponent("text.werewolves.oil.applied_to").withStyle(ChatFormatting.GRAY));
        tooltips.add(new TextComponent(" ").append(new TranslatableComponent("text.werewolves.swords").withStyle(ChatFormatting.DARK_GREEN)));
    }
}
