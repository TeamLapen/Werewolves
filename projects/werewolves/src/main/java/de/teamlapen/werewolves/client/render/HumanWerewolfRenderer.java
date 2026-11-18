package de.teamlapen.werewolves.client.render;

import de.teamlapen.library.client.models.entities.ClothedModel;
import de.teamlapen.library.client.renderer.DualBipedRenderer;
import de.teamlapen.library.client.renderer.entities.state.AvatarLikeRenderState;
import de.teamlapen.werewolves.common.world.entities.werewolf.HumanWerewolfEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.PlayerSkin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class HumanWerewolfRenderer extends DualBipedRenderer<HumanWerewolfEntity, AvatarLikeRenderState, ClothedModel<AvatarLikeRenderState>> {

    private final PlayerSkin[] textures;

    public HumanWerewolfRenderer(EntityRendererProvider.Context context) {
        super(context, new ClothedModel<>(context.bakeLayer(ModelLayers.PLAYER), false), new ClothedModel<>(context.bakeLayer(ModelLayers.PLAYER_SLIM), true), 0.5f);
        this.textures = gatherTextures("textures/entity/human", true);
    }

    @Override
    public @NotNull AvatarLikeRenderState createRenderState() {
        return new AvatarLikeRenderState();
    }

    @Override
    public void extractRenderState(@NotNull HumanWerewolfEntity entity, @NotNull AvatarLikeRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.skin = textures[Math.max(0, entity.getSkinType()) % textures.length];
    }

    @Override
    protected PlayerSkin @NotNull [] gatherTextures(@NotNull String dirPath, boolean required) {
        Collection<Identifier> hunterTextures = new ArrayList<>(Minecraft.getInstance().getResourceManager().listResources(dirPath, s -> s.getPath().endsWith(".png")).keySet());
        PlayerSkin[] textures = separateSlimTextures(hunterTextures.stream().filter(r -> REFERENCE.MODID.equals(r.getNamespace())));
        if (textures.length == 0 && required) {
            throw new IllegalStateException("Must have at least one hunter texture: " + REFERENCE.MODID + ":" + dirPath + "/texture.png");
        }
        return textures;
    }
}
