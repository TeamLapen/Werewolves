package de.teamlapen.werewolves.common.util;

import com.mojang.datafixers.util.Pair;
import de.teamlapen.faction.api.factions.village.VillageBanner;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BannerPatterns;

import java.util.List;

public class WerewolfVillage {

    public static VillageBanner create() {
        return new VillageBanner(Items.YELLOW_BANNER.builtInRegistryHolder(), List.of(
                new Pair<>(BannerPatterns.RHOMBUS_MIDDLE, DyeColor.BLACK),
                new Pair<>(BannerPatterns.TRIANGLE_BOTTOM, DyeColor.BLACK),
                new Pair<>(BannerPatterns.BORDER, DyeColor.BLACK),
                new Pair<>(BannerPatterns.TRIANGLES_BOTTOM, DyeColor.BROWN),
                new Pair<>(BannerPatterns.TRIANGLES_TOP, DyeColor.BROWN)
        ));
    }
}
