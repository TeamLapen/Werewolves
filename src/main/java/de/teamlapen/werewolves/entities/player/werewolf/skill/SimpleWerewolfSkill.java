package de.teamlapen.werewolves.entities.player.werewolf.skill;

import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.vampirism.api.entity.player.skills.ISkillType;
import de.teamlapen.vampirism.api.entity.player.skills.SkillType;
import de.teamlapen.vampirism.player.skills.VampirismSkill;
import de.teamlapen.werewolves.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

public class SimpleWerewolfSkill extends VampirismSkill<IWerewolfPlayer> {

    public SimpleWerewolfSkill() {
        this(false);
    }

    public SimpleWerewolfSkill(boolean desc) {
        if (desc) setHasDefaultDescription();
    }

    @Nonnull
    @Override
    public IPlayableFaction<?> getFaction() {
        return WReference.WEREWOLF_FACTION;
    }

    public SimpleWerewolfSkill defaultDescWithExtra(Supplier<ITextComponent> text) {
        this.setDescription(() -> new TranslationTextComponent(this.getTranslationKey() + ".desc").append("\n").append(text.get()));
        return this;
    }

    @SafeVarargs
    public final SimpleWerewolfSkill defaultDescWithExtra(TranslationTextComponent prefix, Supplier<ISkill>... skills) {
        this.setDescription(() -> {
            IFormattableTextComponent text = new TranslationTextComponent(this.getTranslationKey() + ".desc").append("\n").append(prefix.withStyle(TextFormatting.AQUA)).append(" ");
            text.append(Helper.joinComponents(", ", Arrays.stream(skills).map(skill -> new TranslationTextComponent(skill.get().getTranslationKey())).toArray(IFormattableTextComponent[]::new)).withStyle(TextFormatting.AQUA));
            return text;
        });
        return this;
    }

    @SafeVarargs
    public final SimpleWerewolfSkill defaultDescWithFormRequirement(Supplier<ISkill>... skills) {
        return defaultDescWithExtra(new TranslationTextComponent("text.werewolves.skills.only_applies"), skills);
    }

    @SafeVarargs
    public final SimpleWerewolfSkill defaultDescWithEnhancement(Supplier<ISkill>... skill) {
        return defaultDescWithExtra(new TranslationTextComponent("text.werewolves.skills.upgrade"), skill);
    }

    public static class LordWerewolfSkill extends SimpleWerewolfSkill {
        public LordWerewolfSkill(boolean desc) {
            super(desc);
        }

        public ISkillType getType() {
            return SkillType.LORD;
        }
    }

    public static class AttributeSkill extends SimpleWerewolfSkill {

        private final UUID attribute;
        private final Attribute attributeType;
        private final AttributeModifier.Operation operation;
        private final Function<IWerewolfPlayer, Double> attribute_value;

        public AttributeSkill(boolean desc, UUID attributeUUID, Attribute attributeType, AttributeModifier.Operation operation, Function<IWerewolfPlayer, Double> attribute_value) {
            super(desc);
            this.attribute = attributeUUID;
            this.attributeType = attributeType;
            this.operation = operation;
            this.attribute_value = attribute_value;
        }

        @Override
        protected void onDisabled(IWerewolfPlayer player) {
            ModifiableAttributeInstance attributes = player.getRepresentingPlayer().getAttribute(this.attributeType);
            attributes.removeModifier(this.attribute);
        }

        @Override
        protected void onEnabled(IWerewolfPlayer player) {
            ModifiableAttributeInstance attributes = player.getRepresentingPlayer().getAttribute(this.attributeType);
            if (attributes.getModifier(this.attribute) == null) {
                //noinspection ConstantConditions
                attributes.addPermanentModifier(new AttributeModifier(this.attribute, this.getRegistryName().toString() + "_skill", this.attribute_value.apply(player), this.operation));
            }
        }
    }
}
