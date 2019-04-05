package de.teamlapen.vampirewerewolf.items;

import de.teamlapen.vampirewerewolf.VampireWerewolfMod;
import de.teamlapen.vampirewerewolf.util.REFERENCE;
import net.minecraft.item.ItemSpade;

public class ItemModShovel extends ItemSpade{

    public ItemModShovel(String regName, ToolMaterial material) {
        super(material);
        this.setCreativeTab(VampireWerewolfMod.creativeTab);
        this.setRegistryName(REFERENCE.MODID, regName);
        this.setUnlocalizedName(REFERENCE.MODID + "." + regName);
    }

}
