package de.teamlapen.werewolves.world;

import de.teamlapen.werewolves.core.ModDamageTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;

public class ModDamageSources {

    private final Registry<DamageType> damageTypes;
    private final DamageSource bloodLoss;

    public ModDamageSources(RegistryAccess registryAccess) {
        this.damageTypes = registryAccess.registryOrThrow(Registries.DAMAGE_TYPE);
        this.bloodLoss = init(ModDamageTypes.BLOOD_LOSS);
    }

    @SuppressWarnings({"SameParameterValue", "unused"})
    private DamageSource init(ResourceKey<DamageType> key) {
        return new DamageSource(this.damageTypes.getHolderOrThrow(key));
    }

    public DamageSource bite(LivingEntity entity) {
        return new DamageSource(this.damageTypes.getHolderOrThrow(ModDamageTypes.BITE), entity);
    }

    public DamageSource bloodLoss() {
        return this.bloodLoss;
    }

}
