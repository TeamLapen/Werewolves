package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.potion.DrowsyPotion;
import de.teamlapen.werewolves.potion.TrueFormPotion;
import de.teamlapen.werewolves.potion.WerewolvesPotion;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@GameRegistry.ObjectHolder(REFERENCE.MODID)
public class ModPotions {

    public static final WerewolvesPotion wolfsbite = getNull();
    public static final TrueFormPotion true_form = getNull();
    public static final WerewolvesPotion invisible_speed = getNull();
    /**
     * use {@link DrowsyPotion#addDrowsyPotion(EntityPlayer)}
     */
    public static final DrowsyPotion drowsy = getNull();

    static void registerPotions(IForgeRegistry<Potion> registry) {
        registry.register(new WerewolvesPotion("wolfsbite", false, 0x6A0888, true).setIconIndex(2, 0));
        registry.register(new WerewolvesPotion("invisible_speed", false, 0x000000, false).registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "b52dc98e-5992-41af-a744-a32620c83692", 0.20000000298023224D, 2));
        registry.register(new TrueFormPotion().setIconIndex(2, 0));
        registry.register(new DrowsyPotion().setIconIndex(2,0));
    }
}
