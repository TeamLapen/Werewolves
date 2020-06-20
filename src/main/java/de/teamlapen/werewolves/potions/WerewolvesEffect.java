package de.teamlapen.werewolves.potions;

import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;


public class WerewolvesEffect extends Effect {
    public WerewolvesEffect(String name, EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
        this.setRegistryName(REFERENCE.MODID, name);
    }
}
