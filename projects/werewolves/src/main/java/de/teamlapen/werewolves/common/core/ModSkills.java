package de.teamlapen.werewolves.common.core;

import de.teamlapen.faction.api.factions.skills.ISkill;
import de.teamlapen.faction.api.registries.skills.DeferredSkill;
import de.teamlapen.faction.api.registries.skills.DeferredSkillRegister;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.actions.SurvivalWerewolfFormAction;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.skill.*;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import org.jetbrains.annotations.ApiStatus;

@SuppressWarnings("unused")
public class ModSkills {

    public static final DeferredSkillRegister SKILLS = DeferredSkillRegister.create(REFERENCE.MODID);

    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> LEVEL_ROOT = SKILLS.registerSkill(REFERENCE.WEREWOLF_PLAYER_KEY.getPath(), WerewolfSkill::new);
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> LORD_ROOT = SKILLS.registerSkill(REFERENCE.WEREWOLF_PLAYER_KEY.withSuffix("_lord").getPath(), WerewolfLordSkill::new);
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> HUMAN_FORM = SKILLS.registerSkill("human_form", () -> new FormActionSkill(ModActions.HUMAN_FORM, Trees.LEVEL, 2));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> NIGHT_VISION = SKILLS.registerSkill("night_vision", () -> new SimpleWerewolfSkill(true).setToggleActions(
            (player) -> ((WerewolfPlayer) player).getSpecialAttributes().night_vision = true,
            (player) -> ((WerewolfPlayer) player).getSpecialAttributes().night_vision = false));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> RAGE = SKILLS.registerSkill("rage", () -> new ActionSkill<>(ModActions.RAGE, Trees.LEVEL, 2,true));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> BEAST_FORM = SKILLS.registerSkill("beast_form", () -> new FormActionSkill(ModActions.BEAST_FORM, Trees.LEVEL, 3));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> SURVIVAL_FORM = SKILLS.registerSkill("survival_form", () -> new FormActionSkill(ModActions.SURVIVAL_FORM, Trees.LEVEL, 3));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> STUN_BITE = SKILLS.registerSkill("stun_bite", () -> new SimpleWerewolfSkill(true).defaultDescWithFormRequirement(BEAST_FORM::get, SURVIVAL_FORM::get));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> BLEEDING_BITE = SKILLS.registerSkill("bleeding_bite", () -> new SimpleWerewolfSkill(true).defaultDescWithFormRequirement(BEAST_FORM::get, SURVIVAL_FORM::get));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> DAMAGE = SKILLS.registerSkill("damage", () -> new SimpleWerewolfSkill(true).defaultDescWithFormRequirement(BEAST_FORM::get, SURVIVAL_FORM::get));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> RESISTANCE = SKILLS.registerSkill("resistance", () -> new SimpleWerewolfSkill(true));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> HEALTH_AFTER_KILL = SKILLS.registerSkill("health_after_kill", () -> new SimpleWerewolfSkill(1, true));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> HOWLING = SKILLS.registerSkill("howling", () -> new ActionSkill<>(ModActions.HOWLING, Trees.LEVEL, true));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> SENSE = SKILLS.registerSkill("sense", () -> new ActionSkill<>(ModActions.SENSE, Trees.LEVEL, true));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> SPEED = SKILLS.registerSkill("speed", () -> new SimpleWerewolfSkill.AttributeSkill(true, Attributes.MOVEMENT_SPEED, AttributeModifier.Operation.ADD_VALUE, player -> ModConfig.BALANCE.SKILLS.speed_amount.get()));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> JUMP = SKILLS.registerSkill("jump", () -> new SimpleWerewolfSkill(true));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> LEAP = SKILLS.registerSkill("leap", () -> new ActionSkill<>(ModActions.LEAP, Trees.LEVEL, true));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> WOLF_PACK = SKILLS.registerSkill("wolf_pack", () -> new SimpleWerewolfSkill(1).defaultDescWithEnhancement(HOWLING::get));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> MOVEMENT_TACTICS = SKILLS.registerSkill("movement_tactics", () -> new SimpleWerewolfSkill().defaultDescWithFormRequirement(SURVIVAL_FORM::get));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> CLIMBER = SKILLS.registerSkill("climber", () -> new SimpleWerewolfSkill().defaultDescWithFormRequirement(SURVIVAL_FORM::get).setToggleActions(SurvivalWerewolfFormAction::climberSkillEnabled, SurvivalWerewolfFormAction::climberSkillDisabled));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> WOLF_PAWN = SKILLS.registerSkill("wolf_pawn", () -> new SimpleWerewolfSkill(1,true)); //TODO maybe add soul sand & slime & ice & webs as blocks that do not affect movement speed
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> NOT_MEAT = SKILLS.registerSkill("not_meat", () -> new SimpleWerewolfSkill(3,true));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> WATER_LOVER = SKILLS.registerSkill("water_lover", () -> new SimpleWerewolfSkill(true));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> FREE_WILL = SKILLS.registerSkill("free_will", () -> new SimpleWerewolfSkill(true));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> WEAR_ARMOR = SKILLS.registerSkill("wear_armor", () -> new SimpleWerewolfSkill(3).defaultDescWithFormRequirement(HUMAN_FORM::get));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> SILVER_BLOODED = SKILLS.registerSkill("silver_blooded", () -> new SimpleWerewolfSkill(true).defaultDescWithFormRequirement(HUMAN_FORM::get));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> HIDE_NAME = SKILLS.registerSkill("hide_name", () -> new ActionSkill<>(ModActions.HIDE_NAME, Trees.LEVEL, 1,true));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> FEAR = SKILLS.registerSkill("fear", () -> new ActionSkill<>(ModActions.FEAR, Trees.LEVEL, 1,true));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> SIXTH_SENSE = SKILLS.registerSkill("sixth_sense", () -> new SimpleWerewolfSkill(1,true));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> HEALTH_REG = SKILLS.registerSkill("health_reg", () -> new SimpleWerewolfSkill(true));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> THICK_FUR = SKILLS.registerSkill("thick_fur", () -> new SimpleWerewolfSkill().defaultDescWithFormRequirement(BEAST_FORM::get, SURVIVAL_FORM::get, HUMAN_FORM::get));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> DIGGER = SKILLS.registerSkill("digger", DiggerSkill::new);
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> ENHANCED_DIGGER = SKILLS.registerSkill("enhanced_digger", DiggerSkill::new);
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> MINION_STATS_INCREASE = SKILLS.registerSkill("werewolf_minion_stats_increase", () -> new SimpleWerewolfSkill.LordWerewolfSkill(2, true).setToggleActions(IWerewolfPlayer::updateMinionAttributes));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> MINION_COLLECT = SKILLS.registerSkill("werewolf_minion_collect", () -> new SimpleWerewolfSkill.LordWerewolfSkill(2,true));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> BEAST_RAGE = SKILLS.registerSkill("beast_rage", () -> new SimpleWerewolfSkill(true).defaultDescWithFormRequirement(BEAST_FORM::get));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> ARROW_AWARENESS = SKILLS.registerSkill("arrow_awareness", () -> new SimpleWerewolfSkill(true).defaultDescWithFormRequirement(SURVIVAL_FORM::get));
    public static final DeferredSkill<IWerewolfPlayer, ISkill<IWerewolfPlayer>> EFFICIENT_DIET = SKILLS.registerSkill("efficient_diet", () -> new SimpleWerewolfSkill(true).defaultDescWithFormRequirement(SURVIVAL_FORM::get));

