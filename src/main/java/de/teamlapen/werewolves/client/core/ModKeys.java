package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.core.WerewolfActions;
import de.teamlapen.werewolves.network.InputEventPacket;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class ModKeys {
    private static final String CATEGORY = "keys.werewolves.category";
    private static final String LEAP_KEY = "keys.werewolves.leap";

    private static KeyBinding LEAP = new KeyBinding(LEAP_KEY, KeyConflictContext.IN_GAME, KeyModifier.CONTROL, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_SPACE, CATEGORY);

    public static void register() {
        MinecraftForge.EVENT_BUS.register(new ModKeys());
        ClientRegistry.registerKeyBinding(LEAP);
    }

    @SubscribeEvent
    public void handleInputEvent(InputEvent event) {
        KeyBinding key = getPressedKeyBinding();
        if (key != null) {
            if (key == LEAP) {
                if (Helper.isWerewolf(Minecraft.getInstance().player)) {
                    if (!WerewolfPlayer.get(Minecraft.getInstance().player).getActionHandler().isActionOnCooldown(WerewolfActions.leap)) {
                        WerewolvesMod.dispatcher.sendToServer(new InputEventPacket(InputEventPacket.LEAP, ""));
                        WerewolfPlayer.get(Minecraft.getInstance().player).getActionHandler().toggleAction(WerewolfActions.leap);
                    }
                }
            }
        }
    }

    private KeyBinding getPressedKeyBinding() {
        if (LEAP.isPressed()) {
            return LEAP;
        }
        return null;
    }
}
