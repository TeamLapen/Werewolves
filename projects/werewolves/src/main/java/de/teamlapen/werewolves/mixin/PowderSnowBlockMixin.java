package de.teamlapen.werewolves.mixin;

import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.core.ModSkills;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.PowderSnowBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {

    @Inject(method = "canEntityWalkOnPowderSnow(Lnet/minecraft/world/entity/Entity;)Z", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
    private static void walk(Entity pEntity, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() && pEntity instanceof Player player) {
            WerewolfPlayer werewolf = WerewolfPlayer.get(player);
            if (werewolf.getForm() == WerewolfForm.SURVIVALIST && werewolf.getSkillHandler().isSkillEnabled(ModSkills.WOLF_PAWN.get())) {
                cir.setReturnValue(true);
            }
        }
    }
}
