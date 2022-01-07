package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.entities.player.skill.ActionSkill;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.entities.player.werewolf.skill.SimpleWerewolfSkill;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;
import static de.teamlapen.werewolves.util.SkillUtils.SPEED_SKILL;

@SuppressWarnings("unused")
@ObjectHolder(REFERENCE.MODID)
public class WerewolfSkills {

    public static final ISkill human_form = getNull();
    public static final ISkill night_vision = getNull();
    public static final ISkill rage = getNull();
    public static final ISkill beast_form = getNull();
    public static final ISkill stun_bite = getNull();
    public static final ISkill bleeding_bite = getNull();
    public static final ISkill damage = getNull();
    public static final ISkill resistance = getNull();
    public static final ISkill health_after_kill = getNull();
    public static final ISkill howling = getNull();
    public static final ISkill sense = getNull();
    public static final ISkill survival_form = getNull();
    public static final ISkill speed = getNull();
    public static final ISkill leap = getNull();
    public static final ISkill wolf_pack = getNull();
    public static final ISkill movement_tactics = getNull();
    public static final ISkill throat_seeker = getNull();
    public static final ISkill climber = getNull();
    public static final ISkill wolf_pawn = getNull(); //TODO maybe add soul sand & slime & ice & webs as blocks that do not affect movement speed
    public static final ISkill not_meat = getNull();
    public static final ISkill water_lover = getNull();
    public static final ISkill free_will = getNull();
    public static final ISkill wear_armor = getNull();
    public static final ISkill silver_blooded = getNull();
    public static final ISkill hide_name = getNull();
    public static final ISkill fear = getNull();
    public static final ISkill sixth_sense = getNull();
    public static final ISkill health_reg = getNull();
    public static final ISkill thick_fur = getNull();


    @SuppressWarnings("deprecation")
    static void registerWerewolfSkills(IForgeRegistry<ISkill> registry) {
        registry.register(new SimpleWerewolfSkill(REFERENCE.WEREWOLF_PLAYER_KEY));
        registry.register(new ActionSkill<>(ModActions.human_form, true));
        registry.register(new SimpleWerewolfSkill("night_vision", true).setToggleActions(
                (player) -> ((WerewolfPlayer) player).getSpecialAttributes().night_vision = true,
                (player) -> ((WerewolfPlayer) player).getSpecialAttributes().night_vision = false));
        registry.register(new ActionSkill<>(ModActions.rage, true));
        registry.register(new ActionSkill<>(ModActions.beast_form, true));
        registry.register(new SimpleWerewolfSkill("health_reg", true));
        registry.register(new SimpleWerewolfSkill("damage", true));
        registry.register(new SimpleWerewolfSkill("resistance", true));
        registry.register(new SimpleWerewolfSkill("health_after_kill", true));
        registry.register(new SimpleWerewolfSkill("stun_bite", true));
        registry.register(new SimpleWerewolfSkill("bleeding_bite", true));
        registry.register(new ActionSkill<>(ModActions.howling, true));
        registry.register(new ActionSkill<>(ModActions.sense, true));
        registry.register(new ActionSkill<>(ModActions.survival_form, true));
        registry.register(new SimpleWerewolfSkill.AttributeSkill("speed", true, SPEED_SKILL, Attributes.MOVEMENT_SPEED, AttributeModifier.Operation.ADDITION, player -> WerewolvesConfig.BALANCE.SKILLS.speed_amount.get()));
        registry.register(new ActionSkill<>(ModActions.leap, true));
        registry.register(new SimpleWerewolfSkill("sixth_sense", true));
        registry.register(new ActionSkill<>(ModActions.fear, true));
        registry.register(new ActionSkill<>(ModActions.hide_name, true));
        registry.register(new SimpleWerewolfSkill("wolf_pack").defaultDescWithEnhancement(() -> howling));
        registry.register(new SimpleWerewolfSkill("throat_seeker").defaultDescWithFormRequirement(() -> beast_form));
        registry.register(new SimpleWerewolfSkill("movement_tactics").defaultDescWithFormRequirement(() -> survival_form));
        registry.register(new SimpleWerewolfSkill("climber").defaultDescWithFormRequirement(() -> survival_form));
        registry.register(new SimpleWerewolfSkill("wolf_pawn", true));
        registry.register(new SimpleWerewolfSkill("not_meat", true));
        registry.register(new SimpleWerewolfSkill("water_lover", true));
        registry.register(new SimpleWerewolfSkill("free_will", true));
        registry.register(new SimpleWerewolfSkill("wear_armor").defaultDescWithFormRequirement(() -> human_form));
        registry.register(new SimpleWerewolfSkill("silver_blooded", true));
        registry.register(new SimpleWerewolfSkill("thick_fur", true));
    }
}
