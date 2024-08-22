package de.teamlapen.werewolves.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.vampirism.client.renderer.entity.layers.TaskMasterTypeLayer;
import de.teamlapen.werewolves.api.WResourceLocation;
import de.teamlapen.werewolves.entities.werewolf.WerewolfTaskMasterEntity;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class WerewolfTaskMasterRenderer extends MobRenderer<WerewolfTaskMasterEntity, VillagerModel<WerewolfTaskMasterEntity>> {
    private final static ResourceLocation texture = WResourceLocation.mc("textures/entity/villager/villager.png");
    private final static ResourceLocation overlay = WResourceLocation.v("textures/entity/vampire_task_master_overlay.png");

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
    protected void renderNameTag(WerewolfTaskMasterEntity pEntity, Component pDisplayName, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, float pPartialTick) {
        double dist = this.entityRenderDispatcher.distanceToSqr(pEntity);
        if (dist <= 128) {
            super.renderNameTag(pEntity, pDisplayName, pPoseStack, pBufferSource, pPackedLight, pPartialTick);
        }
    }
}
