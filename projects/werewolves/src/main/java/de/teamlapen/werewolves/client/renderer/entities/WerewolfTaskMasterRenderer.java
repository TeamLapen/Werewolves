package de.teamlapen.werewolves.client.renderer.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.faction.client.core.FactionEntitiesRenderer;
import de.teamlapen.faction.client.renderer.entities.layers.TaskMasterTypeLayer;
import de.teamlapen.faction.client.renderer.entities.state.TaskMasterRenderState;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.common.world.entities.werewolf.WerewolfTaskMasterEntity;
import net.minecraft.client.model.npc.VillagerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;

public class WerewolfTaskMasterRenderer extends MobRenderer<WerewolfTaskMasterEntity, WerewolfTaskMasterRenderer.WerewolfTaskMasterRenderState, VillagerModel> {
    private final static Identifier texture = WIdentifier.mc("textures/entity/villager/villager.png");
    private final static Identifier overlay = WIdentifier.mod("textures/entity/werewolf_task_master_overlay.png");

    public WerewolfTaskMasterRenderer(EntityRendererProvider.Context context) {
        super(context, new VillagerModel(context.bakeLayer(FactionEntitiesRenderer.TASK_MASTER)), 0.5f);
        this.addLayer(new TaskMasterTypeLayer<>(this, overlay));
    }

    @Override
    public Identifier getTextureLocation(WerewolfTaskMasterRenderState state) {
        return texture;
    }

    @Override
    public WerewolfTaskMasterRenderState createRenderState() {
        return new WerewolfTaskMasterRenderState();
    }

    @Override
    public void extractRenderState(WerewolfTaskMasterEntity entity, WerewolfTaskMasterRenderState renderState, float partialTicks) {
        super.extractRenderState(entity, renderState, partialTicks);
        renderState.villagerData = entity.getVillageData();
    }

    @Override
    protected void submitNameDisplay(WerewolfTaskMasterRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        if (state.distanceToCameraSq <= 128) {
            super.submitNameDisplay(state, poseStack, submitNodeCollector, camera);
        }
    }

    public static class WerewolfTaskMasterRenderState extends TaskMasterRenderState {
    }
}
