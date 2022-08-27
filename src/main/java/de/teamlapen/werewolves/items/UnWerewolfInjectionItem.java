package de.teamlapen.werewolves.items;

import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class UnWerewolfInjectionItem extends Item {

    public UnWerewolfInjectionItem() {
        super(new Item.Properties().tab(WUtils.creativeTab));
    }

    @NotNull
    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level worldIn, @NotNull Player playerIn, @NotNull InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        playerIn.displayClientMessage(Component.literal("Please use a ").append(Component.translatable(ModBlocks.V.MED_CHAIR.get().getDescriptionId())), true);
        return InteractionResultHolder.pass(stack);
    }
}
