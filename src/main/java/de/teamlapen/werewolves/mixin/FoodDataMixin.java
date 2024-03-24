package de.teamlapen.werewolves.mixin;

import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.core.ModSkills;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(FoodData.class)
public class FoodDataMixin {

    @ModifyVariable(method = "eat(Lnet/minecraft/world/item/Item;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)V", at = @At("STORE"), ordinal = 0, remap = false)
    private FoodProperties eat(FoodProperties value, Item pItem, ItemStack pStack, @org.jetbrains.annotations.Nullable net.minecraft.world.entity.LivingEntity entity) {
        if (entity instanceof Player player && Helper.isWerewolf(player)) {
            if (Helper.isMeat(player, pStack)) {
                if (Helper.isRawMeatSkipMeat(pStack)) {
                    return werewolves$builder(value).saturationMod(value.getSaturationModifier() * 2).nutrition(value.getNutrition() * 2).build();
                }
            } else if (!WerewolfPlayer.get(player).getSkillHandler().isSkillEnabled(ModSkills.NOT_MEAT.get())) {
                player.displayClientMessage(Component.translatable("text.werewolves.taste_not_right"), true);
                return werewolves$builder(value).saturationMod(0).nutrition(0).build();
            }
        }

        return value;
    }

    @Unique
    private FoodProperties.Builder werewolves$builder(FoodProperties properties) {
        var builder = new FoodProperties.Builder()
                .nutrition(properties.getNutrition())
                .saturationMod(properties.getSaturationModifier());
        if (properties.isMeat()) {
            builder.meat();
        }
        if (properties.isFastFood()) {
            builder.fast();
        }
        if (properties.canAlwaysEat()) {
            builder.alwaysEat();
        }
        properties.getEffects().forEach(effect -> builder.effect(effect::getFirst, effect.getSecond()));
        return builder;
    }
}
