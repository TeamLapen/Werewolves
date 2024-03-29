package de.teamlapen.werewolves.modcompat.guide;

import de.maxanier.guideapi.api.IPage;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.api.util.BookHelper;
import de.maxanier.guideapi.api.util.PageHelper;
import de.maxanier.guideapi.category.CategoryItemStack;
import de.maxanier.guideapi.page.PageHolderWithLinks;
import de.maxanier.guideapi.page.PageTextImage;
import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.vampirism.client.core.ModKeys;
import de.teamlapen.vampirism.modcompat.guide.EntryText;
import de.teamlapen.vampirism.modcompat.guide.VampirismGuideBookCategoriesEvent;
import de.teamlapen.vampirism.modcompat.guide.pages.PageTable;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModEntities;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModOils;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfLevelConf;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.*;

import static de.teamlapen.vampirism.modcompat.guide.GuideBook.translate;
import static de.teamlapen.vampirism.modcompat.guide.GuideBook.translateComponent;

@SuppressWarnings("CollectionAddAllCanBeReplacedWithConstructor")
public class WerewolvesGuideBook {

    private final static String IMAGE_BASE = "vampirismguide:textures/images/";

    public static void onVampirismGuideBookCategoriesEvent(VampirismGuideBookCategoriesEvent event) {
        BookHelper helper = new BookHelper.Builder(REFERENCE.MODID).build();
        int werewolfPos = -1;
        int itemPos = -1;
        int blockPos = -1;
        for (int i1 = 0; i1 < event.categories.size(); i1++) {
            if (werewolfPos == -1 && event.categories.get(i1).entries.keySet().stream().findAny().map(t -> t.getPath().contains("guide.vampirism.hunter")).orElse(false)) {
                werewolfPos = i1 + 1;
                continue;
            }
            if (itemPos == -1 && event.categories.get(i1).entries.keySet().stream().findAny().map(t -> t.getPath().contains("guide.vampirism.items")).orElse(false)) {
                itemPos = i1 + 1;
                continue;
            }
            if (blockPos == -1 && event.categories.get(i1).entries.keySet().stream().findAny().map(t -> t.getPath().contains("guide.vampirism.blocks")).orElse(false)) {
                blockPos = i1 + 1;
                continue;
            }
        }
        if (werewolfPos < 0) {
            werewolfPos = event.categories.size();
        }
        CategoryAbstract category = new CategoryItemStack(buildWerewolf(helper), translateComponent("guide.werewolves.entity.werewolf.title"), ModItems.LIVER.get().getDefaultInstance());
        helper.registerLinkablePages(Collections.singletonList(category));
        event.categories.add(werewolfPos, category);
        if (itemPos >= 0) {
            CategoryAbstract items = event.categories.get(itemPos);
            buildItems(items.entries, helper);
            helper.registerLinkablePages(Collections.singletonList(items));
        }
        if (blockPos >= 0) {
            CategoryAbstract blocks = event.categories.get(blockPos);
            buildBlocks(blocks.entries, helper);
            helper.registerLinkablePages(Collections.singletonList(blocks));
        }
    }

