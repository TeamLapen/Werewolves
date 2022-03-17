package de.teamlapen.werewolves.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * WerewolfEarsModel - RebelT
 * Created using Tabula 7.1.0
 */
public class WerewolfEarsModel<T extends LivingEntity> extends WerewolfBaseModel<T> {

    public static String CLAWS_LEFT = "clawsLeft";
    public static String CLAWS_RIGHT = "clawsRight";
    public static String EAR_RIGHT = "earRight";
    public static String EAR_LEFT = "earLeft";

    public ModelPart clawsLeft;
    public ModelPart clawsRight;
    public ModelPart earRight;
    public ModelPart earLeft;

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = WerewolfBaseModel.createMesh(CubeDeformation.NONE);
        PartDefinition root = mesh.getRoot();

        PartDefinition head = root.getChild("head");
        PartDefinition earRight = head.addOrReplaceChild(EAR_RIGHT, CubeListBuilder.create().texOffs(16, 0).addBox(-4.5F, -8.0F, -2.5F, 1, 6, 3), PartPose.rotation(-0.4886921905584123F, -0.2617993877991494F, 0.0F));
        PartDefinition earLeft = head.addOrReplaceChild(EAR_LEFT, CubeListBuilder.create().texOffs(16, 0).addBox(3.5F, -8.0F, -2.5F, 1, 6, 3), PartPose.rotation(-0.4886921905584123F, 0.2617993877991494F, 0.0F));

        PartDefinition clawsRight = root.getChild("right_arm").addOrReplaceChild(CLAWS_RIGHT, CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, 10.0F, -2.0F, 4, 3, 4), PartPose.ZERO);
        PartDefinition clawsLeft = root.getChild("left_arm").addOrReplaceChild(CLAWS_LEFT, CubeListBuilder.create().texOffs(0, 7).addBox(-1F, 10.0F, -2.0F, 4, 3, 4), PartPose.ZERO);
        return LayerDefinition.create(mesh, 64, 32);
    }

    public WerewolfEarsModel(ModelPart part) {
        super(part);
        ModelPart head = part.getChild("head");
        this.earRight = head.getChild(EAR_RIGHT);
        this.earLeft = head.getChild(EAR_LEFT);
        ModelPart rightArm = part.getChild("right_arm");
        ModelPart leftArm = part.getChild("left_arm");
        this.clawsRight = rightArm.getChild(CLAWS_RIGHT);
        this.clawsLeft = leftArm.getChild(CLAWS_LEFT);
    }

    @Nullable
    @Override
    public ModelPart getModelRenderer() {
        return null;
    }

    @Nullable
    @Override
    public ModelPart getHeadModel() {
        return null;
    }

    @Nullable
    @Override
    public ModelPart getLeftArmModel() {
        return this.clawsLeft;
    }

    @Nullable
    @Override
    public ModelPart getRightArmModel() {
        return this.clawsRight;
    }

    @Nonnull
    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.clawsRight, this.clawsLeft);
    }

    @Nonnull
    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.rightArm, this.leftArm);
    }

    @Override
    public void renderToBuffer(@Nonnull PoseStack matrixStackIn, @Nonnull VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.head.copyFrom(this.playerModel.head);
        this.rightArm.copyFrom(this.playerModel.rightArm);
        this.leftArm.copyFrom(this.playerModel.leftArm);
        super.renderToBuffer(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @SuppressWarnings("SameParameterValue")
    private void setRotateAngle(ModelPart ModelRenderer, float x, float y, float z) {
        ModelRenderer.xRot = x;
        ModelRenderer.yRot = y;
        ModelRenderer.zRot = z;
    }

}
