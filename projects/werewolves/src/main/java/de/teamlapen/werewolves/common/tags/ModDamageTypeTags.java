package de.teamlapen.werewolves.common.tags;

import de.teamlapen.werewolves.api.WIdentifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypeTags {
    public static final TagKey<DamageType> WEREWOLF_FUR_IMMUNE = tag("werewolf_fur_immune");
    public static final TagKey<DamageType> WEREWOLF_ARMOR_REDUCTION = tag("werewolf_armor_reduction");
    public static final TagKey<DamageType> BITE = tag("bite");

    private static TagKey<DamageType> tag(String name) {
        return TagKey.create(Registries.DAMAGE_TYPE, WIdentifier.mod(name));
    }

}
