package de.teamlapen.werewolves.entities.player.werewolf.skill;

import com.mojang.datafixers.util.Either;
import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.vampirism.api.entity.player.skills.ISkillType;
import de.teamlapen.vampirism.entity.player.hunter.skills.HunterSkills;
import de.teamlapen.vampirism.entity.player.skills.VampirismSkill;
import de.teamlapen.vampirism.util.RegUtil;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.core.ModSkills;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class SimpleWerewolfSkill extends VampirismSkill<IWerewolfPlayer> {

    public SimpleWerewolfSkill() {
        super(Either.left(ModSkills.Trees.LEVEL), false);
    }

    public SimpleWerewolfSkill(int skillPointCost) {
        super(Either.left(ModSkills.Trees.LEVEL), skillPointCost, false);
    }

    public SimpleWerewolfSkill(boolean desc) {
        super(Either.left(ModSkills.Trees.LEVEL), desc);
    }

    public SimpleWerewolfSkill(int skillPoints, boolean desc) {
        super(Either.left(ModSkills.Trees.LEVEL), skillPoints, desc);
    }

    @Nonnull
    @Override
    public Optional<IPlayableFaction<?>> getFaction() {
        return Optional.of(WReference.WEREWOLF_FACTION);
    }

    @Override
    protected void onEnabled(IWerewolfPlayer player) {
        super.onEnabled(player);
        ((WerewolfPlayer) player).checkWerewolfFormModifier();
    }

    @Override
    protected void onDisabled(IWerewolfPlayer player) {
        super.onDisabled(player);
        ((WerewolfPlayer) player).checkWerewolfFormModifier();
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

    public static class LordWerewolfSkill extends VampirismSkill<IWerewolfPlayer> {
        public LordWerewolfSkill(int skillPointCost, boolean desc) {
            super(Either.left(ModSkills.Trees.LORD), skillPointCost, desc);
        }

        @Override
        public @NotNull Optional<IPlayableFaction<?>> getFaction() {
            return Optional.of(WReference.WEREWOLF_FACTION);
        }

        public LordWerewolfSkill setToggleActions(BiConsumer<IWerewolfPlayer, Boolean> action) {
            this.setToggleActions(w -> action.accept(w, true), w -> action.accept(w, false));
            return this;
        }
    }
    public static class AttributeSkill extends SimpleWerewolfSkill {

        private final Holder<Attribute> attributeType;
        private final AttributeModifier.Operation operation;
        private final Function<IWerewolfPlayer, Double> attribute_value;

        public AttributeSkill(boolean desc, Holder<Attribute> attributeType, AttributeModifier.Operation operation, Function<IWerewolfPlayer, Double> attribute_value) {
            super(desc);
            this.attributeType = attributeType;
            this.operation = operation;
            this.attribute_value = attribute_value;
        }

        @Override
        protected void onDisabled(IWerewolfPlayer player) {
            super.onDisabled(player);
            AttributeInstance attributes = player.asEntity().getAttribute(this.attributeType);
            attributes.removeModifier(RegUtil.id(this));
        }

        @Override
        protected void onEnabled(IWerewolfPlayer player) {
            super.onEnabled(player);
            AttributeInstance attributes = player.asEntity().getAttribute(this.attributeType);
            ResourceLocation id = RegUtil.id(this);
            if (attributes.getModifier(id) == null) {
                attributes.addPermanentModifier(new AttributeModifier(id, this.attribute_value.apply(player), this.operation));
            }
        }
    }
}
