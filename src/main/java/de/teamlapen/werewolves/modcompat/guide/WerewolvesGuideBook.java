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
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfLevelConf;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

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
                werewolfPos = i1+1;
                continue;
            }
            if (itemPos == -1 && event.categories.get(i1).entries.keySet().stream().findAny().map(t -> t.getPath().contains("guide.vampirism.items")).orElse(false)){
                itemPos = i1 +1;
                continue;
            }
            if (blockPos == -1 && event.categories.get(i1).entries.keySet().stream().findAny().map(t -> t.getPath().contains("guide.vampirism.blocks")).orElse(false)){
                blockPos = i1 +1;
                continue;
            }
        }
        if (werewolfPos < 0) {
            werewolfPos = event.categories.size();
        }
        CategoryAbstract category = new CategoryItemStack(buildWerewolf(helper), translateComponent("guide.werewolves.entity.werewolf.title"), new ItemStack(ModItems.liver));
        helper.registerLinkablePages(Collections.singletonList(category));
        event.categories.add(werewolfPos, category);
        if (itemPos >=0) {
            CategoryAbstract items = event.categories.get(itemPos);
            buildItems(items.entries, helper);
            helper.registerLinkablePages(Collections.singletonList(items));
        }
        if (blockPos >=0) {
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
        String stone_altar = "§l" + loc(ModBlocks.stone_altar) + "§r\n§o" + translate(base + "leveling.stone.reach") + "§r\n";
        stone_altar += translate(base + "leveling.stone.intro", loc(Items.FLINT_AND_STEEL), loc(Items.TORCH));
        levelingPages.addAll(helper.addLinks(PageHelper.pagesForLongText(new StringTextComponent(stone_altar)), new ResourceLocation("guide.werewolves.blocks.stone_altar"), new ResourceLocation("guide.werewolves.blocks.stone_altar_fire_bowl")));
        levelingPages.addAll(PageHelper.pagesForLongText(translateComponent(base + "leveling.stone.structure", loc(ModBlocks.stone_altar), loc(ModBlocks.stone_altar_fire_bowl),loc(ModBlocks.stone_altar), loc(ModBlocks.stone_altar_fire_bowl), loc(Items.FLINT_AND_STEEL))));
        levelingPages.add(new PageTextImage(translateComponent(base + "leveling.stone.image1"), new ResourceLocation(IMAGE_BASE + "stone1.png"), false));
        String item = loc(ModItems.liver) + ", " + loc(ModItems.cracked_bone);
        levelingPages.addAll(helper.addLinks(PageHelper.pagesForLongText(translateComponent(base + "leveling.stone.items", item)), new ResourceLocation("guide.werewolves.items.liver"), new ResourceLocation("guide.werewolves.items.bone")));
        PageTable.Builder requirementBuilder = new PageTable.Builder(3);
        requirementBuilder.addUnlocLine("text.vampirism.level_short", loc(ModItems.liver), loc(ModItems.cracked_bone));
        for (int i = 2; i <= 14; i++) {
            //noinspection ConstantConditions
            requirementBuilder.addLine(i, WerewolfLevelConf.getInstance().getStoneRequirement(i).liverAmount,WerewolfLevelConf.getInstance().getStoneRequirement(i).bonesAmount);
        }
        requirementBuilder.setHeadline(translateComponent(base + "leveling.stone_req"));
        PageHolderWithLinks requirementTable = new PageHolderWithLinks(helper, requirementBuilder.build());
        requirementTable.addLink(new ResourceLocation("guide.werewolves.items.liver"));
        requirementTable.addLink(new ResourceLocation("guide.werewolves.items.bone"));
        levelingPages.add(requirementTable);

        entries.put(new ResourceLocation(base + "leveling"), new EntryText(levelingPages, translateComponent(base + "leveling")));

        List<IPage> skillPages = new ArrayList<>();
        skillPages.addAll(PageHelper.pagesForLongText(translateComponent(base + "skills.text", UtilLib.translate(ModKeys.getKeyBinding(ModKeys.KEY.SKILL).saveString()))));
        skillPages.addAll(PageHelper.pagesForLongText(translateComponent(base + "skills.decision")));
        entries.put(new ResourceLocation(base + "skills"), new EntryText(skillPages, translateComponent(base + "skills")));

        List<IPage> werewolfLord = new ArrayList<>();
        werewolfLord.addAll(PageHelper.pagesForLongText(translateComponent(base + "lord.text")));
        entries.put(new ResourceLocation(base + "lord"), new EntryText(werewolfLord, translateComponent(base + "lord")));

        List<IPage> unWerewolf = new ArrayList<>();
        unWerewolf.addAll(helper.addLinks(PageHelper.pagesForLongText(translateComponent(base + "un_werewolf.text", loc(ModItems.injection_un_werewolf), loc(ModBlocks.V.med_chair))), new ResourceLocation("guide.vampirism.items.injection_empty"), new ResourceLocation("guide.vampirism.blocks.item_med_chair")));
        entries.put(new ResourceLocation(base + "un_werewolf"), new EntryText(unWerewolf, translateComponent(base + "un_werewolf")));
        return entries;
    }

    private static void buildItems(Map<ResourceLocation, EntryAbstract> entriesIn, BookHelper helper) {
        Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<>();
        helper.info(ModItems.liver).setLinks(new ResourceLocation("guide.werewolves.werewolf.leveling")).build(entries);
        helper.info(ModItems.cracked_bone).setLinks(new ResourceLocation("guide.werewolves.werewolf.leveling")).build(entries);
        entriesIn.putAll(entries);
    }

    private static void buildBlocks(Map<ResourceLocation, EntryAbstract> entriesIn, BookHelper helper) {
        Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<>();
        helper.info(ModBlocks.stone_altar).setLinks(new ResourceLocation("guide.werewolves.werewolf.leveling")).recipes(new ResourceLocation(REFERENCE.MODID,"stone_altar")).build(entries);
        helper.info(ModBlocks.stone_altar_fire_bowl).setLinks(new ResourceLocation("guide.werewolves.werewolf.leveling")).recipes(new ResourceLocation(REFERENCE.MODID,"stone_altar_fire_bowl")).build(entries);
        entriesIn.putAll(entries);
    }

    private static String loc(Item i) {
        return UtilLib.translate(i.getDescriptionId());
    }

    private static String loc(Block i) {
        return UtilLib.translate(i.getDescriptionId());
    }
}
