package de.teamlapen.werewolves.items;

import de.teamlapen.werewolves.api.items.oil.IOil;
import de.teamlapen.werewolves.core.ModOils;
import de.teamlapen.werewolves.core.ModRegistries;
import de.teamlapen.werewolves.items.oil.WeaponOil;
import de.teamlapen.werewolves.util.OilUtils;
import de.teamlapen.werewolves.util.RegUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
        return OilUtils.setOil(super.getDefaultInstance(), ModOils.empty.get());
    }

    public ItemStack withOil(IOil oil) {
        return OilUtils.setOil(new ItemStack(this), oil);
    }

    @Nonnull
    @Override
    public Component getName(@Nonnull ItemStack stack) {
        ResourceLocation id = RegUtil.id(OilUtils.getOil(stack));
        return Component.translatable("oil." + id.getNamespace() + "." + id.getPath()).append(" ").append(Component.translatable(this.getDescriptionId(stack)));
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltips, @Nonnull TooltipFlag flag) {
        OilUtils.addOilTooltip(stack, tooltips);
    }

    @Override
    public void fillItemCategory(@Nonnull CreativeModeTab itemGroup, @Nonnull NonNullList<ItemStack> items) {
        if (this.allowedIn(itemGroup)) {
            for (IOil value : RegUtil.values(ModRegistries.OILS)) {
                if (value == ModOils.empty.get()) continue;
                items.add(withOil(value));
            }
        }
    }

    @Override
    public boolean isFoil(@Nonnull ItemStack stack) {
        return super.isFoil(stack) || (OilUtils.getOil(stack) instanceof WeaponOil);
    }
}
