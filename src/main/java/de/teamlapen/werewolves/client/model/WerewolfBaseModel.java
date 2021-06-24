package de.teamlapen.werewolves.client.model;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public abstract class WerewolfBaseModel<T extends LivingEntity> extends BipedModel<T> {

    protected PlayerModel<T> playerModel;

    public WerewolfBaseModel() {
        super(0);
    }

    /**
     * copied from {@link net.minecraft.client.renderer.entity.model.BipedModel}
     */
    protected float func_205060_a(float p_205060_1_, float p_205060_2_, float p_205060_3_) {
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

    public void setPlayerModel(PlayerModel<T> model) {
        this.playerModel = model;
    }

}
