package de.teamlapen.werewolves.world;

import de.teamlapen.vampirism.api.entity.CaptureEntityEntry;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.event.VampirismVillageEvent;
import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.werewolves.entities.werewolf.IVillagerTransformable;
import de.teamlapen.werewolves.entities.werewolf.WerewolfTransformable;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.util.WeighedRandom;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class ModWorldEventHandler {

    @SubscribeEvent
    public void onVillageCaptureFinish(VampirismVillageEvent.VillagerCaptureFinish.Pre event) {
        Level world = ((BlockEntity) event.getTotem()).getLevel();
        List<Mob> werewolves = world.getEntitiesOfClass(Mob.class, event.getVillageArea(), entity -> entity instanceof WerewolfTransformable);
        if (WReference.WEREWOLF_FACTION.equals(event.getControllingFaction())) {
            werewolves.forEach(e -> {
                if (((WerewolfTransformable) e).canTransform()) {
                    ((WerewolfTransformable) e).transformBack();
                } else if (event.isForced()) {
                    spawnEntity(world, getCaptureEntity(event.getCapturingFaction(), world), e, true);
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
    public void onVillageReplaceBlock(VampirismVillageEvent.ReplaceBlock event) {
        if (event.getState().getBlock() == ModBlocks.cursed_earth) {
            ((BlockEntity) event.getTotem()).getLevel().setBlockAndUpdate(event.getBlockPos(), ((BlockEntity) event.getTotem()).getLevel().getBiome(event.getBlockPos()).getGenerationSettings().getSurfaceBuilderConfig().getTopMaterial());
        }
    }
    @SubscribeEvent
    public void onVillageMakeAggressive(VampirismVillageEvent.MakeAggressive event) {
        if (event.getControllingFaction() == WReference.WEREWOLF_FACTION && ((IVillagerTransformable) event.getOldVillager()).canTransform()) {
            event.setCanceled(true);
            ((IVillagerTransformable) event.getOldVillager()).transformToWerewolf(WerewolfTransformable.TransformType.RAID);
        }
    }

    private void spawnEntity(Level world, Mob newEntity, Mob oldEntity, boolean replaceOld) {
        newEntity.restoreFrom(oldEntity);
        newEntity.setUUID(Mth.createInsecureUUID());
        if (replaceOld) oldEntity.remove();
        world.addFreshEntity(newEntity);
    }

    private Mob getCaptureEntity(IFaction<?> faction, Level world) {
        List<CaptureEntityEntry> entry = faction.getVillageData().getCaptureEntries();
        return WeighedRandom.getRandomItem(world.getRandom(), entry).getEntity().create(world);
    }
}
