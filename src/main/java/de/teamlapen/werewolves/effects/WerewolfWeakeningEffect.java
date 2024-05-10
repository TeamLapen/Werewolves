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
import java.util.*;

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
        for(Map.Entry<Attribute, AttributeModifier> entry : this.getAttributeModifiers(pLivingEntity, pAmplifier).entrySet()) {
            AttributeInstance attributeinstance = pAttributeMap.getInstance(entry.getKey());
            if (attributeinstance != null) {
                AttributeModifier attributemodifier = entry.getValue();
                attributeinstance.removeModifier(attributemodifier.getId());
                attributeinstance.addPermanentModifier(attributemodifier);
            }
        }
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
        this.modifiers.forEach(modifier -> {
            AttributeInstance attributeinstance = pAttributeMap.getInstance(modifier.attribute);
            if (attributeinstance != null) {
                attributeinstance.removeModifier(modifier.uuid);
            }
        });
    }

    public @NotNull Map<Attribute, AttributeModifier> getAttributeModifiers(@Nullable LivingEntity entity, int pAmplifier) {
        Map<Attribute, AttributeModifier> map = new HashMap<>();
        if (entity == null || Helper.isWerewolf(entity)) {
            int level;
            int maxLevel;
            if (entity instanceof Player player) {
                WerewolfPlayer werewolf = WerewolfPlayer.get(player);
                level = werewolf.getLevel();
                maxLevel = werewolf.getMaxLevel();
            } else if (entity instanceof IWerewolf werewolf && werewolf instanceof IAdjustableLevel levelEntity) {
                level = levelEntity.getEntityLevel();
                maxLevel = levelEntity.getMaxEntityLevel();
            } else {
                level = 1;
                maxLevel = 1;
            }
            this.modifiers.forEach(modifier -> map.put(modifier.attribute, modifier.createModifier(level, maxLevel, pAmplifier)));
        }
        return map;
    }

    @Override
    public @NotNull Map<Attribute, AttributeModifier> getAttributeModifiers() {
        return this.getAttributeModifiers(null, 0);
    }

    protected static class Modifier {
        protected final Attribute attribute;
        protected final UUID uuid;
        protected final String name;
        protected final float maxModifier;

        protected Modifier(Attribute attribute, UUID uuid, String name, float maxModifier) {
            this.attribute = attribute;
            this.uuid = uuid;
            this.name = name;
            this.maxModifier = maxModifier;
        }

        public AttributeModifier createModifier(int level, int maxLevel, int amplifier) {
            double value = LevelAttributeModifier.calculateModifierValue(level, maxLevel, this.maxModifier, 1.3);
            return new AttributeModifier(this.uuid, this.name, - (amplifier + 1) * value, AttributeModifier.Operation.MULTIPLY_TOTAL);
        }
    }
}
