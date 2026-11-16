package de.teamlapen.werewolves.client.core;

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
                var button = ((ScreenAccessor) event.getScreen()).invokeAddRenderableWidget_werewolves(new ImageButton(((FactionMenuScreen) event.getScreen()).getLeftPos() + 47, ((FactionMenuScreen) event.getScreen()).getTopPos() + 90, 20, 20, icon, (context) -> Minecraft.getInstance().setScreen(new WerewolfPlayerAppearanceScreen(event.getScreen())), Component.empty()));
                button.setTooltip(Tooltip.create(Component.translatable("gui.vampirism.vampirism_menu.appearance_menu")));

                WerewolfPlayer werewolf = WerewolfPlayer.get(Minecraft.getInstance().player);
                if (werewolf.getMaxLevel() == werewolf.getLevel()) return;
                AbstractContainerScreen<?> screen = ((AbstractContainerScreen<?>) event.getScreen());
                ((ScreenAccessor) event.getScreen()).invokeAddRenderableWidget_werewolves(new ExpBar(screen.getLeftPos() - 14, screen.getTopPos()));
            }
        }
    }

    @SubscribeEvent
    public void onRenderNamePlate(RenderNameTagEvent event) {
//        if (event.getEntity() instanceof Player) { TODO
//            if (Helper.isWerewolf((Player) event.getEntity())) {
//                WerewolfPlayer werewolf = WerewolfPlayer.get(((Player) event.getEntity()));
//                IActionHandler<IWerewolfPlayer> d = werewolf.getActionHandler();
//                if (d.isActionActive(ModActions.HIDE_NAME.get()) && FormHelper.isFormActionActive(werewolf)) {
//                    event.setCanRender(TriState.FALSE);
//                }
//            }
//        }
    }

    @SubscribeEvent
    public void onPlayerRender(RenderPlayerEvent.Pre event) {
//        if (Helper.isWerewolf(event.getEntity()) && !PlayerAnimatorCompat.checkAnimation(event.getEntity())) {
//            if (WerewolvesModClient.getInstance().getModPlayerRenderer().renderPlayer((AbstractClientPlayer) event.getEntity(), 1, event.getPartialTick(), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight())) {
//                event.setCanceled(true);
//            } TODO
//        }
    }

    public void onZoomPressed() {
        this.zoomTime = 20;
        this.zoomAmount = Minecraft.getInstance().options.fov().get() / 4f / this.zoomTime;
        this.zoomModifier = Minecraft.getInstance().options.fov().get() - Minecraft.getInstance().options.fov().get() / 4f;
    }

    @SubscribeEvent
    public void onLevelJoined(ClientPlayerNetworkEvent.LoggingIn event) {
        if (!ModConfig.CLIENT.mcaMessage.get() && ModList.get().isLoaded("mca")) {
            ModConfig.CLIENT.mcaMessage.set(true);
            Minecraft.getInstance().player.sendSystemMessage(Component.translatable("text.werewolves.mca_integration.first").append(Component.translatable("text.werewolves.mca_integration.seconds").withStyle(style -> style.withClickEvent(new ClickEvent.OpenUrl(URI.create("https://github.com/TeamLapen/Werewolves/issues/182"))).withUnderlined(true))));
        }
    }
}
