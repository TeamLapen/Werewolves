package de.teamlapen.werewolves.common.world.items;

import de.teamlapen.faction.api.factions.IFaction;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.common.core.ModFactions;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class WerewolfMinionUpgradeItem extends Item {
    private final int minLevel;
    private final int maxLevel;

    public WerewolfMinionUpgradeItem(Item.Properties properties, int minLevel, int maxLevel) {
        super(properties);
        this.maxLevel = maxLevel;
        this.minLevel = minLevel;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltip, flagIn);
        tooltip.accept(Component.translatable("item.werewolves.moon_charm.desc").withStyle(ChatFormatting.DARK_GRAY));
        tooltip.accept(Component.empty());
        tooltip.accept(Component.translatable("item.vampirism.minion_upgrade_item.desc").withStyle(ChatFormatting.GRAY));
        tooltip.accept(Component.translatable("text.vampirism.for_to_levels", minLevel + 1, maxLevel + 1).withStyle(ChatFormatting.GRAY));
    }

    public Holder<? extends IFaction<?>> getFaction() {
        return ModFactions.WEREWOLF;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getMinLevel() {
        return minLevel;
    }
}
