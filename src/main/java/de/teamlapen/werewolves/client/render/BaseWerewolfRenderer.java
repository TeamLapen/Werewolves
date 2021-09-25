package de.teamlapen.werewolves.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.client.model.WerewolfBeastModel;
import de.teamlapen.werewolves.client.model.WerewolfEarsModel;
import de.teamlapen.werewolves.client.model.WerewolfSurvivalistModel;
import de.teamlapen.werewolves.client.render.layer.WerewolfFaceOverlayLayer;
import de.teamlapen.werewolves.entities.werewolf.IWerewolf;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WerewolfForm;
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
 * @param <T> must extend {@link IWerewolf} or be {@link net.minecraft.entity.player.PlayerEntity}
 */
public abstract class BaseWerewolfRenderer<T extends LivingEntity> extends LivingRenderer<T, WerewolfBaseModel<T>> {

    private final Map<WerewolfForm, WerewolfModelWrapper<T>> models = new HashMap<>();

    protected final ResourceLocation[] eyeOverlays;
    protected List<ResourceLocation> textures;
    protected WerewolfForm form = WerewolfForm.NONE;

    public BaseWerewolfRenderer(EntityRendererManager rendererManager, float size) {
        //noinspection ConstantConditions
        super(rendererManager, null, 0f);
        this.eyeOverlays = new ResourceLocation[REFERENCE.EYE_TYPE_COUNT];
        for (int i = 0; i < this.eyeOverlays.length; i++) {
            this.eyeOverlays[i] = new ResourceLocation(REFERENCE.MODID + ":textures/entity/werewolf/eye/eye_" + (i) + ".png");
        }

        this.models.put(WerewolfForm.NONE, new WerewolfModelWrapper<>(()->null, null, 0, false));
        this.models.put(WerewolfForm.HUMAN, new WerewolfModelWrapper<>(WerewolfEarsModel::new, (renderer) -> Collections.emptyList(), WerewolfModelWrapper::getHumanTextures, size, false));
        this.models.put(WerewolfForm.BEAST, new WerewolfModelWrapper<>(WerewolfBeastModel::new, (renderer) -> Collections.singleton(new WerewolfFaceOverlayLayer<>(renderer, eyeOverlays)), WerewolfModelWrapper::getBeastTextures, 2.6f * size, true));
        this.models.put(WerewolfForm.SURVIVALIST, new WerewolfModelWrapper<>(WerewolfSurvivalistModel::new, (renderer) -> Collections.singleton(new WerewolfFaceOverlayLayer<>(renderer,eyeOverlays)), WerewolfModelWrapper::getSurvivalTextures, size, true));
        this.models.forEach((a,b)-> b.refresh(this));
    }

    public void switchModel(WerewolfForm type) {
        if (this.form == type) return;
        this.form = type;
        WerewolfModelWrapper<T> werewolfModelWrapper = getWrapper(type);
        this.model = werewolfModelWrapper.getModel();
        this.shadowRadius = werewolfModelWrapper.shadow;
        this.textures = werewolfModelWrapper.textures;
        this.layers.clear();
        this.layers.addAll(werewolfModelWrapper.getLayers());
    }

    protected WerewolfModelWrapper<T> getWrapper(WerewolfForm type) {
        return this.models.get(type);
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
