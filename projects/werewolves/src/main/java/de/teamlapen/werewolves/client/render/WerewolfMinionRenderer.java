package de.teamlapen.werewolves.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.client.render.state.WerewolfRenderState;
import de.teamlapen.werewolves.common.world.entities.minion.WerewolfMinionEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.NotNull;

public class WerewolfMinionRenderer extends BaseWerewolfRenderer<WerewolfMinionEntity> {

    public WerewolfMinionRenderer(EntityRendererProvider.Context context) {
        super(context, 0.3f);
    }

    @Override
    protected void scale(@NotNull WerewolfRenderState state, @NotNull PoseStack matrixStack) {
        float s = state.scale;
        matrixStack.scale(s, s, s);
    }

    public int getSkinTextureCount(WerewolfForm form) {
        return this.getWrapper(form).textures().size();
    }

    public int getEyeTextureCount(WerewolfForm form) {
        return REFERENCE.EYE_TYPE_COUNT;
    }
}
