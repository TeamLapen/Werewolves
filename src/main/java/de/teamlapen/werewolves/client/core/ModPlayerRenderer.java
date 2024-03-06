package de.teamlapen.werewolves.client.core;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.client.render.player.WerewolfPlayerBeastRenderer;
import de.teamlapen.werewolves.client.render.player.WerewolfPlayerSurvivalistRenderer;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.HumanoidArm;

import java.util.function.Function;

public class ModPlayerRenderer {

    private final WerewolfPlayerBeastRenderer beastRenderer;
    private final WerewolfPlayerSurvivalistRenderer survivalistRenderer;

    public ModPlayerRenderer(EntityRendererProvider.Context context) {
        this.beastRenderer = new WerewolfPlayerBeastRenderer(context);
        this.survivalistRenderer = new WerewolfPlayerSurvivalistRenderer(context);
    }

    public boolean renderPlayer(AbstractClientPlayer player, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int packedLight) {
        return render(player, x -> {
            if (x == WerewolfForm.SURVIVALIST) {
                survivalistRenderer.render(player, entityYaw, partialTicks, stack, buffer, packedLight);
                return true;
            } else if (x == WerewolfForm.BEAST) {
                beastRenderer.render(player, entityYaw, partialTicks, stack, buffer, packedLight);
                return true;
            }
            return false;
        });
    }

    private boolean render(AbstractClientPlayer player, Function<WerewolfForm, Boolean> renderFunction) {
        return WerewolfPlayer.getOpt(player).map(WerewolfPlayer::getForm).filter(s -> s.isTransformed() && !s.isHumanLike()).map(renderFunction).orElse(false);
    }

    public boolean renderArm(AbstractClientPlayer player, PoseStack stack, MultiBufferSource bufferSource, int pCombinedLight, HumanoidArm arm) {
        return render(player, form -> {
            if (form == WerewolfForm.SURVIVALIST) {
                if (arm == HumanoidArm.LEFT) {
                    survivalistRenderer.renderLeftHand(stack, bufferSource, pCombinedLight, player);
                } else {
                    survivalistRenderer.renderRightHand(stack, bufferSource, pCombinedLight, player);
                }
                return true;
            } else if (form == WerewolfForm.BEAST) {
                if (arm == HumanoidArm.LEFT) {
                    beastRenderer.renderLeftHand(stack, bufferSource, pCombinedLight, player);
                } else {
                    beastRenderer.renderRightHand(stack, bufferSource, pCombinedLight, player);
                }
                return true;
            }
            return false;
        });
    }

    public boolean renderArmMap(AbstractClientPlayer player, PoseStack stack, MultiBufferSource bufferSource, int pCombinedLight, HumanoidArm arm) {
        return render(player, form -> true);
    }
}
