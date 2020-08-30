package de.teamlapen.werewolves.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import de.teamlapen.lib.lib.util.BasicCommand;
import de.teamlapen.werewolves.entities.werewolf.WerewolfTransformable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import org.apache.logging.log4j.LogManager;

import java.util.List;

public class WerewolfTransformCommand extends BasicCommand {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("werewolf-transform")
                .requires(context -> context.hasPermissionLevel(PERMISSION_LEVEL_CHEAT))
                .then(Commands.literal("to-werewolf")
                        .executes(context -> transformToWerewolf(context.getSource().asPlayer())))
                .then(Commands.literal("from-werewolf")
                        .executes(context -> transformFromWerewolf(context.getSource().asPlayer())));
    }

    private static int transformToWerewolf(PlayerEntity player) {
        try {
            List<LivingEntity> entites = player.world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(player.getPosition()).grow(10), entity -> entity instanceof WerewolfTransformable);
            entites.forEach(entity -> ((WerewolfTransformable) entity).transformToWerewolf());
        } catch (Exception e) {
            LogManager.getLogger().error(e);
        }
        return 0;
    }

    private static int transformFromWerewolf(PlayerEntity player) {
        List<LivingEntity> entites = player.world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(player.getPosition()).grow(10), entity -> entity instanceof WerewolfTransformable);
        entites.forEach(entity -> ((WerewolfTransformable) entity).transformBack());
        return 0;
    }
}
