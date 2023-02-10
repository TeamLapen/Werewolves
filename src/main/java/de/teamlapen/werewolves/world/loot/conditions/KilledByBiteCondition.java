package de.teamlapen.werewolves.world.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import de.teamlapen.werewolves.core.ModLootTables;
import de.teamlapen.werewolves.util.BiteDamageSource;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

public class KilledByBiteCondition implements LootItemCondition {

    public static LootItemCondition.Builder create() {
        return KilledByBiteCondition::new;
    }

    @Override
    public @NotNull LootItemConditionType getType() {
        return ModLootTables.KILLED_BY_BITE.get();
    }

    @Override
    public boolean test(LootContext lootContext) {
        return lootContext.getParamOrNull(LootContextParams.DAMAGE_SOURCE) instanceof BiteDamageSource;
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<KilledByBiteCondition> {


        @Override
        public void serialize(@NotNull JsonObject pJson, @NotNull KilledByBiteCondition pValue, @NotNull JsonSerializationContext pSerializationContext) {
        }

        @Override
        public @NotNull KilledByBiteCondition deserialize(@NotNull JsonObject pJson, @NotNull JsonDeserializationContext pSerializationContext) {
            return new KilledByBiteCondition();
        }
    }
}
