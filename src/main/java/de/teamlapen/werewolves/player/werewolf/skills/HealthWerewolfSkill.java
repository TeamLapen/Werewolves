package de.teamlapen.werewolves.player.werewolf.skills;

import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.player.IWerewolfPlayer;
import de.teamlapen.werewolves.player.SimpleWerewolfSkill;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

import java.util.UUID;

public class HealthWerewolfSkill extends SimpleWerewolfSkill {
    private static final UUID HEALTH = UUID.fromString("96b2adbf-7e4b-49e1-b6e0-cc25d7e1309b");

    public HealthWerewolfSkill() {
        super("health", true);
    }

    @Override
    protected void onEnabled(IWerewolfPlayer player) {
        IAttributeInstance attributes = player.getRepresentingPlayer().getAttribute(SharedMonsterAttributes.MAX_HEALTH);
        if (attributes.getModifier(HEALTH) == null) {
            attributes.applyModifier(new AttributeModifier(HEALTH, "werewolf_health_action", WerewolvesConfig.BALANCE.SKILLS.HEALTH.amount.get(), AttributeModifier.Operation.ADDITION));
        }
    }

    @Override
    protected void onDisabled(IWerewolfPlayer player) {
        IAttributeInstance attributes = player.getRepresentingPlayer().getAttribute(SharedMonsterAttributes.MAX_HEALTH);
        attributes.removeModifier(HEALTH);
    }
}
