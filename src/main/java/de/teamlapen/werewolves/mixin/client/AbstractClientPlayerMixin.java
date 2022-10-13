package de.teamlapen.werewolves.mixin.client;

import com.mojang.authlib.GameProfile;
import de.teamlapen.werewolves.client.gui.WerewolfPlayerAppearanceScreen;
import de.teamlapen.werewolves.client.render.WerewolfPlayerRenderer;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin extends Player {

    @Deprecated
    private AbstractClientPlayerMixin(Level p_219727_, BlockPos p_219728_, float p_219729_, GameProfile p_219730_, @Nullable ProfilePublicKey p_219731_) {
        super(p_219727_, p_219728_, p_219729_, p_219730_, p_219731_);
    }

    @Inject(method = "getModelName", at = @At("HEAD"), cancellable = true)
    private void getWerewolfModelName(CallbackInfoReturnable<String> cir) {
        WerewolfPlayerRenderer.getWerewolfRenderer(((AbstractClientPlayer)(Object) this)).ifPresent(cir::setReturnValue);
    }
}
