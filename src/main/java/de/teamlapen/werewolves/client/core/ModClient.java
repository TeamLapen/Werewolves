package de.teamlapen.werewolves.client.core;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import de.teamlapen.werewolves.mixin.client.RenderStateShardAccessor;
import de.teamlapen.werewolves.mixin.client.RenderTypeAccessor;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public abstract class ModClient {
    public static final ResourceLocation OIL_GLINT_LOCATION = new ResourceLocation(REFERENCE.MODID, "textures/misc/oil_item_glint.png");
    public static final RenderType OIL_GLINT = RenderTypeAccessor.create("oil_glint", DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, RenderType.CompositeState.builder().setShaderState(RenderStateShardAccessor.getRENDERTYPE_GLINT_SHADER()).setTextureState(new RenderStateShard.TextureStateShard(OIL_GLINT_LOCATION, true, false)).setWriteMaskState(RenderStateShardAccessor.getCOLOR_WRITE()).setCullState(RenderStateShardAccessor.getNO_CULL()).setDepthTestState(RenderStateShardAccessor.getEQUAL_DEPTH_TEST()).setTransparencyState(RenderStateShardAccessor.getGLINT_TRANSPARENCY()).setTexturingState(RenderStateShardAccessor.getGLINT_TEXTURING()).createCompositeState(false));
    public static final RenderType OIL_GLINT_DIRECT = RenderTypeAccessor.create("glint_direct", DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, RenderType.CompositeState.builder().setShaderState(RenderStateShardAccessor.getRENDERTYPE_GLINT_DIRECT_SHADER()).setTextureState(new RenderStateShard.TextureStateShard(OIL_GLINT_LOCATION, true, false)).setWriteMaskState(RenderStateShardAccessor.getCOLOR_WRITE()).setCullState(RenderStateShardAccessor.getNO_CULL()).setDepthTestState(RenderStateShardAccessor.getEQUAL_DEPTH_TEST()).setTransparencyState(RenderStateShardAccessor.getGLINT_TRANSPARENCY()).setTexturingState(RenderStateShardAccessor.getGLINT_TEXTURING()).createCompositeState(false));

}
