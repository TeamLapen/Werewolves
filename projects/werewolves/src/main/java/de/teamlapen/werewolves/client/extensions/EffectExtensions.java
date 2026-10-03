package de.teamlapen.werewolves.client.extensions;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;
import org.jetbrains.annotations.NotNull;

public class EffectExtensions {

    public static final IClientMobEffectExtensions HIDDEN_EFFECT = new IClientMobEffectExtensions() {
        @Override
        public boolean renderInventoryText(@NotNull MobEffectInstance instance, @NotNull EffectRenderingInventoryScreen<?> screen, @NotNull GuiGraphics graphics, int x, int y, int blitOffset) {
            return true;
        }

        @Override
        public boolean isVisibleInGui(@NotNull MobEffectInstance instance) {
            return false;
        }
    };
}
