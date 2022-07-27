package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.vampirism.api.entity.player.skills.SkillType;
import de.teamlapen.vampirism.core.ModRegistries;
import de.teamlapen.vampirism.player.skills.ActionSkill;
import de.teamlapen.vampirism.player.skills.MinionRecoverySkill;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.entities.player.werewolf.skill.SimpleWerewolfSkill;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import javax.annotation.Nonnull;

import static de.teamlapen.werewolves.util.SkillUtils.SPEED_SKILL;

public class WerewolfSkills {

    public static final DeferredRegister<ISkill> SKILLS = DeferredRegister.create(ModRegistries.SKILLS, REFERENCE.MODID);

    public static final RegistryObject<ISkill> WEREWOLF = SKILLS.register("werewolf", SimpleWerewolfSkill::new);
    public static final RegistryObject<ISkill> HUMAN_FORM = SKILLS.register("human_form", () -> new ActionSkill<>(ModActions.HUMAN_FORM.get(), true));
    public static final RegistryObject<ISkill> NIGHT_VISION = SKILLS.register("night_vision", () -> new SimpleWerewolfSkill(true).setToggleActions((player) -> ((WerewolfPlayer) player).getSpecialAttributes().night_vision = true, (player) -> ((WerewolfPlayer) player).getSpecialAttributes().night_vision = false));
    public static final RegistryObject<ISkill> RAGE = SKILLS.register("rage", () -> new ActionSkill<>(ModActions.RAGE.get(), true));
    public static final RegistryObject<ISkill> BEAST_FORM = SKILLS.register("beast_form", () -> new ActionSkill<>(ModActions.BEAST_FORM.get(), true));
    public static final RegistryObject<ISkill> STUN_BITE = SKILLS.register("stun_bite", () -> new SimpleWerewolfSkill( true));
    public static final RegistryObject<ISkill> BLEEDING_BITE = SKILLS.register("bleeding_bite", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill> DAMAGE = SKILLS.register("damage", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill> RESISTANCE = SKILLS.register("resistance", () -> new SimpleWerewolfSkill( true));
    public static final RegistryObject<ISkill> HEALTH_AFTER_KILL = SKILLS.register("health_after_kill", () -> new SimpleWerewolfSkill( true));
    public static final RegistryObject<ISkill> HOWLING = SKILLS.register("howling", () -> new ActionSkill<>(ModActions.HOWLING.get(), true));
    public static final RegistryObject<ISkill> SENSE = SKILLS.register("sense", () -> new ActionSkill<>(ModActions.SENSE.get(), true));
    public static final RegistryObject<ISkill> SURVIVAL_FORM = SKILLS.register("survival_form", () -> new ActionSkill<>(ModActions.SURVIVAL_FORM.get(), true));
    public static final RegistryObject<ISkill> SPEED = SKILLS.register("speed", () -> new SimpleWerewolfSkill.AttributeSkill(true, SPEED_SKILL, Attributes.MOVEMENT_SPEED, AttributeModifier.Operation.ADDITION, player -> WerewolvesConfig.BALANCE.SKILLS.speed_amount.get()));
    public static final RegistryObject<ISkill> LEAP = SKILLS.register("leap", () -> new ActionSkill<>(ModActions.LEAP.get(), true));
    public static final RegistryObject<ISkill> WOLF_PACK = SKILLS.register("wolf_pack", () -> new SimpleWerewolfSkill().defaultDescWithEnhancement(HOWLING));
    public static final RegistryObject<ISkill> MOVEMENT_TACTICS = SKILLS.register("movement_tactics", () -> new SimpleWerewolfSkill().defaultDescWithFormRequirement(SURVIVAL_FORM));
    public static final RegistryObject<ISkill> THROAT_SEEKER = SKILLS.register("throat_seeker", () -> new SimpleWerewolfSkill().defaultDescWithFormRequirement(BEAST_FORM));
    public static final RegistryObject<ISkill> CLIMBER = SKILLS.register("climber", () -> new SimpleWerewolfSkill().defaultDescWithFormRequirement(SURVIVAL_FORM));
    public static final RegistryObject<ISkill> WOLF_PAWN = SKILLS.register("wolf_pawn", () -> new SimpleWerewolfSkill(true)); //TODO maybe add soul sand & slime & ice & webs as blocks that do not affect movement speed
    public static final RegistryObject<ISkill> NOT_MEAT = SKILLS.register("not_meat", () -> new SimpleWerewolfSkill( true));
    public static final RegistryObject<ISkill> WATER_LOVER = SKILLS.register("water_lover", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill> FREE_WILL = SKILLS.register("free_will", () -> new SimpleWerewolfSkill( true));
    public static final RegistryObject<ISkill> WEAR_ARMOR = SKILLS.register("wear_armor", () -> new SimpleWerewolfSkill().defaultDescWithFormRequirement(HUMAN_FORM));
    public static final RegistryObject<ISkill> SILVER_BLOODED = SKILLS.register("silver_blooded", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill> HIDE_NAME = SKILLS.register("hide_name", () -> new ActionSkill<>(ModActions.HIDE_NAME.get(), true));
    public static final RegistryObject<ISkill> FEAR = SKILLS.register("fear", () -> new ActionSkill<>(ModActions.FEAR.get(), true));
    public static final RegistryObject<ISkill> SIXTH_SENSE = SKILLS.register("sixth_sense", () -> new SimpleWerewolfSkill( true));
    public static final RegistryObject<ISkill> HEALTH_REG = SKILLS.register("health_reg", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill> THICK_FUR = SKILLS.register("thick_fur", () -> new SimpleWerewolfSkill().defaultDescWithFormRequirement(BEAST_FORM, SURVIVAL_FORM));

    public static final RegistryObject<ISkill> WEREWOLF_LORD = SKILLS.register("werewolf_lord", () -> new SimpleWerewolfSkill.LordWerewolfSkill(false));
    public static final RegistryObject<ISkill> WEREWOLF_MINION_STATS_INCREASE = SKILLS.register("werewolf_minion_stats_increase", () -> new SimpleWerewolfSkill.LordWerewolfSkill(true));
    public static final RegistryObject<ISkill> WEREWOLF_LORD_SPEED = SKILLS.register("werewolf_lord_speed", () -> new ActionSkill<>(ModActions.WEREWOLF_LORD_SPEED.get(), SkillType.LORD, true));
    public static final RegistryObject<ISkill> WEREWOLF_LORD_ATTACK_SPEED = SKILLS.register("werewolf_lord_attack_speed", () -> new ActionSkill<>(ModActions.WEREWOLF_LORD_ATTACK_SPEED.get(), SkillType.LORD, true));
    public static final RegistryObject<ISkill> WEREWOLF_MINION_COLLECT = SKILLS.register("werewolf_minion_collect", () -> new SimpleWerewolfSkill.LordWerewolfSkill(true));
    public static final RegistryObject<ISkill> WEREWOLF_MINION_RECOVERY = SKILLS.register("werewolf_minion_recovery", () -> new MinionRecoverySkill<IWerewolfPlayer>() {
        @Nonnull
        public IPlayableFaction<?> getFaction() {
            return WReference.WEREWOLF_FACTION;
        }
    });

    static void registerSkills(IEventBus bus){
        SKILLS.register(bus);
    }
}
