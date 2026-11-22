package de.teamlapen.werewolves.integration.vampirism.common.core;

import de.teamlapen.vampirism.common.world.items.crossbow.QuarrelItem;
import de.teamlapen.werewolves.common.core.ModBlocks;
import de.teamlapen.werewolves.integration.vampirism.common.world.items.SilverBehavior;
import de.teamlapen.werewolves.integration.vampirism.common.world.items.UnWerewolfInjectionItem;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CompatItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(REFERENCE.MODID);
    public static final DeferredItem<QuarrelItem> QUARREL_SILVER = ITEMS.registerItem("quarrel_silver", (props) -> new QuarrelItem(new SilverBehavior(), props));
    public static final DeferredItem<UnWerewolfInjectionItem> INJECTION_UN_WEREWOLF = ITEMS.registerItem("injection_un_werewolf", UnWerewolfInjectionItem::new);
    public static final DeferredItem<BlockItem> WOLFSBANE_DIFFUSER_CORE = ITEMS.registerSimpleBlockItem(CompatBlocks.WOLFSBANE_DIFFUSER_CORE, x -> x.useBlockDescriptionPrefix().factions$withShiftDescription());
    public static final DeferredItem<BlockItem> WOLFSBANE_DIFFUSER_CORE_STRONG = ITEMS.registerSimpleBlockItem(CompatBlocks.WOLFSBANE_DIFFUSER_CORE_STRONG, x -> x.factions$withShiftDescription(Component.translatable("tooltip.werewolves.wolfsbane_diffuser_core")));
    public static final DeferredItem<BlockItem> WOLFSBANE_DIFFUSER_CORE_LONG = ITEMS.registerSimpleBlockItem(CompatBlocks.WOLFSBANE_DIFFUSER_CORE_LONG, x -> x.factions$withShiftDescription(Component.translatable("tooltip.werewolves.wolfsbane_diffuser_core")));
    public static final DeferredItem<BlockItem> WOLFSBANE_DIFFUSER = ITEMS.registerSimpleBlockItem(CompatBlocks.WOLFSBANE_DIFFUSER, x -> x.factions$withShiftDescription());
    public static final DeferredItem<BlockItem> WOLFSBANE_DIFFUSER_LONG = ITEMS.registerSimpleBlockItem(CompatBlocks.WOLFSBANE_DIFFUSER_LONG, x -> x.factions$withShiftDescription());
    public static final DeferredItem<BlockItem> WOLFSBANE_DIFFUSER_STRONG = ITEMS.registerSimpleBlockItem(CompatBlocks.WOLFSBANE_DIFFUSER_STRONG, x -> x.factions$withShiftDescription());

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
