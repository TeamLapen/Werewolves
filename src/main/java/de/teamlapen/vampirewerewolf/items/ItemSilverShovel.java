package de.teamlapen.vampirewerewolf.items;

import de.teamlapen.vampirewerewolf.VampireWerewolfMod;
import de.teamlapen.vampirewerewolf.api.items.ISilverFactionSlayerItem;
import de.teamlapen.vampirewerewolf.util.REFERENCE;
import net.minecraft.item.ItemSpade;

public class ItemSilverShovel extends ItemSpade implements ISilverFactionSlayerItem {
    public static final String regName = "silver_shovel";

    public ItemSilverShovel() {
        super(VampireWerewolfMod.TOOL_SILVER);
        this.setCreativeTab(VampireWerewolfMod.creativeTab);
        this.setRegistryName(REFERENCE.MODID, regName);
        this.setUnlocalizedName(REFERENCE.MODID + "." + regName);
    }


}
