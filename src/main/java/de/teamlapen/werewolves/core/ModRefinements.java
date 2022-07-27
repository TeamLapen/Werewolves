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

    public static final RegistryObject<IRefinement> WEREWOLF_FORM_DURATION_GENERAL_1 = newRefinement("werewolf_form_duration_general_1");
    public static final RegistryObject<IRefinement> WEREWOLF_FORM_DURATION_GENERAL_2 = newRefinement("werewolf_form_duration_general_2");
    public static final RegistryObject<IRefinement> WEREWOLF_FORM_DURATION_SURVIVAL_1 = newRefinement("werewolf_form_duration_survival_1");
    public static final RegistryObject<IRefinement> WEREWOLF_FORM_DURATION_SURVIVAL_2 = newRefinement("werewolf_form_duration_survival_2");
    public static final RegistryObject<IRefinement> WEREWOLF_FORM_DURATION_BEAST_1 = newRefinement("werewolf_form_duration_beast_1");
    public static final RegistryObject<IRefinement> WEREWOLF_FORM_DURATION_BEAST_2 = newRefinement("werewolf_form_duration_beast_2");
    public static final RegistryObject<IRefinement> RAGE_FURY = newRefinement("rage_fury");
    public static final RegistryObject<IRefinement> HEALTH_AFTER_KILL = newRefinement("health_after_kill");
    public static final RegistryObject<IRefinement> STUN_BITE = newRefinement("stun_bite");
    public static final RegistryObject<IRefinement> BLEEDING_BITE = newRefinement("bleeding_bite");
    public static final RegistryObject<IRefinement> MORE_WOLVES = newRefinement("more_wolves");
    public static final RegistryObject<IRefinement> GREATER_DOGE_CHANCE = newRefinement("greater_doge_chance");
    public static final RegistryObject<IRefinement> NO_LEAP_COOLDOWN = newRefinement("no_leap_cooldown");

    public static class V {
        public static final RegistryObject<IRefinement> ARMOR_1 = vRefinements("armor1");
        public static final RegistryObject<IRefinement> ARMOR_2 = vRefinements("armor2");
        public static final RegistryObject<IRefinement> ARMOR_3 = vRefinements("armor3");
        public static final RegistryObject<IRefinement > N_ARMOR_1 = vRefinements("n_armor1");
        public static final RegistryObject<IRefinement> N_ARMOR_2 = vRefinements("n_armor2");
        public static final RegistryObject<IRefinement> N_ARMOR_3 = vRefinements("n_armor3");
        public static final RegistryObject<IRefinement> SPEED_1 = vRefinements("speed1");
        public static final RegistryObject<IRefinement> SPEED_2 = vRefinements("speed2");
        public static final RegistryObject<IRefinement> SPEED_3 = vRefinements("speed3");
        public static final RegistryObject<IRefinement> N_SPEED_1 = vRefinements("n_speed1");
        public static final RegistryObject<IRefinement> N_SPEED_2 = vRefinements("n_speed2");
        public static final RegistryObject<IRefinement> N_SPEED_3 = vRefinements("n_speed3");
        public static final RegistryObject<IRefinement> HEALTH_1 = vRefinements("health1");
        public static final RegistryObject<IRefinement> HEALTH_2 = vRefinements("health2");
        public static final RegistryObject<IRefinement> HEALTH_3 = vRefinements("health3");
        public static final RegistryObject<IRefinement> N_HEALTH_1 = vRefinements("n_health1");
        public static final RegistryObject<IRefinement> N_HEALTH_2 = vRefinements("n_health2");
        public static final RegistryObject<IRefinement> N_HEALTH_3 = vRefinements("n_health3");
        public static final RegistryObject<IRefinement> DAMAGE_1 = vRefinements("damage1");
        public static final RegistryObject<IRefinement> DAMAGE_2 = vRefinements("damage2");
        public static final RegistryObject<IRefinement> DAMAGE_3 = vRefinements("damage3");
        public static final RegistryObject<IRefinement> N_DAMAGE_1 = vRefinements("n_damage1");
        public static final RegistryObject<IRefinement> N_DAMAGE_2 = vRefinements("n_damage2");
        public static final RegistryObject<IRefinement> N_DAMAGE_3 = vRefinements("n_damage3");
        public static final RegistryObject<IRefinement> ATTACK_SPEED_1 = vRefinements("attack_speed1");
        public static final RegistryObject<IRefinement> ATTACK_SPEED_2 = vRefinements("attack_speed2");
        public static final RegistryObject<IRefinement> ATTACK_SPEED_3 = vRefinements("attack_speed3");
        public static final RegistryObject<IRefinement> N_ATTACK_SPEED_1 = vRefinements("n_attack_speed1");
        public static final RegistryObject<IRefinement> N_ATTACK_SPEED_2 = vRefinements("n_attack_speed2");
        public static final RegistryObject<IRefinement> N_ATTACK_SPEED_3 = vRefinements("n_attack_speed3");

        private static RegistryObject<IRefinement> vRefinements(String id) {
            return RegistryObject.of(new ResourceLocation("vampirism", id), ModRegistries.REFINEMENTS);
        }

        private static void init(){}
    }

    private static RegistryObject<IRefinement> newRefinement(String id) {
        return REFINEMENTS.register(id, Refinement::new);
    }

    static void registerRefinements(IEventBus bus) {
        REFINEMENTS.register(bus);
    }

    static void init(){}
}
