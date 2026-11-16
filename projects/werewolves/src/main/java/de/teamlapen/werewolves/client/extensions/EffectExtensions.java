package de.teamlapen.werewolves.client.extensions;

import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;
import org.jetbrains.annotations.NotNull;

public class EffectExtensions {

    public static final IClientMobEffectExtensions HIDDEN_EFFECT = new IClientMobEffectExtensions() {
        @Override
        public boolean isVisibleInInventory(MobEffectInstance instance) {
            return true;
        }

        @Override
        public boolean isVisibleInGui(@NotNull MobEffectInstance instance) {
            return true;
        }
    };
}
