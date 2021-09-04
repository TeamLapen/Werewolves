package de.teamlapen.werewolves.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.client.model.WerewolfBeastModel;
import de.teamlapen.werewolves.client.model.WerewolfEarsModel;
import de.teamlapen.werewolves.client.model.WerewolfSurvivalistModel;
import de.teamlapen.werewolves.client.render.layer.WerewolfFaceOverlayLayer;
import de.teamlapen.werewolves.entities.IWerewolf;
import de.teamlapen.werewolves.player.WerewolfForm;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param <T> must extend {@link IWerewolf} be {@link net.minecraft.entity.player.PlayerEntity}
 */
public abstract class BaseWerewolfRenderer<T extends LivingEntity> extends LivingRenderer<T, WerewolfBaseModel<T>> {

    private final Map<WerewolfForm, WerewolfModelWrapper<T>> models = new HashMap<>();

    protected List<ResourceLocation> textures;
    protected WerewolfForm form = WerewolfForm.NONE;

    public BaseWerewolfRenderer(EntityRendererManager rendererManager, float size) {
        super(rendererManager, null, size);
        models.put(WerewolfForm.NONE, new WerewolfModelWrapper<>(()->null, null, 0, false));
        models.put(WerewolfForm.HUMAN, new WerewolfModelWrapper<>(WerewolfEarsModel::new, (renderer) -> Collections.emptyList(), WerewolfModelWrapper::getHumanTextures, 0.5f, false));
        models.put(WerewolfForm.BEAST, new WerewolfModelWrapper<>(WerewolfBeastModel::new, (renderer) -> Collections.singleton(new WerewolfFaceOverlayLayer<>(renderer)), WerewolfModelWrapper::getBeastTextures, 1.3f, true));
        models.put(WerewolfForm.SURVIVALIST, new WerewolfModelWrapper<>(WerewolfSurvivalistModel::new, (renderer) -> Collections.singleton(new WerewolfFaceOverlayLayer<>(renderer)), WerewolfModelWrapper::getSurvivalTextures, 0.5f, true));
        models.forEach((a,b)-> b.refresh(this));
    }

    public void switchModel(WerewolfForm type) {
        if (this.form == type) return;
        this.form = type;
        WerewolfModelWrapper<T> werewolfModelWrapper = getWrapper(type);
        this.model = werewolfModelWrapper.getModel();
        this.shadowRadius = werewolfModelWrapper.shadow;
        this.textures = werewolfModelWrapper.textures.get();
        this.layers.clear();
        this.layers.addAll(werewolfModelWrapper.getLayers());
    }

    protected WerewolfModelWrapper<T> getWrapper(WerewolfForm type) {
        return models.get(type);
    }

    @Override
    protected void setupRotations(@Nonnull T entityLiving, @Nonnull MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        if (entityLiving.getSwimAmount(partialTicks) > 0.0F && this.form.isHumanLike()) {
            float f3 = entityLiving.isInWater() ? -90.0F - entityLiving.xRot : -90.0F;
            float f4 = MathHelper.lerp(entityLiving.getSwimAmount(partialTicks), 0.0F, f3);
            matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(f4));
            if (entityLiving.isVisuallySwimming()) {
                matrixStackIn.translate(0.0D, -1.0D, 0.3F);
            }
        }
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull T entity) {
        return this.textures.get(Helper.asIWerewolf(entity).getSkinType() % this.form.getSkinTypes());
    }
}
