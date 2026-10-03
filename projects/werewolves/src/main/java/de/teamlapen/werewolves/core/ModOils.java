package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.VampirismRegistries;
import de.teamlapen.vampirism.api.items.oil.IOil;
import de.teamlapen.werewolves.items.oil.SilverOil;
import de.teamlapen.werewolves.util.REFERENCE;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.checkerframework.checker.units.qual.K;

public class ModOils {

    public static final DeferredRegister<IOil> OILS = DeferredRegister.create(VampirismRegistries.Keys.OIL, REFERENCE.MODID);

    public static final DeferredHolder<IOil, SilverOil> SILVER_OIL_1 = OILS.register("silver_oil_1", () -> new SilverOil(0x999999, 15));
    public static final DeferredHolder<IOil, SilverOil> SILVER_OIL_2 = OILS.register("silver_oil_2", () ->  new SilverOil(0xaaaaaa, 40));

    static void register(IEventBus bus) {
        OILS.register(bus);
    }
}
