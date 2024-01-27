package de.teamlapen.werewolves.mixin.entity;

import de.teamlapen.werewolves.core.ModSkills;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow protected abstract void playBlockFallSound();

    @Inject(method = "addEatEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;isEdible()Z", shift = At.Shift.AFTER), cancellable = true)
    private void test(ItemStack pFood, Level pLevel, LivingEntity pLivingEntity, CallbackInfo ci) {
        var food = pFood.getFoodProperties(pLivingEntity);
        if (food != null && !food.isMeat() && pLivingEntity instanceof  Player player && Helper.isWerewolf(player) && WerewolfPlayer.getOpt(player).map(WerewolfPlayer::getSkillHandler).filter(s -> s.isSkillEnabled(ModSkills.NOT_MEAT.get())).isEmpty()) {
            ci.cancel();
        }
    }
}
