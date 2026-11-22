package de.teamlapen.werewolves.common.tags;

import de.teamlapen.werewolves.api.WIdentifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;

public class ModEffectTags {
    public static final TagKey<MobEffect> DISABLES_ACTIONS_WEREWOLF = tag("disables_actions/werewolf");

    private static TagKey<MobEffect> tag(String name) {
        return TagKey.create(Registries.MOB_EFFECT, WIdentifier.mc(name));
    }

}
