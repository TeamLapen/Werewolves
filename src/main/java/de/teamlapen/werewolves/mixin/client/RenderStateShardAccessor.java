package de.teamlapen.werewolves.mixin.client;

import net.minecraft.client.renderer.RenderStateShard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RenderStateShard.class)
public interface RenderStateShardAccessor {

    @Accessor
    static RenderStateShard.ShaderStateShard getRENDERTYPE_GLINT_SHADER() {
        throw new IllegalStateException("Failed to inject Accessor");
    }

    @Accessor
    static RenderStateShard.ShaderStateShard getRENDERTYPE_GLINT_DIRECT_SHADER() {
        throw new IllegalStateException("Failed to inject Accessor");
    }

    @Accessor
    static RenderStateShard.WriteMaskStateShard getCOLOR_WRITE() {
        throw new IllegalStateException("Failed to inject Accessor");
    }

    @Accessor
    static RenderStateShard.CullStateShard getNO_CULL() {
        throw new IllegalStateException("Failed to inject Accessor");
    }

    @Accessor
    static RenderStateShard.DepthTestStateShard getEQUAL_DEPTH_TEST() {
        throw new IllegalStateException("Failed to inject Accessor");
    }

    @Accessor
    static RenderStateShard.TransparencyStateShard getGLINT_TRANSPARENCY() {
        throw new IllegalStateException("Failed to inject Accessor");
    }

    @Accessor
    static RenderStateShard.TexturingStateShard getGLINT_TEXTURING() {
        throw new IllegalStateException("Failed to inject Accessor");
    }
}
