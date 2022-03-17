package de.teamlapen.werewolves.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;

/**
 * Model made by Rebel
 * Created using Tabula 7.1.0
 */
public class WerewolfEntityExtrasModel<T extends LivingEntity> extends EntityModel<T> {
    public ModelPart clawsLeft;
    public ModelPart clawsRight;
    public ModelPart earRight;
    public ModelPart earRight_1;

    public WerewolfEntityExtrasModel() {
        this.texWidth = 64;
        this.texHeight = 32;
        this.earRight = new ModelPart(this, 16, 0);
        this.earRight.setPos(0.0F, 0.0F, 0.0F);
        this.earRight.addBox(-4.5F, -8.0F, -2.5F, 1, 6, 3, 0.0F);
        this.setRotateAngle(earRight, -0.4886921905584123F, -0.2617993877991494F, 0.0F);
        this.earRight_1 = new ModelPart(this, 16, 9);
        this.earRight_1.setPos(0.0F, 0.0F, 0.0F);
        this.earRight_1.addBox(3.5F, -8.0F, -2.5F, 1, 6, 3, 0.0F);
        this.setRotateAngle(earRight_1, -0.4886921905584123F, 0.2617993877991494F, 0.0F);
        this.clawsRight = new ModelPart(this, 0, 0);
        this.clawsRight.setPos(-5.0F, 2.0F, 0.0F);
        this.clawsRight.addBox(-3.0F, 10.0F, -2.0F, 4, 3, 4, 0.0F);
        this.clawsLeft = new ModelPart(this, 0, 7);
        this.clawsLeft.setPos(5.0F, 2.0F, 0.0F);
        this.clawsLeft.addBox(-1.0F, 10.0F, -2.0F, 4, 3, 4, 0.0F);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.earRight.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.earRight_1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.clawsRight.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.clawsLeft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelPart ModelRenderer, float x, float y, float z) {
        ModelRenderer.xRot = x;
        ModelRenderer.yRot = y;
        ModelRenderer.zRot = z;
    }
}
