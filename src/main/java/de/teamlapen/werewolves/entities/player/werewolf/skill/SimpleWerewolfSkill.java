package de.teamlapen.werewolves.entities.player.werewolf.skill;

import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.vampirism.player.skills.VampirismSkill;
import de.teamlapen.werewolves.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

public class SimpleWerewolfSkill extends VampirismSkill<IWerewolfPlayer> {

    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    public SimpleWerewolfSkill(String id) {
        this(new ResourceLocation(REFERENCE.MODID, id), false);
    }

    @SuppressWarnings("DeprecatedIsStillUsed")
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

    public SimpleWerewolfSkill defaultDescWithExtra(Supplier<Component> text) {
        this.setDescription(() -> new TranslatableComponent(this.getTranslationKey() + ".desc").append("\n").append(text.get()));
        return this;
    }

    @SafeVarargs
    public final SimpleWerewolfSkill defaultDescWithExtra(TranslatableComponent prefix, Supplier<ISkill>... skills) {
        this.setDescription(() -> {
            MutableComponent text = new TranslatableComponent(this.getTranslationKey() + ".desc").append("\n").append(prefix.withStyle(ChatFormatting.AQUA)).append(" ");
            text.append(Helper.joinComponents(", ", Arrays.stream(skills).map(skill -> new TranslatableComponent(skill.get().getTranslationKey())).toArray(MutableComponent[]::new)).withStyle(ChatFormatting.AQUA));
            return text;
        });
        return this;
    }

    @SafeVarargs
    public final SimpleWerewolfSkill defaultDescWithFormRequirement(Supplier<ISkill>... skills) {
        return defaultDescWithExtra(new TranslatableComponent("text.werewolves.skills.only_applies"), skills);
    }

    @SafeVarargs
    public final SimpleWerewolfSkill defaultDescWithEnhancement(Supplier<ISkill>... skill) {
        return defaultDescWithExtra(new TranslatableComponent("text.werewolves.skills.upgrade"), skill);
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
            AttributeInstance attributes = player.getRepresentingPlayer().getAttribute(this.attributeType);
            attributes.removeModifier(this.attribute);
        }

        @Override
        protected void onEnabled(IWerewolfPlayer player) {
            AttributeInstance attributes = player.getRepresentingPlayer().getAttribute(this.attributeType);
            if (attributes.getModifier(this.attribute) == null) {
                //noinspection ConstantConditions
                attributes.addPermanentModifier(new AttributeModifier(this.attribute, this.getRegistryName().toString() + "_skill", this.attribute_value.apply(player), this.operation));
            }
        }
    }
}
