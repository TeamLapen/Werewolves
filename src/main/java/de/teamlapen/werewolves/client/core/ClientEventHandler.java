package de.teamlapen.werewolves.client.core;

import de.teamlapen.vampirism.api.entity.player.actions.IActionHandler;
import de.teamlapen.vampirism.client.gui.screens.VampirismContainerScreen;
import de.teamlapen.werewolves.api.client.gui.ScreenAccessor;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.client.gui.ExpBar;
import de.teamlapen.werewolves.client.gui.WerewolfPlayerAppearanceScreen;
import de.teamlapen.werewolves.client.render.player.WerewolfPlayerBeastRenderer;
import de.teamlapen.werewolves.client.render.player.WerewolfPlayerSurvivalistRenderer;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModActions;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.FormHelper;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;


@OnlyIn(Dist.CLIENT)
public class ClientEventHandler {
    private int zoomTime = 0;
    private double zoomAmount = 0;
    private double zoomModifier = 0;

    @SubscribeEvent
    public void onFOVModifier(ViewportEvent.ComputeFov event) {
        if (this.zoomTime > 0) {
            event.setFOV(event.getFOV() - this.zoomModifier);
            this.zoomModifier -= this.zoomAmount;
            --this.zoomTime;
        }
    }


    @SubscribeEvent
    public void onGuiInitPost(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof VampirismContainerScreen) {
            if (Helper.isWerewolf(Minecraft.getInstance().player)) {
                ResourceLocation icon = new ResourceLocation(REFERENCE.MODID, "textures/gui/appearance_button.png");
                var button = ((ScreenAccessor) event.getScreen()).invokeAddRenderableWidget_werewolves(new ImageButton(((VampirismContainerScreen) event.getScreen()).getGuiLeft() + 47, ((VampirismContainerScreen) event.getScreen()).getGuiTop() + 90, 20, 20, 0, 0, 20, icon, 20, 40, (context) -> {
                    Minecraft.getInstance().setScreen(new WerewolfPlayerAppearanceScreen(event.getScreen()));
                }, Component.empty()));
                button.setTooltip(Tooltip.create(Component.translatable("gui.vampirism.vampirism_menu.appearance_menu")));

                WerewolfPlayer.getOptSave(Minecraft.getInstance().player).ifPresent(werewolf -> {
                    if (werewolf.getMaxLevel() == werewolf.getLevel()) return;
                    AbstractContainerScreen<?> screen = ((AbstractContainerScreen<?>) event.getScreen());
                    ((ScreenAccessor) event.getScreen()).invokeAddRenderableWidget_werewolves(new ExpBar(screen.getGuiLeft() - 14, screen.getGuiTop()));
                });
            }
        }
    }

    @SubscribeEvent
    public void onRenderNamePlate(RenderNameTagEvent event) {
        if (event.getEntity() instanceof Player) {
            if (Helper.isWerewolf((Player) event.getEntity())) {
                WerewolfPlayer werewolf = WerewolfPlayer.get(((Player) event.getEntity()));
                IActionHandler<IWerewolfPlayer> d = werewolf.getActionHandler();
                if (d.isActionActive(ModActions.HIDE_NAME.get()) && FormHelper.isFormActionActive(werewolf)) {
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }

    public void onAddLayer(EntityRenderersEvent.AddLayers event) {
        ClientRegistryHandler.initRenderer(event.getContext());
    }

    @SubscribeEvent
    public void onPlayerRender(RenderPlayerEvent.Pre event) {
        if (Helper.isWerewolf(event.getEntity())) {
            if (ClientRegistryHandler.getModPlayerRenderer().renderPlayer((AbstractClientPlayer) event.getEntity(), 1, event.getPartialTick(), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight())) {
                event.setCanceled(true);
            }
        }
    }

    public void onZoomPressed() {
        this.zoomTime = 20;
        this.zoomAmount = Minecraft.getInstance().options.fov.get() / 4f / this.zoomTime;
        this.zoomModifier = Minecraft.getInstance().options.fov.get() - Minecraft.getInstance().options.fov.get() / 4f;
    }

    private static boolean shouldShowInTooltip(int p_242394_0_, ItemStack.TooltipPart p_242394_1_) {
        return (p_242394_0_ & p_242394_1_.getMask()) == 0;
    }

    private int getHideFlags(ItemStack stack) {
        return stack.hasTag() && stack.getTag().contains("HideFlags", 99) ? stack.getTag().getInt("HideFlags") : 0;
    }


    @SubscribeEvent
    public void onLevelJoined(ClientPlayerNetworkEvent.LoggingIn event) {
        if (!WerewolvesConfig.CLIENT.mcaMessage.get() && ModList.get().isLoaded("mca")) {
            WerewolvesConfig.CLIENT.mcaMessage.set(true);
            Minecraft.getInstance().player.sendSystemMessage(Component.translatable("text.werewolves.mca_integration.first").append(Component.translatable("text.werewolves.mca_integration.seconds").withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/TeamLapen/Werewolves/issues/182")).withUnderlined(true))));
        }
    }
}
