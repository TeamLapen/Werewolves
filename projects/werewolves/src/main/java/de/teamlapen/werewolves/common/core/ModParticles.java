package de.teamlapen.werewolves.common.core;

import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, REFERENCE.MODID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> JACARANDA_LEAVES = PARTICLE_TYPES.register("jacaranda_leaves", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MAGIC_LEAVES = PARTICLE_TYPES.register("magic_leaves", () -> new SimpleParticleType(false));

    static void register(IEventBus bus) {
        PARTICLE_TYPES.register(bus);
    }

}
