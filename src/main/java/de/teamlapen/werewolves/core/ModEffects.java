package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.potions.DrownsyEffect;
import de.teamlapen.werewolves.potions.HowlingEffect;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.potion.Effect;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class ModEffects extends de.teamlapen.vampirism.core.ModEffects {

    public static final Effect drownsy = getNull();
    public static final Effect howling = getNull();

    static void registerEffects(IForgeRegistry<Effect> registry) {
        registry.register(new DrownsyEffect());
        registry.register(new HowlingEffect());
    }
}