    @ApiStatus.Internal
    static void register(IEventBus bus) {
        SKILLS.register(bus);
    }

    public static class Nodes {

        public static final ResourceKey<ISkillNode> LEVEL_ROOT = node("level_root");
        public static final ResourceKey<ISkillNode> SKILL1 = node("skill1");
        public static final ResourceKey<ISkillNode> SKILL2 = node("skill2");
        public static final ResourceKey<ISkillNode> BEAST1 = node("beast1");
        public static final ResourceKey<ISkillNode> BEAST2 = node("beast2");
        public static final ResourceKey<ISkillNode> BEAST3_1 = node("beast3_1");
        public static final ResourceKey<ISkillNode> BEAST3 = node("beast3");
        public static final ResourceKey<ISkillNode> BEAST4 = node("beast4");
        public static final ResourceKey<ISkillNode> BEAST5 = node("beast5");
        public static final ResourceKey<ISkillNode> BEAST6 = node("beast6");
        public static final ResourceKey<ISkillNode> BEAST7 = node("beast7");
        public static final ResourceKey<ISkillNode> SURVIVAL1 = node("survival1");
        public static final ResourceKey<ISkillNode> SURVIVAL2 = node("survival2");
        public static final ResourceKey<ISkillNode> SURVIVAL3 = node("survival3");
        public static final ResourceKey<ISkillNode> SURVIVAL4 = node("survival4");
        public static final ResourceKey<ISkillNode> SURVIVAL5 = node("survival5");
        public static final ResourceKey<ISkillNode> SURVIVAL31 = node("survival31");
        public static final ResourceKey<ISkillNode> UTIL1 = node("util1");
        public static final ResourceKey<ISkillNode> UTIL2 = node("util2");
        public static final ResourceKey<ISkillNode> UTIL3 = node("util3");
        public static final ResourceKey<ISkillNode> OTHER1 = node("other1");
        public static final ResourceKey<ISkillNode> OTHER2 = node("other2");
        public static final ResourceKey<ISkillNode> OTHER3 = node("other3");
        public static final ResourceKey<ISkillNode> OTHER4 = node("other4");
        public static final ResourceKey<ISkillNode> OTHER5 = node("other5");
        public static final ResourceKey<ISkillNode> OTHER6 = node("other6");

