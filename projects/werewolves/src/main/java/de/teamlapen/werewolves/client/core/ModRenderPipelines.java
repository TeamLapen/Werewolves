package de.teamlapen.werewolves.client.core;

import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.misc.mixin.client.RenderSetupAccessor;
import de.teamlapen.werewolves.misc.mixin.client.RenderSetupTextureBindingAccessor;
import de.teamlapen.werewolves.misc.mixin.client.RenderTypeAccessor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.OutputTarget;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.neoforged.neoforge.client.event.RegisterRenderPipelinesEvent;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class ModRenderPipelines {

    /**
     * Like the vanilla outline pipeline (flat vertex color, texture only used for cutout), but with depth testing so that the
     * nearest surface of all sensed entities ends up in the sense target
     */
    public static final RenderPipeline SENSE_MASK_CULL = RenderPipeline.builder(RenderPipelines.OUTLINE_SNIPPET)
            .withLocation(WIdentifier.mod("pipeline/sense_mask_cull"))
            .withDepthStencilState(DepthStencilState.DEFAULT)
            .build();
    public static final RenderPipeline SENSE_MASK_NO_CULL = RenderPipeline.builder(RenderPipelines.OUTLINE_SNIPPET)
            .withLocation(WIdentifier.mod("pipeline/sense_mask_no_cull"))
            .withDepthStencilState(DepthStencilState.DEFAULT)
            .withCull(false)
            .build();

    public static final OutputTarget SENSE_TARGET = new OutputTarget("werewolves_sense_target", RenderHandler::senseTarget);

    private static final BiFunction<Identifier, Boolean, RenderType> SENSE_MASK = Util.memoize((texture, cull) -> RenderType.create(
            WIdentifier.modString("sense_mask"),
            RenderSetup.builder(cull ? SENSE_MASK_CULL : SENSE_MASK_NO_CULL)
                    .withTexture("Sampler0", texture)
                    .setOutputTarget(SENSE_TARGET)
                    .createRenderSetup()
    ));
    private static final Map<RenderType, Optional<RenderType>> SENSE_MASK_BY_TYPE = new ConcurrentHashMap<>();

    /**
     * @return a render type that draws the geometry of the given render type into the sense target, or null if it has no texture to cut out
     */
    public static @Nullable RenderType senseMask(RenderType renderType) {
        return SENSE_MASK_BY_TYPE.computeIfAbsent(renderType, type -> {
            Object binding = ((RenderSetupAccessor) (Object) ((RenderTypeAccessor) type).werewolves$getState()).werewolves$getTextures().get("Sampler0");
            if (binding == null) {
                return Optional.empty();
            }
            Identifier texture = ((RenderSetupTextureBindingAccessor) binding).werewolves$getLocation();
            return Optional.of(SENSE_MASK.apply(texture, type.pipeline().isCull()));
        }).orElse(null);
    }

    public static void registerRenderPipelines(RegisterRenderPipelinesEvent event) {
        event.registerPipeline(SENSE_MASK_CULL);
        event.registerPipeline(SENSE_MASK_NO_CULL);
    }
}
