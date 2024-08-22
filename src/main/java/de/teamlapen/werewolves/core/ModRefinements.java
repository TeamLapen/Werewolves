package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.VampirismRegistries;
import de.teamlapen.vampirism.api.entity.player.refinement.IRefinement;
import de.teamlapen.vampirism.entity.player.refinements.Refinement;
import de.teamlapen.werewolves.api.WResourceLocation;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRefinements {

    public static final DeferredRegister<IRefinement> REFINEMENTS = DeferredRegister.create(VampirismRegistries.Keys.REFINEMENT, REFERENCE.MODID);

    public static final DeferredHolder<IRefinement, IRefinement> WEREWOLF_FORM_DURATION_GENERAL_1 = REFINEMENTS.register("werewolf_form_duration_general_1", Refinement::new);
    public static final DeferredHolder<IRefinement, IRefinement> WEREWOLF_FORM_DURATION_GENERAL_2 = REFINEMENTS.register("werewolf_form_duration_general_2", Refinement::new);
    public static final DeferredHolder<IRefinement, IRefinement> WEREWOLF_FORM_DURATION_SURVIVAL_1 = REFINEMENTS.register("werewolf_form_duration_survival_1", Refinement::new);
    public static final DeferredHolder<IRefinement, IRefinement> WEREWOLF_FORM_DURATION_SURVIVAL_2 = REFINEMENTS.register("werewolf_form_duration_survival_2", Refinement::new);
    public static final DeferredHolder<IRefinement, IRefinement> WEREWOLF_FORM_DURATION_BEAST_1 = REFINEMENTS.register("werewolf_form_duration_beast_1", Refinement::new);
    public static final DeferredHolder<IRefinement, IRefinement> WEREWOLF_FORM_DURATION_BEAST_2 = REFINEMENTS.register("werewolf_form_duration_beast_2", Refinement::new);
    public static final DeferredHolder<IRefinement, IRefinement> RAGE_FURY = REFINEMENTS.register("rage_fury", Refinement::new);
    public static final DeferredHolder<IRefinement, IRefinement> HEALTH_AFTER_KILL = REFINEMENTS.register("health_after_kill", Refinement::new);
    public static final DeferredHolder<IRefinement, IRefinement> STUN_BITE = REFINEMENTS.register("stun_bite", Refinement::new);
    public static final DeferredHolder<IRefinement, IRefinement> BLEEDING_BITE = REFINEMENTS.register("bleeding_bite", Refinement::new);
    public static final DeferredHolder<IRefinement, IRefinement> MORE_WOLVES = REFINEMENTS.register("more_wolves", Refinement::new);
    public static final DeferredHolder<IRefinement, IRefinement> GREATER_DOGE_CHANCE = REFINEMENTS.register("greater_doge_chance", Refinement::new);
    public static final DeferredHolder<IRefinement, IRefinement> NO_LEAP_COOLDOWN = REFINEMENTS.register("no_leap_cooldown", Refinement::new);

    public static class V {
        public static final DeferredHolder<IRefinement, IRefinement> ARMOR_1 = refinement("armor1");
        public static final DeferredHolder<IRefinement, IRefinement> ARMOR_2 = refinement("armor2");
        public static final DeferredHolder<IRefinement, IRefinement> ARMOR_3 = refinement("armor3");
        public static final DeferredHolder<IRefinement, IRefinement> N_ARMOR_1 = refinement("n_armor1");
        public static final DeferredHolder<IRefinement, IRefinement> N_ARMOR_2 = refinement("n_armor2");
        public static final DeferredHolder<IRefinement, IRefinement> N_ARMOR_3 = refinement("n_armor3");
        public static final DeferredHolder<IRefinement, IRefinement> SPEED_1 = refinement("speed1");
        public static final DeferredHolder<IRefinement, IRefinement> SPEED_2 = refinement("speed2");
        public static final DeferredHolder<IRefinement, IRefinement> SPEED_3 = refinement("speed3");
        public static final DeferredHolder<IRefinement, IRefinement> N_SPEED_1 = refinement("n_speed1");
        public static final DeferredHolder<IRefinement, IRefinement> N_SPEED_2 = refinement("n_speed2");
        public static final DeferredHolder<IRefinement, IRefinement> N_SPEED_3 = refinement("n_speed3");
        public static final DeferredHolder<IRefinement, IRefinement> HEALTH_1 = refinement("health1");
        public static final DeferredHolder<IRefinement, IRefinement> HEALTH_2 = refinement("health2");
        public static final DeferredHolder<IRefinement, IRefinement> HEALTH_3 = refinement("health3");
        public static final DeferredHolder<IRefinement, IRefinement> N_HEALTH_1 = refinement("n_health1");
        public static final DeferredHolder<IRefinement, IRefinement> N_HEALTH_2 = refinement("n_health2");
        public static final DeferredHolder<IRefinement, IRefinement> N_HEALTH_3 = refinement("n_health3");
        public static final DeferredHolder<IRefinement, IRefinement> DAMAGE_1 = refinement("damage1");
        public static final DeferredHolder<IRefinement, IRefinement> DAMAGE_2 = refinement("damage2");
        public static final DeferredHolder<IRefinement, IRefinement> DAMAGE_3 = refinement("damage3");
        public static final DeferredHolder<IRefinement, IRefinement> N_DAMAGE_1 = refinement("n_damage1");
        public static final DeferredHolder<IRefinement, IRefinement> N_DAMAGE_2 = refinement("n_damage2");
        public static final DeferredHolder<IRefinement, IRefinement> N_DAMAGE_3 = refinement("n_damage3");
        public static final DeferredHolder<IRefinement, IRefinement> ATTACK_SPEED_1 = refinement("attack_speed1");
        public static final DeferredHolder<IRefinement, IRefinement> ATTACK_SPEED_2 = refinement("attack_speed2");
        public static final DeferredHolder<IRefinement, IRefinement> ATTACK_SPEED_3 = refinement("attack_speed3");
        public static final DeferredHolder<IRefinement, IRefinement> N_ATTACK_SPEED_1 = refinement("n_attack_speed1");
        public static final DeferredHolder<IRefinement, IRefinement> N_ATTACK_SPEED_2 = refinement("n_attack_speed2");
        public static final DeferredHolder<IRefinement, IRefinement> N_ATTACK_SPEED_3 = refinement("n_attack_speed3");

        private static void init(){}

        private static DeferredHolder<IRefinement, IRefinement> refinement(String name) {
            return DeferredHolder.create(ResourceKey.create(VampirismRegistries.Keys.REFINEMENT, WResourceLocation.v(name)));
        }
    }

    static {
        V.init();
    }

    static void register(IEventBus bus) {
        REFINEMENTS.register(bus);
    }

}
