package de.teamlapen.werewolves.util;

import com.google.common.collect.Lists;
import de.teamlapen.vampirism.api.entity.CaptureEntityEntry;
import de.teamlapen.vampirism.api.entity.ITaskMasterEntity;
import de.teamlapen.vampirism.api.entity.factions.IVillageFactionData;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModEntities;
import de.teamlapen.werewolves.core.ModVillage;
import de.teamlapen.werewolves.entities.werewolf.WerewolfBaseEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class WerewolfVillageData implements IVillageFactionData {

    private final List<CaptureEntityEntry> captureEntityEntries = Lists.newArrayList(new CaptureEntityEntry(ModEntities.werewolf_beast, 5), new CaptureEntityEntry(ModEntities.werewolf_survivalist, 5));

    @Override
    public Class<? extends MobEntity> getGuardSuperClass() {
        return WerewolfBaseEntity.class;
    }

    @Override
    public VillagerProfession getFactionVillageProfession() {
        return ModVillage.werewolf_expert;
    }

    @Override
    public List<CaptureEntityEntry> getCaptureEntries() {
        return captureEntityEntries;
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    public Pair<Block, Block> getTotemTopBlock() {
        return Pair.of(ModBlocks.totem_top_werewolves_werewolf, ModBlocks.totem_top_werewolves_werewolf_crafted);
    }

    @Override
    public EntityType<? extends ITaskMasterEntity> getTaskMasterEntity() {
        return ModEntities.task_master_werewolf;
    }
}
