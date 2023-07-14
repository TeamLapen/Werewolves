package de.teamlapen.werewolves.mixin.entity;

import de.teamlapen.werewolves.core.ModSkills;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow @Final private Inventory inventory;

    private PlayerEntityMixin(EntityType<? extends LivingEntity> p_i48577_1_, Level p_i48577_2_) {
        super(p_i48577_1_, p_i48577_2_);
    }

    @Inject(method = "hurtArmor(Lnet/minecraft/world/damagesource/DamageSource;F)V", at = @At("HEAD"), cancellable = true)
    private void doNotHurtArmorIfWerewolf(DamageSource p_230294_1_, float p_230294_2_, CallbackInfo ci) {
        WerewolfPlayer.getOpt(((Player) (Object) this)).filter(w -> w.getLevel() > 0).filter(w -> w.getForm().isTransformed() && (!w.getForm().isHumanLike() || !w.getSkillHandler().isSkillEnabled(ModSkills.WEAR_ARMOR.get()))).ifPresent(werewolf -> {
            ci.cancel();
        });
    }

    @Redirect(method = "getDigSpeed(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)F", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;getDestroySpeed(Lnet/minecraft/world/level/block/state/BlockState;)F"))
    private float werewolfDigSpeed(Inventory inventory, BlockState state) {
        float f = this.inventory.getDestroySpeed(state);
        if (Helper.isWerewolf((Player)(Object)this)) {
            ItemStack stack = this.inventory.player.getMainHandItem();
            if (stack.isEmpty()) {
                return Math.max(f, WerewolfPlayer.getOpt((Player)(Object)this).map(WerewolfPlayer::getDigSpeed).orElse(1.0F));
            }
        }
        return f;
    }

    @Inject(method = "dropEquipment", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;dropAll()V"))
    private void dropWerewolfEquipment(CallbackInfo ci) {
        WerewolfPlayer.getOpt(((Player) (Object) this)).ifPresent(WerewolfPlayer::dropEquipment);

    }
}
