package de.teamlapen.werewolves.client.gui.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.entities.player.werewolf.actions.WerewolfFormAction;
import de.teamlapen.werewolves.mixin.client.InGameGuiAccessor;
import de.teamlapen.werewolves.util.FormHelper;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

public class WerewolfFormDurationOverlay extends GuiComponent implements IIngameOverlay {

    private final Minecraft mc = Minecraft.getInstance();

    @Override
    public void render(ForgeIngameGui gui, PoseStack mStack, float partialTicks, int width, int height) {
        Player player = this.mc.player;
        if (Helper.isWerewolf(player)) {
            WerewolfPlayer werewolf = WerewolfPlayer.get(player);
            if (werewolf.getSpecialAttributes().transformationTime > 0) {
                double perc = 1 - werewolf.getSpecialAttributes().transformationTime;
                float trans = FormHelper.getActiveFormAction(werewolf).map(WerewolfFormAction::consumesWerewolfTime).orElse(false) ? 1f : 0.7f;
                renderExpBar(mStack, perc, trans);
            }
        }
    }

    private void renderExpBar(PoseStack matrixStack, double perc, float transparency) {
        int scaledWidth = ((InGameGuiAccessor) Minecraft.getInstance().gui).getScaledWidth();
        int scaledHeight = ((InGameGuiAccessor) Minecraft.getInstance().gui).getScaledHeight();
        int x = scaledWidth / 2 - 91;
        this.mc.getProfiler().push("werewolfActionDurationBar");
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);

        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1f, 0.1f, 0f, transparency);

        int k = (int) ((1 - perc) * 183.0F);
        int l = scaledHeight - 32 + 3;
        this.blit(matrixStack, x, l, 0, 64, 182, 5);
        this.blit(matrixStack, x + k, l, k, 69, 182 - k, 5);
        this.mc.getProfiler().pop();
    }
}
