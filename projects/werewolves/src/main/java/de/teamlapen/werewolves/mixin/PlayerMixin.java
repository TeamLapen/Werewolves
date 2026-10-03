package de.teamlapen.werewolves.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import de.teamlapen.werewolves.core.ModAttributes;
import de.teamlapen.werewolves.core.ModSkills;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @WrapOperation(method = "eat", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;eat(Lnet/minecraft/world/food/FoodProperties;)V"))
    private void eatWerewolf(FoodData instance, FoodProperties pFoodProperties, Operation<Void> original, Level level, ItemStack stack) {
        Player player = (Player) (Object) this;
        if (Helper.isWerewolf(player)) {
            double foodConsumption = player.getAttributeValue(ModAttributes.FOOD_GAIN);
            pFoodProperties = new FoodProperties((int) (pFoodProperties.nutrition() * foodConsumption), (float) (pFoodProperties.saturation() * foodConsumption), pFoodProperties.canAlwaysEat(), pFoodProperties.eatSeconds(), pFoodProperties.usingConvertsTo(), pFoodProperties.effects());
            WerewolfPlayer werewolf = WerewolfPlayer.get(player);
            if (Helper.isMeat(player, stack)) {
                if (Helper.isRawMeatSkipMeat(stack)) {
                    pFoodProperties = new FoodProperties(pFoodProperties.nutrition() * 2, pFoodProperties.saturation() * 2, pFoodProperties.canAlwaysEat(), pFoodProperties.eatSeconds(), pFoodProperties.usingConvertsTo(), pFoodProperties.effects());
                }
            } else if (!werewolf.getSkillHandler().isSkillEnabled(ModSkills.NOT_MEAT)) {
                player.displayClientMessage(Component.translatable("text.werewolves.taste_not_right"), true);
                pFoodProperties = new FoodProperties(0, 0, pFoodProperties.canAlwaysEat(), pFoodProperties.eatSeconds(), pFoodProperties.usingConvertsTo(), List.of());
            }
        }
        original.call(instance, pFoodProperties);
    }
}
