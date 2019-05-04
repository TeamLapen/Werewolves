package de.teamlapen.werewolves.util;

import com.google.common.collect.Maps;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.werewolves.api.VReference;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import java.util.Map;

public class Helper {

    public static final Map<ResourceLocation, Float> RAWMEAT = Maps.newHashMap();

    public static boolean isWerewolf(Entity entity) {
        return VReference.WEREWOLF_FACTION.equals(VampirismAPI.factionRegistry().getFaction(entity));
    }

    public static void addRawMeat(Map<ResourceLocation, Float> resourceLocation) {
        RAWMEAT.putAll(resourceLocation);
    }

    public static void overrideRawMeat(Map<ResourceLocation, Float> override) {


    }
}
