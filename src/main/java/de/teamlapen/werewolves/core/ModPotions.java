package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.potion.WerewolvesPotion;
import de.teamlapen.werewolves.util.REFERENCE;

import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@GameRegistry.ObjectHolder(REFERENCE.MODID)
public class ModPotions {

    public static final WerewolvesPotion wolfsbite = getNull();

    static void registerPotions(IForgeRegistry<Potion> registry) {
        registry.register(new WerewolvesPotion("wolfsbite", false, 0x6A0888).setIconIndex(2, 0));
    }
}
