package de.teamlapen.werewolves.data;

import de.teamlapen.werewolves.core.ModEntities;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.world.loot.MobLootModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class GlobalLootTableGenerator extends GlobalLootModifierProvider {

    public GlobalLootTableGenerator(PackOutput packOutput) {
        super(packOutput, REFERENCE.MODID);
    }

    @Override
    protected void start() {
        add("hunter_liver", MobLootModifier.builder()
                .table(LootTable.lootTable()
                        .withPool(LootPool.lootPool().name("werewolves_general").setRolls(ConstantValue.exactly(1)).when(LootItemKilledByPlayerCondition.killedByPlayer()).when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.33f, 0.005f))
                                .add(LootItem.lootTableItem(ModItems.LIVER.get()).setWeight(1))))
                .onlyFor(ModEntities.V.HUNTER.get(), ModEntities.V.HUNTER_IMOB.get())
                .build());
    }


}
