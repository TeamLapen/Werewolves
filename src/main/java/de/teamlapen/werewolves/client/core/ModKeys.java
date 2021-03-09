package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.core.WerewolfActions;
import de.teamlapen.werewolves.network.InputEventPacket;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

import java.util.Optional;

public class ModKeys {
    private static final String CATEGORY = "keys.werewolves.category";
    private static final String LEAP_KEY = "keys.werewolves.leap";

    private static final KeyBinding LEAP = new KeyBinding(LEAP_KEY, KeyConflictContext.IN_GAME, KeyModifier.CONTROL, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_SPACE, CATEGORY);
    private static final KeyBinding BITE = de.teamlapen.vampirism.client.core.ModKeys.getKeyBinding(de.teamlapen.vampirism.client.core.ModKeys.KEY.SUCK);

    public static void register(ClientEventHandler clientEventHandler) {
        MinecraftForge.EVENT_BUS.register(new ModKeys(clientEventHandler));
        ClientRegistry.registerKeyBinding(LEAP);
    }

    private final ClientEventHandler clientEventHandler;

    public ModKeys(ClientEventHandler clientEventHandler) {
        this.clientEventHandler = clientEventHandler;
    }

    @SubscribeEvent
    public void handleInputEvent(InputEvent event) {
        Optional<KeyBinding> keyOpt = getPressedKeyBinding();
        keyOpt.ifPresent(key -> {
            PlayerEntity player = Minecraft.getInstance().player;
            LazyOptional<WerewolfPlayer> werewolfOpt = WerewolfPlayer.getOpt(Minecraft.getInstance().player);
            if (key == LEAP) {
                if (Helper.isWerewolf(player)) {
                    werewolfOpt.filter(w -> !w.getActionHandler().isActionOnCooldown(WerewolfActions.leap)).ifPresent(w -> {
                        WerewolvesMod.dispatcher.sendToServer(new InputEventPacket(InputEventPacket.LEAP, ""));
                        WerewolfPlayer.get(player).getActionHandler().toggleAction(WerewolfActions.leap);
                    });
                }
            } else if (key == BITE) {
                if (Helper.isWerewolf(player)) {
                    RayTraceResult mouseOver = Minecraft.getInstance().objectMouseOver;
                    Entity entity = mouseOver instanceof EntityRayTraceResult? ((EntityRayTraceResult) mouseOver).getEntity():null;
                    if (entity instanceof LivingEntity && !player.isSpectator() && werewolfOpt.map(w -> w.getForm().isTransformed() && w.getLevel() > 0 && w.canBiteEntity(((LivingEntity) entity))).orElse(false)) {
                        WerewolvesMod.dispatcher.sendToServer(new InputEventPacket(InputEventPacket.BITE, "" + ((EntityRayTraceResult) mouseOver).getEntity().getEntityId()));
                        clientEventHandler.onZoomPressed();
                    }
                }
            }
        });
    }

    public Optional<KeyBinding> getPressedKeyBinding() {
        if (LEAP.isPressed()) {
            return Optional.of(LEAP);
        } else if (BITE.isPressed()) {
            return Optional.of(BITE);
        }
        return Optional.empty();
    }
}
