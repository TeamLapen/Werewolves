package de.teamlapen.werewolves.world.loot;

import de.teamlapen.werewolves.util.REFERENCE;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class LootHandler {
    public static final ResourceLocation DIREWOLF = register("entities/direwolf");

    public static final LootHandler instance = new LootHandler();

    public static LootHandler getInstance() {
        return instance;
    }

    private static ResourceLocation register(String s) {
        ResourceLocation loc = new ResourceLocation(REFERENCE.MODID, s);
        LootTableList.register(loc);
        return loc;
    }
}
