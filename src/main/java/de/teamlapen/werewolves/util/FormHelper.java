package de.teamlapen.werewolves.util;

import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.werewolf.IWerewolfDataholder;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModBiomes;
import de.teamlapen.werewolves.core.ModTags;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.entities.player.werewolf.actions.WerewolfFormAction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;

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
        noWerewolfFormTickingBiomes.add(ModBiomes.WEREWOLF_HEAVEN.getId());
    }

    public static boolean isInWerewolfBiome(LevelAccessor world, BlockPos pos) {
        Holder<Biome> biome = world.getBiome(pos);
        return biome.is(ModTags.Biomes.IS_WEREWOLF_BIOME) || noWerewolfFormTickingBiomes.contains(RegUtil.id(world.getBiome(pos).value()));
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
