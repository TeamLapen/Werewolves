package de.teamlapen.werewolves.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import de.teamlapen.werewolves.api.entities.werewolf.IWerewolf;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.client.core.ModModelRender;
import de.teamlapen.werewolves.client.model.*;
import de.teamlapen.werewolves.client.render.layer.WerewolfFormFaceOverlayLayer;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @param <T> must extend {@link IWerewolf} or be {@link net.minecraft.world.entity.player.Player}
 */
public abstract class BaseWerewolfRenderer<T extends LivingEntity> extends LivingEntityRenderer<T, WerewolfBaseModel<T>> {
    private static final Logger LOGGER = LogManager.getLogger();

    private final Map<WerewolfForm, WerewolfModelWrapper<T>> models = new HashMap<>();

    protected final ResourceLocation @NotNull [] eyeOverlays;
    protected List<ResourceLocation> textures;
    protected WerewolfForm form = WerewolfForm.NONE;

    public BaseWerewolfRenderer(EntityRendererProvider.@NotNull Context context, float size) {
        //noinspection ConstantConditions
        super(context, null, 0f);
        this.eyeOverlays = new ResourceLocation[REFERENCE.EYE_TYPE_COUNT];
        for (int i = 0; i < this.eyeOverlays.length; i++) {
            this.eyeOverlays[i] = new ResourceLocation(REFERENCE.MODID + ":textures/entity/werewolf/eye/eye_" + (i) + ".png");
        }

        this.models.put(WerewolfForm.NONE, new WerewolfModelWrapper.Builder<T>()
                .shadow(0)
                .build());
        this.models.put(WerewolfForm.HUMAN, new WerewolfModelWrapper.Builder<T>()
                .model(new WerewolfEarsModel<>(context.bakeLayer(ModModelRender.EARS_CLAWS)))
                .layers(Collections.emptyList())
                .textures(getHumanTextures())
                .shadow(size)
                .build());
        this.models.put(WerewolfForm.BEAST, new WerewolfModelWrapper.Builder<T>()
                .model(new WerewolfBeastModel<>(context.bakeLayer(ModModelRender.WEREWOLF_BEAST)))
                .layers(Collections.singleton(new WerewolfFormFaceOverlayLayer<>(WerewolfForm.BEAST, this, eyeOverlays)))
                .textures(getBeastTextures())
                .shadow(2.6f * size)
                .skipPlayerModel()
                .build());
        this.models.put(WerewolfForm.SURVIVALIST, new WerewolfModelWrapper.Builder<T>()
                .model(new WerewolfSurvivalistModel<>(context.bakeLayer(ModModelRender.WEREWOLF_SURVIVALIST)))
                .layers(Collections.singleton(new WerewolfFormFaceOverlayLayer<>(WerewolfForm.SURVIVALIST, this, eyeOverlays)))
                .textures(getSurvivalTextures())
                .shadow(size)
                .skipPlayerModel()
                .build());
        this.models.put(WerewolfForm.BEAST4L, new WerewolfModelWrapper.Builder<T>()
                .model(new Werewolf4LModel<>(context.bakeLayer(ModModelRender.WEREWOLF_4L)))
                .layers(Collections.singleton(new WerewolfFormFaceOverlayLayer<>(WerewolfForm.BEAST4L, this, eyeOverlays)))
                .textures(getBeastTextures())
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
    protected void setupRotations(@NotNull T entityLiving, @NotNull PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        if (entityLiving.getSwimAmount(partialTicks) > 0.0F && this.form.isHumanLike()) {
            float f3 = entityLiving.isInWater() ? -90.0F - entityLiving.getXRot() : -90.0F;
            float f4 = Mth.lerp(entityLiving.getSwimAmount(partialTicks), 0.0F, f3);
            matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(f4));
            if (entityLiving.isVisuallySwimming()) {
                matrixStackIn.translate(0.0D, -1.0D, 0.3F);
            }
        }
    }

    @NotNull
    @Override
    public ResourceLocation getTextureLocation(@NotNull T entity) {
        return this.textures.get(Helper.asIWerewolf(entity).getSkinType(this.form) % this.form.getSkinTypes());
    }

    @NotNull
    public static List<ResourceLocation> getBeastTextures() {
        List<ResourceLocation> locs = Minecraft.getInstance().getResourceManager().listResources("textures/entity/werewolf/beast", s -> s.getPath().endsWith(".png")).keySet().stream().filter(r -> REFERENCE.MODID.equals(r.getNamespace())).collect(Collectors.toList());
        if (locs.size() < WerewolfForm.BEAST.getSkinTypes()) {
            LOGGER.error("Could not find all textures for the beast werewolf form");
            for (int i = 0; i < WerewolfForm.BEAST.getSkinTypes(); i++) {
                ResourceLocation s = new ResourceLocation(REFERENCE.MODID, "textures/entity/werewolf/beast/beast_" + i + ".png");
                if (!locs.contains(s)) {
                    locs.add(s);
                }
            }
        }
        return locs;
    }

    @NotNull
    public static List<ResourceLocation> getSurvivalTextures() {
        List<ResourceLocation> locs = Minecraft.getInstance().getResourceManager().listResources("textures/entity/werewolf/survivalist", s -> s.getPath().endsWith(".png")).keySet().stream().filter(r -> REFERENCE.MODID.equals(r.getNamespace())).collect(Collectors.toList());
        if (locs.size() < WerewolfForm.SURVIVALIST.getSkinTypes()) {
            LOGGER.error("Could not find all textures for the survivalist werewolf form");
            for (int i = 0; i < WerewolfForm.SURVIVALIST.getSkinTypes(); i++) {
                ResourceLocation s = new ResourceLocation(REFERENCE.MODID, "textures/entity/werewolf/survivalist/survivalist_" + i + ".png");
                if (!locs.contains(s)) {
                    locs.add(s);
                }
            }
        }
        return locs;
    }

    @NotNull
    public static List<ResourceLocation> getHumanTextures() {
        List<ResourceLocation> locs = Minecraft.getInstance().getResourceManager().listResources("textures/entity/werewolf/human", s -> s.getPath().endsWith(".png")).keySet().stream().filter(r -> REFERENCE.MODID.equals(r.getNamespace())).collect(Collectors.toList());
        if (locs.size() < WerewolfForm.HUMAN.getSkinTypes()) {
            LOGGER.error("Could not find all textures for the human werewolf form");
            for (int i = 0; i < WerewolfForm.HUMAN.getSkinTypes(); i++) {
                ResourceLocation s = new ResourceLocation(REFERENCE.MODID, "textures/entity/werewolf/human/werewolf_ear_claws_" + i + ".png");
                if (!locs.contains(s)) {
                    locs.add(s);
                }
            }
        }
        return locs;
    }
}
