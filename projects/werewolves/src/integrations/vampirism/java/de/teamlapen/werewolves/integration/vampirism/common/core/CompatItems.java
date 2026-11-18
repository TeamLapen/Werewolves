package de.teamlapen.werewolves.integration.vampirism.common.core;

import de.teamlapen.vampirism.common.world.items.crossbow.QuarrelItem;
import de.teamlapen.werewolves.common.core.ModBlocks;
import de.teamlapen.werewolves.integration.vampirism.common.world.items.SilverBehavior;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CompatItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(REFERENCE.MODID);
    public static final DeferredItem<QuarrelItem> QUARREL_SILVER = ITEMS.registerItem("quarrel_silver", (props) -> new QuarrelItem(new SilverBehavior(), props));
    public static final DeferredItem<Item> WOLFSBANE_DIFFUSER_CORE = ITEMS.registerItem("wolfsbane_diffuser_core", Item::new);
    public static final DeferredItem<Item> WOLFSBANE_DIFFUSER_CORE_IMPROVED = ITEMS.registerItem("wolfsbane_diffuser_core_improved", Item::new);
    public static final DeferredItem<BlockItem> WOLFSBANE_DIFFUSER = ITEMS.registerSimpleBlockItem(CompatBlocks.WOLFSBANE_DIFFUSER);
    public static final DeferredItem<BlockItem> WOLFSBANE_DIFFUSER_LONG = ITEMS.registerSimpleBlockItem(CompatBlocks.WOLFSBANE_DIFFUSER_LONG);
    public static final DeferredItem<BlockItem> WOLFSBANE_DIFFUSER_IMPROVED = ITEMS.registerSimpleBlockItem(CompatBlocks.WOLFSBANE_DIFFUSER_IMPROVED);

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
