package de.teamlapen.werewolves.world;

import de.teamlapen.vampirism.api.entity.CaptureEntityEntry;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.event.VampirismVillageEvent;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entities.werewolf.IVillagerTransformable;
import de.teamlapen.werewolves.api.entities.werewolf.TransformType;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfTransformable;
import net.minecraft.util.Mth;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.Optional;

public class ModWorldEventHandler {

    @SubscribeEvent
    public void onVillageCaptureFinish(VampirismVillageEvent.VillagerCaptureFinish.Pre event) {
        Level world = ((BlockEntity) event.getTotem()).getLevel();
        List<Mob> werewolves = world.getEntitiesOfClass(Mob.class, event.getVillageArea(), entity -> entity instanceof WerewolfTransformable);
        if (WReference.WEREWOLF_FACTION.equals(event.getControllingFaction())) {
            werewolves.forEach(e -> {
                if (((WerewolfTransformable) e).canTransform()) {
                    ((WerewolfTransformable) e).transformBack();
                } else if (event.isForced() && event.getCapturingFaction() != null) {
                    getCaptureEntity(event.getCapturingFaction(), world).ifPresent(mob -> {
                        spawnEntity(world, mob, e, true);
                    });
                }
            });
        } else {
            for (Mob werewolf : werewolves) {
                if (werewolf instanceof IVillagerTransformable) {
                    ((IVillagerTransformable) werewolf).transformBack();
                    ((IVillagerTransformable) werewolf).setWerewolfFaction(false);
                }
            }
        }
    }

    @SubscribeEvent
    public void onVillageSpawnNewVillager(VampirismVillageEvent.SpawnNewVillager event) {
        if (event.getControllingFaction() == WReference.WEREWOLF_FACTION) {
            if (event.getNewVillager().getRandom().nextInt(6) == 0) {
                ((IVillagerTransformable) event.getNewVillager()).setWerewolfFaction(true);
            }
        }
    }

    @SubscribeEvent
    public void onVillageMakeAggressive(VampirismVillageEvent.MakeAggressive event) {
        if (event.getControllingFaction() == WReference.WEREWOLF_FACTION && ((IVillagerTransformable) event.getOldVillager()).canTransform()) {
            event.setCanceled(true);
            ((IVillagerTransformable) event.getOldVillager()).transformToWerewolf(TransformType.RAID);
        }
    }

    @SuppressWarnings("SameParameterValue")
    private void spawnEntity(Level world, Mob newEntity, Mob oldEntity, boolean replaceOld) {
        newEntity.restoreFrom(oldEntity);
        newEntity.setUUID(Mth.createInsecureUUID());
        if (replaceOld) oldEntity.remove(Entity.RemovalReason.DISCARDED);
        world.addFreshEntity(newEntity);
    }

    private Optional<Mob> getCaptureEntity(IFaction<?> faction, Level world) {
        List<CaptureEntityEntry<?>> entries = faction.getVillageData().getCaptureEntries();
        return WeightedRandom.getRandomItem(world.getRandom(), entries).map(entry -> entry.getEntity().create(world));
    }
}
