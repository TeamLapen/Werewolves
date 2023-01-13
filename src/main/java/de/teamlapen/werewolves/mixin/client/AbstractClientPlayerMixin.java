package de.teamlapen.werewolves.mixin.client;

import com.mojang.authlib.GameProfile;
import de.teamlapen.werewolves.client.render.WerewolfPlayerRenderer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin extends Player {

    @Deprecated
    private AbstractClientPlayerMixin(Level p_250508_, BlockPos p_250289_, float p_251702_, GameProfile p_252153_) {
        super(p_250508_, p_250289_, p_251702_, p_252153_);
    }

    @Inject(method = "getModelName", at = @At("HEAD"), cancellable = true)
    private void getWerewolfModelName(CallbackInfoReturnable<String> cir) {
        WerewolfPlayerRenderer.getWerewolfRenderer(((AbstractClientPlayer)(Object) this)).ifPresent(cir::setReturnValue);
    }
}
