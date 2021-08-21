package de.teamlapen.werewolves.world.loot;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.Alternative;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MobLootModifier extends LootModifier {

    private final List<AdditionalItems> additionalItems;

    public MobLootModifier(ILootCondition[] conditionsIn, List<AdditionalItems> additionalItems) {
        super(conditionsIn);
        this.additionalItems = additionalItems;
    }

    @Nonnull
    protected static ItemStack getItemStackWithLooting(LootContext context, RandomValueRange hideDropRange, Item loot) {
        LootingEnchantBonus leb = (LootingEnchantBonus) new LootingEnchantBonus.Builder(hideDropRange).setLimit((int) hideDropRange.getMax()).build();
        return leb.run(new ItemStack(loot), context);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        for (AdditionalItems add : additionalItems) {
            generatedLoot.removeIf(itemStack -> itemStack.getItem() == add.item);
            float rand = context.getRandom().nextFloat();
            float chance = add.chance + Float.parseFloat("0." + context.getLootingModifier());
            if (chance > 1f) {
                chance = 1f;
            }
            if (rand <= chance) {
                ItemStack item = getItemStackWithLooting(context, add.range, add.item);
                generatedLoot.add(item);
            }
        }
        return generatedLoot;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final List<AdditionalItems> additionalItems = new ArrayList<>();
        private final List<ILootCondition> lootConditions = new ArrayList<>();

        private final List<EntityType<?>> entityTypes = new ArrayList<>();

        public Builder item(AdditionalItems.Builder item) {
            this.additionalItems.add(item.build());
            return this;
        }

        public Builder condition(ILootCondition.IBuilder condition) {
            this.lootConditions.add(condition.build());
            return this;
        }

        public Builder entities(EntityType<?>... type) {
            this.entityTypes.addAll(Arrays.asList(type));
            return this;
        }

        public MobLootModifier build() {
            if (!this.entityTypes.isEmpty()){
                this.lootConditions.add(this.entityTypes.stream().map(type -> EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(type))).collect(Alternative.Builder::new, Alternative.Builder::or, Alternative.Builder::or).build());
            }
            return new MobLootModifier(this.lootConditions.toArray(new ILootCondition[0]),this.additionalItems);
        }

    }

    public static class ModLootModifierSerializer extends GlobalLootModifierSerializer<MobLootModifier> {

        @Override
        public MobLootModifier read(ResourceLocation location, JsonObject object, ILootCondition[] lootConditions) {
            JsonArray itemArray = object.get("additionalItems").getAsJsonArray();
            List<AdditionalItems> additionalItems = new ArrayList<>();
            itemArray.forEach(itemObj -> additionalItems.add(AdditionalItems.deserialize(itemObj.getAsJsonObject())));
            return new MobLootModifier(lootConditions, additionalItems);
        }
        @Override
        public JsonObject write(MobLootModifier instance) {
            JsonObject obj = makeConditions(instance.conditions);
            JsonArray item = new JsonArray();
            instance.additionalItems.forEach(i -> item.add(i.serialize()));
            obj.add("additionalItems", item);
            return obj;
        }

    }

    public static class AdditionalItems {

        private static final RandomValueRange.Serializer rangeSerializer = new RandomValueRange.Serializer();
        public final Item item;
        public final RandomValueRange range;
        public final float chance;

        public final boolean useLootEnchantment;

        public AdditionalItems(Item item, RandomValueRange range, float chance, boolean useLootEnchantment) {
            this.item = item;
            this.range = range;
            this.chance = chance;
            this.useLootEnchantment = useLootEnchantment;
        }

        public JsonObject serialize() {
            JsonObject obj = new JsonObject();
            obj.addProperty("item", ForgeRegistries.ITEMS.getKey(this.item).toString());
            obj.add("range", rangeSerializer.serialize(this.range, null, null));
            obj.addProperty("chance", this.chance);
            obj.addProperty("useLootEnchantment", this.useLootEnchantment);
            return obj;
        }

        public static AdditionalItems deserialize(JsonObject obj) {
            return new AdditionalItems(ForgeRegistries.ITEMS.getValue(new ResourceLocation(obj.get("item").getAsString())), rangeSerializer.deserialize(obj.get("range"), null, null), obj.get("chance").getAsFloat(), obj.get("useLootEnchantment").getAsBoolean());
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private Item item;
            private RandomValueRange range;
            private float chance;

            private boolean useLootEnchantment;

            public Builder item(Item item) {
                this.item = item;
                return this;
            }

            public Builder range(RandomValueRange range) {
                this.range = range;
                return this;
            }

            public Builder chance(float chance) {
                this.chance = chance;
                return this;
            }

            public Builder useLootEnchantment(boolean useLootEnchantment) {
                this.useLootEnchantment = useLootEnchantment;
                return this;
            }
            public AdditionalItems build() {
                return new AdditionalItems(this.item, this.range, this.chance, this.useLootEnchantment);
            }

        }
    }
}
