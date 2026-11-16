package de.teamlapen.werewolves.mixin;

import net.minecraft.world.item.component.Consumable;

import net.minecraft.world.entity.LivingEntity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import de.teamlapen.werewolves.common.core.ModAttributes;
import de.teamlapen.werewolves.common.core.ModSkills;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;


/**
 * Since 1.21.2 food is applied through {@link FoodProperties#onConsume} instead of {@code Player#eat}
 */
@Mixin(FoodProperties.class)
public abstract class FoodPropertiesMixin {

    @WrapOperation(method = "onConsume", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;eat(Lnet/minecraft/world/food/FoodProperties;)V"))
    private void eatWerewolf(FoodData instance, FoodProperties pFoodProperties, Operation<Void> original, Level level, LivingEntity user, ItemStack stack, Consumable consumable) {
        if (user instanceof Player player && Helper.isWerewolf(player)) {
            double foodConsumption = player.getAttributeValue(ModAttributes.FOOD_GAIN);
            pFoodProperties = new FoodProperties((int) (pFoodProperties.nutrition() * foodConsumption), (float) (pFoodProperties.saturation() * foodConsumption), pFoodProperties.canAlwaysEat());
            WerewolfPlayer werewolf = WerewolfPlayer.get(player);
            if (Helper.isMeat(player, stack)) {
                if (Helper.isRawMeatSkipMeat(stack)) {
                    pFoodProperties = new FoodProperties(pFoodProperties.nutrition() * 2, pFoodProperties.saturation() * 2, pFoodProperties.canAlwaysEat());
                }
            } else if (!werewolf.getSkillHandler().isSkillEnabled(ModSkills.NOT_MEAT)) {
                player.sendOverlayMessage(Component.translatable("text.werewolves.taste_not_right"));
                pFoodProperties = new FoodProperties(0, 0, pFoodProperties.canAlwaysEat());
            }
        }
        original.call(instance, pFoodProperties);
    }
}
