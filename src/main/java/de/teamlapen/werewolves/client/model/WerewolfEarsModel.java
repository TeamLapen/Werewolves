package de.teamlapen.werewolves.client.model;

import com.google.common.collect.ImmutableList;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * WerewolfEarsModel - RebelT
 * Created using Tabula 7.1.0
 */
public class WerewolfEarsModel<T extends LivingEntity> extends HumanoidModel<T> {

    public static final String HEAD = "head";
    public static final String CLAWS_LEFT = "clawsLeft";
    public static final String CLAWS_RIGHT = "clawsRight";
    public static final String EAR_RIGHT = "earRight";
    public static final String EAR_LEFT = "earLeft";

    public final ModelPart clawsLeft;
    public final ModelPart clawsRight;

    @SuppressWarnings("unused")
    public static MeshDefinition createMesh() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition root = mesh.getRoot();

        PartDefinition head = root.getChild(HEAD);
        PartDefinition earRight = head.addOrReplaceChild(EAR_RIGHT, CubeListBuilder.create().texOffs(16, 0).addBox(-4.9F, -8.0F, -2.5F, 1, 6, 3), PartPose.rotation(-0.4886921905584123F, -0.2617993877991494F, 0.0F));
        PartDefinition earLeft = head.addOrReplaceChild(EAR_LEFT, CubeListBuilder.create().texOffs(16, 0).addBox(3.9F, -8.0F, -2.5F, 1, 6, 3), PartPose.rotation(-0.4886921905584123F, 0.2617993877991494F, 0.0F));

        return mesh;
    }

    public static LayerDefinition createDefaultLayer() {
        MeshDefinition mesh = createMesh();
        PartDefinition root = mesh.getRoot();

        PartDefinition clawsRight = root.getChild("right_arm").addOrReplaceChild(CLAWS_RIGHT, CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, 10.0F, -2.0F, 4, 3, 4), PartPose.ZERO);
        PartDefinition clawsLeft = root.getChild("left_arm").addOrReplaceChild(CLAWS_LEFT, CubeListBuilder.create().texOffs(0, 7).addBox(-1F, 10.0F, -2.0F, 4, 3, 4), PartPose.ZERO);

        return LayerDefinition.create(mesh, 64, 32);
    }

    public static LayerDefinition createSlimLayer() {
        MeshDefinition mesh = createMesh();
        PartDefinition root = mesh.getRoot();

        PartDefinition clawsRight = root.getChild("right_arm").addOrReplaceChild(CLAWS_RIGHT, CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 10.0F, -2.0F, 3, 3, 4), PartPose.ZERO);
        PartDefinition clawsLeft = root.getChild("left_arm").addOrReplaceChild(CLAWS_LEFT, CubeListBuilder.create().texOffs(0, 7).addBox(-1F, 10.0F, -2.0F, 3, 3, 4), PartPose.ZERO);

        return LayerDefinition.create(mesh, 64, 32);
    }

    public WerewolfEarsModel(ModelPart part) {
        super(part);
        this.head.skipDraw = true;
        this.rightArm.skipDraw = true;
        this.leftArm.skipDraw = true;
        this.clawsRight = this.rightArm.getChild(CLAWS_RIGHT);
        this.clawsLeft = this.leftArm.getChild(CLAWS_LEFT);
    }

    @Nonnull
    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.rightArm, this.leftArm);
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
