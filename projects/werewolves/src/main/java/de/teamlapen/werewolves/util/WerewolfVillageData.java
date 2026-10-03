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
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Unit;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
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

    public static ItemStack createBanner(HolderLookup.Provider provider) {
        HolderLookup.RegistryLookup<BannerPattern> bannerPattern = provider.lookupOrThrow(Registries.BANNER_PATTERN);

        ItemStack itemStack = Items.YELLOW_BANNER.getDefaultInstance();
        itemStack.set(DataComponents.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE);
        itemStack.set(DataComponents.CUSTOM_NAME, Component.translatable("block.minecraft.ominous_banner").withStyle(ChatFormatting.GOLD));
        BannerPatternLayers.Builder builder = new BannerPatternLayers.Builder();
        builder.add(bannerPattern.getOrThrow(BannerPatterns.RHOMBUS_MIDDLE), DyeColor.BLACK)
                .add(bannerPattern.getOrThrow(BannerPatterns.TRIANGLE_BOTTOM), DyeColor.BLACK)
                .add(bannerPattern.getOrThrow(BannerPatterns.BORDER), DyeColor.BLACK)
                .add(bannerPattern.getOrThrow(BannerPatterns.TRIANGLES_BOTTOM), DyeColor.BROWN)
                .add(bannerPattern.getOrThrow(BannerPatterns.TRIANGLES_TOP), DyeColor.BROWN);
        itemStack.set(DataComponents.BANNER_PATTERNS, builder.build());
        return itemStack;
    }
}
