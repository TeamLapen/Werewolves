package de.teamlapen.werewolves.items;

import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.api.items.ISilverFactionSlayerItem;
import de.teamlapen.werewolves.util.REFERENCE;

import net.minecraft.item.ItemPickaxe;

public class ItemSilverPickaxe extends ItemPickaxe implements ISilverFactionSlayerItem {
    public static final String regName = "silver_pickaxe";

    public ItemSilverPickaxe() {
        super(WerewolvesMod.TOOL_SILVER);
        this.setCreativeTab(WerewolvesMod.creativeTab);
        this.setRegistryName(REFERENCE.MODID, regName);
        this.setUnlocalizedName(REFERENCE.MODID + "." + regName);
    }

}
