package de.teamlapen.werewolves.common.world.items;

import de.teamlapen.werewolves.api.items.ISilverItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

public class SilverSwordItem extends Item implements ISilverItem {

    public SilverSwordItem(Properties properties, ToolMaterial material, float attackDamageBaseline, float attackSpeedBaseline) {
        super(properties.sword(material, attackDamageBaseline, attackSpeedBaseline));
    }
}
