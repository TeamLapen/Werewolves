package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.VampirismRegistries;
import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.vampirism.api.entity.player.skills.SkillType;
import de.teamlapen.vampirism.entity.player.skills.ActionSkill;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.entities.player.werewolf.skill.SimpleWerewolfSkill;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static de.teamlapen.werewolves.util.SkillUtils.SPEED_SKILL;

@SuppressWarnings("unused")
public class ModSkills {

    public static final DeferredRegister<ISkill<?>> SKILLS = DeferredRegister.create(VampirismRegistries.SKILLS_ID, REFERENCE.MODID);

    public static final RegistryObject<ISkill<IWerewolfPlayer>> HUMAN_FORM = SKILLS.register("human_form", () -> new ActionSkill<>(ModActions.HUMAN_FORM, true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> NIGHT_VISION = SKILLS.register("night_vision", () -> new SimpleWerewolfSkill(true).setToggleActions(
            (player) -> ((WerewolfPlayer) player).getSpecialAttributes().night_vision = true,
            (player) -> ((WerewolfPlayer) player).getSpecialAttributes().night_vision = false));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> RAGE = SKILLS.register("rage", () -> new ActionSkill<>(ModActions.RAGE, true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> BEAST_FORM = SKILLS.register("beast_form", () -> new ActionSkill<>(ModActions.BEAST_FORM, true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> SURVIVAL_FORM = SKILLS.register("survival_form", () -> new ActionSkill<>(ModActions.SURVIVAL_FORM, true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> STUN_BITE = SKILLS.register("stun_bite", () -> new SimpleWerewolfSkill(true).defaultDescWithFormRequirement(BEAST_FORM::get, SURVIVAL_FORM::get));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> BLEEDING_BITE = SKILLS.register("bleeding_bite", () -> new SimpleWerewolfSkill(true).defaultDescWithFormRequirement(BEAST_FORM::get, SURVIVAL_FORM::get));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> DAMAGE = SKILLS.register("damage", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> RESISTANCE = SKILLS.register("resistance", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> HEALTH_AFTER_KILL = SKILLS.register("health_after_kill", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> HOWLING = SKILLS.register("howling", () -> new ActionSkill<>(ModActions.HOWLING, true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> SENSE = SKILLS.register("sense", () -> new ActionSkill<>(ModActions.SENSE, true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> SPEED = SKILLS.register("speed", () -> new SimpleWerewolfSkill.AttributeSkill("speed", true, SPEED_SKILL, Attributes.MOVEMENT_SPEED, AttributeModifier.Operation.ADDITION, player -> WerewolvesConfig.BALANCE.SKILLS.speed_amount.get()));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> LEAP = SKILLS.register("leap", () -> new ActionSkill<>(ModActions.LEAP, true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> WOLF_PACK = SKILLS.register("wolf_pack", () -> new SimpleWerewolfSkill().defaultDescWithEnhancement(HOWLING::get));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> MOVEMENT_TACTICS = SKILLS.register("movement_tactics", () -> new SimpleWerewolfSkill().defaultDescWithFormRequirement(SURVIVAL_FORM::get));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> THROAT_SEEKER = SKILLS.register("throat_seeker", () -> new SimpleWerewolfSkill().defaultDescWithFormRequirement(BEAST_FORM::get));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> CLIMBER = SKILLS.register("climber", () -> new SimpleWerewolfSkill().defaultDescWithFormRequirement(SURVIVAL_FORM::get));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> WOLF_PAWN = SKILLS.register("wolf_pawn", () -> new SimpleWerewolfSkill(true)); //TODO maybe add soul sand & slime & ice & webs as blocks that do not affect movement speed
    public static final RegistryObject<ISkill<IWerewolfPlayer>> NOT_MEAT = SKILLS.register("not_meat", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> WATER_LOVER = SKILLS.register("water_lover", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> FREE_WILL = SKILLS.register("free_will", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> WEAR_ARMOR = SKILLS.register("wear_armor", () -> new SimpleWerewolfSkill().defaultDescWithFormRequirement(HUMAN_FORM::get).setToggleActions(IWerewolfPlayer::checkArmorStatus, IWerewolfPlayer::checkArmorStatus));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> SILVER_BLOODED = SKILLS.register("silver_blooded", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> HIDE_NAME = SKILLS.register("hide_name", () -> new ActionSkill<>(ModActions.HIDE_NAME, true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> FEAR = SKILLS.register("fear", () -> new ActionSkill<>(ModActions.FEAR, true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> SIXTH_SENSE = SKILLS.register("sixth_sense", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> HEALTH_REG = SKILLS.register("health_reg", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> THICK_FUR = SKILLS.register("thick_fur", () -> new SimpleWerewolfSkill().defaultDescWithFormRequirement(BEAST_FORM::get, SURVIVAL_FORM::get));

    public static final RegistryObject<ISkill<IWerewolfPlayer>> MINION_STATS_INCREASE = SKILLS.register("werewolf_minion_stats_increase", () -> new SimpleWerewolfSkill.LordWerewolfSkill(true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> MINION_COLLECT = SKILLS.register("werewolf_minion_collect", () -> new SimpleWerewolfSkill.LordWerewolfSkill(true));

    static {
        SKILLS.register(SkillType.LEVEL.createIdForFaction(REFERENCE.WEREWOLF_PLAYER_KEY).getPath(), () -> new SimpleWerewolfSkill(false));
        SKILLS.register(SkillType.LORD.createIdForFaction(REFERENCE.WEREWOLF_PLAYER_KEY).getPath(), () -> new SimpleWerewolfSkill.LordWerewolfSkill(false));
    }

    static void register(IEventBus bus) {
        SKILLS.register(bus);
    }

}
