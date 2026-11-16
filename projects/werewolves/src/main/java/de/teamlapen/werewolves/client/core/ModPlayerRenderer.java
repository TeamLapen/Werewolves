package de.teamlapen.werewolves.client.core;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.client.render.WerewolfPlayerRenderer;
import de.teamlapen.werewolves.client.render.player.WerewolfPlayerBeastRenderer;
import de.teamlapen.werewolves.client.render.player.WerewolfPlayerSurvivalistRenderer;
import de.teamlapen.werewolves.client.render.state.WerewolfRenderState;
import de.teamlapen.werewolves.client.render.state.WerewolvesRenderStates;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.HumanoidArm;
import org.jetbrains.annotations.Nullable;

/**
 * Handles rendering of players in non-human like werewolf forms.
 */
public class ModPlayerRenderer {

    private final WerewolfPlayerBeastRenderer beastRenderer;
    private final WerewolfPlayerSurvivalistRenderer survivalistRenderer;

    public ModPlayerRenderer(EntityRendererProvider.Context context) {
        this.beastRenderer = new WerewolfPlayerBeastRenderer(context);
        this.survivalistRenderer = new WerewolfPlayerSurvivalistRenderer(context);
    }

    @Nullable
    private WerewolfPlayerRenderer getRenderer(WerewolfForm form) {
        if (form == WerewolfForm.SURVIVALIST) {
            return this.survivalistRenderer;
        } else if (form == WerewolfForm.BEAST) {
            return this.beastRenderer;
        }
        return null;
    }

    /**
     * Extract the render state for the given player and form
     */
    @Nullable
    public WerewolfRenderState extract(AbstractClientPlayer player, WerewolfForm form, float partialTicks) {
        WerewolfPlayerRenderer renderer = getRenderer(form);
        return renderer != null ? renderer.createRenderState(player, partialTicks) : null;
    }

    /**
     * Submits the werewolf model for the extracted state
     *
     * @return whether the werewolf was rendered and the player rendering should be skipped
     */
    public boolean submit(WerewolfRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector) {
        WerewolfPlayerRenderer renderer = getRenderer(state.form);
        if (renderer == null) return false;
        renderer.submit(state, poseStack, submitNodeCollector, Minecraft.getInstance().gameRenderer.getGameRenderState().levelRenderState.cameraRenderState);
        return true;
    }

    /**
     * @return whether the werewolf arm was rendered and the player arm rendering should be skipped
     */
    public boolean renderArm(AbstractClientPlayer player, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, HumanoidArm arm) {
        WerewolfForm form = WerewolvesRenderStates.getRenderForm(player);
        if (!form.isTransformed() || form.isHumanLike()) return false;
        WerewolfPlayerRenderer renderer = getRenderer(form);
        if (renderer == null) return false;
        renderer.renderHand(poseStack, submitNodeCollector, lightCoords, player, arm);
        return true;
    }
}
