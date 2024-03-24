package de.teamlapen.werewolves.world;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import de.teamlapen.vampirism.api.EnumStrength;
import de.teamlapen.werewolves.core.ModAttachments;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class LevelWolfsbane {


    public static Optional<LevelWolfsbane> getOpt(@NotNull Level level) {
        return Optional.of(level.getData(ModAttachments.LEVEL_WOLFSBANE));
    }

    private final HashMap<Integer, LevelWolfsbane.Emitter> wolfsbaneEmitter = new HashMap<>();
    private final HashMap<ChunkPos, Integer> wolfsbaneDiffuser = new HashMap<>();

    public int registerWolfsbaneDiffuserBlock(int amplifier, ChunkPos... chunks) {
        Preconditions.checkArgument(Arrays.stream(chunks).allMatch(Objects::nonNull), "Wolfsbane emitter position cannot be null");
        var emitter = new LevelWolfsbane.Emitter(amplifier, chunks);

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
        this.wolfsbaneDiffuser.putAll(this.wolfsbaneEmitter.entrySet().stream().flatMap(e -> Arrays.stream(e.getValue().chunks).map(l -> Pair.of(l, e.getKey()))).collect(Collectors.toMap(Pair::getLeft, Pair::getRight, (a, b) -> a > b ? a : b)));
    }

    public int isEffectedByWolfsbane(BlockPos pos) {
        Integer integer = this.wolfsbaneDiffuser.get(new ChunkPos(pos));
        return integer == null ? -1 : this.wolfsbaneEmitter.get(integer).amplifier;
    }

    private record Emitter(int amplifier, ChunkPos[] chunks) {
    }
}
