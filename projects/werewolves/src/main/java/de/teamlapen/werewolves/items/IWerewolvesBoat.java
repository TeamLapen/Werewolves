package de.teamlapen.werewolves.items;

import de.teamlapen.werewolves.core.ModBlocks;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

/**
 * must be extended by a class that extend {@link net.minecraft.world.entity.Entity}
 */
public interface IWerewolvesBoat {

    void setType(BoatType type);

    BoatType getBType();

    enum BoatType {
        JACARANDA(ModBlocks.JACARANDA_PLANKS, "jacaranda"),
        MAGIC(ModBlocks.MAGIC_PLANKS, "magic");

        private final String name;
        private final Supplier<Block> planks;

        BoatType(Supplier<Block> planks, String name) {
            this.name = name;
            this.planks = planks;
        }

        public String getName() {
            return this.name;
        }

        public Block getPlanks() {
            return planks.get();
        }

        @Override
        public String toString() {
            return this.name;
        }

        public static BoatType byId(int id) {
            BoatType[] values = values();
            if (id < 0 || id >= values.length) {
                id = 0;
            }
            return values[id];
        }

        public static BoatType byName(String name) {
            BoatType[] types = values();
            for (BoatType type : types) {
                if (type.getName().equals(name)) {
                    return type;
                }
            }
            return types[0];
        }
    }
}
