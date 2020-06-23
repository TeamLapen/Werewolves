package de.teamlapen.werewolves.world;

import de.teamlapen.vampirism.api.entity.CaptureEntityEntry;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.event.VampirismVillageEvent;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.entities.ExtendedWerewolf;
import de.teamlapen.werewolves.entities.WerewolfEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.Random;

public class WWorldEventHandler {

    @SubscribeEvent
    public void onVillageCaptureFinish(VampirismVillageEvent.VillagerCaptureFinish event) {
        if(WReference.WEREWOLF_FACTION.equals(event.getControllingFaction())) {
            List<WerewolfEntity> werewolves = event.getWorld().getEntitiesWithinAABB(WerewolfEntity.class,event.getVillageArea());
            for(WerewolfEntity werewolf: werewolves) {
                if(werewolf.isConverted()) {
                    AbstractVillagerEntity villager = werewolf.transformBack();
                    ExtendedWerewolf.getSafe(villager).ifPresent(villager1 -> villager1.setWerewolfFaction(false));
                    event.getVillager().add((VillagerEntity)villager);
                }else if(event.isForced()) {
                    spawnEntity(event.getWorld(), getCaptureEntity(event.getCapturingFaction(),event.getWorld()), werewolf, true);
                }
            }
        }
    }

    @SubscribeEvent
    public void onVillageCaptureFinishPost(VampirismVillageEvent.VillagerCaptureFinish.Post event) {
        if(WReference.WEREWOLF_FACTION.equals(event.getCapturingFaction())) {
            for(VillagerEntity entity : event.getVillager()) {
                ExtendedWerewolf.getSafe(entity).ifPresent(villager -> villager.setWerewolfFaction(true));
            }
        }else if(WReference.WEREWOLF_FACTION.equals(event.getControllingFaction())) {
            for(VillagerEntity entity : event.getVillager()) {
                ExtendedWerewolf.getSafe(entity).ifPresent(villager -> villager.setWerewolfFaction(false));
            }
        }
    }
    @SubscribeEvent
    public void onVillageSpawnNewVillager(VampirismVillageEvent.SpawnNewVillager event) {

    }
    @SubscribeEvent
    public void onVillageReplaceBlock(VampirismVillageEvent.ReplaceBlock event) {

    }
    @SubscribeEvent
    public void onVillageMakeAggressive(VampirismVillageEvent.MakeAggressive event) {

    }

    private boolean spawnEntity(World world, MobEntity newEntity, MobEntity oldEntity, boolean replaceOld) {
        newEntity.copyDataFromOld(oldEntity);
        newEntity.setUniqueId(MathHelper.getRandomUUID());
        if (replaceOld) oldEntity.remove();
        return world.addEntity(newEntity);
    }

    private MobEntity getCaptureEntity(IFaction faction, World world) {
        List<CaptureEntityEntry> entry = faction.getVillageData().getCaptureEntries();
        return WeightedRandom.getRandomItem(world.getRandom(),entry).getEntity().create(world);
    }
}
