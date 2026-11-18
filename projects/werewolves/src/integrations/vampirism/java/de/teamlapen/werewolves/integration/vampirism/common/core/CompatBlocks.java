package de.teamlapen.werewolves.integration.vampirism.common.core;

import de.teamlapen.library.api.world.EnumStrength;
import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.integration.vampirism.common.world.blocks.WolfsbaneDiffuserBlock;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CompatBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(REFERENCE.MODID);

    public static final DeferredBlock<WolfsbaneDiffuserBlock> WOLFSBANE_DIFFUSER = BLOCKS.registerBlock("wolfsbane_diffuser_normal", (props) -> new WolfsbaneDiffuserBlock(props, EnumStrength.WEAK, ModConfig.balance().BLOCKS.wolfsbaneDiffuserNormalDist), x -> x.mapColor(MapColor.STONE).strength(40.0F, 1200.0F).sound(SoundType.STONE).noOcclusion());
    public static final DeferredBlock<WolfsbaneDiffuserBlock> WOLFSBANE_DIFFUSER_LONG = BLOCKS.registerBlock("wolfsbane_diffuser_long", (props) -> new WolfsbaneDiffuserBlock(props, EnumStrength.MEDIUM, ModConfig.balance().BLOCKS.wolfsbaneDiffuserLongDist), () -> BlockBehaviour.Properties.ofFullCopy(WOLFSBANE_DIFFUSER.get()));
    public static final DeferredBlock<WolfsbaneDiffuserBlock> WOLFSBANE_DIFFUSER_IMPROVED = BLOCKS.registerBlock("wolfsbane_diffuser_improved", (props) -> new WolfsbaneDiffuserBlock(props, EnumStrength.STRONG, ModConfig.balance().BLOCKS.wolfsbaneDiffuserImprovedDist), () -> BlockBehaviour.Properties.ofFullCopy(WOLFSBANE_DIFFUSER.get()));

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }

}
