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
                .requires(context -> context.hasPermission(PERMISSION_LEVEL_CHEAT))
                .then(Commands.literal("to-werewolf")
                        .executes(context -> transformToWerewolf(context.getSource().getPlayerOrException())))
                .then(Commands.literal("from-werewolf")
                        .executes(context -> transformFromWerewolf(context.getSource().getPlayerOrException())));
    }

    private static int transformToWerewolf(PlayerEntity player) {
        try {
            List<LivingEntity> entites = player.level.getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(player.blockPosition()).inflate(10), entity -> entity instanceof WerewolfTransformable);
            entites.forEach(entity -> ((WerewolfTransformable) entity).transformToWerewolf(WerewolfTransformable.TransformType.TIME_LIMITED));
        } catch (Exception e) {
            LogManager.getLogger().error(e);
        }
        return 0;
    }

    private static int transformFromWerewolf(PlayerEntity player) {
        List<LivingEntity> entites = player.level.getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(player.blockPosition()).inflate(10), entity -> entity instanceof WerewolfTransformable);
        entites.forEach(entity -> ((WerewolfTransformable) entity).transformBack());
        return 0;
    }
}
