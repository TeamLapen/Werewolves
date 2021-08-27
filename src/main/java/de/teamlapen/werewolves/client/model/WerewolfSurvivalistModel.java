package de.teamlapen.werewolves.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * WerewolfSurvivalistModel - Rebel
 * Created using Tabula 7.1.0
 */
@OnlyIn(Dist.CLIENT)
public class WerewolfSurvivalistModel<T extends LivingEntity> extends WerewolfBaseModel<T> {
    public ModelRenderer body;
    public ModelRenderer hip;
    public ModelRenderer neck;
    public ModelRenderer armLeft;
    public ModelRenderer armRight;
    public ModelRenderer bodyFluff;
    public ModelRenderer legLeft;
    public ModelRenderer legRight;
    public ModelRenderer tail;
    public ModelRenderer legLeft2;
    public ModelRenderer footLeft;
    public ModelRenderer legRight2;
    public ModelRenderer footRight;
    public ModelRenderer tail2;
    public ModelRenderer tail3;
    public ModelRenderer joint;
    public ModelRenderer neckFluff;
    public ModelRenderer neckFluffLeft;
    public ModelRenderer neckFluffRight;
    public ModelRenderer neckFluffBottom;
    public ModelRenderer head;
    public ModelRenderer earLeft;
    public ModelRenderer earRight;
    public ModelRenderer snout;
    public ModelRenderer jaw;
    public ModelRenderer headFluff;
    public ModelRenderer headSidburnLeft;
    public ModelRenderer headSidburnRight;
    public ModelRenderer nose;
    public ModelRenderer snoutTeeth;
    public ModelRenderer jawTeeth;
    public ModelRenderer jawFluff;
    public ModelRenderer armLeft2;
    public ModelRenderer footLeft_1;
    public ModelRenderer armRight2;
    public ModelRenderer footLeft_2;

