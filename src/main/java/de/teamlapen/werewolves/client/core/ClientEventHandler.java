package de.teamlapen.werewolves.client.core;

import de.teamlapen.vampirism.client.core.ModKeys;
import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.core.WerewolfActions;
import de.teamlapen.werewolves.core.WerewolfSkills;
import de.teamlapen.werewolves.network.InputEventPacket;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


@OnlyIn(Dist.CLIENT)
public class ClientEventHandler {
    private static final KeyBinding BITE = ModKeys.getKeyBinding(ModKeys.KEY.SUCK);
    private boolean suck_down;
    private int zoomTime = 0;
    private double zoomAmount = 0;


    @SubscribeEvent
    public void onRenderPlayer(RenderPlayerEvent.Pre event) {
        AbstractClientPlayerEntity player = (AbstractClientPlayerEntity) event.getPlayer();
        if (Helper.isWerewolf(player) && WerewolfPlayer.getOpt(player).map(WerewolfPlayer::getSpecialAttributes).map(attributes -> attributes.werewolfForm).orElse(false)) {
            event.setCanceled(WEntityRenderer.render.render(WerewolfPlayer.get(player), event.getX(), event.getY(), event.getZ(), MathHelper.lerp(event.getPartialRenderTick(), player.prevRotationYaw, player.rotationYaw), event.getPartialRenderTick()));
        }
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (this.zoomTime > 0) {
            Minecraft.getInstance().gameSettings.fov += this.zoomAmount;
            this.zoomTime--;
        }
    }

    @SubscribeEvent
    public void handleInputEvent(InputEvent event) {
        if (Minecraft.getInstance().player == null) return;
        if (WerewolfPlayer.getOpt(Minecraft.getInstance().player).map(player -> player.getSkillHandler().isSkillEnabled(WerewolfSkills.bite) && !player.getActionHandler().isActionOnCooldown(WerewolfActions.bite)).orElse(false)) {
            if (!BITE.isKeyDown()) {
                suck_down = true;
            }
            if (BITE.isKeyDown() && suck_down && !isZoomActive()) {
                suck_down = false;
                RayTraceResult mouseOver = Minecraft.getInstance().objectMouseOver;
                PlayerEntity player = Minecraft.getInstance().player;
                if (mouseOver != null && mouseOver.getType() == RayTraceResult.Type.ENTITY && !player.isSpectator() && FactionPlayerHandler.get(player).isInFaction(WReference.WEREWOLF_FACTION) && WerewolfPlayer.get(player).canBite(((EntityRayTraceResult) mouseOver).getEntity())) {
                    WerewolvesMod.dispatcher.sendToServer(new InputEventPacket(InputEventPacket.BITE, "" + ((EntityRayTraceResult) mouseOver).getEntity().getEntityId()));
                    onZoomPressed();
                }
            }
        }
    }

    @SubscribeEvent
    public void onGuiInitPre(GuiScreenEvent.InitGuiEvent.Pre event) {
        if(event.getGui() instanceof InventoryScreen) {
            if (Helper.isWerewolf(Minecraft.getInstance().player)) {
                if (WerewolfPlayer.get(Minecraft.getInstance().player).getSpecialAttributes().werewolfForm) {
                    event.setCanceled(true);
                    Minecraft.getInstance().displayGuiScreen(null);
                }
            }
        }
    }

    public void onZoomPressed() {
        this.zoomAmount = Minecraft.getInstance().gameSettings.fov / 4 / 20;
        this.zoomTime = 20;
        Minecraft.getInstance().gameSettings.fov -= Minecraft.getInstance().gameSettings.fov / 4;
    }

    public boolean isZoomActive() {
        return this.zoomTime > 0;
    }
}
