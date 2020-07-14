package de.teamlapen.werewolves.client.model;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

/**
 * WerewolfSurvivalistModel - Rebel
 * Created using Tabula 7.1.0
 */
@OnlyIn(Dist.CLIENT)
public class WerewolfSurvivalistModel<T extends LivingEntity> extends WerewolfBaseModel<T> {
    public RendererModel body;
    public RendererModel hip;
    public RendererModel neck;
    public RendererModel armLeft;
    public RendererModel armRight;
    public RendererModel bodyFluff;
    public RendererModel legLeft;
    public RendererModel legRight;
    public RendererModel tail;
    public RendererModel legLeft2;
    public RendererModel footLeft;
    public RendererModel legRight2;
    public RendererModel footRight;
    public RendererModel tail2;
    public RendererModel tail3;
    public RendererModel joint;
    public RendererModel neckFluff;
    public RendererModel neckFluffLeft;
    public RendererModel neckFluffRight;
    public RendererModel neckFluffBottom;
    public RendererModel head;
    public RendererModel earLeft;
    public RendererModel earRight;
    public RendererModel snout;
    public RendererModel jaw;
    public RendererModel headFluff;
    public RendererModel headSidburnLeft;
    public RendererModel headSidburnRight;
    public RendererModel nose;
    public RendererModel snoutTeeth;
    public RendererModel jawTeeth;
    public RendererModel jawFluff;
    public RendererModel armLeft2;
    public RendererModel footLeft_1;
    public RendererModel armRight2;
    public RendererModel footLeft_2;

