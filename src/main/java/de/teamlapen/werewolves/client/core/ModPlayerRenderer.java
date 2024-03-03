package de.teamlapen.werewolves.client.core;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.client.render.player.WerewolfPlayerBeastRenderer;
import de.teamlapen.werewolves.client.render.player.WerewolfPlayerSurvivalistRenderer;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;

import java.util.function.Function;

public class ModPlayerRenderer {

    private final WerewolfPlayerBeastRenderer BEAST_RENDERER;
    private final WerewolfPlayerSurvivalistRenderer SURVIVALIST_RENDERER;

    public ModPlayerRenderer(EntityRendererProvider.Context context) {
        this.BEAST_RENDERER = new WerewolfPlayerBeastRenderer(context);
        this.SURVIVALIST_RENDERER = new WerewolfPlayerSurvivalistRenderer(context);
    }

    public boolean renderPlayer(AbstractClientPlayer player, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int packedLight) {
        return render(player, x -> {
            if (x == WerewolfForm.SURVIVALIST) {
                SURVIVALIST_RENDERER.render(player, entityYaw, partialTicks, stack, buffer, packedLight);
                return true;
            } else if (x == WerewolfForm.BEAST) {
                BEAST_RENDERER.render(player, entityYaw, partialTicks, stack, buffer, packedLight);
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
                    SURVIVALIST_RENDERER.renderLeftHand(stack, bufferSource, pCombinedLight, player);
                } else {
                    SURVIVALIST_RENDERER.renderRightHand(stack, bufferSource, pCombinedLight, player);
                }
                return true;
            } else if (form == WerewolfForm.BEAST) {
                if (arm == HumanoidArm.LEFT) {
                    BEAST_RENDERER.renderLeftHand(stack, bufferSource, pCombinedLight, player);
                } else {
                    BEAST_RENDERER.renderRightHand(stack, bufferSource, pCombinedLight, player);
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
