package de.teamlapen.werewolves.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * WerewolfSurvivalistModel - Rebel
 * Created using Tabula 7.1.0
 */
@OnlyIn(Dist.CLIENT)
public class WerewolfSurvivalistModel<T extends LivingEntity> extends WerewolfBaseModel<T> {
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
    public static final String HEAD_SIDEBURNS_LEFT = "headSideburnsLeft";
    public static final String HEAD_SIDEBURNS_RIGHT = "headSideburnsRight";
    public static final String NOSE = "nose";
    public static final String SNOUT_TEETH = "snoutTeeth";
    public static final String JAW_TEETH = "jawTeeth";
    public static final String JAW_FLUFF = "jawFluff";
    public static final String ARM_LEFT_2 = "armLeft2";
    public static final String FOOT_LEFT_1 = "footLeft_1";
    public static final String ARM_RIGHT_2 = "armRight2";
    public static final String FOOT_LEFT_2 = "footLeft_2";

    public final ModelPart head;
    public final ModelPart body;
    public final ModelPart armLeft;
    public final ModelPart armRight;
    public final ModelPart legRight;
    public final ModelPart legLeft;
    public final ModelPart tail;
    public final ModelPart jaw;
    public final ModelPart jawTeeth;
    public final ModelPart neck;
    public final ModelPart joint;


    public WerewolfSurvivalistModel(ModelPart part) {
        super(part);
        this.body = part.getChild(BODY);
        ModelPart hip = this.body.getChild("hip");
        this.tail = hip.getChild(TAIL);
        this.legRight = hip.getChild(LEG_RIGHT);
        this.legLeft = hip.getChild(LEG_LEFT);
        this.armLeft = this.body.getChild(ARM_LEFT);
        this.armRight = this.body.getChild(ARM_RIGHT);
        this.neck = this.body.getChild("neck");
        this.joint = neck.getChild("joint");
        this.head = joint.getChild(HEAD);
        this.jaw = this.head.getChild(JAW);
        this.jawTeeth = jaw.getChild(JAW_TEETH);
    }

