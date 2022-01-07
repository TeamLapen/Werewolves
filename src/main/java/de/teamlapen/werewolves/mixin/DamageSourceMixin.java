package de.teamlapen.werewolves.mixin;

import de.teamlapen.werewolves.util.DamageSourceExtended;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DamageSource.class)
public class DamageSourceMixin implements DamageSourceExtended {

    private float ignoreArmorPerc = 1;
    @Override
    public float ignoreArmorPerc() {
        return this.ignoreArmorPerc;
    }

    @Override
    public void setArmorIgnorePerc(float perc) {
        this.ignoreArmorPerc = perc;
    }
}
