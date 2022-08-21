package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.api.items.oil.IOil;
import de.teamlapen.werewolves.items.oil.Oil;
import de.teamlapen.werewolves.items.oil.SilverOil;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModOils {

    public static final DeferredRegister<IOil> OILS = DeferredRegister.create(ModRegistries.OIL_ID, REFERENCE.MODID);

    public static final RegistryObject<SilverOil> SILVER_OIL_1 = OILS.register("silver_oil_1", () -> new SilverOil(0x999999));
    public static final RegistryObject<SilverOil> SILVER_OIL_2 = OILS.register("silver_oil_2", () -> new SilverOil(0xaaaaaa));
    public static final RegistryObject<Oil> PLANT_OIL = OILS.register("plant_oil", () -> new Oil(0x7e6d27));
    public static final RegistryObject<Oil> EMPTY = OILS.register("empty", () -> new Oil(16253176));

    static void register(IEventBus bus) {
        OILS.register(bus);
    }
}
