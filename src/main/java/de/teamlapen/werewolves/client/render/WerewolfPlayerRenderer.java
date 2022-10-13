package de.teamlapen.werewolves.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.client.core.ModModelRender;
import de.teamlapen.werewolves.client.gui.WerewolfPlayerAppearanceScreen;
import de.teamlapen.werewolves.client.model.Werewolf4LModel;
import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.client.model.WerewolfBeastModel;
import de.teamlapen.werewolves.client.model.WerewolfSurvivalistModel;
import de.teamlapen.werewolves.client.render.layer.WerewolfFormFaceOverlayLayer;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public abstract class WerewolfPlayerRenderer<T extends LivingEntity, E extends HumanoidModel<T>> extends LivingEntityRenderer<T, E>  {

    public static Optional<String> getWerewolfRenderer(AbstractClientPlayer player) {
        if (Helper.isWerewolf(player)) {
            WerewolfPlayer werewolf = WerewolfPlayer.get(player);
            WerewolfForm form = werewolf.getForm();
            if (Minecraft.getInstance().screen instanceof WerewolfPlayerAppearanceScreen && ((WerewolfPlayerAppearanceScreen) Minecraft.getInstance().screen).isRenderForm()) {
                form = ((WerewolfPlayerAppearanceScreen) Minecraft.getInstance().screen).getActiveForm();
            }
            if (form == WerewolfForm.BEAST) {
                return Optional.of(REFERENCE.MODID+":beast");
            } else if (form == WerewolfForm.SURVIVALIST) {
                return Optional.of(REFERENCE.MODID+":survivalist");
            }
        }
        return Optional.empty();
    }

    public WerewolfPlayerRenderer(EntityRendererProvider.Context context, E model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    public abstract void renderRightHand(PoseStack stack, MultiBufferSource bufferSource, int p_117773_, T entity);

    public abstract void renderLeftHand(PoseStack stack, MultiBufferSource bufferSource, int p_117816_, T entity);

    protected void renderHand(PoseStack stack, MultiBufferSource bufferSource, int p_117778_, T entity, ModelPart arm) {
        E model = this.getModel();
        model.attackTime = 0.0F;
        model.crouching = false;
        model.swimAmount = 0.0F;
        model.setupAnim(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        arm.xRot = 0.0F;
        arm.render(stack, bufferSource.getBuffer(RenderType.entitySolid(getTextureLocation(entity))), p_117778_, OverlayTexture.NO_OVERLAY);
    }

}
