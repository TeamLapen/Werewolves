package de.teamlapen.werewolves.client.model;

import com.google.common.collect.ImmutableList;
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
    public ModelRenderer dummyBipedHead;
    public ModelRenderer clawsLeft;
    public ModelRenderer clawsRight;
    public ModelRenderer earRight;
    public ModelRenderer earLeft;

    public WerewolfEarsModel() {
        this.texWidth = 64;
        this.texHeight = 32;
        this.dummyBipedHead = new ModelRenderer(this, 0, 0);
        this.dummyBipedHead.copyFrom(this.head);

        this.earRight = new ModelRenderer(this, 16, 0);
        this.earRight.addBox(-4.5F, -8.0F, -2.5F, 1, 6, 3, 0.0F);
        this.setRotateAngle(earRight, -0.4886921905584123F, -0.2617993877991494F, 0.0F);
        this.dummyBipedHead.addChild(this.earRight);

        this.earLeft = new ModelRenderer(this, 16, 9);
        this.earLeft.addBox(3.5F, -8.0F, -2.5F, 1, 6, 3, 0.0F);
        this.setRotateAngle(earLeft, -0.4886921905584123F, 0.2617993877991494F, 0.0F);
        this.dummyBipedHead.addChild(this.earLeft);

        this.clawsRight = new ModelRenderer(this, 0, 0);
        this.clawsRight.addBox(-3.0F, 10.0F, -2.0F, 4, 3, 4, 0.0F);
        this.rightArm.addChild(this.clawsRight);

        this.clawsLeft = new ModelRenderer(this, 0, 7);
        this.clawsLeft.addBox(-1F, 10.0F, -2.0F, 4, 3, 4, 0.0F);
        this.leftArm.addChild(this.clawsLeft);
    }

    @Nullable
    @Override
    public ModelRenderer getModelRenderer() {
        return null;
    }

    @Nullable
    @Override
    public ModelRenderer getHeadModel() {
        return this.dummyBipedHead;
    }

    @Nullable
    @Override
    public ModelRenderer getLeftArmModel() {
        return this.clawsLeft;
    }

    @Nullable
    @Override
    public ModelRenderer getRightArmModel() {
        return this.clawsRight;
    }

    @Nonnull
    @Override
    protected Iterable<ModelRenderer> headParts() {
        return ImmutableList.of(this.dummyBipedHead);
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(this.rightArm, this.leftArm);
    }

    @Override
    public void renderToBuffer(@Nonnull MatrixStack matrixStackIn, @Nonnull IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.dummyBipedHead.copyFrom(this.playerModel.head);
        this.rightArm.copyFrom(this.playerModel.rightArm);
        this.leftArm.copyFrom(this.playerModel.leftArm);
        super.renderToBuffer(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @SuppressWarnings("SameParameterValue")
    private void setRotateAngle(ModelRenderer ModelRenderer, float x, float y, float z) {
        ModelRenderer.xRot = x;
        ModelRenderer.yRot = y;
        ModelRenderer.zRot = z;
    }

}
