package de.teamlapen.werewolves.misc.mixin.client;

import de.teamlapen.werewolves.misc.extensions.client.IFeatureRenderDispatcher;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.feature.ModelPartFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FeatureRenderDispatcher.class)
public interface FeatureRenderDispatcherAccessor extends IFeatureRenderDispatcher {

    @Accessor("modelPartFeatureRenderer")
    @Override
    ModelPartFeatureRenderer werewolves$modelPartFeatureRenderer();

    @Accessor("modelFeatureRenderer")
    @Override
    ModelFeatureRenderer werewolves$modelFeatureRenderer();
}
