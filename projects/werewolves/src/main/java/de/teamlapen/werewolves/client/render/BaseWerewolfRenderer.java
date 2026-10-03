package de.teamlapen.werewolves.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.teamlapen.werewolves.api.entities.werewolf.IWerewolf;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.client.core.ModModelRender;
import de.teamlapen.werewolves.client.model.Werewolf4LModel;
import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.client.model.WerewolfBeastModel;
import de.teamlapen.werewolves.client.model.WerewolfSurvivalistModel;
import de.teamlapen.werewolves.client.render.layer.WerewolfFormFaceOverlayLayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param <T> must extend {@link IWerewolf} or be {@link net.minecraft.world.entity.player.Player}
 */
public abstract class BaseWerewolfRenderer<T extends LivingEntity> extends LivingEntityRenderer<T, WerewolfBaseModel<T>> {
    private static final Logger LOGGER = LogManager.getLogger();

    private final Map<WerewolfForm, WerewolfModelWrapper<T>> models = new HashMap<>();

    protected List<ResourceLocation> textures;
    protected WerewolfForm form = WerewolfForm.NONE;

    public BaseWerewolfRenderer(EntityRendererProvider.Context context, float size) {
        //noinspection ConstantConditions
        super(context, null, 0f);

        this.models.put(WerewolfForm.NONE, new WerewolfModelWrapper.Builder<T>()
                .shadow(0)
                .build());
        this.models.put(WerewolfForm.BEAST, new WerewolfModelWrapper.Builder<T>()
                .model(new WerewolfBeastModel<>(context.bakeLayer(ModModelRender.WEREWOLF_BEAST)))
                .layers(Collections.singleton(new WerewolfFormFaceOverlayLayer<>(WerewolfForm.BEAST, this)))
                .textures(WerewolfBeastModel.getBeastTextures())
                .shadow(2.6f * size)
                .skipPlayerModel()
                .build());
        this.models.put(WerewolfForm.SURVIVALIST, new WerewolfModelWrapper.Builder<T>()
                .model(new WerewolfSurvivalistModel<>(context.bakeLayer(ModModelRender.WEREWOLF_SURVIVALIST)))
                .layers(Collections.singleton(new WerewolfFormFaceOverlayLayer<>(WerewolfForm.SURVIVALIST, this)))
                .textures(WerewolfSurvivalistModel.getSurvivalTextures())
                .shadow(size)
                .skipPlayerModel()
                .build());
        this.models.put(WerewolfForm.BEAST4L, new WerewolfModelWrapper.Builder<T>()
                .model(new Werewolf4LModel<>(context.bakeLayer(ModModelRender.WEREWOLF_4L)))
                .layers(Collections.singleton(new WerewolfFormFaceOverlayLayer<>(WerewolfForm.BEAST4L, this)))
                .textures(Werewolf4LModel.get4LTextures())
                .shadow(2.6f * size)
                .skipPlayerModel()
                .build());
    }

    public void switchModel(WerewolfForm type) {
        if (this.form == type) return;
        this.form = type;
        WerewolfModelWrapper<T> werewolfModelWrapper = getWrapper(type);
        this.model = werewolfModelWrapper.model();
        this.shadowRadius = werewolfModelWrapper.shadow();
        this.textures = werewolfModelWrapper.textures();
        this.layers.clear();
        this.layers.addAll(werewolfModelWrapper.layers());
    }

    protected WerewolfModelWrapper<T> getWrapper(WerewolfForm type) {
        return this.models.get(type);
    }

    @Override
    protected void setupRotations(T pEntity, PoseStack pPoseStack, float pBob, float pYBodyRot, float pPartialTick, float pScale) {
        super.setupRotations(pEntity, pPoseStack, pBob, pYBodyRot, pPartialTick, pScale);
        if (pEntity.getSwimAmount(pPartialTick) > 0.0F && this.form.isHumanLike()) {
            float f3 = pEntity.isInWater() ? -90.0F - pEntity.getXRot() : -90.0F;
            float f4 = Mth.lerp(pEntity.getSwimAmount(pPartialTick), 0.0F, f3);
            pPoseStack.mulPose(Axis.XP.rotationDegrees(f4));
            if (pEntity.isVisuallySwimming()) {
                pPoseStack.translate(0.0D, -1.0D, 0.3F);
            }
        }
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull T entity) {
        return this.textures.get(Helper.asIWerewolf(entity).map(s -> s.getSkinType(this.form) % this.form.getSkinTypes()).orElse(0));
    }
}
