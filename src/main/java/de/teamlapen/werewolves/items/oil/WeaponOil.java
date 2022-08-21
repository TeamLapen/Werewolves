package de.teamlapen.werewolves.items.oil;

import de.teamlapen.werewolves.util.RegUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
        ResourceLocation id = RegUtil.id(this);
        tooltips.add(Component.translatable("oil." + id.getNamespace() + "." + id.getPath() + ".desc").append(" (" + getMaxDuration(stack) + ")").withStyle(ChatFormatting.GOLD));
        tooltips.add(Component.empty());
        tooltips.add(Component.translatable("text.werewolves.oil.applied_to").withStyle(ChatFormatting.GRAY));
        tooltips.add(Component.literal(" ").append(Component.translatable("text.werewolves.swords").withStyle(ChatFormatting.DARK_GREEN)));
    }
}
