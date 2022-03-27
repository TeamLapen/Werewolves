package de.teamlapen.werewolves.world.loot;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.AlternativeLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.minecraft.world.level.storage.loot.Deserializers.createLootTableSerializer;
import static net.minecraft.world.level.storage.loot.LootTable.createStackSplitter;

@ParametersAreNonnullByDefault
public class MobLootModifier extends LootModifier {

    private final LootTable lootTable;

    public MobLootModifier(LootItemCondition[] conditionsIn, LootTable lootTable) {
        super(conditionsIn);
        this.lootTable = lootTable;
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        this.lootTable.getRandomItemsRaw(context, createStackSplitter(generatedLoot::add));
        return generatedLoot;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private LootTable lootTable;
        private final List<EntityType<?>> entityTypes = new ArrayList<>();

        public Builder table(LootTable.Builder table) {
            this.lootTable = table.build();
            return this;
        }

        public Builder onlyFor(EntityType<?>... types) {
            return onlyFor(Arrays.asList(types));
        }

        public Builder onlyFor(List<EntityType<?>> types) {
            this.entityTypes.addAll(types);
            return this;
        }

        public MobLootModifier build() {
            if (this.entityTypes.isEmpty()) {
                throw new IllegalStateException("You must specify target entities");
            }
            return new MobLootModifier(new LootItemCondition[]{this.entityTypes.stream().map(type -> LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(type))).collect(AlternativeLootItemCondition.Builder::new, AlternativeLootItemCondition.Builder::or, AlternativeLootItemCondition.Builder::or).build()}, this.lootTable);
        }

    }

    public static class ModLootModifierSerializer extends GlobalLootModifierSerializer<MobLootModifier> {
        private static final Gson LOOT_TABLE_SERIALIZER = createLootTableSerializer().create();

        @Override
        public MobLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] lootConditions) {
            return new MobLootModifier(lootConditions, ForgeHooks.loadLootTable(LOOT_TABLE_SERIALIZER, location, object.get("loottable"), true, WUtils.LOOT_TABLE_MANAGER));
        }

        @Override
        public JsonObject write(MobLootModifier instance) {
            JsonObject obj = makeConditions(instance.conditions);
            obj.add("loottable", LOOT_TABLE_SERIALIZER.toJsonTree(instance.lootTable));
            return obj;
        }

    }
}
