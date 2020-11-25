package de.teamlapen.werewolves.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.teamlapen.vampirism.client.render.layers.TaskMasterTypeLayer;
import de.teamlapen.werewolves.entities.werewolf.WerewolfTaskMasterEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.VillagerModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WerewolfTaskMasterRenderer extends MobRenderer<WerewolfTaskMasterEntity, VillagerModel<WerewolfTaskMasterEntity>> {
    private final static ResourceLocation texture = new ResourceLocation("textures/entity/villager/villager.png");
    private final static ResourceLocation overlay = new ResourceLocation("vampirism", "textures/entity/vampire_task_master_overlay.png");

    public WerewolfTaskMasterRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new VillagerModel<>(0f), 0.5f);
        this.addLayer(new TaskMasterTypeLayer<>(this, overlay));
    }

    @Override
    public ResourceLocation getEntityTexture(WerewolfTaskMasterEntity entity) {
        return texture;
    }

    @Override
    protected void renderName(WerewolfTaskMasterEntity entityIn, ITextComponent displayNameIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        double dist = this.renderManager.squareDistanceTo(entityIn);
        if (dist <= 128) {
            super.renderName(entityIn, displayNameIn, matrixStackIn, bufferIn, packedLightIn);
        }
    }
}
