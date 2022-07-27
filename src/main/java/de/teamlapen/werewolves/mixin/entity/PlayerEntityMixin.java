package de.teamlapen.werewolves.mixin.entity;

import de.teamlapen.werewolves.core.WerewolfSkills;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    private PlayerEntityMixin(EntityType<? extends LivingEntity> p_i48577_1_, World p_i48577_2_) {
        super(p_i48577_1_, p_i48577_2_);
    }

    @Inject(method = "hurtArmor(Lnet/minecraft/util/DamageSource;F)V", at = @At("HEAD"), cancellable = true)
    private void doNotHurtArmorIfWerewolf(DamageSource p_230294_1_, float p_230294_2_, CallbackInfo ci) {
        WerewolfPlayer.getOpt(((PlayerEntity) (Object) this)).filter(w -> w.getLevel() > 0).filter(w -> w.getForm().isTransformed() && (!w.getForm().isHumanLike() || !w.getSkillHandler().isSkillEnabled(WerewolfSkills.WEAR_ARMOR.get()))).ifPresent(werewolf -> {
            ci.cancel();
        });
    }
}
