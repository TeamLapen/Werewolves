package de.teamlapen.werewolves.client.model;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

/**
 * Model made by Rebel
 * Created using Tabula 7.1.0
 */
@OnlyIn(Dist.CLIENT)
public class WerewolfBeastModel<T extends LivingEntity> extends WerewolfBaseModel<T> {
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
    public RendererModel earLeft2;
    public RendererModel earRight2;
    public RendererModel nose;
    public RendererModel snoutTeeth;
    public RendererModel jawTeeth;
    public RendererModel jawFluff;
    public RendererModel armLeft2;
    public RendererModel fingerLeft;
    public RendererModel fingerLeft2;
    public RendererModel fingerLeft3;
    public RendererModel fingerLeft4;
    public RendererModel thumbLeft;
    public RendererModel armRight2;
    public RendererModel fingerRight;
    public RendererModel fingerRight2;
    public RendererModel fingerRight3;
    public RendererModel fingerRight4;
    public RendererModel thumbRight;

    public boolean isSneak;
    public float swimAnimation;


    public WerewolfBeastModel() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.armRight = new RendererModel(this, 36, 25);
        this.armRight.setRotationPoint(-4.5F, 3.0F, 0.0F);
        this.armRight.addBox(-4.0F, -2.0F, -2.0F, 4, 10, 4, 0.0F);
        this.setRotateAngle(armRight, -0.3490658503988659F, 0.0F, 0.08726646259971647F);
        this.legRight = new RendererModel(this, 24, 39);
        this.legRight.setRotationPoint(-0.5F, 9.0F, 0.0F);
        this.legRight.addBox(-4.0F, -2.0F, -3.0F, 4, 8, 6, 0.0F);
        this.setRotateAngle(legRight, -0.3839724354387525F, 0.0F, 0.0F);
        this.neck = new RendererModel(this, 0, 45);
        this.neck.setRotationPoint(0.0F, 1.5F, 1.0F);
        this.neck.addBox(-3.0F, -6.0F, -3.0F, 6, 7, 6, 0.0F);
        this.setRotateAngle(neck, 0.6981317007977318F, 0.0F, 0.0F);
        this.headSidburnLeft = new RendererModel(this, 32, 12);
        this.headSidburnLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.headSidburnLeft.addBox(1.0F, -1.0F, -5.0F, 3, 6, 0, 0.0F);
        this.setRotateAngle(headSidburnLeft, 0.0F, -0.5235987755982988F, 0.0F);
        this.jawFluff = new RendererModel(this, 96, 8);
        this.jawFluff.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.jawFluff.addBox(-1.5F, 2.0F, -3.5F, 3, 2, 4, 0.0F);
        this.neckFluff = new RendererModel(this, 64, 19);
        this.neckFluff.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.neckFluff.addBox(-3.5F, -5.0F, 2.5F, 7, 8, 2, 0.0F);
        this.setRotateAngle(neckFluff, 0.2617993877991494F, 0.0F, 0.0F);
        this.fingerLeft = new RendererModel(this, 0, 13);
        this.fingerLeft.setRotationPoint(1.2F, 9.0F, -1.5F);
        this.fingerLeft.addBox(-1.5F, 0.0F, -0.5F, 2, 3, 1, 0.0F);
        this.setRotateAngle(fingerLeft, -0.2617993877991494F, 0.0F, -0.17453292519943295F);
        this.body = new RendererModel(this, 0, 13);
        this.body.setRotationPoint(0.0F, -4.0F, -2.5F);
        this.body.addBox(-4.5F, 0.0F, -4.0F, 9, 8, 8, 0.0F);
        this.setRotateAngle(body, 0.5235987755982988F, 0.0F, 0.0F);
        this.legRight2 = new RendererModel(this, 44, 44);
        this.legRight2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.legRight2.addBox(-3.5F, 2.0F, 3.0F, 3, 10, 4, 0.0F);
        this.hip = new RendererModel(this, 0, 30);
        this.hip.setRotationPoint(0.0F, 6.0F, 1.0F);
        this.hip.addBox(-3.5F, 0.0F, -3.0F, 7, 9, 6, 0.0F);
        this.setRotateAngle(hip, -0.5235987755982988F, 0.0F, 0.0F);
        this.tail = new RendererModel(this, 62, 30);
        this.tail.setRotationPoint(0.0F, 7.0F, 2.0F);
        this.tail.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
        this.setRotateAngle(tail, 0.7853981633974483F, 0.0F, 0.0F);
        this.earLeft = new RendererModel(this, 26, 0);
        this.earLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.earLeft.addBox(3.5F, -2.5F, -2.5F, 1, 4, 3, 0.0F);
        this.setRotateAngle(earLeft, -0.4886921905584123F, 0.3490658503988659F, 0.0F);
        this.footRight = new RendererModel(this, 24, 54);
        this.footRight.setRotationPoint(-2.0F, 12.0F, 5.0F);
        this.footRight.addBox(-2.0F, -1.5F, -4.0F, 4, 3, 6, 0.0F);
        this.setRotateAngle(footRight, 0.3839724354387525F, 0.0F, 0.0F);
        this.fingerRight = new RendererModel(this, 0, 13);
        this.fingerRight.mirror = true;
        this.fingerRight.setRotationPoint(-1.2F, 9.0F, -1.5F);
        this.fingerRight.addBox(-0.5F, 0.0F, -0.5F, 2, 3, 1, 0.0F);
        this.setRotateAngle(fingerRight, -0.2617993877991494F, 0.0F, 0.17453292519943295F);
        this.snoutTeeth = new RendererModel(this, 34, 6);
        this.snoutTeeth.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.snoutTeeth.addBox(-2.0F, 0.0F, -4.0F, 4, 2, 4, 0.0F);
        this.armLeft2 = new RendererModel(this, 48, 15);
        this.armLeft2.mirror = true;
        this.armLeft2.setRotationPoint(2.0F, 7.0F, 0.0F);
        this.armLeft2.addBox(-1.5F, 0.0F, -2.0F, 3, 10, 4, 0.0F);
        this.setRotateAngle(armLeft2, -0.3490658503988659F, 0.0F, 0.0F);
        this.headSidburnRight = new RendererModel(this, 32, 12);
        this.headSidburnRight.mirror = true;
        this.headSidburnRight.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.headSidburnRight.addBox(-4.0F, -1.0F, -5.0F, 3, 6, 0, 0.0F);
        this.setRotateAngle(headSidburnRight, 0.0F, 0.5235987755982988F, 0.0F);
        this.nose = new RendererModel(this, 64, 0);
        this.nose.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.nose.addBox(-1.5F, -3.5F, -3.7F, 3, 2, 5, 0.0F);
        this.setRotateAngle(nose, 0.17453292519943295F, 0.0F, 0.0F);
        this.fingerRight2 = new RendererModel(this, 0, 13);
        this.fingerRight2.mirror = true;
        this.fingerRight2.setRotationPoint(-1.2F, 9.0F, -0.5F);
        this.fingerRight2.addBox(-0.5F, 0.0F, -0.5F, 2, 3, 1, 0.0F);
        this.setRotateAngle(fingerRight2, -0.08726646259971647F, 0.0F, 0.17453292519943295F);
        this.snout = new RendererModel(this, 34, 0);
        this.snout.setRotationPoint(0.0F, 2.0F, -5.0F);
        this.snout.addBox(-2.0F, -2.0F, -4.0F, 4, 2, 4, 0.0F);
        this.setRotateAngle(snout, -0.08726646259971647F, 0.0F, 0.0F);
        this.fingerLeft3 = new RendererModel(this, 0, 13);
        this.fingerLeft3.setRotationPoint(1.2F, 9.0F, 0.5F);
        this.fingerLeft3.addBox(-1.5F, 0.0F, -0.5F, 2, 3, 1, 0.0F);
        this.setRotateAngle(fingerLeft3, 0.08726646259971647F, 0.0F, -0.17453292519943295F);
        this.fingerLeft4 = new RendererModel(this, 0, 13);
        this.fingerLeft4.setRotationPoint(1.2F, 9.0F, 1.5F);
        this.fingerLeft4.addBox(-1.5F, 0.0F, -0.5F, 2, 3, 1, 0.0F);
        this.setRotateAngle(fingerLeft4, 0.2617993877991494F, 0.0F, -0.17453292519943295F);
        this.head = new RendererModel(this, 0, 0);
        this.head.setRotationPoint(0.0F, 0.0F, 1.0F);
        this.head.addBox(-3.5F, -3.5F, -5.0F, 7, 7, 6, 0.0F);
        this.bodyFluff = new RendererModel(this, 82, 14);
        this.bodyFluff.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bodyFluff.addBox(-4.5F, 8.0F, -4.0F, 9, 3, 8, 0.0F);
        this.fingerRight4 = new RendererModel(this, 0, 13);
        this.fingerRight4.mirror = true;
        this.fingerRight4.setRotationPoint(-1.2F, 9.0F, 1.5F);
        this.fingerRight4.addBox(-0.5F, 0.0F, -0.5F, 2, 3, 1, 0.0F);
        this.setRotateAngle(fingerRight4, 0.2617993877991494F, 0.0F, 0.17453292519943295F);
        this.tail3 = new RendererModel(this, 62, 49);
        this.tail3.setRotationPoint(0.0F, 7.8F, 0.0F);
        this.tail3.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
        this.setRotateAngle(tail3, 0.136659280431156F, 0.0F, 0.0F);
        this.neckFluffRight = new RendererModel(this, 82, 25);
        this.neckFluffRight.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.neckFluffRight.addBox(-3.5F, -5.5F, 3.0F, 6, 8, 3, 0.0F);
        this.setRotateAngle(neckFluffRight, 0.3490658503988659F, -1.5707963267948966F, 0.0F);
        this.armLeft = new RendererModel(this, 36, 25);
        this.armLeft.mirror = true;
        this.armLeft.setRotationPoint(4.5F, 3.0F, 0.0F);
        this.armLeft.addBox(0.0F, -2.0F, -2.0F, 4, 10, 4, 0.0F);
        this.setRotateAngle(armLeft, -0.3490658503988659F, 0.0F, -0.08726646259971647F);
        this.headFluff = new RendererModel(this, 96, 0);
        this.headFluff.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.headFluff.addBox(-3.5F, 3.5F, -5.0F, 7, 2, 6, 0.0F);
        this.thumbLeft = new RendererModel(this, 0, 17);
        this.thumbLeft.setRotationPoint(-1.2F, 9.0F, -1.5F);
        this.thumbLeft.addBox(-0.5F, 0.0F, -0.5F, 2, 2, 1, 0.0F);
        this.setRotateAngle(thumbLeft, -0.2617993877991494F, 0.0F, 0.17453292519943295F);
        this.legLeft = new RendererModel(this, 24, 39);
        this.legLeft.setRotationPoint(0.5F, 9.0F, 0.0F);
        this.legLeft.addBox(0.0F, -2.0F, -3.0F, 4, 8, 6, 0.0F);
        this.setRotateAngle(legLeft, -0.3839724354387525F, 0.0F, 0.0F);
        this.fingerLeft2 = new RendererModel(this, 0, 13);
        this.fingerLeft2.setRotationPoint(1.2F, 9.0F, -0.5F);
        this.fingerLeft2.addBox(-1.5F, 0.0F, -0.5F, 2, 3, 1, 0.0F);
        this.setRotateAngle(fingerLeft2, -0.08726646259971647F, 0.0F, -0.17453292519943295F);
        this.tail2 = new RendererModel(this, 62, 37);
        this.tail2.setRotationPoint(0.0F, 3.0F, 0.0F);
        this.tail2.addBox(-2.0F, 0.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(tail2, -0.3490658503988659F, 0.0F, 0.0F);
        this.thumbRight = new RendererModel(this, 0, 17);
        this.thumbRight.mirror = true;
        this.thumbRight.setRotationPoint(1.2F, 9.0F, -1.5F);
        this.thumbRight.addBox(-1.5F, 0.0F, -0.5F, 2, 2, 1, 0.0F);
        this.setRotateAngle(thumbRight, -0.2617993877991494F, 0.0F, -0.17453292519943295F);
        this.earRight2 = new RendererModel(this, 26, 7);
        this.earRight2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.earRight2.addBox(-4.0F, -5.5F, -2.0F, 1, 4, 2, 0.0F);
        this.setRotateAngle(earRight2, 0.0F, 0.0F, -0.17453292519943295F);
        this.footLeft = new RendererModel(this, 24, 54);
        this.footLeft.mirror = true;
        this.footLeft.setRotationPoint(2.0F, 12.0F, 5.0F);
        this.footLeft.addBox(-2.0F, -1.5F, -4.0F, 4, 3, 6, 0.0F);
        this.setRotateAngle(footLeft, 0.3839724354387525F, 0.0F, 0.0F);
        this.joint = new RendererModel(this, 0, 0);
        this.joint.setRotationPoint(0.0F, -6.5F, -1.0F);
        this.joint.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(joint, -1.2217304763960306F, 0.0F, 0.0F);
        this.armRight2 = new RendererModel(this, 48, 15);
        this.armRight2.setRotationPoint(-2.0F, 7.0F, 0.0F);
        this.armRight2.addBox(-1.5F, 0.0F, -2.0F, 3, 10, 4, 0.0F);
        this.setRotateAngle(armRight2, -0.3490658503988659F, 0.0F, 0.0F);
        this.jawTeeth = new RendererModel(this, 50, 6);
        this.jawTeeth.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.jawTeeth.addBox(-1.5F, -1.0F, -3.5F, 3, 1, 4, 0.0F);
        this.neckFluffBottom = new RendererModel(this, 80, 0);
        this.neckFluffBottom.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.neckFluffBottom.addBox(-3.0F, -0.6F, 4.5F, 6, 6, 2, 0.0F);
        this.setRotateAngle(neckFluffBottom, 1.0471975511965976F, 3.141592653589793F, 0.0F);
        this.legLeft2 = new RendererModel(this, 44, 44);
        this.legLeft2.mirror = true;
        this.legLeft2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.legLeft2.addBox(0.5F, 2.0F, 3.0F, 3, 10, 4, 0.0F);
        this.earLeft2 = new RendererModel(this, 26, 7);
        this.earLeft2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.earLeft2.addBox(3.0F, -5.5F, -2.0F, 1, 4, 2, 0.0F);
        this.setRotateAngle(earLeft2, 0.0F, 0.0F, 0.17453292519943295F);
        this.fingerRight3 = new RendererModel(this, 0, 13);
        this.fingerRight3.mirror = true;
        this.fingerRight3.setRotationPoint(-1.2F, 9.0F, 0.5F);
        this.fingerRight3.addBox(-0.5F, 0.0F, -0.5F, 2, 3, 1, 0.0F);
        this.setRotateAngle(fingerRight3, 0.08726646259971647F, 0.0F, 0.17453292519943295F);
        this.earRight = new RendererModel(this, 26, 0);
        this.earRight.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.earRight.addBox(-4.5F, -2.5F, -2.5F, 1, 4, 3, 0.0F);
        this.setRotateAngle(earRight, -0.4886921905584123F, -0.3490658503988659F, 0.0F);
        this.jaw = new RendererModel(this, 50, 0);
        this.jaw.setRotationPoint(0.0F, 1.5F, -5.0F);
        this.jaw.addBox(-1.5F, 0.0F, -3.5F, 3, 2, 4, 0.0F);
        this.setRotateAngle(jaw, 1.2217304763960306F, 0.0F, 0.0F);
        this.neckFluffLeft = new RendererModel(this, 82, 25);
        this.neckFluffLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.neckFluffLeft.addBox(-2.5F, -5.5F, 3.0F, 6, 8, 3, 0.0F);
        this.setRotateAngle(neckFluffLeft, 0.3490658503988659F, 1.5707963267948966F, 0.0F);
        this.body.addChild(this.armRight);
        this.hip.addChild(this.legRight);
        this.body.addChild(this.neck);
        this.head.addChild(this.headSidburnLeft);
        this.jaw.addChild(this.jawFluff);
        this.neck.addChild(this.neckFluff);
        this.armLeft2.addChild(this.fingerLeft);
        this.legRight.addChild(this.legRight2);
        this.body.addChild(this.hip);
        this.hip.addChild(this.tail);
        this.head.addChild(this.earLeft);
        this.legRight2.addChild(this.footRight);
        this.armRight2.addChild(this.fingerRight);
        this.snout.addChild(this.snoutTeeth);
        this.armLeft.addChild(this.armLeft2);
        this.head.addChild(this.headSidburnRight);
        this.snout.addChild(this.nose);
        this.armRight2.addChild(this.fingerRight2);
        this.head.addChild(this.snout);
        this.armLeft2.addChild(this.fingerLeft3);
        this.armLeft2.addChild(this.fingerLeft4);
        this.joint.addChild(this.head);
        this.body.addChild(this.bodyFluff);
        this.armRight2.addChild(this.fingerRight4);
        this.tail2.addChild(this.tail3);
        this.neck.addChild(this.neckFluffRight);
        this.body.addChild(this.armLeft);
        this.head.addChild(this.headFluff);
        this.armLeft2.addChild(this.thumbLeft);
        this.hip.addChild(this.legLeft);
        this.armLeft2.addChild(this.fingerLeft2);
        this.tail.addChild(this.tail2);
        this.armRight2.addChild(this.thumbRight);
        this.earRight.addChild(this.earRight2);
        this.legLeft2.addChild(this.footLeft);
        this.neck.addChild(this.joint);
        this.armRight.addChild(this.armRight2);
        this.jaw.addChild(this.jawTeeth);
        this.neck.addChild(this.neckFluffBottom);
        this.legLeft.addChild(this.legLeft2);
        this.earLeft.addChild(this.earLeft2);
        this.armRight2.addChild(this.fingerRight3);
        this.head.addChild(this.earRight);
        this.head.addChild(this.jaw);
        this.neck.addChild(this.neckFluffLeft);
    }

