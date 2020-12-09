package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.effects.*;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.potion.Effect;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class ModEffects extends de.teamlapen.vampirism.core.ModEffects {

    public static final LupusSanguinemEffect lupus_sanguinem = getNull();
    public static final HowlingEffect howling = getNull();
    public static final SilverEffect silver = getNull();
    public static final BleedingEffect bleeding = getNull();
    public static final UnWerewolfEffect un_werewolf = getNull();

    static void registerEffects(IForgeRegistry<Effect> registry) {
        registry.register(new LupusSanguinemEffect());
        registry.register(new HowlingEffect());
        registry.register(new SilverEffect());
        registry.register(new BleedingEffect());
        registry.register(new UnWerewolfEffect());
    }
}
