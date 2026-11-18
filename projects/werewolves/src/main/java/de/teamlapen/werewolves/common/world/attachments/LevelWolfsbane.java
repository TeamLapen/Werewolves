package de.teamlapen.werewolves.common.world.attachments;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.teamlapen.library.api.world.EnumStrength;
import de.teamlapen.werewolves.api.world.IWolfsbaneChunkHandler;
import de.teamlapen.werewolves.common.core.ModAttachments;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LevelWolfsbane implements IWolfsbaneChunkHandler {


    public static LevelWolfsbane get(Level level) {
        return level.getData(ModAttachments.LEVEL_WOLFSBANE);
    }

    private final HashMap<ChunkPos, EnumStrength> strengthHashMap = Maps.newHashMap();
    protected final HashMap<Integer, LevelWolfsbane.Emitter> emitterHashMap = Maps.newHashMap();

    @Override
    public EnumStrength getStrengthAtChunk(ChunkPos pos) {
        EnumStrength s = strengthHashMap.get(pos);
        return s == null ? EnumStrength.NONE : s;
    }

    @Override
    public int registerWolfsbaneBlock(EnumStrength strength, List<ChunkPos> pos) {
        LevelWolfsbane.Emitter e = new LevelWolfsbane.Emitter(strength, pos);
        int hash = e.hashCode();
        emitterHashMap.putIfAbsent(hash, e);
        notifyChange(e);
        rebuildStrengthMap();
        return hash;
    }

    protected void notifyChange(LevelWolfsbane.Emitter emitter) {

    }

    protected void notifyRemove(int emitter) {

    }

    protected void notifyClear() {

    }

    public void updatePlayer(ServerPlayer player) {

    }

    @Override
    public void removeWolfsbaneBlock(int id) {
        LevelWolfsbane.Emitter e = emitterHashMap.remove(id);
        notifyRemove(id);
        rebuildStrengthMap();
    }

    private void rebuildStrengthMap() {
        strengthHashMap.clear();
        for (LevelWolfsbane.Emitter e : emitterHashMap.values()) {
            for (ChunkPos pos : e.pos) {
                EnumStrength old = strengthHashMap.get(pos);
                if (old == null || e.strength.isStrongerThan(old)) {
                    strengthHashMap.put(pos, e.strength);
                }

            }
        }
    }

    public void fill(List<LevelWolfsbane.Emitter> emitters) {
        this.clearCache();
        this.emitterHashMap.putAll(emitters.stream().collect(Collectors.toMap(Record::hashCode, x -> x)));
        rebuildStrengthMap();
        notifyClear();
    }

    public void printDebug(CommandSourceStack sender) {
        for (LevelWolfsbane.Emitter e : emitterHashMap.values()) {
            sender.sendSuccess(() -> Component.literal("E: " + e.toString()), true);
        }
        for (Map.Entry<ChunkPos, EnumStrength> e : strengthHashMap.entrySet()) {
            sender.sendSuccess(() -> Component.literal("S: " + e.toString()), true);
        }
    }

    @Override
    public void clearCache() {
        this.strengthHashMap.clear();
        this.emitterHashMap.clear();
        notifyClear();
    }

    public record Emitter(EnumStrength strength, List<ChunkPos> pos) {
        public static Codec<LevelWolfsbane.Emitter> CODEC = RecordCodecBuilder.create(inst ->
                inst.group(
                        EnumStrength.CODEC.fieldOf("strength").forGetter(s -> s.strength),
                        ChunkPos.CODEC.listOf().fieldOf("pos").forGetter(x -> x.pos)
                ).apply(inst, LevelWolfsbane.Emitter::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, LevelWolfsbane.Emitter> STREAM_CODEC = StreamCodec.composite(
                NeoForgeStreamCodecs.enumCodec(EnumStrength.class), LevelWolfsbane.Emitter::strength,
                NeoForgeStreamCodecs.CHUNK_POS.apply(ByteBufCodecs.list()), LevelWolfsbane.Emitter::pos,
                LevelWolfsbane.Emitter::new
        );
    }

    public static class Factory implements Function<IAttachmentHolder, LevelWolfsbane> {
        @Override
        public LevelWolfsbane apply(IAttachmentHolder holder) {
            if (holder instanceof Level) {
                if (holder instanceof ServerLevel serverLevel) {
                    return new ServerLevelWolfsbane(serverLevel);
                } else {
                    return new LevelWolfsbane();
                }
            }
            throw new IllegalArgumentException("Cannot create level garlic for holder " + holder.getClass() + ". Expected Level");
        }
    }
}
