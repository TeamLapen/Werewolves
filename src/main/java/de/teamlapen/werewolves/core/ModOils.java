package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.VampirismRegistries;
import de.teamlapen.vampirism.api.items.oil.IOil;
import de.teamlapen.werewolves.items.oil.SilverOil;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModOils {

    public static final DeferredRegister<IOil> OILS = DeferredRegister.create(VampirismRegistries.OILS_ID, REFERENCE.MODID);

    public static final RegistryObject<SilverOil> SILVER_OIL_1 = OILS.register("silver_oil_1", () -> new SilverOil(0x999999, 15));
    public static final RegistryObject<SilverOil> SILVER_OIL_2 = OILS.register("silver_oil_2", () ->  new SilverOil(0xaaaaaa, 40));

    static void register(IEventBus bus) {
        OILS.register(bus);
    }
}
