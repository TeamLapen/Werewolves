package de.teamlapen.werewolves.client.core;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import de.teamlapen.werewolves.api.WIdentifier;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.neoforged.neoforge.client.event.RegisterRenderPipelinesEvent;

public class ModRenderPipelines {
    public static final RenderPipeline.Snippet SOLID_TRANSPARENCY = RenderPipeline.builder(RenderPipelines.MATRICES_FOG_LIGHT_DIR_SNIPPET)
            .withVertexShader("core/entity")
            .withFragmentShader("core/entity")
            .withSampler("Sampler1")
            .withVertexFormat(DefaultVertexFormat.ENTITY, VertexFormat.Mode.QUADS)
            .buildSnippet();
    public static final RenderPipeline SOLID_TRANSPARENCY_ENTITY = RenderPipeline.builder(SOLID_TRANSPARENCY)
            .withLocation(WIdentifier.mod("pipeline/entity_solid"))
            .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT_PREMULTIPLIED_ALPHA))
            .withCull(true)
            .build();

    public static final RenderSetup SOLID = RenderSetup.builder(SOLID_TRANSPARENCY_ENTITY)
            .bufferSize(256)
            .useLightmap()
            .useOverlay()
            .sortOnUpload()
            .createRenderSetup();
    private static final RenderType ENTITY_TRANSPARENCY = RenderType.create(WIdentifier.modString("solid_transparency_entity"), SOLID);


    public static RenderType solidTransparencyEntity() {
        return ENTITY_TRANSPARENCY;
    }

    public static void registerRenderPipelines(RegisterRenderPipelinesEvent event) {
        event.registerPipeline(SOLID_TRANSPARENCY_ENTITY);
    }
}
