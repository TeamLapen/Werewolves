package de.teamlapen.werewolves.client.model.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

/**
 * Wolf head helmet model.
 * <p>
 * Since 1.21.2 armor models have to be {@link HumanoidModel}s that are posed by the armor layer, so the wolf head is attached to an otherwise empty humanoid skeleton.
 */
public class WolfHeadModel extends HumanoidModel<HumanoidRenderState> {

    private static final String WOLF_HEAD = "wolf_head";
    private static final String RIGHT_EAR = "rightEar";
    private static final String RIGHT_EAR2 = "rightEar2";
    private static final String LEFT_EAR = "leftEar";
    private static final String LEFT_EAR2 = "leftEar2";
    private static final String NOSE = "nose";

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition humanoidHead = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
        humanoidHead.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5.0F, 2.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5.0F, 2.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-1.9F, 12.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(1.9F, 12.0F, 0.0F));

        PartDefinition head = humanoidHead.addOrReplaceChild(WOLF_HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.ZERO.withScale(1.2F));
        PartDefinition rightEar = head.addOrReplaceChild(RIGHT_EAR, CubeListBuilder.create().texOffs(0, 25).addBox(-1.5F, -2.0F, -0.5F, 3.0F, 2.0F, 0.001F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, -8.0F, 0.0F));
        rightEar.addOrReplaceChild(RIGHT_EAR2, CubeListBuilder.create().texOffs(6, 23).addBox(-1.5F, -0.001F, -2.0F, 3.0F, 0.001F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -0.5F));
        PartDefinition leftEar = head.addOrReplaceChild(LEFT_EAR, CubeListBuilder.create().texOffs(0, 23).addBox(-1.5F, -2.0F, -0.5F, 3.0F, 2.0F, 0.001F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -8.0F, 0.0F));
        leftEar.addOrReplaceChild(LEFT_EAR2, CubeListBuilder.create().texOffs(6, 25).addBox(-1.5F, -0.001F, -2.0F, 3.0F, 0.001F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -0.5F));
        head.addOrReplaceChild(NOSE, CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, -1.5F, -4.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.5F, -4.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public WolfHeadModel(ModelPart root) {
        super(root);
    }
}
