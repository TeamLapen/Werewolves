package de.teamlapen.werewolves.items;

import de.teamlapen.werewolves.core.ModOils;
import de.teamlapen.werewolves.core.ModRegistries;
import de.teamlapen.werewolves.items.oil.IOil;
import de.teamlapen.werewolves.items.oil.WeaponOil;
import de.teamlapen.werewolves.util.OilUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class OilItem extends Item {

    public OilItem(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public ItemStack getDefaultInstance() {
        return OilUtils.setOil(super.getDefaultInstance(), ModOils.plant_oil);
    }

    @Nonnull
    @Override
    public String getDescriptionId(@Nonnull ItemStack stack) {
        return OilUtils.getOilOpt(stack).map(oil -> oil.getName(this.getDescriptionId() + ".oil.")).orElseGet(this::getDescriptionId);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World level, @Nonnull List<ITextComponent> tooltips, @Nonnull ITooltipFlag flag) {
        OilUtils.addOilTooltip(stack, tooltips);
    }

    @Override
    public void fillItemCategory(@Nonnull ItemGroup itemGroup, @Nonnull NonNullList<ItemStack> items) {
        if (this.allowdedIn(itemGroup)) {
            for (IOil value : ModRegistries.WEAPON_OILS.getValues()) {
                if (value != ModOils.empty) {
                    items.add(OilUtils.setOil(new ItemStack(this), value));
                }
            }
        }
    }

    @Override
    public boolean isFoil(@Nonnull ItemStack stack) {
        return super.isFoil(stack) || (OilUtils.getOil(stack) instanceof WeaponOil);
    }
}
