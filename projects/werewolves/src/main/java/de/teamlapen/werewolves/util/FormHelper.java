package de.teamlapen.werewolves.util;

import de.teamlapen.faction.common.factions.actions.ActionHandler;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.werewolf.IWerewolfDataholder;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.core.ModBiomes;
import de.teamlapen.werewolves.common.tags.ModBiomeTags;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.actions.WerewolfFormAction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class FormHelper {

    private static final Set<Identifier> noWerewolfFormTickingBiomes = new HashSet<>();

    public static void reload() {
        noWerewolfFormTickingBiomes.clear();
        List<? extends String> biomes = ModConfig.server().werewolfFormFreeFormBiomes.get();
        for (String s : biomes) {
            Identifier id = Identifier.parse(s);
            noWerewolfFormTickingBiomes.add(id);
        }
        noWerewolfFormTickingBiomes.add(ModBiomes.WEREWOLF_FOREST.identifier());
    }

    public static boolean isInWerewolfBiome(LevelAccessor world, BlockPos pos) {
        Holder<Biome> biome = world.getBiome(pos);
        return biome.is(ModBiomeTags.IS_WEREWOLF_BIOME) || world.getBiome(pos).unwrapKey().map(ResourceKey::identifier).map(noWerewolfFormTickingBiomes::contains).orElse(false);
    }

    public static WerewolfForm getForm(LivingEntity entity) {
        if (entity instanceof Player player) {
            return WerewolfPlayer.get(player).getForm();
        } else if (entity instanceof IWerewolfDataholder holder) {
            return holder.getForm();
        }
        return WerewolfForm.NONE;
    }

    public static boolean isFormActionActive(IWerewolfPlayer player) {
        return WerewolfFormAction.isWerewolfFormActionActive(player.getActionHandler());
    }

    public static Optional<Holder<WerewolfFormAction>> getActiveFormAction(IWerewolfPlayer werewolf) {
        // werewolf form actions can not be enabled at the same time
        //noinspection unchecked
        return WerewolfFormAction.getAllAction().stream().filter(action -> werewolf.getActionHandler().isActionActive(action)).findAny().map(action -> (Holder<WerewolfFormAction>) (Object) action);
    }

    public static void deactivateWerewolfActions(IWerewolfPlayer player) {
        WerewolfFormAction.getAllAction().stream().filter(action -> player.getActionHandler().isActionActive(action)).forEach(action -> player.getActionHandler().toggleAction(action, new ActionHandler.ActivationContext()));
    }
}
