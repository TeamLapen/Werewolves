package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.player.skill.ActionSkill;
import de.teamlapen.werewolves.player.skill.SimpleWerewolfSkill;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;
import static de.teamlapen.werewolves.util.SkillUtils.DAMAGE_SKILL;
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


    @SuppressWarnings("deprecation")
    static void registerWerewolfSkills(IForgeRegistry<ISkill> registry) {
        registry.register(new SimpleWerewolfSkill(REFERENCE.WEREWOLF_PLAYER_KEY));
        registry.register(new ActionSkill<>(WerewolfActions.human_form).setHasDefaultDescription());
        registry.register(new SimpleWerewolfSkill("night_vision").setToggleActions(
                (player) -> ((WerewolfPlayer) player).getSpecialAttributes().night_vision = true,
                (player) -> ((WerewolfPlayer) player).getSpecialAttributes().night_vision = false));
        registry.register(new SimpleWerewolfSkill("night_vision").setToggleActions(
                (player) -> ((WerewolfPlayer) player).getSpecialAttributes().night_vision = true,
                (player) -> ((WerewolfPlayer) player).getSpecialAttributes().night_vision = false).setHasDefaultDescription());
        registry.register(new ActionSkill<>(WerewolfActions.rage).setHasDefaultDescription());
        registry.register(new ActionSkill<>(WerewolfActions.beast_form).setHasDefaultDescription());
        registry.register(new SimpleWerewolfSkill("health_reg", true).setHasDefaultDescription());
        registry.register(new SimpleWerewolfSkill.AttributeSkill("damage", true, DAMAGE_SKILL, Attributes.ATTACK_DAMAGE, AttributeModifier.Operation.ADDITION, player -> WerewolvesConfig.BALANCE.SKILLS.damage_amount.get()).setHasDefaultDescription());
        registry.register(new SimpleWerewolfSkill("resistance").setHasDefaultDescription());
        registry.register(new SimpleWerewolfSkill("health_after_kill").setHasDefaultDescription());
        registry.register(new SimpleWerewolfSkill("stun_bite").setHasDefaultDescription());
        registry.register(new SimpleWerewolfSkill("bleeding_bite").setHasDefaultDescription());
        registry.register(new ActionSkill<>(WerewolfActions.howling).setHasDefaultDescription());
        registry.register(new ActionSkill<>(WerewolfActions.sense).setHasDefaultDescription());
        registry.register(new ActionSkill<>(WerewolfActions.survival_form).setHasDefaultDescription());
        registry.register(new SimpleWerewolfSkill.AttributeSkill("speed", true, SPEED_SKILL, Attributes.MOVEMENT_SPEED, AttributeModifier.Operation.ADDITION, player -> WerewolvesConfig.BALANCE.SKILLS.speed_amount.get()).setHasDefaultDescription());
        registry.register(new ActionSkill<>(WerewolfActions.leap).setHasDefaultDescription());
        registry.register(new SimpleWerewolfSkill("sixth_sense").setHasDefaultDescription());
        registry.register(new ActionSkill<>(WerewolfActions.fear).setHasDefaultDescription());
        registry.register(new ActionSkill<>(WerewolfActions.hide_name).setHasDefaultDescription());
        registry.register(new SimpleWerewolfSkill("wolf_pack").setHasDefaultDescription());
        registry.register(new SimpleWerewolfSkill("throat_seeker").setHasDefaultDescription());
        registry.register(new SimpleWerewolfSkill("movement_tactics").setHasDefaultDescription());
        registry.register(new SimpleWerewolfSkill("climber").setHasDefaultDescription());
        registry.register(new SimpleWerewolfSkill("wolf_pawn").setHasDefaultDescription());
        registry.register(new SimpleWerewolfSkill("not_meat").setHasDefaultDescription());
        registry.register(new SimpleWerewolfSkill("water_lover").setHasDefaultDescription());
        registry.register(new SimpleWerewolfSkill("free_will").setHasDefaultDescription());
        registry.register(new SimpleWerewolfSkill("wear_armor").setHasDefaultDescription());
        registry.register(new SimpleWerewolfSkill("silver_blooded").setHasDefaultDescription());
    }
}
