package de.teamlapen.werewolves.common.world.items;

import de.teamlapen.werewolves.common.core.ModBlocks;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class UnWerewolfInjectionItem extends Item {

    public UnWerewolfInjectionItem(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull Level worldIn, Player playerIn, @Nonnull InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        playerIn.sendOverlayMessage(Component.literal("Please use a ").append(Component.translatable(ModBlocks.V.MED_CHAIR.get().getDescriptionId())));
        return InteractionResult.PASS;
    }
}
