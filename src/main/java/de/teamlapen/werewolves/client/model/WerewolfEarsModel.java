package de.teamlapen.werewolves.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * WerewolfEarsModel - RebelT
 * Created using Tabula 7.1.0
 */
public class WerewolfEarsModel<T extends LivingEntity> extends WerewolfBaseModel<T> {
    public ModelRenderer clawsLeft;
    public ModelRenderer clawsRight;
    public ModelRenderer earRight;
    public ModelRenderer earLeft;

    public WerewolfEarsModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.earRight = new ModelRenderer(this, 16, 0);
        this.earRight.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.earRight.addBox(-4.5F, -8.0F, -2.5F, 1, 6, 3, 0.0F);
        this.setRotateAngle(earRight, -0.4886921905584123F, -0.2617993877991494F, 0.0F);
        this.earLeft = new ModelRenderer(this, 16, 9);
        this.earLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.earLeft.addBox(3.5F, -8.0F, -2.5F, 1, 6, 3, 0.0F);
        this.setRotateAngle(earLeft, -0.4886921905584123F, 0.2617993877991494F, 0.0F);
        this.clawsRight = new ModelRenderer(this, 0, 0);
        this.clawsRight.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.clawsRight.addBox(-3.0F, 10.0F, -2.0F, 4, 3, 4, 0.0F);
        this.clawsLeft = new ModelRenderer(this, 0, 7);
        this.clawsLeft.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.clawsLeft.addBox(-1.4F, 10.0F, -2.0F, 4, 3, 4, 0.0F);
    }

    @Nullable
    @Override
    public ModelRenderer getModelRenderer() {
        return null;
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStackIn, @Nonnull IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.earRight.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.earLeft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.clawsRight.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.clawsLeft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    public void setRotationAngles(@Nonnull T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.earLeft.copyModelAngles(this.bipedHead);
        this.earRight.copyModelAngles(this.bipedHead);
        this.clawsLeft.copyModelAngles(this.bipedLeftArm);
        this.clawsRight.copyModelAngles(this.bipedRightArm);

        if (this.isSneak) {
            this.earLeft.rotationPointY = 4.2F;
            this.earRight.rotationPointY = 4.2F;
        } else {
            this.earLeft.rotationPointY = 0.0F;
            this.earRight.rotationPointY = 0.0F;
        }

    }

    @SuppressWarnings("SameParameterValue")
    private void setRotateAngle(ModelRenderer ModelRenderer, float x, float y, float z) {
        ModelRenderer.rotateAngleX = x;
        ModelRenderer.rotateAngleY = y;
        ModelRenderer.rotateAngleZ = z;
    }

}
