package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.entity.player.refinement.IRefinement;
import de.teamlapen.vampirism.player.refinements.Refinement;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.LogManager;

import java.util.Arrays;
import java.util.Objects;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class ModRefinements {

    public static final Refinement werewolf_form_duration_general = getNull();
    public static final Refinement werewolf_form_duration_human = getNull();
    public static final Refinement werewolf_form_duration_survival = getNull();
    public static final Refinement werewolf_form_duration_beast = getNull();
    public static final Refinement rage_fury = getNull();
    public static final Refinement health_after_kill = getNull();
    public static final Refinement stun_bite = getNull();
    public static final Refinement bleeding_bite = getNull();
    public static final Refinement more_wolves = getNull();
    public static final Refinement greater_doge_chance = getNull();

    @ObjectHolder(de.teamlapen.vampirism.REFERENCE.MODID)
    public static class V {
        public static final Refinement armor1 = getNull();
        public static final Refinement armor2 = getNull();
        public static final Refinement armor3 = getNull();
        public static final Refinement n_armor1 = getNull();
        public static final Refinement n_armor2 = getNull();
        public static final Refinement n_armor3 = getNull();
        public static final Refinement speed1 = getNull();
        public static final Refinement speed2 = getNull();
        public static final Refinement speed3 = getNull();
        public static final Refinement n_speed1 = getNull();
        public static final Refinement n_speed2 = getNull();
        public static final Refinement n_speed3 = getNull();
        public static final Refinement health1 = getNull();
        public static final Refinement health2 = getNull();
        public static final Refinement health3 = getNull();
        public static final Refinement n_health1 = getNull();
        public static final Refinement n_health2 = getNull();
        public static final Refinement n_health3 = getNull();
        public static final Refinement damage1 = getNull();
        public static final Refinement damage2 = getNull();
        public static final Refinement damage3 = getNull();
        public static final Refinement n_damage1 = getNull();
        public static final Refinement n_damage2 = getNull();
        public static final Refinement n_damage3 = getNull();
        public static final Refinement attack_speed1 = getNull();
        public static final Refinement attack_speed2 = getNull();
        public static final Refinement attack_speed3 = getNull();
        public static final Refinement n_attack_speed1 = getNull();
        public static final Refinement n_attack_speed2 = getNull();
        public static final Refinement n_attack_speed3 = getNull();

        public static void validate() {
            if(Arrays.stream(V.class.getFields()).anyMatch(Objects::isNull)) {
                LogManager.getLogger().error("Invalid refinement loaded");
            }
        }
    }

    public static void register(IForgeRegistry<IRefinement> registry) {
        { //simple refinement
            registry.register(newRefinement("werewolf_form_duration_general"));
            registry.register(newRefinement("werewolf_form_duration_human"));
            registry.register(newRefinement("werewolf_form_duration_survival"));
            registry.register(newRefinement("werewolf_form_duration_beast"));
            registry.register(newRefinement("rage_fury"));
            registry.register(newRefinement("health_after_kill"));
            registry.register(newRefinement("stun_bite"));
            registry.register(newRefinement("bleeding_bite"));
            registry.register(newRefinement("more_wolves"));
            registry.register(newRefinement("greater_doge_chance"));
        }
    }

    private static IRefinement newRefinement(String name) {
        return new Refinement().setRegistryName(REFERENCE.MODID, name);
    }
}
