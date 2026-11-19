package de.teamlapen.werewolves.integration.vampirism.common.core;

import de.teamlapen.library.api.world.EnumStrength;
import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.integration.vampirism.common.world.blocks.WolfsbaneDiffuserBlock;
import de.teamlapen.werewolves.integration.vampirism.common.world.blocks.WolfsbaneDiffuserCoreBlock;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CompatBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(REFERENCE.MODID);

    public static final DeferredBlock<WolfsbaneDiffuserCoreBlock> WOLFSBANE_DIFFUSER_CORE = BLOCKS.registerBlock("wolfsbane_diffuser_core", (props) -> new WolfsbaneDiffuserCoreBlock(props, EnumStrength.WEAK), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.PURPLE_WOOL));
    public static final DeferredBlock<WolfsbaneDiffuserCoreBlock> WOLFSBANE_DIFFUSER_CORE_STRONG = BLOCKS.registerBlock("wolfsbane_diffuser_core_strong", (props) -> new WolfsbaneDiffuserCoreBlock(props, EnumStrength.MEDIUM), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BLUE_WOOL));
    public static final DeferredBlock<WolfsbaneDiffuserCoreBlock> WOLFSBANE_DIFFUSER_CORE_LONG = BLOCKS.registerBlock("wolfsbane_diffuser_core_long", (props) -> new WolfsbaneDiffuserCoreBlock(props, EnumStrength.MEDIUM), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_WOOL));

    public static final DeferredBlock<WolfsbaneDiffuserBlock> WOLFSBANE_DIFFUSER = BLOCKS.registerBlock("wolfsbane_diffuser_normal", (props) -> new WolfsbaneDiffuserBlock(props, EnumStrength.WEAK, ModConfig.balance().BLOCKS.wolfsbaneDiffuserNormalDist), x -> x.mapColor(MapColor.STONE).strength(40.0F, 1200.0F).sound(SoundType.STONE).noOcclusion());
    public static final DeferredBlock<WolfsbaneDiffuserBlock> WOLFSBANE_DIFFUSER_LONG = BLOCKS.registerBlock("wolfsbane_diffuser_long", (props) -> new WolfsbaneDiffuserBlock(props, EnumStrength.WEAK, ModConfig.balance().BLOCKS.wolfsbaneDiffuserLongDist), () -> BlockBehaviour.Properties.ofFullCopy(WOLFSBANE_DIFFUSER.get()));
    public static final DeferredBlock<WolfsbaneDiffuserBlock> WOLFSBANE_DIFFUSER_STRONG = BLOCKS.registerBlock("wolfsbane_diffuser_strong", (props) -> new WolfsbaneDiffuserBlock(props, EnumStrength.MEDIUM, ModConfig.balance().BLOCKS.wolfsbaneDiffuserStrongDist), () -> BlockBehaviour.Properties.ofFullCopy(WOLFSBANE_DIFFUSER.get()));

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }

}
