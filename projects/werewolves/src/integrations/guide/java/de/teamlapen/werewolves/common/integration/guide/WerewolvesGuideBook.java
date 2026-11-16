package de.teamlapen.werewolves.common.integration.guide;

import de.maxanier.guideapi.api.category.CategoryBase;
import de.maxanier.guideapi.api.category.CategoryItemStack;
import de.maxanier.guideapi.api.entry.EntryBase;
import de.maxanier.guideapi.api.pages.IPage;
import de.maxanier.guideapi.api.pages.PageHolderWithLinks;
import de.maxanier.guideapi.api.pages.PageTextImage;
import de.maxanier.guideapi.api.util.BookHelper;
import de.maxanier.guideapi.api.util.PageHelper;
import de.teamlapen.faction.api.factions.IPlayableFaction;
import de.teamlapen.faction.common.core.FactionKeys;
import de.teamlapen.vampirism.common.integration.guide.EntryText;
import de.teamlapen.vampirism.common.integration.guide.VampirismGuideBookCategoriesEvent;
import de.teamlapen.vampirism.common.integration.guide.pages.PageTable;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.client.core.ModKeys;
import de.teamlapen.werewolves.common.core.ModBlocks;
import de.teamlapen.werewolves.common.core.ModEntities;
import de.teamlapen.werewolves.common.core.ModFactions;
import de.teamlapen.werewolves.common.core.ModItems;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfLevelConf;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WerewolfLordTitles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.*;

import static de.teamlapen.vampirism.common.integration.guide.GuideBook.translate;
import static de.teamlapen.vampirism.common.integration.guide.GuideBook.translateComponent;

@SuppressWarnings("CollectionAddAllCanBeReplacedWithConstructor")
public class WerewolvesGuideBook {

    private static final WerewolfLordTitles LORD_TITLES = new WerewolfLordTitles();
    private final static String IMAGE_BASE = "vampirismguide:textures/images/";

    public static void onVampirismGuideBookCategoriesEvent(VampirismGuideBookCategoriesEvent event) {
        RegistryAccess access = Optional.ofNullable(Minecraft.getInstance().getConnection()).map(ClientPacketListener::registryAccess).map(RegistryAccess.class::cast).orElse(RegistryAccess.EMPTY);
        BookHelper helper = new BookHelper.Builder(REFERENCE.MODID).build(access);
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
        CategoryBase category = new CategoryItemStack(buildWerewolf(helper), translateComponent("guide.werewolves.entity.werewolf.title"), ModItems.LIVER.get().getDefaultInstance());
        helper.registerLinkablePages(Collections.singletonList(category));
        event.categories.add(werewolfPos, category);
        if (itemPos >= 0) {
            CategoryBase items = event.categories.get(itemPos);
            buildItems(items.entries, helper);
            helper.registerLinkablePages(Collections.singletonList(items));
        }
        if (blockPos >= 0) {
            CategoryBase blocks = event.categories.get(blockPos);
            buildBlocks(blocks.entries, helper);
            helper.registerLinkablePages(Collections.singletonList(blocks));
        }
    }

