package de.teamlapen.werewolves.misc.injection.client;

import de.teamlapen.werewolves.misc.extensions.client.ILevelRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;

@Deprecated
public interface ILevelRendererWerewolvesMock extends ILevelRenderer {

    @Override
    default EntityRenderDispatcher werewolves$entityRenderDispatcher() {
        throw new IllegalStateException("This class is only supported as injection class");
    }
}
