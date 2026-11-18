package de.teamlapen.werewolves.integration.vampirism.common.datamaps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.teamlapen.vampirism.api.datamaps.IDiffuserFuel;

public record WolfsbaneDiffuserFuel(int burnDuration) implements IDiffuserFuel {

    public static final Codec<WolfsbaneDiffuserFuel> CODEC = RecordCodecBuilder.create(inst ->
            inst.group(
                    Codec.INT.fieldOf("burn_duration").forGetter(WolfsbaneDiffuserFuel::burnDuration)
            ).apply(inst, WolfsbaneDiffuserFuel::new)
    );
    public static final Codec<WolfsbaneDiffuserFuel> NETWORK_CODEC = Codec.INT.xmap(WolfsbaneDiffuserFuel::new, WolfsbaneDiffuserFuel::burnDuration);
}
