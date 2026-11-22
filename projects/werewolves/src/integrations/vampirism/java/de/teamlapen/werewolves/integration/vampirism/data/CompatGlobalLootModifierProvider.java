package de.teamlapen.werewolves.integration.vampirism.data;

import de.teamlapen.vampirism.common.tags.ModEntityTags;
import de.teamlapen.werewolves.common.core.ModLootTables;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.EntityTypePredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;

import java.util.concurrent.CompletableFuture;

public class CompatGlobalLootModifierProvider extends GlobalLootModifierProvider {

    public CompatGlobalLootModifierProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries, REFERENCE.MODID);
    }

    @Override
    protected void start() {
        var entityTypes = this.registries.lookupOrThrow(Registries.ENTITY_TYPE);
        add("hunter_liver", new AddTableLootModifier(new LootItemCondition[]{
                LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityTypes, ModEntityTags.HUNTER))).build()
        }, 0, ModLootTables.HUNTER_LIVER));
    }
}
