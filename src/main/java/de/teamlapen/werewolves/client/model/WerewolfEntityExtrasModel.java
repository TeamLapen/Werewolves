package de.teamlapen.werewolves.client.model;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;

import javax.annotation.Nonnull;

/**
 * Model made by Rebel
 * Created using Tabula 7.1.0
 */
public class WerewolfEntityExtrasModel<T extends LivingEntity> extends EntityModel<T> {
    public RendererModel clawsLeft;
    public RendererModel clawsRight;
    public RendererModel earRight;
    public RendererModel earRight_1;

    public WerewolfEntityExtrasModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.earRight = new RendererModel(this, 16, 0);
        this.earRight.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.earRight.addBox(-4.5F, -8.0F, -2.5F, 1, 6, 3, 0.0F);
        this.setRotateAngle(earRight, -0.4886921905584123F, -0.2617993877991494F, 0.0F);
        this.earRight_1 = new RendererModel(this, 16, 9);
        this.earRight_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.earRight_1.addBox(3.5F, -8.0F, -2.5F, 1, 6, 3, 0.0F);
        this.setRotateAngle(earRight_1, -0.4886921905584123F, 0.2617993877991494F, 0.0F);
        this.clawsRight = new RendererModel(this, 0, 0);
        this.clawsRight.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.clawsRight.addBox(-3.0F, 10.0F, -2.0F, 4, 3, 4, 0.0F);
        this.clawsLeft = new RendererModel(this, 0, 7);
        this.clawsLeft.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.clawsLeft.addBox(-1.0F, 10.0F, -2.0F, 4, 3, 4, 0.0F);
    }

    @Override
    public void render(@Nonnull T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.earRight.render(scale);
        this.earRight_1.render(scale);
        this.clawsRight.render(scale);
        this.clawsLeft.render(scale);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(RendererModel RendererModel, float x, float y, float z) {
        RendererModel.rotateAngleX = x;
        RendererModel.rotateAngleY = y;
        RendererModel.rotateAngleZ = z;
    }
}
