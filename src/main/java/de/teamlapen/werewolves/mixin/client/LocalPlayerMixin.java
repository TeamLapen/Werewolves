package de.teamlapen.werewolves.mixin.client;

import com.mojang.authlib.GameProfile;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.core.ModSkills;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("unused")
@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer {

    @Shadow
    private boolean autoJumpEnabled;

    public LocalPlayerMixin(ClientLevel p_250460_, GameProfile p_249912_) {
        super(p_250460_, p_249912_);
    }

    @Inject(method = "sendPosition()V", at = @At("RETURN"))
    public void sendPosition(CallbackInfo ci) {
        if (!isControlledCamera()) return;
        if (!this.isAlive()) return;
        WerewolfPlayer werewolf = WerewolfPlayer.get(this);
        if (werewolf.getForm() != WerewolfForm.SURVIVALIST && werewolf.getSkillHandler().isSkillEnabled(ModSkills.CLIMBER.get())) {
            return;
        }
        this.autoJumpEnabled = false;
    }

    @Shadow
    protected abstract boolean isControlledCamera();
}
