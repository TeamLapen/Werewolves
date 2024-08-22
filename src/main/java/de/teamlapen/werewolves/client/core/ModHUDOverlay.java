package de.teamlapen.werewolves.client.core;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.lib.util.OptifineHandler;
import de.teamlapen.vampirism.api.entity.player.IFactionPlayer;
import de.teamlapen.vampirism.config.VampirismConfig;
import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.api.WResourceLocation;
import de.teamlapen.werewolves.api.items.ISilverItem;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModActions;
import de.teamlapen.werewolves.core.ModSkills;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ModHUDOverlay {

    private final Minecraft mc = Minecraft.getInstance();
    private final ResourceLocation ICONS = WResourceLocation.mod("textures/gui/hud.png");
    protected static final ResourceLocation WIDGETS_TEX_PATH = WResourceLocation.mc("textures/gui/widgets.png");

    private int screenColor = 0;
    private int screenPercentage = 0;

    private final List<Integer> entities = new ArrayList<>();
    private int attackTargetScreenPercentage = 0;
    private int waitTicks = 0;

    @SubscribeEvent
    public void onClientTick(ClientTickEvent.Pre event) {
        if (mc.player == null || !mc.player.isAlive()) {
            this.screenPercentage = 0;
            this.attackTargetScreenPercentage = 0;
            this.entities.clear();
            this.waitTicks = 0;
            return;
        }

        FactionPlayerHandler.getCurrentFactionPlayer(mc.player).filter(WerewolfPlayer.class::isInstance).ifPresentOrElse(player -> this.handleScreenColorWerewolf(((WerewolfPlayer) player)), () -> {
            this.screenPercentage = 0;
            this.attackTargetScreenPercentage = 0;
            this.waitTicks = 0;
            this.entities.clear();
        });
    }

    @SubscribeEvent
    public void onRenderGui(RenderGuiLayerEvent.Pre event) {
        if (mc.player == null || !mc.player.isAlive() || event.getName() != VanillaGuiLayers.CROSSHAIR) {
            return;
        }
        this.renderCrosshair(event);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderWorldLast(RenderGuiEvent.Pre event) {
        int percentages = 0;
        int color = 0;
        if (this.screenPercentage > 0) {
            percentages = this.screenPercentage;
            color = this.screenColor;
        } else if (this.attackTargetScreenPercentage > 0) {
            percentages = this.attackTargetScreenPercentage;
            color = 0xffff6e07;
        }

        if (percentages > 0 && VampirismConfig.CLIENT.renderScreenOverlay.get()) {
            PoseStack stack = event.getGuiGraphics().pose();
            stack.pushPose();
            int w = (this.mc.getWindow().getGuiScaledWidth());
            int h = (this.mc.getWindow().getGuiScaledHeight());

            int bh = Math.round(h / (float) 4 * percentages / 100);
            int bw = Math.round(w / (float) 8 * percentages / 100);

            event.getGuiGraphics().fillGradient(0, 0, w, bh, color, 0x000);
            if (!OptifineHandler.isShaders()) {
                event.getGuiGraphics().fillGradient(0, h - bh, w, h, 0x00000000, color);
            }
//            this.fillGradient2(stack, 0, 0, bw, h, 0x000000, color);
//            this.fillGradient2(stack, w - bw, 0, w, h, color, 0x00);

            stack.popPose();
        }
    }

    public void attackTriggered(int entityId) {
        if (!entities.contains(entityId)) {
            this.entities.add(entityId);
            if (this.waitTicks == 0) {
                this.screenPercentage = 100;
                this.waitTicks = 100;
                this.screenColor = 0xffff6e07;
            }
        }
    }

    private void handleScreenColorWerewolf(WerewolfPlayer player) {
        boolean sixth_sense = player.getSkillHandler().isSkillEnabled(ModSkills.SIXTH_SENSE.get());
        boolean rage = player.getActionHandler().isActionActive(ModActions.RAGE.get());
        if (sixth_sense) {
            if (this.screenPercentage > 0) {
                this.screenPercentage -= 10;
            }
            if (this.waitTicks > 0) {
                --this.waitTicks;
            }
        } else {
            this.waitTicks = 0;
            this.entities.clear();
        }

        if (rage) {
            this.screenPercentage = 100;
            this.screenColor = 0xfff00000;
        }

        if (!(sixth_sense || rage)) {
            this.screenPercentage = 0;
        }
    }

    private void renderFangs(GuiGraphics graphics, int width, int height, @Nullable LivingEntity entity) {
        int left = width / 2 - 9;
        int top = height / 2 - 6;
        boolean silver = false;
        if (entity != null) {
            for (ItemStack stack : entity.getArmorSlots()) {
                if (stack.getItem() instanceof ISilverItem) {
                    silver = true;
                    break;
                }
            }
        }
        graphics.setColor(1f, 1f, 1f, 1f);
        graphics.blit(ICONS, left, top, silver ? 30 : 15, 0, 15, 15, 256, 256);//other option is 18x18 - 307x307
    }

    private void renderCrosshair(RenderGuiLayerEvent.Pre event) {
        if (WerewolvesConfig.CLIENT.disableFangCrosshairRendering.get()) return;
        if (Helper.isWerewolf(this.mc.player)) {
            HitResult result = Minecraft.getInstance().hitResult;
            LivingEntity entity = result instanceof EntityHitResult entityResult ? entityResult.getEntity() instanceof LivingEntity living ? living : null : null;
            if (entity != null && WerewolfPlayer.get(mc.player).canBite()) {
                renderFangs(event.getGuiGraphics(), this.mc.getWindow().getGuiScaledWidth(), this.mc.getWindow().getGuiScaledHeight(), entity);
                event.setCanceled(true);
            }
        }
    }
}