    private static Map<Identifier, EntryBase> buildWerewolf(BookHelper helper) {
        Map<Identifier, EntryBase> entries = new LinkedHashMap<>();
        String base = "guide.werewolves.werewolf.";

        List<IPage> gettingStarted = new ArrayList<>();
        gettingStarted.addAll(PageHelper.pagesForLongText(translateComponent(base + "getting_started.become")));
        gettingStarted.addAll(PageHelper.pagesForLongText(translateComponent(base + "getting_started.as_werewolf")));
        gettingStarted.addAll(PageHelper.pagesForLongText(translateComponent(base + "getting_started.weakness")));
        gettingStarted.addAll(PageHelper.pagesForLongText(translateComponent(base + "getting_started.skills")));
        entries.put(WIdentifier.mod(base + "getting_started"), new EntryText(gettingStarted, translateComponent(base + "getting_started")));

        List<IPage> levelingPages = new ArrayList<>();
        levelingPages.addAll(PageHelper.pagesForLongText(translateComponent(base + "leveling.intro")));
        String stone_altar = "§l" + loc(ModBlocks.STONE_ALTAR.get()) + "§r\n§o" + translate(base + "leveling.stone.reach") + "§r\n";
        stone_altar += translate(base + "leveling.stone.intro", loc(Items.FLINT_AND_STEEL), loc(Items.TORCH));
        levelingPages.addAll(helper.addLinks(PageHelper.pagesForLongText(Component.literal(stone_altar)), WIdentifier.mod("guide.werewolves.blocks.stone_altar"), WIdentifier.mod("guide.werewolves.blocks.stone_altar_fire_bowl")));
        levelingPages.addAll(PageHelper.pagesForLongText(translateComponent(base + "leveling.stone.structure", loc(ModBlocks.STONE_ALTAR.get()), loc(ModBlocks.STONE_ALTAR_FIRE_BOWL.get()), loc(ModBlocks.STONE_ALTAR.get()), loc(ModBlocks.STONE_ALTAR_FIRE_BOWL.get()), loc(Items.FLINT_AND_STEEL))));
        levelingPages.add(new PageTextImage(translateComponent(base + "leveling.stone.image1"), Identifier.parse(IMAGE_BASE + "stone1.png"), false));
        String item = loc(ModItems.LIVER.get()) + ", " + loc(ModItems.CRACKED_BONE.get());
        levelingPages.addAll(helper.addLinks(PageHelper.pagesForLongText(translateComponent(base + "leveling.stone.items", item)), WIdentifier.mod("guide.werewolves.items.liver"), WIdentifier.mod("guide.werewolves.items.cracked_bone"), WIdentifier.mod("guide.werewolves.werewolf.slayed_creatures")));
        PageTable.Builder requirementBuilder = new PageTable.Builder(4);
        requirementBuilder.addLine(Component.translatable("text.vampirism.level_short"), loc(ModItems.LIVER.get()), loc(ModItems.CRACKED_BONE.get()), Component.translatable("text.werewolves.slayed_creatures"));
        for (int i = 2; i <= 14; i++) {
            WerewolfLevelConf.StoneAltarRequirement req = WerewolfLevelConf.getInstance().getStoneRequirement(i);
            //noinspection ConstantConditions
            requirementBuilder.addLine(i, req.liverAmount, req.bonesAmount, req.xpAmount);
        }
        requirementBuilder.setHeadline(translateComponent(base + "leveling.stone_req"));
        PageHolderWithLinks requirementTable = new PageHolderWithLinks(helper, requirementBuilder.build());
        requirementTable.addLink(WIdentifier.mod("guide.werewolves.items.liver"));
        requirementTable.addLink(WIdentifier.mod("guide.werewolves.items.cracked_bone"));
        requirementTable.addLink(WIdentifier.mod("guide.werewolves.werewolf.slayed_creatures"));
        levelingPages.add(requirementTable);

        entries.put(WIdentifier.mod(base + "leveling"), new EntryText(levelingPages, translateComponent(base + "leveling")));

        List<IPage> skillPages = new ArrayList<>();
        skillPages.addAll(PageHelper.pagesForLongText(Component.translatable(base + "skills.text", Component.keybind(ModKeys.BITE_KEY))));
        skillPages.addAll(PageHelper.pagesForLongText(translateComponent(base + "skills.decision")));
        entries.put(WIdentifier.mod(base + "skills"), new EntryText(skillPages, translateComponent(base + "skills")));

        List<IPage> werewolfLord = new ArrayList<>();
        werewolfLord.addAll(helper.addLinks(PageHelper.pagesForLongText(translateComponent(base + "lord.text", ModEntities.TASK_MASTER_WEREWOLF.get().getDescription(), LORD_TITLES.getLordTitle(1, IPlayableFaction.TitleGender.UNKNOWN), LORD_TITLES.getLordTitle(ModFactions.WEREWOLF.value().getHighestLordLevel(), IPlayableFaction.TitleGender.UNKNOWN))), WIdentifier.mod("guide.vampirism.entity.taskmaster")));
        PageTable.Builder lordTitleBuilder = new PageTable.Builder(2).setHeadline(translateComponent(base + "lord.titles"));
        lordTitleBuilder.addLine(Component.translatable("text.vampirism.level"), Component.translatable("text.vampirism.title"));
        lordTitleBuilder.addLine(1, LORD_TITLES.getLordTitle(1, IPlayableFaction.TitleGender.UNKNOWN).getString());
        lordTitleBuilder.addLine(2, LORD_TITLES.getLordTitle(2, IPlayableFaction.TitleGender.UNKNOWN).getString());
        lordTitleBuilder.addLine(3, LORD_TITLES.getLordTitle(3, IPlayableFaction.TitleGender.UNKNOWN).getString());
        lordTitleBuilder.addLine(4, LORD_TITLES.getLordTitle(4, IPlayableFaction.TitleGender.UNKNOWN).getString());
        lordTitleBuilder.addLine(5, LORD_TITLES.getLordTitle(5, IPlayableFaction.TitleGender.UNKNOWN).getString());
        werewolfLord.add(lordTitleBuilder.build());
        werewolfLord.addAll(helper.addLinks(PageHelper.pagesForLongText(translateComponent(base + "lord.minion", loc(ModItems.WEREWOLF_MINION_CHARM.get()), loc(ModItems.WEREWOLF_MINION_UPGRADE_SIMPLE.get()), loc(ModItems.WEREWOLF_MINION_UPGRADE_ENHANCED.get()), loc(ModItems.WEREWOLF_MINION_UPGRADE_SPECIAL.get()))), WIdentifier.mod("guide.werewolves.items.werewolf_minion_charm")));
        werewolfLord.addAll(helper.addLinks(PageHelper.pagesForLongText(translateComponent("guide.vampirism.common.minion_control", FactionKeys.MINION.getTranslatedKeyMessage(), translate("text.vampirism.minion.call_single"), translate("text.vampirism.minion.respawn")))));
        entries.put(WIdentifier.mod(base + "lord"), new EntryText(werewolfLord, translateComponent(base + "lord")));

        List<IPage> unWerewolf = new ArrayList<>();
        unWerewolf.addAll(helper.addLinks(PageHelper.pagesForLongText(translateComponent(base + "un_werewolf.text", loc(ModItems.INJECTION_UN_WEREWOLF.get()), loc(ModBlocks.V.MED_CHAIR.get()))), WIdentifier.mod("guide.vampirism.items.injection_empty"), WIdentifier.mod("guide.vampirism.blocks.item_med_chair")));
        entries.put(WIdentifier.mod(base + "un_werewolf"), new EntryText(unWerewolf, translateComponent(base + "un_werewolf")));

        List<IPage> creatures = new ArrayList<>();
        creatures.addAll(helper.addLinks(PageHelper.pagesForLongText(translateComponent(base + "slayed_creatures.text")), WIdentifier.mod(base + "leveling")));
        entries.put(WIdentifier.mod(base + "slayed_creatures"), new EntryText(creatures, translateComponent(base + "slayed_creatures")));
        return entries;
    }

