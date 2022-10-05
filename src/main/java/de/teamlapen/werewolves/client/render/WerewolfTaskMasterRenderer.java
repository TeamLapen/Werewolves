package de.teamlapen.werewolves.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.vampirism.client.renderer.entity.layers.TaskMasterTypeLayer;
import de.teamlapen.werewolves.entities.werewolf.WerewolfTaskMasterEntity;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class WerewolfTaskMasterRenderer extends MobRenderer<WerewolfTaskMasterEntity, VillagerModel<WerewolfTaskMasterEntity>> {
    private final static ResourceLocation texture = new ResourceLocation("textures/entity/villager/villager.png");
    private final static ResourceLocation overlay = new ResourceLocation("vampirism", "textures/entity/vampire_task_master_overlay.png");

    public WerewolfTaskMasterRenderer(EntityRendererProvider.Context context) {
        super(context, new VillagerModel<>(context.bakeLayer(de.teamlapen.vampirism.client.core.ModEntitiesRender.TASK_MASTER)), 0.5f);
        this.addLayer(new TaskMasterTypeLayer<>(this, overlay));
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull WerewolfTaskMasterEntity entity) {
        return texture;
    }

    @Override
    protected void renderNameTag(@Nonnull WerewolfTaskMasterEntity entityIn, @Nonnull Component displayNameIn, @Nonnull PoseStack matrixStackIn, @Nonnull MultiBufferSource bufferIn, int packedLightIn) {
        double dist = this.entityRenderDispatcher.distanceToSqr(entityIn);
        if (dist <= 128) {
            super.renderNameTag(entityIn, displayNameIn, matrixStackIn, bufferIn, packedLightIn);
        }
    }
}
