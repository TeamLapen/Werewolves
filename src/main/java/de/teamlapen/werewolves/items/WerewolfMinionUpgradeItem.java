package de.teamlapen.werewolves.items;

import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.werewolves.api.WReference;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * copy of {@link de.teamlapen.vampirism.items.MinionUpgradeItem} because the registryname is hardcoded
 */
public class WerewolfMinionUpgradeItem extends Item {
    private final int minLevel;
    private final int maxLevel;

    public WerewolfMinionUpgradeItem(Item.@NotNull Properties properties, int minLevel, int maxLevel) {
        super(properties);
        this.maxLevel = maxLevel;
        this.minLevel = minLevel;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable("item.werewolves.moon_charm.desc").withStyle(ChatFormatting.DARK_GRAY));
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("item.vampirism.minion_upgrade_item.desc").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("text.vampirism.for_to_levels", minLevel + 1, maxLevel + 1).withStyle(ChatFormatting.GRAY));
    }

    public IFaction<?> getFaction() {
        return WReference.WEREWOLF_FACTION;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getMinLevel() {
        return minLevel;
    }
}
