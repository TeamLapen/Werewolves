package de.teamlapen.werewolves.entities.player.werewolf;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;

public class WerewolfPlayerSpecialAttributes {

    public boolean night_vision;
    public boolean leap;
    public int biteTicks;
    public double transformationTime;
    private int diggingLevel = -1;
    public Tier diggerTier;
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
                case 0 -> Tiers.WOOD;
                case 1 -> Tiers.STONE;
                case 2 -> Tiers.IRON;
                default -> Tiers.DIAMOND;
            };
        }
    }
}
