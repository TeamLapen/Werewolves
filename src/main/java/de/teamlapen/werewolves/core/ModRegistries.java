package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.api.items.oil.IOil;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

public class ModRegistries {
    public static final ResourceLocation WEAPON_OIL_ID = new ResourceLocation(REFERENCE.MODID, "weapon_oil");

    public static IForgeRegistry<IOil> WEAPON_OILS;

    static void createRegistries(NewRegistryEvent event) {
        event.create(new RegistryBuilder<IOil>().setName(WEAPON_OIL_ID).setType(IOil.class).setMaxID(Integer.MAX_VALUE >> 5), r -> WEAPON_OILS = r);
    }
}
