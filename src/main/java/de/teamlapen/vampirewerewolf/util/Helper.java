package de.teamlapen.vampirewerewolf.util;

import com.google.common.collect.Lists;
import de.teamlapen.vampirism.api.VampirismAPI;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import java.util.List;

public class Helper {

    public static final List<Item> RAWMEAT = Lists.newArrayList(Items.PORKCHOP);

    public static boolean isWerewolf(Entity entity) {
        return VReference.WEREWOLF_FACTION.equals(VampirismAPI.factionRegistry().getFaction(entity));
    }
}
