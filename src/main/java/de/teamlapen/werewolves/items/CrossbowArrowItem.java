package de.teamlapen.werewolves.items;

import de.teamlapen.vampirism.api.items.IEntityCrossbowArrow;
import de.teamlapen.vampirism.api.items.IVampirismCrossbowArrow;
import de.teamlapen.vampirism.config.VampirismConfig;
import de.teamlapen.vampirism.entity.CrossbowArrowEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class CrossbowArrowItem extends Item implements IVampirismCrossbowArrow<CrossbowArrowEntity> {

    private final ArrowType type;

    public CrossbowArrowItem(ArrowType type) {
        super(new Item.Properties().group(WUtils.creativeTab));
        this.type = type;
        this.setRegistryName(REFERENCE.MODID, "crossbow_arrow_" + type.name);
    }

    @Override
    public CrossbowArrowEntity createEntity(ItemStack stack, World world, PlayerEntity player, double heightOffset, double centerOffset, boolean rightHand) {
        CrossbowArrowEntity entity = CrossbowArrowEntity.createWithShooter(world, player, heightOffset, centerOffset, rightHand, stack);
        entity.setDamage(this.type.baseDamage * VampirismConfig.BALANCE.crossbowDamageMult.get());
        return entity;
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        if (this.type.hasToolTip) {
            tooltip.add(new TranslationTextComponent(this.getTranslationKey() + ".tooltip").mergeStyle(TextFormatting.GRAY));
        }
    }

    public ArrowType getType() {
        return type;
    }

    @Override
    public boolean isCanBeInfinite() {
        return type.canBeInfinit;
    }

    @Override
    public void onHitBlock(ItemStack arrow, BlockPos blockPos, IEntityCrossbowArrow arrowEntity, Entity shootingEntity) {
        this.type.onHitBlock(arrow, blockPos, arrowEntity, shootingEntity);
    }

    @Override
    public void onHitEntity(ItemStack arrow, LivingEntity entity, IEntityCrossbowArrow arrowEntity, Entity shootingEntity) {
        this.type.onHitEntity(arrow, entity, arrowEntity, shootingEntity);
    }

    public static class ArrowType {
        public final String name;
        public final int baseDamage;
        public final int color;
        public final boolean canBeInfinit;
        public final boolean hasToolTip;

        public ArrowType(String name, int baseDamage, int color, boolean canBeInfinit, boolean hasToolTip) {
            this.name = name;
            this.baseDamage = baseDamage;
            this.color = color;
            this.canBeInfinit = canBeInfinit;
            this.hasToolTip = hasToolTip;
        }

        private void onHitBlock(ItemStack arrow, BlockPos blockPos, IEntityCrossbowArrow arrowEntity, Entity shootingEntity) {

        }

        public void onHitEntity(ItemStack arrow, LivingEntity entity, IEntityCrossbowArrow arrowEntity, Entity shootingEntity) {

        }

    }

}
