package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.items.oil.IWeaponOil;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public class ModRegistries extends de.teamlapen.vampirism.core.ModRegistries {
    public static final ResourceLocation WEAPON_OIL_ID = new ResourceLocation(REFERENCE.MODID, "weapon_oil");

    public static final IForgeRegistry<IWeaponOil> WEAPON_OILS;

    static {
        WEAPON_OILS = new RegistryBuilder<IWeaponOil>().setName(WEAPON_OIL_ID).setType(IWeaponOil.class).setMaxID(Integer.MAX_VALUE >> 5).create();
    }

    static void init() {
    }
}
