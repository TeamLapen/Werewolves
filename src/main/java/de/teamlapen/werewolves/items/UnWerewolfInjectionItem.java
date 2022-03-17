package de.teamlapen.werewolves.items;

import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class UnWerewolfInjectionItem extends Item {

    public UnWerewolfInjectionItem() {
        super(new Item.Properties().tab(WUtils.creativeTab));
        this.setRegistryName(REFERENCE.MODID, "injection_un_werewolf");
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level worldIn, Player playerIn, @Nonnull InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        playerIn.displayClientMessage(new TextComponent("Please use a ").append(new TranslatableComponent(ModBlocks.med_chair.getDescriptionId())), true);
        return InteractionResultHolder.pass(stack);
    }
}
