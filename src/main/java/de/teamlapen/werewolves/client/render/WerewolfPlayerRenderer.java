package de.teamlapen.werewolves.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.teamlapen.werewolves.client.gui.WerewolfPlayerAppearanceScreen;
import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.client.model.WerewolfBeastModel;
import de.teamlapen.werewolves.client.model.WerewolfEarsModel;
import de.teamlapen.werewolves.client.model.WerewolfSurvivalistModel;
import de.teamlapen.werewolves.client.render.layer.WerewolfPlayerFaceOverlayLayer;
import de.teamlapen.werewolves.player.WerewolfForm;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@OnlyIn(Dist.CLIENT)
public class WerewolfPlayerRenderer extends LivingRenderer<AbstractClientPlayerEntity, WerewolfBaseModel<AbstractClientPlayerEntity>> {

    private static final Logger LOGGER = LogManager.getLogger();

    private static class WerewolfModelWrapper {
        private final Supplier<WerewolfBaseModel<AbstractClientPlayerEntity>> modelSupplier;
        private WerewolfBaseModel<AbstractClientPlayerEntity> model;
        private final Collection<LayerRenderer<AbstractClientPlayerEntity, WerewolfBaseModel<AbstractClientPlayerEntity>>> layers = new ArrayList<>();
        private final Function<WerewolfPlayerRenderer, Collection<LayerRenderer<AbstractClientPlayerEntity, WerewolfBaseModel<AbstractClientPlayerEntity>>>> layersFactory;
        private final Supplier<List<ResourceLocation>> textures;
        private final float shadow;
        private final boolean skipPlayerModel;

        public WerewolfModelWrapper(Supplier<WerewolfBaseModel<AbstractClientPlayerEntity>> model, Function<WerewolfPlayerRenderer, Collection<LayerRenderer<AbstractClientPlayerEntity, WerewolfBaseModel<AbstractClientPlayerEntity>>>> layersFactory, Supplier<List<ResourceLocation>> textures, float shadow, boolean skipPlayerModel) {
            this.modelSupplier = model;
            this.textures = textures;
            this.shadow = shadow;
            this.skipPlayerModel = skipPlayerModel;
            this.layersFactory = layersFactory;
        }

        public WerewolfModelWrapper(Supplier<WerewolfBaseModel<AbstractClientPlayerEntity>> model, Function<WerewolfPlayerRenderer, Collection<LayerRenderer<AbstractClientPlayerEntity, WerewolfBaseModel<AbstractClientPlayerEntity>>>> layersFactory, ResourceLocation texture, float shadow, boolean skipPlayerModel) {
            this(model, layersFactory, () -> Collections.singletonList(texture), shadow, skipPlayerModel);
        }

        public WerewolfModelWrapper(Supplier<WerewolfBaseModel<AbstractClientPlayerEntity>> model, ResourceLocation textures, float shadow, boolean skipPlayerModel) {
            this(model, (a)-> Collections.emptyList(), () -> Collections.singletonList(textures), shadow, skipPlayerModel);
        }
    }

    private static final Map<WerewolfForm, WerewolfModelWrapper> MODELS = new HashMap<>();

