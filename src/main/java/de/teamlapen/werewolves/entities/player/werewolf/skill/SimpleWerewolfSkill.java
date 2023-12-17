package de.teamlapen.werewolves.entities.player.werewolf.skill;

import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.vampirism.api.entity.player.skills.ISkillType;
import de.teamlapen.vampirism.api.entity.player.skills.SkillType;
import de.teamlapen.vampirism.entity.player.skills.VampirismSkill;
import de.teamlapen.vampirism.util.RegUtil;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

public class SimpleWerewolfSkill extends VampirismSkill<IWerewolfPlayer> {

    public SimpleWerewolfSkill() {
        super(false);
    }

    public SimpleWerewolfSkill(int skillPointCost) {
        super(skillPointCost, false);
    }

    public SimpleWerewolfSkill(boolean desc) {
        super(desc);
    }

    public SimpleWerewolfSkill(int skillPoints, boolean desc) {
        super(skillPoints, desc);
    }

    @Nonnull
    @Override
    public Optional<IPlayableFaction<?>> getFaction() {
        return Optional.of(WReference.WEREWOLF_FACTION);
    }

    public SimpleWerewolfSkill defaultDescWithExtra(Supplier<Component> text) {
        this.setDescription(() -> Component.translatable(this.getTranslationKey() + ".desc").append("\n").append(text.get()));
        return this;
    }

    @SafeVarargs
    public final SimpleWerewolfSkill defaultDescWithExtra(MutableComponent prefix, Supplier<ISkill<?>>... skills) {
        this.setDescription(() -> {
            MutableComponent text = Component.translatable(this.getTranslationKey() + ".desc").append("\n").append(prefix.withStyle(ChatFormatting.AQUA)).append(" ");
            text.append(Helper.joinComponents(", ", Arrays.stream(skills).map(skill -> Component.translatable(skill.get().getTranslationKey())).toArray(MutableComponent[]::new)).withStyle(ChatFormatting.AQUA));
            return text;
        });
        return this;
    }

    @SafeVarargs
    public final SimpleWerewolfSkill defaultDescWithFormRequirement(Supplier<ISkill<?>>... skills) {
        return defaultDescWithExtra(Component.translatable("text.werewolves.skills.only_applies"), skills);
    }

    @SafeVarargs
    public final SimpleWerewolfSkill defaultDescWithEnhancement(Supplier<ISkill<?>>... skill) {
        return defaultDescWithExtra(Component.translatable("text.werewolves.skills.upgrade"), skill);
    }

    public static class LordWerewolfSkill extends SimpleWerewolfSkill {
        public LordWerewolfSkill(boolean desc) {
            super((desc));
        }

        @Override
        public @NotNull ISkillType getType() {
            return SkillType.LORD;
        }
    }
    public static class AttributeSkill extends SimpleWerewolfSkill {

        private final UUID attribute;
        private final Attribute attributeType;
        private final AttributeModifier.Operation operation;
        private final Function<IWerewolfPlayer, Double> attribute_value;

        public AttributeSkill(String id, boolean desc, UUID attributeUUID, Attribute attributeType, AttributeModifier.Operation operation, Function<IWerewolfPlayer, Double> attribute_value) {
            super(desc);
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
                attributes.addPermanentModifier(new AttributeModifier(this.attribute, RegUtil.id(this).toString() + "_skill", this.attribute_value.apply(player), this.operation));
            }
        }
    }
}
