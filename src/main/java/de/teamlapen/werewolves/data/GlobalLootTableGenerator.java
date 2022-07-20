package de.teamlapen.werewolves.data;

import de.teamlapen.werewolves.core.ModEntities;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModLootTables;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.world.loot.MobLootModifier;
import net.minecraft.data.DataGenerator;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.conditions.KilledByPlayer;
import net.minecraft.loot.conditions.RandomChanceWithLooting;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class GlobalLootTableGenerator extends GlobalLootModifierProvider {

    public GlobalLootTableGenerator(DataGenerator gen) {
        super(gen, REFERENCE.MODID);
    }

    @Override
    protected void start() {
        add("hunter_liver", ModLootTables.mob_modifier.get(), MobLootModifier.builder()
                .table(LootTable.lootTable()
                        .withPool(LootPool.lootPool().name("werewolves_general").setRolls(ConstantRange.exactly(1)).when(KilledByPlayer.killedByPlayer()).when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.33f, 0.005f))
                                .add(ItemLootEntry.lootTableItem(ModItems.liver.get()).setWeight(1))))
                .onlyFor(ModEntities.V.hunter.get(), ModEntities.V.hunter_imob.get())
                .build());
    }


}
