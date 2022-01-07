package de.teamlapen.werewolves.mixin.entity;

import de.teamlapen.werewolves.util.DamageSourceExtended;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow public abstract int getArmorValue();

    @Redirect(method = "getDamageAfterArmorAbsorb(Lnet/minecraft/util/DamageSource;F)F", at =  @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getArmorValue()I"))
    private int modifyArmorValue_werewolves(LivingEntity instance, DamageSource source, float amount) {
        return privateGetArmorValue(source, amount);
    }

    private int privateGetArmorValue(DamageSource source, float amount) {
        return (int) (((DamageSourceExtended) source).ignoreArmorPerc() * this.getArmorValue());
    }
}
