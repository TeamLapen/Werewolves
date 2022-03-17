package de.teamlapen.werewolves.mixin.client;

import de.teamlapen.werewolves.client.core.ModEntityRenderer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

    @Shadow
    @Final
    private ItemRenderer itemRenderer;

    @Shadow
    @Final
    private EntityModelSet entityModels;

    @Shadow
    @Final
    private Font font;

    @Inject(method = "onResourceManagerReload(Lnet/minecraft/server/packs/resources/ResourceManager;)V", at = @At(value = "TAIL"))
    private void test(ResourceManager p_174004_, CallbackInfo ci) {
        ModEntityRenderer.updateRenderer(new EntityRendererProvider.Context((EntityRenderDispatcher) (Object) this, this.itemRenderer, p_174004_, this.entityModels, this.font));
    }
}
