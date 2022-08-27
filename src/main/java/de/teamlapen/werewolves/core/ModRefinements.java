package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.VampirismRegistries;
import de.teamlapen.vampirism.api.entity.player.refinement.IRefinement;
import de.teamlapen.vampirism.entity.player.refinements.Refinement;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class ModRefinements {

    public static final DeferredRegister<IRefinement> REFINEMENTS = DeferredRegister.create(VampirismRegistries.REFINEMENT_ID, REFERENCE.MODID);

    public static final RegistryObject<IRefinement> WEREWOLF_FORM_DURATION_GENERAL_1 = REFINEMENTS.register("werewolf_form_duration_general_1", Refinement::new);
    public static final RegistryObject<IRefinement> WEREWOLF_FORM_DURATION_GENERAL_2 = REFINEMENTS.register("werewolf_form_duration_general_2", Refinement::new);
    public static final RegistryObject<IRefinement> WEREWOLF_FORM_DURATION_SURVIVAL_1 = REFINEMENTS.register("werewolf_form_duration_survival_1", Refinement::new);
    public static final RegistryObject<IRefinement> WEREWOLF_FORM_DURATION_SURVIVAL_2 = REFINEMENTS.register("werewolf_form_duration_survival_2", Refinement::new);
    public static final RegistryObject<IRefinement> WEREWOLF_FORM_DURATION_BEAST_1 = REFINEMENTS.register("werewolf_form_duration_beast_1", Refinement::new);
    public static final RegistryObject<IRefinement> WEREWOLF_FORM_DURATION_BEAST_2 = REFINEMENTS.register("werewolf_form_duration_beast_2", Refinement::new);
    public static final RegistryObject<IRefinement> RAGE_FURY = REFINEMENTS.register("rage_fury", Refinement::new);
    public static final RegistryObject<IRefinement> HEALTH_AFTER_KILL = REFINEMENTS.register("health_after_kill", Refinement::new);
    public static final RegistryObject<IRefinement> STUN_BITE = REFINEMENTS.register("stun_bite", Refinement::new);
    public static final RegistryObject<IRefinement> BLEEDING_BITE = REFINEMENTS.register("bleeding_bite", Refinement::new);
    public static final RegistryObject<IRefinement> MORE_WOLVES = REFINEMENTS.register("more_wolves", Refinement::new);
    public static final RegistryObject<IRefinement> GREATER_DOGE_CHANCE = REFINEMENTS.register("greater_doge_chance", Refinement::new);
    public static final RegistryObject<IRefinement> NO_LEAP_COOLDOWN = REFINEMENTS.register("no_leap_cooldown", Refinement::new);

    public static class V {
        public static final RegistryObject<IRefinement> ARMOR_1 = refinement("armor1");
        public static final RegistryObject<IRefinement> ARMOR_2 = refinement("armor2");
        public static final RegistryObject<IRefinement> ARMOR_3 = refinement("armor3");
        public static final RegistryObject<IRefinement> N_ARMOR_1 = refinement("n_armor1");
        public static final RegistryObject<IRefinement> N_ARMOR_2 = refinement("n_armor2");
        public static final RegistryObject<IRefinement> N_ARMOR_3 = refinement("n_armor3");
        public static final RegistryObject<IRefinement> SPEED_1 = refinement("speed1");
        public static final RegistryObject<IRefinement> SPEED_2 = refinement("speed2");
        public static final RegistryObject<IRefinement> SPEED_3 = refinement("speed3");
        public static final RegistryObject<IRefinement> N_SPEED_1 = refinement("n_speed1");
        public static final RegistryObject<IRefinement> N_SPEED_2 = refinement("n_speed2");
        public static final RegistryObject<IRefinement> N_SPEED_3 = refinement("n_speed3");
        public static final RegistryObject<IRefinement> HEALTH_1 = refinement("health1");
        public static final RegistryObject<IRefinement> HEALTH_2 = refinement("health2");
        public static final RegistryObject<IRefinement> HEALTH_3 = refinement("health3");
        public static final RegistryObject<IRefinement> N_HEALTH_1 = refinement("n_health1");
        public static final RegistryObject<IRefinement> N_HEALTH_2 = refinement("n_health2");
        public static final RegistryObject<IRefinement> N_HEALTH_3 = refinement("n_health3");
        public static final RegistryObject<IRefinement> DAMAGE_1 = refinement("damage1");
        public static final RegistryObject<IRefinement> DAMAGE_2 = refinement("damage2");
        public static final RegistryObject<IRefinement> DAMAGE_3 = refinement("damage3");
        public static final RegistryObject<IRefinement> N_DAMAGE_1 = refinement("n_damage1");
        public static final RegistryObject<IRefinement> N_DAMAGE_2 = refinement("n_damage2");
        public static final RegistryObject<IRefinement> N_DAMAGE_3 = refinement("n_damage3");
        public static final RegistryObject<IRefinement> ATTACK_SPEED_1 = refinement("attack_speed1");
        public static final RegistryObject<IRefinement> ATTACK_SPEED_2 = refinement("attack_speed2");
        public static final RegistryObject<IRefinement> ATTACK_SPEED_3 = refinement("attack_speed3");
        public static final RegistryObject<IRefinement> N_ATTACK_SPEED_1 = refinement("n_attack_speed1");
        public static final RegistryObject<IRefinement> N_ATTACK_SPEED_2 = refinement("n_attack_speed2");
        public static final RegistryObject<IRefinement> N_ATTACK_SPEED_3 = refinement("n_attack_speed3");

        private static void init(){}

        private static @NotNull RegistryObject<IRefinement> refinement(@NotNull String name) {
            return RegistryObject.create(new ResourceLocation("vampirism", name), VampirismRegistries.REFINEMENT_ID, REFERENCE.MODID);
        }
    }

    static {
        V.init();
    }

    static void register(IEventBus bus) {
        REFINEMENTS.register(bus);
    }

}
