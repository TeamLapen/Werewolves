package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.world.gen.WerewolfHeavenBiome;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModBiomes {

    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, REFERENCE.MODID);

    public static final RegistryObject<Biome> WEREWOLF_HEAVEN = BIOMES.register("werewolf_heaven", WerewolfHeavenBiome::createWerewolfHeavenBiome);

    static void register(IEventBus bus) {
        BIOMES.register(bus);
    }
}
