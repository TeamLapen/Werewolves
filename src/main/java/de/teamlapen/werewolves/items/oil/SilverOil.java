package de.teamlapen.werewolves.items.oil;

import de.teamlapen.werewolves.api.items.ISilverItem;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModOils;
import de.teamlapen.werewolves.core.ModTags;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

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
        return super.canBeAppliedTo(stack) && !(stack.getItem() instanceof ISilverItem || stack.is(ModTags.Items.SILVER_TOOL));
    }

    public float getDamageModifier() {
        return this == ModOils.SILVER_OIL_2.get() ? 0.2f : 0.125f;
    }

    @Override
    public int getMaxDuration(ItemStack stack) {
        return this == ModOils.SILVER_OIL_2.get() ? WerewolvesConfig.BALANCE.OILS.silverOil2Duration.get() : WerewolvesConfig.BALANCE.OILS.silverOil1Duration.get();
    }

    @Override
    public void getDescription(ItemStack stack, List<Component> tooltips) {
        super.getDescription(stack, tooltips);
        tooltips.add(Component.translatable("text.werewolves.oil.when_applied").withStyle(ChatFormatting.GRAY));
        tooltips.add(Component.literal(" ").append(Component.translatable("text.werewolves.oil.silver.more_damage", getDamageModifier() * 100).withStyle(ChatFormatting.DARK_GREEN)));
    }
}
