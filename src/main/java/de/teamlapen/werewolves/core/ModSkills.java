package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.advancements.critereon.FactionSubPredicate;
import de.teamlapen.vampirism.api.VampirismRegistries;
import de.teamlapen.vampirism.api.entity.factions.ISkillNode;
import de.teamlapen.vampirism.api.entity.factions.ISkillTree;
import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.vampirism.api.entity.player.skills.SkillType;
import de.teamlapen.vampirism.entity.player.lord.skills.LordSkills;
import de.teamlapen.vampirism.entity.player.skills.ActionSkill;
import de.teamlapen.vampirism.entity.player.skills.SkillNode;
import de.teamlapen.vampirism.entity.player.skills.SkillTree;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.entities.player.werewolf.actions.SurvivalWerewolfFormAction;
import de.teamlapen.werewolves.entities.player.werewolf.skill.DiggerSkill;
import de.teamlapen.werewolves.entities.player.werewolf.skill.FormActionSkill;
import de.teamlapen.werewolves.entities.player.werewolf.skill.SimpleWerewolfSkill;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.ApiStatus;

import static de.teamlapen.werewolves.util.SkillUtils.SPEED_SKILL;

@SuppressWarnings("unused")
public class ModSkills {

    public static final DeferredRegister<ISkill<?>> SKILLS = DeferredRegister.create(VampirismRegistries.Keys.SKILL, REFERENCE.MODID);

    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> LEVEL_ROOT = SKILLS.register(SkillType.LEVEL.createIdForFaction(REFERENCE.WEREWOLF_PLAYER_KEY).getPath(), () -> new SimpleWerewolfSkill(0,false));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> LORD_ROOT = SKILLS.register(SkillType.LORD.createIdForFaction(REFERENCE.WEREWOLF_PLAYER_KEY).getPath(), () -> new SimpleWerewolfSkill.LordWerewolfSkill(0,false));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> HUMAN_FORM = SKILLS.register("human_form", () -> new FormActionSkill(ModActions.HUMAN_FORM, Trees.LEVEL, 2));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> NIGHT_VISION = SKILLS.register("night_vision", () -> new SimpleWerewolfSkill(true).setToggleActions(
            (player) -> ((WerewolfPlayer) player).getSpecialAttributes().night_vision = true,
            (player) -> ((WerewolfPlayer) player).getSpecialAttributes().night_vision = false));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> RAGE = SKILLS.register("rage", () -> new ActionSkill<>(ModActions.RAGE, Trees.LEVEL, 2,true));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> BEAST_FORM = SKILLS.register("beast_form", () -> new FormActionSkill(ModActions.BEAST_FORM, Trees.LEVEL, 3));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> SURVIVAL_FORM = SKILLS.register("survival_form", () -> new FormActionSkill(ModActions.SURVIVAL_FORM, Trees.LEVEL, 3));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> STUN_BITE = SKILLS.register("stun_bite", () -> new SimpleWerewolfSkill(true).defaultDescWithFormRequirement(BEAST_FORM::get, SURVIVAL_FORM::get));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> BLEEDING_BITE = SKILLS.register("bleeding_bite", () -> new SimpleWerewolfSkill(true).defaultDescWithFormRequirement(BEAST_FORM::get, SURVIVAL_FORM::get));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> DAMAGE = SKILLS.register("damage", () -> new SimpleWerewolfSkill(true).defaultDescWithFormRequirement(BEAST_FORM::get, SURVIVAL_FORM::get));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> RESISTANCE = SKILLS.register("resistance", () -> new SimpleWerewolfSkill(true));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> HEALTH_AFTER_KILL = SKILLS.register("health_after_kill", () -> new SimpleWerewolfSkill(1, true));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> HOWLING = SKILLS.register("howling", () -> new ActionSkill<>(ModActions.HOWLING, Trees.LEVEL, true));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> SENSE = SKILLS.register("sense", () -> new ActionSkill<>(ModActions.SENSE, Trees.LEVEL, true));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> SPEED = SKILLS.register("speed", () -> new SimpleWerewolfSkill.AttributeSkill("speed", true, SPEED_SKILL, Attributes.MOVEMENT_SPEED, AttributeModifier.Operation.ADDITION, player -> WerewolvesConfig.BALANCE.SKILLS.speed_amount.get()));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> JUMP = SKILLS.register("jump", () -> new SimpleWerewolfSkill(true));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> LEAP = SKILLS.register("leap", () -> new ActionSkill<>(ModActions.LEAP, Trees.LEVEL, true));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> WOLF_PACK = SKILLS.register("wolf_pack", () -> new SimpleWerewolfSkill(1).defaultDescWithEnhancement(HOWLING::get));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> MOVEMENT_TACTICS = SKILLS.register("movement_tactics", () -> new SimpleWerewolfSkill().defaultDescWithFormRequirement(SURVIVAL_FORM::get));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> THROAT_SEEKER = SKILLS.register("throat_seeker", () -> new SimpleWerewolfSkill().defaultDescWithFormRequirement(BEAST_FORM::get));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> CLIMBER = SKILLS.register("climber", () -> new SimpleWerewolfSkill().defaultDescWithFormRequirement(SURVIVAL_FORM::get).setToggleActions(SurvivalWerewolfFormAction::climberSkillEnabled, SurvivalWerewolfFormAction::climberSkillDisabled));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> WOLF_PAWN = SKILLS.register("wolf_pawn", () -> new SimpleWerewolfSkill(1,true)); //TODO maybe add soul sand & slime & ice & webs as blocks that do not affect movement speed
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> NOT_MEAT = SKILLS.register("not_meat", () -> new SimpleWerewolfSkill(3,true));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> WATER_LOVER = SKILLS.register("water_lover", () -> new SimpleWerewolfSkill(true));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> FREE_WILL = SKILLS.register("free_will", () -> new SimpleWerewolfSkill(true));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> WEAR_ARMOR = SKILLS.register("wear_armor", () -> new SimpleWerewolfSkill(3).defaultDescWithFormRequirement(HUMAN_FORM::get));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> SILVER_BLOODED = SKILLS.register("silver_blooded", () -> new SimpleWerewolfSkill(true).defaultDescWithFormRequirement(HUMAN_FORM::get));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> HIDE_NAME = SKILLS.register("hide_name", () -> new ActionSkill<>(ModActions.HIDE_NAME, Trees.LEVEL, 1,true));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> FEAR = SKILLS.register("fear", () -> new ActionSkill<>(ModActions.FEAR, Trees.LEVEL, 1,true));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> SIXTH_SENSE = SKILLS.register("sixth_sense", () -> new SimpleWerewolfSkill(1,true));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> HEALTH_REG = SKILLS.register("health_reg", () -> new SimpleWerewolfSkill(true));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> THICK_FUR = SKILLS.register("thick_fur", () -> new SimpleWerewolfSkill().defaultDescWithFormRequirement(BEAST_FORM::get, SURVIVAL_FORM::get, HUMAN_FORM::get));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> DIGGER = SKILLS.register("digger", DiggerSkill::new);
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> ENHANCED_DIGGER = SKILLS.register("enhanced_digger", DiggerSkill::new);
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> MINION_STATS_INCREASE = SKILLS.register("werewolf_minion_stats_increase", () -> new SimpleWerewolfSkill.LordWerewolfSkill(2, true).setToggleActions(IWerewolfPlayer::updateMinionAttributes));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> MINION_COLLECT = SKILLS.register("werewolf_minion_collect", () -> new SimpleWerewolfSkill.LordWerewolfSkill(2,true));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> BEAST_RAGE = SKILLS.register("beast_rage", () -> new SimpleWerewolfSkill(true).defaultDescWithFormRequirement(BEAST_FORM::get));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> ARROW_AWARENESS = SKILLS.register("arrow_awareness", () -> new SimpleWerewolfSkill(true).defaultDescWithFormRequirement(SURVIVAL_FORM::get));
    public static final DeferredHolder<ISkill<?>, ISkill<IWerewolfPlayer>> EFFICIENT_DIET = SKILLS.register("efficient_diet", () -> new SimpleWerewolfSkill(true).defaultDescWithFormRequirement(SURVIVAL_FORM::get));

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
            return ResourceKey.create(VampirismRegistries.Keys.SKILL_NODE, new ResourceLocation(REFERENCE.MODID, "werewolf/" + path));
        }

        public static void createSkillNodes(BootstapContext<ISkillNode> context) {
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
            return ResourceKey.create(VampirismRegistries.Keys.SKILL_TREE, new ResourceLocation(REFERENCE.MODID, "werewolf/" + path));
        }

        public static void createSkillTrees(BootstapContext<ISkillTree> context) {
            HolderGetter<ISkillNode> lookup = context.lookup(VampirismRegistries.Keys.SKILL_NODE);
            context.register(LEVEL, new SkillTree(WReference.WEREWOLF_FACTION, EntityPredicate.Builder.entity().subPredicate(FactionSubPredicate.faction(WReference.WEREWOLF_FACTION)).build(), new ItemStack(ModItems.LIVER.get()), Component.translatable("text.vampirism.skills.level")));
            context.register(LORD, new SkillTree(WReference.WEREWOLF_FACTION, EntityPredicate.Builder.entity().subPredicate(FactionSubPredicate.lord(WReference.WEREWOLF_FACTION)).build(), new ItemStack(ModItems.WEREWOLF_MINION_CHARM.get()), Component.translatable("text.vampirism.skills.lord")));
        }
    }
}
