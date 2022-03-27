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
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BannerPattern;

public class WerewolfVillageData {

    public static void werewolfVillage(IFactionVillageBuilder builder) {
        builder.captureEntities(() -> Lists.newArrayList(new CaptureEntityEntry(ModEntities.werewolf_beast, 5), new CaptureEntityEntry(ModEntities.werewolf_survivalist, 5)))
                .factionVillagerProfession(() -> ModVillage.werewolf_expert)
                .guardSuperClass(WerewolfBaseEntity.class)
                .taskMaster(() -> ModEntities.task_master_werewolf)
                .badOmenEffect(() -> ModEffects.bad_omen_werewolf)
                .totem(() -> ModBlocks.totem_top_werewolves_werewolf, () -> ModBlocks.totem_top_werewolves_werewolf_crafted)
                .banner(WerewolfVillageData::createBanner);
    }

    public static ItemStack createBanner() {
        ItemStack itemStack = new ItemStack(Items.YELLOW_BANNER);
        CompoundTag compoundNBT = itemStack.getOrCreateTagElement("BlockEntityTag");
        ListTag listNBT = new BannerPattern.Builder()
                .addPattern(BannerPattern.RHOMBUS_MIDDLE, DyeColor.BLACK)
                .addPattern(BannerPattern.TRIANGLE_BOTTOM, DyeColor.BLACK)
                .addPattern(BannerPattern.BORDER, DyeColor.BLACK)
                .addPattern(BannerPattern.TRIANGLES_BOTTOM, DyeColor.BROWN)
                .addPattern(BannerPattern.TRIANGLES_TOP, DyeColor.BROWN)
                .toListTag();
        compoundNBT.put("Patterns", listNBT);
        itemStack.hideTooltipPart(ItemStack.TooltipPart.ADDITIONAL);
        itemStack.setHoverName((new TranslatableComponent("block.minecraft.ominous_banner")).withStyle(ChatFormatting.GOLD));
        return itemStack;
    }
}
