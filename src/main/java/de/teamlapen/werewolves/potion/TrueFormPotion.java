package de.teamlapen.werewolves.potion;

import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.player.werewolf.actions.WerewolfActions;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.entity.player.EntityPlayer;

public class TrueFormPotion extends WerewolvesPotion {

    public TrueFormPotion() {
        super("true_form", true, 0x6A0888, true);
    }

    @Override
    public void applyAttributesModifiersToEntity(net.minecraft.entity.EntityLivingBase entityLivingBaseIn, net.minecraft.entity.ai.attributes.AbstractAttributeMap attributeMapIn, int amplifier) {
        super.applyAttributesModifiersToEntity(entityLivingBaseIn, attributeMapIn, amplifier);
        if (entityLivingBaseIn instanceof EntityPlayer) {
            if (Helper.isWerewolf(entityLivingBaseIn) && WerewolfPlayer.get((EntityPlayer) entityLivingBaseIn).getActionHandler().isActionUnlocked(WerewolfActions.werewolf_werewolf)) {
                if (!WerewolfPlayer.get((EntityPlayer) entityLivingBaseIn).getActionHandler().isActionActive(WerewolfActions.werewolf_werewolf)) {
                    WerewolfPlayer.get((EntityPlayer) entityLivingBaseIn).getActionHandler().toggleAction(WerewolfActions.werewolf_werewolf);
                }
                WerewolfPlayer.get((EntityPlayer) entityLivingBaseIn).getSpecialAttributes().transformable = false;
                WerewolvesMod.log.t("True Form: %s", WerewolfPlayer.get((EntityPlayer) entityLivingBaseIn).getSpecialAttributes().transformable);
            }
        }
    }

    @Override
    public void removeAttributesModifiersFromEntity(net.minecraft.entity.EntityLivingBase entityLivingBaseIn, net.minecraft.entity.ai.attributes.AbstractAttributeMap attributeMapIn, int amplifier) {
        super.removeAttributesModifiersFromEntity(entityLivingBaseIn, attributeMapIn, amplifier);
        if (entityLivingBaseIn instanceof EntityPlayer) {
            WerewolfPlayer.get((EntityPlayer) entityLivingBaseIn).getSpecialAttributes().transformable = true;
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        if (duration % 20 == 0)
            return true;
        return false;
    }
}
