package de.teamlapen.werewolves.integration.vampirism.common.world.items;

import de.teamlapen.faction.api.factions.IFactionPlayerHandler;
import de.teamlapen.faction.api.factions.IPlayableFaction;
import de.teamlapen.vampirism.common.world.items.InjectionItem;
import de.teamlapen.werewolves.common.core.ModEffects;
import de.teamlapen.werewolves.common.world.effects.inst.UnWerewolfEffectInstance;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Cures the werewolf curse when used in Vampirism's injection chair
 */
public class UnWerewolfInjectionItem extends InjectionItem {

    public UnWerewolfInjectionItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean handleInjection(Level level, BlockPos pos, Player player, IFactionPlayerHandler handler, Holder<? extends IPlayableFaction<?>> currentFaction) {
        if (!Helper.isWerewolf(player)) {
            if (!level.isClientSide()) {
                player.sendOverlayMessage(Component.translatable("text.werewolves.injection.not_use"));
            }
            return false;
        }
        if (player.getEffect(ModEffects.UN_WEREWOLF) != null) {
            if (!level.isClientSide()) {
                player.sendOverlayMessage(Component.translatable("text.werewolves.injection.in_use"));
            }
            return false;
        }
        if (!level.isClientSide()) {
            player.addEffect(new UnWerewolfEffectInstance(2000));
        }
        return true;
    }
}
