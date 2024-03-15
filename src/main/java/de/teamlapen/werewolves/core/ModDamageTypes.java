package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypes {

    public static final ResourceKey<DamageType> BITE = createKey("bite");
    public static final ResourceKey<DamageType> BLOOD_LOSS = createKey("blood_loss");

    private static ResourceKey<DamageType> createKey(@SuppressWarnings("SameParameterValue") String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(REFERENCE.MODID, name));
    }

    public static void createDamageTypes(BootstapContext<DamageType> context) {
        context.register(BITE, new DamageType("werewolves.bite", 0.1F));
        context.register(BLOOD_LOSS, new DamageType("werewolves.blood_loss", 0.2F));
    }

}
