package de.teamlapen.werewolves.misc.extensions;

import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;

public interface IMultiNoiseBiomeSourceParameterList {

    interface Preset {
        MultiNoiseBiomeSourceParameterList.Preset.SourceProvider getProvider();

        void setProvider(MultiNoiseBiomeSourceParameterList.Preset.SourceProvider provider);

    }
}