    private static Map<ResourceLocation, EntryAbstract> buildWerewolf(BookHelper helper) {
        Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<>();
        String base = "guide.werewolves.werewolf.";

        List<IPage> gettingStarted = new ArrayList<>();
        gettingStarted.addAll(PageHelper.pagesForLongText(translateComponent(base + "getting_started.become")));
        gettingStarted.addAll(PageHelper.pagesForLongText(translateComponent(base + "getting_started.as_werewolf")));
        gettingStarted.addAll(PageHelper.pagesForLongText(translateComponent(base + "getting_started.weakness")));
        gettingStarted.addAll(PageHelper.pagesForLongText(translateComponent(base + "getting_started.skills")));
        entries.put(new ResourceLocation(base + "getting_started"), new EntryText(gettingStarted, translateComponent(base + "getting_started")));

        List<IPage> levelingPages = new ArrayList<>();
        levelingPages.addAll(PageHelper.pagesForLongText(translateComponent(base + "leveling.intro")));
        String stone_altar = "§l" + loc(ModBlocks.STONE_ALTAR.get()) + "§r\n§o" + translate(base + "leveling.stone.reach") + "§r\n";
        stone_altar += translate(base + "leveling.stone.intro", loc(Items.FLINT_AND_STEEL), loc(Items.TORCH));
        levelingPages.addAll(helper.addLinks(PageHelper.pagesForLongText(Component.literal(stone_altar)), new ResourceLocation("guide.werewolves.blocks.stone_altar"), new ResourceLocation("guide.werewolves.blocks.stone_altar_fire_bowl")));
        levelingPages.addAll(PageHelper.pagesForLongText(translateComponent(base + "leveling.stone.structure", loc(ModBlocks.STONE_ALTAR.get()), loc(ModBlocks.STONE_ALTAR_FIRE_BOWL.get()), loc(ModBlocks.STONE_ALTAR.get()), loc(ModBlocks.STONE_ALTAR_FIRE_BOWL.get()), loc(Items.FLINT_AND_STEEL))));
        levelingPages.add(new PageTextImage(translateComponent(base + "leveling.stone.image1"), new ResourceLocation(IMAGE_BASE + "stone1.png"), false));
        String item = loc(ModItems.LIVER.get()) + ", " + loc(ModItems.CRACKED_BONE.get());
        levelingPages.addAll(helper.addLinks(PageHelper.pagesForLongText(translateComponent(base + "leveling.stone.items", item)), new ResourceLocation("guide.werewolves.items.liver"), new ResourceLocation("guide.werewolves.items.cracked_bone"), new ResourceLocation("guide.werewolves.werewolf.slayed_creatures")));
        PageTable.Builder requirementBuilder = new PageTable.Builder(4);
        requirementBuilder.addUnlocLine("text.vampirism.level_short", loc(ModItems.LIVER.get()), loc(ModItems.CRACKED_BONE.get()), "text.werewolves.slayed_creatures");
        for (int i = 2; i <= 14; i++) {
            WerewolfLevelConf.StoneAltarRequirement req = WerewolfLevelConf.getInstance().getStoneRequirement(i);
            //noinspection ConstantConditions
            requirementBuilder.addLine(i, req.liverAmount, req.bonesAmount, req.xpAmount);
        }
        requirementBuilder.setHeadline(translateComponent(base + "leveling.stone_req"));
        PageHolderWithLinks requirementTable = new PageHolderWithLinks(helper, requirementBuilder.build());
        requirementTable.addLink(new ResourceLocation("guide.werewolves.items.liver"));
        requirementTable.addLink(new ResourceLocation("guide.werewolves.items.cracked_bone"));
        requirementTable.addLink(new ResourceLocation("guide.werewolves.werewolf.slayed_creatures"));
        levelingPages.add(requirementTable);

        entries.put(new ResourceLocation(base + "leveling"), new EntryText(levelingPages, translateComponent(base + "leveling")));

        List<IPage> skillPages = new ArrayList<>();
        skillPages.addAll(PageHelper.pagesForLongText(translateComponent(base + "skills.text", UtilLib.translate(ModKeys.SUCK.saveString()))));
        skillPages.addAll(PageHelper.pagesForLongText(translateComponent(base + "skills.decision")));
        entries.put(new ResourceLocation(base + "skills"), new EntryText(skillPages, translateComponent(base + "skills")));

        List<IPage> werewolfLord = new ArrayList<>();
        werewolfLord.addAll(helper.addLinks(PageHelper.pagesForLongText(translateComponent(base + "lord.text", ModEntities.TASK_MASTER_WEREWOLF.get().getDescription(), WReference.WEREWOLF_FACTION.getLordTitle(1, false), WReference.WEREWOLF_FACTION.getLordTitle(WReference.WEREWOLF_FACTION.getHighestLordLevel(), false))), new ResourceLocation("guide.vampirism.entity.taskmaster")));
        PageTable.Builder lordTitleBuilder = new PageTable.Builder(2).setHeadline(translateComponent(base + "lord.titles"));
        lordTitleBuilder.addUnlocLine("text.vampirism.level", "text.vampirism.title");
        lordTitleBuilder.addLine(1, WReference.WEREWOLF_FACTION.getLordTitle(1, false).getString());
        lordTitleBuilder.addLine(2, WReference.WEREWOLF_FACTION.getLordTitle(2, false).getString());
        lordTitleBuilder.addLine(3, WReference.WEREWOLF_FACTION.getLordTitle(3, false).getString());
        lordTitleBuilder.addLine(4, WReference.WEREWOLF_FACTION.getLordTitle(4, false).getString());
        lordTitleBuilder.addLine(5, WReference.WEREWOLF_FACTION.getLordTitle(5, false).getString());
        werewolfLord.add(lordTitleBuilder.build());
        werewolfLord.addAll(helper.addLinks(PageHelper.pagesForLongText(translateComponent(base + "lord.minion", loc(ModItems.WEREWOLF_MINION_CHARM.get()), loc(ModItems.WEREWOLF_MINION_UPGRADE_SIMPLE.get()), loc(ModItems.WEREWOLF_MINION_UPGRADE_ENHANCED.get()), loc(ModItems.WEREWOLF_MINION_UPGRADE_SPECIAL.get()))), new ResourceLocation("guide.werewolves.items.werewolf_minion_charm")));
        werewolfLord.addAll(helper.addLinks(PageHelper.pagesForLongText(translateComponent("guide.vampirism.common.minion_control", translate(ModKeys.MINION.saveString()), translate("text.vampirism.minion.call_single"), translate("text.vampirism.minion.respawn")))));
        entries.put(new ResourceLocation(base + "lord"), new EntryText(werewolfLord, translateComponent(base + "lord")));

        List<IPage> unWerewolf = new ArrayList<>();
        unWerewolf.addAll(helper.addLinks(PageHelper.pagesForLongText(translateComponent(base + "un_werewolf.text", loc(ModItems.INJECTION_UN_WEREWOLF.get()), loc(ModBlocks.V.MED_CHAIR.get()))), new ResourceLocation("guide.vampirism.items.injection_empty"), new ResourceLocation("guide.vampirism.blocks.item_med_chair")));
        entries.put(new ResourceLocation(base + "un_werewolf"), new EntryText(unWerewolf, translateComponent(base + "un_werewolf")));

        List<IPage> creatures = new ArrayList<>();
        creatures.addAll(helper.addLinks(PageHelper.pagesForLongText(translateComponent(base + "slayed_creatures.text")), new ResourceLocation(base + "leveling")));
        entries.put(new ResourceLocation(base + "slayed_creatures"), new EntryText(creatures, translateComponent(base + "slayed_creatures")));
        return entries;
    }

