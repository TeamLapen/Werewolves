package de.teamlapen.werewolves.misc.injection.client;

import de.teamlapen.werewolves.misc.extensions.client.IFeatureRenderDispatcher;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.feature.ModelPartFeatureRenderer;

@Deprecated
public interface IFeatureRenderDispatcherWerewolvesMock extends IFeatureRenderDispatcher {

    @Override
    default ModelPartFeatureRenderer werewolves$modelPartFeatureRenderer() {
        throw new IllegalStateException("This class is only supported as injection class");
    }

    @Override
    default ModelFeatureRenderer werewolves$modelFeatureRenderer() {
        throw new IllegalStateException("This class is only supported as injection class");
    }
}
