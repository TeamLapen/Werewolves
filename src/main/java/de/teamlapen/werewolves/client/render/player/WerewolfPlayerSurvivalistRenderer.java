package de.teamlapen.werewolves.client.render.player;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.client.core.ModModelRender;
import de.teamlapen.werewolves.client.model.WerewolfSurvivalistModel;
import de.teamlapen.werewolves.client.render.WerewolfPlayerRenderer;
import de.teamlapen.werewolves.client.render.layer.WerewolfFormFaceOverlayLayer;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

public class WerewolfPlayerSurvivalistRenderer extends WerewolfPlayerRenderer<AbstractClientPlayer, WerewolfSurvivalistModel<AbstractClientPlayer>> {

    private final List<ResourceLocation> textures;
    public WerewolfPlayerSurvivalistRenderer(EntityRendererProvider.Context context) {
        super(context, new WerewolfSurvivalistModel<>(context.bakeLayer(ModModelRender.WEREWOLF_SURVIVALIST)), 0.5F);
        this.textures = getSurvivalTextures();

        this.addLayer(new WerewolfFormFaceOverlayLayer<>(WerewolfForm.SURVIVALIST, this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull AbstractClientPlayer player) {
        WerewolfPlayer werewolf = WerewolfPlayer.get(player);
        return textures.get(werewolf.getSkinType(WerewolfForm.SURVIVALIST) % textures.size());
    }

    @Nonnull
    public static List<ResourceLocation> getSurvivalTextures() {
        List<ResourceLocation> locs = Minecraft.getInstance().getResourceManager().listResources("textures/entity/werewolf/survivalist", s -> s.getPath().endsWith(".png")).keySet().stream().filter(r -> REFERENCE.MODID.equals(r.getNamespace())).collect(Collectors.toList());
        if (locs.size() < WerewolfForm.SURVIVALIST.getSkinTypes()) {
            for (int i = 0; i < WerewolfForm.SURVIVALIST.getSkinTypes(); i++) {
                ResourceLocation s = new ResourceLocation(REFERENCE.MODID, "textures/entity/werewolf/survivalist/survivalist_" + i + ".png");
                if (!locs.contains(s)) {
                    locs.add(s);
                }
            }
        }
        return locs;
    }

    @Override
    public void renderRightHand(PoseStack stack, MultiBufferSource bufferSource, int p_117773_, AbstractClientPlayer entity) {

    }

    @Override
    public void renderLeftHand(PoseStack stack, MultiBufferSource bufferSource, int p_117816_, AbstractClientPlayer entity) {

    }
}
