package de.teamlapen.werewolves.world;

import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;

public class WerewolvesWorld {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final Capability<WerewolvesWorld> CAP = CapabilityManager.get(new CapabilityToken<>() {
    });

    /**
     * @deprecated Always prefer {@link #getOpt(Level)}
     */
    @Deprecated
    public static @NotNull WerewolvesWorld get(@NotNull Level level) {
        return level.getCapability(CAP).orElseThrow(() -> new IllegalStateException("Cannot get WerewolvesWorld from World " + level));
    }

    public static @NotNull LazyOptional<WerewolvesWorld> getOpt(@NotNull Level level) {
        LazyOptional<WerewolvesWorld> opt = level.getCapability(CAP);
        if (!opt.isPresent()) {
            LOGGER.warn("Cannot get world capability. This might break mod functionality.", new Throwable().fillInStackTrace());
        }
        return opt;
    }

    public static @NotNull ICapabilityProvider createNewCapability(final @NotNull Level level) {
        return new ICapabilitySerializable<CompoundTag>() {

            final WerewolvesWorld inst = new WerewolvesWorld(level);
            final LazyOptional<WerewolvesWorld> opt = LazyOptional.of(() -> inst);

            @Override
            public void deserializeNBT(CompoundTag nbt) {
                inst.loadNBTData(nbt);
            }

            @NotNull
            @Override
            public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, Direction facing) {
                return CAP.orEmpty(capability, opt);
            }

            @Override
            public @NotNull CompoundTag serializeNBT() {
                CompoundTag tag = new CompoundTag();
                inst.saveNBTData(tag);
                return tag;
            }
        };
    }

    private final Level level;
    private final ModDamageSources damageSources;
    private final HashMap<Integer, WolfbaneEmitter> wolfsbaneEmitter = new HashMap<>();
    private final HashMap<ChunkPos, Integer> wolfsbaneDiffuser = new HashMap<>();

    public WerewolvesWorld(Level level) {
        this.level = level;
        this.damageSources = new ModDamageSources(level.registryAccess());
    }

    private void saveNBTData(CompoundTag nbt) {
    }

    private void loadNBTData(CompoundTag nbt) {

    }

    public int registerWolfsbaneDiffuserBlock(int amplifier, ChunkPos... chunks) {
        Preconditions.checkArgument(Arrays.stream(chunks).allMatch(Objects::nonNull), "Wolfsbane emitter position cannot be null");
        var emitter = new WolfbaneEmitter(amplifier, chunks);

        int hash = emitter.hashCode();
        this.wolfsbaneEmitter.put(hash, emitter);
        this.buildWolfsbaneSet();
        return hash;
    }

    public void removeWolfsbaneBlocksBlock(int id) {
        this.wolfsbaneEmitter.remove(id);
        this.buildWolfsbaneSet();
    }

    private void buildWolfsbaneSet() {
        this.wolfsbaneDiffuser.clear();
        this.wolfsbaneDiffuser.putAll(this.wolfsbaneEmitter.entrySet().stream().flatMap(e -> Arrays.stream(e.getValue().chunks).map(l -> Pair.of(l, e.getKey()))).collect(Collectors.toMap(Pair::getLeft, Pair::getRight, (a,b) -> a > b ? a : b)));
    }

    public int isEffectedByWolfsbane(BlockPos pos) {
        Integer integer = this.wolfsbaneDiffuser.get(new ChunkPos(pos));
        return integer == null ? -1 : this.wolfsbaneEmitter.get(integer).amplifier;
    }

    public ModDamageSources damageSources() {
        return this.damageSources;
    }

    private record WolfbaneEmitter(int amplifier, ChunkPos[] chunks) {

    }
}
