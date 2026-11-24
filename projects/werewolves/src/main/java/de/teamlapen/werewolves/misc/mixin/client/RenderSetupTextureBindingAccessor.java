package de.teamlapen.werewolves.misc.mixin.client;

import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net.minecraft.client.renderer.rendertype.RenderSetup$TextureBinding")
public interface RenderSetupTextureBindingAccessor {

    @Accessor("location")
    Identifier werewolves$getLocation();
}
