package de.teamlapen.werewolves.items;

import de.teamlapen.werewolves.api.items.oil.IOil;
import de.teamlapen.werewolves.core.ModOils;
import de.teamlapen.werewolves.core.ModRegistries;
import de.teamlapen.werewolves.items.oil.WeaponOil;
import de.teamlapen.werewolves.util.OilUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class OilItem extends Item implements IOilItem {

    public OilItem(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public ItemStack getDefaultInstance() {
        return OilUtils.setOil(super.getDefaultInstance(), ModOils.empty);
    }

    @Nonnull
    @Override
    public Component getName(@Nonnull ItemStack stack) {
        IOil oil = OilUtils.getOil(stack);
        return new TranslatableComponent("oil." + oil.getRegistryName().getNamespace() + "." + oil.getRegistryName().getPath()).append(" ").append(new TranslatableComponent(this.getDescriptionId(stack)));
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltips, @Nonnull TooltipFlag flag) {
        OilUtils.addOilTooltip(stack, tooltips);
    }

    @Override
    public void fillItemCategory(@Nonnull CreativeModeTab itemGroup, @Nonnull NonNullList<ItemStack> items) {
        if (this.allowdedIn(itemGroup)) {
            for (IOil value : ModRegistries.WEAPON_OILS.getValues()) {
                if (value == ModOils.empty) continue;
                items.add(OilUtils.setOil(new ItemStack(this), value));
            }
        }
    }

    @Override
    public boolean isFoil(@Nonnull ItemStack stack) {
        return super.isFoil(stack) || (OilUtils.getOil(stack) instanceof WeaponOil);
    }
}
