package de.teamlapen.werewolves.common.world.items.oil;

import de.teamlapen.vampirism.api.world.items.oil.IWeaponOil;
import de.teamlapen.vampirism.common.world.items.oil.EffectWeaponOil;
import de.teamlapen.vampirism.common.world.items.oil.WeaponOil;
import de.teamlapen.werewolves.api.items.ISilverItem;
import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.core.ModOils;
import de.teamlapen.werewolves.common.core.ModTags;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class SilverOil extends WeaponOil {

    public SilverOil(int color, int maxDuration) {
        super(color, maxDuration);
    }

    @Override
    public float onDamage(ItemStack stack, float amount, IWeaponOil oil, LivingEntity target, LivingEntity source) {
        return Helper.isWerewolf(target) ? amount * getDamageModifier():0;
    }

    @Override
    public boolean canBeApplied(ItemInstance stack) {
        return super.canBeApplied(stack) && !(stack.is(ModTags.Items.SILVER_TOOL));
    }

    public float getDamageModifier() {
        return this == ModOils.SILVER_OIL_2.get() ? 0.25f : 0.125f;
    }

    @Override
    public int getMaxDuration(ItemStack stack) {
        int duration = super.getMaxDuration(stack);
        if (this == ModOils.SILVER_OIL_2.get()) {
            duration *= 2;
        }
        return duration;
    }

    @Override
    public Component getDescriptionTitle() {
        return Component.translatable("tooltip.vampirism.oil.weapon_effect_on_hit");
    }

    @Override
    public List<Component> getEffectDescription(Item.@Nullable TooltipContext context) {
        return List.of(
                Component.translatable("text.werewolves.oil.when_applied").withStyle(ChatFormatting.GRAY),
                Component.literal("  ").append(Component.translatable("text.werewolves.oil.silver.more_damage", getDamageModifier() * 100).withStyle(ChatFormatting.DARK_GREEN))
        );
    }

}
