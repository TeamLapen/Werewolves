package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class ModAttributes {

    public static final Attribute bite_damage = getNull();
    public static final Attribute time_regain = getNull();

    static void registerAttributes(IForgeRegistry<Attribute> registry) {
        registry.register(new RangedAttribute("werewolves.bite_damage", 0.0D, 0D, 100D).setRegistryName(REFERENCE.MODID, "bite_damage"));
        registry.register(new RangedAttribute("werewolves.werewolf_form_time_gain", 1D, 0D, 2D).setRegistryName(REFERENCE.MODID, "time_regain"));
    }
}
