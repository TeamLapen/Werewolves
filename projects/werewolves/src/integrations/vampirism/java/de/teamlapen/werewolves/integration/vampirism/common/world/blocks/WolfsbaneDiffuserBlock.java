package de.teamlapen.werewolves.integration.vampirism.common.world.blocks;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.teamlapen.faction.api.world.IDescriptionProvider;
import de.teamlapen.library.api.world.EnumStrength;
import de.teamlapen.vampirism.common.world.blockentity.diffuser.DiffuserBlockEntity;
import de.teamlapen.vampirism.common.world.blocks.DiffuserBlock;
import de.teamlapen.werewolves.common.core.ModBlockEntities;
import de.teamlapen.werewolves.integration.vampirism.common.core.CompatBlockEntities;
import de.teamlapen.werewolves.integration.vampirism.common.world.blockentities.WolfsbaneDiffuserBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class WolfsbaneDiffuserBlock extends DiffuserBlock implements IDescriptionProvider {

    public static final MapCodec<WolfsbaneDiffuserBlock> CODEC = RecordCodecBuilder.mapCodec(inst ->
            inst.group(
                    propertiesCodec(),
                    EnumStrength.CODEC.fieldOf("type").forGetter(p -> p.type),
                    Codec.INT.fieldOf("radius").forGetter(p -> p.radius.get())
            ).apply(inst, (properties, strength, radius) -> new WolfsbaneDiffuserBlock(properties, strength, () -> radius))
    );

    private final EnumStrength type;
    private final Supplier<Integer> radius;

    public WolfsbaneDiffuserBlock(Properties properties, EnumStrength strength, Supplier<Integer> radius) {
        super(properties, CompatBlockEntities.WOLFSBANE_DIFFUSER::get);
        this.type = strength;
        this.radius = radius;
    }

    @Override
    protected MapCodec<? extends DiffuserBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable DiffuserBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WolfsbaneDiffuserBlockEntity(pos, state, type, radius.get());
    }

    @Override
    public Object[] getDescriptionParameters() {
        int chunks = 1 + 2 * radius.get();
        return new Object[]{chunks, chunks};
    }


}