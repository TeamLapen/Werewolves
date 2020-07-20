package de.teamlapen.werewolves.player.werewolf;

public class WerewolfFormUtil {

    public enum Form {
        NONE("none"), HUMAN("human"), BEAST("beast"), SURVIVALIST("survivalist");

        private final String name;

        Form(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
