package de.teamlapen.werewolves.util;

import com.google.common.collect.Lists;
import de.teamlapen.vampirism.api.entity.CaptureEntityEntry;
import de.teamlapen.vampirism.api.entity.factions.IFactionVillageBuilder;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModEffects;
import de.teamlapen.werewolves.core.ModEntities;
import de.teamlapen.werewolves.core.ModVillage;
import de.teamlapen.werewolves.entities.werewolf.WerewolfBaseEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatterns;

public class WerewolfVillageData {

    public static void werewolfVillage(IFactionVillageBuilder builder) {
        builder.captureEntities(Lists.newArrayList(new CaptureEntityEntry<>(ModEntities.WEREWOLF_BEAST, 5), new CaptureEntityEntry<>(ModEntities.WEREWOLF_SURVIVALIST, 5)))
                .factionVillagerProfession(ModVillage.WEREWOLF_EXPERT)
                .guardSuperClass(WerewolfBaseEntity.class)
                .taskMaster(ModEntities.TASK_MASTER_WEREWOLF)
                .badOmenEffect(ModEffects.BAD_OMEN_WEREWOLF)
                .totem(ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF, ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF_CRAFTED)
                .banner(WerewolfVillageData::createBanner);
    }

    public static ItemStack createBanner() {
        ItemStack itemStack = Items.YELLOW_BANNER.getDefaultInstance();
        CompoundTag compoundNBT = itemStack.getOrCreateTagElement("BlockEntityTag");
        ListTag listNBT = new BannerPattern.Builder()
                .addPattern(BannerPatterns.RHOMBUS_MIDDLE, DyeColor.BLACK)
                .addPattern(BannerPatterns.TRIANGLE_BOTTOM, DyeColor.BLACK)
                .addPattern(BannerPatterns.BORDER, DyeColor.BLACK)
                .addPattern(BannerPatterns.TRIANGLES_BOTTOM, DyeColor.BROWN)
                .addPattern(BannerPatterns.TRIANGLES_TOP, DyeColor.BROWN)
                .toListTag();
        compoundNBT.put("Patterns", listNBT);
        itemStack.hideTooltipPart(ItemStack.TooltipPart.ADDITIONAL);
        itemStack.setHoverName((Component.translatable("block.minecraft.ominous_banner")).withStyle(ChatFormatting.GOLD));
        return itemStack;
    }
}