    @Override
    public void render(@Nonnull T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.body.render(scale);
    }

    @SuppressWarnings("DuplicatedCode")
    public void setRotationAngles(@Nonnull T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        boolean flag1 = entityIn.isActualySwimming();
        this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        if(this.swimAnimation > 0.0f ) {
            if (flag1) {
                this.head.rotateAngleX = this.func_205060_a(this.head.rotateAngleX, (-(float)Math.PI / 4F), this.swimAnimation);
            } else {
                this.head.rotateAngleX = this.func_205060_a(this.head.rotateAngleX, headPitch * ((float)Math.PI / 180F), this.swimAnimation);
            }
        } else {
            this.head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        }

        this.armRight.rotateAngleZ = 0.0f;
        this.armRight.rotateAngleX = -0.2f;
        this.armLeft.rotateAngleZ = 0.0f;
        this.armLeft.rotateAngleX = 0.2f;
        this.armLeft2.rotateAngleX = -1f;
        this.armRight2.rotateAngleX = -1f;

        this.armRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.2F * limbSwingAmount * 0.5F;
        this.armLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.2F * limbSwingAmount * 0.5F;

        this.armRight.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.armLeft.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.armRight.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.armLeft.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;

        this.legRight.rotateAngleX = -0.3839724354387525F;
        this.legLeft.rotateAngleX = -0.3839724354387525F;

        this.legRight.rotateAngleX += MathHelper.cos(limbSwing * 0.6662F * 0.7f) * 1.4F * limbSwingAmount;
        this.legLeft.rotateAngleX += MathHelper.cos(limbSwing * 0.6662F * 0.7f + (float) Math.PI) * 1.4F * limbSwingAmount;

        if (this.isSneak) {
            this.body.rotateAngleX = 0.7235987755982988F;
            this.hip.rotateAngleX = -0.7235987755982988F;
        } else {
            this.body.rotateAngleX = 0.5235987755982988F;
            this.hip.rotateAngleX = -0.5235987755982988F;
        }

        //reset tail rotation angle
        this.tail.rotateAngleX = 0.7853981633974483F;
        this.tail.rotateAngleY = 0F;

        //idle rotations
        this.tail.rotateAngleX -= 0.035f;
        this.tail.rotateAngleX += MathHelper.cos(ageInTicks * 0.10F) * 0.07F;

        this.tail.rotateAngleY = -0.035F;
        this.tail.rotateAngleY += MathHelper.sin(ageInTicks * 0.14F) * 0.07f;

        //running tail animation
        this.tail.rotateAngleX += MathHelper.cos(limbSwing * 0.6662F * 0.7f) * 0.3F * MathHelper.abs(limbSwingAmount) + 0.3f;
        this.tail.rotateAngleY += MathHelper.sin(limbSwing * 0.6662F * 0.7f) * 0.1F * limbSwingAmount;

        if (this.swingProgress > 0.0F) {
            RendererModel renderermodel = this.armRight;
            float f1 = this.swingProgress;
            f1 = 1.0F - this.swingProgress;
            f1 = f1 * f1;
            f1 = f1 * f1;
            f1 = 1.0F - f1;
            float f2 = MathHelper.sin(f1 * (float) Math.PI);
            float f3 = MathHelper.sin(this.swingProgress * (float) Math.PI) * -(this.head.rotateAngleX - 0.7F) * 0.75F;
            renderermodel.rotateAngleX = (float) ((double) renderermodel.rotateAngleX - ((double) f2 * 1.2D + (double) f3));
            renderermodel.rotateAngleY += this.body.rotateAngleY * 2.0F;
            renderermodel.rotateAngleZ += MathHelper.sin(this.swingProgress * (float) Math.PI) * -0.4F;
        }

        this.jaw.rotateAngleX = 0.8217304763960306f;
        this.jaw.rotateAngleX += MathHelper.cos(ageInTicks * 0.1F) * 0.07F;

        this.earLeft.rotateAngleX = -0.4886921905584123F;
        this.earLeft.rotateAngleX += MathHelper.cos(ageInTicks * 0.1F) * 0.07F;

        this.earRight.rotateAngleX = -0.4886921905584123F;
        this.earRight.rotateAngleX += MathHelper.cos(ageInTicks * 0.1F) * 0.07F;
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
        this.body.showModel = visible;
    }

    @Override
    public void setSneak(boolean sneak) {
        this.isSneak = sneak;
    }

    /**
     * copied from {@link net.minecraft.client.renderer.entity.model.BipedModel}
     */
    private float func_203068_a(float p_203068_1_) {
        return -65.0F * p_203068_1_ + p_203068_1_ * p_203068_1_;
    }
}
