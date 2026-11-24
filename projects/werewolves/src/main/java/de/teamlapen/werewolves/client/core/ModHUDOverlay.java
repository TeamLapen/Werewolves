package de.teamlapen.werewolves.client.core;

import de.teamlapen.faction.common.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.api.items.ISilverItem;
import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.core.ModActions;
import de.teamlapen.werewolves.common.core.ModSkills;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ModHUDOverlay {

    private final Identifier ICONS = WIdentifier.mod("textures/gui/hud.png");
    protected static final Identifier WIDGETS_TEX_PATH = WIdentifier.mc("textures/gui/widgets.png");

    private int screenColor = 0;
    private int screenPercentage = 0;

    private final List<Integer> entities = new ArrayList<>();
    private int attackTargetScreenPercentage = 0;
    private int waitTicks = 0;

    private Minecraft mc() {
        return Minecraft.getInstance();
    }

    @SubscribeEvent
    public void onClientTick(ClientTickEvent.Pre event) {
        if (mc() == null || mc().player == null || !mc().player.isAlive()) {
            this.screenPercentage = 0;
            this.attackTargetScreenPercentage = 0;
            this.entities.clear();
            this.waitTicks = 0;
            return;
        }

        FactionPlayerHandler.getCurrentFactionPlayer(mc().player).filter(WerewolfPlayer.class::isInstance).ifPresentOrElse(player -> this.handleScreenColorWerewolf(((WerewolfPlayer) player)), () -> {
            this.screenPercentage = 0;
            this.attackTargetScreenPercentage = 0;
            this.waitTicks = 0;
            this.entities.clear();
        });
    }

    @SubscribeEvent
    public void onRenderGui(RenderGuiLayerEvent.Pre event) {
        if (mc().player == null || !mc().player.isAlive() || event.getName() != VanillaGuiLayers.CROSSHAIR) {
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

        if (percentages > 0 && ModConfig.client().renderScreenOverlay.get()) {
            GuiGraphicsExtractor graphics = event.getGuiGraphics();
            int w = this.mc().getWindow().getGuiScaledWidth();
            int h = this.mc().getWindow().getGuiScaledHeight();
            int bh = Math.round(h / 4f * percentages / 100);
            graphics.fillGradient(0, 0, w, bh, color, 0x00000000);
            graphics.fillGradient(0, h - bh, w, h, 0x00000000, color);
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
        boolean sixth_sense = player.getSkillHandler().isSkillEnabled(ModSkills.SIXTH_SENSE);
        boolean rage = player.getActionHandler().isActionActive(ModActions.RAGE);
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

    private void renderFangs(GuiGraphicsExtractor graphics, int width, int height, @Nullable LivingEntity entity) {
        int left = width / 2 - 9;
        int top = height / 2 - 6;
        boolean silver = false;
        if (entity != null) {
            for (EquipmentSlot value : EquipmentSlot.values()) {
                if (value.isArmor() && entity.getItemBySlot(value).getItem() instanceof ISilverItem) {
                    silver = true;
                    break;
                }
            }
        }
        graphics.blit(RenderPipelines.GUI_TEXTURED, ICONS, left, top, silver ? 30 : 15, 0, 15, 15, 256, 256);//other option is 18x18 - 307x307
    }

    private void renderCrosshair(RenderGuiLayerEvent.Pre event) {
        if (ModConfig.client().disableFangCrosshairRendering.get()) return;
        if (Helper.isWerewolf(this.mc().player)) {
            HitResult result = Minecraft.getInstance().hitResult;
            LivingEntity entity = result instanceof EntityHitResult entityResult ? entityResult.getEntity() instanceof LivingEntity living ? living : null : null;
            if (entity != null && WerewolfPlayer.get(mc().player).canBite()) {
                renderFangs(event.getGuiGraphics(), this.mc().getWindow().getGuiScaledWidth(), this.mc().getWindow().getGuiScaledHeight(), entity);
                event.setCanceled(true);
            }
        }
    }
}
