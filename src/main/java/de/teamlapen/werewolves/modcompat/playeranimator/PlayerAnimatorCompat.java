package de.teamlapen.werewolves.modcompat.playeranimator;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.ModList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PlayerAnimatorCompat {

    private static boolean modChecked = false;
    private static boolean skipCheck;

    public static boolean checkAnimation(Player player) {
        if (!checkMod()) {
            return false;
        }
        if (Minecraft.getInstance().player != player) return false;
        return checkFirstPersonAnimation(player);
    }

    private static boolean checkMod() {
        if (!modChecked) {
            skipCheck = !ModList.get().isLoaded("playeranimator");
            modChecked = false;
        }
        return !skipCheck;
    }

    private static boolean checkFirstPersonAnimation(Player player) {
        try {
            if (!isFirstPersonPass()) return false;
            return isAnimationActive(player);
        } catch (Exception e) {
            return false;
        }
    }

    private static Method isFirstPersonPassMethod;

    private static boolean isFirstPersonPass() throws ReflectiveOperationException {
        if (isFirstPersonPassMethod == null) {
            isFirstPersonPassMethod = Class.forName("dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode").getMethod("isFirstPersonPass");
        }

        Object isFirstPersonPass = isFirstPersonPassMethod.invoke(null);
        return isFirstPersonPass != null && (boolean) isFirstPersonPass;
    }

    private static Class<?> iAnimatedPlayerClass;
    private static Method getAnimationApplierMethod;

    private static boolean isAnimationActive(Player player) throws ReflectiveOperationException {
        if (iAnimatedPlayerClass == null || getAnimationApplierMethod == null) {
            iAnimatedPlayerClass = Class.forName("dev.kosmx.playerAnim.impl.IAnimatedPlayer");
            getAnimationApplierMethod = iAnimatedPlayerClass.getMethod("playerAnimator_getAnimation");
        }
        if (!iAnimatedPlayerClass.isInstance(player)) return false;
        Object animationApplier = getAnimationApplierMethod.invoke(player);
        return (boolean) animationApplier.getClass().getMethod("isActive").invoke(animationApplier);
    }

}
