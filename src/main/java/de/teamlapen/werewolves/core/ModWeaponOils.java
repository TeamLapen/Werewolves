package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.items.oil.IWeaponOil;
import de.teamlapen.werewolves.items.oil.SilverWeaponOil;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class ModWeaponOils {

    public static final SilverWeaponOil silver_oil = getNull();

    public static void register(IForgeRegistry<IWeaponOil> registry) {
        registry.register(new SilverWeaponOil().setRegistryName(REFERENCE.MODID, "silver_oil"));
    }
}
