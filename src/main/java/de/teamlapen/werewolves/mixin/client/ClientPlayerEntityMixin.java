package de.teamlapen.werewolves.mixin.client;

import com.mojang.authlib.GameProfile;
import de.teamlapen.werewolves.core.WerewolfSkills;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.WerewolfForm;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

    @Shadow
    private boolean autoJumpEnabled;

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "sendPosition()V", at = @At("RETURN"))
    public void sendPosition(CallbackInfo ci){
        if (!isControlledCamera()) return;
        if (!this.isAlive()) return;
        if (!WerewolfPlayer.getOpt(this).map(w -> w.getForm() == WerewolfForm.SURVIVALIST && w.getSkillHandler().isSkillEnabled(WerewolfSkills.climber)).orElse(false)) {
            return;
        }
        this.autoJumpEnabled = false;
    }

    @Shadow
    protected abstract boolean isControlledCamera();
}
