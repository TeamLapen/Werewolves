package de.teamlapen.vampirewerewolf.util;

import de.teamlapen.vampirewerewolf.api.VReference;
import de.teamlapen.vampirism.api.VampirismAPI;

import net.minecraft.entity.Entity;

import java.util.HashMap;
import java.util.Map;

public class Helper {

    public static final Map<String, Float> RAWMEAT = new HashMap<String, Float>();

    public static boolean isWerewolf(Entity entity) {
        return VReference.WEREWOLF_FACTION.equals(VampirismAPI.factionRegistry().getFaction(entity));
    }

    public static void addRawMeat(Map<String, Float> resourceLocation) {
        RAWMEAT.putAll(resourceLocation);
    }

    public static void overrideRawMeat(Map<String, Float> override) {


    }
}