    static {
        addModel(WerewolfForm.NONE, new WerewolfModelWrapper(()->null, null, 0, false));
        addModel(WerewolfForm.HUMAN, new WerewolfModelWrapper(WerewolfEarsModel::new, (renderer) -> Collections.emptyList(), WerewolfPlayerRenderer::getHumanTextures, 0.5f, false));
        addModel(WerewolfForm.BEAST, new WerewolfModelWrapper(WerewolfBeastModel::new, (renderer) -> Collections.singleton(new WerewolfPlayerFaceOverlayLayer(renderer)), WerewolfPlayerRenderer::getBeastTextures , 1.3f, true));
        addModel(WerewolfForm.SURVIVALIST, new WerewolfModelWrapper(WerewolfSurvivalistModel::new, (renderer) -> Collections.singleton(new WerewolfPlayerFaceOverlayLayer(renderer)), WerewolfPlayerRenderer::getSurvivalTextures, 0.5f, true));
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
            m.model = m.modelSupplier.get();
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
        if (this.entityModel != null && this.skipPlayerModel) {
                this.render(((AbstractClientPlayerEntity) entity.getRepresentingPlayer()), entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
                return true;
        }
        return false;
    }

    public void renderPost(PlayerModel<AbstractClientPlayerEntity> entityModel, WerewolfPlayer entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        if (this.entityModel != null && !this.skipPlayerModel) {
            this.entityModel.setPlayerModel(entityModel);
            render(((AbstractClientPlayerEntity) entity.getRepresentingPlayer()), entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        }
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

    @Override
    protected void applyRotations(AbstractClientPlayerEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        if (entityLiving.getSwimAnimation(partialTicks) > 0.0F && this.form.isHumanLike()) {
            float f3 = entityLiving.isInWater() ? -90.0F - entityLiving.rotationPitch : -90.0F;
            float f4 = MathHelper.lerp(entityLiving.getSwimAnimation(partialTicks), 0.0F, f3);
            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(f4));
            if (entityLiving.isActualySwimming()) {
                matrixStackIn.translate(0.0D, -1.0D, 0.3F);
            }
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
        List<ResourceLocation> locs = Minecraft.getInstance().getResourceManager().getAllResourceLocations("textures/entity/werewolf/beast", s -> s.endsWith(".png")).stream().filter(r -> REFERENCE.MODID.equals(r.getNamespace())).collect(Collectors.toList());
        if (locs.size() < WerewolfForm.BEAST.getSkinTypes()) {
            LOGGER.error("Could not find all textures for the beast werewolf form");
            for (int i = 0; i < WerewolfForm.BEAST.getSkinTypes(); i++) {
                ResourceLocation s = new ResourceLocation(REFERENCE.MODID, "textures/entity/werewolf/beast/beast_" + i + ".png");
                if (!locs.contains(s))  {
                    locs.add(s);
                }
            }
        }
        return locs;
    }

    private static List<ResourceLocation> getSurvivalTextures() {
        List<ResourceLocation> locs = Minecraft.getInstance().getResourceManager().getAllResourceLocations("textures/entity/werewolf/survivalist", s -> s.endsWith(".png")).stream().filter(r -> REFERENCE.MODID.equals(r.getNamespace())).collect(Collectors.toList());
        if (locs.size() < WerewolfForm.SURVIVALIST.getSkinTypes()) {
            LOGGER.error("Could not find all textures for the survivalist werewolf form");
            for (int i = 0; i < WerewolfForm.SURVIVALIST.getSkinTypes(); i++) {
                ResourceLocation s = new ResourceLocation(REFERENCE.MODID, "textures/entity/werewolf/survivalist/survivalist_" + i + ".png");
                if (!locs.contains(s))  {
                    locs.add(s);
                }
            }
        }
        return locs;
    }

    private static List<ResourceLocation> getHumanTextures() {
        List<ResourceLocation> locs = Minecraft.getInstance().getResourceManager().getAllResourceLocations("textures/entity/werewolf/human", s -> s.endsWith(".png")).stream().filter(r -> REFERENCE.MODID.equals(r.getNamespace())).collect(Collectors.toList());
        if (locs.size() < WerewolfForm.HUMAN.getSkinTypes()) {
            LOGGER.error("Could not find all textures for the human werewolf form");
            for (int i = 0; i < WerewolfForm.HUMAN.getSkinTypes(); i++) {
                ResourceLocation s = new ResourceLocation(REFERENCE.MODID, "textures/entity/werewolf/human/werewolf_ear_claws_" + i + ".png");
                if (!locs.contains(s))  {
                    locs.add(s);
                }
            }
        }
        return locs;
    }
}
