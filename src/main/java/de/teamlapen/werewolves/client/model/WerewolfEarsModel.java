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
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * WerewolfEarsModel - RebelT
 * Created using Tabula 7.1.0
 */
public class WerewolfEarsModel<T extends LivingEntity> extends WerewolfBaseModel<T> {

    public static final String DUMMY_HEAD = "dummyHead";
    public static final String CLAWS_LEFT = "clawsLeft";
    public static final String CLAWS_RIGHT = "clawsRight";
    public static final String EAR_RIGHT = "earRight";
    public static final String EAR_LEFT = "earLeft";

    public final ModelPart dummyHead;
    public final ModelPart clawsLeft;
    public final ModelPart clawsRight;
    public final ModelPart earRight;
    public final ModelPart earLeft;

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = WerewolfBaseModel.createMesh(CubeDeformation.NONE);
        PartDefinition root = mesh.getRoot();

        PartDefinition dummyHead = root.addOrReplaceChild(DUMMY_HEAD, CubeListBuilder.create().addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
        PartDefinition earRight = dummyHead.addOrReplaceChild(EAR_RIGHT, CubeListBuilder.create().texOffs(16, 0).addBox(-4.5F, -8.0F, -2.5F, 1, 6, 3), PartPose.rotation(-0.4886921905584123F, -0.2617993877991494F, 0.0F));
        PartDefinition earLeft = dummyHead.addOrReplaceChild(EAR_LEFT, CubeListBuilder.create().texOffs(16, 0).addBox(3.5F, -8.0F, -2.5F, 1, 6, 3), PartPose.rotation(-0.4886921905584123F, 0.2617993877991494F, 0.0F));

        PartDefinition clawsRight = root.getChild("right_arm").addOrReplaceChild(CLAWS_RIGHT, CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, 10.0F, -2.0F, 4, 3, 4), PartPose.ZERO);
        PartDefinition clawsLeft = root.getChild("left_arm").addOrReplaceChild(CLAWS_LEFT, CubeListBuilder.create().texOffs(0, 7).addBox(-1F, 10.0F, -2.0F, 4, 3, 4), PartPose.ZERO);
        return LayerDefinition.create(mesh, 64, 32);
    }

    public WerewolfEarsModel(ModelPart part) {
        super(part);
        this.dummyHead = part.getChild(DUMMY_HEAD);
        this.dummyHead.skipDraw = true;
        this.earRight = this.dummyHead.getChild(EAR_RIGHT);
        this.earLeft = this.dummyHead.getChild(EAR_LEFT);
        this.clawsRight = this.rightArm.getChild(CLAWS_RIGHT);
        this.clawsLeft = this.leftArm.getChild(CLAWS_LEFT);
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
        return ImmutableList.of(this.dummyHead);
    }

    @Nonnull
    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.rightArm, this.leftArm);
    }

    @Override
    public void setupAnim(@Nonnull T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
        this.dummyHead.copyFrom(this.playerModel.head);
        this.rightArm.copyFrom(this.playerModel.rightArm);
        this.leftArm.copyFrom(this.playerModel.leftArm);
    }

    @Override
    public void renderToBuffer(PoseStack p_102034_, VertexConsumer p_102035_, int p_102036_, int p_102037_, float p_102038_, float p_102039_, float p_102040_, float p_102041_) {
        super.renderToBuffer(p_102034_, p_102035_, p_102036_, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_);
    }

    @Nonnull
    public static List<ResourceLocation> getHumanTextures() {
        List<ResourceLocation> locs = Minecraft.getInstance().getResourceManager().listResources("textures/entity/werewolf/human", s -> s.getPath().endsWith(".png")).keySet().stream().filter(r -> REFERENCE.MODID.equals(r.getNamespace())).collect(Collectors.toList());
        if (locs.size() < WerewolfForm.HUMAN.getSkinTypes()) {
            for (int i = 0; i < WerewolfForm.HUMAN.getSkinTypes(); i++) {
                ResourceLocation s = new ResourceLocation(REFERENCE.MODID, "textures/entity/werewolf/human/werewolf_ear_claws_" + i + ".png");
                if (!locs.contains(s)) {
                    locs.add(s);
                }
            }
        }
        return locs;
    }
}
