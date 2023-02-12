package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.world.gen.feature.JacarandaTrunkPlacer;
import de.teamlapen.werewolves.world.gen.feature.MagicFoliagePlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModWorld {

    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, REFERENCE.MODID);
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER = DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, REFERENCE.MODID);


    public static final RegistryObject<TrunkPlacerType<?>> JACARANDA_TRUNK = TRUNK_PLACER.register("jacaranda", () -> new TrunkPlacerType<>(JacarandaTrunkPlacer.CODEC));
    public static final RegistryObject<FoliagePlacerType<?>> MAGIC_FOLIAGE = FOLIAGE_PLACER.register("magic", () -> new FoliagePlacerType<>(MagicFoliagePlacer.CODEC));

    static void register(IEventBus bus) {
        TRUNK_PLACER.register(bus);
        FOLIAGE_PLACER.register(bus);
    }
}
