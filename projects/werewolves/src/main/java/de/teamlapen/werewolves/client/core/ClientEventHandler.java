package de.teamlapen.werewolves.client.core;

import de.teamlapen.faction.client.gui.screens.ILastScreenProvider;

import de.teamlapen.werewolves.client.render.state.WerewolfRenderState;

import de.teamlapen.werewolves.client.render.WerewolfPlayerRenderer;

import de.teamlapen.werewolves.client.WerewolvesModClient;

import de.teamlapen.faction.client.gui.screens.FactionMenuScreen;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.api.client.gui.ScreenAccessor;
import de.teamlapen.werewolves.client.gui.ExpBar;
import de.teamlapen.werewolves.client.gui.WerewolfPlayerAppearanceScreen;
import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.event.*;

import java.net.URI;


public class ClientEventHandler {
    private int zoomTime = 0;
    private double zoomAmount = 0;
    private double zoomModifier = 0;

    @SubscribeEvent
    public void onFOVModifier(ViewportEvent.ComputeFov event) {
        if (this.zoomTime > 0) {
            event.setFOV((float) (event.getFOV() - this.zoomModifier));
            this.zoomModifier -= this.zoomAmount;
            --this.zoomTime;
        }
    }


    @SubscribeEvent
    public void onGuiInitPost(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof FactionMenuScreen) {
            if (Helper.isWerewolf(Minecraft.getInstance().player)) {
                WidgetSprites icon = new WidgetSprites(WIdentifier.loc("vampirism","widget/appearance"), WIdentifier.loc("vampirism","widget/appearance_highlighted"));
                var button = ((ScreenAccessor) event.getScreen()).werewolves$addRenderableWidget(new ImageButton(((FactionMenuScreen) event.getScreen()).getLeftPos() + 47, ((FactionMenuScreen) event.getScreen()).getTopPos() + 90, 20, 20, icon, (context) -> Minecraft.getInstance().setScreen(new WerewolfPlayerAppearanceScreen(ILastScreenProvider.current())), Component.empty()));
                button.setTooltip(Tooltip.create(Component.translatable("gui.vampirism.vampirism_menu.appearance_menu")));

                WerewolfPlayer werewolf = WerewolfPlayer.get(Minecraft.getInstance().player);
                if (werewolf.getMaxLevel() == werewolf.getLevel()) return;
                AbstractContainerScreen<?> screen = ((AbstractContainerScreen<?>) event.getScreen());
                ((ScreenAccessor) event.getScreen()).werewolves$addRenderableWidget(new ExpBar(screen.getLeftPos() - 14, screen.getTopPos()));
            }
        }
    }

    @SubscribeEvent
    public void onRenderNamePlate(RenderNameTagEvent.CanRender event) {
//        if (event.getEntity() instanceof Player) { TODO
//            if (Helper.isWerewolf((Player) event.getEntity())) {
//                WerewolfPlayer werewolf = WerewolfPlayer.get(((Player) event.getEntity()));
//                IActionHandler<IWerewolfPlayer> d = werewolf.getActionHandler();
//                if (d.isActionActive(ModActions.HIDE_NAME) && FormHelper.isFormActionActive(werewolf)) {
//                    event.setCanRender(TriState.FALSE);
//                }
//            }
//        }
    }

    @SubscribeEvent
    public void onPlayerRender(RenderPlayerEvent.Pre<?> event) {
        WerewolfRenderState werewolfState = WerewolfPlayerRenderer.getState(event.getRenderState());
        ModPlayerRenderer renderer = WerewolvesModClient.services().playerRenderer();
        if (werewolfState != null && renderer != null) {
            if (renderer.submit(werewolfState, event.getPoseStack(), event.getSubmitNodeCollector())) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onRenderArm(RenderArmEvent event) {
        ModPlayerRenderer renderer = WerewolvesModClient.services().playerRenderer();
        if (renderer != null && Helper.isWerewolf(event.getPlayer()) && renderer.renderArm(event.getPlayer(), event.getPoseStack(), event.getSubmitNodeCollector(), event.getPackedLight(), event.getArm())) {
            event.setCanceled(true);
        }
    }

    public void onZoomPressed() {
        this.zoomTime = 20;
        this.zoomAmount = Minecraft.getInstance().options.fov().get() / 4f / this.zoomTime;
        this.zoomModifier = Minecraft.getInstance().options.fov().get() - Minecraft.getInstance().options.fov().get() / 4f;
    }

    @SubscribeEvent
    public void onLevelJoined(ClientPlayerNetworkEvent.LoggingIn event) {
        if (!ModConfig.client().mcaMessage.get() && ModList.get().isLoaded("mca")) {
            ModConfig.client().mcaMessage.set(true);
            Minecraft.getInstance().player.sendSystemMessage(Component.translatable("text.werewolves.mca_integration.first").append(Component.translatable("text.werewolves.mca_integration.seconds").withStyle(style -> style.withClickEvent(new ClickEvent.OpenUrl(URI.create("https://github.com/TeamLapen/Werewolves/issues/182"))).withUnderlined(true))));
        }
    }
}
