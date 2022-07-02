package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.entity.player.refinement.IRefinement;
import de.teamlapen.vampirism.core.ModRegistries;
import de.teamlapen.vampirism.player.refinements.Refinement;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class ModRefinements {

    public static final DeferredRegister<IRefinement> REFINEMENTS = DeferredRegister.create(ModRegistries.REFINEMENTS, REFERENCE.MODID);

    public static final RegistryObject<IRefinement> werewolf_form_duration_general_1 = newRefinement("werewolf_form_duration_general_1");
    public static final RegistryObject<IRefinement> werewolf_form_duration_general_2 = newRefinement("werewolf_form_duration_general_2");
    public static final RegistryObject<IRefinement> werewolf_form_duration_survival_1 = newRefinement("werewolf_form_duration_survival_1");
    public static final RegistryObject<IRefinement> werewolf_form_duration_survival_2 = newRefinement("werewolf_form_duration_survival_2");
    public static final RegistryObject<IRefinement> werewolf_form_duration_beast_1 = newRefinement("werewolf_form_duration_beast_1");
    public static final RegistryObject<IRefinement> werewolf_form_duration_beast_2 = newRefinement("werewolf_form_duration_beast_2");
    public static final RegistryObject<IRefinement> rage_fury = newRefinement("rage_fury");
    public static final RegistryObject<IRefinement> health_after_kill = newRefinement("health_after_kill");
    public static final RegistryObject<IRefinement> stun_bite = newRefinement("stun_bite");
    public static final RegistryObject<IRefinement> bleeding_bite = newRefinement("bleeding_bite");
    public static final RegistryObject<IRefinement> more_wolves = newRefinement("more_wolves");
    public static final RegistryObject<IRefinement> greater_doge_chance = newRefinement("greater_doge_chance");
    public static final RegistryObject<IRefinement> no_leap_cooldown = newRefinement("no_leap_cooldown");

    public static class V {
        public static final RegistryObject<IRefinement> armor1 = vRefinements("armor1");
        public static final RegistryObject<IRefinement> armor2 = vRefinements("armor2");
        public static final RegistryObject<IRefinement> armor3 = vRefinements("armor3");
        public static final RegistryObject<IRefinement >n_armor1 = vRefinements("n_armor1");
        public static final RegistryObject<IRefinement> n_armor2 = vRefinements("n_armor2");
        public static final RegistryObject<IRefinement> n_armor3 = vRefinements("n_armor3");
        public static final RegistryObject<IRefinement> speed1 = vRefinements("speed1");
        public static final RegistryObject<IRefinement> speed2 = vRefinements("speed2");
        public static final RegistryObject<IRefinement> speed3 = vRefinements("speed3");
        public static final RegistryObject<IRefinement> n_speed1 = vRefinements("n_speed1");
        public static final RegistryObject<IRefinement> n_speed2 = vRefinements("n_speed2");
        public static final RegistryObject<IRefinement> n_speed3 = vRefinements("n_speed3");
        public static final RegistryObject<IRefinement> health1 = vRefinements("health1");
        public static final RegistryObject<IRefinement> health2 = vRefinements("health2");
        public static final RegistryObject<IRefinement> health3 = vRefinements("health3");
        public static final RegistryObject<IRefinement> n_health1 = vRefinements("n_health1");
        public static final RegistryObject<IRefinement> n_health2 = vRefinements("n_health2");
        public static final RegistryObject<IRefinement> n_health3 = vRefinements("n_health3");
        public static final RegistryObject<IRefinement> damage1 = vRefinements("damage1");
        public static final RegistryObject<IRefinement> damage2 = vRefinements("damage2");
        public static final RegistryObject<IRefinement> damage3 = vRefinements("damage3");
        public static final RegistryObject<IRefinement> n_damage1 = vRefinements("n_damage1");
        public static final RegistryObject<IRefinement> n_damage2 = vRefinements("n_damage2");
        public static final RegistryObject<IRefinement> n_damage3 = vRefinements("n_damage3");
        public static final RegistryObject<IRefinement> attack_speed1 = vRefinements("attack_speed1");
        public static final RegistryObject<IRefinement> attack_speed2 = vRefinements("attack_speed2");
        public static final RegistryObject<IRefinement> attack_speed3 = vRefinements("attack_speed3");
        public static final RegistryObject<IRefinement> n_attack_speed1 = vRefinements("n_attack_speed1");
        public static final RegistryObject<IRefinement> n_attack_speed2 = vRefinements("n_attack_speed2");
        public static final RegistryObject<IRefinement> n_attack_speed3 = vRefinements("n_attack_speed3");

        private static RegistryObject<IRefinement> vRefinements(String id) {
            return RegistryObject.of(new ResourceLocation("vampirism", id), ModRegistries.REFINEMENTS);
        }
    }

    private static RegistryObject<IRefinement> newRefinement(String id) {
        return REFINEMENTS.register(id, Refinement::new);
    }

    static void registerRefinements(IEventBus bus) {
        REFINEMENTS.register(bus);
    }
}
