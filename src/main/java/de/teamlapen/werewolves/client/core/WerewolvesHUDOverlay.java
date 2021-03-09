package de.teamlapen.werewolves.client.core;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import de.teamlapen.lib.lib.client.gui.ExtendedGui;
import de.teamlapen.vampirism.api.entity.player.IFactionPlayer;
import de.teamlapen.vampirism.api.entity.player.actions.IAction;
import de.teamlapen.vampirism.config.VampirismConfig;
import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.core.WerewolfActions;
import de.teamlapen.werewolves.core.WerewolfSkills;
import de.teamlapen.werewolves.items.ISilverItem;
import de.teamlapen.werewolves.mixin.client.InGameGuiAccessor;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.player.werewolf.actions.IActionCooldownMenu;
import de.teamlapen.werewolves.player.werewolf.actions.WerewolfFormAction;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class WerewolvesHUDOverlay extends ExtendedGui {

    private final Minecraft mc = Minecraft.getInstance();
    private final ResourceLocation ICONS = new ResourceLocation(REFERENCE.MODID, "textures/gui/hud.png");
    private final ResourceLocation FUR = new ResourceLocation(REFERENCE.MODID, "textures/gui/overlay/werewolf_fur_border.png");
    protected static final ResourceLocation WIDGETS_TEX_PATH = new ResourceLocation("textures/gui/widgets.png");

    private int screenColor = 0;
    private int screenPercentage = 0;

    private final List<Integer> entities = new ArrayList<>();
    private int attackTargetScreenPercentage = 0;
    private int waitTicks = 0;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (mc.player == null || !mc.player.isAlive()) {
            this.screenPercentage = 0;
            this.attackTargetScreenPercentage = 0;
            this.entities.clear();
            this.waitTicks = 0;
            return;
        }
        if (event.phase == TickEvent.Phase.END)
            return;

        @Nullable
        IFactionPlayer<?> player = FactionPlayerHandler.get(mc.player).getCurrentFactionPlayer().orElse(null);
        if (player instanceof WerewolfPlayer) {
            this.handleScreenColorWerewolf(((WerewolfPlayer) player));
        } else {
            this.screenPercentage = 0;
            this.attackTargetScreenPercentage = 0;
            this.waitTicks = 0;
            this.entities.clear();
        }
    }

    @SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Pre event) {
        if (mc.player == null || !mc.player.isAlive()) {
            return;
        }
        switch (event.getType()) {
            case CROSSHAIRS:
                this.renderCrosshair(event);
                break;
            case ALL:
                this.renderFur(event.getMatrixStack());
                break;
        }
    }

    @SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Post event) {
        if (mc.player == null || !mc.player.isAlive()) {
            return;
        }
        switch (event.getType()) {
            case EXPERIENCE:
                this.renderExperienceBar(event);
                break;
            case ALL:
                this.renderActionCooldown(event.getMatrixStack());
                break;
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onRenderWorldLast(RenderWorldLastEvent event) {
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
            RenderSystem.clear(GL11.GL_DEPTH_BUFFER_BIT, Minecraft.IS_RUNNING_ON_MAC);
            MatrixStack stack = new MatrixStack();
            stack.push();
            RenderSystem.matrixMode(GL11.GL_PROJECTION);
            RenderSystem.loadIdentity();
            RenderSystem.ortho(0.0D, this.mc.getMainWindow().getScaledWidth(), this.mc.getMainWindow().getScaledHeight(), 0.0D, 1D, -1D);
            RenderSystem.matrixMode(GL11.GL_MODELVIEW);
            RenderSystem.loadIdentity();
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int w = (this.mc.getMainWindow().getScaledWidth());
            int h = (this.mc.getMainWindow().getScaledHeight());

            int bh = Math.round(h / (float) 4 * percentages / 100);
            int bw = Math.round(w / (float) 8 * percentages / 100);

            this.fillGradient(stack, 0, 0, w, bh, color, 0x000);
            this.fillGradient(stack, 0, h - bh, w, h, 0x00000000, color);
            this.fillGradient2(stack, 0, 0, bw, h, 0x000000, color);
            this.fillGradient2(stack, w - bw, 0, w, h, color, 0x00);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            stack.pop();
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
        boolean sixth_sense = player.getSkillHandler().isSkillEnabled(WerewolfSkills.sixth_sense);
        boolean rage = player.getActionHandler().isActionActive(WerewolfActions.rage);
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

    private void renderFangs(MatrixStack matrixStack, int width, int height, @Nullable Entity entity) {
        this.mc.getTextureManager().bindTexture(ICONS);
        int left = width / 2 - 9;
        int top = height / 2 - 6;
        boolean silver = false;
        if (entity != null) {
            for (ItemStack stack : entity.getArmorInventoryList()) {
                if (stack.getItem() instanceof ISilverItem) {
                    silver = true;
                    break;
                }
            }
        }
        GL11.glEnable(GL11.GL_BLEND);
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        blit(matrixStack, left, top, this.getBlitOffset(), silver ? 30 : 15, 0, 15, 15, 256, 256);//other option is 18x18 - 307x307
        GL11.glDisable(GL11.GL_BLEND);
    }

    private void renderFur(MatrixStack matrixStack) {
        if (this.mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON && Helper.isWerewolf(this.mc.player) && WerewolfPlayer.getOpt(this.mc.player).map(Helper::isFormActionActive).orElse(false)) {
            this.mc.getTextureManager().bindTexture(FUR);
            RenderSystem.enableBlend();
            blit(matrixStack, 0, 0, this.getBlitOffset(), 0, 0, this.mc.getMainWindow().getWidth(), this.mc.getMainWindow().getHeight(), this.mc.getMainWindow().getScaledHeight(), this.mc.getMainWindow().getScaledWidth());
            RenderSystem.disableBlend();
        }
    }

    private void renderCrosshair(RenderGameOverlayEvent.Pre event) {
        if (Helper.isWerewolf(this.mc.player)) {
            RayTraceResult p = Minecraft.getInstance().objectMouseOver;
            Entity entity = p instanceof EntityRayTraceResult?((EntityRayTraceResult) p).getEntity():null;
            if (WerewolfPlayer.get(mc.player).canBite()) {
                renderFangs(event.getMatrixStack(), this.mc.getMainWindow().getScaledWidth(), this.mc.getMainWindow().getScaledHeight(), entity);
                event.setCanceled(true);
            }
        }
    }

    private void renderExperienceBar(RenderGameOverlayEvent.Post event) {
        PlayerEntity player = mc.player;
        if(Helper.isWerewolf(player)) {
            WerewolfPlayer werewolf = WerewolfPlayer.get(player);
            if (werewolf.getSpecialAttributes().werewolfTime > 0) {
                float perc = WerewolfFormAction.getDurationPercentage(werewolf);
                float trans = Helper.isFormActionActive(werewolf)?1f:0.7f;
                if (!Helper.isNight(player.getEntityWorld())) {
                    renderExpBar(event.getMatrixStack(), perc, trans);
                }
            }
        }
    }

    private void renderExpBar(MatrixStack matrixStack, float perc, float transparency) {
        int scaledWidth = ((InGameGuiAccessor) Minecraft.getInstance().ingameGUI).getScaledWidth();
        int scaledHeight = ((InGameGuiAccessor) Minecraft.getInstance().ingameGUI).getScaledHeight();
        int x = scaledWidth / 2 - 91;
        this.mc.getProfiler().startSection("werewolfActionDurationBar");
        this.mc.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);

        RenderSystem.enableBlend();
        RenderSystem.color4f(1f, 0.1f, 0f, transparency);

        int k = (int) ((1 - perc) * 183.0F);
        int l = scaledHeight - 32 + 3;
        this.blit(matrixStack, x, l, 0, 64, 182, 5);
        this.blit(matrixStack, x + k, l, k, 69, 182 - k, 5);
        this.mc.getProfiler().endSection();
    }

    private void renderActionCooldown(MatrixStack matrixStack) {
        if (Helper.isWerewolf(this.mc.player)) {
            WerewolfPlayer werewolf = WerewolfPlayer.get(this.mc.player);
            List<IAction> actions = new ArrayList<>();
            actions.addAll(werewolf.getActionHandler().getUnlockedActions());
            actions.removeIf(action -> !(action instanceof IActionCooldownMenu));
            actions.removeIf(action -> !(werewolf.getActionHandler().isActionOnCooldown(action)));


            int x = 12;
            int y = this.mc.getMainWindow().getScaledHeight() - 27;
            for (IAction action : actions) {
                ResourceLocation loc = new ResourceLocation(action.getRegistryName().getNamespace(), "textures/actions/" + action.getRegistryName().getPath() + ".png");
                this.mc.getTextureManager().bindTexture(loc);
                RenderSystem.color4f(1, 1, 1, 0.5f);
                blit(matrixStack, x, y, this.getBlitOffset(), 0, 0, 16, 16, 16, 16);
                float perc1 = 1 - -werewolf.getActionHandler().getPercentageForAction(action);
                int perc = (int) (perc1 * 16);
                RenderSystem.color4f(1, 1, 1, 1);
                blit(matrixStack, x, y + perc, this.getBlitOffset(), 0, 0 + perc, 16, 16 - perc, 16, 16);
                x += 16;
            }
        }
    }

}
