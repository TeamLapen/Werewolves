package de.teamlapen.werewolves.misc.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.SmithingTemplateItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SmithingTemplateItem.class)
public interface SmithingTemplateItemAccessor {

    @Accessor("DESCRIPTION_FORMAT")
    static ChatFormatting getDescriptionFormat() {
        throw new IllegalStateException("Mixin failed to apply");
    }

    @Accessor("TITLE_FORMAT")
    static ChatFormatting getTitleFormat() {
        throw new IllegalStateException("Mixin failed to apply");
    }

    @Accessor("EMPTY_SLOT_HELMET")
    static Identifier getEmptySlotHelmet() {
        throw new IllegalStateException("Mixin failed to apply");
    }

    @Accessor("EMPTY_SLOT_CHESTPLATE")
    static Identifier getEmptySlotChestplate() {
        throw new IllegalStateException("Mixin failed to apply");
    }

    @Accessor("EMPTY_SLOT_LEGGINGS")
    static Identifier getEmptySlotLeggings() {
        throw new IllegalStateException("Mixin failed to apply");
    }

    @Accessor("EMPTY_SLOT_BOOTS")
    static Identifier getEmptySlotBoots() {
        throw new IllegalStateException("Mixin failed to apply");
    }
}