    public WerewolfSurvivalistModel() {
        this.texWidth = 128;
        this.texHeight = 64;
        this.snout = new ModelRenderer(this, 34, 0);
        this.snout.setPos(0.0F, 2.0F, -5.0F);
        this.snout.addBox(-2.0F, -2.0F, -4.0F, 4, 2, 4, 0.0F);
        this.footLeft_1 = new ModelRenderer(this, 24, 54);
        this.footLeft_1.mirror = true;
        this.footLeft_1.setPos(0.0F, 7.0F, -0.5F);
        this.footLeft_1.addBox(-2.0F, -1.5F, -4.0F, 4, 3, 6, 0.0F);
        this.snoutTeeth = new ModelRenderer(this, 34, 6);
        this.snoutTeeth.setPos(0.0F, 0.0F, 0.0F);
        this.snoutTeeth.addBox(-2.0F, 0.0F, -4.0F, 4, 2, 4, 0.0F);
        this.armLeft2 = new ModelRenderer(this, 48, 15);
        this.armLeft2.mirror = true;
        this.armLeft2.setPos(2.0F, 5.0F, 0.0F);
        this.armLeft2.addBox(-1.5F, 0.0F, -2.0F, 3, 8, 4, 0.0F);
        this.setRotateAngle(armLeft2, -0.3490658503988659F, 0.0F, 0.0F);
        this.bodyFluff = new ModelRenderer(this, 82, 14);
        this.bodyFluff.setPos(0.0F, 0.0F, 0.0F);
        this.bodyFluff.addBox(-4.5F, 8.0F, -4.0F, 9, 3, 8, 0.0F);
        this.legRight = new ModelRenderer(this, 24, 39);
        this.legRight.setPos(-0.5F, 8.0F, 0.0F);
        this.legRight.addBox(-4.0F, -2.0F, -3.0F, 4, 8, 6, 0.0F);
        this.setRotateAngle(legRight, -1.5707963267948966F, 0.0F, 0.0F);
        this.armLeft = new ModelRenderer(this, 36, 25);
        this.armLeft.mirror = true;
        this.armLeft.setPos(1.5F, 3.0F, -1.5F);
        this.armLeft.addBox(0.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(armLeft, -1.2217304763960306F, 0.0F, 0.0F);
        this.legLeft2 = new ModelRenderer(this, 44, 44);
        this.legLeft2.mirror = true;
        this.legLeft2.setPos(0.0F, 0.0F, 0.0F);
        this.legLeft2.addBox(0.5F, 2.0F, 1.0F, 3, 10, 4, 0.0F);
        this.tail3 = new ModelRenderer(this, 62, 49);
        this.tail3.setPos(0.0F, 7.8F, 0.0F);
        this.tail3.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
        this.setRotateAngle(tail3, 0.136659280431156F, 0.0F, 0.0F);
        this.headSidburnLeft = new ModelRenderer(this, 32, 12);
        this.headSidburnLeft.setPos(0.0F, 0.0F, 0.0F);
        this.headSidburnLeft.addBox(1.0F, -1.0F, -5.0F, 3, 6, 0, 0.0F);
        this.setRotateAngle(headSidburnLeft, 0.0F, -0.5235987755982988F, 0.0F);
        this.neckFluffRight = new ModelRenderer(this, 82, 25);
        this.neckFluffRight.setPos(0.0F, 0.0F, 0.0F);
        this.neckFluffRight.addBox(-3.5F, -5.5F, 3.0F, 6, 8, 3, 0.0F);
        this.setRotateAngle(neckFluffRight, 0.3490658503988659F, -1.5707963267948966F, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setPos(0.0F, 0.0F, 1.0F);
        this.head.addBox(-3.5F, -3.5F, -5.0F, 7, 7, 6, 0.0F);
        this.legLeft = new ModelRenderer(this, 24, 39);
        this.legLeft.setPos(0.5F, 8.0F, 0.0F);
        this.legLeft.addBox(0.0F, -2.0F, -3.0F, 4, 8, 6, 0.0F);
        this.setRotateAngle(legLeft, -1.5707963267948966F, 0.0F, 0.0F);
        this.footLeft = new ModelRenderer(this, 24, 54);
        this.footLeft.mirror = true;
        this.footLeft.setPos(2.0F, 12.0F, 2.5F);
        this.footLeft.addBox(-2.0F, -1.5F, -4.0F, 4, 3, 6, 0.0F);
        this.joint = new ModelRenderer(this, 0, 0);
        this.joint.setPos(0.0F, -6.5F, -1.0F);
        this.joint.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(joint, -1.2217304763960306F, 0.0F, 0.0F);
        this.jaw = new ModelRenderer(this, 50, 0);
        this.jaw.setPos(0.0F, 1.5F, -5.0F);
        this.jaw.addBox(-1.5F, 0.0F, -3.5F, 3, 2, 4, 0.0F);
        this.setRotateAngle(jaw, 0.4553564018453205F, 0.0F, 0.0F);
        this.headFluff = new ModelRenderer(this, 96, 0);
        this.headFluff.setPos(0.0F, 0.0F, 0.0F);
        this.headFluff.addBox(-3.5F, 3.5F, -5.0F, 7, 2, 6, 0.0F);
        this.legRight2 = new ModelRenderer(this, 44, 44);
        this.legRight2.setPos(0.0F, 0.0F, 0.0F);
        this.legRight2.addBox(-3.5F, 2.0F, 1.0F, 3, 10, 4, 0.0F);
        this.earRight = new ModelRenderer(this, 26, 0);
        this.earRight.setPos(0.0F, 0.0F, 0.0F);
        this.earRight.addBox(-3.5F, -6.5F, -1.0F, 2, 3, 1, 0.0F);
        this.headSidburnRight = new ModelRenderer(this, 32, 12);
        this.headSidburnRight.mirror = true;
        this.headSidburnRight.setPos(0.0F, 0.0F, 0.0F);
        this.headSidburnRight.addBox(-4.0F, -1.0F, -5.0F, 3, 6, 0, 0.0F);
        this.setRotateAngle(headSidburnRight, 0.0F, 0.5235987755982988F, 0.0F);
        this.jawFluff = new ModelRenderer(this, 96, 8);
        this.jawFluff.setPos(0.0F, 0.0F, 0.0F);
        this.jawFluff.addBox(-1.5F, 2.0F, -3.5F, 3, 2, 4, 0.0F);
        this.footRight = new ModelRenderer(this, 24, 54);
        this.footRight.setPos(-2.0F, 12.0F, 2.5F);
        this.footRight.addBox(-2.0F, -1.5F, -4.0F, 4, 3, 6, 0.0F);
        this.neckFluff = new ModelRenderer(this, 64, 19);
        this.neckFluff.setPos(0.0F, 0.0F, 0.0F);
        this.neckFluff.addBox(-3.5F, -5.0F, 2.5F, 7, 8, 2, 0.0F);
        this.setRotateAngle(neckFluff, 0.2617993877991494F, 0.0F, 0.0F);
        this.tail2 = new ModelRenderer(this, 62, 37);
        this.tail2.setPos(0.0F, 3.0F, 0.0F);
        this.tail2.addBox(-2.0F, 0.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(tail2, -0.3490658503988659F, 0.0F, 0.0F);
        this.armRight2 = new ModelRenderer(this, 48, 15);
        this.armRight2.setPos(-2.0F, 5.0F, 0.0F);
        this.armRight2.addBox(-1.5F, 0.0F, -2.0F, 3, 8, 4, 0.0F);
        this.setRotateAngle(armRight2, -0.3490658503988659F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 13);
        this.body.setPos(0.0F, 9.5F, -2.5F);
        this.body.addBox(-4.5F, 0.0F, -4.0F, 9, 8, 8, 0.0F);
        this.setRotateAngle(body, 1.5707963267948966F, 0.0F, 0.0F);
        this.neck = new ModelRenderer(this, 0, 45);
        this.neck.setPos(0.0F, 1.5F, 1.0F);
        this.neck.addBox(-3.0F, -6.0F, -3.0F, 6, 7, 6, 0.0F);
        this.setRotateAngle(neck, -0.3141592653589793F, 0.0F, 0.0F);
        this.neckFluffLeft = new ModelRenderer(this, 82, 25);
        this.neckFluffLeft.setPos(0.0F, 0.0F, 0.0F);
        this.neckFluffLeft.addBox(-2.5F, -5.5F, 3.0F, 6, 8, 3, 0.0F);
        this.setRotateAngle(neckFluffLeft, 0.3490658503988659F, 1.5707963267948966F, 0.0F);
        this.armRight = new ModelRenderer(this, 36, 25);
        this.armRight.setPos(-1.5F, 3.0F, -1.5F);
        this.armRight.addBox(-4.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(armRight, -1.2217304763960306F, 0.0F, 0.0F);
        this.neckFluffBottom = new ModelRenderer(this, 80, 0);
        this.neckFluffBottom.setPos(0.0F, 0.0F, 0.0F);
        this.neckFluffBottom.addBox(-3.0F, -0.6F, 4.5F, 6, 6, 2, 0.0F);
        this.setRotateAngle(neckFluffBottom, 1.0471975511965976F, 3.141592653589793F, 0.0F);
        this.earLeft = new ModelRenderer(this, 26, 0);
        this.earLeft.mirror = true;
        this.earLeft.setPos(0.0F, 0.0F, 0.0F);
        this.earLeft.addBox(1.5F, -6.5F, -1.0F, 2, 3, 1, 0.0F);
        this.hip = new ModelRenderer(this, 0, 30);
        this.hip.setPos(0.0F, 6.0F, -0.9F);
        this.hip.addBox(-3.5F, 0.0F, -3.0F, 7, 9, 6, 0.0F);
        this.nose = new ModelRenderer(this, 64, 0);
        this.nose.setPos(0.0F, 0.0F, 0.0F);
        this.nose.addBox(-1.5F, -3.5F, -3.7F, 3, 2, 5, 0.0F);
        this.setRotateAngle(nose, 0.17453292519943295F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, 62, 30);
        this.tail.setPos(0.0F, 7.0F, 2.0F);
        this.tail.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
        this.setRotateAngle(tail, -0.22759093446006054F, 0.0F, 0.0F);
        this.jawTeeth = new ModelRenderer(this, 50, 6);
        this.jawTeeth.setPos(0.0F, 0.0F, 0.0F);
        this.jawTeeth.addBox(-1.5F, -1.0F, -3.5F, 3, 1, 4, 0.0F);
        this.footLeft_2 = new ModelRenderer(this, 24, 54);
        this.footLeft_2.setPos(0.0F, 7.0F, -0.5F);
        this.footLeft_2.addBox(-2.0F, -1.5F, -4.0F, 4, 3, 6, 0.0F);
        this.head.addChild(this.snout);
        this.armLeft2.addChild(this.footLeft_1);
        this.snout.addChild(this.snoutTeeth);
        this.armLeft.addChild(this.armLeft2);
        this.body.addChild(this.bodyFluff);
        this.hip.addChild(this.legRight);
        this.body.addChild(this.armLeft);
        this.legLeft.addChild(this.legLeft2);
        this.tail2.addChild(this.tail3);
        this.head.addChild(this.headSidburnLeft);
        this.neck.addChild(this.neckFluffRight);
        this.joint.addChild(this.head);
        this.hip.addChild(this.legLeft);
        this.legLeft2.addChild(this.footLeft);
        this.neck.addChild(this.joint);
        this.head.addChild(this.jaw);
        this.head.addChild(this.headFluff);
        this.legRight.addChild(this.legRight2);
        this.head.addChild(this.earRight);
        this.head.addChild(this.headSidburnRight);
        this.jaw.addChild(this.jawFluff);
        this.legRight2.addChild(this.footRight);
        this.neck.addChild(this.neckFluff);
        this.tail.addChild(this.tail2);
        this.armRight.addChild(this.armRight2);
        this.body.addChild(this.neck);
        this.neck.addChild(this.neckFluffLeft);
        this.body.addChild(this.armRight);
        this.neck.addChild(this.neckFluffBottom);
        this.head.addChild(this.earLeft);
        this.body.addChild(this.hip);
        this.snout.addChild(this.nose);
        this.hip.addChild(this.tail);
        this.jaw.addChild(this.jawTeeth);
        this.armRight2.addChild(this.footLeft_2);
    }

    @Nullable
    @Override
    public ModelRenderer getModelRenderer() {
        return this.body;
    }

    @Nullable
    @Override
    public ModelRenderer getHeadModel() {
        return this.head;
    }

    @Nullable
    @Override
    public ModelRenderer getLeftArmModel() {
        return this.armLeft;
    }

    @Nullable
    @Override
    public ModelRenderer getRightArmModel() {
        return this.armRight;
    }

    @Override
    public void renderToBuffer(@Nonnull MatrixStack matrixStackIn, @Nonnull IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        boolean flag1 = entityIn.isVisuallySwimming();
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        if (this.swimAmount > 0.0f) {
            if (flag1) {
                this.head.xRot = this.rotlerpRad(this.head.xRot, (-(float) Math.PI / 4F), this.swimAmount);
            } else {
                this.head.xRot = this.rotlerpRad(this.head.xRot, headPitch * ((float) Math.PI / 180F), this.swimAmount);
            }
        } else {
            this.head.xRot = headPitch * ((float) Math.PI / 180F);
        }


        this.legRight.xRot = -1.5707963267948966F;
        this.legLeft.xRot = -1.5707963267948966F;

        this.armRight.xRot = -1.2217304763960306F;
        this.armLeft.xRot = -1.2217304763960306F;

        this.legRight.xRot += MathHelper.cos(limbSwing * 0.6662F * 0.8f + 1.5f * (float) Math.PI) * 0.8F * limbSwingAmount;
        this.legLeft.xRot += MathHelper.cos(limbSwing * 0.6662F * 0.8f + 0.5f * (float) Math.PI) * 0.8F * limbSwingAmount;

        this.armRight.xRot += MathHelper.cos(limbSwing * 0.6662F * 0.8f) * 0.8F * limbSwingAmount;
        this.armLeft.xRot += MathHelper.cos(limbSwing * 0.6662F * 0.8f + (float) Math.PI) * 0.8F * limbSwingAmount;


        //reset tail rotation angle
        this.tail.xRot = -0.22759093446006054F;
        this.tail.yRot = 0F;

        //idle rotations
        this.tail.xRot -= 0.035f;
        this.tail.xRot += MathHelper.cos(ageInTicks * 0.10F) * 0.07F;

        this.tail.yRot = -0.035F;
        this.tail.yRot += MathHelper.sin(ageInTicks * 0.14F) * 0.07f;

        //running tail animation
        this.tail.xRot += MathHelper.cos(limbSwing * 0.6662F * 0.7f) * 0.3F * MathHelper.abs(limbSwingAmount) + 0.3f;
        this.tail.yRot += MathHelper.sin(limbSwing * 0.6662F * 0.7f) * 0.1F * limbSwingAmount;
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer ModelRenderer, float x, float y, float z) {
        ModelRenderer.xRot = x;
        ModelRenderer.yRot = y;
        ModelRenderer.zRot = z;
    }

}
