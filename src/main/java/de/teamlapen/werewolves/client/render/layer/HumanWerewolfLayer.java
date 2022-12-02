package de.teamlapen.werewolves.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.client.model.WerewolfEarsModel;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
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
        Helper.asIWerewolf(entity).filter(werewolf -> werewolf.getForm() == WerewolfForm.HUMAN).ifPresent(werewolf -> {
            ResourceLocation texture = this.textures.get(werewolf.getSkinType() % this.textures.size());
            coloredCutoutModelCopyLayerRender(getParentModel(), model, texture, stack, bufferSource, light, entity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch, pPartialTick,1,1,1);
        });
    }

    /**
     * from {@link #coloredCutoutModelCopyLayerRender(net.minecraft.client.model.EntityModel, net.minecraft.client.model.EntityModel, net.minecraft.resources.ResourceLocation, com.mojang.blaze3d.vertex.PoseStack, net.minecraft.client.renderer.MultiBufferSource, int, net.minecraft.world.entity.LivingEntity, float, float, float, float, float, float, float, float, float)}
     * but with humanoid model to copy the correct properties
     */
    protected static <T extends LivingEntity> void coloredCutoutModelCopyLayerRender(HumanoidModel<T> pModelParent, HumanoidModel<T> pModel, ResourceLocation pTextureLocation, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, float pPartialTicks, float pRed, float pGreen, float pBlue) {
        if (!pEntity.isInvisible()) {
            pModelParent.copyPropertiesTo(pModel);
            pModel.prepareMobModel(pEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks);
            pModel.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
            renderColoredCutoutModel(pModel, pTextureLocation, pMatrixStack, pBuffer, pPackedLight, pEntity, pRed, pGreen, pBlue);
        }

    }

    @Override
    protected @NotNull ResourceLocation getTextureLocation(@NotNull T entity) {
        return this.textures.get(Helper.asIWerewolf(entity).map(s -> s.getSkinType() % s.getForm().getSkinTypes()).orElse(0));
    }
}
