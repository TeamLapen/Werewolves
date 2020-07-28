package de.teamlapen.werewolves.player.werewolf.skills;

import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.player.IWerewolfPlayer;
import de.teamlapen.werewolves.player.SimpleWerewolfSkill;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

import java.util.UUID;

public class DamageWerewolfSkill extends SimpleWerewolfSkill {

    private static final UUID DAMAGE = UUID.fromString("33227645-38c8-467e-8c1c-c15215361935");

    @SuppressWarnings("deprecation")
    public DamageWerewolfSkill() {
        super("damage", true);
    }

    @Override
    protected void onEnabled(IWerewolfPlayer player) {
        IAttributeInstance attributes = player.getRepresentingPlayer().getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        if (attributes.getModifier(DAMAGE) == null) {
            attributes.applyModifier(new AttributeModifier(DAMAGE, "werewolf_damage_skill", WerewolvesConfig.BALANCE.SKILLS.damage_amount.get(), AttributeModifier.Operation.ADDITION));
        }
    }

    @Override
    protected void onDisabled(IWerewolfPlayer player) {
        IAttributeInstance attributes = player.getRepresentingPlayer().getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        attributes.removeModifier(DAMAGE);
    }
}
