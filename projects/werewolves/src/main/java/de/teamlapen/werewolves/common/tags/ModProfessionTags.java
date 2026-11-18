package de.teamlapen.werewolves.common.tags;

import de.teamlapen.werewolves.api.WIdentifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.villager.VillagerProfession;

public class ModProfessionTags {
    public static final TagKey<VillagerProfession> IS_WEREWOLF = tag("is_werewolf");


    private static TagKey<VillagerProfession> tag(String name) {
        return TagKey.create(Registries.VILLAGER_PROFESSION, WIdentifier.mod(name));
    }

}
