package de.teamlapen.werewolves.misc.mixin.client;

import de.teamlapen.werewolves.misc.extensions.client.ILevelRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LevelRenderer.class)
public interface LevelRendererAccessor extends ILevelRenderer {
    @Override
    @Accessor("entityRenderDispatcher")
    EntityRenderDispatcher werewolves$entityRenderDispatcher();
}