    public WerewolfSurvivalistModel() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.snout = new RendererModel(this, 34, 0);
        this.snout.setRotationPoint(0.0F, 2.0F, -5.0F);
        this.snout.addBox(-2.0F, -2.0F, -4.0F, 4, 2, 4, 0.0F);
        this.footLeft_1 = new RendererModel(this, 24, 54);
        this.footLeft_1.mirror = true;
        this.footLeft_1.setRotationPoint(0.0F, 7.0F, -0.5F);
        this.footLeft_1.addBox(-2.0F, -1.5F, -4.0F, 4, 3, 6, 0.0F);
        this.snoutTeeth = new RendererModel(this, 34, 6);
        this.snoutTeeth.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.snoutTeeth.addBox(-2.0F, 0.0F, -4.0F, 4, 2, 4, 0.0F);
        this.armLeft2 = new RendererModel(this, 48, 15);
        this.armLeft2.mirror = true;
        this.armLeft2.setRotationPoint(2.0F, 5.0F, 0.0F);
        this.armLeft2.addBox(-1.5F, 0.0F, -2.0F, 3, 8, 4, 0.0F);
        this.setRotateAngle(armLeft2, -0.3490658503988659F, 0.0F, 0.0F);
        this.bodyFluff = new RendererModel(this, 82, 14);
        this.bodyFluff.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bodyFluff.addBox(-4.5F, 8.0F, -4.0F, 9, 3, 8, 0.0F);
        this.legRight = new RendererModel(this, 24, 39);
        this.legRight.setRotationPoint(-0.5F, 8.0F, 0.0F);
        this.legRight.addBox(-4.0F, -2.0F, -3.0F, 4, 8, 6, 0.0F);
        this.setRotateAngle(legRight, -1.5707963267948966F, 0.0F, 0.0F);
        this.armLeft = new RendererModel(this, 36, 25);
        this.armLeft.mirror = true;
        this.armLeft.setRotationPoint(1.5F, 3.0F, -1.5F);
        this.armLeft.addBox(0.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(armLeft, -1.2217304763960306F, 0.0F, 0.0F);
        this.legLeft2 = new RendererModel(this, 44, 44);
        this.legLeft2.mirror = true;
        this.legLeft2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.legLeft2.addBox(0.5F, 2.0F, 1.0F, 3, 10, 4, 0.0F);
        this.tail3 = new RendererModel(this, 62, 49);
        this.tail3.setRotationPoint(0.0F, 7.8F, 0.0F);
        this.tail3.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
        this.setRotateAngle(tail3, 0.136659280431156F, 0.0F, 0.0F);
        this.headSidburnLeft = new RendererModel(this, 32, 12);
        this.headSidburnLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.headSidburnLeft.addBox(1.0F, -1.0F, -5.0F, 3, 6, 0, 0.0F);
        this.setRotateAngle(headSidburnLeft, 0.0F, -0.5235987755982988F, 0.0F);
        this.neckFluffRight = new RendererModel(this, 82, 25);
        this.neckFluffRight.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.neckFluffRight.addBox(-3.5F, -5.5F, 3.0F, 6, 8, 3, 0.0F);
        this.setRotateAngle(neckFluffRight, 0.3490658503988659F, -1.5707963267948966F, 0.0F);
        this.head = new RendererModel(this, 0, 0);
        this.head.setRotationPoint(0.0F, 0.0F, 1.0F);
        this.head.addBox(-3.5F, -3.5F, -5.0F, 7, 7, 6, 0.0F);
        this.legLeft = new RendererModel(this, 24, 39);
        this.legLeft.setRotationPoint(0.5F, 8.0F, 0.0F);
        this.legLeft.addBox(0.0F, -2.0F, -3.0F, 4, 8, 6, 0.0F);
        this.setRotateAngle(legLeft, -1.5707963267948966F, 0.0F, 0.0F);
        this.footLeft = new RendererModel(this, 24, 54);
        this.footLeft.mirror = true;
        this.footLeft.setRotationPoint(2.0F, 12.0F, 2.5F);
        this.footLeft.addBox(-2.0F, -1.5F, -4.0F, 4, 3, 6, 0.0F);
        this.joint = new RendererModel(this, 0, 0);
        this.joint.setRotationPoint(0.0F, -6.5F, -1.0F);
        this.joint.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(joint, -1.2217304763960306F, 0.0F, 0.0F);
        this.jaw = new RendererModel(this, 50, 0);
        this.jaw.setRotationPoint(0.0F, 1.5F, -5.0F);
        this.jaw.addBox(-1.5F, 0.0F, -3.5F, 3, 2, 4, 0.0F);
        this.setRotateAngle(jaw, 0.4553564018453205F, 0.0F, 0.0F);
        this.headFluff = new RendererModel(this, 96, 0);
        this.headFluff.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.headFluff.addBox(-3.5F, 3.5F, -5.0F, 7, 2, 6, 0.0F);
        this.legRight2 = new RendererModel(this, 44, 44);
        this.legRight2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.legRight2.addBox(-3.5F, 2.0F, 1.0F, 3, 10, 4, 0.0F);
        this.earRight = new RendererModel(this, 26, 0);
        this.earRight.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.earRight.addBox(-3.5F, -6.5F, -1.0F, 2, 3, 1, 0.0F);
        this.headSidburnRight = new RendererModel(this, 32, 12);
        this.headSidburnRight.mirror = true;
        this.headSidburnRight.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.headSidburnRight.addBox(-4.0F, -1.0F, -5.0F, 3, 6, 0, 0.0F);
        this.setRotateAngle(headSidburnRight, 0.0F, 0.5235987755982988F, 0.0F);
        this.jawFluff = new RendererModel(this, 96, 8);
        this.jawFluff.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.jawFluff.addBox(-1.5F, 2.0F, -3.5F, 3, 2, 4, 0.0F);
        this.footRight = new RendererModel(this, 24, 54);
        this.footRight.setRotationPoint(-2.0F, 12.0F, 2.5F);
        this.footRight.addBox(-2.0F, -1.5F, -4.0F, 4, 3, 6, 0.0F);
        this.neckFluff = new RendererModel(this, 64, 19);
        this.neckFluff.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.neckFluff.addBox(-3.5F, -5.0F, 2.5F, 7, 8, 2, 0.0F);
        this.setRotateAngle(neckFluff, 0.2617993877991494F, 0.0F, 0.0F);
        this.tail2 = new RendererModel(this, 62, 37);
        this.tail2.setRotationPoint(0.0F, 3.0F, 0.0F);
        this.tail2.addBox(-2.0F, 0.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(tail2, -0.3490658503988659F, 0.0F, 0.0F);
        this.armRight2 = new RendererModel(this, 48, 15);
        this.armRight2.setRotationPoint(-2.0F, 5.0F, 0.0F);
        this.armRight2.addBox(-1.5F, 0.0F, -2.0F, 3, 8, 4, 0.0F);
        this.setRotateAngle(armRight2, -0.3490658503988659F, 0.0F, 0.0F);
        this.body = new RendererModel(this, 0, 13);
        this.body.setRotationPoint(0.0F, 9.5F, -2.5F);
        this.body.addBox(-4.5F, 0.0F, -4.0F, 9, 8, 8, 0.0F);
        this.setRotateAngle(body, 1.5707963267948966F, 0.0F, 0.0F);
        this.neck = new RendererModel(this, 0, 45);
        this.neck.setRotationPoint(0.0F, 1.5F, 1.0F);
        this.neck.addBox(-3.0F, -6.0F, -3.0F, 6, 7, 6, 0.0F);
        this.setRotateAngle(neck, -0.3141592653589793F, 0.0F, 0.0F);
        this.neckFluffLeft = new RendererModel(this, 82, 25);
        this.neckFluffLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.neckFluffLeft.addBox(-2.5F, -5.5F, 3.0F, 6, 8, 3, 0.0F);
        this.setRotateAngle(neckFluffLeft, 0.3490658503988659F, 1.5707963267948966F, 0.0F);
        this.armRight = new RendererModel(this, 36, 25);
        this.armRight.setRotationPoint(-1.5F, 3.0F, -1.5F);
        this.armRight.addBox(-4.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(armRight, -1.2217304763960306F, 0.0F, 0.0F);
        this.neckFluffBottom = new RendererModel(this, 80, 0);
        this.neckFluffBottom.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.neckFluffBottom.addBox(-3.0F, -0.6F, 4.5F, 6, 6, 2, 0.0F);
        this.setRotateAngle(neckFluffBottom, 1.0471975511965976F, 3.141592653589793F, 0.0F);
        this.earLeft = new RendererModel(this, 26, 0);
        this.earLeft.mirror = true;
        this.earLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.earLeft.addBox(1.5F, -6.5F, -1.0F, 2, 3, 1, 0.0F);
        this.hip = new RendererModel(this, 0, 30);
        this.hip.setRotationPoint(0.0F, 6.0F, -0.9F);
        this.hip.addBox(-3.5F, 0.0F, -3.0F, 7, 9, 6, 0.0F);
        this.nose = new RendererModel(this, 64, 0);
        this.nose.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.nose.addBox(-1.5F, -3.5F, -3.7F, 3, 2, 5, 0.0F);
        this.setRotateAngle(nose, 0.17453292519943295F, 0.0F, 0.0F);
        this.tail = new RendererModel(this, 62, 30);
        this.tail.setRotationPoint(0.0F, 7.0F, 2.0F);
        this.tail.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
        this.setRotateAngle(tail, -0.22759093446006054F, 0.0F, 0.0F);
        this.jawTeeth = new RendererModel(this, 50, 6);
        this.jawTeeth.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.jawTeeth.addBox(-1.5F, -1.0F, -3.5F, 3, 1, 4, 0.0F);
        this.footLeft_2 = new RendererModel(this, 24, 54);
        this.footLeft_2.setRotationPoint(0.0F, 7.0F, -0.5F);
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

    @Override
    public void render(@Nonnull T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.body.render(scale);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(RendererModel RendererModel, float x, float y, float z) {
        RendererModel.rotateAngleX = x;
        RendererModel.rotateAngleY = y;
        RendererModel.rotateAngleZ = z;
    }

    @Override
    public void setVisible(boolean visible) {

    }

    @Override
    public void setSneak(boolean sneak) {

    }
}
