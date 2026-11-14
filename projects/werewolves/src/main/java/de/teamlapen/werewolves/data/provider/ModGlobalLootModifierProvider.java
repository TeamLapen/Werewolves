package de.teamlapen.werewolves.data.provider;

import de.teamlapen.werewolves.common.core.ModEntities;
import de.teamlapen.werewolves.common.core.ModLootTables;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {

    public ModGlobalLootModifierProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries, REFERENCE.MODID);
    }

    @Override
    protected void start() {
        var entityTypes = this.registries.lookupOrThrow(Registries.ENTITY_TYPE);
        add("hunter_liver", new AddTableLootModifier(new LootItemCondition[]
                {
                        Stream.of(ModEntities.V.HUNTER.get(), ModEntities.V.HUNTER_IMOB.get()).map(type -> LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(entityTypes, type))).collect(AnyOfCondition.Builder::new, AnyOfCondition.Builder::or, AnyOfCondition.Builder::or).build()
                }
                , 0, ModLootTables.HUNTER_LIVER));
        ModLootTables.getInjectTables().forEach((original, injected) -> add(injected.identifier().getPath(), new AddTableLootModifier (new LootItemCondition[]{LootTableIdCondition.builder(original.identifier()).build()} ,0, injected)));
    }


}
