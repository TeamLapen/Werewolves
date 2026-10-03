package de.teamlapen.werewolves.mixin.client;

import net.neoforged.fml.ModLoadingException;
import net.neoforged.neoforge.client.loading.ClientModLoader;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientModLoader.class)
public interface ClientModLoaderAccessor {

    @Accessor(value = "error", remap = false)
    @Nullable
    static ModLoadingException getError() {
        throw new IllegalStateException("Failed to inject Accessor");
    }
}
