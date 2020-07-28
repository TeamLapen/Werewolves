package de.teamlapen.werewolves.player.werewolf.skills;

import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.player.IWerewolfPlayer;
import de.teamlapen.werewolves.player.SimpleWerewolfSkill;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

import java.util.UUID;

public class ResistanceWerewolfSkill extends SimpleWerewolfSkill {

    private static final UUID RESISTANCE = UUID.fromString("cbe098f7-a52d-4756-bc4a-32d73aeb248b");

    @SuppressWarnings("deprecation")
    public ResistanceWerewolfSkill() {
        super("resistance", true);
    }

    @Override
    protected void onEnabled(IWerewolfPlayer player) {
        IAttributeInstance attributes = player.getRepresentingPlayer().getAttribute(SharedMonsterAttributes.ARMOR);
        if (attributes.getModifier(RESISTANCE) == null) {
            attributes.applyModifier(new AttributeModifier(RESISTANCE, "werewolf_damage_skill", WerewolvesConfig.BALANCE.SKILLS.resistance_amount.get(), AttributeModifier.Operation.ADDITION));
        }
    }

    @Override
    protected void onDisabled(IWerewolfPlayer player) {
        IAttributeInstance attributes = player.getRepresentingPlayer().getAttribute(SharedMonsterAttributes.ARMOR);
        attributes.removeModifier(RESISTANCE);
    }
}
