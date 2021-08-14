package de.teamlapen.werewolves.items;

import de.teamlapen.vampirism.player.vampire.VampirePlayer;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class LiverItem extends Item {
    public LiverItem() {
        super(new Item.Properties().tab(WUtils.creativeTab).food(new Food.Builder().meat().nutrition(10).saturationMod(1.5f).build()));
    }

    @Nonnull
    @Override
    public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull LivingEntity entityLiving) {
        //copied from VampirismItemBloodFood
        if (entityLiving instanceof PlayerEntity) {
            assert stack.getItem().getFoodProperties() != null;

            PlayerEntity player = (PlayerEntity) entityLiving;
            VampirePlayer.getOpt(player).ifPresent((v) -> {
                v.drinkBlood(stack.getItem().getFoodProperties().getNutrition(), stack.getItem().getFoodProperties().getSaturationModifier());
            });
            worldIn.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.random.nextFloat() * 0.1F + 0.9F);
            entityLiving.eat(worldIn, stack);
        }

        return stack;
    }
}
