package de.teamlapen.werewolves.common.core;

import de.teamlapen.faction.api.FactionRegistries;
import de.teamlapen.faction.api.FactionTagKeys;
import de.teamlapen.faction.api.event.AddFactionTagEvent;
import de.teamlapen.faction.api.factions.IPlayableFaction;
import de.teamlapen.faction.api.factions.skills.ISkill;
import de.teamlapen.faction.api.factions.skills.ISkillHandler;
import de.teamlapen.faction.api.registries.factions.DeferredFaction;
import de.teamlapen.faction.api.registries.factions.DeferredFactionRegister;
import de.teamlapen.faction.api.world.entities.minion.IMinionEntry;
import de.teamlapen.faction.api.world.entities.minion.MinionAppearance;
import de.teamlapen.faction.api.world.entities.minion.MinionEntryProperties;
import de.teamlapen.faction.api.world.entities.player.IFactionPlayer;
import de.teamlapen.faction.api.world.items.RefinementItems;
import de.teamlapen.faction.common.factions.PlayableFaction;
import de.teamlapen.faction.common.factions.minions.MinionData;
import de.teamlapen.faction.common.util.Color;
import de.teamlapen.faction.common.world.entities.minion.MinionEntry;
import de.teamlapen.faction.common.world.entities.minion.MinionStats;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.WerewolvesFactions;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.common.tags.*;
import de.teamlapen.werewolves.common.util.WerewolfVillage;
import de.teamlapen.werewolves.util.WerewolfLordTitles;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.ApiStatus;

public class ModFactions {
    private static final DeferredFactionRegister FACTIONS = DeferredFactionRegister.create(WReference.MODID);
    private static final DeferredRegister<IMinionEntry<?>> MINIONS = DeferredRegister.create(FactionRegistries.Keys.MINION, WReference.MODID);

    public static final DeferredFaction<IWerewolfPlayer, IPlayableFaction<IWerewolfPlayer>> WEREWOLF = FACTIONS.registerFaction(WerewolvesFactions.Keys.WEREWOLF.getPath(), props ->
            new PlayableFaction<>(props.playerAttachment(ModAttachments.WEREWOLF_PLAYER)
                    .color(Color.MAGENTA_DARK.getRGB())
                    .chatColor(ChatFormatting.DARK_PURPLE)
                    .maxLevel(REFERENCE.HIGHEST_WEREWOLF_LEVEL)
                    .lord(REFERENCE.HIGHEST_WEREWOLF_LORD_LEVEL, new WerewolfLordTitles())
                    .refinements(new RefinementItems(ModItems.BONE_NECKLACE, ModItems.CHARM_BRACELET, ModItems.DREAM_CATCHER))
                    .badOmen(ModEffects.BAD_OMEN_WEREWOLF)
                    .villageGuards(ModEntityTypeTags.WEREWOLF_VILLAGE_GUARDS)
                    .taskMaster(ModEntities.TASK_MASTER_WEREWOLF)
                    .banner(WerewolfVillage.create())
                    .totem(ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF, ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF_CRAFTED)));

    public static final DeferredHolder<IMinionEntry<?>, IMinionEntry<IWerewolfPlayer>> WEREWOLF_MINION = MINIONS.register(WerewolvesFactions.Keys.WEREWOLF.getPath(), () -> new MinionEntry<>(new MinionEntryProperties<IWerewolfPlayer>()
            .withProvider((player, entry) -> createMinionData(player, entry, ModSkills.MINION_STATS_INCREASE))
            .withEntityType(ModEntities.WEREWOLF_MINION)
            .withFaction(ModFactions.WEREWOLF)
            .withMinionStats(MinionStats.HEALTH_STATS, MinionStats.INVENTORY_STATS, MinionStats.ATTACK_DAMAGE_STATS, MinionStats.SPEED_STATS)
            .withMinionAppearances(MinionAppearance.NAME_TYPE, MinionAppearance.SKIN_TYPE, MinionAppearance.MINION_SKIN, MinionAppearance.LORD_SKIN)
            .withMaxLevel(6)));

    private static MinionData createMinionData(IFactionPlayer<?> player, IMinionEntry<?> entry, Holder<? extends ISkill<?>> statsIncreaseSkill) {
        MinionData data = new MinionData(entry);
        data.setIncreasedStats(ISkillHandler.isSkillEnabled(player.asEntity(), statsIncreaseSkill));
        return data;
    }

    static void register(IEventBus bus) {
        FACTIONS.register(bus);
        MINIONS.register(bus);
    }

    @ApiStatus.Internal
    public static void registerFactionTags(AddFactionTagEvent event) {
        event.faction(WEREWOLF)
                .add(Registries.BIOME, ModBiomeTags.IS_WEREWOLF_BIOME)
                .add(Registries.POINT_OF_INTEREST_TYPE, ModPoiTypeTags.IS_WEREWOLF)
                .add(Registries.VILLAGER_PROFESSION, ModProfessionTags.IS_WEREWOLF)
                .add(Registries.ENTITY_TYPE, ModEntityTypeTags.WEREWOLF)
                .defaultTargets(ModEntityTypeTags.WEREWOLF_DEFAULT_TARGETS)
                .add(FactionRegistries.Keys.TASK, ModTaskTags.IS_WEREWOLF)
                .add(FactionRegistries.Keys.FACTION, ModFactionsTags.IS_WEREWOLF)
                .add(FactionRegistries.Keys.REFINEMENT_SET, ModRefinementSetTags.IS_WEREWOLF)
                .add(Registries.DATA_COMPONENT_TYPE, ModDataComponentTags.WEREWOLF_FOOD)
                .addCustom(FactionTagKeys.ACTION_DISABLES, ModEffectTags.DISABLES_ACTIONS_WEREWOLF)
        ;
    }
}
