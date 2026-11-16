package de.teamlapen.werewolves.common.world;

import de.teamlapen.faction.api.event.FactionVillageEvent;
import de.teamlapen.faction.api.factions.IFaction;
import de.teamlapen.werewolves.api.entities.werewolf.IVillagerTransformable;
import de.teamlapen.werewolves.api.entities.werewolf.TransformType;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfTransformable;
import de.teamlapen.werewolves.common.advancements.criterion.WerewolfActionCriterionTrigger;
import de.teamlapen.werewolves.common.core.ModAdvancements;
import de.teamlapen.werewolves.common.core.ModFactions;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.util.List;
import java.util.Optional;

public class ModWorldEventHandler {

    @SubscribeEvent
    public void onUpdateCreaturesOnCapture(FactionVillageEvent.UpdateCreaturesOnCaptureFinishEvent event) {
        Level world = event.getLevel();
        List<Mob> werewolves = world.getEntitiesOfClass(Mob.class, event.getVillageArea(), WerewolfTransformable.class::isInstance);
        if (IFaction.is(event.getControllingFaction(), ModFactions.WEREWOLF)) {
            werewolves.forEach(e -> {
                if (((WerewolfTransformable) e).canTransform()) {
                    ((WerewolfTransformable) e).transformBack();
                } else if (event.isForced() && event.getCapturingFaction() != null) {
                    getCaptureEntity(event, event.getCapturingFaction()).ifPresent(mob -> spawnEntity(world, mob, e, true));
                }
            });
        } else {
            for (Mob werewolf : werewolves) {
                if (werewolf instanceof IVillagerTransformable transformable) {
                    transformable.transformBack();
                    transformable.setWerewolfFaction(false);
                }
            }
        }

        if (IFaction.is(event.getCapturingFaction(), ModFactions.WEREWOLF)) {
            var entities = world.getEntitiesOfClass(ServerPlayer.class, event.getVillageArea());
            for (ServerPlayer entity : entities) {
                if (Helper.isWerewolf(entity)) {
                    ModAdvancements.TRIGGER_VAMPIRE_ACTION.get().trigger(entity, WerewolfActionCriterionTrigger.Action.VILLAGE_CONQUERED);
                }
            }
        }
    }

    @SubscribeEvent
    public void onVillageSpawnNewVillager(FactionVillageEvent.SpawnNewVillager event) {
        if (IFaction.is(event.getFaction(), ModFactions.WEREWOLF)) {
            Villager villager = event.getOrCreateNewVillager();
            if (villager != null && villager.getRandom().nextInt(6) == 0) {
                ((IVillagerTransformable) villager).setWerewolfFaction(true);
            }
        }
    }

    @SubscribeEvent
    public void onVillageMakeAggressive(FactionVillageEvent.MakeAggressive event) {
        if (IFaction.is(event.getControllingFaction(), ModFactions.WEREWOLF) && ((IVillagerTransformable) event.getVillager()).canTransform()) {
            event.setCanceled(true);
            ((IVillagerTransformable) event.getVillager()).transformToWerewolf(TransformType.RAID);
        }
    }

    @SuppressWarnings("SameParameterValue")
    private void spawnEntity(Level world, Mob newEntity, Mob oldEntity, boolean replaceOld) {
        newEntity.restoreFrom(oldEntity);
        newEntity.setUUID(Mth.createInsecureUUID(world.getRandom()));
        if (replaceOld) oldEntity.remove(Entity.RemovalReason.DISCARDED);
        world.addFreshEntity(newEntity);
    }

    private Optional<Mob> getCaptureEntity(FactionVillageEvent event, Holder<? extends IFaction<?>> faction) {
        LivingEntity entity = NeoForge.EVENT_BUS.post(new FactionVillageEvent.SpawnCaptureEntityEvent(event.getTotem(), faction)).getEntity();
        return entity instanceof Mob mob ? Optional.of(mob) : Optional.empty();
    }
}
