package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.vampirism.core.ModRegistries;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.entities.player.skill.ActionSkill;
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
public class WerewolfSkills {

    public static final DeferredRegister<ISkill<?>> SKILLS = DeferredRegister.create(ModRegistries.SKILLS, REFERENCE.MODID);

    public static final RegistryObject<ISkill<IWerewolfPlayer>> werewolf = SKILLS.register("werewolf", SimpleWerewolfSkill::new);
    public static final RegistryObject<ISkill<IWerewolfPlayer>> human_form = SKILLS.register("human_form", () -> new ActionSkill<>(ModActions.human_form.get(), true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> night_vision = SKILLS.register("night_vision", () -> new SimpleWerewolfSkill(true).setToggleActions(
            (player) -> ((WerewolfPlayer) player).getSpecialAttributes().night_vision = true,
            (player) -> ((WerewolfPlayer) player).getSpecialAttributes().night_vision = false));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> rage = SKILLS.register("rage", () -> new ActionSkill<>(ModActions.rage.get(), true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> beast_form = SKILLS.register("beast_form", () -> new ActionSkill<>(ModActions.beast_form.get(), true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> stun_bite = SKILLS.register("stun_bite", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> bleeding_bite = SKILLS.register("bleeding_bite", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> damage = SKILLS.register("damage", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> resistance = SKILLS.register("resistance", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> health_after_kill = SKILLS.register("health_after_kill", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> howling = SKILLS.register("howling", () -> new ActionSkill<>(ModActions.howling.get(), true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> sense = SKILLS.register("sense", () -> new ActionSkill<>(ModActions.sense.get(), true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> survival_form = SKILLS.register("survival_form", () -> new ActionSkill<>(ModActions.survival_form.get(), true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> speed = SKILLS.register("speed", () -> new SimpleWerewolfSkill.AttributeSkill("speed", true, SPEED_SKILL, Attributes.MOVEMENT_SPEED, AttributeModifier.Operation.ADDITION, player -> WerewolvesConfig.BALANCE.SKILLS.speed_amount.get()));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> leap = SKILLS.register("leap", () -> new ActionSkill<>(ModActions.leap.get(), true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> wolf_pack = SKILLS.register("wolf_pack", () -> new SimpleWerewolfSkill().defaultDescWithEnhancement(howling::get));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> movement_tactics = SKILLS.register("movement_tactics", () -> new SimpleWerewolfSkill().defaultDescWithFormRequirement(survival_form::get));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> throat_seeker = SKILLS.register("throat_seeker", () -> new SimpleWerewolfSkill().defaultDescWithFormRequirement(beast_form::get));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> climber = SKILLS.register("climber", () -> new SimpleWerewolfSkill().defaultDescWithFormRequirement(survival_form::get));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> wolf_pawn = SKILLS.register("wolf_pawn", () -> new SimpleWerewolfSkill(true)); //TODO maybe add soul sand & slime & ice & webs as blocks that do not affect movement speed
    public static final RegistryObject<ISkill<IWerewolfPlayer>> not_meat = SKILLS.register("not_meat", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> water_lover = SKILLS.register("water_lover", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> free_will = SKILLS.register("free_will", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> wear_armor = SKILLS.register("wear_armor", () -> new SimpleWerewolfSkill().defaultDescWithFormRequirement(human_form::get));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> silver_blooded = SKILLS.register("silver_blooded", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> hide_name = SKILLS.register("hide_name", () -> new ActionSkill<>(ModActions.hide_name.get(), true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> fear = SKILLS.register("fear", () -> new ActionSkill<>(ModActions.fear.get(), true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> sixth_sense = SKILLS.register("sixth_sense", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> health_reg = SKILLS.register("health_reg", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill<IWerewolfPlayer>> thick_fur = SKILLS.register("thick_fur", () -> new SimpleWerewolfSkill().defaultDescWithFormRequirement(beast_form::get, survival_form::get));


    static void registerWerewolfSkills(IEventBus bus) {
        SKILLS.register(bus);
    }
}
