package de.teamlapen.werewolves.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Model made by Rebel
 * Created using Tabula 7.1.0
 */
@OnlyIn(Dist.CLIENT)
public class Werewolf4LModel<T extends LivingEntity> extends WerewolfBaseModel<T> {
    public ModelPart body;
    public ModelPart hip;
    public ModelPart neck;
    public ModelPart armLeft;
    public ModelPart armRight;
    public ModelPart bodyFluff;
    public ModelPart legLeft;
    public ModelPart legRight;
    public ModelPart tail1;
    public ModelPart legLeft2;
    public ModelPart footLeft;
    public ModelPart legRight2;
    public ModelPart footRight;
    public ModelPart tail2;
    public ModelPart tail3;
    public ModelPart joint;
    public ModelPart neckFluff;
    public ModelPart neckFluffLeft;
    public ModelPart neckFluffRight;
    public ModelPart neckFluffBottom;
    public ModelPart head;
    public ModelPart earLeft;
    public ModelPart earRight;
    public ModelPart snout;
    public ModelPart jaw;
    public ModelPart headFluff;
    public ModelPart headSidburnLeft;
    public ModelPart headSidburnRight;
    public ModelPart earLeft2;
    public ModelPart earRight2;
    public ModelPart nose;
    public ModelPart snoutTeeth;
    public ModelPart jawTeeth;
    public ModelPart jawFluff;
    public ModelPart armLeft2;
    public ModelPart fingerLeft;
    public ModelPart fingerLeft2;
    public ModelPart fingerLeft3;
    public ModelPart fingerLeft4;
    public ModelPart thumbLeft;
    public ModelPart armRight2;
    public ModelPart fingerRight;
    public ModelPart fingerRight2;
    public ModelPart fingerRight3;
    public ModelPart fingerRight4;
    public ModelPart thumbRight;

