package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.effects.BleedingEffect;
import de.teamlapen.werewolves.effects.HowlingEffect;
import de.teamlapen.werewolves.effects.LupusSanguinem;
import de.teamlapen.werewolves.effects.SilverEffect;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.potion.Effect;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class ModEffects extends de.teamlapen.vampirism.core.ModEffects {

    public static final LupusSanguinem lupus_sanguinem = getNull();
    public static final HowlingEffect howling = getNull();
    public static final SilverEffect silver = getNull();
    public static final BleedingEffect bleeding = getNull();

    static void registerEffects(IForgeRegistry<Effect> registry) {
        registry.register(new LupusSanguinem());
        registry.register(new HowlingEffect());
        registry.register(new SilverEffect());
        registry.register(new BleedingEffect());
    }
}