        public static final ResourceKey<ISkillNode> LORD_ROOT = node("lord_root");
        public static final ResourceKey<ISkillNode> LORD_2 = node("lord_2");
        public static final ResourceKey<ISkillNode> LORD_3 = node("lord_3");
        public static final ResourceKey<ISkillNode> LORD_4 = node("lord_4");
        public static final ResourceKey<ISkillNode> LORD_5 = node("lord_5");

        private static ResourceKey<ISkillNode> node(String path) {
            return ResourceKey.create(VampirismRegistries.Keys.SKILL_NODE, WIdentifier.mod("werewolf/" + path));
        }

        public static void createSkillNodes(BootstrapContext<ISkillNode> context) {
            context.register(LEVEL_ROOT, new SkillNode(ModSkills.LEVEL_ROOT));
            context.register(SKILL1, new SkillNode(ModSkills.HUMAN_FORM));
            context.register(SKILL2, new SkillNode(ModSkills.NIGHT_VISION, ModSkills.SENSE));
            context.register(BEAST1, new SkillNode(ModSkills.RAGE));
            context.register(BEAST2, new SkillNode(ModSkills.BEAST_FORM));
            context.register(BEAST3_1, new SkillNode(ModSkills.DAMAGE));
            context.register(BEAST3, new SkillNode(ModSkills.THICK_FUR));
            context.register(BEAST4, new SkillNode(ModSkills.STUN_BITE, ModSkills.BLEEDING_BITE));
            context.register(BEAST5, new SkillNode(ModSkills.HEALTH_AFTER_KILL));
            context.register(BEAST6, new SkillNode(ModSkills.BEAST_RAGE));
            context.register(BEAST7, new SkillNode(ModSkills.FEAR));
            context.register(SURVIVAL1, new SkillNode(ModSkills.JUMP, ModSkills.SPEED));
            context.register(SURVIVAL2, new SkillNode(ModSkills.SURVIVAL_FORM));
            context.register(SURVIVAL3, new SkillNode(ModSkills.WOLF_PAWN, ModSkills.CLIMBER));
            context.register(SURVIVAL4, new SkillNode(ModSkills.EFFICIENT_DIET, ModSkills.ARROW_AWARENESS));
            context.register(SURVIVAL5, new SkillNode(ModSkills.MOVEMENT_TACTICS));
            context.register(SURVIVAL31, new SkillNode(ModSkills.LEAP));
            context.register(UTIL1, new SkillNode(ModSkills.HOWLING));
            context.register(UTIL2, new SkillNode(ModSkills.WOLF_PACK, ModSkills.DIGGER));
            context.register(UTIL3, new SkillNode(ModSkills.NOT_MEAT, ModSkills.HEALTH_REG));
            context.register(OTHER1, new SkillNode(ModSkills.FREE_WILL));
            context.register(OTHER2, new SkillNode(ModSkills.SILVER_BLOODED));
            context.register(OTHER3, new SkillNode(ModSkills.WEAR_ARMOR));
            context.register(OTHER4, new SkillNode(ModSkills.WATER_LOVER));
            context.register(OTHER5, new SkillNode(ModSkills.HIDE_NAME));
            context.register(OTHER6, new SkillNode(ModSkills.ENHANCED_DIGGER));
            context.register(LORD_ROOT, new SkillNode(ModSkills.LORD_ROOT));
            context.register(LORD_2, new SkillNode(ModSkills.MINION_STATS_INCREASE));
            context.register(LORD_3, new SkillNode(LordSkills.LORD_SPEED, LordSkills.LORD_ATTACK_SPEED));
            context.register(LORD_4, new SkillNode(ModSkills.MINION_COLLECT));
            context.register(LORD_5, new SkillNode(LordSkills.MINION_RECOVERY));
        }

    }

    public static class Trees {
        public static final ResourceKey<ISkillTree> LEVEL = tree("level");
        public static final ResourceKey<ISkillTree> LORD = tree("lord");

        private static ResourceKey<ISkillTree> tree(String path) {
            return ResourceKey.create(VampirismRegistries.Keys.SKILL_TREE, WIdentifier.mod("werewolf/" + path));
        }

        public static void createSkillTrees(BootstrapContext<ISkillTree> context) {
            HolderGetter<ISkillNode> lookup = context.lookup(VampirismRegistries.Keys.SKILL_NODE);
            context.register(LEVEL, new SkillTree(WReference.WEREWOLF_FACTION, EntityPredicate.Builder.entity().subPredicate(PlayerFactionSubPredicate.faction(WReference.WEREWOLF_FACTION)).build(), new ItemStack(ModItems.LIVER.get()), Component.translatable("text.vampirism.skills.level")));
            context.register(LORD, new SkillTree(WReference.WEREWOLF_FACTION, EntityPredicate.Builder.entity().subPredicate(PlayerFactionSubPredicate.lord(WReference.WEREWOLF_FACTION)).build(), new ItemStack(ModItems.WEREWOLF_MINION_CHARM.get()), Component.translatable("text.vampirism.skills.lord")));
        }
    }
}
