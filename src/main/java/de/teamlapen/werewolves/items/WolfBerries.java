package de.teamlapen.werewolves.items;

import de.teamlapen.werewolves.util.Helper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class WolfBerries extends ItemNameBlockItem {

    public WolfBerries(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pLivingEntity) {
        var stack = super.finishUsingItem(pStack, pLevel, pLivingEntity);
        if (!Helper.isWerewolf(pLivingEntity)) {
            pLivingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 0));
        }
        return stack;
    }
}
