package de.teamlapen.werewolves.client.renderer.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.teamlapen.werewolves.api.entities.werewolf.IWerewolf;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.client.core.ModModelRender;
import de.teamlapen.werewolves.client.model.Werewolf4LModel;
import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.client.model.WerewolfBeastModel;
import de.teamlapen.werewolves.client.model.WerewolfSurvivalistModel;
import de.teamlapen.werewolves.client.model.WerewolfModelWrapper;
import de.teamlapen.werewolves.client.renderer.entities.layer.WerewolfFaceOverlayLayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Renders the werewolf model matching the current form of the entity
 *
 * @param <T> must implement {@link IWerewolf}
 */
public abstract class BaseWerewolfRenderer<T extends LivingEntity & IWerewolf> extends LivingEntityRenderer<T, WerewolfRenderState, WerewolfBaseModel> {

    private final Map<WerewolfForm, WerewolfModelWrapper> models = new HashMap<>();
    private WerewolfForm form = WerewolfForm.NONE;

    @SuppressWarnings("DataFlowIssue")
    public BaseWerewolfRenderer(EntityRendererProvider.Context context, float size) {
        super(context, null, 0f);

        this.models.put(WerewolfForm.NONE, new WerewolfModelWrapper.Builder()
                .shadow(0)
                .build());
        this.models.put(WerewolfForm.BEAST, new WerewolfModelWrapper.Builder()
                .model(new WerewolfBeastModel(context.bakeLayer(ModModelRender.WEREWOLF_BEAST)))
                .layers(Collections.singleton(new WerewolfFaceOverlayLayer(this)))
                .textures(WerewolfBeastModel.getBeastTextures())
                .shadow(2.6f * size)
                .build());
        this.models.put(WerewolfForm.SURVIVALIST, new WerewolfModelWrapper.Builder()
                .model(new WerewolfSurvivalistModel(context.bakeLayer(ModModelRender.WEREWOLF_SURVIVALIST)))
                .layers(Collections.singleton(new WerewolfFaceOverlayLayer(this)))
                .textures(WerewolfSurvivalistModel.getSurvivalTextures())
                .shadow(size)
                .build());
        this.models.put(WerewolfForm.BEAST4L, new WerewolfModelWrapper.Builder()
                .model(new Werewolf4LModel(context.bakeLayer(ModModelRender.WEREWOLF_4L)))
                .layers(Collections.singleton(new WerewolfFaceOverlayLayer(this)))
                .textures(Werewolf4LModel.get4LTextures())
                .shadow(2.6f * size)
                .build());
    }

    protected WerewolfModelWrapper getWrapper(WerewolfForm type) {
        return this.models.getOrDefault(type, this.models.get(WerewolfForm.NONE));
    }

    /**
     * Switches model and layers to the given form
     */
    protected void switchModel(WerewolfForm type) {
        if (this.form == type) return;
        this.form = type;
        WerewolfModelWrapper wrapper = getWrapper(type);
        this.model = wrapper.model();
        this.layers.clear();
        this.layers.addAll(wrapper.layers());
    }

    @Override
    public WerewolfRenderState createRenderState() {
        return new WerewolfRenderState();
    }

    @Override
    public void extractRenderState(T entity, WerewolfRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        WerewolfForm form = entity.getForm();
        WerewolfRenderState.extract(entity, entity, form, state, partialTicks, this.itemModelResolver);
        var textures = getWrapper(form).textures();
        state.texture = textures.isEmpty() ? MissingTextureAtlasSprite.getLocation() : textures.get(state.skinType % textures.size());
    }

    @Override
    protected float getShadowRadius(WerewolfRenderState state) {
        return getWrapper(state.form).shadow();
    }

    @Override
    public void submit(WerewolfRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        this.switchModel(state.form);
        //noinspection ConstantValue
        if (this.model == null) return;
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    @Override
    protected void setupRotations(WerewolfRenderState state, PoseStack poseStack, float bodyRot, float entityScale) {
        super.setupRotations(state, poseStack, bodyRot, entityScale);
        if (state.swimAmount > 0.0F && state.form.isHumanLike()) {
            float f3 = state.isInWater ? -90.0F - state.xRot : -90.0F;
            float f4 = Mth.lerp(state.swimAmount, 0.0F, f3);
            poseStack.mulPose(Axis.XP.rotationDegrees(f4));
            if (state.isVisuallySwimming) {
                poseStack.translate(0.0D, -1.0D, 0.3F);
            }
        }
    }

    @Override
    public Identifier getTextureLocation(WerewolfRenderState state) {
        return state.texture;
    }
}
