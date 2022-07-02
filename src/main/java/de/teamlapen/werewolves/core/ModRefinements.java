package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.entity.player.refinement.IRefinement;
import de.teamlapen.vampirism.core.ModRegistries;
import de.teamlapen.vampirism.player.refinements.Refinement;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModRefinements {

    public static final DeferredRegister<IRefinement> REFINEMENTS = DeferredRegister.create(ModRegistries.REFINEMENT_ID, REFERENCE.MODID);

    public static final RegistryObject<IRefinement> werewolf_form_duration_general_1 = REFINEMENTS.register("werewolf_form_duration_general_1", Refinement::new);
    public static final RegistryObject<IRefinement> werewolf_form_duration_general_2 = REFINEMENTS.register("werewolf_form_duration_general_2", Refinement::new);
    public static final RegistryObject<IRefinement> werewolf_form_duration_survival_1 = REFINEMENTS.register("werewolf_form_duration_survival_1", Refinement::new);
    public static final RegistryObject<IRefinement> werewolf_form_duration_survival_2 = REFINEMENTS.register("werewolf_form_duration_survival_2", Refinement::new);
    public static final RegistryObject<IRefinement> werewolf_form_duration_beast_1 = REFINEMENTS.register("werewolf_form_duration_beast_1", Refinement::new);
    public static final RegistryObject<IRefinement> werewolf_form_duration_beast_2 = REFINEMENTS.register("werewolf_form_duration_beast_2", Refinement::new);
    public static final RegistryObject<IRefinement> rage_fury = REFINEMENTS.register("rage_fury", Refinement::new);
    public static final RegistryObject<IRefinement> health_after_kill = REFINEMENTS.register("health_after_kill", Refinement::new);
    public static final RegistryObject<IRefinement> stun_bite = REFINEMENTS.register("stun_bite", Refinement::new);
    public static final RegistryObject<IRefinement> bleeding_bite = REFINEMENTS.register("bleeding_bite", Refinement::new);
    public static final RegistryObject<IRefinement> more_wolves = REFINEMENTS.register("more_wolves", Refinement::new);
    public static final RegistryObject<IRefinement> greater_doge_chance = REFINEMENTS.register("greater_doge_chance", Refinement::new);
    public static final RegistryObject<IRefinement> no_leap_cooldown = REFINEMENTS.register("no_leap_cooldown", Refinement::new);

    public static class V {
        public static final RegistryObject<IRefinement> armor1 = refinement("armor1");
        public static final RegistryObject<IRefinement> armor2 = refinement("armor2");
        public static final RegistryObject<IRefinement> armor3 = refinement("armor3");
        public static final RegistryObject<IRefinement> n_armor1 = refinement("n_armor1");
        public static final RegistryObject<IRefinement> n_armor2 = refinement("n_armor2");
        public static final RegistryObject<IRefinement> n_armor3 = refinement("n_armor3");
        public static final RegistryObject<IRefinement> speed1 = refinement("speed1");
        public static final RegistryObject<IRefinement> speed2 = refinement("speed2");
        public static final RegistryObject<IRefinement> speed3 = refinement("speed3");
        public static final RegistryObject<IRefinement> n_speed1 = refinement("n_speed1");
        public static final RegistryObject<IRefinement> n_speed2 = refinement("n_speed2");
        public static final RegistryObject<IRefinement> n_speed3 = refinement("n_speed3");
        public static final RegistryObject<IRefinement> health1 = refinement("health1");
        public static final RegistryObject<IRefinement> health2 = refinement("health2");
        public static final RegistryObject<IRefinement> health3 = refinement("health3");
        public static final RegistryObject<IRefinement> n_health1 = refinement("n_health1");
        public static final RegistryObject<IRefinement> n_health2 = refinement("n_health2");
        public static final RegistryObject<IRefinement> n_health3 = refinement("n_health3");
        public static final RegistryObject<IRefinement> damage1 = refinement("damage1");
        public static final RegistryObject<IRefinement> damage2 = refinement("damage2");
        public static final RegistryObject<IRefinement> damage3 = refinement("damage3");
        public static final RegistryObject<IRefinement> n_damage1 = refinement("n_damage1");
        public static final RegistryObject<IRefinement> n_damage2 = refinement("n_damage2");
        public static final RegistryObject<IRefinement> n_damage3 = refinement("n_damage3");
        public static final RegistryObject<IRefinement> attack_speed1 = refinement("attack_speed1");
        public static final RegistryObject<IRefinement> attack_speed2 = refinement("attack_speed2");
        public static final RegistryObject<IRefinement> attack_speed3 = refinement("attack_speed3");
        public static final RegistryObject<IRefinement> n_attack_speed1 = refinement("n_attack_speed1");
        public static final RegistryObject<IRefinement> n_attack_speed2 = refinement("n_attack_speed2");
        public static final RegistryObject<IRefinement> n_attack_speed3 = refinement("n_attack_speed3");

        private static RegistryObject<IRefinement> refinement(String name) {
            return RegistryObject.create(new ResourceLocation("vampirism", name), ModRegistries.REFINEMENTS);
        }
    }

    public static void register(IEventBus bus) {
        REFINEMENTS.register(bus);
    }

}
