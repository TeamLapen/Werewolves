package de.teamlapen.werewolves.potions;

import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.core.WEffects;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.Permissions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraftforge.server.permission.PermissionAPI;

import javax.annotation.Nonnull;

public class TrueFormEffect extends WerewolvesEffect {

    public static void addEffect(PlayerEntity player, int duration) {
        if(PermissionAPI.hasPermission(player, Permissions.TRUE_FORM)) {
            player.addPotionEffect(new EffectInstance(WEffects.true_form, duration,0,false,false, false));
        }
    }

    public TrueFormEffect() {
        super("true_form", EffectType.NEUTRAL,  0x255F03);
    }

    @Override
    public void applyAttributesModifiersToEntity(@Nonnull LivingEntity entityLivingBaseIn, @Nonnull AbstractAttributeMap attributeMapIn, int amplifier) {
        if(entityLivingBaseIn instanceof PlayerEntity && Helper.isWerewolf((PlayerEntity)entityLivingBaseIn)) {
            FactionPlayerHandler.getOpt((PlayerEntity)entityLivingBaseIn).ifPresent(factionPH -> factionPH.getCurrentFactionPlayer().ifPresent(factionPlayer->((WerewolfPlayer)factionPlayer).getSpecialAttributes().trueForm = true));
        }
        super.applyAttributesModifiersToEntity(entityLivingBaseIn, attributeMapIn, amplifier);
    }

    @Override
    public void removeAttributesModifiersFromEntity(@Nonnull LivingEntity entityLivingBaseIn, @Nonnull AbstractAttributeMap attributeMapIn, int amplifier) {
        if(entityLivingBaseIn instanceof PlayerEntity && Helper.isWerewolf((PlayerEntity)entityLivingBaseIn)) {
            FactionPlayerHandler.getOpt((PlayerEntity)entityLivingBaseIn).ifPresent(factionPH -> factionPH.getCurrentFactionPlayer().ifPresent(factionPlayer->((WerewolfPlayer)factionPlayer).getSpecialAttributes().trueForm = false));
        }
        super.removeAttributesModifiersFromEntity(entityLivingBaseIn, attributeMapIn, amplifier);
    }

    @Override
    public void performEffect(@Nonnull LivingEntity entityLivingBaseIn, int amplifier) {
    }
}
