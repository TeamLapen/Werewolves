package de.teamlapen.werewolves.client.extensions;

import de.teamlapen.werewolves.common.core.ModItems;

import de.teamlapen.werewolves.common.core.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;
import org.jetbrains.annotations.NotNull;

public class EffectExtensions {

    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerMobEffect(HIDDEN_EFFECT, ModEffects.UN_WEREWOLF.get());
        event.registerItem(ItemExtensions.WOLF_PELT, ModItems.PELT_HELMET.get(), ModItems.DARK_PELT_HELMET.get(), ModItems.WHITE_PELT_HELMET.get());
    }

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
