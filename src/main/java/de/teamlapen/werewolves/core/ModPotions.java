package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.player.werewolf.skills.WerewolfSkills;
import de.teamlapen.werewolves.potion.WerewolvesPotion;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@GameRegistry.ObjectHolder(REFERENCE.MODID)
public class ModPotions {

    public static final WerewolvesPotion wolfsbite = getNull();
    public static final WerewolvesPotion true_form = getNull();

    static void registerPotions(IForgeRegistry<Potion> registry) {
        registry.register(new WerewolvesPotion("wolfsbite", false, 0x6A0888).setIconIndex(2, 0));
        registry.register(new WerewolvesPotion("true_form", true, 0x6A0888) {
            @Override
            public void performEffect(net.minecraft.entity.EntityLivingBase entityLivingBaseIn, int amplifier) {
                if (entityLivingBaseIn instanceof EntityPlayer && Helper.isWerewolf(entityLivingBaseIn) && !WerewolfPlayer.get((EntityPlayer) entityLivingBaseIn).getSpecialAttributes().werewolf && WerewolfPlayer.get((EntityPlayer) entityLivingBaseIn).getSkillHandler().isSkillEnabled(WerewolfSkills.werewolf)) {
                    WerewolfPlayer.get((EntityPlayer) entityLivingBaseIn).transformWerewolf();
                }
            };

            public boolean isReady(int duration, int amplifier) {
                if (duration % 20 == 0) {
                    return true;
                }
                return false;
            };
        }.setIconIndex(2, 0));
    }
}
