package de.teamlapen.werewolves.client.render;

import de.teamlapen.werewolves.common.world.entities.werewolf.HumanWerewolfEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class HumanWerewolfRenderer extends DualBipedRenderer<HumanWerewolfEntity, HumanModel<HumanWerewolfEntity>> {

    private final PlayerSkin[] textures;

    public HumanWerewolfRenderer(EntityRendererProvider.Context context) {
        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER_SLIM), true),0.5f);
        this.textures = gatherTextures("textures/entity/human", true);
    }

    @Override
    protected PlayerSkin determineTextureAndModel(HumanWerewolfEntity entity) {
        return textures[entity.getSkinType() % textures.length];
    }

    protected PlayerSkin @NotNull [] gatherTextures(@NotNull String dirPath, boolean required) {
        Collection<ResourceLocation> hunterTextures = new ArrayList<>(Minecraft.getInstance().getResourceManager().listResources(dirPath, s -> s.getPath().endsWith(".png")).keySet());
        PlayerSkin[] textures = separateSlimTextures(hunterTextures.stream().filter(r -> REFERENCE.MODID.equals(r.getNamespace())));
        if (textures.length == 0 && required) {
            throw new IllegalStateException("Must have at least one hunter texture: " + REFERENCE.MODID + ":" + dirPath + "/texture.png");
        }
        return textures;
    }
}
