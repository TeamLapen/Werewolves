package de.teamlapen.werewolves.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.client.render.state.WerewolfRenderState;
import de.teamlapen.werewolves.util.AlphanumericComparator;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.HumanoidArm;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Base model for all werewolf forms.
 * <p>
 * Since 1.21.2 a model always renders its complete root part, therefore the werewolf meshes only contain the werewolf parts (no vanilla player parts).
 */
public abstract class WerewolfBaseModel extends EntityModel<WerewolfRenderState> implements ArmedModel<WerewolfRenderState>, HeadedModel {

    public static MeshDefinition createMesh() {
        return new MeshDefinition();
    }

    public WerewolfBaseModel(ModelPart part) {
        super(part);
    }

    /**
     * copied from {@link net.minecraft.client.model.HumanoidModel}
     */
    protected float rotlerpRad(float p_205060_1_, float p_205060_2_, float p_205060_3_) {
        float f = (p_205060_2_ - p_205060_1_) % ((float) Math.PI * 2F);
        if (f < -(float) Math.PI) {
            f += ((float) Math.PI * 2F);
        }

        if (f >= (float) Math.PI) {
            f -= ((float) Math.PI * 2F);
        }

        return p_205060_1_ + p_205060_3_ * f;
    }

    @Nullable
    public abstract ModelPart getModelRenderer();

    @Nullable
    public abstract ModelPart getHeadModel();

    @Nullable
    public abstract ModelPart getLeftArmModel();

    @Nullable
    public abstract ModelPart getRightArmModel();

    protected abstract @NotNull ModelPart getArm(@NotNull HumanoidArm pSide);

    @Override
    public @NotNull ModelPart getHead() {
        ModelPart head = getHeadModel();
        return head != null ? head : this.root;
    }

    @Override
    public void translateToHand(@NotNull WerewolfRenderState state, @NotNull HumanoidArm arm, @NotNull PoseStack stack) {
    }

    protected static List<Identifier> getTextures(String texturePath) {
        return Minecraft.getInstance().getResourceManager().listResources(texturePath, s -> s.getPath().endsWith(".png")).keySet().stream().sorted(new AlphanumericComparator()).filter(r -> REFERENCE.MODID.equals(r.getNamespace())).collect(Collectors.toList());
    }
}
