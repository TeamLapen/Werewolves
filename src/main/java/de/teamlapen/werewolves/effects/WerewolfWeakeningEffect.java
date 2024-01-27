package de.teamlapen.werewolves.effects;

import de.teamlapen.vampirism.api.difficulty.IAdjustableLevel;
import de.teamlapen.vampirism.entity.player.LevelAttributeModifier;
import de.teamlapen.werewolves.api.entities.werewolf.IWerewolf;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class WerewolfWeakeningEffect extends WerewolvesEffect {

    private final List<Modifier> modifiers;

    public WerewolfWeakeningEffect(int color, List<Modifier> attributes) {
        super(MobEffectCategory.HARMFUL, color);
        this.modifiers = attributes;
    }

    @Override
    public void applyEffectTick(@Nonnull LivingEntity entityLivingBaseIn, int amplifier) {
        if (!Helper.isWerewolf(entityLivingBaseIn)) {
            entityLivingBaseIn.removeEffect(this);
        }
    }

    @Override
    public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
        for(Map.Entry<Attribute, AttributeModifier> entry : this.getAttributeModifiers(pLivingEntity).entrySet()) {
            AttributeInstance attributeinstance = pAttributeMap.getInstance(entry.getKey());
            if (attributeinstance != null) {
                AttributeModifier attributemodifier = entry.getValue();
                attributeinstance.removeModifier(attributemodifier);
                attributeinstance.addPermanentModifier(new AttributeModifier(attributemodifier.getId(), this.getDescriptionId() + " " + pAmplifier, this.getAttributeModifierValue(pAmplifier, attributemodifier), attributemodifier.getOperation()));
            }
        }
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
        for (Map.Entry<Attribute, AttributeModifier> entry : this.getAttributeModifiers(pLivingEntity).entrySet()) {
            AttributeInstance attributeinstance = pAttributeMap.getInstance(entry.getKey());
            if (attributeinstance != null) {
                attributeinstance.removeModifier(entry.getValue());
            }
        }
    }

    public @NotNull Map<Attribute, AttributeModifier> getAttributeModifiers(@Nullable LivingEntity entity) {
        Map<Attribute, AttributeModifier> map = new HashMap<>();
        if (entity == null || Helper.isWerewolf(entity)) {
            int level = 1;
            int maxLevel = 1;
            if (entity instanceof Player player) {
                WerewolfPlayer werewolf = WerewolfPlayer.get(player);
                level = werewolf.getLevel();
                maxLevel = werewolf.getMaxLevel();
            } else if (entity instanceof IWerewolf werewolf && werewolf instanceof IAdjustableLevel levelEntity) {
                level = levelEntity.getEntityLevel();
                maxLevel = levelEntity.getMaxEntityLevel();
            }
            double value = LevelAttributeModifier.calculateModifierValue(level, maxLevel, 0.2f, 1.3);
            this.modifiers.forEach(modifier -> map.put(modifier.attribute(), modifier.createModifier(value)));
        }
        return map;
    }

    @Override
    public @NotNull Map<Attribute, AttributeModifier> getAttributeModifiers() {
        return this.getAttributeModifiers(null);
    }

    protected record Modifier(Attribute attribute, UUID uuid, String name) {

        public AttributeModifier createModifier(double value) {
            return new AttributeModifier(this.uuid, this.name, -value, AttributeModifier.Operation.MULTIPLY_TOTAL);
        }
    }
}
