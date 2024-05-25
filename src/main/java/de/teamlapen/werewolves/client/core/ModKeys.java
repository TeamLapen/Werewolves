package de.teamlapen.werewolves.client.core;

import com.mojang.blaze3d.platform.InputConstants;
import de.teamlapen.vampirism.entity.player.actions.ActionHandler;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.core.ModActions;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.network.ServerboundBiteEventPackage;
import de.teamlapen.werewolves.network.ServerboundSimpleInputEventPacket;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.ApiStatus;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nonnull;
import java.util.Optional;

public class ModKeys {
    private static final String CATEGORY = "keys.werewolves.category";
    private static final String LEAP_KEY = "keys.werewolves.leap";
    private static final String BITE_KEY = "keys.werewolves.bite";

    private static final KeyMapping LEAP = new KeyMapping(LEAP_KEY, KeyConflictContext.IN_GAME, KeyModifier.NONE, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_CONTROL, CATEGORY);
    private static final KeyMapping BITE = new KeyMapping(BITE_KEY, KeyConflictContext.IN_GAME, KeyModifier.NONE, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, CATEGORY);

    @ApiStatus.Internal
    public static void registerKeyMapping(@Nonnull RegisterKeyMappingsEvent event){
        event.register(LEAP);
        event.register(BITE);
    }

    private final ClientEventHandler clientEventHandler;

    public ModKeys(ClientEventHandler clientEventHandler) {
        this.clientEventHandler = clientEventHandler;
    }

    @SubscribeEvent
    public void handleMouseButton(InputEvent.MouseButton.Pre event) {
        handleInputEvent(event);
    }

    @SubscribeEvent
    public void handleKey(InputEvent.Key event) {
        handleInputEvent(event);
    }

    public void handleInputEvent(InputEvent event) {
        Optional<KeyMapping> keyOpt = getPressedKeyBinding();
        keyOpt.ifPresent(key -> {
            LocalPlayer player = Minecraft.getInstance().player;
            if (key == LEAP && Minecraft.getInstance().options.keyJump.isDown()) {
                if (Helper.isWerewolf(player)) {
                    WerewolfPlayer werewolf = WerewolfPlayer.get(player);
                    if (!werewolf.getActionHandler().isActionOnCooldown(ModActions.LEAP.get()) && werewolf.getForm().isTransformed()) {
                        player.connection.send(new ServerboundSimpleInputEventPacket(ServerboundSimpleInputEventPacket.Type.LEAP));
                        werewolf.getActionHandler().toggleAction(ModActions.LEAP.get(), new ActionHandler.ActivationContext());
                    }
                }
            } else if (key == BITE) {
                if (Helper.isWerewolf(player)) {
                    WerewolfPlayer werewolf = WerewolfPlayer.get(player);
                    HitResult mouseOver = Minecraft.getInstance().hitResult;
                    Entity entity = mouseOver instanceof EntityHitResult ? ((EntityHitResult) mouseOver).getEntity() : null;
                    if (entity instanceof LivingEntity && werewolf.canBite() && werewolf.canBiteEntity(((LivingEntity) entity))) {
                        player.connection.send(new ServerboundBiteEventPackage(((EntityHitResult) mouseOver).getEntity().getId()));
                        clientEventHandler.onZoomPressed();
                    }
                }
            }
        });
    }

    public Optional<KeyMapping> getPressedKeyBinding() {
        if (BITE.consumeClick()) {
            return Optional.of(BITE);
        } else if (LEAP.isDown()) {
            return Optional.of(LEAP);
        }
        return Optional.empty();
    }
}
