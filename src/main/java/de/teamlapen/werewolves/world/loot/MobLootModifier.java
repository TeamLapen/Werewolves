package de.teamlapen.werewolves.world.loot;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.conditions.Alternative;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.minecraft.loot.LootSerializers.createLootTableSerializer;
import static net.minecraft.loot.LootTable.createStackSplitter;

@ParametersAreNonnullByDefault
public class MobLootModifier extends LootModifier {

    private final LootTable lootTable;

    public MobLootModifier(ILootCondition[] conditionsIn, LootTable lootTable) {
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
            return new MobLootModifier(new ILootCondition[]{this.entityTypes.stream().map(type -> EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(type))).collect(Alternative.Builder::new, Alternative.Builder::or, Alternative.Builder::or).build()}, this.lootTable);
        }

    }

    public static class ModLootModifierSerializer extends GlobalLootModifierSerializer<MobLootModifier> {
        private static final Gson LOOT_TABLE_SERIALIZER = createLootTableSerializer().create();

        @Override
        public MobLootModifier read(ResourceLocation location, JsonObject object, ILootCondition[] lootConditions) {
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
