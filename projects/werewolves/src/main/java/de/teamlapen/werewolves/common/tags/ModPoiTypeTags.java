package de.teamlapen.werewolves.common.tags;

import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public class ModPoiTypeTags {
    public static final TagKey<PoiType> IS_WEREWOLF = tag("is_werewolf");

    private static TagKey<PoiType> tag(String name) {
        return tag(REFERENCE.MODID, name);
    }

    private static TagKey<PoiType> tag(String modid, String name) {
        return TagKey.create(Registries.POINT_OF_INTEREST_TYPE, WIdentifier.loc(modid, name));
    }

}
