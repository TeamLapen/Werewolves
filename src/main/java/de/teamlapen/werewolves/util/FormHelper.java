package de.teamlapen.werewolves.util;

import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModBiomes;
import de.teamlapen.werewolves.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.entities.player.werewolf.actions.WerewolfFormAction;
import de.teamlapen.werewolves.entities.werewolf.IWerewolfDataholder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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

    public static boolean isInWerewolfBiome(LevelAccessor world, BlockPos pos) {
        ResourceLocation loc = world.getBiome(pos).getRegistryName();
        return noWerewolfFormTickingBiomes.contains(loc);
    }

    public static WerewolfForm getForm(LivingEntity entity) {
        if (entity instanceof Player) {
            return WerewolfPlayer.getOpt(((Player) entity)).map(WerewolfPlayer::getForm).orElse(WerewolfForm.NONE);
        } else if (entity instanceof IWerewolfDataholder) {
            return ((IWerewolfDataholder) entity).getForm();
        }
        return WerewolfForm.NONE;
    }

    public static boolean isFormActionActive(IWerewolfPlayer player) {
        return WerewolfFormAction.isWerewolfFormActionActive(player.getActionHandler());
    }

    public static Optional<WerewolfFormAction> getActiveFormAction(IWerewolfPlayer werewolf) {
        // werewolf form actions can not be enabled at the same time
        return WerewolfFormAction.getAllAction().stream().filter(action -> werewolf.getActionHandler().isActionActive(action)).findAny();
    }

    public static void deactivateWerewolfActions(IWerewolfPlayer player) {
        WerewolfFormAction.getAllAction().stream().filter(action -> player.getActionHandler().isActionActive(action)).forEach(action -> player.getActionHandler().toggleAction(action));
    }
}
