package de.teamlapen.werewolves.client.model;


import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class WerewolfModel<T extends LivingEntity> extends EntityModel<T> {
    RendererModel Head;
    RendererModel Nose;
    RendererModel Snout;
    RendererModel TeethU;
    RendererModel TeethL;
    RendererModel Mouth;
    RendererModel LEar;
    RendererModel REar;
    RendererModel Neck;
    RendererModel Neck2;
    RendererModel SideburnL;
    RendererModel SideburnR;
    RendererModel Chest;
    RendererModel Abdomen;
    RendererModel TailA;
    RendererModel TailC;
    RendererModel TailB;
    RendererModel TailD;
    RendererModel RLegA;
    RendererModel RFoot;
    RendererModel RLegB;
    RendererModel RLegC;
    RendererModel LLegB;
    RendererModel LFoot;
    RendererModel LLegC;
    RendererModel LLegA;
    RendererModel RArmB;
    RendererModel RArmC;
    RendererModel LArmB;
    RendererModel RHand;
    RendererModel RArmA;
    RendererModel LArmA;
    RendererModel LArmC;
    RendererModel LHand;
    RendererModel RFinger1;
    RendererModel RFinger2;
    RendererModel RFinger3;
    RendererModel RFinger4;
    RendererModel RFinger5;
    RendererModel LFinger1;
    RendererModel LFinger2;
    RendererModel LFinger3;
    RendererModel LFinger4;
    RendererModel LFinger5;
    public boolean hunched;

    public boolean isSneak;

    //TODO new Model
    public WerewolfModel() {
        textureWidth = 64;
        textureHeight = 128;

        Head = new RendererModel(this, 0, 0);
        Head.addBox(-4.0F, -3.0F, -6.0F, 8, 8, 6);
        Head.setRotationPoint(0.0F, -8.0F, -6.0F);
        Head.setTextureSize(64, 128);

        Nose = new RendererModel(this, 44, 33);
        Nose.addBox(-1.5F, -1.7F, -12.3F, 3, 2, 7);
        Nose.setRotationPoint(0.0F, -8.0F, -6.0F);
        setRotation(Nose, 0.2792527F, 0.0F, 0.0F);

        Snout = new RendererModel(this, 0, 25);
        Snout.addBox(-2.0F, 2.0F, -12.0F, 4, 2, 6);
        Snout.setRotationPoint(0.0F, -8.0F, -6.0F);

        TeethU = new RendererModel(this, 46, 18);
        TeethU.addBox(-2.0F, 4.01F, -12.0F, 4, 2, 5);
        TeethU.setRotationPoint(0.0F, -8.0F, -6.0F);

        TeethL = new RendererModel(this, 20, 109);
        TeethL.addBox(-1.5F, -12.5F, 2.01F, 3, 5, 2);
        TeethL.setRotationPoint(0.0F, -8.0F, -6.0F);
        setRotation(TeethL, 2.530727F, 0.0F, 0.0F);

        Mouth = new RendererModel(this, 42, 69);
        Mouth.addBox(-1.5F, -12.5F, 0.0F, 3, 9, 2);
        Mouth.setRotationPoint(0.0F, -8.0F, -6.0F);
        setRotation(Mouth, 2.530727F, 0.0F, 0.0F);

        LEar = new RendererModel(this, 13, 14);
        LEar.addBox(0.5F, -7.5F, -1.0F, 3, 5, 1);
        LEar.setRotationPoint(0.0F, -8.0F, -6.0F);
        setRotation(LEar, 0.0F, 0.0F, 0.1745329F);

        REar = new RendererModel(this, 22, 0);
        REar.addBox(-3.5F, -7.5F, -1.0F, 3, 5, 1);
        REar.setRotationPoint(0.0F, -8.0F, -6.0F);
        setRotation(REar, 0.0F, 0.0F, -0.1745329F);

        Neck = new RendererModel(this, 28, 0);
        Neck.addBox(-3.5F, -3.0F, -7.0F, 7, 8, 7);
        Neck.setRotationPoint(0.0F, -5.0F, -2.0F);
        setRotation(Neck, -0.6025001F, 0.0F, 0.0F);

        Neck2 = new RendererModel(this, 0, 14);
        Neck2.addBox(-1.5F, -2.0F, -5.0F, 3, 4, 7);
        Neck2.setRotationPoint(0.0F, -1.0F, -6.0F);
        setRotation(Neck2, -0.4537856F, 0.0F, 0.0F);

        SideburnL = new RendererModel(this, 28, 33);
        SideburnL.addBox(3.0F, 0.0F, -2.0F, 2, 6, 6);
        SideburnL.setRotationPoint(0.0F, -8.0F, -6.0F);
        setRotation(SideburnL, -0.2094395F, 0.418879F, -0.0872665F);

        SideburnR = new RendererModel(this, 28, 45);
        SideburnR.addBox(-5.0F, 0.0F, -2.0F, 2, 6, 6);
        SideburnR.setRotationPoint(0.0F, -8.0F, -6.0F);
        setRotation(SideburnR, -0.2094395F, -0.418879F, 0.0872665F);

        Chest = new RendererModel(this, 20, 15);
        Chest.addBox(-4.0F, 0.0F, -7.0F, 8, 8, 10);
        Chest.setRotationPoint(0.0F, -6.0F, -2.5F);
        setRotation(Chest, 0.641331F, 0.0F, 0.0F);

        Abdomen = new RendererModel(this, 0, 40);
        Abdomen.addBox(-3.0F, -8.0F, -8.0F, 6, 14, 8);
        Abdomen.setRotationPoint(0.0F, 4.5F, 5.0F);
        setRotation(Abdomen, 0.2695449F, 0.0F, 0.0F);

        TailA = new RendererModel(this, 52, 42);
        TailA.addBox(-1.5F, -1.0F, -2.0F, 3, 4, 3);
        TailA.setRotationPoint(0.0F, 9.5F, 6.0F);
        setRotation(TailA, 1.064651F, 0.0F, 0.0F);

        TailC = new RendererModel(this, 48, 59);
        TailC.addBox(-2.0F, 6.8F, -4.6F, 4, 6, 4);
        TailC.setRotationPoint(0.0F, 9.5F, 6.0F);
        setRotation(TailC, 1.099557F, 0.0F, 0.0F);

        TailB = new RendererModel(this, 48, 49);
        TailB.addBox(-2.0F, 2.0F, -2.0F, 4, 6, 4);
        TailB.setRotationPoint(0.0F, 9.5F, 6.0F);
        setRotation(TailB, 0.7504916F, 0.0F, 0.0F);

        TailD = new RendererModel(this, 52, 69);
        TailD.addBox(-1.5F, 9.8F, -4.1F, 3, 5, 3);
        TailD.setRotationPoint(0.0F, 9.5F, 6.0F);
        setRotation(TailD, 1.099557F, 0.0F, 0.0F);

        RLegA = new RendererModel(this, 12, 64);
        RLegA.addBox(-2.5F, -1.5F, -3.5F, 3, 8, 5);
        RLegA.setRotationPoint(-3.0F, 9.5F, 3.0F);
        setRotation(RLegA, -0.8126625F, 0.0F, 0.0F);

        RFoot = new RendererModel(this, 14, 93);
        RFoot.addBox(-2.506667F, 12.5F, -5.0F, 3, 2, 3);
        RFoot.setRotationPoint(-3.0F, 9.5F, 3.0F);

        RLegB = new RendererModel(this, 14, 76);
        RLegB.addBox(-1.9F, 4.2F, 0.5F, 2, 2, 5);
        RLegB.setRotationPoint(-3.0F, 9.5F, 3.0F);
        setRotation(RLegB, -0.8445741F, 0.0F, 0.0F);

        RLegC = new RendererModel(this, 14, 83);
        RLegC.addBox(-2.0F, 6.2F, 0.5F, 2, 8, 2);
        RLegC.setRotationPoint(-3.0F, 9.5F, 3.0F);
        setRotation(RLegC, -0.2860688F, 0.0F, 0.0F);

        LLegB = new RendererModel(this, 0, 76);
        LLegB.addBox(-0.1F, 4.2F, 0.5F, 2, 2, 5);
        LLegB.setRotationPoint(3.0F, 9.5F, 3.0F);
        setRotation(LLegB, -0.8445741F, 0.0F, 0.0F);

        LFoot = new RendererModel(this, 0, 93);
        LFoot.addBox(-0.5066667F, 12.5F, -5.0F, 3, 2, 3);
        LFoot.setRotationPoint(3.0F, 9.5F, 3.0F);

        LLegC = new RendererModel(this, 0, 83);
        LLegC.addBox(0.0F, 6.2F, 0.5F, 2, 8, 2);
        LLegC.setRotationPoint(3.0F, 9.5F, 3.0F);
        setRotation(LLegC, -0.2860688F, 0.0F, 0.0F);

        LLegA = new RendererModel(this, 0, 64);
        LLegA.addBox(-0.5F, -1.5F, -3.5F, 3, 8, 5);
        LLegA.setRotationPoint(3.0F, 9.5F, 3.0F);
        setRotation(LLegA, -0.8126625F, 0.0F, 0.0F);

        RArmB = new RendererModel(this, 48, 77);
        RArmB.addBox(-3.5F, 1.0F, -1.5F, 4, 8, 4);
        RArmB.setRotationPoint(-4.0F, -4.0F, -2.0F);
        setRotation(RArmB, 0.2617994F, 0.0F, 0.3490659F);

        RArmC = new RendererModel(this, 48, 112);
        RArmC.addBox(-6.0F, 5.0F, 3.0F, 4, 7, 4);
        RArmC.setRotationPoint(-4.0F, -4.0F, -2.0F);
        setRotation(RArmC, -0.3490659F, 0.0F, 0.0F);

        LArmB = new RendererModel(this, 48, 89);
        LArmB.addBox(-0.5F, 1.0F, -1.5F, 4, 8, 4);
        LArmB.setRotationPoint(4.0F, -4.0F, -2.0F);
        setRotation(LArmB, 0.2617994F, 0.0F, -0.3490659F);

        RHand = new RendererModel(this, 32, 118);
        RHand.addBox(-6.0F, 12.5F, -1.5F, 4, 3, 4);
        RHand.setRotationPoint(-4.0F, -4.0F, -2.0F);

        RArmA = new RendererModel(this, 0, 108);
        RArmA.addBox(-5.0F, -3.0F, -2.0F, 5, 5, 5);
        RArmA.setRotationPoint(-4.0F, -4.0F, -2.0F);
        setRotation(RArmA, 0.6320364F, 0.0F, 0.0F);

        LArmA = new RendererModel(this, 0, 98);
        LArmA.addBox(0.0F, -3.0F, -2.0F, 5, 5, 5);
        LArmA.setRotationPoint(4.0F, -4.0F, -2.0F);
        setRotation(LArmA, 0.6320364F, 0.0F, 0.0F);

        LArmC = new RendererModel(this, 48, 101);
        LArmC.addBox(2.0F, 5.0F, 3.0F, 4, 7, 4);
        LArmC.setRotationPoint(4.0F, -4.0F, -2.0F);
        setRotation(LArmC, -0.3490659F, 0.0F, 0.0F);

        LHand = new RendererModel(this, 32, 111);
        LHand.addBox(2.0F, 12.5F, -1.5F, 4, 3, 4);
        LHand.setRotationPoint(4.0F, -4.0F, -2.0F);

        RFinger1 = new RendererModel(this, 8, 120);
        RFinger1.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1);
        RFinger1.setRotationPoint(-6.5F, 11.5F, -0.5F);

        RFinger1 = new RendererModel(this, 8, 120);
        RFinger1.addBox(-3.0F, 15.5F, 1.0F, 1, 3, 1);
        RFinger1.setRotationPoint(-4.0F, -4.0F, -2.0F);

        RFinger2 = new RendererModel(this, 12, 124);
        RFinger2.addBox(-3.5F, 15.5F, -1.5F, 1, 3, 1);
        RFinger2.setRotationPoint(-4.0F, -4.0F, -2.0F);

        RFinger3 = new RendererModel(this, 12, 119);
        RFinger3.addBox(-4.8F, 15.5F, -1.5F, 1, 4, 1);
        RFinger3.setRotationPoint(-4.0F, -4.0F, -2.0F);

        RFinger4 = new RendererModel(this, 16, 119);
        RFinger4.addBox(-6.0F, 15.5F, -0.5F, 1, 4, 1);
        RFinger4.setRotationPoint(-4.0F, -4.0F, -2.0F);

        RFinger5 = new RendererModel(this, 16, 124);
        RFinger5.addBox(-6.0F, 15.5F, 1.0F, 1, 3, 1);
        RFinger5.setRotationPoint(-4.0F, -4.0F, -2.0F);

        LFinger1 = new RendererModel(this, 8, 124);
        LFinger1.addBox(2.0F, 15.5F, 1.0F, 1, 3, 1);
        LFinger1.setRotationPoint(4.0F, -4.0F, -2.0F);

        LFinger2 = new RendererModel(this, 0, 124);
        LFinger2.addBox(2.5F, 15.5F, -1.5F, 1, 3, 1);
        LFinger2.setRotationPoint(4.0F, -4.0F, -2.0F);

        LFinger3 = new RendererModel(this, 0, 119);
        LFinger3.addBox(3.8F, 15.5F, -1.5F, 1, 4, 1);
        LFinger3.setRotationPoint(4.0F, -4.0F, -2.0F);

        LFinger4 = new RendererModel(this, 4, 119);
        LFinger4.addBox(5.0F, 15.5F, -0.5F, 1, 4, 1);
        LFinger4.setRotationPoint(4.0F, -4.0F, -2.0F);

        LFinger5 = new RendererModel(this, 4, 124);
        LFinger5.addBox(5.0F, 15.5F, 1.0F, 1, 3, 1);
        LFinger5.setRotationPoint(4.0F, -4.0F, -2.0F);
    }

    @Override
    public void render(@Nonnull T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        Head.render(scale);
        Nose.render(scale);
        Snout.render(scale);
        TeethU.render(scale);
        TeethL.render(scale);
        Mouth.render(scale);
        LEar.render(scale);
        REar.render(scale);
        Neck.render(scale);
        Neck2.render(scale);
        SideburnL.render(scale);
        SideburnR.render(scale);
        Chest.render(scale);
        Abdomen.render(scale);
        TailA.render(scale);
        TailC.render(scale);
        TailB.render(scale);
        TailD.render(scale);
        RLegA.render(scale);
        RFoot.render(scale);
        RLegB.render(scale);
        RLegC.render(scale);
        LLegB.render(scale);
        LFoot.render(scale);
        LLegC.render(scale);
        LLegA.render(scale);
        RArmB.render(scale);
        RArmC.render(scale);
        LArmB.render(scale);
        RHand.render(scale);
        RArmA.render(scale);
        LArmA.render(scale);
        LArmC.render(scale);
        LHand.render(scale);
        RFinger1.render(scale);
        RFinger2.render(scale);
        RFinger3.render(scale);
        RFinger4.render(scale);
        RFinger5.render(scale);
        LFinger1.render(scale);
        LFinger2.render(scale);
        LFinger3.render(scale);
        LFinger4.render(scale);
        LFinger5.render(scale);
    }

    private void setRotation(RendererModel model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setVisible(boolean visible) {
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        float radianF = 57.29578F;
        float RLegXRot = MathHelper.cos(f * 0.6662F + 3.141593F) * 0.8F * f1;
        float LLegXRot = MathHelper.cos(f * 0.6662F) * 0.8F * f1;

        Head.rotateAngleY = (f3 / radianF);


        if (!hunched) {
            Head.rotationPointY = -8.0F;
            Head.rotationPointZ = -6.0F;
            Head.rotateAngleX = (f4 / radianF);
            Neck.rotateAngleX = (-34.0F / radianF);
            Neck.rotationPointY = -5.0F;
            Neck.rotationPointZ = -2.0F;
            Neck2.rotationPointY = -1.0F;
            Neck2.rotationPointZ = -6.0F;
            Chest.rotationPointY = -6.0F;
            Chest.rotationPointZ = -2.5F;
            Chest.rotateAngleX = (36.0F / radianF);
            Abdomen.rotateAngleX = (15.0F / radianF);
            LLegA.rotationPointZ = 3.0F;

            LArmA.rotationPointY = -4.0F;
            LArmA.rotationPointZ = -2.0F;

            TailA.rotationPointY = 9.5F;
            TailA.rotationPointZ = 6.0F;
        } else {
            Head.rotationPointY = 0.0F;
            Head.rotationPointZ = -11.0F;
            Head.rotateAngleX = ((15.0F + f4) / radianF);

            Neck.rotateAngleX = (-10.0F / radianF);
            Neck.rotationPointY = 2.0F;
            Neck.rotationPointZ = -6.0F;
            Neck2.rotationPointY = 9.0F;
            Neck2.rotationPointZ = -9.0F;
            Chest.rotationPointY = 1.0F;
            Chest.rotationPointZ = -7.5F;
            Chest.rotateAngleX = (60.0F / radianF);
            Abdomen.rotateAngleX = (75.0F / radianF);
            LLegA.rotationPointZ = 7.0F;
            LArmA.rotationPointY = 4.5F;
            LArmA.rotationPointZ = -6.0F;
            TailA.rotationPointY = 7.5F;
            TailA.rotationPointZ = 10.0F;
        }


        Nose.rotationPointY = Head.rotationPointY;
        Snout.rotationPointY = Head.rotationPointY;
        TeethU.rotationPointY = Head.rotationPointY;
        LEar.rotationPointY = Head.rotationPointY;
        REar.rotationPointY = Head.rotationPointY;
        TeethL.rotationPointY = Head.rotationPointY;
        Mouth.rotationPointY = Head.rotationPointY;
        SideburnL.rotationPointY = Head.rotationPointY;
        SideburnR.rotationPointY = Head.rotationPointY;

        Nose.rotationPointZ = Head.rotationPointZ;
        Snout.rotationPointZ = Head.rotationPointZ;
        TeethU.rotationPointZ = Head.rotationPointZ;
        LEar.rotationPointZ = Head.rotationPointZ;
        REar.rotationPointZ = Head.rotationPointZ;
        TeethL.rotationPointZ = Head.rotationPointZ;
        Mouth.rotationPointZ = Head.rotationPointZ;
        SideburnL.rotationPointZ = Head.rotationPointZ;
        SideburnR.rotationPointZ = Head.rotationPointZ;

        LArmB.rotationPointY = LArmA.rotationPointY;
        LArmC.rotationPointY = LArmA.rotationPointY;
        LHand.rotationPointY = LArmA.rotationPointY;
        LFinger1.rotationPointY = LArmA.rotationPointY;
        LFinger2.rotationPointY = LArmA.rotationPointY;
        LFinger3.rotationPointY = LArmA.rotationPointY;
        LFinger4.rotationPointY = LArmA.rotationPointY;
        LFinger5.rotationPointY = LArmA.rotationPointY;
        RArmA.rotationPointY = LArmA.rotationPointY;
        RArmB.rotationPointY = LArmA.rotationPointY;
        RArmC.rotationPointY = LArmA.rotationPointY;
        RHand.rotationPointY = LArmA.rotationPointY;
        RFinger1.rotationPointY = LArmA.rotationPointY;
        RFinger2.rotationPointY = LArmA.rotationPointY;
        RFinger3.rotationPointY = LArmA.rotationPointY;
        RFinger4.rotationPointY = LArmA.rotationPointY;
        RFinger5.rotationPointY = LArmA.rotationPointY;

        LArmB.rotationPointZ = LArmA.rotationPointZ;
        LArmC.rotationPointZ = LArmA.rotationPointZ;
        LHand.rotationPointZ = LArmA.rotationPointZ;
        LFinger1.rotationPointZ = LArmA.rotationPointZ;
        LFinger2.rotationPointZ = LArmA.rotationPointZ;
        LFinger3.rotationPointZ = LArmA.rotationPointZ;
        LFinger4.rotationPointZ = LArmA.rotationPointZ;
        LFinger5.rotationPointZ = LArmA.rotationPointZ;
        RArmA.rotationPointZ = LArmA.rotationPointZ;
        RArmB.rotationPointZ = LArmA.rotationPointZ;
        RArmC.rotationPointZ = LArmA.rotationPointZ;
        RHand.rotationPointZ = LArmA.rotationPointZ;
        RFinger1.rotationPointZ = LArmA.rotationPointZ;
        RFinger2.rotationPointZ = LArmA.rotationPointZ;
        RFinger3.rotationPointZ = LArmA.rotationPointZ;
        RFinger4.rotationPointZ = LArmA.rotationPointZ;
        RFinger5.rotationPointZ = LArmA.rotationPointZ;

        RLegA.rotationPointZ = LLegA.rotationPointZ;
        RLegB.rotationPointZ = LLegA.rotationPointZ;
        RLegC.rotationPointZ = LLegA.rotationPointZ;
        RFoot.rotationPointZ = LLegA.rotationPointZ;
        LLegB.rotationPointZ = LLegA.rotationPointZ;
        LLegC.rotationPointZ = LLegA.rotationPointZ;
        LFoot.rotationPointZ = LLegA.rotationPointZ;

        TailB.rotationPointY = TailA.rotationPointY;
        TailB.rotationPointZ = TailA.rotationPointZ;
        TailC.rotationPointY = TailA.rotationPointY;
        TailC.rotationPointZ = TailA.rotationPointZ;
        TailD.rotationPointY = TailA.rotationPointY;
        TailD.rotationPointZ = TailA.rotationPointZ;

        Nose.rotateAngleY = Head.rotateAngleY;
        Snout.rotateAngleY = Head.rotateAngleY;
        TeethU.rotateAngleY = Head.rotateAngleY;
        LEar.rotateAngleY = Head.rotateAngleY;
        REar.rotateAngleY = Head.rotateAngleY;
        TeethL.rotateAngleY = Head.rotateAngleY;
        Mouth.rotateAngleY = Head.rotateAngleY;

        TeethL.rotateAngleX = (Head.rotateAngleX + 2.530727F);
        Mouth.rotateAngleX = (Head.rotateAngleX + 2.530727F);

        SideburnL.rotateAngleX = (-0.2094395F + Head.rotateAngleX);
        SideburnL.rotateAngleY = (0.418879F + Head.rotateAngleY);
        SideburnR.rotateAngleX = (-0.2094395F + Head.rotateAngleX);
        SideburnR.rotateAngleY = (-0.418879F + Head.rotateAngleY);

        Nose.rotateAngleX = (0.2792527F + Head.rotateAngleX);
        Snout.rotateAngleX = Head.rotateAngleX;
        TeethU.rotateAngleX = Head.rotateAngleX;

        LEar.rotateAngleX = Head.rotateAngleX;
        REar.rotateAngleX = Head.rotateAngleX;

        RLegA.rotateAngleX = (-0.8126625F + RLegXRot);
        RLegB.rotateAngleX = (-0.8445741F + RLegXRot);
        RLegC.rotateAngleX = (-0.2860688F + RLegXRot);
        RFoot.rotateAngleX = RLegXRot;

        LLegA.rotateAngleX = (-0.8126625F + LLegXRot);
        LLegB.rotateAngleX = (-0.8445741F + LLegXRot);
        LLegC.rotateAngleX = (-0.2860688F + LLegXRot);
        LFoot.rotateAngleX = LLegXRot;

        RArmA.rotateAngleZ = (-(MathHelper.cos(f2 * 0.09F) * 0.05F) + 0.05F);
        LArmA.rotateAngleZ = (MathHelper.cos(f2 * 0.09F) * 0.05F - 0.05F);
        RArmA.rotateAngleX = LLegXRot;
        LArmA.rotateAngleX = RLegXRot;

        RArmB.rotateAngleZ = (0.3490659F + RArmA.rotateAngleZ);
        LArmB.rotateAngleZ = (-0.3490659F + LArmA.rotateAngleZ);
        RArmB.rotateAngleX = (0.2617994F + RArmA.rotateAngleX);
        LArmB.rotateAngleX = (0.2617994F + LArmA.rotateAngleX);

        RArmC.rotateAngleZ = RArmA.rotateAngleZ;
        LArmC.rotateAngleZ = LArmA.rotateAngleZ;
        RArmC.rotateAngleX = (-0.3490659F + RArmA.rotateAngleX);
        LArmC.rotateAngleX = (-0.3490659F + LArmA.rotateAngleX);

        RHand.rotateAngleZ = RArmA.rotateAngleZ;
        LHand.rotateAngleZ = LArmA.rotateAngleZ;
        RHand.rotateAngleX = RArmA.rotateAngleX;
        LHand.rotateAngleX = LArmA.rotateAngleX;

        RFinger1.rotateAngleX = RArmA.rotateAngleX;
        RFinger2.rotateAngleX = RArmA.rotateAngleX;
        RFinger3.rotateAngleX = RArmA.rotateAngleX;
        RFinger4.rotateAngleX = RArmA.rotateAngleX;
        RFinger5.rotateAngleX = RArmA.rotateAngleX;

        LFinger1.rotateAngleX = LArmA.rotateAngleX;
        LFinger2.rotateAngleX = LArmA.rotateAngleX;
        LFinger3.rotateAngleX = LArmA.rotateAngleX;
        LFinger4.rotateAngleX = LArmA.rotateAngleX;
        LFinger5.rotateAngleX = LArmA.rotateAngleX;

        RFinger1.rotateAngleZ = RArmA.rotateAngleZ;
        RFinger2.rotateAngleZ = RArmA.rotateAngleZ;
        RFinger3.rotateAngleZ = RArmA.rotateAngleZ;
        RFinger4.rotateAngleZ = RArmA.rotateAngleZ;
        RFinger5.rotateAngleZ = RArmA.rotateAngleZ;

        LFinger1.rotateAngleZ = LArmA.rotateAngleZ;
        LFinger2.rotateAngleZ = LArmA.rotateAngleZ;
        LFinger3.rotateAngleZ = LArmA.rotateAngleZ;
        LFinger4.rotateAngleZ = LArmA.rotateAngleZ;
        LFinger5.rotateAngleZ = LArmA.rotateAngleZ;
    }
}
