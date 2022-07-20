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

    public static final RegistryObject<ISkill> werewolf = SKILLS.register("werewolf", SimpleWerewolfSkill::new);
    public static final RegistryObject<ISkill> human_form = SKILLS.register("human_form", () -> new ActionSkill<>(ModActions.human_form.get(), true));
    public static final RegistryObject<ISkill> night_vision = SKILLS.register("night_vision", () -> new SimpleWerewolfSkill(true).setToggleActions((player) -> ((WerewolfPlayer) player).getSpecialAttributes().night_vision = true, (player) -> ((WerewolfPlayer) player).getSpecialAttributes().night_vision = false));
    public static final RegistryObject<ISkill> rage = SKILLS.register("rage", () -> new ActionSkill<>(ModActions.rage.get(), true));
    public static final RegistryObject<ISkill> beast_form = SKILLS.register("beast_form", () -> new ActionSkill<>(ModActions.beast_form.get(), true));
    public static final RegistryObject<ISkill> stun_bite = SKILLS.register("stun_bite", () -> new SimpleWerewolfSkill( true));
    public static final RegistryObject<ISkill> bleeding_bite = SKILLS.register("bleeding_bite", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill> damage = SKILLS.register("damage", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill> resistance = SKILLS.register("resistance", () -> new SimpleWerewolfSkill( true));
    public static final RegistryObject<ISkill> health_after_kill = SKILLS.register("health_after_kill", () -> new SimpleWerewolfSkill( true));
    public static final RegistryObject<ISkill> howling = SKILLS.register("howling", () -> new ActionSkill<>(ModActions.howling.get(), true));
    public static final RegistryObject<ISkill> sense = SKILLS.register("sense", () -> new ActionSkill<>(ModActions.sense.get(), true));
    public static final RegistryObject<ISkill> survival_form = SKILLS.register("survival_form", () -> new ActionSkill<>(ModActions.survival_form.get(), true));
    public static final RegistryObject<ISkill> speed = SKILLS.register("speed", () -> new SimpleWerewolfSkill.AttributeSkill(true, SPEED_SKILL, Attributes.MOVEMENT_SPEED, AttributeModifier.Operation.ADDITION, player -> WerewolvesConfig.BALANCE.SKILLS.speed_amount.get()));
    public static final RegistryObject<ISkill> leap = SKILLS.register("leap", () -> new ActionSkill<>(ModActions.leap.get(), true));
    public static final RegistryObject<ISkill> wolf_pack = SKILLS.register("wolf_pack", () -> new SimpleWerewolfSkill().defaultDescWithEnhancement(howling));
    public static final RegistryObject<ISkill> movement_tactics = SKILLS.register("movement_tactics", () -> new SimpleWerewolfSkill().defaultDescWithFormRequirement(survival_form));
    public static final RegistryObject<ISkill> throat_seeker = SKILLS.register("throat_seeker", () -> new SimpleWerewolfSkill().defaultDescWithFormRequirement(beast_form));
    public static final RegistryObject<ISkill> climber = SKILLS.register("climber", () -> new SimpleWerewolfSkill().defaultDescWithFormRequirement(survival_form));
    public static final RegistryObject<ISkill> wolf_pawn = SKILLS.register("wolf_pawn", () -> new SimpleWerewolfSkill(true)); //TODO maybe add soul sand & slime & ice & webs as blocks that do not affect movement speed
    public static final RegistryObject<ISkill> not_meat = SKILLS.register("not_meat", () -> new SimpleWerewolfSkill( true));
    public static final RegistryObject<ISkill> water_lover = SKILLS.register("water_lover", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill> free_will = SKILLS.register("free_will", () -> new SimpleWerewolfSkill( true));
    public static final RegistryObject<ISkill> wear_armor = SKILLS.register("wear_armor", () -> new SimpleWerewolfSkill().defaultDescWithFormRequirement(human_form));
    public static final RegistryObject<ISkill> silver_blooded = SKILLS.register("silver_blooded", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill> hide_name = SKILLS.register("hide_name", () -> new ActionSkill<>(ModActions.hide_name.get(), true));
    public static final RegistryObject<ISkill> fear = SKILLS.register("fear", () -> new ActionSkill<>(ModActions.fear.get(), true));
    public static final RegistryObject<ISkill> sixth_sense = SKILLS.register("sixth_sense", () -> new SimpleWerewolfSkill( true));
    public static final RegistryObject<ISkill> health_reg = SKILLS.register("health_reg", () -> new SimpleWerewolfSkill(true));
    public static final RegistryObject<ISkill> thick_fur = SKILLS.register("thick_fur", () -> new SimpleWerewolfSkill().defaultDescWithFormRequirement(beast_form, survival_form));

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
