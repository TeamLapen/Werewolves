package de.teamlapen.werewolves.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.entities.minion.WerewolfMinionEntity;
import de.teamlapen.werewolves.util.WerewolfForm;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import javax.annotation.Nonnull;

public class WerewolfMinionRenderer extends BaseWerewolfRenderer<WerewolfMinionEntity> {

    public WerewolfMinionRenderer(EntityRendererProvider.Context context) {
        super(context, 0.3f);
    }

    @Override
    public void render(WerewolfMinionEntity entity, float p_225623_2_, float p_225623_3_, @Nonnull PoseStack matrixStack, @Nonnull MultiBufferSource buffer, int p_225623_6_) {
        switchModel(entity.getForm());
        if (this.model == null) return;
        super.render(entity, p_225623_2_, p_225623_3_, matrixStack, buffer, p_225623_6_);
    }

    @Override
    protected void scale(WerewolfMinionEntity entityIn, PoseStack matrixStack, float float1) {
        float s = entityIn.getScale();
        matrixStack.scale(s,s,s);
    }

    public int getSkinTextureCount(WerewolfForm form) {
        return this.getWrapper(form).textures.size();
    }

    public int getEyeTextureCount(WerewolfForm form) {
        return this.eyeOverlays.length;
    }
}
