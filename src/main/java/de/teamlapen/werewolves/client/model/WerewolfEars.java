package de.teamlapen.werewolves.client.model;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;

import javax.annotation.Nonnull;

/**
 * WerewolfEars - RebelT
 * Created using Tabula 7.1.0
 */
public class WerewolfEars<T extends LivingEntity> extends WerewolfBaseModel<T> {
    public RendererModel clawsLeft;
    public RendererModel clawsRight;
    public RendererModel earRight;
    public RendererModel earLeft;

    private boolean visible;
    private boolean sneak;

    public WerewolfEars() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.earRight = new RendererModel(this, 16, 0);
        this.earRight.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.earRight.addBox(-4.5F, -8.0F, -2.5F, 1, 6, 3, 0.0F);
        this.setRotateAngle(earRight, -0.4886921905584123F, -0.2617993877991494F, 0.0F);
        this.earLeft = new RendererModel(this, 16, 9);
        this.earLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.earLeft.addBox(3.5F, -8.0F, -2.5F, 1, 6, 3, 0.0F);
        this.setRotateAngle(earLeft, -0.4886921905584123F, 0.2617993877991494F, 0.0F);
        this.clawsRight = new RendererModel(this, 0, 0);
        this.clawsRight.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.clawsRight.addBox(-3.0F, 10.0F, -2.0F, 4, 3, 4, 0.0F);
        this.clawsLeft = new RendererModel(this, 0, 7);
        this.clawsLeft.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.clawsLeft.addBox(-1.0F, 10.0F, -2.0F, 4, 3, 4, 0.0F);
    }

    @Override
    public void render(@Nonnull T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngels();
        this.earRight.render(scale);
        this.earLeft.render(scale);
        this.clawsRight.render(scale);
        this.clawsLeft.render(scale);
    }

    private void setRotationAngels() {
        this.clawsLeft.showModel = visible;
        this.clawsRight.showModel = visible;
        this.earLeft.showModel = visible;
        this.earRight.showModel = visible;

        if (this.sneak) {
            this.clawsLeft.rotationPointY = 1.0F;
            this.clawsRight.rotationPointY = 1.0F;
            this.earLeft.rotationPointY = 1.0F;
            this.earRight.rotationPointY = 1.0F;
        } else {
            this.clawsLeft.rotationPointY = 0.0F;
            this.clawsRight.rotationPointY = 0.0F;
            this.earLeft.rotationPointY = 0.0F;
            this.earRight.rotationPointY = 0.0F;
        }
    }

    @SuppressWarnings("SameParameterValue")
    private void setRotateAngle(RendererModel rendererModel, float x, float y, float z) {
        rendererModel.rotateAngleX = x;
        rendererModel.rotateAngleY = y;
        rendererModel.rotateAngleZ = z;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public void setSneak(boolean sneak) {
        this.sneak = sneak;
    }
}
