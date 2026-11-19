package de.teamlapen.werewolves.integration.vampirism.common;

import de.teamlapen.werewolves.common.core.ModItems;
import de.teamlapen.werewolves.integration.vampirism.common.core.CompatItems;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public class CompatEventHandler {

    public static void appendCreativeTab(BuildCreativeModeTabContentsEvent  event) {
        if (ModItems.CREATIVE_TAB.is(event.getTabKey())) {
            event.insertAfter(ModItems.STONE_ALTAR_FIRE_BOWL.toStack(), CompatItems.WOLFSBANE_DIFFUSER.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(CompatItems.WOLFSBANE_DIFFUSER.toStack(), CompatItems.WOLFSBANE_DIFFUSER_LONG.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(CompatItems.WOLFSBANE_DIFFUSER_LONG.toStack(), CompatItems.WOLFSBANE_DIFFUSER_STRONG.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.SILVER_HOE.toStack(), CompatItems.QUARREL_SILVER.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.INJECTION_UN_WEREWOLF.toStack(), CompatItems.WOLFSBANE_DIFFUSER_CORE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(CompatItems.WOLFSBANE_DIFFUSER_CORE.toStack(), CompatItems.WOLFSBANE_DIFFUSER_CORE_STRONG.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(CompatItems.WOLFSBANE_DIFFUSER_CORE_STRONG.toStack(), CompatItems.WOLFSBANE_DIFFUSER_CORE_LONG.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}
