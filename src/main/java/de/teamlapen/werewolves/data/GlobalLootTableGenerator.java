package de.teamlapen.werewolves.data;

import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class GlobalLootTableGenerator extends GlobalLootModifierProvider {

    public GlobalLootTableGenerator(DataGenerator gen) {
        super(gen, REFERENCE.MODID);
    }

    @Override
    protected void start() {

    }


}
