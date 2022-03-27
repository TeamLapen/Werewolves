package de.teamlapen.werewolves.mixin.client;

import com.mojang.blaze3d.vertex.BufferBuilder;
import de.teamlapen.werewolves.client.core.ModClient;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderBuffers.class)
public abstract class RenderTypeBuffersMixin {

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Inject(method = "lambda$new$1(Lit/unimi/dsi/fastutil/objects/Object2ObjectLinkedOpenHashMap;)V", at = @At("TAIL"))
    private void addOwn(Object2ObjectLinkedOpenHashMap p_228485_1_, CallbackInfo ci) {
        put(p_228485_1_, ModClient.OIL_GLINT);
        put(p_228485_1_, ModClient.OIL_GLINT_DIRECT);
    }

    @Shadow
    private static void put(Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder> p_228486_0_, RenderType p_228486_1_) {
        throw new IllegalStateException();
    }
}
