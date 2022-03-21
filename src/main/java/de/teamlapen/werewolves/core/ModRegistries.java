package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.items.oil.IOil;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public class ModRegistries {
    public static final ResourceLocation WEAPON_OIL_ID = new ResourceLocation(REFERENCE.MODID, "weapon_oil");

    public static final IForgeRegistry<IOil> WEAPON_OILS;

    static {
        WEAPON_OILS = new RegistryBuilder<IOil>().setName(WEAPON_OIL_ID).setType(IOil.class).setMaxID(Integer.MAX_VALUE >> 5).create();
    }

    @SuppressWarnings("EmptyMethod")
    static void init() {
    }
}