    public Werewolf4LModel() {
        this.texWidth = 128;
        this.texHeight = 64;
        this.legRight = new ModelPart(this, 24, 39);
        this.legRight.setPos(-0.5F, 9.0F, 0.0F);
        this.legRight.addBox(-4.0F, -2.0F, -3.0F, 4, 8, 6, 0.0F);
        this.setRotateAngle(legRight, -1.3089969389957472F, 0.0F, 0.0F);
        this.armRight = new ModelPart(this, 36, 25);
        this.armRight.setPos(-4.5F, 3.0F, 0.0F);
        this.armRight.addBox(-4.0F, -2.0F, -2.0F, 4, 10, 4, 0.0F);
        this.setRotateAngle(armRight, -1.3962634015954636F, 0.0F, 0.08726646259971647F);
        this.headSidburnRight = new ModelPart(this, 32, 12);
        this.headSidburnRight.mirror = true;
        this.headSidburnRight.setPos(0.0F, 0.0F, 0.0F);
        this.headSidburnRight.addBox(-4.0F, -1.0F, -5.0F, 3, 6, 0, 0.0F);
        this.setRotateAngle(headSidburnRight, 0.0F, 0.5235987755982988F, 0.0F);
        this.fingerLeft4 = new ModelPart(this, 0, 13);
        this.fingerLeft4.setPos(1.2F, 9.0F, 1.5F);
        this.fingerLeft4.addBox(-1.5F, 0.0F, -0.5F, 2, 3, 1, 0.0F);
        this.setRotateAngle(fingerLeft4, 0.2617993877991494F, 0.0F, -0.17453292519943295F);
        this.snout = new ModelPart(this, 34, 0);
        this.snout.setPos(0.0F, 2.0F, -5.0F);
        this.snout.addBox(-2.0F, -2.0F, -4.0F, 4, 2, 4, 0.0F);
        this.setRotateAngle(snout, -0.08726646259971647F, 0.0F, 0.0F);
        this.tail2 = new ModelPart(this, 62, 37);
        this.tail2.setPos(0.0F, 3.0F, 0.0F);
        this.tail2.addBox(-2.0F, 0.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(tail2, -0.3490658503988659F, 0.0F, 0.0F);
        this.fingerLeft3 = new ModelPart(this, 0, 13);
        this.fingerLeft3.setPos(1.2F, 9.0F, 0.5F);
        this.fingerLeft3.addBox(-1.5F, 0.0F, -0.5F, 2, 3, 1, 0.0F);
        this.setRotateAngle(fingerLeft3, 0.08726646259971647F, 0.0F, -0.17453292519943295F);
        this.neckFluff = new ModelPart(this, 64, 19);
        this.neckFluff.setPos(0.0F, 0.0F, 0.0F);
        this.neckFluff.addBox(-3.5F, -5.0F, 2.5F, 7, 8, 2, 0.0F);
        this.setRotateAngle(neckFluff, 0.2617993877991494F, 0.0F, 0.0F);
        this.headSidburnLeft = new ModelPart(this, 32, 12);
        this.headSidburnLeft.setPos(0.0F, 0.0F, 0.0F);
        this.headSidburnLeft.addBox(1.0F, -1.0F, -5.0F, 3, 6, 0, 0.0F);
        this.setRotateAngle(headSidburnLeft, 0.0F, -0.5235987755982988F, 0.0F);
        this.armLeft = new ModelPart(this, 36, 25);
        this.armLeft.mirror = true;
        this.armLeft.setPos(4.5F, 3.0F, 0.0F);
        this.armLeft.addBox(0.0F, -2.0F, -2.0F, 4, 10, 4, 0.0F);
        this.setRotateAngle(armLeft, -1.3962634015954636F, 0.0F, -0.08726646259971647F);
        this.neckFluffLeft = new ModelPart(this, 82, 25);
        this.neckFluffLeft.setPos(0.0F, 0.0F, 0.0F);
        this.neckFluffLeft.addBox(-2.5F, -5.5F, 3.0F, 6, 8, 3, 0.0F);
        this.setRotateAngle(neckFluffLeft, 0.3490658503988659F, 1.5707963267948966F, 0.0F);
        this.thumbLeft = new ModelPart(this, 0, 17);
        this.thumbLeft.setPos(-1.2F, 9.0F, -1.5F);
        this.thumbLeft.addBox(-0.5F, 0.0F, -0.5F, 2, 2, 1, 0.0F);
        this.setRotateAngle(thumbLeft, -0.2617993877991494F, 0.0F, 0.17453292519943295F);
        this.neckFluffRight = new ModelPart(this, 82, 25);
        this.neckFluffRight.setPos(0.0F, 0.0F, 0.0F);
        this.neckFluffRight.addBox(-3.5F, -5.5F, 3.0F, 6, 8, 3, 0.0F);
        this.setRotateAngle(neckFluffRight, 0.3490658503988659F, -1.5707963267948966F, 0.0F);
        this.nose = new ModelPart(this, 64, 0);
        this.nose.setPos(0.0F, 0.0F, 0.0F);
        this.nose.addBox(-1.5F, -3.5F, -3.7F, 3, 2, 5, 0.0F);
        this.setRotateAngle(nose, 0.17453292519943295F, 0.0F, 0.0F);
        this.armRight2 = new ModelPart(this, 48, 15);
        this.armRight2.setPos(-2.0F, 7.0F, 0.0F);
        this.armRight2.addBox(-1.5F, 0.0F, -2.0F, 3, 10, 4, 0.0F);
        this.setRotateAngle(armRight2, -0.8726646259971648F, 0.0F, 0.0F);
        this.footRight = new ModelPart(this, 24, 54);
        this.footRight.setPos(-2.0F, 12.0F, 5.0F);
        this.footRight.addBox(-2.0F, -1.5F, -4.0F, 4, 3, 6, 0.0F);
        this.setRotateAngle(footRight, 0.08726646259971647F, 0.0F, 0.0F);
        this.head = new ModelPart(this, 0, 0);
        this.head.setPos(0.0F, 0.0F, 1.0F);
        this.head.addBox(-3.5F, -3.5F, -5.0F, 7, 7, 6, 0.0F);
        this.body = new ModelPart(this, 0, 13);
        this.body.setPos(0.0F, 9.0F, -2.5F);
        this.body.addBox(-4.5F, 0.0F, -4.0F, 9, 8, 8, 0.0F);
        this.setRotateAngle(body, 1.7453292519943295F, 0.0F, 0.0F);
        this.legLeft = new ModelPart(this, 24, 39);
        this.legLeft.setPos(0.5F, 9.0F, 0.0F);
        this.legLeft.addBox(0.0F, -2.0F, -3.0F, 4, 8, 6, 0.0F);
        this.setRotateAngle(legLeft, -1.3089969389957472F, 0.0F, 0.0F);
        this.fingerLeft2 = new ModelPart(this, 0, 13);
        this.fingerLeft2.setPos(1.2F, 9.0F, -0.5F);
        this.fingerLeft2.addBox(-1.5F, 0.0F, -0.5F, 2, 3, 1, 0.0F);
        this.setRotateAngle(fingerLeft2, -0.08726646259971647F, 0.0F, -0.17453292519943295F);
        this.legLeft2 = new ModelPart(this, 44, 44);
        this.legLeft2.mirror = true;
        this.legLeft2.setPos(0.0F, 0.0F, 0.0F);
        this.legLeft2.addBox(0.5F, 2.0F, 3.0F, 3, 10, 4, 0.0F);
        this.armLeft2 = new ModelPart(this, 48, 15);
        this.armLeft2.mirror = true;
        this.armLeft2.setPos(2.0F, 7.0F, 0.0F);
        this.armLeft2.addBox(-1.5F, 0.0F, -2.0F, 3, 10, 4, 0.0F);
        this.setRotateAngle(armLeft2, -0.8726646259971648F, 0.0F, 0.0F);
        this.fingerRight = new ModelPart(this, 0, 13);
        this.fingerRight.mirror = true;
        this.fingerRight.setPos(-1.2F, 9.0F, -1.5F);
        this.fingerRight.addBox(-0.5F, 0.0F, -0.5F, 2, 3, 1, 0.0F);
        this.setRotateAngle(fingerRight, -0.2617993877991494F, 0.0F, 0.17453292519943295F);
        this.fingerRight3 = new ModelPart(this, 0, 13);
        this.fingerRight3.mirror = true;
        this.fingerRight3.setPos(-1.2F, 9.0F, 0.5F);
        this.fingerRight3.addBox(-0.5F, 0.0F, -0.5F, 2, 3, 1, 0.0F);
        this.setRotateAngle(fingerRight3, 0.08726646259971647F, 0.0F, 0.17453292519943295F);
        this.legRight2 = new ModelPart(this, 44, 44);
        this.legRight2.setPos(0.0F, 0.0F, 0.0F);
        this.legRight2.addBox(-3.5F, 2.0F, 3.0F, 3, 10, 4, 0.0F);
        this.tail1 = new ModelPart(this, 62, 30);
        this.tail1.setPos(0.0F, 7.0F, 2.0F);
        this.tail1.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
        this.setRotateAngle(tail1, 0.045553093477052F, 0.0F, 0.0F);
        this.earRight = new ModelPart(this, 26, 0);
        this.earRight.setPos(0.0F, 0.0F, 0.0F);
        this.earRight.addBox(-4.5F, -2.5F, -2.5F, 1, 4, 3, 0.0F);
        this.setRotateAngle(earRight, -0.4886921905584123F, -0.3490658503988659F, 0.0F);
        this.thumbRight = new ModelPart(this, 0, 17);
        this.thumbRight.mirror = true;
        this.thumbRight.setPos(1.2F, 9.0F, -1.5F);
        this.thumbRight.addBox(-1.5F, 0.0F, -0.5F, 2, 2, 1, 0.0F);
        this.setRotateAngle(thumbRight, -0.2617993877991494F, 0.0F, -0.17453292519943295F);
        this.neckFluffBottom = new ModelPart(this, 80, 0);
        this.neckFluffBottom.setPos(0.0F, 0.0F, 0.0F);
        this.neckFluffBottom.addBox(-3.0F, -0.6F, 4.5F, 6, 6, 2, 0.0F);
        this.setRotateAngle(neckFluffBottom, 1.0471975511965976F, 3.141592653589793F, 0.0F);
        this.earLeft2 = new ModelPart(this, 26, 7);
        this.earLeft2.setPos(0.0F, 0.0F, 0.0F);
        this.earLeft2.addBox(3.0F, -5.5F, -2.0F, 1, 4, 2, 0.0F);
        this.setRotateAngle(earLeft2, 0.0F, 0.0F, 0.17453292519943295F);
        this.jawTeeth = new ModelPart(this, 50, 6);
        this.jawTeeth.setPos(0.0F, 0.0F, 0.0F);
        this.jawTeeth.addBox(-1.5F, -1.0F, -3.5F, 3, 1, 4, 0.0F);
        this.jaw = new ModelPart(this, 50, 0);
        this.jaw.setPos(0.0F, 1.5F, -5.0F);
        this.jaw.addBox(-1.5F, 0.0F, -3.5F, 3, 2, 4, 0.0F);
        this.setRotateAngle(jaw, 1.2217304763960306F, 0.0F, 0.0F);
        this.tail3 = new ModelPart(this, 62, 49);
        this.tail3.setPos(0.0F, 7.8F, 0.0F);
        this.tail3.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
        this.setRotateAngle(tail3, 0.136659280431156F, 0.0F, 0.0F);
        this.fingerLeft = new ModelPart(this, 0, 13);
        this.fingerLeft.setPos(1.2F, 9.0F, -1.5F);
        this.fingerLeft.addBox(-1.5F, 0.0F, -0.5F, 2, 3, 1, 0.0F);
        this.setRotateAngle(fingerLeft, -0.2617993877991494F, 0.0F, -0.17453292519943295F);
        this.neck = new ModelPart(this, 0, 45);
        this.neck.setPos(0.0F, 1.5F, 1.0F);
        this.neck.addBox(-3.0F, -6.0F, -3.0F, 6, 7, 6, 0.0F);
        this.setRotateAngle(neck, -0.45378560551852565F, 0.0F, 0.0F);
        this.joint = new ModelPart(this, 0, 0);
        this.joint.setPos(0.0F, -6.5F, -1.0F);
        this.joint.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(joint, -1.2217304763960306F, 0.0F, 0.0F);
        this.fingerRight2 = new ModelPart(this, 0, 13);
        this.fingerRight2.mirror = true;
        this.fingerRight2.setPos(-1.2F, 9.0F, -0.5F);
        this.fingerRight2.addBox(-0.5F, 0.0F, -0.5F, 2, 3, 1, 0.0F);
        this.setRotateAngle(fingerRight2, -0.08726646259971647F, 0.0F, 0.17453292519943295F);
        this.headFluff = new ModelPart(this, 96, 0);
        this.headFluff.setPos(0.0F, 0.0F, 0.0F);
        this.headFluff.addBox(-3.5F, 3.5F, -5.0F, 7, 2, 6, 0.0F);
        this.earRight2 = new ModelPart(this, 26, 7);
        this.earRight2.setPos(0.0F, 0.0F, 0.0F);
        this.earRight2.addBox(-4.0F, -5.5F, -2.0F, 1, 4, 2, 0.0F);
        this.setRotateAngle(earRight2, 0.0F, 0.0F, -0.17453292519943295F);
        this.bodyFluff = new ModelPart(this, 82, 14);
        this.bodyFluff.setPos(0.0F, 0.0F, 0.0F);
        this.bodyFluff.addBox(-4.5F, 8.0F, -4.0F, 9, 3, 8, 0.0F);
        this.earLeft = new ModelPart(this, 26, 0);
        this.earLeft.setPos(0.0F, 0.0F, 0.0F);
        this.earLeft.addBox(3.5F, -2.5F, -2.5F, 1, 4, 3, 0.0F);
        this.setRotateAngle(earLeft, -0.4886921905584123F, 0.3490658503988659F, 0.0F);
        this.footLeft = new ModelPart(this, 24, 54);
        this.footLeft.mirror = true;
        this.footLeft.setPos(2.0F, 12.0F, 5.0F);
        this.footLeft.addBox(-2.0F, -1.5F, -4.0F, 4, 3, 6, 0.0F);
        this.setRotateAngle(footLeft, 0.08726646259971647F, 0.0F, 0.0F);
        this.snoutTeeth = new ModelPart(this, 34, 6);
        this.snoutTeeth.setPos(0.0F, 0.0F, 0.0F);
        this.snoutTeeth.addBox(-2.0F, 0.0F, -4.0F, 4, 2, 4, 0.0F);
        this.jawFluff = new ModelPart(this, 96, 8);
        this.jawFluff.setPos(0.0F, 0.0F, 0.0F);
        this.jawFluff.addBox(-1.5F, 2.0F, -3.5F, 3, 2, 4, 0.0F);
        this.hip = new ModelPart(this, 0, 30);
        this.hip.setPos(0.0F, 6.0F, 1.0F);
        this.hip.addBox(-3.5F, 0.0F, -3.0F, 7, 9, 6, 0.0F);
        this.setRotateAngle(hip, -0.5235987755982988F, 0.0F, 0.0F);
        this.fingerRight4 = new ModelPart(this, 0, 13);
        this.fingerRight4.mirror = true;
        this.fingerRight4.setPos(-1.2F, 9.0F, 1.5F);
        this.fingerRight4.addBox(-0.5F, 0.0F, -0.5F, 2, 3, 1, 0.0F);
        this.setRotateAngle(fingerRight4, 0.2617993877991494F, 0.0F, 0.17453292519943295F);
        this.hip.addChild(this.legRight);
        this.body.addChild(this.armRight);
        this.head.addChild(this.headSidburnRight);
        this.armLeft2.addChild(this.fingerLeft4);
        this.head.addChild(this.snout);
        this.tail1.addChild(this.tail2);
        this.armLeft2.addChild(this.fingerLeft3);
        this.neck.addChild(this.neckFluff);
        this.head.addChild(this.headSidburnLeft);
        this.body.addChild(this.armLeft);
        this.neck.addChild(this.neckFluffLeft);
        this.armLeft2.addChild(this.thumbLeft);
        this.neck.addChild(this.neckFluffRight);
        this.snout.addChild(this.nose);
        this.armRight.addChild(this.armRight2);
        this.legRight2.addChild(this.footRight);
        this.joint.addChild(this.head);
        this.hip.addChild(this.legLeft);
        this.armLeft2.addChild(this.fingerLeft2);
        this.legLeft.addChild(this.legLeft2);
        this.armLeft.addChild(this.armLeft2);
        this.armRight2.addChild(this.fingerRight);
        this.armRight2.addChild(this.fingerRight3);
        this.legRight.addChild(this.legRight2);
        this.hip.addChild(this.tail1);
        this.head.addChild(this.earRight);
        this.armRight2.addChild(this.thumbRight);
        this.neck.addChild(this.neckFluffBottom);
        this.earLeft.addChild(this.earLeft2);
        this.jaw.addChild(this.jawTeeth);
        this.head.addChild(this.jaw);
        this.tail2.addChild(this.tail3);
        this.armLeft2.addChild(this.fingerLeft);
        this.body.addChild(this.neck);
        this.neck.addChild(this.joint);
        this.armRight2.addChild(this.fingerRight2);
        this.head.addChild(this.headFluff);
        this.earRight.addChild(this.earRight2);
        this.body.addChild(this.bodyFluff);
        this.head.addChild(this.earLeft);
        this.legLeft2.addChild(this.footLeft);
        this.snout.addChild(this.snoutTeeth);
        this.jaw.addChild(this.jawFluff);
        this.body.addChild(this.hip);
        this.armRight2.addChild(this.fingerRight4);
    }

    @Override
    public void setupAnim(@Nonnull T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        boolean flag1 = entityIn.isVisuallySwimming();
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        if(this.swimAmount > 0.0f ) {
            if (flag1) {
                this.head.xRot = this.rotlerpRad(this.head.xRot, (-(float)Math.PI / 4F), this.swimAmount);
            } else {
                this.head.xRot = this.rotlerpRad(this.head.xRot, headPitch * ((float)Math.PI / 180F), this.swimAmount);
            }
        } else {
            this.head.xRot = headPitch * ((float) Math.PI / 180F);
        }

        this.armRight.xRot = -1.3962634015954636F;
        this.armLeft.xRot = -1.3962634015954636F;
        this.legRight.xRot = -1.3089969389957472F;
        this.legLeft.xRot = -1.3089969389957472F;

        this.armRight.xRot += Mth.cos(limbSwing * 0.6662F * 0.7f)* limbSwingAmount;
        this.armLeft.xRot += Mth.cos(limbSwing * 0.6662F * 0.7f + (float) Math.PI)  * limbSwingAmount;
        this.legRight.xRot += Mth.cos(limbSwing * 0.6662F * 0.7f)  * limbSwingAmount;
        this.legLeft.xRot += Mth.cos(limbSwing * 0.6662F * 0.7f + (float) Math.PI)  * limbSwingAmount;

        //reset tail rotation angle
        this.tail1.xRot = 0.045553093477052F;
        this.tail1.yRot = 0F;

        //idle rotations
        this.tail1.xRot -= 0.035f;
        this.tail1.xRot += Mth.cos(ageInTicks * 0.08F) * 0.07F;

        this.tail1.yRot = -0.035F;
        this.tail1.yRot += Mth.sin(ageInTicks * 0.12F) * 0.03f;

        //running tail animation
        this.tail1.xRot += Mth.cos(limbSwing * 0.6662F * 0.7f) * 0.7F * Mth.abs(limbSwingAmount * 0.6f) + 0.3f;
        this.tail1.yRot += Mth.sin(limbSwing * 0.6662F * 0.7f) * 0.1F * limbSwingAmount * 0.6f;

        this.jaw.xRot = 1.2217304763960306F;
        this.jaw.xRot += Mth.cos(ageInTicks * 0.09F + 0.02F) * 0.07F;

        this.earLeft.xRot = -0.4886921905584123F;
        this.earLeft.xRot += Mth.cos(ageInTicks * 0.06F + 0.01F) * 0.07F;

        this.earRight.xRot = -0.4886921905584123F;
        this.earRight.xRot += Mth.cos(ageInTicks * 0.06F) * 0.07F;

        if (this.attackTime > 0.0F) {
            this.jaw.xRot = 0.2f + this.attackTime * (1.2217304763960306F - 0.2f);
        }

    }

    @Override
    public void renderToBuffer(@Nonnull PoseStack matrixStackIn, @Nonnull VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelPart ModelRenderer, float x, float y, float z) {
        ModelRenderer.xRot = x;
        ModelRenderer.yRot = y;
        ModelRenderer.zRot = z;
    }

    @Nullable
    @Override
    public ModelPart getModelRenderer() {
        return this.body;
    }

    @Nullable
    @Override
    public ModelPart getHeadModel() {
        return this.head;
    }

    @Nullable
    @Override
    public ModelPart getLeftArmModel() {
        return this.leftArm;
    }

    @Nullable
    @Override
    public ModelPart getRightArmModel() {
        return this.rightArm;
    }

    @Nonnull
    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body);
    }
}
