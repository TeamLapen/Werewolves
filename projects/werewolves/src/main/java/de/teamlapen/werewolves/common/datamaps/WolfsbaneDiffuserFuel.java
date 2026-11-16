package de.teamlapen.werewolves.common.datamaps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.teamlapen.werewolves.api.datamaps.IWolfsbaneDiffuserFuel;

public record WolfsbaneDiffuserFuel(int burnDuration) implements IWolfsbaneDiffuserFuel {

    public static final Codec<IWolfsbaneDiffuserFuel> CODEC = RecordCodecBuilder.create(inst ->
            inst.group(
                    Codec.INT.fieldOf("burn_duration").forGetter(IWolfsbaneDiffuserFuel::burnDuration)
            ).apply(inst, WolfsbaneDiffuserFuel::new)
    );
    public static final Codec<IWolfsbaneDiffuserFuel> NETWORK_CODEC = Codec.INT.xmap(WolfsbaneDiffuserFuel::new, IWolfsbaneDiffuserFuel::burnDuration);
}
