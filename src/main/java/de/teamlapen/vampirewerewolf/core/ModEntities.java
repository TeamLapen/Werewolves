package de.teamlapen.vampirewerewolf.core;

import de.teamlapen.vampirewerewolf.entity.EntityDirewolf;
import de.teamlapen.vampirewerewolf.util.REFERENCE;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

public class ModEntities {
    public static final String DIREWOLF = "direwolf";

    public static final List<String> spawnableEntityNames = new ArrayList<>();
    private static int modEntityId = 0;

    static void registerEntities(IForgeRegistry<EntityEntry> registry) {
        registry.register(prepareEntityEntry(EntityDirewolf.class, DIREWOLF, EntityLiving.SpawnPlacementType.ON_GROUND, true).build());
    }

    private static <T extends Entity> EntityEntryBuilder<T> prepareEntityEntry(Class<T> clazz, String id, EntityLiving.SpawnPlacementType placementType, boolean egg) {
        ResourceLocation n = new ResourceLocation(REFERENCE.MODID, id);
        EntityEntryBuilder<T> builder = EntityEntryBuilder.<T>create().entity(clazz).id(n, modEntityId++).name("vampirewerewolf." + id).tracker(80, 1, true);
        if (egg) {
            builder.egg(0x8B15A3, id.hashCode());
        }
        EntitySpawnPlacementRegistry.setPlacementType(clazz, placementType);
        return builder;
    }
}
