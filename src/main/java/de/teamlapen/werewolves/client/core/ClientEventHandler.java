package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


@OnlyIn(Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public void onRenderPlayer(RenderPlayerEvent.Pre event) {
        AbstractClientPlayerEntity player = (AbstractClientPlayerEntity)event.getPlayer();
        if(Helper.isWerewolf(player) && WerewolfPlayer.getOpt(player).map(WerewolfPlayer::getSpecialAttributes).map(attributes ->attributes.trueForm).orElse(false)) {
            event.setCanceled(true);
            WEntityRenderer.render.doRender(player,event.getX(),event.getY(),event.getZ(), MathHelper.lerp(event.getPartialRenderTick(), player.prevRotationYaw, player.rotationYaw),event.getPartialRenderTick());
        }
    }
}
