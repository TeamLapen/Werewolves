package de.teamlapen.werewolves.client.core;

import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.api.VReference;
import de.teamlapen.werewolves.network.InputEventPacket;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.player.werewolf.actions.WerewolfActions;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Keyboard;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class ModKeys {

    private static final String CATEGORY = "keys.werewolves.category";
    private static final String BITE_EAT = "keys.werewolves.bite";

    private static KeyBinding BITE = new KeyBinding(BITE_EAT, KeyConflictContext.IN_GAME, Keyboard.KEY_V, CATEGORY);

    @Nonnull
    public static KeyBinding getKeyBinding(@Nonnull KEY key) {
        assert key != null;
        switch (key) {
            case BITE:
                return BITE;
            default:
                WerewolvesMod.log.e("ModKeys", "Keybinding %s does not exists", key);
                return BITE;
        }
    }

    public static void register() {
        MinecraftForge.EVENT_BUS.register(new ModKeys());
        ClientRegistry.registerKeyBinding(BITE);
    }

    @SubscribeEvent
    public void handleInputEvent(InputEvent event) {
        KEY keyPressed = this.getPressedKeyBinding();
        if (keyPressed == KEY.BITE) {
            RayTraceResult mouseOver = Minecraft.getMinecraft().objectMouseOver;
            EntityPlayer player = Minecraft.getMinecraft().player;
            if (mouseOver != null && !player.isSpectator() && FactionPlayerHandler.get(player).isInFaction(VReference.WEREWOLF_FACTION) && WerewolfPlayer.get(player).getActionHandler().isActionActive(WerewolfActions.werewolf_werewolf)) {
                if (mouseOver.entityHit != null) {
                    WerewolvesMod.dispatcher.sendToServer(new InputEventPacket(InputEventPacket.BITE, "" + mouseOver.entityHit.getEntityId()));
                }
            }
        }
    }

    private KEY getPressedKeyBinding() {
        if (BITE.isPressed())
            return KEY.BITE;
        return KEY.UNKNOWN;
    }

    public enum KEY {
        BITE, UNKNOWN;
    }
}
