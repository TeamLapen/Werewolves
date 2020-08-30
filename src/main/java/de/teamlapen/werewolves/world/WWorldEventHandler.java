package de.teamlapen.werewolves.world;

import de.teamlapen.vampirism.api.entity.CaptureEntityEntry;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.event.VampirismVillageEvent;
import de.teamlapen.werewolves.entities.werewolf.WerewolfTransformable;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class WWorldEventHandler {

    @SubscribeEvent
    public void onVillageCaptureFinish(VampirismVillageEvent.VillagerCaptureFinish event) {
        if(WReference.WEREWOLF_FACTION.equals(event.getControllingFaction())) {
            List<MobEntity> werewolves = event.getWorld().getEntitiesWithinAABB(MobEntity.class, event.getVillageArea(), entity -> entity instanceof WerewolfTransformable);
            werewolves.forEach(e -> {
                if (((WerewolfTransformable) e).canTransform()) {
                    ((WerewolfTransformable) e).transformBack();
                } else if (event.isForced()) {
                    spawnEntity(event.getWorld(), getCaptureEntity(event.getCapturingFaction(), event.getWorld()), e, true);
                }
            });
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

    private void spawnEntity(World world, MobEntity newEntity, MobEntity oldEntity, boolean replaceOld) {
        newEntity.copyDataFromOld(oldEntity);
        newEntity.setUniqueId(MathHelper.getRandomUUID());
        if (replaceOld) oldEntity.remove();
        world.addEntity(newEntity);
    }

    private MobEntity getCaptureEntity(IFaction<?> faction, World world) {
        List<CaptureEntityEntry> entry = faction.getVillageData().getCaptureEntries();
        return WeightedRandom.getRandomItem(world.getRandom(), entry).getEntity().create(world);
    }
}
