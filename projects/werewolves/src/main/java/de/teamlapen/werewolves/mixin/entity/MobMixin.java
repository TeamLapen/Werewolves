package de.teamlapen.werewolves.mixin.entity;

import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.core.ModTags;
import de.teamlapen.werewolves.common.world.effects.SilverEffect;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MobMixin {

    @Shadow public abstract ItemStack getItemBySlot(EquipmentSlot pSlot);

    @Inject(method = "doHurtTarget(Lnet/minecraft/world/entity/Entity;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", shift = At.Shift.BEFORE))
    private void applySilverEffect(Entity pEntity, CallbackInfoReturnable<Boolean> cir) {
        if (pEntity instanceof LivingEntity living && Helper.isWerewolf(living) && this.getItemBySlot(EquipmentSlot.MAINHAND).is(ModTags.Items.SILVER_TOOL)) {
            living.addEffect(SilverEffect.createSilverEffect(living, ModConfig.BALANCE.UTIL.silverItemEffectDuration.get(), 0));
        }
    }
}
