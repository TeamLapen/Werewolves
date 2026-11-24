package de.teamlapen.werewolves.misc.mixin.client.modcompat.epicfight;

import de.teamlapen.werewolves.client.renderer.entities.WerewolfPlayerRenderer;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "yesman.epicfight.client.events.engine.RenderEngine$Events")
public class EpicFightRenderEngineMixin {

    @Inject(method = "renderLivingEvent", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/event/RenderLivingEvent$Pre;setCanceled(Z)V", shift = At.Shift.BEFORE), cancellable = true, remap = false)
    private static void cancelWerewolfPlayerRendering(RenderLivingEvent.Pre<?, ?, ?> event, CallbackInfo ci) {
        if (event.getRenderer() instanceof WerewolfPlayerRenderer) {
            ci.cancel();
        }
    }

}
