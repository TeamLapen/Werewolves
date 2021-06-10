package de.teamlapen.werewolves.client.render;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.client.model.WerewolfBeastModel;
import de.teamlapen.werewolves.client.model.WerewolfEarsModel;
import de.teamlapen.werewolves.client.model.WerewolfSurvivalistModel;
import de.teamlapen.werewolves.client.render.layer.WerewolfFaceOverlayLayer;
import de.teamlapen.werewolves.player.WerewolfForm;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class WerewolfPlayerRenderer extends LivingRenderer<AbstractClientPlayerEntity, WerewolfBaseModel<AbstractClientPlayerEntity>> {

    private static class WerewolfModelWrapper {
        private final WerewolfBaseModel<AbstractClientPlayerEntity> model;
        private final Collection<LayerRenderer<AbstractClientPlayerEntity, WerewolfBaseModel<AbstractClientPlayerEntity>>> layers = new ArrayList<>();
        private final Function<WerewolfPlayerRenderer, Collection<LayerRenderer<AbstractClientPlayerEntity, WerewolfBaseModel<AbstractClientPlayerEntity>>>> layersFactory;
        private final Supplier<List<ResourceLocation>> textures;
        private final float shadow;
        private final boolean skipPlayerModel;

        public WerewolfModelWrapper(WerewolfBaseModel<AbstractClientPlayerEntity> model, Function<WerewolfPlayerRenderer, Collection<LayerRenderer<AbstractClientPlayerEntity, WerewolfBaseModel<AbstractClientPlayerEntity>>>> layersFactory, Supplier<List<ResourceLocation>> textures, float shadow, boolean skipPlayerModel) {
            this.model = model;
            this.textures = textures;
            this.shadow = shadow;
            this.skipPlayerModel = skipPlayerModel;
            this.layersFactory = layersFactory;
        }

        public WerewolfModelWrapper(WerewolfBaseModel<AbstractClientPlayerEntity> model, Function<WerewolfPlayerRenderer, Collection<LayerRenderer<AbstractClientPlayerEntity, WerewolfBaseModel<AbstractClientPlayerEntity>>>> layersFactory, ResourceLocation texture, float shadow, boolean skipPlayerModel) {
            this(model, layersFactory, () -> Collections.singletonList(texture), shadow, skipPlayerModel);
        }

        public WerewolfModelWrapper(WerewolfBaseModel<AbstractClientPlayerEntity> model, ResourceLocation textures, float shadow, boolean skipPlayerModel) {
            this(model, (a)-> Collections.emptyList(), () -> Collections.singletonList(textures), shadow, skipPlayerModel);
        }
    }

    private static final Map<WerewolfForm, WerewolfModelWrapper> MODELS = new HashMap<>();

    static {
        addModel(WerewolfForm.NONE, new WerewolfModelWrapper(null, null, 0, false));
        addModel(WerewolfForm.HUMAN, new WerewolfModelWrapper(new WerewolfEarsModel<>(), (renderer) -> Collections.emptyList(),WerewolfPlayerRenderer::getHumanTextures, 0.5f, false));
        addModel(WerewolfForm.BEAST, new WerewolfModelWrapper(new WerewolfBeastModel<>(), (renderer) -> Collections.singleton(new WerewolfFaceOverlayLayer(renderer)), WerewolfPlayerRenderer::getBeastTextures , 1.3f, true));
        addModel(WerewolfForm.SURVIVALIST, new WerewolfModelWrapper(new WerewolfSurvivalistModel<>(), (renderer) -> Collections.singleton(new WerewolfFaceOverlayLayer(renderer)), WerewolfPlayerRenderer::getSurvivalTextures, 0.5f, true));
    }

    public static void addModel(WerewolfForm form, WerewolfModelWrapper render) {
        MODELS.put(form, render);
    }

    private List<ResourceLocation> textures;
    private boolean skipPlayerModel;
    private WerewolfForm form;

    public WerewolfPlayerRenderer(EntityRendererManager rendererManager) {
        //noinspection ConstantConditions
        super(rendererManager, null, 0f);
        MODELS.values().forEach(m -> {
            m.layers.clear();
            m.layers.addAll(m.layersFactory.apply(this));
        });
    }

    public void switchModel(WerewolfForm type) {
        this.form = type;
        WerewolfModelWrapper werewolfModelWrapper = MODELS.get(type);
        this.entityModel = werewolfModelWrapper.model;
        this.shadowSize = werewolfModelWrapper.shadow;
        this.textures = werewolfModelWrapper.textures.get();
        this.skipPlayerModel = werewolfModelWrapper.skipPlayerModel;
        this.layerRenderers.clear();
        this.layerRenderers.addAll(werewolfModelWrapper.layers);
    }

    /**
     * @returns if the player model should be renderer
     */
    public boolean render(WerewolfPlayer entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        this.switchModel(entity.getForm());
        if (this.entityModel != null) {
            render(((AbstractClientPlayerEntity) entity.getRepresentingPlayer()), entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
            return skipPlayerModel;
        }
        return false;
    }

    /**
     * use {@link #render(WerewolfPlayer, float, float, MatrixStack, IRenderTypeBuffer, int)}
     */
    @Deprecated
    @Override
    public void render(AbstractClientPlayerEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        if (!entity.isUser() || this.renderManager.info.getRenderViewEntity() == entity) {
            this.setModelVisible(entity);
            super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        }
    }

    private void setModelVisible(AbstractClientPlayerEntity clientPlayer) {
        WerewolfBaseModel<AbstractClientPlayerEntity> playerModel = this.getEntityModel();
        if (clientPlayer.isSpectator()) {
            playerModel.setVisible(false);
            playerModel.bipedHead.showModel = true;
        } else {
            playerModel.setVisible(true);
            playerModel.isSneak = clientPlayer.isCrouching();
        }
    }

    @Nonnull
    @Override
    public ResourceLocation getEntityTexture(@Nonnull AbstractClientPlayerEntity entity) {
        return this.textures.get(WerewolfPlayer.get(entity).getSkinType(this.form) % this.form.getSkinTypes());
    }

    private static List<ResourceLocation> getBeastTextures() {
        return Lists.newArrayList(new ResourceLocation(REFERENCE.MODID, "textures/entity/werewolf/player/beast/beast_1.png"),new ResourceLocation(REFERENCE.MODID, "textures/entity/werewolf/player/beast/beast_2.png"));
    }

    private static List<ResourceLocation> getSurvivalTextures() {
        return Lists.newArrayList(new ResourceLocation(REFERENCE.MODID, "textures/entity/werewolf/player/survivalist/survivalist_1.png"),new ResourceLocation(REFERENCE.MODID, "textures/entity/werewolf/player/survivalist/survivalist_2.png"));
    }

    private static List<ResourceLocation> getHumanTextures() {
        return Lists.newArrayList(new ResourceLocation(REFERENCE.MODID, "textures/entity/werewolf/player/human/werewolf_ear_claws_1.png"),new ResourceLocation(REFERENCE.MODID, "textures/entity/werewolf/player/human/werewolf_ear_claws_1.png"));
    }

//    private BipedModel.ArmPose func_217766_a(AbstractClientPlayerEntity p_217766_1_, ItemStack p_217766_2_, ItemStack p_217766_3_, Hand p_217766_4_) {
//        BipedModel.ArmPose bipedmodel$armpose = BipedModel.ArmPose.EMPTY;
//        ItemStack itemstack = p_217766_4_ == Hand.MAIN_HAND ? p_217766_2_ : p_217766_3_;
//        if (!itemstack.isEmpty()) {
//            bipedmodel$armpose = BipedModel.ArmPose.ITEM;
//            if (p_217766_1_.getItemInUseCount() > 0) {
//                UseAction useaction = itemstack.getUseAction();
//                if (useaction == UseAction.BLOCK) {
//                    bipedmodel$armpose = BipedModel.ArmPose.BLOCK;
//                } else if (useaction == UseAction.BOW) {
//                    bipedmodel$armpose = BipedModel.ArmPose.BOW_AND_ARROW;
//                } else if (useaction == UseAction.SPEAR) {
//                    bipedmodel$armpose = BipedModel.ArmPose.THROW_SPEAR;
//                } else if (useaction == UseAction.CROSSBOW && p_217766_4_ == p_217766_1_.getActiveHand()) {
//                    bipedmodel$armpose = BipedModel.ArmPose.CROSSBOW_CHARGE;
//                }
//            } else {
//                boolean flag3 = p_217766_2_.getItem() == Items.CROSSBOW;
//                boolean flag = CrossbowItem.isCharged(p_217766_2_);
//                boolean flag1 = p_217766_3_.getItem() == Items.CROSSBOW;
//                boolean flag2 = CrossbowItem.isCharged(p_217766_3_);
//                if (flag3 && flag) {
//                    bipedmodel$armpose = BipedModel.ArmPose.CROSSBOW_HOLD;
//                }
//
//                if (flag1 && flag2 && p_217766_2_.getItem().getUseAction(p_217766_2_) == UseAction.NONE) {
//                    bipedmodel$armpose = BipedModel.ArmPose.CROSSBOW_HOLD;
//                }
//            }
//        }
//
//        return bipedmodel$armpose;
//    }
}
