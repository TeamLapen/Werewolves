package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.items.oil.WeaponOil;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class ModWeaponOils {

    public static final WeaponOil silver_oil = getNull();

    public static void register(IForgeRegistry<WeaponOil> registry) {
        registry.register(new WeaponOil(Helper::isWerewolf, (entity, damage) -> damage * 0.1f).setRegistryName(REFERENCE.MODID, "silver_oil"));
    }
}
