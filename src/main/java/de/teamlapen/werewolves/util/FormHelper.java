package de.teamlapen.werewolves.util;

import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModBiomes;
import de.teamlapen.werewolves.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.werewolves.entities.player.werewolf.actions.WerewolfFormAction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FormHelper {

    private static final Set<ResourceLocation> noWerewolfFormTickingBiomes = new HashSet<>();

    public static void reload() {
        noWerewolfFormTickingBiomes.clear();
        List<? extends String> biomes = WerewolvesConfig.SERVER.werewolfFormFreeFormBiomes.get();
        for (String s : biomes) {
            ResourceLocation id = new ResourceLocation(s);
            noWerewolfFormTickingBiomes.add(id);
        }
        noWerewolfFormTickingBiomes.add(ModBiomes.WEREWOLF_HEAVEN_KEY.location());
    }

    public static boolean isInWerewolfBiome(IWorld world, BlockPos pos) {
        ResourceLocation loc = world.getBiome(pos).getRegistryName();
        return noWerewolfFormTickingBiomes.contains(loc);
    }

    public static boolean isWerewolfFormTicking(World world, BlockPos pos) {
        return !Helper.isNight(world) && !isInWerewolfBiome(world, pos);
    }

    public static boolean isFormActionActive(IWerewolfPlayer player) {
        return WerewolfFormAction.isWerewolfFormActionActive(player.getActionHandler());
    }

    public static void deactivateWerewolfActions(IWerewolfPlayer player) {
        WerewolfFormAction.getAllAction().stream().filter(action -> player.getActionHandler().isActionActive(action)).forEach(action -> player.getActionHandler().toggleAction(action));
    }
}
