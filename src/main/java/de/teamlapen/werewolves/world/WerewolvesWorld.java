package de.teamlapen.werewolves.world;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

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

    public WerewolvesWorld(Level level) {
        this.level = level;
        this.damageSources = new ModDamageSources(level.registryAccess());
    }

    private void loadNBTData(CompoundTag nbt) {
    }

    private void saveNBTData(CompoundTag nbt) {
    }

    public ModDamageSources damageSources() {
        return this.damageSources;
    }

}
