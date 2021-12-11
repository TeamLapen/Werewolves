package de.teamlapen.werewolves.items;

import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModEffects;
import de.teamlapen.werewolves.effects.LupusSanguinemEffect;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class WerewolfTooth extends Item {

    public WerewolfTooth() {
        super(new Item.Properties().tab(WUtils.creativeTab));
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(@Nonnull World world, @Nonnull PlayerEntity player, @Nonnull Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide) {
            if (WerewolvesConfig.SERVER.disableToothInfection.get()) {
                player.displayClientMessage(new TranslationTextComponent("text.vampirism.deactivated_by_serveradmin"), true);
            } else {
                if (Helper.canBecomeWerewolf(player)) {
                    LupusSanguinemEffect.addSanguinemEffect(player);
                    player.addEffect(new EffectInstance(ModEffects.V.poison, 60));
                } else {
                    if (Helper.isWerewolf(player)) {
                        player.displayClientMessage(new TranslationTextComponent("text.werewolves.already_werewolf"), true);
                    } else {
                        player.displayClientMessage(new TranslationTextComponent("text.vampirism.immune_to").append(new TranslationTextComponent(ModEffects.lupus_sanguinem.getDescriptionId())), true);
                    }
                }
                stack.shrink(1);
            }
        }
        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }
}
