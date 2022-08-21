package de.teamlapen.werewolves.client.render.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;

public class HiddenDurationEffectRenderer implements IClientMobEffectExtensions {

    @Override
    public boolean renderInventoryText(MobEffectInstance instance, EffectRenderingInventoryScreen<?> screen, PoseStack poseStack, int x, int y, int blitOffset) {
        return true;
    }

    @Override
    public boolean isVisibleInGui(MobEffectInstance instance) {
        return false;
    }
}
