package de.teamlapen.werewolves.client.render;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import de.teamlapen.werewolves.client.gui.WerewolfPlayerAppearanceScreen;
import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.client.model.WerewolfBeastModel;
import de.teamlapen.werewolves.client.model.WerewolfEarsModel;
import de.teamlapen.werewolves.client.model.WerewolfSurvivalistModel;
import de.teamlapen.werewolves.client.render.layer.WerewolfFaceOverlayLayer;
import de.teamlapen.werewolves.player.WerewolfForm;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
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
        WerewolfForm form = entity.getForm();
        if (Minecraft.getInstance().currentScreen instanceof WerewolfPlayerAppearanceScreen) {
            form = ((WerewolfPlayerAppearanceScreen) Minecraft.getInstance().currentScreen).getActiveForm();
        }
        this.switchModel(form);
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

    public WerewolfForm getForm() {
        return form;
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
        return Lists.newArrayList(new ResourceLocation(REFERENCE.MODID, "textures/entity/werewolf/player/human/werewolf_ear_claws_1.png"),new ResourceLocation(REFERENCE.MODID, "textures/entity/werewolf/player/human/werewolf_ear_claws_2.png"));
    }
}
