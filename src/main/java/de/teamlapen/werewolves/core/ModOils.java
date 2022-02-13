package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.items.oil.IOil;
import de.teamlapen.werewolves.items.oil.Oil;
import de.teamlapen.werewolves.items.oil.SilverOil;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class ModOils {

    public static final SilverOil silver_oil_1 = getNull();
    public static final SilverOil silver_oil_2 = getNull();
    public static final Oil plant_oil = getNull();
    public static final Oil empty = getNull();

    public static void register(IForgeRegistry<IOil> registry) {
        registry.register(new SilverOil(0x999999).setRegistryName(REFERENCE.MODID, "silver_oil_1"));
        registry.register(new SilverOil(0xaaaaaa).setRegistryName(REFERENCE.MODID, "silver_oil_2"));
        registry.register(new Oil(16253176).setRegistryName(REFERENCE.MODID, "empty"));
        registry.register(new Oil(0x7e6d27).setRegistryName(REFERENCE.MODID, "plant_oil"));
    }
}
