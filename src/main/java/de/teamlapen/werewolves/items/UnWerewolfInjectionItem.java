package de.teamlapen.werewolves.items;

import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class UnWerewolfInjectionItem extends Item {

    public UnWerewolfInjectionItem() {
        super(new Item.Properties().tab(WUtils.creativeTab));
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(@Nonnull World worldIn, PlayerEntity playerIn, @Nonnull Hand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        playerIn.displayClientMessage(new StringTextComponent("Please use a ").append(new TranslationTextComponent(ModBlocks.med_chair.get().getDescriptionId())), true);
        return ActionResult.pass(stack);
    }
}
