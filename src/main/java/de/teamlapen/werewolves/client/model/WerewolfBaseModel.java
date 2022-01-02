package de.teamlapen.werewolves.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.HandSide;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public abstract class WerewolfBaseModel<T extends LivingEntity> extends PlayerModel<T> {

    protected PlayerModel<T> playerModel;

    public WerewolfBaseModel() {
        super(0, false);
    }

    /**
     * copied from {@link net.minecraft.client.renderer.entity.model.BipedModel}
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
    public abstract ModelRenderer getModelRenderer();

    @Nullable
    public abstract ModelRenderer getHeadModel();

    @Nullable
    public abstract ModelRenderer getLeftArmModel();

    @Nullable
    public abstract ModelRenderer getRightArmModel();


    public void setPlayerModel(PlayerModel<T> model) {
        this.playerModel = model;
    }

    @Nonnull
    @Override
    protected abstract Iterable<ModelRenderer> bodyParts();

    @Deprecated
    @Override
    public void renderEars(@Nonnull MatrixStack stack, @Nonnull IVertexBuilder builder, int p_228287_3_, int p_228287_4_) {
    }

    @Deprecated
    @Override
    public void renderCloak(@Nonnull MatrixStack stack, @Nonnull IVertexBuilder builder, int p_228289_3_, int p_228289_4_) {
    }

    @Override
    public void setupAnim(@Nonnull T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

    }

    @Override
    public void setAllVisible(boolean p_178719_1_) {
    }

    @Override
    public void translateToHand(@Nonnull HandSide p_225599_1_, @Nonnull MatrixStack p_225599_2_) {
    }
}
