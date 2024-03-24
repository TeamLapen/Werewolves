package de.teamlapen.werewolves.effects;

import de.teamlapen.vampirism.api.difficulty.IAdjustableLevel;
import de.teamlapen.vampirism.entity.player.LevelAttributeModifier;
import de.teamlapen.werewolves.api.entities.werewolf.IWerewolf;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.world.effect.AttributeModifierTemplate;
import net.minecraft.world.effect.MobEffect;
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

    protected WerewolfWeakeningEffect(int color, List<Modifier> attributes) {
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
    public void onEffectStarted(@NotNull LivingEntity entity, int amplifier) {
        if (!Helper.isWerewolf(entity)) {
            entity.removeEffect(this);
        } else {
            this.addAttributeModifiers(entity, entity.getAttributes(), amplifier);
        }
    }

    @Override
    public void addAttributeModifiers(@NotNull AttributeMap attributeMap, int amplifier) {
    }

    public void addAttributeModifiers(LivingEntity livingEntity, @NotNull AttributeMap attributeMap, int pAmplifier) {
        for(Map.Entry<Attribute, AttributeModifierTemplate> entry : this.getAttributeModifiers(livingEntity).entrySet()) {
            AttributeInstance attributeinstance = attributeMap.getInstance(entry.getKey());
            if (attributeinstance != null) {
                attributeinstance.removeModifier(entry.getValue().getAttributeModifierId());
                attributeinstance.addPermanentModifier(entry.getValue().create(pAmplifier));
            }
        }
    }

    @Override
    public void removeAttributeModifiers(@NotNull AttributeMap attributeMap) {
        for(Map.Entry<Attribute, UUID> entry : this.getAttributes().entrySet()) {
            AttributeInstance attributeinstance = attributeMap.getInstance(entry.getKey());
            if (attributeinstance != null) {
                attributeinstance.removeModifier(entry.getValue());
            }
        }
    }

    public Map<Attribute, UUID> getAttributes() {
        Map<Attribute, UUID> map = new HashMap<>();
        this.modifiers.forEach(modifier -> map.put(modifier.attribute(), modifier.uuid()));
        return map;
    }

    public @NotNull Map<Attribute, AttributeModifierTemplate> getAttributeModifiers(@Nullable LivingEntity entity) {
        Map<Attribute, AttributeModifierTemplate> map = new HashMap<>();
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

    protected record Modifier(Attribute attribute, UUID uuid, String name) {

        public AttributeModifierTemplate createModifier(double value) {
            return new AttributeModifierTemplate() {
                @Override
                public @NotNull UUID getAttributeModifierId() {
                    return uuid;
                }

                @Override
                public @NotNull AttributeModifier create(int amplifier) {
                    return new AttributeModifier(uuid(), name(), -value, AttributeModifier.Operation.MULTIPLY_TOTAL);
                }
            };
        }
    }
}
