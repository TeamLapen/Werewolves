package de.teamlapen.werewolves.util;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.List;

import static de.teamlapen.werewolves.mixin.SmithingTemplateItemAccessor.*;

public class ModSmithingTemplates {
    private static final Component WHITE_PELT_UPGRADE_APPLIES_TO = Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(REFERENCE.MODID, "smithing_template.white_pelt_upgrade.applies_to"))).withStyle(getDescriptionFormat());
    private static final Component WHITE_PELT_UPGRADE_INGREDIENTS = Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(REFERENCE.MODID, "smithing_template.white_pelt_upgrade.ingredients"))).withStyle(getDescriptionFormat());
    private static final Component WHITE_PELT_UPGRADE = Component.translatable(Util.makeDescriptionId("upgrade", new ResourceLocation(REFERENCE.MODID, "white_pelt_upgrade"))).withStyle(getTitleFormat());
    private static final Component WHITE_PELT_UPGRADE_BASE_SLOT_DESCRIPTION = Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(REFERENCE.MODID, "smithing_template.white_pelt_upgrade.base_slot_description")));
    private static final Component WHITE_PELT_UPGRADE_ADDITIONS_SLOT_DESCRIPTION = Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(REFERENCE.MODID, "smithing_template.white_pelt_upgrade.additions_slot_description")));
    private static final ResourceLocation EMPTY_SLOT_PELT = new ResourceLocation(REFERENCE.MODID, "item/empty_slot_pelt");
    private static final ResourceLocation EMPTY_SLOT_PELT_HELMET = new ResourceLocation(REFERENCE.MODID, "item/empty_slot_pelt_helmet");
    private static final ResourceLocation EMPTY_SLOT_PELT_CHESTPLATE = new ResourceLocation(REFERENCE.MODID, "item/empty_slot_pelt_chestplate");
    private static final ResourceLocation EMPTY_SLOT_PELT_LEGGINGS = new ResourceLocation(REFERENCE.MODID, "item/empty_slot_pelt_leggings");
    private static final ResourceLocation EMPTY_SLOT_PELT_BOOTS = new ResourceLocation(REFERENCE.MODID, "item/empty_slot_pelt_boots");

    private static List<ResourceLocation> createPeltUpgradeIconList() {
        return List.of(EMPTY_SLOT_PELT_HELMET, EMPTY_SLOT_PELT_CHESTPLATE, EMPTY_SLOT_PELT_LEGGINGS, EMPTY_SLOT_PELT_BOOTS);
    }

    private static List<ResourceLocation> createWhitePeltUpgradeMaterialList() {
        return List.of(EMPTY_SLOT_PELT);
    }

    public static SmithingTemplateItem createWhitePeltUpgradeTemplate() {
        return new SmithingTemplateItem(WHITE_PELT_UPGRADE_APPLIES_TO, WHITE_PELT_UPGRADE_INGREDIENTS, WHITE_PELT_UPGRADE, WHITE_PELT_UPGRADE_BASE_SLOT_DESCRIPTION, WHITE_PELT_UPGRADE_ADDITIONS_SLOT_DESCRIPTION, createPeltUpgradeIconList(), createWhitePeltUpgradeMaterialList());
    }
}
