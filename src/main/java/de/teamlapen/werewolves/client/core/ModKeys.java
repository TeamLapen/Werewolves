package de.teamlapen.werewolves.client.core;

import com.mojang.blaze3d.platform.InputConstants;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.core.ModActions;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.network.InputEventPacket;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

import java.util.Optional;

@SuppressWarnings("ClassCanBeRecord")
public class ModKeys {
    private static final String CATEGORY = "keys.werewolves.category";
    private static final String LEAP_KEY = "keys.werewolves.leap";
    private static final String BITE_KEY = "keys.werewolves.bite";

    private static final KeyMapping LEAP = new KeyMapping(LEAP_KEY, KeyConflictContext.IN_GAME, KeyModifier.CONTROL, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_SPACE, CATEGORY);
    private static final KeyMapping BITE = new KeyMapping(BITE_KEY, KeyConflictContext.IN_GAME, KeyModifier.NONE, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, CATEGORY);

    public static void register(ClientEventHandler clientEventHandler) {
        MinecraftForge.EVENT_BUS.register(new ModKeys(clientEventHandler));
        ClientRegistry.registerKeyBinding(LEAP);
        ClientRegistry.registerKeyBinding(BITE);
    }

    private final ClientEventHandler clientEventHandler;

    public ModKeys(ClientEventHandler clientEventHandler) {
        this.clientEventHandler = clientEventHandler;
    }

    @SubscribeEvent
    public void handleInputEvent(InputEvent event) {
        Optional<KeyMapping> keyOpt = getPressedKeyBinding();
        keyOpt.ifPresent(key -> {
            Player player = Minecraft.getInstance().player;
            LazyOptional<WerewolfPlayer> werewolfOpt = WerewolfPlayer.getOptEx(Minecraft.getInstance().player);
            if (key == LEAP) {
                werewolfOpt.filter(w -> !w.getActionHandler().isActionOnCooldown(ModActions.leap) && w.getForm().isTransformed()).ifPresent(w -> {
                    WerewolvesMod.dispatcher.sendToServer(new InputEventPacket(InputEventPacket.LEAP, ""));
                    WerewolfPlayer.get(player).getActionHandler().toggleAction(ModActions.leap);
                });
            } else if (key == BITE) {
                werewolfOpt.ifPresent(werewolf -> {
                    HitResult mouseOver = Minecraft.getInstance().hitResult;
                    Entity entity = mouseOver instanceof EntityHitResult ? ((EntityHitResult) mouseOver).getEntity() : null;
                    if (entity instanceof LivingEntity && werewolf.canBite() && werewolf.canBiteEntity(((LivingEntity) entity))) {
                        WerewolvesMod.dispatcher.sendToServer(new InputEventPacket(InputEventPacket.BITE, "" + ((EntityHitResult) mouseOver).getEntity().getId()));
                        clientEventHandler.onZoomPressed();
                    }
                });
            }
        });
    }

    public Optional<KeyMapping> getPressedKeyBinding() {
        if (LEAP.consumeClick()) {
            return Optional.of(LEAP);
        } else if (BITE.consumeClick()) {
            return Optional.of(BITE);
        }
        return Optional.empty();
    }
}
