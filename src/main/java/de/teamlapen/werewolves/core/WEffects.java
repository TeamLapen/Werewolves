package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.potions.DrownsyEffect;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.potion.Effect;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class WEffects {

    public static final Effect drownsy = getNull();

    static void registerEffects(IForgeRegistry<Effect> registry) {
        registry.register(new DrownsyEffect());
    }
}
