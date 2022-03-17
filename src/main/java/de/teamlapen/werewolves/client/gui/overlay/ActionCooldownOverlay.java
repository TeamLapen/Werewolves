package de.teamlapen.werewolves.client.gui.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.vampirism.api.entity.player.actions.IAction;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.entities.player.werewolf.actions.IActionCooldownMenu;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

import java.util.ArrayList;

public class ActionCooldownOverlay extends GuiComponent implements IIngameOverlay {

    private final Minecraft mc = Minecraft.getInstance();

    @Override
    public void render(ForgeIngameGui gui, PoseStack mStack, float partialTicks, int width, int height) {
        if (Helper.isWerewolf(this.mc.player)) {
            WerewolfPlayer werewolf = WerewolfPlayer.get(this.mc.player);
            ArrayList<IAction> actions = new ArrayList<>();
            actions.addAll(werewolf.getActionHandler().getUnlockedActions());
            actions.removeIf(action -> !(action instanceof IActionCooldownMenu));
            actions.removeIf(action -> !(werewolf.getActionHandler().isActionOnCooldown(action)));


            int x = 12;
            int y = this.mc.getWindow().getGuiScaledHeight() - 27;
            for (IAction action : actions) {
                ResourceLocation loc = new ResourceLocation(action.getRegistryName().getNamespace(), "textures/actions/" + action.getRegistryName().getPath() + ".png");
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderTexture(0, loc);
                int perc = (int) ((1 - -werewolf.getActionHandler().getPercentageForAction(action)) * 16);
                //render gray transparent background for remaining cooldown
                this.fillGradient(mStack, x, y + perc, x + 16, y + 16, 0x44888888/*Color.GRAY - 0xBB000000 */, 0x44888888/*Color.GRAY - 0xBB000000 */);
                //render action icon transparent
                RenderSystem.enableBlend();
                RenderSystem.setShaderColor(1, 1, 1, 0.4f);
                blit(mStack, x, y, this.getBlitOffset(), 0, 0, 16, 16, 16, 16);
                //render action icon full for remaining cooldown
                RenderSystem.setShaderColor(1, 1, 1, 1);
                blit(mStack, x, y + perc, this.getBlitOffset(), 0, perc, 16, 16 - perc, 16, 16);
                x += 16;
            }
        }
    }
}
