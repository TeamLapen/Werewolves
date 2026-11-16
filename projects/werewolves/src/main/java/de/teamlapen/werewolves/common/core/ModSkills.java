package de.teamlapen.werewolves.common.core;

import de.teamlapen.faction.api.FactionRegistries;
import de.teamlapen.faction.api.factions.skills.ISkill;
import de.teamlapen.faction.api.factions.skills.ISkillSegment;
import de.teamlapen.faction.api.factions.skills.ISkillTree;
import de.teamlapen.faction.api.registries.skills.DeferredSkill;
import de.teamlapen.faction.api.registries.skills.DeferredSkillRegister;
import de.teamlapen.faction.api.util.SafeCast;
import de.teamlapen.faction.common.advancements.criterion.PlayerFactionSubPredicate;
import de.teamlapen.faction.common.core.FactionConsumer;
import de.teamlapen.faction.common.core.FactionSkills;
import de.teamlapen.faction.common.factions.skills.SkillSegment;
import de.teamlapen.faction.common.factions.skills.SkillTree;
import de.teamlapen.vampirism.common.world.entity.player.hunter.skills.HunterSkills;
import de.teamlapen.vampirism.common.world.entity.player.lord.skills.LordSkills;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.actions.SurvivalWerewolfFormAction;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.skill.*;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.neoforged.bus.api.IEventBus;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public class ModSkills {

    public static final DeferredSkillRegister SKILLS = DeferredSkillRegister.create(REFERENCE.MODID);

    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> LEVEL_ROOT = SKILLS.registerSkill(REFERENCE.WEREWOLF_PLAYER_KEY.getPath(), WerewolfSkill::new);
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> LORD_ROOT = SKILLS.registerSkill(REFERENCE.WEREWOLF_PLAYER_KEY.withSuffix("_lord").getPath(), WerewolfLordSkill::new);
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> HUMAN_FORM = SKILLS.registerSkill("human_form", (p) -> new FormActionSkill(SafeCast.cast(ModActions.HUMAN_FORM), p));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> NIGHT_VISION = SKILLS.registerSkill("night_vision", (p) -> new WerewolfSkill(p.withDescription().onEnable(ModConsumer.ENABLE_NIGHT_VISION).onDisable(ModConsumer.DISABLE_NIGHT_VISION)));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> RAGE = SKILLS.registerSkill("rage", p -> new WerewolfSkill(p.cost(2).actionSkill(ModActions.RAGE).withDescription()));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> BEAST_FORM = SKILLS.registerSkill("beast_form", (p) -> new FormActionSkill(SafeCast.cast(ModActions.BEAST_FORM), p.cost(3)));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> SURVIVAL_FORM = SKILLS.registerSkill("survival_form", (p) -> new FormActionSkill(SafeCast.cast(ModActions.SURVIVAL_FORM), p.cost(3)));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> STUN_BITE = SKILLS.registerSkill("stun_bite", (p) -> new WerewolfSkill(p.withDescription(appliesTo(BEAST_FORM, SURVIVAL_FORM))));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> BLEEDING_BITE = SKILLS.registerSkill("bleeding_bite", (p) -> new WerewolfSkill(p.withDescription(appliesTo(BEAST_FORM, SURVIVAL_FORM))));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> DAMAGE = SKILLS.registerSkill("damage", (p) -> new WerewolfSkill(p.withDescription(appliesTo(BEAST_FORM, SURVIVAL_FORM))));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> RESISTANCE = SKILLS.registerSkill("resistance", (p) -> new WerewolfSkill(p.withDescription()));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> HEALTH_AFTER_KILL = SKILLS.registerSkill("health_after_kill", (p) -> new WerewolfSkill(p.withDescription()));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> HOWLING = SKILLS.registerSkill("howling", (p) -> new WerewolfSkill(p.withDescription().actionSkill(ModActions.HOWLING)));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> SENSE = SKILLS.registerSkill("sense", (p) -> new WerewolfSkill(p.withDescription().actionSkill(ModActions.SENSE)));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> SPEED = SKILLS.registerSkill("speed", (p) -> new WerewolfSkill(p.withDescription().attribute(Attributes.MOVEMENT_SPEED,  () -> ModConfig.balance().SKILLS.speed_amount.get(), AttributeModifier.Operation.ADD_VALUE)));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> JUMP = SKILLS.registerSkill("jump", (p) ->  new WerewolfSkill(p.withDescription()));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> LEAP = SKILLS.registerSkill("leap", (p) -> new WerewolfSkill(p.withDescription().actionSkill(ModActions.LEAP)));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> WOLF_PACK = SKILLS.registerSkill("wolf_pack", (p) -> new WerewolfSkill(p.withDescription(appliesTo(HOWLING))));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> MOVEMENT_TACTICS = SKILLS.registerSkill("movement_tactics", (p) -> new WerewolfSkill(p.withDescription(appliesTo(SURVIVAL_FORM))));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> CLIMBER = SKILLS.registerSkill("climber", (p) -> new WerewolfSkill(p.withDescription(appliesTo(BEAST_FORM, SURVIVAL_FORM)).onEnable(ModConsumer.ENABLE_CLIMBER).onDisable(ModConsumer.DISABLE_CLIMBER)));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> WOLF_PAWN = SKILLS.registerSkill("wolf_pawn", (p) -> new WerewolfSkill(p.withDescription())); //TODO maybe add soul sand & slime & ice & webs as blocks that do not affect movement speed
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> NOT_MEAT = SKILLS.registerSkill("not_meat", (p) -> new WerewolfSkill(p.withDescription().cost(3)));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> WATER_LOVER = SKILLS.registerSkill("water_lover", (p) -> new WerewolfSkill(p.withDescription()));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> FREE_WILL = SKILLS.registerSkill("free_will", (p) -> new WerewolfSkill(p.withDescription()));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> WEAR_ARMOR = SKILLS.registerSkill("wear_armor", (p) -> new WerewolfSkill(p.cost(3).withDescription(appliesTo(HUMAN_FORM))));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> SILVER_BLOODED = SKILLS.registerSkill("silver_blooded", (p) -> new WerewolfSkill(p.withDescription(appliesTo(BEAST_FORM, HUMAN_FORM))));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> HIDE_NAME = SKILLS.registerSkill("hide_name", (p) -> new WerewolfSkill(p.withDescription().actionSkill(ModActions.HIDE_NAME)));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> FEAR = SKILLS.registerSkill("fear", (p) -> new WerewolfSkill(p.withDescription().actionSkill(ModActions.FEAR)));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> SIXTH_SENSE = SKILLS.registerSkill("sixth_sense", (p) -> new WerewolfSkill(p.withDescription()));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> HEALTH_REG = SKILLS.registerSkill("health_reg", (p) -> new WerewolfSkill(p.withDescription()));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> THICK_FUR = SKILLS.registerSkill("thick_fur", (p) -> new WerewolfSkill(p.withDescription(appliesTo(BEAST_FORM, SURVIVAL_FORM, HUMAN_FORM))));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> DIGGER = SKILLS.registerSkill("digger", DiggerSkill::new);
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> ENHANCED_DIGGER = SKILLS.registerSkill("enhanced_digger", DiggerSkill::new);
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> MINION_STATS_INCREASE = SKILLS.registerSkill("werewolf_minion_stats_increase", (p) -> new WerewolfLordSkill(p.withDescription().cost(2).onEnable(FactionConsumer.ENABLE_MINION_INCREASED_STATS).onDisable(FactionConsumer.DISABLE_MINION_INCREASED_STATS)));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> MINION_COLLECT = SKILLS.registerSkill("werewolf_minion_collect", (p) -> new WerewolfLordSkill(p.withDescription().cost(2)));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> BEAST_RAGE = SKILLS.registerSkill("beast_rage", (p) -> new WerewolfSkill(p.withDescription(appliesTo(BEAST_FORM))));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> ARROW_AWARENESS = SKILLS.registerSkill("arrow_awareness", (p) -> new WerewolfSkill(p.withDescription(appliesTo(SURVIVAL_FORM))));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> EFFICIENT_DIET = SKILLS.registerSkill("efficient_diet", (p) -> new WerewolfSkill(p.withDescription(appliesTo(SURVIVAL_FORM))));

    @SafeVarargs
    public static Component appliesTo(DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>>... skills) {
        var component = Component.translatable("text.werewolves.skills.only_applies");
        for (DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> skill : skills) {
            component = component.append(skill.getRegisteredName());
        }
        return component;
    }

    @ApiStatus.Internal
    static void register(IEventBus bus) {
        SKILLS.register(bus);
    }

    public static class Segments {

        public static final ResourceKey<ISkillSegment> LEVEL_ROOT = segment("level_root");
        public static final ResourceKey<ISkillSegment> SKILL1 = segment("skill1");
        public static final ResourceKey<ISkillSegment> SKILL2 = segment("skill2");
        public static final ResourceKey<ISkillSegment> BEAST1 = segment("beast1");
        public static final ResourceKey<ISkillSegment> BEAST2 = segment("beast2");
        public static final ResourceKey<ISkillSegment> BEAST3_1 = segment("beast3_1");
        public static final ResourceKey<ISkillSegment> BEAST3 = segment("beast3");
        public static final ResourceKey<ISkillSegment> BEAST4 = segment("beast4");
        public static final ResourceKey<ISkillSegment> BEAST5 = segment("beast5");
        public static final ResourceKey<ISkillSegment> BEAST6 = segment("beast6");
        public static final ResourceKey<ISkillSegment> BEAST7 = segment("beast7");
        public static final ResourceKey<ISkillSegment> SURVIVAL1 = segment("survival1");
        public static final ResourceKey<ISkillSegment> SURVIVAL2 = segment("survival2");
        public static final ResourceKey<ISkillSegment> SURVIVAL3 = segment("survival3");
        public static final ResourceKey<ISkillSegment> SURVIVAL4 = segment("survival4");
        public static final ResourceKey<ISkillSegment> SURVIVAL5 = segment("survival5");
        public static final ResourceKey<ISkillSegment> SURVIVAL31 = segment("survival31");
        public static final ResourceKey<ISkillSegment> UTIL1 = segment("util1");
        public static final ResourceKey<ISkillSegment> UTIL2 = segment("util2");
        public static final ResourceKey<ISkillSegment> UTIL3 = segment("util3");
        public static final ResourceKey<ISkillSegment> OTHER1 = segment("other1");
        public static final ResourceKey<ISkillSegment> OTHER2 = segment("other2");
        public static final ResourceKey<ISkillSegment> OTHER3 = segment("other3");
        public static final ResourceKey<ISkillSegment> OTHER4 = segment("other4");
        public static final ResourceKey<ISkillSegment> OTHER5 = segment("other5");
        public static final ResourceKey<ISkillSegment> OTHER6 = segment("other6");

        public static final ResourceKey<ISkillSegment> LORD_ROOT = segment("lord_root");
        public static final ResourceKey<ISkillSegment> LORD_2 = segment("lord_2");
        public static final ResourceKey<ISkillSegment> LORD_3 = segment("lord_3");
        public static final ResourceKey<ISkillSegment> LORD_4 = segment("lord_4");
        public static final ResourceKey<ISkillSegment> LORD_5 = segment("lord_5");

        private static ResourceKey<ISkillSegment> segment(String path) {
            return ResourceKey.create(FactionRegistries.Keys.SKILL_SEGMENT, WIdentifier.mod("werewolf/" + path));
        }

        public static void createSkillNodes(BootstrapContext<ISkillSegment> context) {
            level(LEVEL_ROOT, ModSkills.LEVEL_ROOT)
                    .register(context);
            level(SKILL1, ModSkills.HUMAN_FORM)
                    .parents(LEVEL_ROOT)
                    .register(context);
            level(SKILL2, ModSkills.NIGHT_VISION, ModSkills.SENSE)
                    .parents(SKILL1)
                    .register(context);
            level(BEAST1, ModSkills.RAGE)
                    .parents(SKILL1)
                    .register(context);
            level(BEAST2, ModSkills.BEAST_FORM)
                    .parents(BEAST2)
                    .register(context);
            level(BEAST3_1, ModSkills.DAMAGE)
                    .parents(BEAST2)
                    .register(context);
            level(BEAST3, ModSkills.THICK_FUR)
                    .parents(BEAST2)
                    .register(context);
            level(BEAST4, ModSkills.STUN_BITE, ModSkills.BLEEDING_BITE)
                    .parents(BEAST4)
                    .register(context);
            level(BEAST5, ModSkills.HEALTH_AFTER_KILL)
                    .parents(BEAST4)
                    .register(context);
            level(BEAST6, ModSkills.BEAST_RAGE)
                    .parents(BEAST5)
                    .register(context);
            level(BEAST7, ModSkills.FEAR)
                    .parents(BEAST6)
                    .register(context);
            level(SURVIVAL1, ModSkills.JUMP, ModSkills.SPEED)
                    .parents(SKILL2)
                    .register(context);
            level(SURVIVAL2, ModSkills.SURVIVAL_FORM)
                    .parents(SURVIVAL1)
                    .register(context);
            level(SURVIVAL3, ModSkills.WOLF_PAWN, ModSkills.CLIMBER)
                    .parents(SURVIVAL2)
                    .register(context);
            level(SURVIVAL4, ModSkills.EFFICIENT_DIET, ModSkills.ARROW_AWARENESS)
                    .parents(SURVIVAL3)
                    .register(context);
            level(SURVIVAL5, ModSkills.MOVEMENT_TACTICS)
                    .parents(SURVIVAL4)
                    .register(context);
            level(SURVIVAL31, ModSkills.LEAP)
                    .parents(SURVIVAL2)
                    .register(context);
            level(UTIL1, ModSkills.HOWLING)
                    .parents(SKILL2)
                    .register(context);
            level(UTIL2, ModSkills.WOLF_PACK, ModSkills.DIGGER)
                    .parents(UTIL1)
                    .register(context);
            level(UTIL3, ModSkills.NOT_MEAT, ModSkills.HEALTH_REG)
                    .parents(UTIL2)
                    .register(context);
            level(OTHER1, ModSkills.FREE_WILL)
                    .parents(SKILL2)
                    .register(context);
            level(OTHER2, ModSkills.SILVER_BLOODED)
                    .parents(OTHER1)
                    .register(context);
            level(OTHER3, ModSkills.WEAR_ARMOR)
                    .parents(OTHER2)
                    .register(context);
            level(OTHER4, ModSkills.WATER_LOVER)
                    .parents(OTHER3)
                    .register(context);
            level(OTHER5, ModSkills.HIDE_NAME)
                    .parents(OTHER4)
                    .register(context);
            level(OTHER6, ModSkills.ENHANCED_DIGGER)
                    .parents(OTHER5)
                    .register(context);
            lord(LORD_ROOT, ModSkills.LORD_ROOT)
                    .register(context);
            lord(LORD_2, ModSkills.MINION_STATS_INCREASE)
                    .parents(LORD_ROOT)
                    .register(context);
            lord(LORD_3, LordSkills.LORD_SPEED, LordSkills.LORD_ATTACK_SPEED)
                    .parents(LORD_ROOT)
                    .register(context);
            lord(LORD_4, ModSkills.MINION_COLLECT)
                    .parents(LORD_ROOT)
                    .register(context);
            lord(LORD_5, FactionSkills.MINION_RECOVERY)
                    .parents(LORD_ROOT)
                    .register(context);
        }

        @SafeVarargs
        public static SkillSegment.Builder level(ResourceKey<ISkillSegment> key, Holder<? extends ISkill<?>>... skills) {
            return SkillSegment.Builder.of(HunterSkills.Trees.LEVEL, key, skills);
        }

        @SafeVarargs
        public static SkillSegment.Builder lord(ResourceKey<ISkillSegment> key, Holder<? extends ISkill<?>>... skills) {
            return SkillSegment.Builder.of(ModSkills.Trees.LORD, key, skills);
        }


    }

    public static class Trees {
        public static final ResourceKey<ISkillTree> LEVEL = tree("level");
        public static final ResourceKey<ISkillTree> LORD = tree("lord");

        private static ResourceKey<ISkillTree> tree(String path) {
            return ResourceKey.create(FactionRegistries.Keys.SKILL_TREE, WIdentifier.mod("werewolf/" + path));
        }

        public static void createSkillTrees(BootstrapContext<ISkillTree> context) {
            context.register(LEVEL, new SkillTree(ModFactions.WEREWOLF, EntityPredicate.Builder.entity().subPredicate(PlayerFactionSubPredicate.faction(ModFactions.WEREWOLF)).build(), new ItemStackTemplate(ModItems.LIVER.get()), Component.translatable("gui.vampirism.skills.level")));
            context.register(LORD, new SkillTree(ModFactions.WEREWOLF, EntityPredicate.Builder.entity().subPredicate(PlayerFactionSubPredicate.lord(ModFactions.WEREWOLF)).build(), new ItemStackTemplate(ModItems.WEREWOLF_MINION_CHARM.get()), Component.translatable("gui.vampirism.skills.lord"), Optional.empty(), List.of(LEVEL)));
        }
    }
}
