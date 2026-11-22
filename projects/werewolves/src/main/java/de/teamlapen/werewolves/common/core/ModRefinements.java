package de.teamlapen.werewolves.common.core;

import de.teamlapen.faction.api.FactionRegistries;
import de.teamlapen.faction.api.factions.refinements.IRefinement;
import de.teamlapen.faction.common.core.FactionRefinements;
import de.teamlapen.faction.common.world.refinements.Refinement;
import de.teamlapen.werewolves.api.WIdentifier;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public class ModRefinements {

    public static final ResourceKey<IRefinement> WEREWOLF_FORM_DURATION_GENERAL_1 = key("werewolf_form_duration_general_1");
    public static final ResourceKey<IRefinement> WEREWOLF_FORM_DURATION_GENERAL_2 = key("werewolf_form_duration_general_2");
    public static final ResourceKey<IRefinement> WEREWOLF_FORM_DURATION_SURVIVAL_1 = key("werewolf_form_duration_survival_1");
    public static final ResourceKey<IRefinement> WEREWOLF_FORM_DURATION_SURVIVAL_2 = key("werewolf_form_duration_survival_2");
    public static final ResourceKey<IRefinement> WEREWOLF_FORM_DURATION_BEAST_1 = key("werewolf_form_duration_beast_1");
    public static final ResourceKey<IRefinement> WEREWOLF_FORM_DURATION_BEAST_2 = key("werewolf_form_duration_beast_2");
    public static final ResourceKey<IRefinement> RAGE_FURY = key("rage_fury");
    public static final ResourceKey<IRefinement> HEALTH_AFTER_KILL = key("health_after_kill");
    public static final ResourceKey<IRefinement> STUN_BITE = key("stun_bite");
    public static final ResourceKey<IRefinement> BLEEDING_BITE = key("bleeding_bite");
    public static final ResourceKey<IRefinement> MORE_WOLVES = key("more_wolves");
    public static final ResourceKey<IRefinement> GREATER_DOGE_CHANCE = key("greater_doge_chance");
    public static final ResourceKey<IRefinement> NO_LEAP_COOLDOWN = key("no_leap_cooldown");

    static void createRefinements(BootstrapContext<IRefinement> context) {
        // faction refinements are referenced by the werewolves refinement sets, so they need to be present during datagen.
        // Only entries of the werewolves namespace are written by the datagen
        FactionRefinements.createRefinements(context);
        context.register(WEREWOLF_FORM_DURATION_GENERAL_1, Refinement.of(WEREWOLF_FORM_DURATION_GENERAL_1));
        context.register(WEREWOLF_FORM_DURATION_GENERAL_2, Refinement.of(WEREWOLF_FORM_DURATION_GENERAL_2));
        context.register(WEREWOLF_FORM_DURATION_SURVIVAL_1, Refinement.of(WEREWOLF_FORM_DURATION_SURVIVAL_1));
        context.register(WEREWOLF_FORM_DURATION_SURVIVAL_2, Refinement.of(WEREWOLF_FORM_DURATION_SURVIVAL_2));
        context.register(WEREWOLF_FORM_DURATION_BEAST_1, Refinement.of(WEREWOLF_FORM_DURATION_BEAST_1));
        context.register(WEREWOLF_FORM_DURATION_BEAST_2, Refinement.of(WEREWOLF_FORM_DURATION_BEAST_2));
        context.register(RAGE_FURY, Refinement.of(RAGE_FURY));
        context.register(HEALTH_AFTER_KILL, Refinement.of(HEALTH_AFTER_KILL));
        context.register(STUN_BITE, Refinement.of(STUN_BITE));
        context.register(BLEEDING_BITE, Refinement.of(BLEEDING_BITE));
        context.register(MORE_WOLVES, Refinement.of(MORE_WOLVES));
        context.register(GREATER_DOGE_CHANCE, Refinement.of(GREATER_DOGE_CHANCE));
        context.register(NO_LEAP_COOLDOWN, Refinement.of(NO_LEAP_COOLDOWN));
    }

    private static ResourceKey<IRefinement> key(String path) {
        return ResourceKey.create(FactionRegistries.Keys.REFINEMENT, WIdentifier.mod(path));
    }
}
