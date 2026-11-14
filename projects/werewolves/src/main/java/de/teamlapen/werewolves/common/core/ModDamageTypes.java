package de.teamlapen.werewolves.common.core;

import de.teamlapen.werewolves.api.WEnums;
import de.teamlapen.werewolves.api.WIdentifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypes {

    public static final ResourceKey<DamageType> BITE = createKey("bite");
    public static final ResourceKey<DamageType> BLOOD_LOSS = createKey("blood_loss");


    private static ResourceKey<DamageType> createKey(@SuppressWarnings("SameParameterValue") String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, WIdentifier.mod(name));
    }

    public static void createDamageTypes(BootstrapContext<DamageType> context) {
        context.register(BITE, new DamageType("werewolves.bite", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.1F, DamageEffects.HURT, WEnums.WEREWOLF_BITE.getValue()));
        context.register(BLOOD_LOSS, new DamageType("werewolves.blood_loss", 0.2F));
    }

}
