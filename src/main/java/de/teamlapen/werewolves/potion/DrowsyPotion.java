package de.teamlapen.werewolves.potion;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.config.Balance;
import de.teamlapen.werewolves.core.ModPotions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

public class DrowsyPotion extends WerewolvesPotion {

    public static void addDrowsyPotion(EntityPlayer player) {
        player.addPotionEffect(new PotionEffect(ModPotions.drowsy, Balance.ge.DROWSYTIME));
        VampirismMod.proxy.renderScreenFullColor(Balance.ge.DROWSYTIME + 100, 0, 0x000000);
    }

    public DrowsyPotion() {
        super("drowsy", true, 0xe012ef, true);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        if (duration == 2)
            return true;
        return false;
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLivingBaseIn;
            EntityPlayer.SleepResult result = player.trySleep(player.getPosition());
            if (result == EntityPlayer.SleepResult.OTHER_PROBLEM && player.isPlayerSleeping()) {
                player.addPotionEffect(new PotionEffect(ModPotions.drowsy, 5));
            } else if (result.equals(EntityPlayer.SleepResult.NOT_SAFE)) {
                player.addPotionEffect(new PotionEffect(ModPotions.drowsy, 10));
            }
            WerewolvesMod.log.t("sleeping %s", result);
        }
    }
}
