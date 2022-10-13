package de.teamlapen.werewolves.client.core;

import de.teamlapen.vampirism.api.entity.player.actions.IActionHandler;
import de.teamlapen.vampirism.client.gui.screens.VampirismContainerScreen;
import de.teamlapen.werewolves.api.client.gui.ScreenAccessor;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.client.gui.ExpBar;
import de.teamlapen.werewolves.client.gui.StoneAltarScreen;
import de.teamlapen.werewolves.client.gui.WerewolfPlayerAppearanceScreen;
import de.teamlapen.werewolves.core.ModActions;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.FormHelper;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderNameTagEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;


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
        boolean vampirismScreen = event.getScreen() instanceof VampirismContainerScreen;
        boolean stoneAltar = event.getScreen() instanceof StoneAltarScreen; //TODO move into stone altar screen
        if (vampirismScreen || stoneAltar) {
            if (Helper.isWerewolf(Minecraft.getInstance().player)) {
                if (vampirismScreen) {
                    ResourceLocation icon = new ResourceLocation(REFERENCE.MODID, "textures/gui/appearance_button.png");
                    ((ScreenAccessor) event.getScreen()).invokeAddRenderableWidget_werewolves(new ImageButton(((VampirismContainerScreen) event.getScreen()).getGuiLeft() + 47, ((VampirismContainerScreen) event.getScreen()).getGuiTop() + 90, 20, 20, 0, 0, 20, icon, 20, 40, (context) -> {
                        Minecraft.getInstance().setScreen(new WerewolfPlayerAppearanceScreen(event.getScreen()));
                    }, (button1, matrixStack, mouseX, mouseY) -> {
                        event.getScreen().renderTooltip(matrixStack, Component.translatable("gui.vampirism.vampirism_menu.appearance_menu"), mouseX, mouseY);
                    }, Component.empty()));
                }

                WerewolfPlayer.getOpt(Minecraft.getInstance().player).ifPresent(werewolf -> {
                    if (werewolf.getMaxLevel() == werewolf.getLevel()) return;
                    AbstractContainerScreen<?> screen = ((AbstractContainerScreen<?>) event.getScreen());
                    ((ScreenAccessor) event.getScreen()).invokeAddRenderableWidget_werewolves(new ExpBar(screen.getGuiLeft() - 14, screen.getGuiTop(), screen));
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
}
