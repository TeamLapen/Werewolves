package de.teamlapen.werewolves.items;

import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * copy of {@link de.teamlapen.vampirism.items.MinionUpgradeItem} because the registryname is hardcoded
 */
public class WerewolfMinionUpgradeItem extends Item {
    private final int minLevel;
    private final int maxLevel;

    public WerewolfMinionUpgradeItem(Item.Properties properties, int minLevel, int maxLevel) {
        super(properties);
        this.maxLevel = maxLevel;
        this.minLevel = minLevel;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("item.vampirism.minion_upgrade_item.desc").withStyle(TextFormatting.GRAY));
        tooltip.add(new TranslationTextComponent("text.vampirism.for_to_levels", minLevel + 1, maxLevel + 1).withStyle(TextFormatting.GRAY));
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
