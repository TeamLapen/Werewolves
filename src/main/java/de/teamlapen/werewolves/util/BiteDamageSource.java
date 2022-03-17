package de.teamlapen.werewolves.util;

import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

public class BiteDamageSource extends EntityDamageSource {

    public BiteDamageSource(String damageTypeIn, @Nullable Entity damageSourceEntityIn) {
        super(damageTypeIn, damageSourceEntityIn);
    }
}
