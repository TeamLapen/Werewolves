package de.teamlapen.vampirewerewolf.items;

import de.teamlapen.vampirewerewolf.VampireWerewolfMod;
import de.teamlapen.vampirewerewolf.util.REFERENCE;
import net.minecraft.item.ItemHoe;

public class ItemModHoe extends ItemHoe {

    public ItemModHoe(String regName, ToolMaterial material) {
        super(material);
        this.setCreativeTab(VampireWerewolfMod.creativeTab);
        this.setRegistryName(REFERENCE.MODID, regName);
        this.setUnlocalizedName(REFERENCE.MODID + "." + regName);
    }

}
