package de.teamlapen.werewolves.common.world.items;

import de.teamlapen.werewolves.util.Helper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class WolfBerries extends BlockItem {

    public WolfBerries(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        var stack = super.finishUsingItem(pStack, pLevel, pLivingEntity);
        if (!Helper.isWerewolf(pLivingEntity)) {
            pLivingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 0));
        }
        return stack;
    }
}
