package de.teamlapen.werewolves.advancements.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.teamlapen.werewolves.core.ModAdvancements;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class WerewolfActionCriterionTrigger extends SimpleCriterionTrigger<WerewolfActionCriterionTrigger.TriggerInstance> {

    public void trigger(@NotNull ServerPlayer player, WerewolfActionCriterionTrigger.Action action) {
        this.trigger(player, (instance) -> instance.matches(action));
    }

    @Override
    public @NotNull Codec<WerewolfActionCriterionTrigger.TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public enum Action implements StringRepresentable {
        HOWLING("howling"),
        TRANSFORM_FULL_MOON("transform_full_moon"),
        TOUCH_SILVER("touch_silver"),
        VILLAGE_CONQUERED("village_conquered"),
        ARMOR_PARTIAL("armor_partial"),
        ;

        private final String name;

        Action(String name) {
            this.name = name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }
    }

    public record TriggerInstance(@NotNull Optional<ContextAwarePredicate> player, @NotNull Action action) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                StringRepresentable.fromEnum(Action::values).fieldOf("action").forGetter(TriggerInstance::action)
        ).apply(inst, TriggerInstance::new));

        public static @NotNull Criterion<TriggerInstance> of(@NotNull Action action) {
            return ModAdvancements.TRIGGER_VAMPIRE_ACTION.get().createCriterion(new TriggerInstance(Optional.empty(), action));
        }


        boolean matches(Action action) {
            return this.action == action;
        }
    }
}
