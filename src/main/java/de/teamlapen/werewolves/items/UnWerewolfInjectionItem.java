package de.teamlapen.werewolves.items;

import de.teamlapen.vampirism.core.ModBlocks;
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
        super(new Item.Properties().group(WUtils.creativeTab));
        this.setRegistryName(REFERENCE.MODID, "injection_un_werewolf");
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, PlayerEntity playerIn, @Nonnull Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        playerIn.sendStatusMessage(new StringTextComponent("Please use a ").append(new TranslationTextComponent(ModBlocks.med_chair.getTranslationKey())), true);
        return ActionResult.resultPass(stack);
    }
}
