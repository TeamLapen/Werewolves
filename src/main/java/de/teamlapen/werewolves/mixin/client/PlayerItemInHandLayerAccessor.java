package de.teamlapen.werewolves.mixin.client;

import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerItemInHandLayer.class)
public interface PlayerItemInHandLayerAccessor {

    @Accessor("itemInHandRenderer")
    ItemInHandRenderer getItemInHandRenderer();
}
