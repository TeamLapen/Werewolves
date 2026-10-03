package de.teamlapen.werewolves.items.oil;

import de.teamlapen.vampirism.api.items.oil.IWeaponOil;
import de.teamlapen.vampirism.items.oil.WeaponOil;
import de.teamlapen.werewolves.api.items.ISilverItem;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModOils;
import de.teamlapen.werewolves.core.ModTags;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    public boolean canBeApplied(@NotNull ItemStack stack) {
        return super.canBeApplied(stack) && !(stack.getItem() instanceof ISilverItem || stack.is(ModTags.Items.SILVER_TOOL));
    }

    public float getDamageModifier() {
        return this == ModOils.SILVER_OIL_2.get() ? 0.25f : 0.125f;
    }

    @Override
    public int getMaxDuration(ItemStack stack) {
        return this == ModOils.SILVER_OIL_2.get() ? WerewolvesConfig.BALANCE.OILS.silverOil2Duration.get() : WerewolvesConfig.BALANCE.OILS.silverOil1Duration.get(); //TODO check
    }

    @Override
    public void getDescription(ItemStack stack, @Nullable Item.@Nullable TooltipContext context, List<Component> tooltips) {
        super.getDescription(stack, context, tooltips);
        tooltips.add(Component.translatable("text.werewolves.oil.when_applied").withStyle(ChatFormatting.GRAY));
        tooltips.add(Component.literal("  ").append(Component.translatable("text.werewolves.oil.silver.more_damage", getDamageModifier() * 100).withStyle(ChatFormatting.DARK_GREEN)));
    }
}
