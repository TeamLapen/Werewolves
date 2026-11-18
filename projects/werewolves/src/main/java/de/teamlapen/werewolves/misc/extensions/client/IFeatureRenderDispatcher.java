package de.teamlapen.werewolves.misc.extensions.client;

import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.feature.ModelPartFeatureRenderer;

public interface IFeatureRenderDispatcher {

    ModelPartFeatureRenderer werewolves$modelPartFeatureRenderer();

    ModelFeatureRenderer werewolves$modelFeatureRenderer();
}
