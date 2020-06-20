package de.teamlapen.werewolves.client.model;// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WerewolfModel<T extends LivingEntity> extends EntityModel<T> {
    private final RendererModel leftLowerLeg;
    private final RendererModel rightLowerLeg;
    private final RendererModel leftLeg;
    private final RendererModel rightLeg;
    private final RendererModel righthip;
    private final RendererModel lowerBody;
    private final RendererModel middlebody;
    private final RendererModel upperbody;
    private final RendererModel head;
    private final RendererModel leftarm;
    private final RendererModel leftlowerarm;
    private final RendererModel lefthib;
    private final RendererModel leftshoulder;
    private final RendererModel mouth;
    private final RendererModel bone;
    private final RendererModel bone2;
    private final RendererModel rightshoulder;
    private final RendererModel rightarm;
    private final RendererModel rightlowerarm;
    private final RendererModel tail;
    private final RendererModel bone4;
    private final RendererModel bone3;
    private final RendererModel ears;
    private final RendererModel left;
    private final RendererModel right;
    private final RendererModel neck;

    //TODO animations & better model
    public WerewolfModel() {
        textureWidth = 64;
        textureHeight = 128;

        leftLowerLeg = new RendererModel(this);
        leftLowerLeg.setRotationPoint(0.0F, 25.0F, -1.0F);
        setRotationAngle(leftLowerLeg, -0.2618F, 0.0F, 0.0F);
        leftLowerLeg.setTextureOffset(50, 51).addBox(3.5F, -7.0F, 2.0F, 2, 5, 2, 0.0F, false);

        rightLowerLeg = new RendererModel(this);
        rightLowerLeg.setRotationPoint(0.0F, 24.0F, 0.0F);
        setRotationAngle(rightLowerLeg, -0.2618F, 0.0F, 0.0F);
        rightLowerLeg.setTextureOffset(50, 51).addBox(-5.5F, -7.0F, 1.0F, 2, 6, 2, 0.0F, false);

        leftLeg = new RendererModel(this);
        leftLeg.setRotationPoint(5.0F, 20.0F, 2.0F);
        setRotationAngle(leftLeg, 0.6109F, 0.0F, 0.0F);
        leftLeg.setTextureOffset(50, 51).addBox(-1.5F, -4.0F, 1.0F, 2, 5, 2, 0.0F, false);

        rightLeg = new RendererModel(this);
        rightLeg.setRotationPoint(5.0F, 20.0F, 2.0F);
        setRotationAngle(rightLeg, 0.6109F, 0.0F, 0.0F);
        rightLeg.setTextureOffset(50, 51).addBox(-10.5F, -6.2456F, 1.3927F, 2, 6, 2, 0.0F, false);

        righthip = new RendererModel(this);
        righthip.setRotationPoint(-6.0F, 24.0F, -1.0F);
        setRotationAngle(righthip, -0.9599F, 0.0F, 0.0F);
        righthip.setTextureOffset(50, 102).addBox(8.5F, -12.6806F, -8.5291F, 3, 7, 3, 0.0F, false);

        lowerBody = new RendererModel(this);
        lowerBody.setRotationPoint(0.0F, 25.0F, 1.0F);
        setRotationAngle(lowerBody, 0.1745F, 0.0F, 0.0F);
        lowerBody.setTextureOffset(22, 22).addBox(-4.0F, -14.7656F, 3.6794F, 8, 3, 8, 0.0F, false);

        middlebody = new RendererModel(this);
        middlebody.setRotationPoint(0.0F, 24.0F, 3.0F);
        setRotationAngle(middlebody, 0.2618F, 0.0F, 0.0F);
        middlebody.setTextureOffset(0, 48).addBox(-3.0F, -21.1141F, 3.4251F, 6, 8, 7, 0.0F, false);

        upperbody = new RendererModel(this);
        upperbody.setRotationPoint(0.0F, 22.0F, 7.0F);
        setRotationAngle(upperbody, 0.5236F, 0.0F, 0.0F);
        upperbody.setTextureOffset(0, 65).addBox(-5.0F, -26.6962F, 3.866F, 10, 9, 7, 0.0F, false);

        head = new RendererModel(this);
        head.setRotationPoint(0.0F, 23.0F, 5.0F);
        setRotationAngle(head, 0.2618F, 0.0F, 0.0F);
        head.setTextureOffset(0, 0).addBox(-4.0F, -33.6315F, -3.7565F, 8, 7, 7, 0.0F, false);

        leftarm = new RendererModel(this);
        leftarm.setRotationPoint(0.0F, 24.0F, -4.0F);
        setRotationAngle(leftarm, -0.1745F, 0.0F, 0.0F);
        leftarm.setTextureOffset(50, 78).addBox(5.0F, -26.2713F, 0.1014F, 3, 7, 3, 0.0F, false);

        leftlowerarm = new RendererModel(this);
        leftlowerarm.setRotationPoint(0.0F, 20.0F, -8.0F);
        setRotationAngle(leftlowerarm, -0.5236F, 0.0F, 0.0F);
        leftlowerarm.setTextureOffset(50, 78).addBox(5.0F, -17.4641F, -1.2679F, 3, 7, 3, 0.0F, false);

        lefthib = new RendererModel(this);
        lefthib.setRotationPoint(-6.0F, 24.0F, -1.0F);
        setRotationAngle(lefthib, -0.9599F, 0.0F, 0.0F);
        lefthib.setTextureOffset(51, 102).addBox(0.5F, -12.6806F, -8.5291F, 3, 7, 3, 0.0F, false);

        leftshoulder = new RendererModel(this);
        leftshoulder.setRotationPoint(0.0F, 24.0F, 0.0F);
        leftshoulder.setTextureOffset(0, 0).addBox(5.0F, -28.0F, 0.0F, 3, 3, 4, 0.0F, false);

        mouth = new RendererModel(this);
        mouth.setRotationPoint(0.0F, 23.0F, 4.0F);
        setRotationAngle(mouth, 0.1745F, 0.0F, 0.0F);
        mouth.setTextureOffset(27, 21).addBox(-2.0F, -30.5209F, -10.9544F, 4, 3, 8, 0.0F, false);

        bone = new RendererModel(this);
        bone.setRotationPoint(-1.0F, -27.0F, -3.0F);
        mouth.addChild(bone);
        setRotationAngle(bone, 0.1745F, 0.0F, 0.0F);
        bone.setTextureOffset(2, 51).addBox(-1.0F, -3.9809F, -7.3191F, 4, 3, 6, 0.0F, true);

        bone2 = new RendererModel(this);
        bone2.setRotationPoint(0.0F, -0.1166F, 5.0977F);
        mouth.addChild(bone2);
        setRotationAngle(bone2, 0.1745F, 0.0F, 0.0F);
        bone2.setTextureOffset(0, 0).addBox(-2.0F, -30.9611F, -6.7146F, 4, 1, 4, 0.0F, false);

        rightshoulder = new RendererModel(this);
        rightshoulder.setRotationPoint(-11.0F, 24.0F, 0.0F);
        rightshoulder.setTextureOffset(0, 0).addBox(3.0F, -28.0F, 0.0F, 3, 3, 4, 0.0F, false);

        rightarm = new RendererModel(this);
        rightarm.setRotationPoint(-12.0F, 24.0F, -4.0F);
        setRotationAngle(rightarm, -0.1745F, 0.0F, 0.0F);
        rightarm.setTextureOffset(50, 78).addBox(4.0F, -26.2713F, 0.1014F, 3, 7, 3, 0.0F, false);

        rightlowerarm = new RendererModel(this);
        rightlowerarm.setRotationPoint(-12.0F, 20.0F, -8.0F);
        setRotationAngle(rightlowerarm, -0.5236F, 0.0F, 0.0F);
        rightlowerarm.setTextureOffset(49, 78).addBox(4.0F, -17.4641F, -1.2679F, 3, 7, 3, 0.0F, false);

        tail = new RendererModel(this);
        tail.setRotationPoint(0.0F, 21.0F, -3.0F);
        setRotationAngle(tail, -0.3491F, 0.0F, 0.0F);
        tail.setTextureOffset(0, 0).addBox(-1.0F, -14.5977F, 7.7183F, 1, 1, 2, 0.0F, false);

        bone4 = new RendererModel(this);
        bone4.setRotationPoint(0.0F, -2.135F, -2.9054F);
        tail.addChild(bone4);
        setRotationAngle(bone4, -0.1745F, 0.0F, 0.0F);
        bone4.setTextureOffset(33, 111).addBox(-1.5F, -15.0239F, 9.9688F, 2, 2, 4, 0.0F, false);

        bone3 = new RendererModel(this);
        bone3.setRotationPoint(0.0F, 3.7711F, 3.8496F);
        bone4.addChild(bone3);
        setRotationAngle(bone3, 0.2618F, 0.0F, 0.0F);
        bone3.setTextureOffset(34, 112).addBox(-1.5F, -15.673F, 13.8325F, 2, 2, 4, 0.0F, false);

        ears = new RendererModel(this);
        ears.setRotationPoint(0.0F, 24.0F, 0.0F);


        left = new RendererModel(this);
        left.setRotationPoint(-12.0F, -1.0F, 0.0F);
        ears.addChild(left);
        setRotationAngle(left, 0.0F, 0.0F, 0.2618F);
        left.setTextureOffset(17, 51).addBox(5.2588F, -38.0341F, -4.0F, 2, 4, 1, 0.0F, false);

        right = new RendererModel(this);
        right.setRotationPoint(9.0F, -2.0F, 0.0F);
        ears.addChild(right);
        setRotationAngle(right, 0.0F, 0.0F, -0.2618F);
        right.setTextureOffset(31, 62).addBox(-4.3F, -36.3F, -4.0F, 2, 4, 1, 0.0F, false);

        neck = new RendererModel(this);
        neck.setRotationPoint(0.0F, 18.0F, 18.0F);
        setRotationAngle(neck, 0.6109F, 0.0F, 0.0F);
        neck.setTextureOffset(5, 71).addBox(-4.0F, -33.8F, -1.0F, 8, 6, 2, 0.0F, false);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {

    }

    @Override
    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        leftLowerLeg.render(scale);
        rightLowerLeg.render(scale);
        leftLeg.render(scale);
        rightLeg.render(scale);
        righthip.render(scale);
        lowerBody.render(scale);
        middlebody.render(scale);
        upperbody.render(scale);
        head.render(scale);
        leftarm.render(scale);
        leftlowerarm.render(scale);
        lefthib.render(scale);
        leftshoulder.render(scale);
        mouth.render(scale);
        rightshoulder.render(scale);
        rightarm.render(scale);
        rightlowerarm.render(scale);
        tail.render(scale);
        ears.render(scale);
        neck.render(scale);
    }

    public void setRotationAngle(RendererModel RendererModel, float x, float y, float z) {
        RendererModel.rotateAngleX = x;
        RendererModel.rotateAngleY = y;
        RendererModel.rotateAngleZ = z;
    }
}