package de.teamlapen.werewolves.items;

import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.vampirism.api.items.IEntityCrossbowArrow;
import de.teamlapen.vampirism.api.items.IVampirismCrossbowArrow;
import de.teamlapen.vampirism.config.Balance;
import de.teamlapen.vampirism.entity.EntityCrossbowArrow;
import de.teamlapen.werewolves.api.entities.werewolf.IWerewolfMob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemCrossbowArrow extends ItemWerewolfBase implements IVampirismCrossbowArrow<EntityCrossbowArrow> {
    private static final String regName = "crossbow_arrow";

    /**
     * @return The {@link EnumArrowType} of this stack
     */
    public static @Nonnull EnumArrowType getType(@Nonnull ItemStack stack) {
        if (stack.hasTagCompound()) {
            if (stack.getTagCompound().hasKey("type")) {
                String type = stack.getTagCompound().getString("type");
                for (EnumArrowType enumType : EnumArrowType.values()) {
                    if (enumType.name.equals(type)) {
                        return enumType;
                    }
                }
            }
        }
        return EnumArrowType.SILVER;
    }

    /**
     * Set's the {@link EnumArrowType} of the stack
     *
     * @return The same stack
     */
    public static @Nonnull ItemStack setType(@Nonnull ItemStack stack, EnumArrowType type) {
        NBTTagCompound nbt = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
        nbt.setString("type", type.name);
        stack.setTagCompound(nbt);
        return stack;
    }


    public ItemCrossbowArrow() {
        super(regName);
    }

    /**
     * @param stack
     *            Is copied by {@link EntityCrossbowArrow}
     * @param heightOffset
     *            An height offset for the position the entity is created
     * @return An arrow entity at the players position using the given itemstack
     */
    public EntityCrossbowArrow createEntity(ItemStack stack, World world, EntityPlayer player, double heightOffset, double centerOffset, boolean rightHand) {
        EntityCrossbowArrow entity = EntityCrossbowArrow.createWithShooter(world, player, heightOffset, centerOffset, rightHand, stack);
        EnumArrowType type = getType(stack);
        entity.setDamage(type.baseDamage);
        return entity;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        EnumArrowType type = getType(stack);
        return UtilLib.translate("item.werewolves." + regName + "." + type.name + ".name");
    }

    /**
     * @param type
     * @return A stack of this item with the given tier
     */
    public ItemStack getStack(EnumArrowType type) {
        return setType(new ItemStack(this), type);
    }

    /**
     * @param arrow
     *            Arrow stack
     * @return If the arrow entity that belongs to this arrow should be burning
     */
    public boolean isBurning(ItemStack arrow) {
        EnumArrowType type = getType(arrow);
        return type == EnumArrowType.SILVER;
    }

    /**
     * @return If an arrow of this type can be used in an infinite crossbow
     */
    public boolean isCanBeInfinite(ItemStack stack) {
        EnumArrowType type = getType(stack);
        return type != EnumArrowType.SILVER;
    }

    /**
     * Called when the {@link EntityCrossbowArrow} hits a block
     *
     * @param arrow
     *            The itemstack of the shot arrow
     * @param blockPos
     *            The position of the hit block
     * @param arrowEntity
     *            The arrow entity
     * @param shootingEntity
     *            The shooting entity. Can be the arrow entity itself
     */
    public void onHitBlock(ItemStack arrow, BlockPos blockPos, IEntityCrossbowArrow arrowEntity, Entity shootingEntity) {
        EnumArrowType type = getType(arrow);
    }

    /**
     * Called when the {@link EntityCrossbowArrow} hits an entity
     *
     * @param arrow
     *            The itemstack of the shot arrow
     * @param entity
     *            The hit entity
     * @param arrowEntity
     *            The arrow entity
     * @param shootingEntity
     *            The shooting entity. Can be the arrow entity itself
     */
    public void onHitEntity(ItemStack arrow, EntityLivingBase entity, IEntityCrossbowArrow arrowEntity, Entity shootingEntity) {
        EnumArrowType type = getType(arrow);
        if (type == EnumArrowType.SILVER) {
            if (entity instanceof IWerewolfMob) {
                float max = entity.getMaxHealth();
                if (max < Balance.general.ARROW_VAMPIRE_KILLER_MAX_HEALTH) {
                    entity.attackEntityFrom(DamageSource.causeArrowDamage((EntityArrow) arrowEntity, shootingEntity), max);
                }
            }
        }
    }

    public enum EnumArrowType {
        SILVER("silver", 0.7, 0x10b6f7);
        public final int color;
        final String name;
        final double baseDamage;

        EnumArrowType(String name, double baseDamage, int color) {
            this.name = name;
            this.baseDamage = baseDamage;
            this.color = color;
        }
    }
}
