package de.teamlapen.werewolves.api.world;

import de.teamlapen.library.api.world.EnumStrength;
import net.minecraft.world.level.ChunkPos;

import java.util.List;

public interface IWolfsbaneChunkHandler {

    /**
     * @return The garlic strength at the given position
     */
    EnumStrength getStrengthAtChunk(ChunkPos pos);

    /**
     * Registers a garlic "emitter".
     *
     * @param strength Strength
     * @param pos      All affected chunk pos. Cannot be null
     * @return A unique hash which is required to remove the registration again
     */
    int registerWolfsbaneBlock(EnumStrength strength, List<ChunkPos> pos);

    /**
     * Removes a garlic "emitter" registration
     *
     * @param id The unique hash obtained during registration
     */
    void removeWolfsbaneBlock(int id);

    /**
     * Clear any caches upon world unload.
     */
    void clearCache();
}
