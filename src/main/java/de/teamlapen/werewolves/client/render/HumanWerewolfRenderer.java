package de.teamlapen.werewolves.client.render;

import de.teamlapen.vampirism.client.renderer.entity.DualBipedRenderer;
import de.teamlapen.werewolves.entities.werewolf.HumanWerewolfEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;

@OnlyIn(Dist.CLIENT)
public class HumanWerewolfRenderer extends DualBipedRenderer<HumanWerewolfEntity, PlayerModel<HumanWerewolfEntity>> {

    private final Pair<ResourceLocation, Boolean>[] textures;

    public HumanWerewolfRenderer(EntityRendererProvider.Context context) {
        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER_SLIM), true),0.5f);
        this.textures = gatherTextures("textures/entity/human", true);
    }

    @Override
    protected Pair<ResourceLocation, Boolean> determineTextureAndModel(HumanWerewolfEntity entity) {
        return textures[entity.getSkinType() % textures.length];
    }

    protected Pair<ResourceLocation, Boolean> @NotNull [] gatherTextures(@NotNull String dirPath, boolean required) {
        Collection<ResourceLocation> hunterTextures = new ArrayList<>(Minecraft.getInstance().getResourceManager().listResources(dirPath, s -> s.getPath().endsWith(".png")).keySet());
        Pair<ResourceLocation, Boolean>[] textures = separateSlimTextures(hunterTextures.stream().filter(r -> REFERENCE.MODID.equals(r.getNamespace())));
        if (textures.length == 0 && required) {
            throw new IllegalStateException("Must have at least one hunter texture: " + REFERENCE.MODID + ":" + dirPath + "/texture.png");
        }
        return textures;
    }
}
