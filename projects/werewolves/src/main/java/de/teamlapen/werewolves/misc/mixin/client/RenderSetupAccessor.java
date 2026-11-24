package de.teamlapen.werewolves.misc.mixin.client;

import net.minecraft.client.renderer.rendertype.RenderSetup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(RenderSetup.class)
public interface RenderSetupAccessor {

    /**
     * @return the texture bindings by sampler name. The values are {@code RenderSetup.TextureBinding}, see {@link RenderSetupTextureBindingAccessor}
     */
    @Accessor("textures")
    Map<String, ?> werewolves$getTextures();
}
