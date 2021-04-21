package de.teamlapen.werewolves.mixin.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingRenderer.class)
public abstract class LivingRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> {

    public LivingRendererMixin(EntityRendererManager renderManager, Minecraft minecraft) {
        super(renderManager);
    }

    @Shadow
    protected M entityModel;

    @Shadow
    protected abstract boolean isVisible(T livingEntityIn);

    @Shadow
    protected abstract RenderType func_230496_a_(T p_230496_1_, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_);

    @Shadow
    protected abstract float getOverlayProgress(T livingEntityIn, float partialTicks);

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingRenderer;func_230496_a_(Lnet/minecraft/entity/LivingEntity;ZZZ)Lnet/minecraft/client/renderer/RenderType;", shift = At.Shift.BEFORE))
    public void renderModel_werewolves(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, CallbackInfo ci) {
        if (this.entityModel instanceof WerewolfBaseModel) {
            Minecraft minecraft = Minecraft.getInstance();
            boolean flag = this.isVisible(entityIn);
            boolean flag1 = !flag && !entityIn.isInvisibleToPlayer(minecraft.player);
            boolean flag2 = minecraft.isEntityGlowing(entityIn);
            RenderType rendertype = this.func_230496_a_(entityIn, flag, flag1, flag2);
            if (rendertype != null) {
                IVertexBuilder ivertexbuilder = bufferIn.getBuffer(rendertype);
                int i = LivingRenderer.getPackedOverlay(entityIn, this.getOverlayProgress(entityIn, partialTicks));
                this.entityModel.render(matrixStackIn, ivertexbuilder, packedLightIn, i, 1.0F, 1.0F, 1.0F, flag1 ? 0.15F : 1.0F);
            }
        }
    }

    @ModifyVariable(method = "render", at = @At(value = "STORE", ordinal = 0))
    public RenderType setRenderTypeToNull(RenderType type){
        if (this.entityModel instanceof WerewolfBaseModel) {
            return null;
        }
        return type;
    }
}