    private static void buildItems(Map<ResourceLocation, EntryAbstract> entriesIn, BookHelper helper) {
        Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<>();
        helper.info(ModItems.LIVER.get()).setLinks(new ResourceLocation("guide.werewolves.werewolf.leveling")).build(entries);
        helper.info(ModItems.CRACKED_BONE.get()).setLinks(new ResourceLocation("guide.werewolves.werewolf.leveling")).build(entries);
        helper.info(ModItems.WEREWOLF_MINION_CHARM.get(), ModItems.WEREWOLF_MINION_UPGRADE_SIMPLE.get(), ModItems.WEREWOLF_MINION_UPGRADE_ENHANCED.get(), ModItems.WEREWOLF_MINION_UPGRADE_SPECIAL.get())
                .setFormats(loc(ModItems.WEREWOLF_MINION_CHARM.get()),
                        loc(ModItems.WEREWOLF_MINION_UPGRADE_SIMPLE.get()), ModItems.WEREWOLF_MINION_UPGRADE_SIMPLE.get().getMinLevel() + 1, ModItems.WEREWOLF_MINION_UPGRADE_SIMPLE.get().getMaxLevel() + 1,
                        loc(ModItems.WEREWOLF_MINION_UPGRADE_ENHANCED.get()), ModItems.WEREWOLF_MINION_UPGRADE_ENHANCED.get().getMinLevel() + 1, ModItems.WEREWOLF_MINION_UPGRADE_ENHANCED.get().getMaxLevel() + 1,
                        loc(ModItems.WEREWOLF_MINION_UPGRADE_SPECIAL.get()), ModItems.WEREWOLF_MINION_UPGRADE_SPECIAL.get().getMinLevel() + 1, ModItems.WEREWOLF_MINION_UPGRADE_SPECIAL.get().getMaxLevel() + 1,
                        translate(ModEntities.TASK_MASTER_WEREWOLF.get().getDescriptionId()))
                .setLinks(new ResourceLocation("guide.vampirism.entity.taskmaster"), new ResourceLocation("guide.vampirism.vampire.lord")).build(entries);
        helper.info(ModItems.WEREWOLF_TOOTH.get()).setFormats(ModItems.WEREWOLF_TOOTH.get().getDescription(), ModEntities.ALPHA_WEREWOLF.get().getDescription()).build(entries);
        helper.info(ModItems.BONE_NECKLACE.get(), ModItems.CHARM_BRACELET.get(), ModItems.DREAM_CATCHER.get()).useCustomEntryName().setKeyName("accessories").build(entries);
        entriesIn.putAll(entries);
    }

    private static void buildBlocks(Map<ResourceLocation, EntryAbstract> entriesIn, BookHelper helper) {
        Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<>();
        helper.info(ModBlocks.STONE_ALTAR.get()).setLinks(new ResourceLocation("guide.werewolves.werewolf.leveling")).recipes(new ResourceLocation(REFERENCE.MODID, "stone_altar")).build(entries);
        helper.info(ModBlocks.STONE_ALTAR_FIRE_BOWL.get()).setLinks(new ResourceLocation("guide.werewolves.werewolf.leveling")).recipes(new ResourceLocation(REFERENCE.MODID, "stone_altar_fire_bowl")).build(entries);
        entriesIn.putAll(entries);
    }

    private static String loc(Item i) {
        return UtilLib.translate(i.getDescriptionId());
    }

    private static String loc(Block i) {
        return UtilLib.translate(i.getDescriptionId());
    }
}
