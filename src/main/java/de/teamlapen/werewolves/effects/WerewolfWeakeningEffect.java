package de.teamlapen.werewolves.effects;

import de.teamlapen.vampirism.api.difficulty.IAdjustableLevel;
import de.teamlapen.vampirism.entity.player.LevelAttributeModifier;
import de.teamlapen.werewolves.api.entities.werewolf.IWerewolf;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
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
import java.util.*;

public abstract class WerewolfWeakeningEffect extends WerewolvesEffect {

    private final List<Modifier> modifiers;

    protected WerewolfWeakeningEffect(int color, List<Modifier> attributes) {
        super(MobEffectCategory.HARMFUL, color);
        this.modifiers = attributes;
    }

    @Override
    public boolean applyEffectTick(@Nonnull LivingEntity entityLivingBaseIn, int amplifier) {
        return Helper.isWerewolf(entityLivingBaseIn);
    }

    @Override
    public void onEffectStarted(@NotNull LivingEntity entity, int amplifier) {
        this.addAttributeModifiers(entity, entity.getAttributes(), amplifier);
    }

    @Override
    public void addAttributeModifiers(@NotNull AttributeMap attributeMap, int amplifier) {
    }

    public void addAttributeModifiers(LivingEntity livingEntity, @NotNull AttributeMap attributeMap, int pAmplifier) {
        for(Map.Entry<Holder<Attribute>, AttributeTemplate> entry : this.getAttributeModifiers(livingEntity).entrySet()) {
            AttributeInstance attributeinstance = attributeMap.getInstance(entry.getKey());
            if (attributeinstance != null) {
                attributeinstance.removeModifier(entry.getValue().modifierId());
                attributeinstance.addPermanentModifier(entry.getValue().create(pAmplifier));
            }
        }
    }

    @Override
    public void removeAttributeModifiers(@NotNull AttributeMap attributeMap) {
        for(Map.Entry<Holder<Attribute>, ResourceLocation> entry : this.getAttributes().entrySet()) {
            AttributeInstance attributeinstance = attributeMap.getInstance(entry.getKey());
            if (attributeinstance != null) {
                attributeinstance.removeModifier(entry.getValue());
            }
        }
    }

    public Map<Holder<Attribute>, ResourceLocation> getAttributes() {
        Map<Holder<Attribute>, ResourceLocation> map = new HashMap<>();
        this.modifiers.forEach(modifier -> map.put(modifier.attribute(), modifier.id()));
        return map;
    }

    public @NotNull Map<Holder<Attribute>, AttributeTemplate> getAttributeModifiers(@Nullable LivingEntity entity) {
        Map<Holder<Attribute>, AttributeTemplate> map = new HashMap<>();
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
            this.modifiers.forEach(modifier -> map.put(modifier.attribute, modifier.createModifier(level, maxLevel)));
        }
        return map;
    }

    protected record Modifier(Holder<Attribute> attribute, ResourceLocation id, float maxModifier, int startingAmplifier) {

        Modifier(Holder<Attribute> attribute, ResourceLocation id, float maxModifier) {
            this(attribute, id, maxModifier, 0);
        }

        public AttributeTemplate createModifier(int level, int maxLevel) {
            double value = LevelAttributeModifier.calculateModifierValue(level, maxLevel, this.maxModifier, 1.3);

            return new AttributeTemplate() {
                @Override
                public @NotNull ResourceLocation modifierId() {
                    return id();
                }

                @Override
                public @NotNull AttributeModifier create(int pAmplifier) {
                    pAmplifier++;
                    pAmplifier = Math.max(0, pAmplifier - startingAmplifier);
                    return new AttributeModifier(id(), -pAmplifier * value, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
                }
            };
        }
    }

    public interface AttributeTemplate {

        ResourceLocation modifierId();

        AttributeModifier create(int amplifier);
    }
}
