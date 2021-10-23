package de.teamlapen.werewolves.util;

import de.teamlapen.werewolves.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.werewolves.entities.player.werewolf.actions.WerewolfFormAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FormHelper {
    public static boolean isWerewolfFormTicking(World world, BlockPos pos) {
        return !Helper.isNight(world) && !Helper.isInWerewolfBiome(world, pos);
    }

    public static boolean isFormActionActive(IWerewolfPlayer player) {
        return WerewolfFormAction.isWerewolfFormActionActive(player.getActionHandler());
    }

    public static void deactivateWerewolfActions(IWerewolfPlayer player) {
        WerewolfFormAction.getAllAction().stream().filter(action -> player.getActionHandler().isActionActive(action)).forEach(action -> player.getActionHandler().toggleAction(action));
    }
}
