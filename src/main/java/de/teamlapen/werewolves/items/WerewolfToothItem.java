package de.teamlapen.werewolves.items;

import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModEffects;
import de.teamlapen.werewolves.effects.LupusSanguinemEffect;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class WerewolfToothItem extends Item {

    public WerewolfToothItem() {
        super(new Item.Properties().tab(WUtils.creativeTab));
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level world, @Nonnull Player player, @Nonnull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide) {
            if (WerewolvesConfig.SERVER.disableToothInfection.get()) {
                player.displayClientMessage(Component.translatable("text.vampirism.deactivated_by_serveradmin"), true);
            } else {
                if (Helper.canBecomeWerewolf(player)) {
                    LupusSanguinemEffect.addSanguinemEffect(player);
                    player.addEffect(new MobEffectInstance(ModEffects.V.POISON.get(), 60));
                } else {
                    if (Helper.isWerewolf(player)) {
                        player.displayClientMessage(Component.translatable("text.werewolves.already_werewolf"), true);
                    } else {
                        player.displayClientMessage(Component.translatable("text.vampirism.immune_to").append(Component.translatable(ModEffects.LUPUS_SANGUINEM.get().getDescriptionId())), true);
                    }
                }
                stack.shrink(1);
            }
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }
}
