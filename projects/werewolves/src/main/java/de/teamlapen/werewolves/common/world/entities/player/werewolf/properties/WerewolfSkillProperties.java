package de.teamlapen.werewolves.common.world.entities.player.werewolf.properties;


import de.teamlapen.werewolves.common.world.entities.player.werewolf.DiggerTier;

public class WerewolfSkillProperties {

    public boolean night_vision;
    public boolean leap;
    public int biteTicks;
    public double transformationTime;
    private int diggingLevel = -1;
    public DiggerTier diggerTier;
    public float diggingSpeed = 1f;

    public void increaseDiggerLevel() {
        diggingLevel++;
        diggingSpeed += 2;
        updateDiggerTier();
    }

    public void decreaseDiggerLevel() {
        diggingLevel--;
        diggingSpeed -= 2;
        updateDiggerTier();
    }

    private void updateDiggerTier() {
        if (diggingLevel < 0) {
            diggerTier = null;
        } else {
            diggerTier = switch (diggingLevel) {
                case 0 -> DiggerTier.WOOD;
                case 1 -> DiggerTier.STONE;
                case 2 -> DiggerTier.IRON;
                default -> DiggerTier.DIAMOND;
            };
        }
    }
}
