package de.teamlapen.werewolves.items;

import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.WReference;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class LiverItem extends Item {
    public LiverItem() {
        super(new Item.Properties().group(WUtils.creativeTab).food(new Food.Builder().meat().hunger(10).saturation(1.5f).build()));
    }

    @Nonnull
    @Override
    public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull LivingEntity entityLiving) {
        if (entityLiving instanceof PlayerEntity && Helper.isWerewolf((PlayerEntity) entityLiving)) {
            FactionPlayerHandler.getOpt((PlayerEntity) entityLiving).ifPresent(player -> {
                if (player.getCurrentLevel() > 0 && player.getCurrentLevel() < 5) {
                    player.setFactionLevel(WReference.WEREWOLF_FACTION, player.getCurrentLevel() + 1);
                }
            });
        }
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
}
