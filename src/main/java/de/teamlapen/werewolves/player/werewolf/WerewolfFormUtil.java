package de.teamlapen.werewolves.player.werewolf;


import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

public class WerewolfFormUtil {

    public enum Form implements IStringSerializable {
        NONE("none"), HUMAN("human"), BEAST("beast"), SURVIVALIST("survivalist");

        private final String name;

        Form(String name) {
            this.name = name;
        }

        @Nonnull
        public String getName() {
            return name;
        }
    }
}
