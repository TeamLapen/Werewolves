package de.teamlapen.werewolves.items;

import de.teamlapen.vampirism.api.entity.vampire.IVampire;
import de.teamlapen.vampirism.entity.player.vampire.VampirePlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class LiverItem extends Item {
    public LiverItem() {
        super(new Item.Properties().food(new FoodProperties.Builder().meat().nutrition(6).saturationMod(1.5f).build()));
    }

    @Nonnull
    @Override
    public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level worldIn, @Nonnull LivingEntity entityLiving) {
        //copied from VampirismItemBloodFood
        assert stack.getItem().getFoodProperties() != null;
        if (entityLiving instanceof Player player) {
            VampirePlayer.getOpt(player).ifPresent((v) -> {
                v.drinkBlood(stack.getItem().getFoodProperties().getNutrition(), stack.getItem().getFoodProperties().getSaturationModifier());
            });
            worldIn.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5F, worldIn.random.nextFloat() * 0.1F + 0.9F);
            entityLiving.eat(worldIn, stack);
        } else if (entityLiving instanceof IVampire) {
            ((IVampire) entityLiving).drinkBlood(stack.getItem().getFoodProperties().getNutrition(), stack.getItem().getFoodProperties().getSaturationModifier());
            stack.shrink(1);
        } else {
            entityLiving.eat(worldIn, stack); //Shrinks stack and applies human food effects
        }

        return stack;
    }
}