    @Nonnull
    @SuppressWarnings({"unused", "DuplicatedCode"})
    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = WerewolfBaseModel.createMesh(CubeDeformation.NONE);
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild(BODY, CubeListBuilder.create().texOffs(0, 13).addBox(-4.5F, 0.0F, -4.0F, 9, 8, 8), PartPose.offsetAndRotation(0.0F, 9.5F, -2.5F, 1.5707963267948966F, 0.0F, 0.0F));
        PartDefinition neck = body.addOrReplaceChild(NECK, CubeListBuilder.create().texOffs(0, 45).addBox(-3.0F, -6.0F, -3.0F, 6, 7, 6), PartPose.offsetAndRotation(0.0F, 1.5F, 1.0F, -0.3141592653589793F, 0.0F, 0.0F));
        PartDefinition joint = neck.addOrReplaceChild(JOINT, CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 1, 1, 1), PartPose.offsetAndRotation(0.0F, -6.5F, -1.0F, -1.2217304763960306F, 0.0F, 0.0F));
        PartDefinition head = joint.addOrReplaceChild(HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.5F, -5.0F, 7, 7, 6), PartPose.offset(0.0F, 0.0F, 1.0F));
        PartDefinition snout = head.addOrReplaceChild(SNOUT, CubeListBuilder.create().texOffs(34, 0).addBox(-2.0F, -2.0F, -4.0F, 4, 2, 4), PartPose.offset(0.0F, 2.0F, -5.0F));
        PartDefinition jaw = head.addOrReplaceChild(JAW, CubeListBuilder.create().texOffs(50, 0).addBox(-1.5F, 0.0F, -3.5F, 3, 2, 4), PartPose.offsetAndRotation(0.0F, 1.5F, -5.0F, 0.4553564018453205F, 0.0F, 0.0F));
        PartDefinition hip = body.addOrReplaceChild(HIP, CubeListBuilder.create().texOffs(0, 30).addBox(-3.5F, 0.0F, -3.0F, 7, 9, 6), PartPose.offset(0.0F, 6.0F, -0.9F));
        PartDefinition armLeft = body.addOrReplaceChild(ARM_LEFT, CubeListBuilder.create().texOffs(36, 25).addBox(0.0F, -2.0F, -2.0F, 4, 8, 4).mirror(), PartPose.offsetAndRotation(1.5F, 3.0F, -1.5F, 1.2217304763960306F, 0.0F, 0.0F));
        PartDefinition armLeft2 = armLeft.addOrReplaceChild(ARM_LEFT_2, CubeListBuilder.create().texOffs(48, 15).addBox(-1.5F, 0.0F, -2.0F, 3, 8, 4).mirror(), PartPose.offsetAndRotation(2.0F, 5.0F, 0.0F, -0.3490658503988659F, 0.0F, 0.0F));
        PartDefinition footLeft_1 = armLeft2.addOrReplaceChild(FOOT_LEFT_1, CubeListBuilder.create().texOffs(24, 54).addBox(-2.0F, -1.5F, -4.0F, 4, 3, 6).mirror(), PartPose.offset(0.0F, 7.0F, -0.5F));
        PartDefinition snoutTeeth = snout.addOrReplaceChild(SNOUT_TEETH, CubeListBuilder.create().texOffs(34, 6).addBox(-2.0F, 0.0F, -4.0F, 4, 2, 4), PartPose.ZERO);
        PartDefinition bodyFluff = body.addOrReplaceChild(BODY_FLUFF, CubeListBuilder.create().texOffs(82, 14).addBox(-4.5F, 8.0F, -4.0F, 9, 3, 8), PartPose.ZERO);
        PartDefinition legRight = hip.addOrReplaceChild(LEG_RIGHT, CubeListBuilder.create().texOffs(24, 39).addBox(-4.0F, -2.0F, -3.0F, 4, 8, 6), PartPose.offsetAndRotation(-0.5F, 8.0F, 0.0F, -1.5707963267948966F, 0.0F, 0.0F));
        PartDefinition legLeft = hip.addOrReplaceChild(LEG_LEFT, CubeListBuilder.create().texOffs(24, 39).addBox(0.0F, -2.0F, -3.0F, 4, 8, 6), PartPose.offsetAndRotation(0.5F, 8.0F, 0.0F, -1.5707963267948966F, 0.0F, 0.0F));
        PartDefinition legLeft2 = legLeft.addOrReplaceChild(LEG_LEFT_2, CubeListBuilder.create().texOffs(44, 44).addBox(0.5F, 2.0F, 1.0F, 3, 10, 4).mirror(), PartPose.ZERO);
        PartDefinition tail = hip.addOrReplaceChild(TAIL, CubeListBuilder.create().texOffs(62, 30).addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3), PartPose.offsetAndRotation(0.0F, 7.0F, 2.0F, -0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition tail2 = tail.addOrReplaceChild(TAIL_2, CubeListBuilder.create().texOffs(62, 37).addBox(-2.0F, 0.0F, -2.0F, 4, 8, 4), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, -0.3490658503988659F, 0.0F, 0.0F));
        PartDefinition tail3 = tail2.addOrReplaceChild(TAIL_3, CubeListBuilder.create().texOffs(62, 49).addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3), PartPose.offsetAndRotation(0.0F, 7.8F, 0.0F, 0.136659280431156F, 0.0F, 0.0F));
        PartDefinition headSideburnsLeft = head.addOrReplaceChild(HEAD_SIDEBURNS_LEFT, CubeListBuilder.create().texOffs(32, 12).addBox(1.0F, -1.0F, -5.0F, 3, 6, 0), PartPose.rotation(0.0F, -0.5235987755982988F, 0.0F));
        PartDefinition neckFluffRight = neck.addOrReplaceChild(HEAD_SIDEBURNS_RIGHT, CubeListBuilder.create().texOffs(82, 25).addBox(-3.5F, -5.5F, 3.0F, 6, 8, 3), PartPose.rotation(0.3490658503988659F, -1.5707963267948966F, 0.0F));
        PartDefinition footLeft = legLeft2.addOrReplaceChild(FOOT_LEFT, CubeListBuilder.create().texOffs(24, 54).addBox(-2.0F, -1.5F, -4.0F, 4, 3, 6).mirror(), PartPose.offset(2.0F, 12.0F, 2.5F));
        PartDefinition headFluff = head.addOrReplaceChild(HEAD_FLUFF, CubeListBuilder.create().texOffs(96, 0).addBox(-3.5F, 3.5F, -5.0F, 7, 2, 6), PartPose.ZERO);
        PartDefinition legRight2 = legRight.addOrReplaceChild(LEG_RIGHT_2, CubeListBuilder.create().texOffs(44, 44).addBox(-3.5F, 2.0F, 1.0F, 3, 10, 4), PartPose.ZERO);
        PartDefinition earRight = head.addOrReplaceChild(EAR_RIGHT, CubeListBuilder.create().texOffs(26, 0).addBox(-3.5F, -6.5F, -1.0F, 2, 3, 1), PartPose.ZERO);
        PartDefinition headSideburnsRight = head.addOrReplaceChild(HEAD_SIDEBURNS_RIGHT, CubeListBuilder.create().texOffs(32, 12).addBox(-4.0F, -1.0F, -5.0F, 3, 6, 0).mirror(), PartPose.rotation(0.0F, 0.5235987755982988F, 0.0F));
        PartDefinition jawFluff = jaw.addOrReplaceChild(JAW_FLUFF, CubeListBuilder.create().texOffs(96, 8).addBox(-1.5F, 2.0F, -3.5F, 3, 2, 4), PartPose.ZERO);
        PartDefinition footRight = legRight2.addOrReplaceChild(FOOT_RIGHT, CubeListBuilder.create().texOffs(24, 54).addBox(-2.0F, -1.5F, -4.0F, 4, 3, 6), PartPose.offset(-2.0F, 12.0F, 2.5F));
        PartDefinition neckFluff = neck.addOrReplaceChild(NECK_FLUFF, CubeListBuilder.create().texOffs(64, 19).addBox(-3.5F, -5.0F, 2.5F, 7, 8, 2), PartPose.rotation(0.2617993877991494F, 0.0F, 0.0F));
        PartDefinition armRight = body.addOrReplaceChild(ARM_RIGHT, CubeListBuilder.create().texOffs(36, 25).addBox(-4.0F, -2.0F, -2.0F, 4, 8, 4), PartPose.offsetAndRotation(-1.5F, 3.0F, -1.5F, -1.2217304763960306F, 0.0F, 0.0F));
        PartDefinition armRight2 = armRight.addOrReplaceChild(ARM_RIGHT_2, CubeListBuilder.create().texOffs(48, 15).addBox(-1.5F, 0.0F, -2.0F, 3, 8, 4), PartPose.offsetAndRotation(-2.0F, 5.0F, 0.0F, -0.3490658503988659F, 0.0F, 0.0F));
        PartDefinition neckFluffLeft = neck.addOrReplaceChild(NECK_FLUFF_LEFT, CubeListBuilder.create().texOffs(82, 25).addBox(-2.5F, -5.5F, 3.0F, 6, 8, 3), PartPose.rotation(0.3490658503988659F, 1.5707963267948966F, 0.0F));
        PartDefinition neckFluffBottom = neck.addOrReplaceChild(NECK_FLUFF_BOTTOM, CubeListBuilder.create().texOffs(80, 0).addBox(-3.0F, -0.6F, 4.5F, 6, 6, 2), PartPose.rotation(1.0471975511965976F, 3.141592653589793F, 0.0F));
        PartDefinition earLeft = head.addOrReplaceChild(EAR_LEFT, CubeListBuilder.create().texOffs(26, 0).addBox(1.5F, -6.5F, -1.0F, 2, 3, 1).mirror(), PartPose.ZERO);
        PartDefinition nose = snout.addOrReplaceChild(NOSE, CubeListBuilder.create().texOffs(64, 0).addBox(-1.5F, -3.5F, -3.7F, 3, 2, 5), PartPose.rotation(0.17453292519943295F, 0.0F, 0.0F));
        PartDefinition jawTeeth = jaw.addOrReplaceChild(JAW_TEETH, CubeListBuilder.create().texOffs(50, 6).addBox(-1.5F, -1.0F, -3.5F, 3, 1, 4), PartPose.ZERO);
        PartDefinition footLeft_2 = armRight2.addOrReplaceChild(FOOT_LEFT_2, CubeListBuilder.create().texOffs(24, 54).addBox(-2.0F, -1.5F, -4.0F, 4, 3, 6), PartPose.offset(0.0F, 7.0F, -0.5F));

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
        return this.armLeft;
    }

    @Nullable
    @Override
    public ModelPart getRightArmModel() {
        return this.armRight;
    }

    @Nonnull
    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body);
    }

    @Override
    public void renderToBuffer(@Nonnull PoseStack matrixStackIn, @Nonnull VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @SuppressWarnings("DuplicatedCode")
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

        setupAttackAnimation(entityIn, ageInTicks);

        this.legRight.xRot = -1.5707963267948966F;
        this.legLeft.xRot = -1.5707963267948966F;

        this.armRight.xRot = -1.2217304763960306F;
        this.armLeft.xRot = -1.2217304763960306F;

        this.legRight.xRot += Mth.cos(limbSwing * 0.6662F * 0.8f + 1.5f * (float) Math.PI) * 0.8F * limbSwingAmount;
        this.legLeft.xRot += Mth.cos(limbSwing * 0.6662F * 0.8f + 0.5f * (float) Math.PI) * 0.8F * limbSwingAmount;

        this.armRight.xRot += Mth.cos(limbSwing * 0.6662F * 0.8f) * 0.8F * limbSwingAmount;
        this.armLeft.xRot += Mth.cos(limbSwing * 0.6662F * 0.8f + (float) Math.PI) * 0.8F * limbSwingAmount;


        //reset tail rotation angle
        this.tail.xRot = -0.22759093446006054F;

        //idle rotations
        this.tail.xRot -= 0.035f;
        this.tail.xRot += Mth.cos(ageInTicks * 0.10F) * 0.07F;

        this.tail.yRot = -0.035F;
        this.tail.yRot += Mth.sin(ageInTicks * 0.14F) * 0.07f;

        //running tail animation
        this.tail.xRot += Mth.cos(limbSwing * 0.6662F * 0.7f) * 0.3F * Mth.abs(limbSwingAmount) + 0.3f;
        this.tail.yRot += Mth.sin(limbSwing * 0.6662F * 0.7f) * 0.1F * limbSwingAmount;
    }

    public void translateToMouth(@NotNull HumanoidArm arm, @NotNull PoseStack stack) {
        this.body.translateAndRotate(stack);
        this.neck.translateAndRotate(stack);
        this.joint.translateAndRotate(stack);
        this.head.translateAndRotate(stack);
        this.jaw.translateAndRotate(stack);
    }

    protected void setupAttackAnimation(T entity, float ageInTicks) {
        if (this.attackTime > 0) {
            float f = this.attackTime;
            f = 1.0F - f;
            head.xRot += f;
        }
    }

    @Nonnull
    public static List<ResourceLocation> getSurvivalTextures() {
        List<ResourceLocation> locs = Minecraft.getInstance().getResourceManager().listResources("textures/entity/werewolf/survivalist", s -> s.getPath().endsWith(".png")).keySet().stream().filter(r -> REFERENCE.MODID.equals(r.getNamespace())).collect(Collectors.toList());
        if (locs.size() < WerewolfForm.SURVIVALIST.getSkinTypes()) {
            for (int i = 0; i < WerewolfForm.SURVIVALIST.getSkinTypes(); i++) {
                ResourceLocation s = new ResourceLocation(REFERENCE.MODID, "textures/entity/werewolf/survivalist/survivalist_" + i + ".png");
                if (!locs.contains(s)) {
                    locs.add(s);
                }
            }
        }
        return locs;
    }

}
