package de.teamlapen.werewolves.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.teamlapen.werewolves.api.WResourceLocation;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
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
public class WerewolfBeastModel<T extends LivingEntity> extends WerewolfBaseModel<T> {

    public static final String BODY = "body";
    public static final String HIP = "hip";
    public static final String NECK = "neck";
    public static final String ARM_LEFT = "armLeft";
    public static final String ARM_RIGHT = "armRight";
    public static final String BODY_FLUFF = "bodyFluff";
    public static final String LEG_LEFT = "legLeft";
    public static final String LEG_RIGHT = "legRight";
    public static final String TAIL = "tail";
    public static final String LEG_LEFT_2 = "legLeft2";
    public static final String FOOT_LEFT = "footLeft";
    public static final String LEG_RIGHT_2 = "legRight2";
    public static final String FOOT_RIGHT = "footRight";
    public static final String TAIL_2 = "tail2";
    public static final String TAIL_3 = "tail3";
    public static final String JOINT = "joint";
    public static final String NECK_FLUFF = "neckFluff";
    public static final String NECK_FLUFF_LEFT = "neckFluffLeft";
    public static final String NECK_FLUFF_RIGHT = "neckFluffRight";
    public static final String NECK_FLUFF_BOTTOM = "neckFluffBottom";
    public static final String HEAD = "head";
    public static final String EAR_LEFT = "earLeft";
    public static final String EAR_RIGHT = "earRight";
    public static final String SNOUT = "snout";
    public static final String JAW = "jaw";
    public static final String HEAD_FLUFF = "headFluff";
    public static final String HEAD_SIDBURN_LEFT = "headSidburnLeft";
    public static final String HEAD_SIDBURN_RIGHT = "headSidburnRight";
    public static final String EAR_LEFT_2 = "earLeft2";
    public static final String EAR_RIGHT_2 = "earRight2";
    public static final String NOSE = "nose";
    public static final String SNOUT_TEETH = "snoutTeeth";
    public static final String JAW_TEETH = "jawTeeth";
    public static final String JAW_FLUFF = "jawFluff";
    public static final String ARM_LEFT_2 = "armLeft2";
    public static final String FINGER_LEFT = "fingerLeft";
    public static final String FINGER_LEFT_2 = "fingerLeft2";
    public static final String FINGER_LEFT_3 = "fingerLeft3";
    public static final String FINGER_LEFT_4 = "fingerLeft4";
    public static final String THUMB_LEFT = "thumbLeft";
    public static final String ARM_RIGHT_2 = "armRight2";
    public static final String FINGER_RIGHT = "fingerRight";
    public static final String FINGER_RIGHT_2 = "fingerRight2";
    public static final String FINGER_RIGHT_3 = "fingerRight3";
    public static final String FINGER_RIGHT_4 = "fingerRight4";
    public static final String THUMB_RIGHT = "thumbRight";

    public final ModelPart body;
    public final ModelPart head;
    public final ModelPart rightArm;
    public final ModelPart leftArm;
    public final ModelPart armRight2;
    public final ModelPart armLeft2;
    public final ModelPart legLeft;
    public final ModelPart legRight;
    public final ModelPart hip;
    public final ModelPart tail;
    public final ModelPart jaw;
    public final ModelPart earLeft;
    public final ModelPart earRight;
    public final ModelPart joint;
    public final ModelPart neck;
    private final List<ModelPart> parts;

    public WerewolfBeastModel(ModelPart part) {
        super(part);
        this.body = part.getChild(BODY);
        this.hip = this.body.getChild(HIP);

        this.rightArm = this.body.getChild(ARM_RIGHT);
        this.leftArm = this.body.getChild(ARM_LEFT);
        this.legRight = this.hip.getChild(LEG_RIGHT);
        this.legLeft = this.hip.getChild(LEG_LEFT);
        this.armLeft2 = this.leftArm.getChild(ARM_LEFT_2);
        this.armRight2 = this.rightArm.getChild(ARM_RIGHT_2);

        this.tail = this.hip.getChild(TAIL);

        this.neck = this.body.getChild(NECK);
        this.joint = neck.getChild(JOINT);
        this.head = joint.getChild(HEAD);
        this.jaw = this.head.getChild(JAW);
        this.earLeft = this.head.getChild(EAR_LEFT);
        this.earRight = this.head.getChild(EAR_RIGHT);
        this.parts = part.getAllParts().filter(s -> !s.isEmpty()).toList();
    }

