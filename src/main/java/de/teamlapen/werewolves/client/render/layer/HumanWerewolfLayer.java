package de.teamlapen.werewolves.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.client.gui.WerewolfPlayerAppearanceScreen;
import de.teamlapen.werewolves.client.model.WerewolfEarsModel;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HumanWerewolfLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {

    private final A model;
    private final List<ResourceLocation> textures;

    public HumanWerewolfLayer(RenderLayerParent<T, M> renderer, A model) {
        super(renderer);
        this.model = model;
        this.textures = WerewolfEarsModel.getHumanTextures();
    }

    @Override
    public void render(@NotNull PoseStack stack, @NotNull MultiBufferSource bufferSource, int light, @NotNull T entity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

        var renderForm = Minecraft.getInstance().screen instanceof WerewolfPlayerAppearanceScreen screen && screen.isRenderForm() ? screen.getActiveForm() : null;
        Helper.asIWerewolf(entity).filter(werewolf -> (renderForm != null ? renderForm : werewolf.getForm()) == WerewolfForm.HUMAN).ifPresent(werewolf -> {
            ResourceLocation texture = this.textures.get(werewolf.getSkinType(WerewolfForm.HUMAN) % this.textures.size());
            coloredCutoutModelCopyLayerRender(getParentModel(), model, texture, stack, bufferSource, light, entity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch, pPartialTick, FastColor.ARGB32.colorFromFloat(1,1,1, 1));
        });
    }

    protected static <T extends LivingEntity> void coloredCutoutModelCopyLayerRender(
            HumanoidModel<T> pModelParent,
            EntityModel<T> pModel,
            ResourceLocation pTextureLocation,
            PoseStack pPoseStack,
            MultiBufferSource pBuffer,
            int pPackedLight,
            T pEntity,
            float pLimbSwing,
            float pLimbSwingAmount,
            float pAgeInTicks,
            float pNetHeadYaw,
            float pHeadPitch,
            float pPartialTick,
            int pColor
    ) {
        if (!pEntity.isInvisible()) {
            pModelParent.copyPropertiesTo(pModel);
            pModel.prepareMobModel(pEntity, pLimbSwing, pLimbSwingAmount, pPartialTick);
            pModel.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
            renderColoredCutoutModel(pModel, pTextureLocation, pPoseStack, pBuffer, pPackedLight, pEntity, pColor);
        }
    }

    @Override
    protected @NotNull ResourceLocation getTextureLocation(@NotNull T entity) {
        return this.textures.get(Helper.asIWerewolf(entity).map(s -> s.getSkinType() % s.getForm().getSkinTypes()).orElse(0));
    }
}
