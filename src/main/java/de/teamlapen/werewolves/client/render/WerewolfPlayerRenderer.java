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
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
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
        if (this.form == type) return;
        this.form = type;
        WerewolfModelWrapper werewolfModelWrapper = MODELS.get(type);
        this.model = werewolfModelWrapper.model;
        this.shadowRadius = werewolfModelWrapper.shadow;
        this.textures = werewolfModelWrapper.textures.get();
        this.skipPlayerModel = werewolfModelWrapper.skipPlayerModel;
        this.layers.clear();
        this.layers.addAll(werewolfModelWrapper.layers);
    }

    private void setModelVisible(AbstractClientPlayerEntity clientPlayer) {
        WerewolfBaseModel<AbstractClientPlayerEntity> playerModel = this.getModel();
        if (clientPlayer.isSpectator()) {
            playerModel.setAllVisible(false);
            playerModel.head.visible = true;
        } else {
            playerModel.setAllVisible(true);
            playerModel.crouching = clientPlayer.isCrouching();
        }
    }

    public boolean renderRightArm(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, AbstractClientPlayerEntity playerIn) {
        WerewolfForm form = WerewolfPlayer.get(playerIn).getForm();
        if (form == WerewolfForm.SURVIVALIST) return true;
        this.switchModel(form);
        ModelRenderer arm = this.getModel().getRightArmModel();
        if (arm != null) {
            matrixStackIn.scale(1.2f,1f,1.2f);
            matrixStackIn.translate(0,0.2,0.4);
            this.renderItem(matrixStackIn, bufferIn, combinedLightIn, playerIn, arm);
        }
        return !form.isHumanLike();
    }

    public void renderLeftArm(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, AbstractClientPlayerEntity playerIn) {
        WerewolfForm form = WerewolfPlayer.get(playerIn).getForm();
        if (!form.isHumanLike()) {
            form = WerewolfForm.BEAST;
        }
        this.switchModel(form);
        ModelRenderer arm = this.getModel().getLeftArmModel();
        if (arm != null) {
            matrixStackIn.scale(1.2f,1f,1.2f);
            matrixStackIn.translate(0,0.2,-0.4);
            this.renderItem(matrixStackIn, bufferIn, combinedLightIn, playerIn, arm);
        }
    }

    private void renderItem(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, AbstractClientPlayerEntity playerIn, ModelRenderer rendererArmIn) {
        WerewolfBaseModel<AbstractClientPlayerEntity> model = this.getModel();
        this.setModelVisible(playerIn);
        model.attackTime = 0F;
        model.crouching = false;
        model.swimAmount = 0F;
        model.setupAnim(playerIn, 0, 0, 0, 0, 0);
        rendererArmIn.xRot = 0F;
        rendererArmIn.render(matrixStackIn, bufferIn.getBuffer(RenderType.entitySolid(this.getTextureLocation(playerIn))), combinedLightIn, OverlayTexture.NO_OVERLAY);
    }

    /**
     * @return if the player model should be renderer
     */
    public boolean render(WerewolfPlayer entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        WerewolfForm form = entity.getForm();
        if (Minecraft.getInstance().screen instanceof WerewolfPlayerAppearanceScreen) {
            form = ((WerewolfPlayerAppearanceScreen) Minecraft.getInstance().screen).getActiveForm();
        }
        this.switchModel(form);
        if (this.model != null && this.skipPlayerModel) {
                this.render(((AbstractClientPlayerEntity) entity.getRepresentingPlayer()), entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
                return true;
        }
        return false;
    }

    public void renderPost(PlayerModel<AbstractClientPlayerEntity> entityModel, WerewolfPlayer entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        if (this.model != null && !this.skipPlayerModel) {
            this.model.setPlayerModel(entityModel);
            render(((AbstractClientPlayerEntity) entity.getRepresentingPlayer()), entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        }
    }

    /**
     * use {@link #render(WerewolfPlayer, float, float, MatrixStack, IRenderTypeBuffer, int)}
     */
    @Deprecated
    @Override
    public void render(AbstractClientPlayerEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        if (!entity.isLocalPlayer() || this.entityRenderDispatcher.camera.getEntity() == entity) {
            this.setModelVisible(entity);
            super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        }
    }

    @Override
    protected void setupRotations(AbstractClientPlayerEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
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

    public WerewolfForm getForm() {
        return form;
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull AbstractClientPlayerEntity entity) {
        return this.textures.get(WerewolfPlayer.get(entity).getSkinType(this.form) % this.form.getSkinTypes());
    }

    private static List<ResourceLocation> getBeastTextures() {
        List<ResourceLocation> locs = Minecraft.getInstance().getResourceManager().listResources("textures/entity/werewolf/beast", s -> s.endsWith(".png")).stream().filter(r -> REFERENCE.MODID.equals(r.getNamespace())).collect(Collectors.toList());
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
        List<ResourceLocation> locs = Minecraft.getInstance().getResourceManager().listResources("textures/entity/werewolf/survivalist", s -> s.endsWith(".png")).stream().filter(r -> REFERENCE.MODID.equals(r.getNamespace())).collect(Collectors.toList());
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
        List<ResourceLocation> locs = Minecraft.getInstance().getResourceManager().listResources("textures/entity/werewolf/human", s -> s.endsWith(".png")).stream().filter(r -> REFERENCE.MODID.equals(r.getNamespace())).collect(Collectors.toList());
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
