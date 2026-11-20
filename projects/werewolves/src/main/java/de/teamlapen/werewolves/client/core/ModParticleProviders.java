package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.common.core.ModParticles;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FallingLeavesParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

public class ModParticleProviders {

    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.JACARANDA_LEAVES.get(), sprites -> new ColoredLeavesProvider(sprites, 0xE371FF));
        event.registerSpriteSet(ModParticles.MAGIC_LEAVES.get(), sprites -> new ColoredLeavesProvider(sprites, 0x70BAFF));
    }

    /**
     * Uses the grayscale vanilla leaf particle sprites and tints them with a fixed color, like the vanilla tinted leaves particle does with the foliage color
     */
    public static class ColoredLeavesProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;
        private final float red;
        private final float green;
        private final float blue;

        public ColoredLeavesProvider(SpriteSet sprites, int color) {
            this.sprites = sprites;
            this.red = (color >> 16 & 0xFF) / 255f;
            this.green = (color >> 8 & 0xFF) / 255f;
            this.blue = (color & 0xFF) / 255f;
        }

        @Override
        public Particle createParticle(SimpleParticleType options, ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux, RandomSource random) {
            FallingLeavesParticle particle = new FallingLeavesParticle(level, x, y, z, this.sprites.get(random), 0.07F, 10.0F, true, false, 2.0F, 0.021F);
            particle.setColor(this.red, this.green, this.blue);
            return particle;
        }
    }
}
