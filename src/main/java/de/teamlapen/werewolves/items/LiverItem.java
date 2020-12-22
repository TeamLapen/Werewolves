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
        super(new Item.Properties().group(WUtils.creativeTab).food(new Food.Builder().meat().hunger(10).saturation(1.5f).build()));
    }

    @Nonnull
    @Override
    public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull LivingEntity entityLiving) {
        //copied from VampirismItemBloodFood
        if (entityLiving instanceof PlayerEntity) {
            assert stack.getItem().getFood() != null;

            PlayerEntity player = (PlayerEntity) entityLiving;
            VampirePlayer.getOpt(player).ifPresent((v) -> {
                v.drinkBlood(stack.getItem().getFood().getHealing(), stack.getItem().getFood().getSaturation());
            });
            worldIn.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
            entityLiving.onFoodEaten(worldIn, stack);
        }

        return stack;
    }
}
