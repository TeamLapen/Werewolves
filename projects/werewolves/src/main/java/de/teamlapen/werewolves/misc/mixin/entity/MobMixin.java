package de.teamlapen.werewolves.misc.mixin.entity;

import de.teamlapen.werewolves.common.tags.ModItemTags;
import net.minecraft.server.level.ServerLevel;

import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.world.effects.SilverEffect;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity {

    @Deprecated
    private MobMixin(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @Inject(method = "doHurtTarget(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Entity;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z", shift = At.Shift.BEFORE))
    private void applySilverEffect(ServerLevel level, Entity pEntity, CallbackInfoReturnable<Boolean> cir) {
        if (pEntity instanceof LivingEntity living && Helper.isWerewolf(living) && this.getItemBySlot(EquipmentSlot.MAINHAND).is(ModItemTags.SILVER_TOOL)) {
            living.addEffect(SilverEffect.createSilverEffect(living, ModConfig.balance().UTIL.silverItemEffectDuration.get(), 0));
        }
    }
}
