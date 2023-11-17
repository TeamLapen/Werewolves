package de.teamlapen.werewolves.client.model.armor;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.teamlapen.vampirism.client.model.armor.VampirismArmorModel;
import de.teamlapen.werewolves.client.core.ModModelRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.jetbrains.annotations.NotNull;

public class WolfHeadModel extends VampirismArmorModel {

    private static final String HEAD = "head";
    private static final String RIGHT_EAR = "rightEar";
    private static final String RIGHT_EAR2 = "rightEar2";
    private static final String LEFT_EAR = "leftEar";
    private static final String LEFT_EAR2 = "leftEar2";
    private static final String NOSE = "nose";

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild(HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition rightEar = head.addOrReplaceChild(RIGHT_EAR, CubeListBuilder.create().texOffs(0, 25).addBox(-1.5F, -2.0F, -0.5F, 3.0F, 2.0F, 0.001F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, -8.0F, 0.0F));
        PartDefinition rightEar2 = rightEar.addOrReplaceChild(RIGHT_EAR2, CubeListBuilder.create().texOffs(6, 23).addBox(-1.5F, -0.001F, -2.0F, 3.0F, 0.001F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -0.5F));
        PartDefinition leftEar = head.addOrReplaceChild(LEFT_EAR, CubeListBuilder.create().texOffs(0, 23).addBox(-1.5F, -2.0F, -0.5F, 3.0F, 2.0F, 0.001F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -8.0F, 0.0F));
        PartDefinition leftEar2 = leftEar.addOrReplaceChild(LEFT_EAR2, CubeListBuilder.create().texOffs(6, 25).addBox(-1.5F, -0.001F, -2.0F, 3.0F, 0.001F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -0.5F));
        PartDefinition nose = head.addOrReplaceChild(NOSE, CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, -1.5F, -4.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.5F, -4.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    private static WolfHeadModel instance;
    public static WolfHeadModel getInstance(HumanoidModel<?> wearerModel) {
        if (instance == null) {
            instance = new WolfHeadModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelRender.WOLF_HEAD));
        }
        instance.copyFromHumanoid(wearerModel);
        return instance;
    }

    private final ModelPart head;

    public WolfHeadModel(ModelPart root) {
        this.head = root.getChild("head");
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer buffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        poseStack.pushPose();
        poseStack.scale(1.2F, 1.2F, 1.2F);
        this.head.render(poseStack, buffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
        poseStack.popPose();
    }

    @Override
    protected @NotNull Iterable<ModelPart> getHeadModels() {
        return ImmutableList.of(this.head);
    }
}
