package de.teamlapen.werewolves.items;

import com.google.common.collect.Sets;

import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.api.items.ISilverFactionSlayerItem;
import de.teamlapen.werewolves.util.REFERENCE;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import java.util.Set;

public class ItemSilverAxe extends ItemTool implements ISilverFactionSlayerItem {
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER, Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE);
    private static final float[] ATTACK_DAMAGES = new float[] {6.0F, 8.0F, 8.0F, 8.0F, 6.0F, WerewolvesMod.TOOL_SILVER.getAttackDamage()};
    private static final float[] ATTACK_SPEEDS = new float[] { -3.2F, -3.2F, -3.1F, -3.0F, -3.0F, -3.1F};
    
    public static final String regName = "silver_axe";

    public ItemSilverAxe() {
        super(WerewolvesMod.TOOL_SILVER, EFFECTIVE_ON);
        this.setCreativeTab(WerewolvesMod.creativeTab);
        this.setRegistryName(REFERENCE.MODID, regName);
        this.setUnlocalizedName(REFERENCE.MODID + "." + regName);
        this.attackDamage = ATTACK_DAMAGES[this.toolMaterial.ordinal()];
        this.attackSpeed = ATTACK_SPEEDS[this.toolMaterial.ordinal()];
    }
    
    public float getDestroySpeed(ItemStack stack, IBlockState state){
        Material material = state.getMaterial();
        return material != Material.WOOD && material != Material.PLANTS && material != Material.VINE ? super.getDestroySpeed(stack, state) : this.efficiency;
    }
}
