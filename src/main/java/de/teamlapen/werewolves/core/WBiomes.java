package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.world.WerewolfHeaven;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class WBiomes {

    public static final Biome werewolf_heaven = getNull();

    public static void registerBiomes(IForgeRegistry<Biome> registry) {
        registry.register(new WerewolfHeaven().setRegistryName(REFERENCE.MODID,"werewolf_heaven"));
    }
}