    private static void buildItems(Map<Identifier, EntryBase> entriesIn, BookHelper helper) {
        Map<Identifier, EntryBase> entries = new LinkedHashMap<>();
        helper.info(ModItems.LIVER.get()).setLinks(WIdentifier.mod("guide.werewolves.werewolf.leveling")).build(entries);
        helper.info(ModItems.CRACKED_BONE.get()).setLinks(WIdentifier.mod("guide.werewolves.werewolf.leveling")).build(entries);
        helper.info(ModItems.WEREWOLF_MINION_CHARM.get(), ModItems.WEREWOLF_MINION_UPGRADE_SIMPLE.get(), ModItems.WEREWOLF_MINION_UPGRADE_ENHANCED.get(), ModItems.WEREWOLF_MINION_UPGRADE_SPECIAL.get())
                .setFormats(loc(ModItems.WEREWOLF_MINION_CHARM.get()),
                        loc(ModItems.WEREWOLF_MINION_UPGRADE_SIMPLE.get()), ModItems.WEREWOLF_MINION_UPGRADE_SIMPLE.get().getMinLevel() + 1, ModItems.WEREWOLF_MINION_UPGRADE_SIMPLE.get().getMaxLevel() + 1,
                        loc(ModItems.WEREWOLF_MINION_UPGRADE_ENHANCED.get()), ModItems.WEREWOLF_MINION_UPGRADE_ENHANCED.get().getMinLevel() + 1, ModItems.WEREWOLF_MINION_UPGRADE_ENHANCED.get().getMaxLevel() + 1,
                        loc(ModItems.WEREWOLF_MINION_UPGRADE_SPECIAL.get()), ModItems.WEREWOLF_MINION_UPGRADE_SPECIAL.get().getMinLevel() + 1, ModItems.WEREWOLF_MINION_UPGRADE_SPECIAL.get().getMaxLevel() + 1,
                        translate(ModEntities.TASK_MASTER_WEREWOLF.get().getDescriptionId()))
                .setLinks(WIdentifier.mod("guide.vampirism.entity.taskmaster"), WIdentifier.mod("guide.vampirism.vampire.lord")).build(entries);
        helper.info(ModItems.WEREWOLF_TOOTH.get()).setFormats(loc(ModItems.WEREWOLF_TOOTH.get()), ModEntities.ALPHA_WEREWOLF.get().getDescription()).build(entries);
        helper.info(ModItems.BONE_NECKLACE.get(), ModItems.CHARM_BRACELET.get(), ModItems.DREAM_CATCHER.get()).useCustomEntryName().setKeyName("accessories").build(entries);
        entriesIn.putAll(entries);
    }

    private static void buildBlocks(Map<Identifier, EntryBase> entriesIn, BookHelper helper) {
        Map<Identifier, EntryBase> entries = new LinkedHashMap<>();
        helper.infoBlocks(ModBlocks.STONE_ALTAR.get()).setLinks(WIdentifier.mod("guide.werewolves.werewolf.leveling")).recipes("stone_altar").build(entries);
        helper.infoBlocks(ModBlocks.STONE_ALTAR_FIRE_BOWL.get()).setLinks(WIdentifier.mod("guide.werewolves.werewolf.leveling")).recipes("stone_altar_fire_bowl").build(entries);
        entriesIn.putAll(entries);
    }

    private static Component loc(Item i) {
        return Component.translatable(i.getDescriptionId());
    }

    private static Component loc(Block i) {
        return Component.translatable(i.getDescriptionId());
    }
}
