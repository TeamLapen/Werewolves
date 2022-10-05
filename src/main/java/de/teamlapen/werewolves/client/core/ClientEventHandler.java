package de.teamlapen.werewolves.client.core;

import de.teamlapen.vampirism.api.entity.player.actions.IActionHandler;
import de.teamlapen.vampirism.client.gui.VampirismScreen;
import de.teamlapen.werewolves.client.gui.ExpBar;
import de.teamlapen.werewolves.client.gui.StoneAltarScreen;
import de.teamlapen.werewolves.client.gui.WerewolfPlayerAppearanceScreen;
import de.teamlapen.werewolves.core.ModActions;
import de.teamlapen.werewolves.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.mixin.client.ScreenAccessor;
import de.teamlapen.werewolves.util.FormHelper;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
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
        if (shouldRenderWerewolfForm(player)) {
            event.setCanceled(ModEntityRenderer.render.render(WerewolfPlayer.get(player), MathHelper.lerp(event.getPartialRenderTick(), player.yRotO, player.yRot), event.getPartialRenderTick(), event.getMatrixStack(), event.getBuffers(), event.getLight()));
        }
    }

    @SubscribeEvent
    public void onRenderPlayerPost(RenderPlayerEvent.Post event) {
        AbstractClientPlayerEntity player = (AbstractClientPlayerEntity) event.getPlayer();
        if (shouldRenderWerewolfForm(player)) {
            ModEntityRenderer.render.renderPost(event.getRenderer().getModel(), WerewolfPlayer.get(player), MathHelper.lerp(event.getPartialRenderTick(), player.yRotO, player.yRot), event.getPartialRenderTick(), event.getMatrixStack(), event.getBuffers(), event.getLight());
        }
    }

    private boolean shouldRenderWerewolfForm(AbstractClientPlayerEntity player) {
        return Helper.isWerewolf(player) && (WerewolfPlayer.getOpt(player).map(w -> w.getForm().isTransformed()).orElse(false) || (Minecraft.getInstance().screen instanceof WerewolfPlayerAppearanceScreen && ((WerewolfPlayerAppearanceScreen) Minecraft.getInstance().screen).isRenderForm()));
    }

    @SubscribeEvent
    public void onFOVModifier(EntityViewRenderEvent.FOVModifier event) {
        if (this.zoomTime > 0) {
            event.setFOV(event.getFOV() - this.zoomModifier);
            this.zoomModifier -= this.zoomAmount;
            --this.zoomTime;
        }
    }


    @SubscribeEvent
    public void onGuiInitPost(GuiScreenEvent.InitGuiEvent.Post event) {
        boolean vampirismScreen = event.getGui() instanceof VampirismScreen;
        boolean stoneAltar = event.getGui() instanceof StoneAltarScreen; //TODO move into stone altar screen
        if (vampirismScreen || stoneAltar) {
            if (Helper.isWerewolf(Minecraft.getInstance().player)) {
                if (vampirismScreen) {
                    ResourceLocation icon = new ResourceLocation(REFERENCE.MODID, "textures/gui/appearance_button.png");
                    ((ScreenAccessor) event.getGui()).invokeAddButton_werewolves(new ImageButton(((VampirismScreen) event.getGui()).getGuiLeft() + 47, ((VampirismScreen) event.getGui()).getGuiTop() + 90, 20, 20, 0, 0, 20, icon, 20, 40, (context) -> {
                        Minecraft.getInstance().setScreen(new WerewolfPlayerAppearanceScreen(event.getGui()));
                    }, (button1, matrixStack, mouseX, mouseY) -> {
                        event.getGui().renderTooltip(matrixStack, new TranslationTextComponent("gui.vampirism.vampirism_menu.appearance_menu"), mouseX, mouseY);
                    }, StringTextComponent.EMPTY));
                }
                WerewolfPlayer.getOpt(Minecraft.getInstance().player).ifPresent(werewolf -> {
                    if (werewolf.getMaxLevel() == werewolf.getLevel()) return;
                    ContainerScreen<?> screen = ((ContainerScreen<?>) event.getGui());
                    ((ScreenAccessor) event.getGui()).invokeAddButton_werewolves(new ExpBar(screen.getGuiLeft()-14, screen.getGuiTop(), screen));
                });
            }
        }
    }

    @SubscribeEvent
    public void onRenderNamePlate(RenderNameplateEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            if (Helper.isWerewolf((PlayerEntity) event.getEntity())) {
                WerewolfPlayer werewolf = WerewolfPlayer.get(((PlayerEntity) event.getEntity()));
                IActionHandler<IWerewolfPlayer> d = werewolf.getActionHandler();
                if (d.isActionActive(ModActions.HIDE_NAME.get()) && FormHelper.isFormActionActive(werewolf)) {
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }

    public void onZoomPressed() {
        this.zoomTime = 20;
        this.zoomAmount = Minecraft.getInstance().options.fov / 4 / this.zoomTime;
        this.zoomModifier = Minecraft.getInstance().options.fov - Minecraft.getInstance().options.fov / 4;
    }

    private static boolean shouldShowInTooltip(int p_242394_0_, ItemStack.TooltipDisplayFlags p_242394_1_) {
        return (p_242394_0_ & p_242394_1_.getMask()) == 0;
    }

    private int getHideFlags(ItemStack stack) {
        return stack.hasTag() && stack.getTag().contains("HideFlags", 99) ? stack.getTag().getInt("HideFlags") : 0;
    }
}
