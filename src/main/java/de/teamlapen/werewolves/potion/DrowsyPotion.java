package de.teamlapen.werewolves.potion;

import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.config.Balance;
import de.teamlapen.werewolves.core.ModPotions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

public class DrowsyPotion extends WerewolvesPotion {

    public static void addDrowsyPotion(EntityPlayer player) {
        if (FactionPlayerHandler.get(player).canJoin(WReference.WEREWOLF_FACTION))
            player.addPotionEffect(new PotionEffect(ModPotions.drowsy, Balance.ge.DROWSYTIME * 1200));
    }

    public DrowsyPotion() {
        super("drowsy", true, 0xe012ef, true);
    }
}
