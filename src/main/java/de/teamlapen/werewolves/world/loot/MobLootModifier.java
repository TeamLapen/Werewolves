package de.teamlapen.werewolves.world.loot;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.teamlapen.werewolves.util.WUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.common.loot.LootModifierManager;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.minecraft.world.level.storage.loot.LootTable.createStackSplitter;

@ParametersAreNonnullByDefault
public class MobLootModifier extends LootModifier {
    private static final Codec<LootTable> LOOT_TABLE_CODEC = LootTable.CODEC;
    public static final Codec<MobLootModifier> CODEC = RecordCodecBuilder.create(inst -> {
        return codecStart(inst).and(LOOT_TABLE_CODEC.fieldOf("lootTable").forGetter(a -> a.lootTable)).apply(inst, MobLootModifier::new);
    });

    @Override
    public @NotNull Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

    private final LootTable lootTable;

    public MobLootModifier(LootItemCondition[] conditionsIn, LootTable lootTable) {
        super(conditionsIn);
        this.lootTable = lootTable;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        this.lootTable.getRandomItemsRaw(context, createStackSplitter(context.getLevel(), generatedLoot::add));
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
            return new MobLootModifier(new LootItemCondition[]{this.entityTypes.stream().map(type -> LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(type))).collect(AnyOfCondition.Builder::new, AnyOfCondition.Builder::or, AnyOfCondition.Builder::or).build()}, this.lootTable);
        }

    }
}
