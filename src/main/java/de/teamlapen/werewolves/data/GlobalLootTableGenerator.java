package de.teamlapen.werewolves.data;

import de.teamlapen.werewolves.core.ModEntities;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModLootTables;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class GlobalLootTableGenerator extends GlobalLootModifierProvider {

    public GlobalLootTableGenerator(PackOutput packOutput,  CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries, REFERENCE.MODID);
    }

    @Override
    protected void start() {
        add("hunter_liver", new AddTableLootModifier(new LootItemCondition[]
                {
                        Stream.of(ModEntities.V.HUNTER.get(), ModEntities.V.HUNTER_IMOB.get()).map(type -> LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(type))).collect(AnyOfCondition.Builder::new, AnyOfCondition.Builder::or, AnyOfCondition.Builder::or).build()
                }
                , ModLootTables.HUNTER_LIVER));
        ModLootTables.getInjectTables().forEach((original, injected) -> add(injected.location().getPath(), new AddTableLootModifier (new LootItemCondition[]{LootTableIdCondition.builder(original.location()).build()}, injected)));
    }


}
