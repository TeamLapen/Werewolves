package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.resources.ResourceLocation;

public abstract class ModClient extends RenderType {
    public static final ResourceLocation OIL_GLINT_LOCATION = new ResourceLocation(REFERENCE.MODID,"textures/misc/oil_item_glint.png");
    public static final RenderType OIL_GLINT = create("oil_glint", DefaultVertexFormat.POSITION_TEX, 7, 256, RenderType.CompositeState.builder().setTextureState(new RenderStateShard.TextureStateShard(OIL_GLINT_LOCATION, true, false)).setWriteMaskState(COLOR_WRITE).setCullState(NO_CULL).setDepthTestState(EQUAL_DEPTH_TEST).setTransparencyState(GLINT_TRANSPARENCY).setTexturingState(GLINT_TEXTURING).createCompositeState(false));
    public static final RenderType OIL_GLINT_DIRECT = create("glint_direct", DefaultVertexFormat.POSITION_TEX, 7, 256, RenderType.CompositeState.builder().setTextureState(new RenderStateShard.TextureStateShard(OIL_GLINT_LOCATION, true, false)).setWriteMaskState(COLOR_WRITE).setCullState(NO_CULL).setDepthTestState(EQUAL_DEPTH_TEST).setTransparencyState(GLINT_TRANSPARENCY).setTexturingState(GLINT_TEXTURING).createCompositeState(false));


    private ModClient(String p_i225992_1_, VertexFormat p_i225992_2_, int p_i225992_3_, int p_i225992_4_, boolean p_i225992_5_, boolean p_i225992_6_, Runnable p_i225992_7_, Runnable p_i225992_8_) {
        super(p_i225992_1_, p_i225992_2_, p_i225992_3_, p_i225992_4_, p_i225992_5_, p_i225992_6_, p_i225992_7_, p_i225992_8_);
    }
}
