package de.teamlapen.werewolves.client.render.util;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.werewolves.mixin.client.ScreenAccessor;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.client.EffectRenderer;

public class HiddenDurationEffectRenderer extends EffectRenderer {
    @Override
    public void renderInventoryEffect(MobEffectInstance effectInstance, EffectRenderingInventoryScreen<?> gui, PoseStack poseStack, int x, int y, float z) {
        String s = UtilLib.translate(effectInstance.getEffect().getDescriptionId());
        ((ScreenAccessor) gui).getFont().drawShadow(poseStack, s, (float) (x + 10 + 18), (float) (y + 6), 16777215);
        String duration = "**:**";
        ((ScreenAccessor) gui).getFont().drawShadow(poseStack, duration, (float) (x + 10 + 18), (float) (y + 6 + 10), 8355711);
    }

    @Override
    public void renderHUDEffect(MobEffectInstance effectInstance, GuiComponent gui, PoseStack poseStack, int x, int y, float z, float alpha) {

    }

    @Override
    public boolean shouldRenderInvText(MobEffectInstance effect) {
        return false;
    }
}
