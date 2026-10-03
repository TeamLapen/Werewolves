package de.teamlapen.werewolves.mixin.client;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RenderType.class)
public interface RenderTypeAccessor {

    @Invoker("create")
    static RenderType.CompositeRenderType create(String name, VertexFormat format, com.mojang.blaze3d.vertex.VertexFormat.Mode mode, int p_173213_, RenderType.CompositeState state) {
        throw new IllegalStateException("Failed to inject Accessor");
    }
}
