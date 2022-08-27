package de.teamlapen.werewolves.mixin.client;

import com.mojang.authlib.GameProfile;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.core.ModSkills;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.ProfilePublicKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("unused")
@Mixin(LocalPlayer.class)
public abstract class AbstractClientPlayerMixin extends AbstractClientPlayer {

    @Shadow
    private boolean autoJumpEnabled;

    public AbstractClientPlayerMixin(@NotNull ClientLevel p_234112_, @NotNull GameProfile p_234113_, @Nullable ProfilePublicKey p_234114_) {
        super(p_234112_, p_234113_, p_234114_);
    }

    @Inject(method = "sendPosition()V", at = @At("RETURN"))
    public void sendPosition(CallbackInfo ci) {
        if (!isControlledCamera()) return;
        if (!this.isAlive()) return;
        if (!WerewolfPlayer.getOpt(this).map(w -> w.getForm() == WerewolfForm.SURVIVALIST && w.getSkillHandler().isSkillEnabled(ModSkills.CLIMBER.get())).orElse(false)) {
            return;
        }
        this.autoJumpEnabled = false;
    }

    @Shadow
    protected abstract boolean isControlledCamera();
}
