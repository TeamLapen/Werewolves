package de.teamlapen.werewolves.client.core;

import de.teamlapen.vampirism.api.entity.player.actions.IActionHandler;
import de.teamlapen.vampirism.client.core.ModKeys;
import de.teamlapen.werewolves.core.WerewolfActions;
import de.teamlapen.werewolves.player.IWerewolfPlayer;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;


@OnlyIn(Dist.CLIENT)
public class ClientEventHandler {
    private int zoomTime = 0;
    private double zoomAmount = 0;
    private double zoomModifier = 0;


    @SubscribeEvent
    public void onRenderPlayer(RenderPlayerEvent.Pre event) {
        AbstractClientPlayerEntity player = (AbstractClientPlayerEntity) event.getPlayer();
        if (Helper.isWerewolf(player) && WerewolfPlayer.getOpt(player).map(w -> w.getForm().isTransformed()).orElse(false)) {
            event.setCanceled(WEntityRenderer.render.render(WerewolfPlayer.get(player), MathHelper.lerp(event.getPartialRenderTick(), player.prevRotationYaw, player.rotationYaw), event.getPartialRenderTick(), event.getMatrixStack(), event.getBuffers(), event.getLight()));
        }
    }

    @SubscribeEvent
    public void onFOVModifier(EntityViewRenderEvent.FOVModifier event) {
        if (this.zoomTime > 0) {
            event.setFOV(event.getFOV() - this.zoomModifier);
            this.zoomModifier -= this.zoomAmount;
            --this.zoomTime;
        }
    }


//    @SubscribeEvent
//    public void onGuiInitPost(GuiScreenEvent.InitGuiEvent.Post event) {
//        if (event.getGui() instanceof SkillsScreen) {
//            if (Helper.isWerewolf(Minecraft.getInstance().player)) {
//                ((ScreenMixin) event.getGui()).invokeAddButton_werewolves(new ExpBar(118, 62, ((SkillsScreen) event.getGui())));
//            }
//        }
//    }
    @SubscribeEvent
    public void onRenderNamePlate(RenderNameplateEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            if (Helper.isWerewolf((PlayerEntity) event.getEntity())) {
                WerewolfPlayer werewolf = WerewolfPlayer.get(((PlayerEntity) event.getEntity()));
                IActionHandler<IWerewolfPlayer> d = werewolf.getActionHandler();
                if (d.isActionActive(WerewolfActions.hide_name) && Helper.isFormActionActive(werewolf)) {
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }

    public void onZoomPressed() {
        this.zoomTime = 20;
        this.zoomAmount = Minecraft.getInstance().gameSettings.fov / 4 / this.zoomTime;
        this.zoomModifier = Minecraft.getInstance().gameSettings.fov - Minecraft.getInstance().gameSettings.fov / 4;
    }
}
