package de.teamlapen.werewolves.util;

import de.teamlapen.werewolves.api.items.oil.IOil;
import de.teamlapen.werewolves.core.ModRegistries;
import net.minecraft.resources.ResourceLocation;

public class RegUtil extends de.teamlapen.vampirism.util.RegUtil {

    public static ResourceLocation id(IOil oil) {
        return ModRegistries.OILS.get().getKey(oil);
    }

    public static IOil getOil(ResourceLocation id) {
        return get(ModRegistries.OILS, id);
    }


}
