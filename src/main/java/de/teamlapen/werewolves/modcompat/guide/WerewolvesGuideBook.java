package de.teamlapen.werewolves.modcompat.guide;

import de.maxanier.guideapi.api.IPage;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.api.util.PageHelper;
import de.maxanier.guideapi.category.CategoryItemStack;
import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.vampirism.client.core.ModKeys;
import de.teamlapen.vampirism.modcompat.guide.EntryText;
import de.teamlapen.vampirism.modcompat.guide.VampirismGuideBookCategoriesEvent;
import de.teamlapen.werewolves.core.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static de.teamlapen.vampirism.modcompat.guide.GuideBook.translateComponent;

@SuppressWarnings("CollectionAddAllCanBeReplacedWithConstructor")
public class WerewolvesGuideBook {

    private final static String IMAGE_BASE = "vampirismguide:textures/images/";


    public static void onVampirismGuideBookCategoriesEvent(VampirismGuideBookCategoriesEvent event) {
        event.categories.add(3, new CategoryItemStack(buildWerewolf(), translateComponent("guide.werewolves.entity.werewolf.title"), new ItemStack(ModItems.liver)));
    }

    private static Map<ResourceLocation, EntryAbstract> buildWerewolf() {
        Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<>();
        String base = "guide.werewolves.werewolf.";

        List<IPage> gettingStarted = new ArrayList<>();
        gettingStarted.addAll(PageHelper.pagesForLongText(translateComponent(base + "getting_started.become")));
        entries.put(new ResourceLocation(base + "getting_started"), new EntryText(gettingStarted, translateComponent(base + "getting_started")));

        List<IPage> levelingPages = new ArrayList<>();
        levelingPages.addAll(PageHelper.pagesForLongText(translateComponent(base + "leveling.intro")));
        levelingPages.addAll(PageHelper.pagesForLongText(translateComponent(base + "leveling.stone_altar")));
        entries.put(new ResourceLocation(base + "leveling"), new EntryText(levelingPages, translateComponent(base + "leveling")));

        List<IPage> skillPages = new ArrayList<>();
        skillPages.addAll(PageHelper.pagesForLongText(translateComponent(base + "skills.text", UtilLib.translate(ModKeys.getKeyBinding(ModKeys.KEY.SKILL).getTranslationKey()))));
        skillPages.addAll(PageHelper.pagesForLongText(translateComponent(base + "skills.actions", UtilLib.translate(ModKeys.getKeyBinding(ModKeys.KEY.ACTION).getTranslationKey()))));
        entries.put(new ResourceLocation(base + "skills"), new EntryText(skillPages, translateComponent(base + "skills")));

        List<IPage> werewolfLord = new ArrayList<>();
        werewolfLord.addAll(PageHelper.pagesForLongText(translateComponent(base + "lord.text")));
        entries.put(new ResourceLocation(base + "lord"), new EntryText(werewolfLord, translateComponent(base + "lord")));

        List<IPage> unWerewolf = new ArrayList<>();
        unWerewolf.addAll(PageHelper.pagesForLongText(translateComponent(base + "un_werewolf.text")));
        entries.put(new ResourceLocation(base + "un_werewolf"), new EntryText(unWerewolf, translateComponent(base + "un_werewolf")));

        return entries;
    }

    private static String loc(Item i) {
        return UtilLib.translate(i.getTranslationKey());
    }
}
