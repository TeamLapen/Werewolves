package de.teamlapen.vampirewerewolf.blocks;

import de.teamlapen.lib.lib.item.ItemMetaBlock;
import de.teamlapen.vampirewerewolf.VampireWerewolfMod;
import de.teamlapen.vampirewerewolf.util.REFERENCE;
import net.minecraft.block.BlockBush;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;

public class WerewolfFlower extends BlockBush implements ItemMetaBlock.IMetaItemName {
    public final static PropertyEnum<EnumFlowerType> TYPE = PropertyEnum.create("type", EnumFlowerType.class);
    private final static String regName = "werewolf_flower";

    public WerewolfFlower() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, EnumFlowerType.WOLFBANE));
        setCreativeTab(VampireWerewolfMod.creativeTab);
        setRegistryName(REFERENCE.MODID, regName);
        this.setUnlocalizedName(REFERENCE.MODID);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(TYPE).getMeta();
    }

    @Override
    public String getItemstackName(ItemStack stack) {
        return EnumFlowerType.getType(stack.getItemDamage()).getUnlocalizedName();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).getMeta();
    }

    public String getRegisteredName() {
        return regName;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, EnumFlowerType.getType(meta));
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        for (EnumFlowerType type : EnumFlowerType.values()) {
            list.add(new ItemStack(this, 1, type.getMeta()));
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TYPE);
    }

    public enum EnumFlowerType implements IStringSerializable {

        WOLFBANE(0, "werewolf_wolfsbane", "werewolf_wolfsbane");
        private static final EnumFlowerType[] TYPE_FOR_META = new EnumFlowerType[values().length];

        static {
            for (final EnumFlowerType type : values()) {
                TYPE_FOR_META[type.getMeta()] = type;
            }
        }

        public static EnumFlowerType getType(int meta) {
            if (meta >= TYPE_FOR_META.length) {
                return TYPE_FOR_META[0];
            }
            return TYPE_FOR_META[meta];
        }

        private final int meta;
        private final String name;
        private final String unlocalizedName;

        EnumFlowerType(int meta, String name, String unlocalizedName) {
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }

        public int getMeta() {
            return meta;
        }

        @Override
        public String getName() {
            return name;
        }

        public String getUnlocalizedName() {
            return unlocalizedName;
        }

    }

}
