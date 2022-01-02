package de.teamlapen.werewolves.entities.player.werewolf.skill;

import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.vampirism.player.skills.VampirismSkill;
import de.teamlapen.werewolves.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

public class SimpleWerewolfSkill extends VampirismSkill<IWerewolfPlayer> {
    @Deprecated
    public SimpleWerewolfSkill(String id) {
        this(new ResourceLocation(REFERENCE.MODID, id), false);
    }

    @Deprecated
    public SimpleWerewolfSkill(String id, boolean desc) {
        this(new ResourceLocation(REFERENCE.MODID, id), desc);
    }

    public SimpleWerewolfSkill(ResourceLocation id) {
        this(id, false);
    }

    public SimpleWerewolfSkill(ResourceLocation id, boolean desc) {
        this.setRegistryName(id);
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

    public SimpleWerewolfSkill defaultDescWithExtra(TranslationTextComponent prefix, Supplier<ISkill> skill) {
        this.setDescription(() -> new TranslationTextComponent(this.getTranslationKey() + ".desc").append("\n").append(prefix.withStyle(TextFormatting.AQUA)).append(" ").append(new TranslationTextComponent(skill.get().getTranslationKey()).withStyle(TextFormatting.AQUA)));
        return this;
    }

    public SimpleWerewolfSkill defaultDescWithFormRequirement(Supplier<ISkill> skill) {
        return defaultDescWithExtra(new TranslationTextComponent("text.werewolves.skills.only_applies"), skill);
    }

    public SimpleWerewolfSkill defaultDescWithEnhancement(Supplier<ISkill> skill) {
        return defaultDescWithExtra(new TranslationTextComponent("text.werewolves.skills.upgrade"), skill);
    }

    public static class AttributeSkill extends SimpleWerewolfSkill {

        private final UUID attribute;
        private final Attribute attributeType;
        private final AttributeModifier.Operation operation;
        private final Function<IWerewolfPlayer, Double> attribute_value;

        public AttributeSkill(String id, boolean desc, UUID attributeUUID, Attribute attributeType, AttributeModifier.Operation operation, Function<IWerewolfPlayer, Double> attribute_value) {
            super(id, desc);
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
