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
import static de.teamlapen.werewolves.util.SkillUtils.*;

@SuppressWarnings("unused")
@ObjectHolder(REFERENCE.MODID)
public class WerewolfSkills {

    public static final ISkill human_form = getNull();
    public static final ISkill night_vision = getNull();
//    public static final ISkill bite = getNull(); //TODO integrate like vampire bite
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
    public static final ISkill jump = getNull();
    public static final ISkill leap = getNull();
    public static final ISkill fall_damage = getNull();
    public static final ISkill wolf_pack = getNull();
    public static final ISkill movement_tactics = getNull();
    public static final ISkill throat_seeker = getNull();//TODO
    public static final ISkill ignored = getNull();//TODO
    public static final ISkill climber = getNull();//TODO
    public static final ISkill wolf_pawn = getNull();//TODO include falldamage
    public static final ISkill not_meat = getNull();//TODO
    public static final ISkill water_lover = getNull();//TODO
    public static final ISkill free_will = getNull();//TODO
    public static final ISkill wear_armor = getNull();//TODO
    public static final ISkill silver_blooded = getNull();//TODO


    public static final ISkill health_reg = getNull();
    public static final ISkill health = getNull();
    public static final ISkill speed_after_kill = getNull();
    public static final ISkill sixth_sense = getNull();
    public static final ISkill hide_name = getNull();
    public static final ISkill fear = getNull();
    public static final ISkill better_claws = getNull();

    @SuppressWarnings("deprecation")
    static void registerWerewolfSkills(IForgeRegistry<ISkill> registry) {
        registry.register(new SimpleWerewolfSkill(REFERENCE.WEREWOLF_PLAYER_KEY));
        registry.register(new ActionSkill<>(WerewolfActions.human_form));
        registry.register(new SimpleWerewolfSkill("night_vision").setToggleActions(
                (player) -> ((WerewolfPlayer) player).getSpecialAttributes().night_vision = true,
                (player) -> ((WerewolfPlayer) player).getSpecialAttributes().night_vision = false));
        registry.register(new SimpleWerewolfSkill("night_vision").setToggleActions(
                (player) -> ((WerewolfPlayer) player).getSpecialAttributes().night_vision = true,
                (player) -> ((WerewolfPlayer) player).getSpecialAttributes().night_vision = false).setHasDefaultDescription());
        registry.register(new ActionSkill<>(WerewolfActions.bite));

        registry.register(new ActionSkill<>(WerewolfActions.rage));
        registry.register(new ActionSkill<>(WerewolfActions.beast_form));
        registry.register(new SimpleWerewolfSkill.AttributeSkill("health", true, HEALTH_SKILL, Attributes.MAX_HEALTH, AttributeModifier.Operation.ADDITION, player -> WerewolvesConfig.BALANCE.SKILLS.health_amount.get()));
        registry.register(new SimpleWerewolfSkill("health_reg", true));
        registry.register(new SimpleWerewolfSkill.AttributeSkill("damage", true, DAMAGE_SKILL, Attributes.ATTACK_DAMAGE, AttributeModifier.Operation.ADDITION, player -> WerewolvesConfig.BALANCE.SKILLS.damage_amount.get()));
        registry.register(new SimpleWerewolfSkill("resistance"));
        registry.register(new SimpleWerewolfSkill("health_after_kill"));
        registry.register(new SimpleWerewolfSkill("stun_bite"));
        registry.register(new SimpleWerewolfSkill("bleeding_bite"));
        registry.register(new SimpleWerewolfSkill("better_claws"));


        registry.register(new ActionSkill<>(WerewolfActions.howling));
        registry.register(new ActionSkill<>(WerewolfActions.sense));

        registry.register(new ActionSkill<>(WerewolfActions.survival_form));
        registry.register(new SimpleWerewolfSkill.AttributeSkill("speed", true, SPEED_SKILL, Attributes.MOVEMENT_SPEED, AttributeModifier.Operation.ADDITION, player -> WerewolvesConfig.BALANCE.SKILLS.speed_amount.get()));
        registry.register(new SimpleWerewolfSkill("jump"));
        registry.register(new SimpleWerewolfSkill("fall_damage"));
        registry.register(new ActionSkill<>(WerewolfActions.leap));
        registry.register(new ActionSkill<>(WerewolfActions.sixth_sense));
        registry.register(new SimpleWerewolfSkill("speed_after_kill"));
        registry.register(new ActionSkill<>(WerewolfActions.fear));

        registry.register(new ActionSkill<>(WerewolfActions.hide_name));
        registry.register(new ActionSkill<>(WerewolfActions.wolf_pack));

        registry.register(new SimpleWerewolfSkill("throat_seeker"));
        registry.register(new SimpleWerewolfSkill("movement_tactics"));
        registry.register(new SimpleWerewolfSkill("ignored"));
        registry.register(new SimpleWerewolfSkill("climber"));
        registry.register(new SimpleWerewolfSkill("wolf_pawn"));
        registry.register(new SimpleWerewolfSkill("not_meat"));
        registry.register(new SimpleWerewolfSkill("water_lover"));
        registry.register(new SimpleWerewolfSkill("free_will"));
        registry.register(new SimpleWerewolfSkill("wear_armor"));
        registry.register(new SimpleWerewolfSkill("silver_blooded"));
    }
}
