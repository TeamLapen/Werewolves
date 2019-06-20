package de.teamlapen.werewolves.potion;

import de.teamlapen.werewolves.config.Balance;
import de.teamlapen.werewolves.core.ModPotions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

public class DrowsyPotion extends WerewolvesPotion {

    public static void addDrowsyPotion(EntityPlayer player) {
        player.addPotionEffect(new PotionEffect(ModPotions.drowsy, Balance.ge.DROWSYTIME * 1200));
    }

    public DrowsyPotion() {
        super("drowsy", true, 0xe012ef, true);
    }
}
