package de.teamlapen.werewolves.mixin.entity;

import de.teamlapen.werewolves.core.WerewolfSkills;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    private PlayerEntityMixin(EntityType<? extends LivingEntity> p_i48577_1_, Level p_i48577_2_) {
        super(p_i48577_1_, p_i48577_2_);
    }

    @Inject(method = "hurtArmor(Lnet/minecraft/util/DamageSource;F)V", at = @At("HEAD"), cancellable = true)
    private void doNotHurtArmorIfWerewolf(DamageSource p_230294_1_, float p_230294_2_, CallbackInfo ci) {
        WerewolfPlayer.getOpt(((Player) (Object) this)).filter(w -> w.getLevel() > 0).filter(w -> w.getForm().isTransformed() && (!w.getForm().isHumanLike() || !w.getSkillHandler().isSkillEnabled(WerewolfSkills.wear_armor))).ifPresent(werewolf -> {
            ci.cancel();
        });
    }
}
