package de.teamlapen.werewolves.effects;

import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;


public class WerewolvesEffect extends MobEffect {
    public WerewolvesEffect(String name, MobEffectCategory typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
        this.setRegistryName(REFERENCE.MODID, name);
    }
}
