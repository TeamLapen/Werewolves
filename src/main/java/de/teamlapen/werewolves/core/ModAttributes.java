package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.Keys.ATTRIBUTES, REFERENCE.MODID);

    public static final RegistryObject<Attribute> BITE_DAMAGE = ATTRIBUTES.register("bite_damage", () -> new RangedAttribute("werewolves.bite_damage", 5D, 0D, 100D));
    public static final RegistryObject<Attribute> TIME_REGAIN = ATTRIBUTES.register("time_regain", () -> new RangedAttribute("werewolves.werewolf_form_time_gain", 0.01D, 0D, 1D));

    static void register(IEventBus bus) {
        ATTRIBUTES.register(bus);
    }
}
