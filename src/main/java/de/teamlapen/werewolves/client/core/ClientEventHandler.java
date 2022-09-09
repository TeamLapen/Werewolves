package de.teamlapen.werewolves.client.core;

import de.teamlapen.vampirism.api.entity.player.actions.IActionHandler;
import de.teamlapen.vampirism.client.gui.VampirismScreen;
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
import de.teamlapen.werewolves.util.WeaponOilHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.commons.lang3.mutable.MutableInt;


@OnlyIn(Dist.CLIENT)
public class ClientEventHandler {
    private int zoomTime = 0;
    private double zoomAmount = 0;
    private double zoomModifier = 0;


    @SubscribeEvent
    public void onRenderPlayer(RenderPlayerEvent.Pre event) {
        AbstractClientPlayer player = (AbstractClientPlayer) event.getPlayer();
        if (shouldRenderWerewolfForm(player)) {
            event.setCanceled(ModEntityRenderer.render.render(WerewolfPlayer.get(player), Mth.lerp(event.getPartialTick(), player.yRotO, player.getYRot()), event.getPartialTick(), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight()));
        }
    }

    @SubscribeEvent
    public void onRenderPlayerPost(RenderPlayerEvent.Post event) {
        AbstractClientPlayer player = (AbstractClientPlayer) event.getPlayer();
        if (shouldRenderWerewolfForm(player)) {
            ModEntityRenderer.render.renderPost(event.getRenderer().getModel(), WerewolfPlayer.get(player), Mth.lerp(event.getPartialTick(), player.yRotO, player.getYRot()), event.getPartialTick(), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight());
        }
    }

    private boolean shouldRenderWerewolfForm(AbstractClientPlayer player) {
        return Helper.isWerewolf(player) && (WerewolfPlayer.getOpt(player).map(w -> w.getForm().isTransformed()).orElse(false) || (Minecraft.getInstance().screen instanceof WerewolfPlayerAppearanceScreen && ((WerewolfPlayerAppearanceScreen) Minecraft.getInstance().screen).isRenderForm()));
    }

    @SubscribeEvent
    public void onFOVModifier(EntityViewRenderEvent.FieldOfView event) {
        if (this.zoomTime > 0) {
            event.setFOV(event.getFOV() - this.zoomModifier);
            this.zoomModifier -= this.zoomAmount;
            --this.zoomTime;
        }
    }


    @SubscribeEvent
    public void onGuiInitPost(ScreenEvent.InitScreenEvent.Post event) {
        boolean vampirismScreen = event.getScreen() instanceof VampirismScreen;
        boolean stoneAltar = event.getScreen() instanceof StoneAltarScreen; //TODO move into stone altar screen
        if (vampirismScreen || stoneAltar) {
            if (Helper.isWerewolf(Minecraft.getInstance().player)) {
                if (vampirismScreen) {
                    ResourceLocation icon = new ResourceLocation(REFERENCE.MODID, "textures/gui/appearance_button.png");
                    ((ScreenAccessor) event.getScreen()).invokeAddRenderableWidget_werewolves(new ImageButton(((VampirismScreen) event.getScreen()).getGuiLeft() + 47, ((VampirismScreen) event.getScreen()).getGuiTop() + 90, 20, 20, 0, 0, 20, icon, 20, 40, (context) -> {
                        Minecraft.getInstance().setScreen(new WerewolfPlayerAppearanceScreen(event.getScreen()));
                    }, (button1, matrixStack, mouseX, mouseY) -> {
                        event.getScreen().renderTooltip(matrixStack, new TranslatableComponent("gui.vampirism.vampirism_menu.appearance_menu"), mouseX, mouseY);
                    }, TextComponent.EMPTY));
                }

                WerewolfPlayer.getOpt(Minecraft.getInstance().player).ifPresent(werewolf -> {
                    if (werewolf.getMaxLevel() == werewolf.getLevel()) return;
                    ContainerScreen screen = ((ContainerScreen) event.getScreen());
                    ((ScreenAccessor) event.getScreen()).invokeAddRenderableWidget_werewolves(new ExpBar(screen.getGuiLeft() - 14, screen.getGuiTop(), screen));
                });
            }
        }
    }

    @SubscribeEvent
    public void onRenderNamePlate(RenderNameplateEvent event) {
        if (event.getEntity() instanceof Player) {
            if (Helper.isWerewolf((Player) event.getEntity())) {
                WerewolfPlayer werewolf = WerewolfPlayer.get(((Player) event.getEntity()));
                IActionHandler<IWerewolfPlayer> d = werewolf.getActionHandler();
                if (d.isActionActive(ModActions.hide_name) && FormHelper.isFormActionActive(werewolf)) {
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }

    @SubscribeEvent
    public void onItemToolTip(ItemTooltipEvent event) {
        MutableInt position = new MutableInt(1);
        int flags = getHideFlags(event.getItemStack());
        if (shouldShowInTooltip(flags, ItemStack.TooltipPart.ADDITIONAL)) position.increment();
        if (event.getItemStack().hasTag()) {
            if (shouldShowInTooltip(flags, ItemStack.TooltipPart.ENCHANTMENTS)) {
                position.add(event.getItemStack().getEnchantmentTags().size());
            }
            WeaponOilHelper.oilOpt(event.getItemStack()).ifPresent((oil) -> {
                event.getToolTip().add(position.getAndIncrement() - 1, new TranslatableComponent("oil." + oil.getLeft().getRegistryName().getNamespace() + "." + oil.getLeft().getRegistryName().getPath() + ".desc").withStyle(ChatFormatting.GOLD));
            });
        }
    }

    @SubscribeEvent
    public void onRenderArm(RenderArmEvent event) {
        if (Helper.isWerewolf(event.getPlayer()) && WerewolfPlayer.get(event.getPlayer()).getForm().isTransformed()) {
            if (switch (event.getArm()) {
                case RIGHT -> ModEntityRenderer.render.renderRightArm(event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), event.getPlayer());
                case LEFT -> ModEntityRenderer.render.renderLeftArm(event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), event.getPlayer());
            }) {
                event.setCanceled(true);
            }
        }
    }

    public void onZoomPressed() {
        this.zoomTime = 20;
        this.zoomAmount = Minecraft.getInstance().options.fov / 4 / this.zoomTime;
        this.zoomModifier = Minecraft.getInstance().options.fov - Minecraft.getInstance().options.fov / 4;
    }

    private static boolean shouldShowInTooltip(int p_242394_0_, ItemStack.TooltipPart p_242394_1_) {
        return (p_242394_0_ & p_242394_1_.getMask()) == 0;
    }

    private int getHideFlags(ItemStack stack) {
        return stack.hasTag() && stack.getTag().contains("HideFlags", 99) ? stack.getTag().getInt("HideFlags") : 0;
    }
}
