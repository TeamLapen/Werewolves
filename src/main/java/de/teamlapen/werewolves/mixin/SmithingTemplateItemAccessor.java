package de.teamlapen.werewolves.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
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
    static ResourceLocation getEmptySlotHelmet() {
        throw new IllegalStateException("Mixin failed to apply");
    }

    @Accessor("EMPTY_SLOT_CHESTPLATE")
    static ResourceLocation getEmptySlotChestplate() {
        throw new IllegalStateException("Mixin failed to apply");
    }

    @Accessor("EMPTY_SLOT_LEGGINGS")
    static ResourceLocation getEmptySlotLeggings() {
        throw new IllegalStateException("Mixin failed to apply");
    }

    @Accessor("EMPTY_SLOT_BOOTS")
    static ResourceLocation getEmptySlotBoots() {
        throw new IllegalStateException("Mixin failed to apply");
    }
}
