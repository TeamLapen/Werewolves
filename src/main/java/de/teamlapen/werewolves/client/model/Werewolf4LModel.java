package de.teamlapen.werewolves.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Model made by Rebel
 * Created using Tabula 7.1.0
 */
public class Werewolf4LModel<T extends LivingEntity> extends WerewolfBaseModel<T> {

    private static final String BODY = "body";
    private static final String HIP = "hip";
    private static final String NECK = "neck";
    private static final String ARM_LEFT = "armLeft";
    private static final String ARM_RIGHT = "armRight";
    private static final String BODY_FLUFF = "bodyFluff";
    private static final String LEG_LEFT = "legLeft";
    private static final String LEG_RIGHT = "legRight";
    private static final String TAIL_1 = "tail1";
    private static final String LEG_LEFT_2 = "legLeft2";
    private static final String FOOT_LEFT = "footLeft";
    private static final String LEG_RIGHT_2 = "legRight2";
    private static final String FOOT_RIGHT = "footRight";
    private static final String TAIL_2 = "tail2";
    private static final String TAIL_3 = "tail3";
    private static final String JOINT = "joint";
    private static final String NECK_FLUFF = "neckFluff";
    private static final String NECK_FLUFF_LEFT = "neckFluffLeft";
    private static final String NECK_FLUFF_RIGHT = "neckFluffRight";
    private static final String NECK_FLUFF_BOTTOM = "neckFluffBottom";
    private static final String HEAD = "head";
    private static final String EAR_LEFT = "earLeft";
    private static final String EAR_RIGHT = "earRight";
    private static final String SNOUT = "snout";
    private static final String JAW = "jaw";
    private static final String HEAD_FLUFF = "headFluff";
    private static final String HEAD_SIDEBURNS_LEFT = "headSideburnsLeft";
    private static final String HEAD_SIDEBURNS_RIGHT = "headSideburnsRight";
    private static final String EAR_LEFT_2 = "earLeft2";
    private static final String EAR_RIGHT_2 = "earRight2";
    private static final String NOSE = "nose";
    private static final String SNOUT_TEETH = "snoutTeeth";
    private static final String JAW_TEETH = "jawTeeth";
    private static final String JAW_FLUFF = "jawFluff";
    private static final String ARM_LEFT_2 = "armLeft2";
    private static final String FINGER_LEFT = "fingerLeft";
    private static final String FINGER_LEFT_2 = "fingerLeft2";
    private static final String FINGER_LEFT_3 = "fingerLeft3";
    private static final String FINGER_LEFT_4 = "fingerLeft4";
    private static final String THUMB_LEFT = "thumbLeft";
    private static final String ARM_RIGHT_2 = "armRight2";
    private static final String FINGER_RIGHT = "fingerRight";
    private static final String FINGER_RIGHT_2 = "fingerRight2";
    private static final String FINGER_RIGHT_3 = "fingerRight3";
    private static final String FINGER_RIGHT_4 = "fingerRight4";
    private static final String THUMB_RIGHT = "thumbRight";

    public final ModelPart body;
    public final ModelPart head;
    public final ModelPart armRight;
    public final ModelPart armLeft;
    public final ModelPart legRight;
    public final ModelPart legLeft;
    public final ModelPart tail1;
    public final ModelPart jaw;
    public final ModelPart earLeft;
    public final ModelPart earRight;

    public Werewolf4LModel(ModelPart part) {
        super(part);
        this.body = part.getChild(BODY);
        this.armLeft = this.body.getChild(ARM_LEFT);
        this.armRight = this.body.getChild(ARM_RIGHT);
        ModelPart hip = this.body.getChild(HIP);
        this.legLeft = hip.getChild(LEG_LEFT);
        this.legRight = hip.getChild(LEG_RIGHT);
        this.tail1 = hip.getChild(TAIL_1);
        ModelPart neck = this.body.getChild(NECK);
        ModelPart joint = neck.getChild(JOINT);
        this.head = joint.getChild(HEAD);
        this.earLeft = this.head.getChild(EAR_LEFT);
        this.earRight = this.head.getChild(EAR_RIGHT);
        this.jaw = this.head.getChild(JAW);
    }

