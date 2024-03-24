package de.teamlapen.werewolves.client.gui.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.vampirism.api.entity.player.actions.IAction;
import de.teamlapen.vampirism.util.RegUtil;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.player.action.IActionCooldownMenu;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.gui.overlay.ExtendedGui;
import net.neoforged.neoforge.client.gui.overlay.IGuiOverlay;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ActionCooldownOverlay implements IGuiOverlay {

    private final Minecraft mc = Minecraft.getInstance();

    @Override
    public void render(@NotNull ExtendedGui gui, @NotNull GuiGraphics graphics, float partialTicks, int width, int height) {
        if (Helper.isWerewolf(this.mc.player)) {
            WerewolfPlayer werewolf = WerewolfPlayer.get(this.mc.player);
            List<IAction<IWerewolfPlayer>> actions = werewolf.getActionHandler().getUnlockedActions().stream().filter(IActionCooldownMenu.class::isInstance).filter(action -> werewolf.getActionHandler().isActionOnCooldown(action)).toList();


            int x = 12;
            int y = this.mc.getWindow().getGuiScaledHeight() - 27;
            for (IAction<IWerewolfPlayer> action : actions) {
                ResourceLocation id = RegUtil.id(action);
                ResourceLocation loc = new ResourceLocation(id.getNamespace(), "textures/actions/" + id.getPath() + ".png");
                int perc = (int) ((1 + werewolf.getActionHandler().getPercentageForAction(action)) * 16);
                //render gray transparent background for remaining cooldown
                graphics.fillGradient(x, y + perc, x + 16, y + 16, 0x44888888/*Color.GRAY - 0xBB000000 */, 0x44888888/*Color.GRAY - 0xBB000000 */);
                //render action icon transparent
                graphics.setColor(1, 1, 1, 0.4f);
                graphics.blit(loc, x, y, 0, 0, 16, 16, 16, 16);
                //render action icon full for remaining cooldown
                graphics.setColor(1, 1, 1, 1);
                graphics.blit(loc, x, y + perc, 0, perc, 16, 16 - perc, 16, 16);
                x += 16;
            }
        }
    }
}
