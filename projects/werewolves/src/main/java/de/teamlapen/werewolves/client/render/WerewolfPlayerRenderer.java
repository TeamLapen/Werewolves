package de.teamlapen.werewolves.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.client.render.state.WerewolfRenderState;
import de.teamlapen.werewolves.client.render.state.WerewolvesRenderStates;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.HumanoidArm;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * Renders a player in a non human like werewolf form
 */
public abstract class WerewolfPlayerRenderer extends LivingEntityRenderer<AbstractClientPlayer, WerewolfRenderState, WerewolfBaseModel> {

    public static Optional<String> getWerewolfRenderer(AbstractClientPlayer player) {
        if (Helper.isWerewolf(player)) {
            WerewolfForm form = WerewolvesRenderStates.getRenderForm(player);
            if (form == WerewolfForm.BEAST) {
                return Optional.of(REFERENCE.MODID + ":beast");
            } else if (form == WerewolfForm.SURVIVALIST) {
                return Optional.of(REFERENCE.MODID + ":survivalist");
            }
        }
        return Optional.empty();
    }

    private final WerewolfForm form;
    private final List<Identifier> textures;

    public WerewolfPlayerRenderer(EntityRendererProvider.Context context, WerewolfBaseModel model, float shadowRadius, WerewolfForm form, List<Identifier> textures) {
        super(context, model, shadowRadius);
        this.form = form;
        this.textures = textures;
    }

    public WerewolfForm getForm() {
        return this.form;
    }

    @Override
    public @NotNull WerewolfRenderState createRenderState() {
        return new WerewolfRenderState();
    }

    @Override
    public void extractRenderState(@NotNull AbstractClientPlayer player, @NotNull WerewolfRenderState state, float partialTicks) {
        super.extractRenderState(player, state, partialTicks);
        WerewolfRenderState.extract(player, WerewolfPlayer.get(player), this.form, state, partialTicks, this.itemModelResolver);
        state.texture = getTexture(state.skinType);
    }

    protected Identifier getTexture(int skinType) {
        return this.textures.get(Math.max(0, skinType) % this.textures.size());
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull WerewolfRenderState state) {
        return state.texture;
    }

    public void renderHand(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, AbstractClientPlayer player, HumanoidArm arm) {
        ModelPart armPart = arm == HumanoidArm.RIGHT ? this.model.getRightArmModel() : this.model.getLeftArmModel();
        if (armPart == null) return;
        this.model.resetPose();
        armPart.xRot = 0.0F;
        Identifier texture = getTexture(WerewolfPlayer.get(player).getSkinType(this.form));
        submitNodeCollector.submitModelPart(armPart, poseStack, RenderTypes.entitySolid(texture), lightCoords, OverlayTexture.NO_OVERLAY, null);
    }

    @Override
    protected void setupRotations(@NotNull WerewolfRenderState state, @NotNull PoseStack poseStack, float bodyRot, float entityScale) {
        super.setupRotations(state, poseStack, bodyRot, entityScale);
        if (!state.isFallFlying && state.swimAmount > 0) {
            setupSwimRotations(state, poseStack);
        }
    }

    protected abstract void setupSwimRotations(WerewolfRenderState state, PoseStack poseStack);

    @Nullable
    public static WerewolfRenderState getState(net.minecraft.client.renderer.entity.state.AvatarRenderState state) {
        return state.getRenderData(WerewolvesRenderStates.WEREWOLF_PLAYER);
    }
}
