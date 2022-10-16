package de.teamlapen.werewolves.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public abstract class WerewolfBaseModel<T extends LivingEntity> extends PlayerModel<T> {

    protected PlayerModel<T> playerModel;

    public static MeshDefinition createMesh(CubeDeformation deformation) {
        return PlayerModel.createMesh(deformation, false);
    }

    public WerewolfBaseModel(ModelPart part) {
        super(part, false);
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


    public void setPlayerModel(PlayerModel<T> model) {
        this.playerModel = model;
    }

    @Nonnull
    @Override
    protected abstract Iterable<ModelPart> bodyParts();

    @Deprecated
    @Override
    public void renderEars(@Nonnull PoseStack stack, @Nonnull VertexConsumer builder, int p_228287_3_, int p_228287_4_) {
    }

    @Deprecated
    @Override
    public void renderCloak(@Nonnull PoseStack stack, @Nonnull VertexConsumer builder, int p_228289_3_, int p_228289_4_) {
    }

    @Override
    public void setupAnim(@Nonnull T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

    }

    @Override
    public void setAllVisible(boolean p_178719_1_) {
    }

    @Override
    public void translateToHand(@Nonnull HumanoidArm arm, @Nonnull PoseStack stack) {
    }
}
