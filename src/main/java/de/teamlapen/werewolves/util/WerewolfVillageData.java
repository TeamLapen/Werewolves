package de.teamlapen.werewolves.util;

import com.google.common.collect.Lists;
import de.teamlapen.vampirism.api.entity.CaptureEntityEntry;
import de.teamlapen.vampirism.api.entity.factions.IFactionVillageBuilder;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModEffects;
import de.teamlapen.werewolves.core.ModEntities;
import de.teamlapen.werewolves.core.ModVillage;
import de.teamlapen.werewolves.entities.werewolf.WerewolfBaseEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class WerewolfVillageData {

    public static void werewolfVillage(IFactionVillageBuilder builder) {
        builder.captureEntities(() -> Lists.newArrayList(new CaptureEntityEntry(ModEntities.WEREWOLF_BEAST.get(), 5), new CaptureEntityEntry(ModEntities.WEREWOLF_SURVIVALIST.get(), 5)))
                .factionVillagerProfession(ModVillage.WEREWOLF_EXPERT)
                .guardSuperClass(WerewolfBaseEntity.class)
                .taskMaster(ModEntities.TASK_MASTER_WEREWOLF::get)
                .badOmenEffect(ModEffects.BAD_OMEN_WEREWOLF)
                .totem(ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF::get, ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF_CRAFTED::get)
                .banner(WerewolfVillageData::createBanner);
    }

    public static ItemStack createBanner() {
        ItemStack itemStack = new ItemStack(Items.YELLOW_BANNER);
        CompoundNBT compoundNBT = itemStack.getOrCreateTagElement("BlockEntityTag");
        ListNBT listNBT = new BannerPattern.Builder()
                .addPattern(BannerPattern.RHOMBUS_MIDDLE, DyeColor.BLACK)
                .addPattern(BannerPattern.TRIANGLE_BOTTOM, DyeColor.BLACK)
                .addPattern(BannerPattern.BORDER, DyeColor.BLACK)
                .addPattern(BannerPattern.TRIANGLES_BOTTOM, DyeColor.BROWN)
                .addPattern(BannerPattern.TRIANGLES_TOP, DyeColor.BROWN)
                .toListTag();
        compoundNBT.put("Patterns", listNBT);
        itemStack.hideTooltipPart(ItemStack.TooltipDisplayFlags.ADDITIONAL);
        itemStack.setHoverName((new TranslationTextComponent("block.minecraft.ominous_banner")).withStyle(TextFormatting.GOLD));
        return itemStack;
    }
}
