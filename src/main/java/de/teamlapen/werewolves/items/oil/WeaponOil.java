package de.teamlapen.werewolves.items.oil;

import net.minecraft.entity.LivingEntity;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.function.Predicate;

public class WeaponOil extends ForgeRegistryEntry<WeaponOil> {

    private final Predicate<LivingEntity> entityPredicate;
    private final DamageCalculator damageCalculator;

    public WeaponOil(Predicate<LivingEntity> entityPredicate, DamageCalculator damageCalculator) {
        this.entityPredicate = entityPredicate;
        this.damageCalculator = damageCalculator;
    }

    public boolean canEffect(LivingEntity entity) {
        return this.entityPredicate.test(entity);
    }

    public float getAdditionalDamage(LivingEntity entity, float damage){
        return this.damageCalculator.getAdditionalDamage(entity, damage);
    }

    public Predicate<LivingEntity> getEntityPredicate() {
        return this.entityPredicate;
    }

    public DamageCalculator getDamageCalculator() {
        return this.damageCalculator;
    }

    public interface DamageCalculator {
        float getAdditionalDamage(LivingEntity entity, float damage);
    }
}
