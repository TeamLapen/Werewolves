package de.teamlapen.werewolves.util;

import com.google.common.collect.Lists;
import de.teamlapen.vampirism.api.entity.CaptureEntityEntry;
import de.teamlapen.vampirism.api.entity.factions.IFactionVillageBuilder;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModEntities;
import de.teamlapen.werewolves.core.ModVillage;
import de.teamlapen.werewolves.entities.werewolf.WerewolfBaseEntity;

public class WerewolfVillageData {

    public static void werewolfVillage(IFactionVillageBuilder builder) {
        builder.captureEntities(() -> Lists.newArrayList(new CaptureEntityEntry(ModEntities.werewolf_beast, 5), new CaptureEntityEntry(ModEntities.werewolf_survivalist, 5)))
                .factionVillagerProfession(() -> ModVillage.werewolf_expert)
                .guardSuperClass(WerewolfBaseEntity.class)
                .taskMaster(() -> ModEntities.task_master_werewolf)
                .totem(() -> ModBlocks.totem_top_werewolves_werewolf, () -> ModBlocks.totem_top_werewolves_werewolf_crafted);
    }
}
