package de.teamlapen.werewolves.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * ModelDirewolf - Cheaterpaul
 * Created using Tabula 7.0.1
 */
@SideOnly(Side.CLIENT)
public class ModelDirewolf extends ModelBase {
    public ModelRenderer frontrightleg;
    public ModelRenderer upperbody;
    public ModelRenderer lowerbody;
    public ModelRenderer backrightleg;
    public ModelRenderer backleftleg;
    public ModelRenderer frontleftleg;
    public ModelRenderer tail;
    public ModelRenderer head;

    public ModelDirewolf() {//TODO better derewolf Model
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.tail = new ModelRenderer(this, 13, 21);
        this.tail.setRotationPoint(-1.5F, 12.0F, 16.0F);
        this.tail.addBox(0.0F, 0.0F, -1.0F, 3, 8, 3, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(-1.0F, 13.0F, -10.0F);
        this.head.addBox(-2.0F, -3.0F, -2.0F, 6, 6, 6, 0.0F);
        this.frontrightleg = new ModelRenderer(this, 0, 21);
        this.frontrightleg.setRotationPoint(1.0F, 16.0F, -5.0F);
        this.frontrightleg.addBox(0.0F, 0.0F, -1.0F, 3, 8, 3, 0.0F);
        this.backrightleg = new ModelRenderer(this, 0, 21);
        this.backrightleg.setRotationPoint(1.0F, 16.0F, 10.0F);
        this.backrightleg.addBox(0.0F, 0.0F, -1.0F, 3, 8, 3, 0.0F);
        this.backleftleg = new ModelRenderer(this, 0, 21);
        this.backleftleg.setRotationPoint(-4.0F, 16.0F, 10.0F);
        this.backleftleg.addBox(0.0F, 0.0F, -1.0F, 3, 8, 3, 0.0F);
        this.frontleftleg = new ModelRenderer(this, 0, 21);
        this.frontleftleg.setRotationPoint(-4.0F, 16.0F, -5.2F);
        this.frontleftleg.addBox(0.0F, 0.0F, -1.0F, 3, 8, 3, 0.0F);
        this.upperbody = new ModelRenderer(this, 25, 0);
        this.upperbody.setRotationPoint(-2.0F, 14.0F, -4.0F);
        this.upperbody.addBox(-3.0F, -3.0F, -3.0F, 10, 10, 9, 0.0F);
        this.setRotateAngle(upperbody, 1.5707963267948966F, 0.0F, 0.0F);
        this.lowerbody = new ModelRenderer(this, 26, 20);
        this.lowerbody.setRotationPoint(-1.0F, 14.0F, 5.0F);
        this.lowerbody.addBox(-3.0F, -2.0F, -3.0F, 8, 12, 8, 0.0F);
        this.setRotateAngle(lowerbody, 1.5707963267948966F, 0.0F, 0.0F);
        this.head.setTextureOffset(0, 13).addBox(-0.5F, 0.0F, -5.0F, 3, 3, 4, 0.0F);
        this.head.setTextureOffset(16, 17).addBox(-2.0F, -5.0F, 0.0F, 2, 2, 1, 0.0F);
        this.head.setTextureOffset(16, 17).addBox(2.0F, -5.0F, 0.0F, 2, 2, 1, 0.0F);
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);

        if (this.isChild) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 5.0F * scale, 2.0F * scale);
            this.head.renderWithRotation(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
            this.lowerbody.render(scale);
            this.frontrightleg.render(scale);
            this.frontleftleg.render(scale);
            this.backrightleg.render(scale);
            this.backleftleg.render(scale);
            this.tail.renderWithRotation(scale);
            this.upperbody.render(scale);
            GlStateManager.popMatrix();
        } else {
            this.head.renderWithRotation(scale);
            this.lowerbody.render(scale);
            this.frontrightleg.render(scale);
            this.frontleftleg.render(scale);
            this.backrightleg.render(scale);
            this.backleftleg.render(scale);
            this.tail.renderWithRotation(scale);
            this.upperbody.render(scale);
        }
    }

    @Override
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        this.tail.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.lowerbody.setRotationPoint(-1.0F, 14.0F, 5.0F);
        this.lowerbody.rotateAngleX = ((float) Math.PI / 2F);
        this.upperbody.setRotationPoint(-2.0F, 14.0F, -4.0F);
        this.upperbody.rotateAngleX = this.lowerbody.rotateAngleX;
        this.tail.setRotationPoint(-1.0F, 12.0F, 8.0F);
        this.frontrightleg.setRotationPoint(1.0F, 16.0F, -5.0F);
        this.frontleftleg.setRotationPoint(-4.0F, 16.0F, -5.2F);
        this.backrightleg.setRotationPoint(1.0F, 16.0F, 10.0F);
        this.backleftleg.setRotationPoint(-4.0F, 16.0F, 10.0F);
        this.frontrightleg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.frontleftleg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.backrightleg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.backleftleg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        //this.tail.rotateAngleX = ageInTicks;
    }
}