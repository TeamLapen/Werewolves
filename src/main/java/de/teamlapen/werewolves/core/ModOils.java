package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.items.oil.IOil;
import de.teamlapen.werewolves.items.oil.Oil;
import de.teamlapen.werewolves.items.oil.SilverOil;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class ModOils {

    public static final DeferredRegister<IOil> OILS = DeferredRegister.create(ModRegistries.WEAPON_OILS, REFERENCE.MODID);

    public static final RegistryObject<SilverOil> silver_oil_1 = OILS.register("silver_oil_1", () -> new SilverOil(0x999999));
    public static final RegistryObject<SilverOil> silver_oil_2 = OILS.register("silver_oil_2", () -> new SilverOil(0xaaaaaa));
    public static final RegistryObject<Oil> plant_oil = OILS.register("plant_oil", () -> new Oil(16253176));
    public static final RegistryObject<Oil> empty = OILS.register("empty", () -> new Oil(0x7e6d27));

    static void registerOils(IEventBus bus) {
        OILS.register(bus);
    }
}
