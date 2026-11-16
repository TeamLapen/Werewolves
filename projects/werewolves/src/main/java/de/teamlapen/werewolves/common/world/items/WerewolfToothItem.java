package de.teamlapen.werewolves.common.world.items;

import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.core.ModEffects;
import de.teamlapen.werewolves.common.world.effects.LupusSanguinemEffect;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class WerewolfToothItem extends Item {

    public WerewolfToothItem(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull Level world, @Nonnull Player player, @Nonnull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide()) {
            if (ModConfig.server().disableWerewolfToothInfection.get()) {
                player.sendOverlayMessage(Component.translatable("text.vampirism.deactivated_by_serveradmin"));
            } else {
                if (Helper.canBecomeWerewolf(player)) {
                    LupusSanguinemEffect.addSanguinemEffect(player);
                    player.addEffect(new MobEffectInstance(ModEffects.V.POISON, 60));
                } else {
                    if (Helper.isWerewolf(player)) {
                        player.sendOverlayMessage(Component.translatable("text.werewolves.already_werewolf"));
                    } else {
                        player.sendOverlayMessage(Component.translatable("text.vampirism.immune_to").append(Component.translatable(ModEffects.LUPUS_SANGUINEM.get().getDescriptionId())));
                    }
                }
                stack.shrink(1);
            }
        }
        return InteractionResult.SUCCESS;
    }
}
