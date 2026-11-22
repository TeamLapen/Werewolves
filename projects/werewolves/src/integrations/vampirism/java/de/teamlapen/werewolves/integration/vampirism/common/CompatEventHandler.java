package de.teamlapen.werewolves.integration.vampirism.common;

import de.teamlapen.werewolves.common.core.ModItems;
import de.teamlapen.werewolves.integration.vampirism.common.core.CompatItems;
import de.teamlapen.werewolves.common.world.entities.werewolf.WerewolfBaseEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class CompatEventHandler {

    public static void appendCreativeTab(BuildCreativeModeTabContentsEvent  event) {
        if (ModItems.CREATIVE_TAB.is(event.getTabKey())) {
            event.insertAfter(ModItems.STONE_ALTAR_FIRE_BOWL.toStack(), CompatItems.WOLFSBANE_DIFFUSER.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(CompatItems.WOLFSBANE_DIFFUSER.toStack(), CompatItems.WOLFSBANE_DIFFUSER_LONG.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(CompatItems.WOLFSBANE_DIFFUSER_LONG.toStack(), CompatItems.WOLFSBANE_DIFFUSER_STRONG.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.SILVER_HOE.toStack(), CompatItems.QUARREL_SILVER.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.WEREWOLF_MINION_UPGRADE_SPECIAL.toStack(), CompatItems.INJECTION_UN_WEREWOLF.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(CompatItems.INJECTION_UN_WEREWOLF.toStack(), CompatItems.WOLFSBANE_DIFFUSER_CORE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(CompatItems.WOLFSBANE_DIFFUSER_CORE.toStack(), CompatItems.WOLFSBANE_DIFFUSER_CORE_STRONG.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(CompatItems.WOLFSBANE_DIFFUSER_CORE_STRONG.toStack(), CompatItems.WOLFSBANE_DIFFUSER_CORE_LONG.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    /**
     * Using an empty syringe on a werewolf fills it with the un-werewolf injection
     */
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        ItemStack stack = event.getItemStack();
        if (stack.is(de.teamlapen.vampirism.common.core.ModItems.SYRINGE_EMPTY) && event.getTarget() instanceof WerewolfBaseEntity) {
            Player player = event.getEntity();
            if (!player.level().isClientSide()) {
                stack.consume(1, player);
                ItemStack injection = CompatItems.INJECTION_UN_WEREWOLF.toStack();
                if (!player.getInventory().add(injection)) {
                    player.drop(injection, false);
                }
            }
            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
        }
    }
}
