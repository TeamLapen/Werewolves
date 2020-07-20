package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.vampirism.player.skills.ActionSkill;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entity.player.IWerewolfPlayer;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.player.SimpleWerewolfSkill;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@SuppressWarnings("unused")
@ObjectHolder(REFERENCE.MODID)
public class WerewolfSkills {

    public static final ISkill night_vision = getNull();
    public static final ISkill bite = getNull();
    public static final ISkill howling = getNull();
    public static final ISkill werewolf_form = getNull();

    static void registerWerewolfSkills(IForgeRegistry<ISkill> registry) {
        registry.register(new SimpleWerewolfSkill(REFERENCE.WEREWOLF_PLAYER_KEY));
        registry.register(new ActionSkill<>(new ResourceLocation(REFERENCE.MODID, "werewolf_form"), WerewolfActions.werewolf_form));
        registry.register(new SimpleWerewolfSkill.ToggleWerewolfAction("night_vision", (player, value) ->((WerewolfPlayer)player).getSpecialAttributes().night_vision = value));
        registry.register(new ActionSkill<>(new ResourceLocation(REFERENCE.MODID, "bite"), WerewolfActions.bite));
        registry.register(new ActionSkill<>(new ResourceLocation(REFERENCE.MODID, "howling"), WerewolfActions.howling));

        //beast tree
//        registry.register(new SimpleWerewolfSkill("rage"));//action
//        registry.register(new SimpleWerewolfSkill("health"));//skill
//        registry.register(new SimpleWerewolfSkill("health_reg"));//skill
//        registry.register(new SimpleWerewolfSkill("more_damage"));//skill
//        registry.register(new SimpleWerewolfSkill("resist"));//skill
//        registry.register(new SimpleWerewolfSkill("beast_form"));//action
//        registry.register(new SimpleWerewolfSkill("stun_bite"));//skill
//        registry.register(new SimpleWerewolfSkill("better_claws"));//skill
//
//        //survival tree
//        registry.register(new SimpleWerewolfSkill("survival_form"));//action
//        registry.register(new SimpleWerewolfSkill("sense"));//action
//        registry.register(new SimpleWerewolfSkill("advanced_sense"));//skill
//        registry.register(new SimpleWerewolfSkill("sixth_sense"));//skill
//        registry.register(new SimpleWerewolfSkill("speed_after_kill"));//skill
//        registry.register(new SimpleWerewolfSkill("leap"));//skill
//        registry.register(new SimpleWerewolfSkill("fall_damage"));//skill
//        registry.register(new SimpleWerewolfSkill("speed"));//skill
//        registry.register(new SimpleWerewolfSkill("jump"));//skill
//
//
//
//        registry.register(new SimpleWerewolfSkill("invisible"));//skill
//        registry.register(new SimpleWerewolfSkill("movement_attacks"));//skill
    }
}