    @SuppressWarnings({"unused", "DuplicatedCode"})
    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = WerewolfBaseModel.createMesh(CubeDeformation.NONE);
        PartDefinition part = mesh.getRoot();

        PartDefinition body = part.addOrReplaceChild(BODY, CubeListBuilder.create().texOffs(0, 13).addBox(-4.5F, 0.0F, -4.0F, 9.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.0F, -2.5F, 1.7453F, 0.0F, 0.0F));
        PartDefinition armRight = body.addOrReplaceChild(ARM_RIGHT, CubeListBuilder.create().texOffs(36, 25).addBox(0.0F, -2.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, 3.0F, 0.0F, -1.3963F, 0.0F, -0.0873F));
        PartDefinition armRight2 = armRight.addOrReplaceChild(ARM_RIGHT_2, CubeListBuilder.create().texOffs(48, 15).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 7.0F, 0.0F, -0.8727F, 0.0F, 0.0F));
        PartDefinition fingerRight = armRight2.addOrReplaceChild(FINGER_RIGHT, CubeListBuilder.create().texOffs(0, 13).addBox(-1.5F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.2F, 9.0F, -1.5F, -0.2618F, 0.0F, -0.1745F));
        PartDefinition fingerRight3 = armRight2.addOrReplaceChild(FINGER_RIGHT_3, CubeListBuilder.create().texOffs(0, 13).addBox(-1.5F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.2F, 9.0F, 0.5F, 0.0873F, 0.0F, -0.1745F));
        PartDefinition thumbRight = armRight2.addOrReplaceChild(THUMB_RIGHT, CubeListBuilder.create().texOffs(0, 17).addBox(-0.5F, 0.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.2F, 9.0F, -1.5F, -0.2618F, 0.0F, 0.1745F));
        PartDefinition fingerRight2 = armRight2.addOrReplaceChild(FINGER_RIGHT_2, CubeListBuilder.create().texOffs(0, 13).addBox(-1.5F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.2F, 9.0F, -0.5F, -0.0873F, 0.0F, -0.1745F));
        PartDefinition fingerRight4 = armRight2.addOrReplaceChild(FINGER_RIGHT_4, CubeListBuilder.create().texOffs(0, 13).addBox(-1.5F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.2F, 9.0F, 1.5F, 0.2618F, 0.0F, -0.1745F));
        PartDefinition armLeft = body.addOrReplaceChild(ARM_LEFT, CubeListBuilder.create().texOffs(36, 25).mirror().addBox(-4.0F, -2.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.5F, 3.0F, 0.0F, -1.3963F, 0.0F, 0.0873F));
        PartDefinition armLeft2 = armLeft.addOrReplaceChild(ARM_LEFT_2, CubeListBuilder.create().texOffs(48, 15).mirror().addBox(-1.5F, 0.0F, -2.0F, 3.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 7.0F, 0.0F, -0.8727F, 0.0F, 0.0F));
        PartDefinition fingerLeft4 = armLeft2.addOrReplaceChild(FINGER_LEFT_4, CubeListBuilder.create().texOffs(0, 13).mirror().addBox(-0.5F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.2F, 9.0F, 1.5F, 0.2618F, 0.0F, 0.1745F));
        PartDefinition fingerLeft3 = armLeft2.addOrReplaceChild(FINGER_LEFT_3, CubeListBuilder.create().texOffs(0, 13).mirror().addBox(-0.5F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.2F, 9.0F, 0.5F, 0.0873F, 0.0F, 0.1745F));
        PartDefinition thumbLeft = armLeft2.addOrReplaceChild(THUMB_LEFT, CubeListBuilder.create().texOffs(0, 17).mirror().addBox(-1.5F, 0.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.2F, 9.0F, -1.5F, -0.2618F, 0.0F, -0.1745F));
        PartDefinition fingerLeft2 = armLeft2.addOrReplaceChild(FINGER_LEFT_2, CubeListBuilder.create().texOffs(0, 13).mirror().addBox(-0.5F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.2F, 9.0F, -0.5F, -0.0873F, 0.0F, 0.1745F));
        PartDefinition fingerLeft = armLeft2.addOrReplaceChild(FINGER_LEFT, CubeListBuilder.create().texOffs(0, 13).mirror().addBox(-0.5F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.2F, 9.0F, -1.5F, -0.2618F, 0.0F, 0.1745F));
        PartDefinition neck = body.addOrReplaceChild(NECK, CubeListBuilder.create().texOffs(0, 45).addBox(-3.0F, -6.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, 1.0F, -0.4538F, 0.0F, 0.0F));
        PartDefinition neckFluff = neck.addOrReplaceChild(NECK_FLUFF, CubeListBuilder.create().texOffs(64, 19).addBox(-3.5F, -5.0F, 2.5F, 7.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));
        PartDefinition neckFluffLeft = neck.addOrReplaceChild(NECK_FLUFF_LEFT, CubeListBuilder.create().texOffs(82, 25).addBox(-3.5F, -5.5F, 3.0F, 6.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, -1.5708F, 0.0F));
        PartDefinition neckFluffRight = neck.addOrReplaceChild(NECK_FLUFF_RIGHT, CubeListBuilder.create().texOffs(82, 25).addBox(-2.5F, -5.5F, 3.0F, 6.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 1.5708F, 0.0F));
        PartDefinition neckFluffBottom = neck.addOrReplaceChild(NECK_FLUFF_BOTTOM, CubeListBuilder.create().texOffs(80, 0).addBox(-3.0F, -0.6F, 4.5F, 6.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.0472F, -3.1416F, 0.0F));
        PartDefinition joint = neck.addOrReplaceChild(JOINT, CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.5F, -1.0F, -1.2217F, 0.0F, 0.0F));
        PartDefinition head = joint.addOrReplaceChild(HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.5F, -5.0F, 7.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 1.0F));
        PartDefinition headSidburnRight = head.addOrReplaceChild(HEAD_SIDEBURNS_RIGHT, CubeListBuilder.create().texOffs(32, 12).mirror().addBox(1.0F, -1.0F, -5.0F, 3.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));
        PartDefinition snout = head.addOrReplaceChild(SNOUT, CubeListBuilder.create().texOffs(34, 0).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, -5.0F, -0.0873F, 0.0F, 0.0F));
        PartDefinition nose = snout.addOrReplaceChild(NOSE, CubeListBuilder.create().texOffs(64, 0).addBox(-1.5F, -3.5F, -3.7F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));
        PartDefinition snoutTeeth = snout.addOrReplaceChild(SNOUT_TEETH, CubeListBuilder.create().texOffs(34, 6).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition headSidburnLeft = head.addOrReplaceChild(HEAD_SIDEBURNS_LEFT, CubeListBuilder.create().texOffs(32, 12).addBox(-4.0F, -1.0F, -5.0F, 3.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));
        PartDefinition earRight = head.addOrReplaceChild(EAR_RIGHT, CubeListBuilder.create().texOffs(26, 0).addBox(3.5F, -2.5F, -2.5F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.4187F, 0.3491F, 0.0F));
        PartDefinition earRight2 = earRight.addOrReplaceChild(EAR_RIGHT_2, CubeListBuilder.create().texOffs(26, 7).addBox(3.0F, -5.5F, -2.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));
        PartDefinition jaw = head.addOrReplaceChild(JAW, CubeListBuilder.create().texOffs(50, 0).addBox(-1.5F, 0.0F, -3.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, -5.0F, 1.2917F, 0.0F, 0.0F));
        PartDefinition jawTeeth = jaw.addOrReplaceChild(JAW_TEETH, CubeListBuilder.create().texOffs(50, 6).addBox(-1.5F, -1.0F, -3.5F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition jawFluff = jaw.addOrReplaceChild(JAW_FLUFF, CubeListBuilder.create().texOffs(96, 8).addBox(-1.5F, 2.0F, -3.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition headFluff = head.addOrReplaceChild(HEAD_FLUFF, CubeListBuilder.create().texOffs(96, 0).addBox(-3.5F, 3.5F, -5.0F, 7.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition earLeft = head.addOrReplaceChild(EAR_LEFT, CubeListBuilder.create().texOffs(26, 0).addBox(-4.5F, -2.5F, -2.5F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.4187F, -0.3491F, 0.0F));
        PartDefinition earLeft2 = earLeft.addOrReplaceChild(EAR_LEFT_2, CubeListBuilder.create().texOffs(26, 7).addBox(-4.0F, -5.5F, -2.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));
        PartDefinition bodyFluff = body.addOrReplaceChild(BODY_FLUFF, CubeListBuilder.create().texOffs(82, 14).addBox(-4.5F, 8.0F, -4.0F, 9.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition hip = body.addOrReplaceChild(HIP, CubeListBuilder.create().texOffs(0, 30).addBox(-3.5F, 0.0F, -3.0F, 7.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 1.0F, -0.5236F, 0.0F, 0.0F));
        PartDefinition legRight = hip.addOrReplaceChild(LEG_RIGHT, CubeListBuilder.create().texOffs(24, 39).addBox(0.0F, -2.0F, -3.0F, 4.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 9.0F, 0.0F, -1.309F, 0.0F, 0.0F));
        PartDefinition legRight2 = legRight.addOrReplaceChild(LEG_RIGHT_2, CubeListBuilder.create().texOffs(44, 44).addBox(0.5F, 2.0F, 3.0F, 3.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition footRight = legRight2.addOrReplaceChild(FOOT_RIGHT, CubeListBuilder.create().texOffs(24, 54).addBox(-2.0F, -1.5F, -4.0F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 12.0F, 5.0F, 0.0873F, 0.0F, 0.0F));
        PartDefinition legLeft = hip.addOrReplaceChild(LEG_LEFT, CubeListBuilder.create().texOffs(24, 39).addBox(-4.0F, -2.0F, -3.0F, 4.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 9.0F, 0.0F, -1.309F, 0.0F, 0.0F));
        PartDefinition legLeft2 = legLeft.addOrReplaceChild(LEG_LEFT_2, CubeListBuilder.create().texOffs(44, 44).mirror().addBox(-3.5F, 2.0F, 3.0F, 3.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition footLeft = legLeft2.addOrReplaceChild(FOOT_LEFT, CubeListBuilder.create().texOffs(24, 54).mirror().addBox(-2.0F, -1.5F, -4.0F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 12.0F, 5.0F, 0.0873F, 0.0F, 0.0F));
        PartDefinition tail1 = hip.addOrReplaceChild(TAIL_1, CubeListBuilder.create().texOffs(62, 30).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 2.0F, 0.3806F, 0.035F, 0.0F));
        PartDefinition tail2 = tail1.addOrReplaceChild(TAIL_2, CubeListBuilder.create().texOffs(62, 37).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, -0.3491F, 0.0F, 0.0F));
        PartDefinition tail3 = tail2.addOrReplaceChild(TAIL_3, CubeListBuilder.create().texOffs(62, 49).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.8F, 0.0F, 0.1367F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 128, 64);
    }

    @Override
    public void setupAnim(@Nonnull T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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

        this.armRight.xRot = -1.3962634015954636F;
        this.armLeft.xRot = -1.3962634015954636F;
        this.legRight.xRot = -1.3089969389957472F;
        this.legLeft.xRot = -1.3089969389957472F;

        this.armRight.xRot += Mth.cos(limbSwing * 0.6662F * 0.7f) * limbSwingAmount;
        this.armLeft.xRot += Mth.cos(limbSwing * 0.6662F * 0.7f + (float) Math.PI) * limbSwingAmount;
        this.legRight.xRot += Mth.cos(limbSwing * 0.6662F * 0.7f) * limbSwingAmount;
        this.legLeft.xRot += Mth.cos(limbSwing * 0.6662F * 0.7f + (float) Math.PI) * limbSwingAmount;

        //reset tail rotation angle
        this.tail1.xRot = 0.045553093477052F;

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
    protected @NotNull ModelPart getArm(@NotNull HumanoidArm pSide) {
        return pSide == HumanoidArm.LEFT ? this.armRight : this.armLeft;
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

    @Nonnull
    public static List<ResourceLocation> get4LTextures() {
        return WerewolfBeastModel.getBeastTextures();
    }
}
