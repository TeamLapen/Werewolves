package de.teamlapen.werewolves.core;

import com.google.common.collect.Lists;
import de.teamlapen.vampirism.tileentity.TotemTileEntity;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.entities.WerewolfEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class ModEntities extends de.teamlapen.vampirism.core.ModEntities {

    public static final EntityType<WerewolfEntity> werewolf;
    public static final EntityType<WerewolfEntity.IMob> werewolf_imob = getNull();


    static void registerEntities(IForgeRegistry<EntityType<?>> registry) {
        registry.register(werewolf);
        registry.register(prepareEntityType("werewolf_imob", EntityType.Builder.create(WerewolfEntity.IMob::new, WReference.WEREWOLF_CREATUE_TYPE).size(0.7f, 2f), false));
    }

    static void registerSpawns() {
        EntitySpawnPlacementRegistry.register(werewolf, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WerewolfEntity::spawnPredicateWerewolf);
    }

    static void registerSpawnEntries() {
        TotemTileEntity.registerCaptureEntities(WReference.WEREWOLF_FACTION, Lists.newArrayList(new TotemTileEntity.CaptureEntityEntry(ModEntities.werewolf, 10)));
    }

    private static <T extends Entity> EntityType<T> prepareEntityType(String id, EntityType.Builder<T> builder, boolean spawnable) {
        EntityType.Builder<T> type = builder.setTrackingRange(80).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true);
        if (!spawnable)
            type.disableSummoning();
        EntityType<T> entry = type.build(REFERENCE.MODID + ":" + id);
        entry.setRegistryName(REFERENCE.MODID, id);
        return entry;
    }

    //needed for worldgen
    static {
        werewolf = prepareEntityType("werewolf", EntityType.Builder.create(WerewolfEntity::new, WReference.WEREWOLF_CREATUE_TYPE).size(0.7f, 2f), true);
    }
}
