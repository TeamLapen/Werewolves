package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.items.oil.IOil;
import de.teamlapen.vampirism.core.ModRegistries;
import de.teamlapen.vampirism.items.oil.Oil;
import de.teamlapen.werewolves.items.oil.SilverOil;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class ModOils {

    public static final DeferredRegister<IOil> OILS = DeferredRegister.create(ModRegistries.OILS, REFERENCE.MODID);

    public static final RegistryObject<SilverOil> silver_oil_1 = OILS.register("silver_oil_1", () -> new SilverOil(0x999999, 15));
    public static final RegistryObject<SilverOil> silver_oil_2 = OILS.register("silver_oil_2", () -> new SilverOil(0xaaaaaa, 40));

    static void registerOils(IEventBus bus) {
        OILS.register(bus);
    }
}
