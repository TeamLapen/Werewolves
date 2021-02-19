package de.teamlapen.werewolves.player.skill;

import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.player.skills.VampirismSkill;
import de.teamlapen.werewolves.player.IWerewolfPlayer;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.UUID;
import java.util.function.Function;

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

    public static class AttributeSkill extends SimpleWerewolfSkill {

        private final UUID attribute;
        private final Attribute attributeType;
        private final AttributeModifier.Operation operation;
        private final Function<IWerewolfPlayer, Double> attribute_value;

        public AttributeSkill(String id, boolean desc, UUID attributeUUID, Attribute attributeType, AttributeModifier.Operation operation, Function<IWerewolfPlayer, Double> attribute_value) {
            super(id);
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
                attributes.applyPersistentModifier(new AttributeModifier(this.attribute, this.getRegistryName().toString() + "_skill", this.attribute_value.apply(player), this.operation));
            }
        }
    }
}