    @SuppressWarnings({"unused", "DuplicatedCode"})
    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = WerewolfBaseModel.createMesh(CubeDeformation.NONE);
        PartDefinition root = mesh.getRoot();
        PartDefinition body = root.addOrReplaceChild(BODY, CubeListBuilder.create().texOffs(0, 13).addBox(-4.5F, 0.0F, -4.0F, 9, 8, 8), PartPose.offsetAndRotation(0.0F, -4.0F, -2.5F, 0.5235987755982988F, 0.0F, 0.0F));
        PartDefinition neck = body.addOrReplaceChild(NECK, CubeListBuilder.create().texOffs(0, 45).addBox(-3.0F, -6.0F, -3.0F, 6, 7, 6), PartPose.offsetAndRotation(0.0F, 1.5F, 1.0F, 0.6981317007977318F, 0.0F, 0.0F));
        PartDefinition joint = neck.addOrReplaceChild(JOINT, CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 1, 1, 1), PartPose.offsetAndRotation(0.0F, -6.5F, -1.0F, -1.2217304763960306F, 0.0F, 0.0F));
        PartDefinition head = joint.addOrReplaceChild(HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.5F, -5.0F, 7, 7, 6), PartPose.offset(0.0F, 0.0F, 1.0F));
        PartDefinition snout = head.addOrReplaceChild(SNOUT, CubeListBuilder.create().texOffs(34, 0).addBox(-2.0F, -2.0F, -4.0F, 4, 2, 4), PartPose.offsetAndRotation(0.0F, 2.0F, -5.0F, -0.08726646259971647F, 0.0F, 0.0F));
        PartDefinition hip = body.addOrReplaceChild(HIP, CubeListBuilder.create().texOffs(0, 30).addBox(-3.5F, 0.0F, -3.0F, 7, 9, 6), PartPose.offsetAndRotation(0.0F, 6.0F, 1.0F, 0.5235987755982988F, 0.0F, 0.0F));
        PartDefinition headSidburnLeft = head.addOrReplaceChild(HEAD_SIDBURN_LEFT, CubeListBuilder.create().texOffs(32, 12).addBox(1.0F, -1.0F, -5.0F, 3, 6, 0), PartPose.rotation(0.0F, -0.5235987755982988F, 0.0F));
        PartDefinition legRight = hip.addOrReplaceChild(LEG_RIGHT, CubeListBuilder.create().texOffs(24, 39).addBox(-4.0F, -2.0F, -3.0F, 4, 8, 6), PartPose.offsetAndRotation(-0.5F, 9.0F, 0.0F, -0.3839724354387525F, 0.0F, 0.0F));
        PartDefinition jaw = head.addOrReplaceChild(JAW, CubeListBuilder.create().texOffs(50, 0).addBox(-1.5F, 0.0F, -3.5F, 3, 2, 4), PartPose.offsetAndRotation(0.0F, 1.5F, -5.0F, 1.2217304763960306F, 0.0F, 0.0F));
        PartDefinition jawFluff = jaw.addOrReplaceChild(JAW_FLUFF, CubeListBuilder.create().texOffs(96, 8).addBox(-1.5F, 2.0F, -3.5F, 3, 2, 4), PartPose.ZERO);
        PartDefinition neckFluff = neck.addOrReplaceChild(NECK_FLUFF, CubeListBuilder.create().texOffs(64, 19).addBox(-3.5F, -5.0F, 2.5F, 7, 8, 2), PartPose.rotation(0.2617993877991494F, 0.0F, 0.0F));
        PartDefinition tail = hip.addOrReplaceChild(TAIL, CubeListBuilder.create().texOffs(62, 30).addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3), PartPose.offsetAndRotation(0.0F, 7.0F, 2.0F, 0.7853981633974483F, 0.0F, 0.0F));
        PartDefinition earLeft = head.addOrReplaceChild(EAR_LEFT, CubeListBuilder.create().texOffs(26, 0).addBox(3.5F, -2.5F, -2.5F, 1, 4, 3), PartPose.rotation(-0.4886921905584123F, 0.3490658503988659F, 0.0F));
        PartDefinition legRight2 = legRight.addOrReplaceChild(LEG_RIGHT_2, CubeListBuilder.create().texOffs(44, 44).addBox(-3.5F, 2.0F, 3.0F, 3, 10, 4), PartPose.ZERO);
        PartDefinition footRight = legRight2.addOrReplaceChild(FOOT_RIGHT, CubeListBuilder.create().texOffs(24, 54).addBox(-2.0F, -1.5F, -4.0F, 4, 3, 6), PartPose.offsetAndRotation(-2.0F, 12.0F, 5.0F, 0.3839724354387525F, 0.0F, 0.0F));
        PartDefinition snoutTeeth = snout.addOrReplaceChild(SNOUT_TEETH, CubeListBuilder.create().texOffs(34, 6).addBox(-2.0F, 0.0F, -4.0F, 4, 2, 4), PartPose.ZERO);
        PartDefinition headSidburnRight = head.addOrReplaceChild(HEAD_SIDBURN_RIGHT, CubeListBuilder.create().texOffs(32, 12).addBox(-4.0F, -1.0F, -5.0F, 3, 6, 0).mirror(), PartPose.rotation(0.0F, 0.5235987755982988F, 0.0F));
        PartDefinition nose = snout.addOrReplaceChild(NOSE, CubeListBuilder.create().texOffs(64, 0).addBox(-1.5F, -3.5F, -3.7F, 3, 2, 5), PartPose.rotation(0.17453292519943295F, 0.0F, 0.0F));
        PartDefinition bodyFluff = body.addOrReplaceChild(BODY_FLUFF, CubeListBuilder.create().texOffs(82, 14).addBox(-4.5F, 8.0F, -4.0F, 9, 3, 8), PartPose.ZERO);
        PartDefinition tail2 = tail.addOrReplaceChild(TAIL_2, CubeListBuilder.create().texOffs(62, 37).addBox(-2.0F, 0.0F, -2.0F, 4, 8, 4), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, -0.3490658503988659F, 0.0F, 0.0F));
        PartDefinition tail3 = tail2.addOrReplaceChild(TAIL_3, CubeListBuilder.create().texOffs(62, 49).addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3), PartPose.offsetAndRotation(0.0F, 7.8F, 0.0F, 0.136659280431156F, 0.0F, 0.0F));
        PartDefinition neckFluffRight = neck.addOrReplaceChild(NECK_FLUFF_RIGHT, CubeListBuilder.create().texOffs(82, 25).addBox(-3.5F, -5.5F, 3.0F, 6, 8, 3), PartPose.rotation(0.3490658503988659F, -1.5707963267948966F, 0.0F));
        PartDefinition headFluff = head.addOrReplaceChild(HEAD_FLUFF, CubeListBuilder.create().texOffs(96, 0).addBox(-3.5F, 3.5F, -5.0F, 7, 2, 6), PartPose.ZERO);
        PartDefinition legLeft = hip.addOrReplaceChild(LEG_LEFT, CubeListBuilder.create().texOffs(24, 39).addBox(0.0F, -2.0F, -3.0F, 4, 8, 6), PartPose.offsetAndRotation(0.5F, 9.0F, 0.0F, -0.3839724354387525F, 0.0F, 0.0F));
        PartDefinition earRight = head.addOrReplaceChild(EAR_RIGHT, CubeListBuilder.create().texOffs(26, 0).addBox(-4.5F, -2.5F, -2.5F, 1, 4, 3), PartPose.rotation(-0.4886921905584123F, -0.3490658503988659F, 0.0F));
        PartDefinition earRight2 = earRight.addOrReplaceChild(EAR_RIGHT_2, CubeListBuilder.create().texOffs(26, 7).addBox(-4.0F, -5.5F, -2.0F, 1, 4, 2), PartPose.rotation(0.0F, 0.0F, -0.17453292519943295F));
        PartDefinition legLeft2 = legLeft.addOrReplaceChild(LEG_LEFT_2, CubeListBuilder.create().texOffs(44, 44).addBox(0.5F, 2.0F, 3.0F, 3, 10, 4).mirror(), PartPose.ZERO);
        PartDefinition footLeft = legLeft2.addOrReplaceChild(FOOT_LEFT, CubeListBuilder.create().texOffs(24, 54).addBox(-2.0F, -1.5F, -4.0F, 4, 3, 6).mirror(), PartPose.offsetAndRotation(2.0F, 12.0F, 5.0F, 0.3839724354387525F, 0.0F, 0.0F));
        PartDefinition jawTeeth = jaw.addOrReplaceChild(JAW_TEETH, CubeListBuilder.create().texOffs(50, 6).addBox(-1.5F, -1.0F, -3.5F, 3, 1, 4), PartPose.ZERO);
        PartDefinition neckFluffBottom = neck.addOrReplaceChild(NECK_FLUFF_BOTTOM, CubeListBuilder.create().texOffs(80, 0).addBox(-3.0F, -0.6F, 4.5F, 6, 6, 2), PartPose.rotation(1.0471975511965976F, 3.141592653589793F, 0.0F));
        PartDefinition earLeft2 = earLeft.addOrReplaceChild(EAR_LEFT_2, CubeListBuilder.create().texOffs(26, 7).addBox(3.0F, -5.5F, -2.0F, 1, 4, 2), PartPose.rotation(0.0F, 0.0F, 0.17453292519943295F));
        PartDefinition neckFluffLeft = neck.addOrReplaceChild(NECK_FLUFF_LEFT, CubeListBuilder.create().texOffs(82, 25).addBox(-2.5F, -5.5F, 3.0F, 6, 8, 3), PartPose.rotation(0.3490658503988659F, 1.5707963267948966F, 0.0F));

        PartDefinition armRight = body.addOrReplaceChild(ARM_RIGHT, CubeListBuilder.create().texOffs(36, 25).addBox(0.0F, -2.0F, -2.0F, 4.0F, 10.0F, 4.0F), PartPose.offsetAndRotation(4.5F, 3.0F, 0.0F, 0.0F, 0.0F, -0.1F));
        PartDefinition armRight2 = armRight.addOrReplaceChild(ARM_RIGHT_2, CubeListBuilder.create().texOffs(48, 15).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 10.0F, 4.0F), PartPose.offsetAndRotation(2.0F, 7.0F, 0.0F, -1.0F, 0.0F, 0.0F));
        PartDefinition thumbRight = armRight2.addOrReplaceChild(THUMB_RIGHT, CubeListBuilder.create().texOffs(0, 17).addBox(-0.5F, 0.0F, -0.5F, 2.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(-1.2F, 9.0F, -1.5F, -0.2618F, 0.0F, 0.1745F));
        PartDefinition fingerRight = armRight2.addOrReplaceChild(FINGER_RIGHT, CubeListBuilder.create().texOffs(0, 13).addBox(-1.5F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(1.2F, 9.0F, -1.5F, -0.2618F, 0.0F, -0.1745F));
        PartDefinition fingerRight2 = armRight2.addOrReplaceChild(FINGER_RIGHT_2, CubeListBuilder.create().texOffs(0, 13).addBox(-1.5F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(1.2F, 9.0F, -0.5F, -0.0873F, 0.0F, -0.1745F));
        PartDefinition fingerRight3 = armRight2.addOrReplaceChild(FINGER_RIGHT_3, CubeListBuilder.create().texOffs(0, 13).addBox(-1.5F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(1.2F, 9.0F, 0.5F, 0.0873F, 0.0F, -0.1745F));
        PartDefinition fingerRight4 = armRight2.addOrReplaceChild(FINGER_RIGHT_4, CubeListBuilder.create().texOffs(0, 13).addBox(-1.5F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(1.2F, 9.0F, 1.5F, 0.2618F, 0.0F, -0.1745F));
        PartDefinition armLeft = body.addOrReplaceChild(ARM_LEFT, CubeListBuilder.create().texOffs(36, 25).mirror().addBox(-4.0F, -2.0F, -2.0F, 4.0F, 10.0F, 4.0F).mirror(false), PartPose.offsetAndRotation(-4.5F, 3.0F, 0.0F, 0.0F, 0.0F, 0.1F));
        PartDefinition armLeft2 = armLeft.addOrReplaceChild(ARM_LEFT_2, CubeListBuilder.create().texOffs(48, 15).mirror().addBox(-1.5F, 0.0F, -2.0F, 3.0F, 10.0F, 4.0F).mirror(false), PartPose.offsetAndRotation(-2.0F, 7.0F, 0.0F, -1.0F, 0.0F, 0.0F));
        PartDefinition thumbLeft = armLeft2.addOrReplaceChild(THUMB_LEFT, CubeListBuilder.create().texOffs(0, 17).mirror().addBox(-1.5F, 0.0F, -0.5F, 2.0F, 2.0F, 1.0F).mirror(false), PartPose.offsetAndRotation(1.2F, 9.0F, -1.5F, -0.2618F, 0.0F, -0.1745F));
        PartDefinition fingerLeft = armLeft2.addOrReplaceChild(FINGER_LEFT, CubeListBuilder.create().texOffs(0, 13).mirror().addBox(-0.5F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F).mirror(false), PartPose.offsetAndRotation(-1.2F, 9.0F, -1.5F, -0.2618F, 0.0F, 0.1745F));
        PartDefinition fingerLeft2 = armLeft2.addOrReplaceChild(FINGER_LEFT_2, CubeListBuilder.create().texOffs(0, 13).mirror().addBox(-0.5F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F).mirror(false), PartPose.offsetAndRotation(-1.2F, 9.0F, -0.5F, -0.0873F, 0.0F, 0.1745F));
        PartDefinition fingerLeft3 = armLeft2.addOrReplaceChild(FINGER_LEFT_3, CubeListBuilder.create().texOffs(0, 13).mirror().addBox(-0.5F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F).mirror(false), PartPose.offsetAndRotation(-1.2F, 9.0F, 0.5F, 0.0873F, 0.0F, 0.1745F));
        PartDefinition fingerLeft4 = armLeft2.addOrReplaceChild(FINGER_LEFT_4, CubeListBuilder.create().texOffs(0, 13).mirror().addBox(-0.5F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F).mirror(false), PartPose.offsetAndRotation(-1.2F, 9.0F, 1.5F, 0.2618F, 0.0F, 0.1745F));

        return LayerDefinition.create(mesh, 128, 64);
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
        return this.armRight2;
    }

    @Nullable
    @Override
    public ModelPart getRightArmModel() {
        return this.armLeft2;
    }

    @Nonnull
    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body);
    }

    @Override
    public void renderToBuffer(@Nonnull PoseStack matrixStackIn, @Nonnull VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int color) {
        this.body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, color);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void setupAnim(@Nonnull T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.crouching = entityIn.isCrouching();
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

        this.rightArm.zRot = 0.0f;
        this.leftArm.zRot = 0.0f;
        this.armLeft2.xRot = -1f;
        this.armRight2.xRot = -1f;

        this.rightArm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.2F * limbSwingAmount * 0.5F;
        this.leftArm.xRot = Mth.cos(limbSwing * 0.6662F) * 1.2F * limbSwingAmount * 0.5F;

        this.rightArm.zRot += Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.leftArm.zRot -= Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.rightArm.xRot += Mth.sin(ageInTicks * 0.067F) * 0.05F;
        this.leftArm.xRot -= Mth.sin(ageInTicks * 0.067F) * 0.05F;

        this.legRight.xRot = -0.3839724354387525F;
        this.legLeft.xRot = -0.3839724354387525F;

        this.legRight.xRot += Mth.cos(limbSwing * 0.6662F * 0.7f) * 1.4F * limbSwingAmount;
        this.legLeft.xRot += Mth.cos(limbSwing * 0.6662F * 0.7f + (float) Math.PI) * 1.4F * limbSwingAmount;

        if (this.crouching) {
            this.body.xRot = 0.7235987755982988F;
            this.hip.xRot = -0.7235987755982988F;
        } else {
            this.body.xRot = 0.5235987755982988F;
            this.hip.xRot = -0.5235987755982988F;
        }

        //reset tail rotation angle
        this.tail.xRot = 0.7853981633974483F;

        //idle rotations
        this.tail.xRot -= 0.035f;
        this.tail.xRot += Mth.cos(ageInTicks * 0.10F) * 0.07F;

        this.tail.yRot = -0.035F;
        this.tail.yRot += Mth.sin(ageInTicks * 0.14F) * 0.07f;

        //running tail animation
        this.tail.xRot += Mth.cos(limbSwing * 0.6662F * 0.7f) * 0.3F * Mth.abs(limbSwingAmount) + 0.3f;
        this.tail.yRot += Mth.sin(limbSwing * 0.6662F * 0.7f) * 0.1F * limbSwingAmount;

        if (this.attackTime > 0.0F) {
            HumanoidArm humanoidarm = this.getAttackArm(entityIn);
            ModelPart modelpart = this.getArm(humanoidarm);
            float f = this.attackTime;
            this.neck.yRot = Mth.sin(Mth.sqrt(f) * ((float)Math.PI * 2F)) * 0.2F;
            if (humanoidarm == HumanoidArm.LEFT) {
                this.neck.yRot *= -1.0F;
            }

            f = 1.0F - this.attackTime;
            f *= f;
            f *= f;
            f = 1.0F - f;
            float f1 = Mth.sin(f * (float)Math.PI);
            float f2 = Mth.sin(this.attackTime * (float)Math.PI) * -(this.head.xRot - 0.7F) * 0.75F;
            modelpart.xRot -= f1 * 1.2F + f2;
            modelpart.yRot += this.body.yRot * 2.0F;
            float zChange = Mth.sin(this.attackTime * (float)Math.PI) * -0.4F;
            if (humanoidarm == HumanoidArm.LEFT) {
                modelpart.zRot -= zChange;
            } else {
                modelpart.zRot += zChange;
            }
        }

        this.jaw.xRot = 0.8217304763960306f;
        this.jaw.xRot += Mth.cos(ageInTicks * 0.1F) * 0.07F;

        this.earLeft.xRot = -0.4886921905584123F;
        this.earLeft.xRot += Mth.cos(ageInTicks * 0.1F) * 0.07F;

        this.earRight.xRot = -0.4886921905584123F;
        this.earRight.xRot += Mth.cos(ageInTicks * 0.1F) * 0.07F;
    }

    @Override
    public void translateToHand(@NotNull HumanoidArm arm, @NotNull PoseStack stack) {
        this.body.translateAndRotate(stack);

        ModelPart mainArm = arm == HumanoidArm.RIGHT ? this.leftArm : this.rightArm;
        mainArm.translateAndRotate(stack);

        ModelPart lowerArm = arm == HumanoidArm.RIGHT ? this.armLeft2 : this.armRight2;
        float f = arm == HumanoidArm.RIGHT ? 1 : -1;
        lowerArm.x += f;
        lowerArm.translateAndRotate(stack);
        lowerArm.x -= f;
    }

    public void translateToHead(PoseStack stack) {
        this.body.translateAndRotate(stack);
        this.neck.translateAndRotate(stack);
        this.joint.translateAndRotate(stack);
        this.head.translateAndRotate(stack);
    }

    @Nonnull
    public static List<ResourceLocation> getBeastTextures() {
        List<ResourceLocation> locs = getTextures("textures/entity/werewolf/beast");
        if (locs.size() < WerewolfForm.BEAST.getSkinTypes()) {
            for (int i = locs.size(); i < WerewolfForm.BEAST.getSkinTypes(); i++) {
                ResourceLocation s = WResourceLocation.mod("textures/entity/werewolf/beast/beast_" + i + ".png");
                locs.add(s);
            }
        }
        return locs;
    }

    @Override
    protected @NotNull ModelPart getArm(@NotNull HumanoidArm pSide) {
        return pSide == HumanoidArm.LEFT ? this.rightArm : this.leftArm;
    }

    @Override
    public @NotNull ModelPart getRandomModelPart(RandomSource pRandom) {
        return this.parts.get(pRandom.nextInt(this.parts.size()));
    }
}
