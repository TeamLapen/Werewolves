package de.teamlapen.werewolves.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import de.teamlapen.lib.lib.util.BasicCommand;
import de.teamlapen.werewolves.api.entities.werewolf.TransformType;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfTransformable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.apache.logging.log4j.LogManager;

import java.util.List;

public class WerewolfTransformCommand extends BasicCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("werewolf-transform")
                .requires(context -> context.hasPermission(PERMISSION_LEVEL_CHEAT))
                .then(Commands.literal("to-werewolf")
                        .executes(context -> transformToWerewolf(context.getSource().getPlayerOrException())))
                .then(Commands.literal("from-werewolf")
                        .executes(context -> transformFromWerewolf(context.getSource().getPlayerOrException())));
    }

    @SuppressWarnings("SameReturnValue")
    private static int transformToWerewolf(Player player) {
        try {
            List<LivingEntity> entites = player.level().getEntitiesOfClass(LivingEntity.class, new AABB(player.blockPosition()).inflate(10), entity -> entity instanceof WerewolfTransformable);
            entites.forEach(entity -> ((WerewolfTransformable) entity).transformToWerewolf(TransformType.TIME_LIMITED));
        } catch (Exception e) {
            LogManager.getLogger().error(e);
        }
        return 0;
    }

    @SuppressWarnings("SameReturnValue")
    private static int transformFromWerewolf(Player player) {
        List<LivingEntity> entites = player.level().getEntitiesOfClass(LivingEntity.class, new AABB(player.blockPosition()).inflate(10), entity -> entity instanceof WerewolfTransformable);
        entites.forEach(entity -> ((WerewolfTransformable) entity).transformBack());
        return 0;
    }
}
