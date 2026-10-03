package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.world.gen.feature.JacarandaTrunkPlacer;
import de.teamlapen.werewolves.world.gen.feature.MagicFoliagePlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModWorld {

    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, REFERENCE.MODID);
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER = DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, REFERENCE.MODID);


    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<JacarandaTrunkPlacer>> JACARANDA_TRUNK = TRUNK_PLACER.register("jacaranda", () -> new TrunkPlacerType<>(JacarandaTrunkPlacer.CODEC));
    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<MagicFoliagePlacer>> MAGIC_FOLIAGE = FOLIAGE_PLACER.register("magic", () -> new FoliagePlacerType<>(MagicFoliagePlacer.CODEC));

    static void register(IEventBus bus) {
        TRUNK_PLACER.register(bus);
        FOLIAGE_PLACER.register(bus);
    }
}
