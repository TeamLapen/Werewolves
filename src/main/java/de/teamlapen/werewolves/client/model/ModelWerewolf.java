package de.teamlapen.werewolves.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * @author Cheaterpaul
 *
 */
@SideOnly(Side.CLIENT)
public class ModelWerewolf extends ModelBiped {

    // fields
    public ModelRenderer e1;
    public ModelRenderer e2;
    public ModelRenderer e3;
    public ModelRenderer e4;
    public ModelRenderer e5;
    public ModelRenderer e6;
    public ModelRenderer e7;
    public ModelRenderer e8;
    public ModelRenderer e9;

    public ModelWerewolf() {
        textureWidth = 64;
        textureHeight = 64;

        e1 = new ModelRenderer(this, 0, 16);
        e1.setRotationPoint(-4F, 45.5F, -5.5F);
        e1.addBox(0F, -8F, 0F, 8, 8, 8);
        e1.setTextureSize(64, 64);
        e1.mirror = true;
        setRotation(e1, 0.3926991F, 0F, 0F);
        e2 = new ModelRenderer(this, 48, 18);
        e2.setRotationPoint(0F, 30F, -0.5F);
        e2.addBox(0F, -10F, 0F, 4, 10, 4);
        e2.setTextureSize(64, 64);
        e2.mirror = true;
        setRotation(e2, 0F, 0F, 0F);
        e3 = new ModelRenderer(this, 48, 18);
        e3.setRotationPoint(-4F, 30F, -0.5F);
        e3.addBox(0F, -10F, 0F, 4, 10, 4);
        e3.setTextureSize(64, 64);
        e3.mirror = true;
        setRotation(e3, 0F, 0F, 0F);
        e4 = new ModelRenderer(this, 32, 16);
        e4.setRotationPoint(4F, 35F, -3F);
        e4.addBox(0F, -12F, 0F, 4, 12, 4);
        e4.setTextureSize(64, 64);
        e4.mirror = true;
        setRotation(e4, 0F, 0F, 0F);
        e5 = new ModelRenderer(this, 32, 16);
        e5.setRotationPoint(-8F, 35F, -3F);
        e5.addBox(0F, -12F, 0F, 4, 12, 4);
        e5.setTextureSize(64, 64);
        e5.mirror = true;
        setRotation(e5, 0F, 0F, 0F);
        e6 = new ModelRenderer(this, 0, 0);
        e6.setRotationPoint(-4F, 35.5F, 1F);
        e6.addBox(0F, -12F, 0F, 8, 12, 4);
        e6.setTextureSize(64, 64);
        e6.mirror = true;
        setRotation(e6, 0.3926991F, 0F, 0F);
        e7 = new ModelRenderer(this, 48, 18);
        e7.setRotationPoint(-4F, 24F, 1F);
        e7.addBox(0F, -10F, 0F, 4, 10, 4);
        e7.setTextureSize(64, 64);
        e7.mirror = true;
        setRotation(e7, 0F, 0F, 0F);
        e8 = new ModelRenderer(this, 48, 18);
        e8.setRotationPoint(0F, 24F, 1F);
        e8.addBox(0F, -10F, 0F, 4, 10, 4);
        e8.setTextureSize(64, 64);
        e8.mirror = true;
        setRotation(e8, 0F, 0F, 0F);
        e9 = new ModelRenderer(this, 32, 7);
        e9.setRotationPoint(-4F, 40F, 3.5F);
        e9.addBox(0F, -7F, 0F, 8, 7, 2);
        e9.setTextureSize(64, 64);
        e9.mirror = true;
        setRotation(e9, 0.3926991F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);

        e1.render(f5);
        e2.render(f5);
        e3.render(f5);
        e4.render(f5);
        e5.render(f5);
        e6.render(f5);
        e7.render(f5);
        e8.render(f5);
        e9.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

}