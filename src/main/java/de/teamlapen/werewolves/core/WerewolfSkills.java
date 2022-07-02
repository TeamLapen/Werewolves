package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.vampirism.core.ModRegistries;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.entities.player.skill.ActionSkill;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.entities.player.werewolf.skill.SimpleWerewolfSkill;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import static de.teamlapen.werewolves.util.SkillUtils.SPEED_SKILL;

public class WerewolfSkills {

    public static final DeferredRegister<ISkill> SKILLS = DeferredRegister.create(ModRegistries.SKILLS, REFERENCE.MODID);

    public static final RegistryObject<ISkill> werewolf = SKILLS.register("werewolf", () -> new SimpleWerewolfSkill(REFERENCE.WEREWOLF_PLAYER_KEY));
    public static final RegistryObject<ISkill> human_form = SKILLS.register("human_form", () -> new ActionSkill<>(ModActions.human_form.get(), true));
    public static final RegistryObject<ISkill> night_vision = SKILLS.register("night_vision", () -> new SimpleWerewolfSkill("night_vision", true).setToggleActions(
            (player) -> ((WerewolfPlayer) player).getSpecialAttributes().night_vision = true,
            (player) -> ((WerewolfPlayer) player).getSpecialAttributes().night_vision = false));
    public static final RegistryObject<ISkill> rage = SKILLS.register("rage", () -> new ActionSkill<>(ModActions.rage.get(), true));
    public static final RegistryObject<ISkill> beast_form = SKILLS.register("beast_form", () -> new ActionSkill<>(ModActions.beast_form.get(), true));
    public static final RegistryObject<ISkill> stun_bite = SKILLS.register("stun_bite", () -> new SimpleWerewolfSkill("stun_bite", true));
    public static final RegistryObject<ISkill> bleeding_bite = SKILLS.register("bleeding_bite", () -> new SimpleWerewolfSkill("bleeding_bite", true));
    public static final RegistryObject<ISkill> damage = SKILLS.register("damage", () -> new SimpleWerewolfSkill("damage", true));
    public static final RegistryObject<ISkill> resistance = SKILLS.register("resistance", () -> new SimpleWerewolfSkill("resistance", true));
    public static final RegistryObject<ISkill> health_after_kill = SKILLS.register("health_after_kill", () -> new SimpleWerewolfSkill("health_after_kill", true));
    public static final RegistryObject<ISkill> howling = SKILLS.register("howling", () -> new ActionSkill<>(ModActions.howling.get(), true));
    public static final RegistryObject<ISkill> sense = SKILLS.register("sense", () -> new ActionSkill<>(ModActions.sense.get(), true));
    public static final RegistryObject<ISkill> survival_form = SKILLS.register("survival_form", () -> new ActionSkill<>(ModActions.survival_form.get(), true));
    public static final RegistryObject<ISkill> speed = SKILLS.register("speed", () -> new SimpleWerewolfSkill.AttributeSkill("speed", true, SPEED_SKILL, Attributes.MOVEMENT_SPEED, AttributeModifier.Operation.ADDITION, player -> WerewolvesConfig.BALANCE.SKILLS.speed_amount.get()));
    public static final RegistryObject<ISkill> leap = SKILLS.register("leap", () -> new ActionSkill<>(ModActions.leap.get(), true));
    public static final RegistryObject<ISkill> wolf_pack = SKILLS.register("wolf_pack", () -> new SimpleWerewolfSkill("wolf_pack").defaultDescWithEnhancement(howling));
    public static final RegistryObject<ISkill> movement_tactics = SKILLS.register("movement_tactics", () -> new SimpleWerewolfSkill("movement_tactics").defaultDescWithFormRequirement(survival_form));
    public static final RegistryObject<ISkill> throat_seeker = SKILLS.register("throat_seeker", () -> new SimpleWerewolfSkill("throat_seeker").defaultDescWithFormRequirement(beast_form));
    public static final RegistryObject<ISkill> climber = SKILLS.register("climber", () -> new SimpleWerewolfSkill("climber").defaultDescWithFormRequirement(survival_form));
    public static final RegistryObject<ISkill> wolf_pawn = SKILLS.register("wolf_pawn", () -> new SimpleWerewolfSkill("wolf_pawn", true)); //TODO maybe add soul sand & slime & ice & webs as blocks that do not affect movement speed
    public static final RegistryObject<ISkill> not_meat = SKILLS.register("not_meat", () -> new SimpleWerewolfSkill("not_meat", true));
    public static final RegistryObject<ISkill> water_lover = SKILLS.register("water_lover", () -> new SimpleWerewolfSkill("water_lover", true));
    public static final RegistryObject<ISkill> free_will = SKILLS.register("free_will", () -> new SimpleWerewolfSkill("free_will", true));
    public static final RegistryObject<ISkill> wear_armor = SKILLS.register("wear_armor", () -> new SimpleWerewolfSkill("wear_armor").defaultDescWithFormRequirement(human_form));
    public static final RegistryObject<ISkill> silver_blooded = SKILLS.register("silver_blooded", () -> new SimpleWerewolfSkill("silver_blooded", true));
    public static final RegistryObject<ISkill> hide_name = SKILLS.register("hide_name", () -> new ActionSkill<>(ModActions.hide_name.get(), true));
    public static final RegistryObject<ISkill> fear = SKILLS.register("fear", () -> new ActionSkill<>(ModActions.fear.get(), true));
    public static final RegistryObject<ISkill> sixth_sense = SKILLS.register("sixth_sense", () -> new SimpleWerewolfSkill("sixth_sense", true));
    public static final RegistryObject<ISkill> health_reg = SKILLS.register("health_reg", () -> new SimpleWerewolfSkill("health_reg", true));
    public static final RegistryObject<ISkill> thick_fur = SKILLS.register("thick_fur", () -> new SimpleWerewolfSkill("thick_fur").defaultDescWithFormRequirement(beast_form, survival_form));

    static void registerSkills(IEventBus bus){
        SKILLS.register(bus);
    }
}
