package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.player.werewolf.actions.WerewolfActions;
import de.teamlapen.werewolves.potion.DrowsyPotion;
import de.teamlapen.werewolves.potion.WerewolvesPotion;
import de.teamlapen.werewolves.util.Helper;
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
    public static final WerewolvesPotion true_form = getNull();
    public static final WerewolvesPotion unvisible_speed = getNull();
    public static final DrowsyPotion drowsy = getNull();

    static void registerPotions(IForgeRegistry<Potion> registry) {
        registry.register(new WerewolvesPotion("wolfsbite", false, 0x6A0888, true).setIconIndex(2, 0));
        registry.register(new WerewolvesPotion("true_form", true, 0x6A0888, true) {

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
        }.setIconIndex(2, 0));
        registry.register(new WerewolvesPotion("unvisible_speed", false, 0x000000, false) {

        }.registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "b52dc98e-5992-41af-a744-a32620c83692", 0.20000000298023224D, 2));
        registry.register(new DrowsyPotion());
    }
}
