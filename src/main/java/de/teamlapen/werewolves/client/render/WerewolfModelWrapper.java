package de.teamlapen.werewolves.client.render;

import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class WerewolfModelWrapper<T extends LivingEntity> {
    private static final Logger LOGGER = LogManager.getLogger();

    private WerewolfBaseModel<T> model;
    private Collection<RenderLayer<T, WerewolfBaseModel<T>>> layers;
    public List<ResourceLocation> textures;
    public final Supplier<WerewolfBaseModel<T>> modelSupplier;
    private final Function<LivingEntityRenderer<T, WerewolfBaseModel<T>>, Collection<RenderLayer<T, WerewolfBaseModel<T>>>> layersFactory;
    public final Supplier<List<ResourceLocation>> texturesSupplier;
    public final float shadow;
    public final boolean skipPlayerModel;

    public WerewolfModelWrapper(Supplier<WerewolfBaseModel<T>> model, Function<LivingEntityRenderer<T, WerewolfBaseModel<T>>, Collection<RenderLayer<T, WerewolfBaseModel<T>>>> layersFactory, Supplier<List<ResourceLocation>> texturesSupplier, float shadow, boolean skipPlayerModel) {
        this.modelSupplier = model;
        this.texturesSupplier = texturesSupplier;
        this.shadow = shadow;
        this.skipPlayerModel = skipPlayerModel;
        this.layersFactory = layersFactory;
    }

    public WerewolfModelWrapper(Supplier<WerewolfBaseModel<T>> model, Function<LivingEntityRenderer<T, WerewolfBaseModel<T>>, Collection<RenderLayer<T, WerewolfBaseModel<T>>>> layersFactory, ResourceLocation texture, float shadow, boolean skipPlayerModel) {
        this(model, layersFactory, () -> Collections.singletonList(texture), shadow, skipPlayerModel);
    }

    public WerewolfModelWrapper(Supplier<WerewolfBaseModel<T>> model, ResourceLocation textures, float shadow, boolean skipPlayerModel) {
        this(model, (a) -> Collections.emptyList(), () -> Collections.singletonList(textures), shadow, skipPlayerModel);
    }

    public void refresh(LivingEntityRenderer<T, WerewolfBaseModel<T>> renderer) {
        this.layers = Collections.unmodifiableCollection(layersFactory.apply(renderer));
        this.model = modelSupplier.get();
        this.textures = texturesSupplier.get();
    }

    public Collection<RenderLayer<T, WerewolfBaseModel<T>>> getLayers() {
        return layers;
    }

    public WerewolfBaseModel<T> getModel() {
        return model;
    }


    public static List<ResourceLocation> getBeastTextures() {
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

    public static List<ResourceLocation> getSurvivalTextures() {
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

    public static List<ResourceLocation> getHumanTextures() {
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
